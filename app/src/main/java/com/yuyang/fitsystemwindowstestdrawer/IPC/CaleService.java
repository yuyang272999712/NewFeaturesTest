package com.yuyang.fitsystemwindowstestdrawer.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yuyang.fitsystemwindowstestdrawer.aidl.ICalcAIDL;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 * TODO yuyang 通过AIDL实现IPC通信-服务端
 * android的AIDL实际上就是对Binder的封装，使用.aidl文件自动生成了服务端Binder类Sub，和客户端Proxy实现类
 *  Service的作用其实就是为我们创建Binder驱动，即服务端与客户端连接的桥梁。
 */
public class CaleService extends Service{
    private static final String TAG = "CaleService";

    private final ICalcAIDL.Stub mBinder = new ICalcAIDL.Stub(){

        @Override
        public int add(int x, int y) throws RemoteException {
            return x+y;
        }

        @Override
        public int min(int x, int y) throws RemoteException {
            try {
                //TODO 耗时操作，应该起个新线程，这里犯懒了……Y(^_^)Y
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return x-y;
        }
    };

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
}
