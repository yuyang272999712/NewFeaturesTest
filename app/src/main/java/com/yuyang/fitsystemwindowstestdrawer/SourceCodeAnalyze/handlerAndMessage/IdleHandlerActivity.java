package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * MessageQueue.IdleHandler使用
 *  IdleHandler会在当前Looper中所有message都执行完之后被执行
 *
    Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
        //ZHU yuyang 如果这个方法返回true，则此方法会在每次Looper执行完所有message后被调用
        @Override
        public boolean queueIdle() {
            return false;
        }
    });
 */

public class IdleHandlerActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    private ViewGroup contentView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_simple_use);

        textView = (TextView) findViewById(R.id.handler_text);
        button = (Button) findViewById(R.id.handler_button);
        contentView = (ViewGroup) findViewById(R.id.content_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(null, ">>>>>>>>>>>>>Child# begin start send msg!!!");
                //向mHandlerThread发送msg 1
                mHandlerThread.sendEmptyMessage(1);
            }
        });

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Toast.makeText(IdleHandlerActivity.this, "queueIdle方法被调用,button高度："+button.getHeight(), Toast.LENGTH_SHORT).show();
                return false;
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
                Looper.loop();
            }
        }.start();
    }
}
