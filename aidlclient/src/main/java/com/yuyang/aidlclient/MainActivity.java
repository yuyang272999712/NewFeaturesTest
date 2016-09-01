package com.yuyang.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.aidl.ICalcAIDL;

/**
 * IPC通讯－客户端
 * TODO yuyang 对于实现AIDL接口，官方提醒我们：
 *  1. 调用者是不能保证在主线程执行的，所以从一调用的开始就需要考虑多线程处理，以及确保线程安全；
 *  2. IPC调用是同步的。如果你知道一个IPC服务需要超过几毫秒的时间才能完成地话，你应该避免在Activity的主线程中调用。
 *      也就是IPC调用会挂起应用程序导致界面失去响应，这种情况应该考虑单独开启一个线程来处理。
 *  3. 抛出的异常是不能返回给调用者（跨进程抛异常处理是不可取的）。
 */
public class MainActivity extends AppCompatActivity {
    private Button bindBtn;
    private Button unBindBtn;
    private Button addBtn;
    private Button minBtn;

    //TODO yuyang 通过AIDL实现IPC通信
    private ICalcAIDL mCalcAidl;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("aidlClient", "onServiceConnected"+";"+name);
            mCalcAidl = ICalcAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("aidlClient", "onServiceDisconnected"+";"+name);
            mCalcAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setAIDLAction();
    }

    private void setAIDLAction() {
        /**点击BindService按钮时调用*/
        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.yuyang.aidl.calc");
                intent.setComponent(new ComponentName("com.yuyang.fitsystemwindowstestdrawer","com.yuyang.fitsystemwindowstestdrawer.IPC.CaleService"));
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
                if (mCalcAidl != null){
                    try {
                        //TODO yuyang 小心服务端是耗时任务，会造成应用无响应
                        int result = mCalcAidl.add(12, 12);
                        Toast.makeText(MainActivity.this, "结果："+result, Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**点击减法按钮时调用*/
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCalcAidl != null){
                    try {
                        int result = mCalcAidl.min(78, 10);
                        Toast.makeText(MainActivity.this, "结果："+result, Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findViews() {
        bindBtn = (Button) findViewById(R.id.bind_service);
        unBindBtn = (Button) findViewById(R.id.un_bind_service);
        addBtn = (Button) findViewById(R.id.aidl_cale_add);
        minBtn = (Button) findViewById(R.id.aidl_cale_min);
    }

    public void gotoMessengerActivity(View view){
        Intent intent = new Intent(this, UseMessengerActivity.class);
        startActivity(intent);
    }
    public void gotoIBinderActivity(View view){
        Intent intent = new Intent(this, UseIBinderActivity.class);
        startActivity(intent);
    }
    public void gotoBookAidlActivity(View view){
        Intent intent = new Intent(this, BookAidlActivity.class);
        startActivity(intent);
    }
    public void gotoTCPClientActivity(View view){
        Intent intent = new Intent(this, TCPClientActivity.class);
        startActivity(intent);
    }
}
