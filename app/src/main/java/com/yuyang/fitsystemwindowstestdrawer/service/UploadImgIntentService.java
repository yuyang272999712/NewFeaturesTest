package com.yuyang.fitsystemwindowstestdrawer.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 *  IntentService是一个基于Service的一个类，用来处理异步的请求。你可以通过startService(Intent)来提交请求，
 * 该Service会在需要的时候创建，当完成所有的任务以后自己关闭，且请求是在工作线程处理的。
 *
 * TODO yuyang IntentService源码解析：
 *      IntentService在onCreate里面初始化了一个HandlerThread
 *      每次调用onStartCommand的时候，通过mServiceHandler发送一个消息，消息中包含我们的intent。
 *  然后在该mServiceHandler的handleMessage中去回调onHandleIntent(intent);就可以了。
 *      onStartCommand中回调了onStart，onStart中通过mServiceHandler发送消息到该handler的handleMessage中去。
 *  最后handleMessage中回调onHandleIntent(intent)。
 *      注意下：回调完成后回调用 stopSelf(msg.arg1)，注意这个msg.arg1是个int值，相当于一个请求的唯一标识。
 *  每发送一个请求，会生成一个唯一的标识，然后将请求放入队列，当全部执行完成(最后一个请求也就相当于
 *  getLastStartId == startId)，或者当前发送的标识是最近发出的那一个（getLastStartId == startId），
 *  则会销毁我们的Service.如果传入的是-1则直接销毁。
 *      那么，当任务完成销毁Service回调onDestroy，可以看到在onDestroy中释放了我们的Looper:mServiceLooper.quit()。
 */
public class UploadImgIntentService extends IntentService{
    private String TAG = "UploadImgIntentService";
    public static final String ACTION_UPLOAD_IMG = "com.yuyang.service.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH = "com.yuyang.service.extra.IMG_PATH";

    public static void startUploadImg(Context context, String path){
        Intent intent = new Intent(context, UploadImgIntentService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *  name Used to name the worker thread, important only for debugging.
     */
    public UploadImgIntentService() {
        super("UploadImgIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //TODO yuyang 这里可以执行耗时任务
        if (intent != null){
            if (ACTION_UPLOAD_IMG.equals(intent.getAction())){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String path = intent.getStringExtra(EXTRA_IMG_PATH);
                Intent intentBroadcast = new Intent(UploadImgActivity.UPLOAD_RESULT);
                intentBroadcast.putExtra(EXTRA_IMG_PATH,path);
                sendBroadcast(intentBroadcast);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LogUtils.e(TAG, "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy");
    }
}
