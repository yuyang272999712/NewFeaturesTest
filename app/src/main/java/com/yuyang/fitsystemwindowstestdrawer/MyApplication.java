package com.yuyang.fitsystemwindowstestdrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.leakcanary.LeakCanary;
import com.yuyang.fitsystemwindowstestdrawer.utils.SPUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.SystemUtils;

/**
 * Created by yuyang on 16/3/1.
 */
public class MyApplication extends MultiDexApplication {
    //ZHU yuyang MultiDex初次启动APP优化
    //这个标记应当随着版本升级而重置，用于读取SharedPreferences中的全局的标记，判断是否初次启动APP
    public static final String KEY_DEX2_SHA1 = "dex2-SHA1-Digest";
    //同步等待锁
    private static final Object lock = new Object();

    private static MyApplication sInstance;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        CrashHandler.getInstance().start(this);

        Fresco.initialize(this, createFrescoConfig());

        LeakCanary.install(this);
    }

    private ImagePipelineConfig createFrescoConfig() {
        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(getExternalCacheDir())
                .setBaseDirectoryName("fresco cache")
                .setMaxCacheSize(100 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(10 * 1024 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * 1024 * 1024)
                .setVersion(1)
                .build();
        return ImagePipelineConfig.newBuilder(this)
//                .setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
//                .setCacheKeyFactory(cacheKeyFactory)
//                .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//                .setExecutorSupplier(executorSupplier)
//                .setImageCacheStatsTracker(imageCacheStatsTracker)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
//                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
//                .setNetworkFetchProducer(networkFetchProducer)
//                .setPoolFactory(poolFactory)
//                .setProgressiveJpegConfig(progressiveJpegConfig)
//                .setRequestListeners(requestListeners)
//                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //ZHU yuyang MultiDex初次启动APP优化
        //如果异步加载进程没有在执行，并且系统版本小于5.0，则执行MultiDex加载过程
        if (!isAsyncLaunchProcess() && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Log.e("BaseApplication", "dexopt finished. alloc MultiDex.install()");
            //是否需要异步加载，如果是第一启动APP就需要异步加载classes2.dex
            if (needWait()){
                /*第一次启动APP由于MultiDex将会非常缓慢，某些低端机可能ANR。
                  因此这里的做法是挂起主进程，开启:async_launch进程执行dexopt。
                  dexopt执行完毕，主进程重新变为前台进程，继续执行初始化。
                  主进程在这过程中变成后台进程，因此阻塞将不会引起ANR。*/
                DexInstallDeamonThread mThread = new DexInstallDeamonThread(this, this);
                mThread.start();
                //阻塞等待:async_launch完成加载
                synchronized (lock){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                mThread.exit();
            }else {
                MultiDex.install(this);
            }
        }
    }

    /**
     * ZHU yuyang MultiDex初次启动APP优化
     * 是否是第一次启动APP
     * @return
     */
    private boolean needWait() {
        boolean needWait = (boolean) SPUtils.get(this, KEY_DEX2_SHA1, false);
        return needWait;
    }

    /**
     * ZHU yuyang MultiDex初次启动APP优化
     * 判断当前的dex加载进程是否正在运行
     * @return
     */
    public boolean isAsyncLaunchProcess() {
        String processName = SystemUtils.getCurrentProcessName(this);
        return processName != null && processName.contains(":async_launch");
    }

    /**
     * ZHU yuyang MultiDex初次启动APP优化
     * 启动异步加载进程的线程，同时负责异步进程的回调，唤醒主线程
     */
    private static class DexInstallDeamonThread extends Thread {
        private Handler handler;
        private Context application;
        private Context base;
        private Looper looper;

        public DexInstallDeamonThread(Context application, Context base) {
            this.application = application;
            this.base = base;
        }

        @Override
        public void run() {
            Looper.prepare();
            looper = Looper.myLooper();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    synchronized (lock) {
                        lock.notify();
                    }
                    SPUtils.put(application, KEY_DEX2_SHA1, true);
                }
            };

            Messenger messenger = new Messenger(handler);
            Intent intent = new Intent(base, LoadResActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("MESSENGER", messenger);
            base.startActivity(intent);
            Looper.loop();
        }

        public void exit() {
            if (looper != null) looper.quit();
        }
    }
}
