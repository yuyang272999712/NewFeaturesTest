package com.yuyang.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 直接使用IBinder对象实现跨进程通讯
 */
public class UseIBinderActivity extends AppCompatActivity {
    private Button bindBinderBtn;
    private Button unBindBinderBtn;
    private Button addBinderBtn;
    private Button minBinderBtn;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_ibinder);

        findViews();
        setBinderAction();
    }

    private void setBinderAction() {
        bindBinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.yuyang.fitsystemwindowstestdrawer","com.yuyang.fitsystemwindowstestdrawer.IPC.CalcPlusService"));
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
                    Toast.makeText(UseIBinderActivity.this, "未连接服务端或服务端被异常杀死", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UseIBinderActivity.this, "结果："+_result, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(UseIBinderActivity.this, "未连接服务端或服务端被异常杀死", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UseIBinderActivity.this, "结果："+_result, Toast.LENGTH_LONG).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void findViews() {
        bindBinderBtn = (Button) findViewById(R.id.bind_service_binder);
        unBindBinderBtn = (Button) findViewById(R.id.un_bind_service_binder);
        addBinderBtn = (Button) findViewById(R.id.cale_add_binder);
        minBinderBtn = (Button) findViewById(R.id.cale_min_binder);
    }
}
