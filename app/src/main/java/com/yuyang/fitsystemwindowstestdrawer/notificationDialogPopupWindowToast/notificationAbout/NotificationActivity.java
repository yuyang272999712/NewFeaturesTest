package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.notificationAbout;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.NotificationDialogPopupToastActivity;

/**
 * Notification 通知
 */
public class NotificationActivity extends AppCompatActivity {
    public static final String ACTION = "notification_action";

    NotificationManager notificationManager;
    private int progress = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //注册监听 进度条通知 点击事件
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //取消普通通知
                notificationManager.cancel(0);
            }
        }, new IntentFilter(ACTION));
    }

    /**
     * 发送普通通知
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNormalNotification(View view){
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setTicker("启动通知时状态栏中显示的文本")
                .setContentTitle("通知标题")
                .setSubText("subText")
                .setContentText("通知内容")
                .setContentInfo("通知Info")
                .setWhen(System.currentTimeMillis())//展开的状态栏按时间顺序排序通知
                //.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)//设置为默认的响铃、震动通知方式
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))//自定义通知铃音
                .setVibrate(new long[]{1000,1000,1000,1000,1000})//自定义通知震动方式
                .setLights(Color.RED, 0, 1)//自定义通知LED颜色
                .setAutoCancel(true);//点击自后自动消失 TODO 尼玛不起作用！！！
        Notification notification = builder.build();
        notificationManager.notify(0, notification);
    }

    /**
     * 发送进度条通知，点击启动activity
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendProgressNotification(View view){
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.circle_menu_item_1_normal)
                .setTicker("进度条通知")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("进度条")
                .setProgress(100, progress++, false)
                .setContentIntent(PendingIntent.getBroadcast(this, 0, new Intent().setAction(ACTION), 0))
                //.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, NotificationDialogPopupToastActivity.class), 0))
                .setAutoCancel(true);//点击自后自动消失 TODO yuyang 改通知必须要有Intent才会起作用
        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendMyNotification(View view){
        RemoteViews myView = new RemoteViews(this.getPackageName(), R.layout.notification_layout);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.circle_menu_item_2_normal)
                .setTicker("进度条通知")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("进度条")
                .setProgress(100, progress++, false)
                .setContentIntent(PendingIntent.getBroadcast(this, 0, new Intent().setAction(ACTION), 0))
                .setContent(myView)
                .setAutoCancel(true);
        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }
}
