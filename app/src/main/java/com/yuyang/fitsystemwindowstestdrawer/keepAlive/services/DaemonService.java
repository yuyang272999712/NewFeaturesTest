package com.yuyang.fitsystemwindowstestdrawer.keepAlive.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 前台Service，使用startForeground
 * 这个Service尽量要轻，不要占用过多的系统资源，否则
 * 系统在资源紧张时，照样会将其杀死
 */

public class DaemonService extends Service {
    private static final String TAG = "DaemonService";
    public static final int NOTICE_ID = 100;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"DaemonService---->onCreate被调用，启动前台service");
        //如果API大于18，需要弹出一个可见通知
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("保活Service");
            builder.setContentText("保活Service正在运行...");
            startForeground(NOTICE_ID,builder.build());
            // 如果觉得常驻通知栏体验不好
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
            Intent intent = new Intent(this, CancelNoticeService.class);
            startService(intent);
        }else{
            startForeground(NOTICE_ID,new Notification());
        }
    }

    /**
     * 在onStartCommand方法中返回START_STICKY，其作用是当Service进程被kill后，系统会尝试重新创建这个
     * Service，且会保留Service的状态为开始状态，但不保留传递的Intent对象，onStartCommand方法一定会被
     * 重新调用。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 如果Service被终止
        // 当资源允许情况下，重启service
        return START_STICKY;
    }

    /**
     * 在onDestory方法中重新启动自己，也就是说，只要Service在被销毁时走到了onDestory这里我们就重新启动它。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"DaemonService---->onDestroy，前台service被杀死");
        // 如果Service被杀死，干掉通知
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            NotificationManager mManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(NOTICE_ID);
        }
        // 重启自己
        Intent intent = new Intent(getApplicationContext(),DaemonService.class);
        startService(intent);
    }
}
