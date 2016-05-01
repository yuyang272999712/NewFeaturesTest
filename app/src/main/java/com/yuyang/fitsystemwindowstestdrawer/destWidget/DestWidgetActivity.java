package com.yuyang.fitsystemwindowstestdrawer.destWidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 2016/4/28.
 * 更新桌面小控件
 * ALarm:
 * AlarmManager三个方法：
 *  1、set(int type,long startTime,PendingIntent pi)；
 *      第一个参数int type指定定时服务的类型，该参数接受如下值：
 *          ELAPSED_REALTIME： 在指定的延时过后，发送广播，但不唤醒设备（闹钟在睡眠状态下不可用）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒。
 *          ELAPSED_REALTIME_WAKEUP： 在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件） 。
 *              延时是要把系统启动的时间SystemClock.elapsedRealtime()算进去的，具体用法看代码。
 *          RTC： 指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，但不唤醒设备）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒（闹钟在睡眠状态下不可用）。
 *          RTC_WAKEUP： 指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，并唤醒设备）。即使系统关机也会执行 operation所对应的组件。
 *      第二个参数表示闹钟执行时间。（与第一个参数配合使用，如果第一个参数是前两个通常使用SystemClock.elapsedRealtime()，如果是后两个值通常使用System.currentTimeMillis()）
 *      第三个参数PendingIntent pi表示闹钟响应动作，比如发送一个广播、给出提示等等
 *  2、setRepeating(int type,long startTime,long intervalTime,PendingIntent pi)
 *      第三个参数表示闹钟两次执行的间隔时间，
 *  3、setInexactRepeating(int type,long startTime,long intervalTime,PendingIntent pi)
 *      第三个参数intervalTime为闹钟间隔，内置的几个变量如下：
 *          INTERVAL_DAY：      设置闹钟，间隔一天
 *          INTERVAL_HALF_DAY：  设置闹钟，间隔半天
 *          INTERVAL_FIFTEEN_MINUTES：设置闹钟，间隔15分钟
 *          INTERVAL_HALF_HOUR：     设置闹钟，间隔半个小时
 *          INTERVAL_HOUR：  设置闹钟，间隔一个小时
 */
public class DestWidgetActivity extends AppCompatActivity {
    Button sendBroadCast;
    Button beginAlarm;
    Button stopAlarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest_widget);

        sendBroadCast = (Button) findViewById(R.id.dest_send_broadcast);
        beginAlarm = (Button) findViewById(R.id.dest_begin_alarm);
        stopAlarm = (Button) findViewById(R.id.dest_stop_alarm);

        sendBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestWidget.TIME_CHANGE);
                sendBroadcast(intent);
            }
        });

        beginAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginAlarm();
            }
        });

        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });
    }

    /**
     * 关闭定时器
     */
    private void stopAlarm() {
        Intent intent = new Intent(DestWidget.TIME_CHANGE);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent,0);

        AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarm.cancel(pi);
    }

    /**
     * 开启一个定时器
     */
    private void beginAlarm() {
        Intent intent = new Intent(DestWidget.TIME_CHANGE);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,0,intent,0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC,
                System.currentTimeMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                pIntent);
    }
}
