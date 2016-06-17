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
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Notification 通知
 */
public class NotificationActivity extends AppCompatActivity {
    public static final String ACTION = "notification_action";//普通广播
    public static final String MY_ACTION = "click_notification_icon";//点击了通知特定控件的广播
    public static final int NOTIFICATION1 = 1;
    public static final int NOTIFICATION2 = 2;
    public static final int NOTIFICATION3 = 3;

    NotificationManager notificationManager;
    private int progress = 20;

    private Notification myNotification;//自定义样式的通知

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //TODO yuyang 获取通知管理类
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //注册监听 进度条通知 点击事件
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //取消普通通知
                notificationManager.cancel(NOTIFICATION1);
                Toast.makeText(context, "点击了通知", Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter(ACTION));
        //注册监听 自定义通知组件点击事件
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //取消普通通知
                Toast.makeText(context, "点击了通知的图标", Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter(MY_ACTION));
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
                .setAutoCancel(true)//点击自后自动消失 TODO 尼玛不起作用！！！
                .setOngoing(true);//用户不能取消，“正在进行的”通知使用户了解正在运行的后台进程。例如，音乐播放器可以显示正在播放的音乐。
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION1, notification);
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
        notificationManager.notify(NOTIFICATION2, notification);
    }

    /**
     * 发送自动以样式的通知
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendMyNotification(View view){
        //TODO yuyang 未通知添加自定义样式
        RemoteViews myView = new RemoteViews(this.getPackageName(), R.layout.layout_custom_notification);

        //TODO yuyang 为通知的局部控件添加监听事件（音乐播放的暂停，下一曲等）
        Intent newIntent = new Intent(MY_ACTION);
        PendingIntent myPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION3, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        myView.setOnClickPendingIntent(R.id.notification_icon, myPendingIntent);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.circle_menu_item_2_normal)
                .setTicker("进度条通知")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)
                .setContentIntent(PendingIntent.getBroadcast(this, 0, new Intent().setAction(ACTION), 0))
                .setContent(myView)
                .setAutoCancel(true);
        myNotification = builder.build();

        notificationManager.notify(NOTIFICATION3, myNotification);
    }

    /**
     * 更新自定义通知
     * @param view
     */
    public void updateMyNotification(View view){
        myNotification.contentView.setImageViewResource(R.id.notification_icon, R.mipmap.f);
        myNotification.contentView.setTextViewText(R.id.notification_title, "更新标题");
        myNotification.contentView.setProgressBar(R.id.notification_progress, 100, 50, false);
        notificationManager.notify(NOTIFICATION3, myNotification);
    }
}
