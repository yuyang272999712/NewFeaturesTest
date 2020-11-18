package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 自定义的SD卡图片加载器
 */
public class MyImageLoader {
    /**
     * 图片缓存的核心类
     * TODO yuyang LRU缓存机制
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTasks;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    /**
     * 线程池的线程数量，默认为1
     */
    private int mThreadCount = 1;
    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;
    /**
     * 轮询的线程
     */
    private Thread mPoolThread;
    private Handler mPoolThreadHandler;
    /**
     * 运行在UI线程的handler，用于给ImageView设置图片
     */
    private Handler mHandler;
    /**
     * 引入一个值为1的信号量，防止mPoolThreadHandler未初始化完成
     */
    private volatile Semaphore mSemaphore = new Semaphore(1);
    /**
     * 引入一个值为1的信号量，由于线程池内部也有一个阻塞线程，防止加入任务的速度过快，使LIFO效果不明显
     */
    private volatile Semaphore mPoolSemaphore;
    /**
     * 队列的调度方式
     */
    public enum Type{
        FIFO, LIFO
    }

    private static MyImageLoader mInstance;

    public static MyImageLoader getInstance(){
        if (mInstance == null){
            synchronized (MyImageLoader.class){
                if (mInstance == null){
                    mInstance = new MyImageLoader(1, Type.LIFO);
                }
            }
        }
        return mInstance;
    }

    /**
     * 私有构造方法
     * @param threadCount 线程池中线程数量
     * @param type 队列的调度方式
     */
    private MyImageLoader(int threadCount, Type type){
        init(threadCount, type);
    }

    private void init(int threadCount, Type type) {
        //创建轮训线程
        mPoolThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSemaphore.acquire();
                } catch (InterruptedException e) {
                }
                Looper.prepare();
                //运行在子线程中的handler
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mThreadPool.execute(getTask());
                        try {
                            mPoolSemaphore.acquire();
                        } catch (InterruptedException e) {
                        }
                    }
                };
                //释放一个信号量
                mSemaphore.release();
                Looper.loop();
            }
        });
        mPoolThread.start();

        //获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        //创建一个线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mPoolSemaphore = new Semaphore(threadCount);
        mTasks = new LinkedList<>();
        mType = type == null ? Type.LIFO:type;
    }

    /**
     * 加载图片
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView){
        //使用tag做标记，防止图片错位
        imageView.setTag(path);
        //初始化UI线程中的handler
        if (mHandler == null){
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    ImgBeanHolder holder = (ImgBeanHolder) msg.obj;
                    ImageView imageView = holder.imageView;
                    Bitmap bm = holder.bitmap;
                    String path = holder.path;
                    if (imageView.getTag().toString().equals(path)){
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }
        //从LruCache中取图片缓存
        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null){
            ImgBeanHolder holder = new ImgBeanHolder();
            holder.imageView = imageView;
            holder.bitmap = bm;
            holder.path = path;
            Message message = Message.obtain();
            message.obj = holder;
            mHandler.sendMessage(message);
        }else {
            //这个Runnable会跑在子线程上
            addTask(new Runnable() {
                @Override
                public void run() {
                    ImageSize imageSize = getImageViewWidth(imageView);
                    int reqWidth = imageSize.width;
                    int reqHeight = imageSize.height;
                    Bitmap bm = decodeSampledBitmapFromResource(path, reqWidth, reqHeight);
                    addBitmapToLruCache(path, bm);//添加进缓存
                    ImgBeanHolder holder = new ImgBeanHolder();
                    holder.path = path;
                    holder.bitmap = bm;
                    holder.imageView = imageView;
                    Message message = Message.obtain();
                    message.obj = holder;
                    mHandler.sendMessage(message);
                    mPoolSemaphore.release();
                }
            });
        }
    }

    /**
     * 根据计算的inSampleSize，得到压缩后图片
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只获取图片大小
        BitmapFactory.decodeFile(path, options);
        //设置缩放值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 获取图片缩放值
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth && height > reqHeight){
            //计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float)width/(float)reqWidth);
            int heightRatio = Math.round((float)height/(float)reqHeight);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 根据ImageView获得适当的压缩的宽和高
     * @param imageView
     * @return
     */
    private ImageSize getImageViewWidth(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        final DisplayMetrics displayMetrics = imageView.getContext()
                .getResources().getDisplayMetrics();
        final LayoutParams params = imageView.getLayoutParams();

        int width = params.width == LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getWidth(); // Get actual image width
        if (width <= 0)
            width = params.width; // Get layout width parameter
        if (width <= 0)
            width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check
        // maxWidth
        // parameter
        if (width <= 0)
            width = displayMetrics.widthPixels;
        int height = params.height == LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getHeight(); // Get actual image height
        if (height <= 0)
            height = params.height; // Get layout height parameter
        if (height <= 0)
            height = getImageViewFieldValue(imageView, "mMaxHeight"); // Check
        // maxHeight
        // parameter
        if (height <= 0)
            height = displayMetrics.heightPixels;
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    /**
     * 添加一个任务
     * @param runnable
     */
    private synchronized void addTask(Runnable runnable){
        try {
            // 请求信号量，防止mPoolThreadHander为null
            if (mPoolThreadHandler == null){
                mSemaphore.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mTasks.add(runnable);
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * 取出一个任务
     * @return
     */
    private synchronized Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTasks.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTasks.removeLast();
        }
        return null;
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     */
    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    /**
     * 往LruCache中添加一张图片
     *
     * @param key
     * @param bitmap
     */
    private void addBitmapToLruCache(String key, Bitmap bitmap) {
        if (getBitmapFromLruCache(key) == null) {
            if (bitmap != null)
                mLruCache.put(key, bitmap);
        }
    }

    /**
     * 反射获得ImageView设置的最大宽度和高度
     * 为了兼容API16以下的android版本
     * @param object
     * @param fieldName
     * @return
     */
    private int getImageViewFieldValue(ImageView object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
                Log.e("TAG", value + "");
            }
        } catch (Exception e) {
        }
        return value;
    }

    private class ImgBeanHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

    private class ImageSize {
        int width;
        int height;
    }
}
