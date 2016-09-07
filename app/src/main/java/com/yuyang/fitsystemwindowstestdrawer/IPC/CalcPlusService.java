package com.yuyang.fitsystemwindowstestdrawer.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 * TODO yuyang 不使用AIDL，直接编写自己的Binder实现IPC通讯
 *  Service的作用其实就是为我们创建Binder驱动，即服务端与客户端连接的桥梁。
 */
public class CalcPlusService extends Service {
    private static final String TAG = "CalcPlusService";
    //TODO yuyang 自定义Binder服务的标识
    private static final String DESCRIPTOR = "com.yuyang.CalcPlusService";
    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        LogUtils.e(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.e(TAG, "onRebind");
    }

    //TODO yuyang 自己编写Binder类
    private class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code){//ZHU yuyang code需要客户端与服务端事先约定好
                case 0x110: {
                    data.enforceInterface(DESCRIPTOR);//ZHU yuyang DESCRIPTOR也需要双方约定好
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1;
                    _arg1 = data.readInt();
                    int _result = _arg0 + _arg1;
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case 0x111: {
                    data.enforceInterface(DESCRIPTOR);//ZHU yuyang DESCRIPTOR也需要双方约定好
                    int _arg0;
                    _arg0 = data.readInt();
                    int _arg1;
                    _arg1 = data.readInt();
                    int _result = _arg0 - _arg1;
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
    }
}
