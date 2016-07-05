package com.yuyang.fitsystemwindowstestdrawer.imageLoader.imageLoaderUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 类似开源框架ImageLoader的图片加载器
 */
public class ImageLoader {
    private static final String TAG = "MyImageLoader";

    private static ImageLoader mInstance;
    /**
     * 图片缓存
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    private static final int DEAFULT_THREAD_COUNT = 3;
    /**
     * 后台轮询线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    /**
     * UI线程中的Handler
     */
    private Handler mUIHandler;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTaskQueue;
    /**
     * 队列的调度方式（默认先进后出）
     */
    private Type mType = Type.LIFO;
    /**
     * 是否开启硬盘缓存
     */
    private boolean isDiskCacheEnable = true;
    /**
     * 信号量
     * 第一个：mSemaphorePoolThreadHandler = new Semaphore(0);
     *  用于控制我们的mPoolThreadHandler的初始化完成，我们在使用mPoolThreadHandler会进行判空，如果为null，
     *  会通过mSemaphorePoolThreadHandler.acquire()进行阻塞；当mPoolThreadHandler初始化结束，
     *  我们会调用.release();解除阻塞。
     * 第二个：mSemaphoreThreadPool = new Semaphore(threadCount);
     *  这个信号量的数量和我们加载图片的线程个数一致；每取一个任务去执行，我们会让信号量减一；每完成一个任务，
     *  会让信号量+1，再去取任务；目的是什么呢？当我们的任务到来时，如果此时在没有空闲线程，任务则一直添加到TaskQueue中，
     *  当线程完成任务，可以根据策略去TaskQueue中去取任务，只有这样，我们的LIFO才有意义。
     */
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    /**
     * 队列的调度方式
     */
    public enum Type{
        FIFO, LIFO
    }

    public static ImageLoader getIntance(){
        if (mInstance == null){
            synchronized (ImageLoader.class){
                if (mInstance == null) {
                    //默认线程池中3个线程，队列调度方式为先进后出
                    mInstance = new ImageLoader(DEAFULT_THREAD_COUNT, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    private ImageLoader(int threadCount, Type type){
        init(threadCount, type);
    }

    /**
     * 初始化操作
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        //初始化后台轮询线程
        initBackThread();
        //获取应用的最大可用内存,设置缓存为最大内存的八分之一
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory/8;
        mLruCache = new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
        //创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<>();
        mSemaphoreThreadPool = new Semaphore(threadCount);
        mType = type;
    }

    /**
     * 初始化后台轮询线程
     */
    private void initBackThread() {
        mPoolThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //线程池去取出一个任务进行执行
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                        }
                    }
                };
                //释放一个信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        });
        mPoolThread.start();
    }

    /**
     * 任务出队
     * @return
     */
    private Runnable getTask() {
        if (mType == Type.FIFO){
            return mTaskQueue.removeFirst();
        }else if (mType == Type.LIFO){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    /**
     * 加载图片
     * @param path
     * @param imageView
     * @param isFromNet
     */
    public void loadImage(String path, ImageView imageView, boolean isFromNet){
        //设置Tag避免图片错位
        imageView.setTag(path);
        if (mUIHandler == null){
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    // 获取得到图片，为imageview回调设置图片
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    String path = holder.path;
                    Bitmap bm = holder.bitmap;
                    ImageView imageView = holder.imageView;
                    if (imageView.getTag().toString().equals(path)){
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }
        //根据path在缓存mLruCache中获取bitmap
        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null){
            refreshBitmap(path, imageView, bm);
        }else {
            addTask(buildTask(path, imageView, isFromNet));
        }
    }

    /**
     * 向任务队列中添加新的任务
     * @param runnable
     */
    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        try {
            //如果mPoolThreadHandler是空的说明初始化操作还没有走完
            if (mPoolThreadHandler == null) {
                mSemaphorePoolThreadHandler.acquire();
            }
        } catch (InterruptedException e) {}
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * 根据传入的参数，新建任务
     * @param path
     * @param imageView
     * @param isFromNet
     */
    private Runnable buildTask(final String path, final ImageView imageView, final boolean isFromNet) {
        return new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                if (isFromNet){
                    //查找缓存文件
                    File file = getDiskCacheDir(imageView.getContext(), md5(path));
                    if (file.exists()){
                        Log.e(TAG, "找到磁盘缓存图片 :" + path);
                        bitmap = loadImageFromLocal(file.getAbsolutePath(), imageView);
                    }else {
                        //是否开启了硬盘缓存
                        if (isDiskCacheEnable){
                            //下载图片
                            boolean downloadState = DownloadImgUtils.downloadImgByUrl(path, file);
                            if (downloadState){// 如果下载成功
                                Log.e(TAG, "开启了磁盘缓存，下载图片 :" + path + " 到磁盘。磁盘路径： " + file.getAbsolutePath());
                                bitmap = loadImageFromLocal(file.getAbsolutePath(), imageView);
                            }
                        }else {// 直接从网络加载
                            Log.e(TAG, "下载图片 :" + path + " 到内存。");
                            bitmap = DownloadImgUtils.downloadImgByUrl(path, imageView);
                        }
                    }
                }else {//本地图片
                    bitmap = loadImageFromLocal(path, imageView);
                }
                //把图片加入到缓存
                addBitmapToLruCache(path, bitmap);
                refreshBitmap(path, imageView, bitmap);
                mSemaphoreThreadPool.release();
            }
        };
    }

    /**
     * 从本地加载图片
     * @param path
     * @param imageView
     * @return
     */
    private Bitmap loadImageFromLocal(String path, ImageView imageView) {
        Bitmap bm;
        //获得图片需要显示的大小
        ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);
        //压缩图片
        bm = decodeSampledBitmapFromPath(path, imageSize.width, imageSize.height);
        return bm;
    }

    /**
     * 根据图片需要显示的宽和高对图片进行压缩
     * @param path
     * @param width
     * @param height
     * @return
     */
    private Bitmap decodeSampledBitmapFromPath(String path, int width, int height) {
        //获取原始图片的宽高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //设置图片的缩放比
        options.inSampleSize = ImageSizeUtil.calculateInSampleSize(options, width, height);
        //再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 获得缓存图片
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //判断SDCard是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            cachePath = context.getExternalCacheDir().getPath();
        }else {
            //这个是获取内存上的缓存路径
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 利用签名辅助类，将字符串字节数组
     *
     * @param path
     * @return
     */
    private String md5(String path) {
        byte[] digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            digest = md.digest(path.getBytes());
            return bytes2hex02(digest);
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * 方式二
     *
     * @param bytes
     * @return
     */
    private String bytes2hex02(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            // 每个字节8为，转为16进制标志，2个16进制位
            if (tmp.length() == 1) {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }

        return sb.toString();
    }

    /**
     * 通过mUIHandler更新图片
     * @param path
     * @param imageView
     * @param bm
     */
    private void refreshBitmap(String path, ImageView imageView, Bitmap bm) {
        ImgBeanHolder holder = new ImgBeanHolder();
        holder.imageView = imageView;
        holder.bitmap = bm;
        holder.path = path;
        Message message = Message.obtain();
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 根据path在缓存mLruCache中获取bitmap
     * @param path
     * @return
     */
    private Bitmap getBitmapFromLruCache(String path) {
        return mLruCache.get(path);
    }

    /**
     * 将图片加如缓存
     * @param path
     * @param bitmap
     */
    private void addBitmapToLruCache(String path, Bitmap bitmap) {
        if (getBitmapFromLruCache(path) == null){
            if (bitmap != null) {
                mLruCache.put(path, bitmap);
            }
        }
    }
}
