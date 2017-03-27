package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * CustomStateView
 *  自定义控件
 */
public class CustomActivity3 extends AppCompatActivity {
    private static final int MSG_DATA_CHANGE = 0x11;
    private CustomLineView lineView;
    private CustomAreaChartsView areaChartsView;

    private static Handler mHandler;
    private Timer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_3);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_DATA_CHANGE:
                        lineView.setLinePoint(msg.arg1, msg.arg2);
                        break;
                }
            }
        };

        lineView = (CustomLineView) findViewById(R.id.custom_line_view);
        areaChartsView = (CustomAreaChartsView) findViewById(R.id.custom_area_charts_view);

        new Thread(){
            int mX = 0;
            @Override
            public void run() {
                for (int i=0; i<20; i++){
                    Message message = new Message();
                    message.what = MSG_DATA_CHANGE;
                    message.arg1 = mX;
                    message.arg2 = (int)(Math.random()*200);
                    mHandler.sendMessage(message);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mX += 30;
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                int x = random.nextInt(120) % (120 + 1) + 0;
                Random randomy = new Random();
                int y = randomy.nextInt(200) % (200 + 1) + 0;
                //随机模拟赋值
                areaChartsView.updateValues(x, y);
            }
        }, 0, 1000);
    }
}
