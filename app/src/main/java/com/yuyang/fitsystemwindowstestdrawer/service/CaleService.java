package com.yuyang.fitsystemwindowstestdrawer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yuyang.fitsystemwindowstestdrawer.aidl.ICalcAIDL;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 * Created by yuyang on 16/7/5.
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
