package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.notificationAbout;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Notification 通知
 */
public class NotificationActivity extends AppCompatActivity {
    NotificationManager notificationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 发送普通通知
     * @param view
     */
    public void sendNormalNotification(View view){
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("启动通知时状态栏中显示的文本")
                .setWhen(System.currentTimeMillis())//展开的状态栏按时间顺序排序通知
//                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)//设置为默认的响铃、震动通知方式
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))//自定义通知铃音
                .setVibrate(new long[]{1000,1000,1000,1000,1000})//自定义通知震动方式
                .setLights(Color.RED, 0, 1);//自定义通知LED颜色
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        }else {
            notification = builder.getNotification();
        }
        notificationManager.notify(1,notification);
    }
}
