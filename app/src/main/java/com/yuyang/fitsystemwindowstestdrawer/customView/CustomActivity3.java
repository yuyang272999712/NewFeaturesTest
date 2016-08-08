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

/**
 * CustomStateView
 *  自定义控件
 */
public class CustomActivity3 extends AppCompatActivity {
    private static final int MSG_DATA_CHANGE = 0x11;
    private CustomLineView lineView;

    private Handler mHandler;

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
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mX += 30;
                }
            }
        }.start();
    }
}
