package com.yuyang.aidlclient;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.aidl.ICalcAIDL;

/**
 * IPC通讯－客户端
 */
public class MainActivity extends AppCompatActivity {
    private Button bindBtn;
    private Button unBindBtn;
    private Button addBtn;
    private Button minBtn;

    private Button bindBinderBtn;
    private Button unBindBinderBtn;
    private Button addBinderBtn;
    private Button minBinderBtn;

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
    //TODO yuyang 自定义Binder实现IPC通讯
    private IBinder mPlusBinder;
    private ServiceConnection mServiceConnPlus = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("aidlClient", "onServiceConnected"+";"+name);
            mPlusBinder = service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("aidlClient", "onServiceDisconnected"+";"+name);
            mPlusBinder = null;
        }
    };
    //TODO yuyang 自定义Binder服务的标识
    private static final String DESCRIPTOR = "com.yuyang.CalcPlusService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setAIDLAction();
        setBinderAction();
    }

    private void setBinderAction() {
        bindBinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.yuyang.fitsystemwindowstestdrawer","com.yuyang.fitsystemwindowstestdrawer.service.CalcPlusService"));
                bindService(intent, mServiceConnPlus, Context.BIND_AUTO_CREATE);
            }
        });
        unBindBinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mServiceConnPlus);
            }
        });
        addBinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlusBinder == null){
                    Toast.makeText(MainActivity.this, "未连接服务端或服务端被异常杀死", Toast.LENGTH_SHORT).show();
                }else {
                    Parcel _data = Parcel.obtain();
                    Parcel _reply = Parcel.obtain();
                    int _result;
                    try {
                        _data.writeInterfaceToken(DESCRIPTOR);
                        _data.writeInt(13);
                        _data.writeInt(13);
                        mPlusBinder.transact(0x110, _data, _reply, 0);//TODO 服务端规定code＝0x110为加法操作
                        _reply.readException();
                        _result = _reply.readInt();
                        Toast.makeText(MainActivity.this, "结果："+_result, Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        minBinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlusBinder == null){
                    Toast.makeText(MainActivity.this, "未连接服务端或服务端被异常杀死", Toast.LENGTH_SHORT).show();
                }else {
                    Parcel _data = Parcel.obtain();
                    Parcel _reply = Parcel.obtain();
                    int _result;
                    try {
                        _data.writeInterfaceToken(DESCRIPTOR);
                        _data.writeInt(78);
                        _data.writeInt(20);
                        mPlusBinder.transact(0x111, _data, _reply, 0);//TODO 服务端规定code＝0x111为减法操作
                        _reply.readException();
                        _result = _reply.readInt();
                        Toast.makeText(MainActivity.this, "结果："+_result, Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setAIDLAction() {
        /**点击BindService按钮时调用*/
        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.yuyang.aidl.calc");
                intent.setComponent(new ComponentName("com.yuyang.fitsystemwindowstestdrawer","com.yuyang.fitsystemwindowstestdrawer.service.CaleService"));
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

        bindBinderBtn = (Button) findViewById(R.id.bind_service_binder);
        unBindBinderBtn = (Button) findViewById(R.id.un_bind_service_binder);
        addBinderBtn = (Button) findViewById(R.id.cale_add_binder);
        minBinderBtn = (Button) findViewById(R.id.cale_min_binder);
    }
}
