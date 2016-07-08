package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 一、HandlerThread使用实例
 *    HandlerThread主要实现了新线程以及线程中Looper的初始化。
 *      其实HandlerThread就是Thread、Looper和Handler的组合实现，Android系统这么封装体现了Android系统组件的思想，
 *  同时也方便了开发者开发。
 *      通过源码可以看到，HandlerThread主要是对Looper进行初始化，并提供一个Looper对象给新创建的Handler对象，
 *  使得Handler处理消息事件在子线程中处理。这样就发挥了Handler的优势，同时又可以很好的和线程结合到一起。
 */
public class HandlerTreadSimpleActivity extends Activity {
    int mCount = 0;
    TextView textView;
    private Button button;
    private HandlerThread handlerThread;
    private Handler threadHandler;//子线程handler
    private Handler mainHandler;//UI主线程handler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_simple_use);
        textView = (TextView) findViewById(R.id.handler_text);
        button = (Button) findViewById(R.id.handler_button);

        mainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.i("--yuyang--", "在主线程中处理！--thread.id="+Thread.currentThread().getId());
                textView.setText("mCount="+mCount);
                //handler接收到独立线程发送来的信息后，向mHandlerThread发送msg 1
                threadHandler.sendEmptyMessage(1);
                mCount++;
                if (mCount >= 30){
                    //TODO yuyang 结束HandlerThread
                    handlerThread.quit();
                }
            }
        };

        handlerThread = new HandlerThread("yuyang"){
            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();
                //TODO yuyang 可以在这里做一些耗时操作
            }
        };
        handlerThread.start();
        //为Handler指定Looper对象为子线程的Looper
        threadHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.i("--yuyang--", "在子线程中处理！--thread.id="+Thread.currentThread().getId());
                //接收发送到子线程的消息，然后向UI线程中的Handler发送msg 0。
                mainHandler.sendEmptyMessageDelayed(0, 500);
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(null, ">>>>>>>>>>>>>Child# begin start send msg!!!");
                //向mHandlerThread发送msg 1
                threadHandler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainHandler.removeCallbacksAndMessages(null);
        handlerThread.quit();
    }
}
