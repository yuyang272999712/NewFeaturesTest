package com.yuyang.fitsystemwindowstestdrawer.internetAbout.downFileService;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;

import static com.yuyang.fitsystemwindowstestdrawer.internetAbout.downFileService.DownLoadFileUtil.CallBack;

/**
 * 文件下载service
 */
public class DownLoadFileService extends Service {
    public static final String FILE_INFO = "fileInfo";
    public static final int STATUS_IN_PROGRESS = 1;
    public static final int STATUS_COMPLETED = 0;
    public static final int STATUS_ERROR = 2;
    public static final String BROADCAST_ACTION_SUFFIX = "com.yuyang.downloadservice.broadcast.status";
    private static final String ACTION_STOP_DOWN = "com.yuyang.downloadservice.stopdown";
    public static final String STATUS = "status";
    public static final String FILE_ID = "file_id";
    public static final String PROGRESS = "progress";
    public static final String ERROR_EXCEPTION = "error_exception";
    private static final String STOP_ONE_KEY= "stop_one_key";

    ConcurrentHashMap<Integer, Call> CHMap ;//缓存任务

    public static void startDownLoadFile(Context context, FileInfo fileInfo){
        Intent intent = new Intent(context, DownLoadFileService.class);
        intent.putExtra(FILE_INFO, fileInfo);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CHMap = new ConcurrentHashMap<>();
        registerStopReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(FILE_INFO);
        Call call = DownLoadFileUtil.loadFile(fileInfo, new DownLoadCallBack(fileInfo));
        CHMap.put(fileInfo.getFileId(), call);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterStopReceiver();
    }

    /**
     * 构建发送状态更新的广播
     * @param status
     * @param fileId
     * @return
     */
    Intent initDownResultIntent(int status, int fileId){
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION_SUFFIX);
        intent.putExtra(STATUS, status);
        intent.putExtra(FILE_ID, fileId);
        return intent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class DownLoadCallBack implements CallBack{
        private final FileInfo fileInfo;

        public DownLoadCallBack(FileInfo fileInfo){
            this.fileInfo = fileInfo;
        }
        @Override
        public void onError(Call call, IOException e) {
            sendBroadcast(initDownResultIntent(STATUS_ERROR, fileInfo.getFileId()).putExtra(ERROR_EXCEPTION,e));
            CHMap.remove(fileInfo.getFileId());
        }

        @Override
        public void onSuccess(File file) {
            sendBroadcast(initDownResultIntent(STATUS_COMPLETED, fileInfo.getFileId()));
            CHMap.remove(fileInfo.getFileId());
        }

        @Override
        public void inProgress(float current, long total) {
            sendBroadcast(initDownResultIntent(STATUS_IN_PROGRESS, fileInfo.getFileId()).putExtra(PROGRESS, current));
        }
    }

    /**
     * 停止指定任务
     * @param context
     * @param fileId
     */
    public static void stopOneTask(Context context,int fileId){
        Intent intent = new Intent();
        intent.setAction(ACTION_STOP_DOWN);
        intent.putExtra(STOP_ONE_KEY, fileId);
        context.sendBroadcast(intent);
    }

    //停止上传的广播
    BroadcastReceiver stopUploadReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            if(null == intent){return;}
            if(ACTION_STOP_DOWN.equals(intent.getAction())){
                cancelOneTask(intent.getIntExtra(STOP_ONE_KEY, -1));
            }
        }
    };

    private void cancelOneTask(Integer fileId) {
        if(null != CHMap.get(fileId) && !CHMap.get(fileId).isCanceled()){
            CHMap.get(fileId).cancel();
            CHMap.remove(fileId);
        }
    }

    void registerStopReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_STOP_DOWN);
        registerReceiver(stopUploadReceiver, intentFilter);
    }

    void unRegisterStopReceiver(){
        if(null != stopUploadReceiver){
            unregisterReceiver(stopUploadReceiver);
        }
    }
}
