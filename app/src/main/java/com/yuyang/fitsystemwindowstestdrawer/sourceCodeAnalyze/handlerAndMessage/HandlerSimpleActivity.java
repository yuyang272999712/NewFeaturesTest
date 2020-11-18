package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.lang.ref.WeakReference;

/**
 * Handler使用
 */
public class HandlerSimpleActivity extends Activity {
    private TextView textView;
    private Button button;

    private int mCount = 0;
    //独立线程中的handler
    private Handler mHandlerThread = null;
    //UI线程中的handler
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("--yuyang--", "mHandler--handleMessage--msg.what="+msg.what);
            textView.setText("mCount="+mCount);
            //handler接收到独立线程发送来的信息后，向mHandlerThread发送msg 1
            mHandlerThread.sendEmptyMessage(1);
            mCount++;
            if (mCount >= 30){
                //由于mHandlerThr是在Child Thread创建，Looper手动死循环阻塞，所以需要quit。
                mHandlerThread.getLooper().quit();
            }
        }
    };

    /*static class TestHandler extends Handler {
        WeakReference<Activity > mActivityReference;

        TestHandler(Activity activity) {
            mActivityReference= new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.i("--yuyang--", "mHandler--handleMessage--msg.what="+msg.what);
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                HandlerSimpleActivity handlerActivity = (HandlerSimpleActivity) activity;
                handlerActivity.textView.setText("mCount=" + handlerActivity.mCount);
                //handler接收到独立线程发送来的信息后，向mHandlerThread发送msg 1
                handlerActivity.mHandlerThread.sendEmptyMessage(1);
                handlerActivity.mCount++;
                Log.i("--yuyang--", "mHandler--handleMessage--mCount="+handlerActivity.mCount);
                *//*if (handlerActivity.mCount >= 30) {
                    //由于mHandlerThr是在Child Thread创建，Looper手动死循环阻塞，所以需要quit。
                    handlerActivity.mHandlerThread.getLooper().quit();
                }*//*
            }
        }
    }
    private Handler mHandler = new TestHandler(this);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_simple_use);

        textView = (TextView) findViewById(R.id.handler_text);
        button = (Button) findViewById(R.id.handler_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(null, ">>>>>>>>>>>>>Child# begin start send msg!!!");
                //向mHandlerThread发送msg 1
                mHandlerThread.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startThread();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO yuyang 移除所有消息以免内存泄露，因为mHandler中持有TextView对象
        mHandler.removeCallbacksAndMessages(null);
        mHandlerThread.getLooper().quit();//结束MessageQueue消息队列阻塞死循环,结束线程
    }

    private void startThread() {
        Log.d("--yuyang--", "begin start thread!!!");
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                mHandlerThread = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.i("--yuyang--", "mHandlerThreads--handleMessage--msg.what=" + msg.what);
                        //接收发送到子线程的消息，然后向UI线程中的Handler发送msg 0。
                        mHandler.sendEmptyMessageDelayed(0, 500);
                    }
                };
                //不能在这个后面添加代码，程序是无法运行到这行之后的，除非quit消息队列
                Looper.loop();// TODO yuyang 该方法会进入一个死循环，不断遍历Looper消息队列中的消息
            }
        }.start();
    }
}
