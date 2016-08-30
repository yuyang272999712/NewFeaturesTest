package com.yuyang.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 使用Messenger实现IPC
 */
public class UseMessengerActivity extends AppCompatActivity {
    private String TAG = "IPC_Messenger_Client";
    private static final int MSG_SUM = 0x110;
    private static final int MSG_MIN = 0x111;

    private Button bindBtn;
    private Button unBindBtn;
    private Button addBtn;
    private Button minBtn;

    private Messenger mMessengerService;
    private boolean isConn;
    private Messenger mMessengerClient = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msgFromServer) {
            int result = 0;
            switch (msgFromServer.what){
                case MSG_SUM:
                    result = msgFromServer.arg1;
                    Toast.makeText(UseMessengerActivity.this, "相加结果："+result, Toast.LENGTH_LONG).show();
                    break;
                case MSG_MIN:
                    result = msgFromServer.arg1;
                    Toast.makeText(UseMessengerActivity.this, "相减结果："+result, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    });
    //与服务端建立连接
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessengerService = new Messenger(service);
            isConn = true;
            Log.e(TAG, "onServiceDisconnected"+";"+name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessengerService = null;
            isConn = false;
            Log.e(TAG, "onServiceDisconnected"+";"+name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_messenger);

        findViews();
        setAIDLAction();
    }

    private void setAIDLAction() {
        /**点击BindService按钮时调用*/
        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //TODO yuyang 当设置了Service的android:exported="true"后就可以直接使用action启动远程service了
                intent.setComponent(new ComponentName("com.yuyang.fitsystemwindowstestdrawer","com.yuyang.fitsystemwindowstestdrawer.IPC.CaleMessengerService"));
                bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
            }
        });
        /**点击unBindService按钮时调用*/
        unBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mServiceConn);
            }
        });
        /**点击加法按钮时调用*/
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConn){
                    //TODO yuyang 使用Messenger不用担心服务端是否是耗时任务，因为客户端同样使用Messenger来处理返回结果
                    Message msgFormClient = Message.obtain(null, MSG_SUM, 10, 10);
                    msgFormClient.replyTo = mMessengerClient;
                    try {
                        //往服务端发送消息
                        mMessengerService.send(msgFormClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        /**点击减法按钮时调用*/
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConn){
                    Message msgFormClient = Message.obtain(null, MSG_MIN, 40, 20);
                    msgFormClient.replyTo = mMessengerClient;
                    try {
                        //往服务端发送消息
                        mMessengerService.send(msgFormClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void findViews() {
        bindBtn = (Button) findViewById(R.id.bind_service_messenger);
        unBindBtn = (Button) findViewById(R.id.un_bind_service_messenger);
        addBtn = (Button) findViewById(R.id.cale_add_messenger);
        minBtn = (Button) findViewById(R.id.cale_min_messenger);
    }
}
