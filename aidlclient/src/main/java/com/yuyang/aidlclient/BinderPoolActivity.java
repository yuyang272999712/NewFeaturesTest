package com.yuyang.aidlclient;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.IPC.binderpool.ICompute;
import com.yuyang.fitsystemwindowstestdrawer.IPC.binderpool.ISecurityCenter;

/**
 * 服务端使用Binder连接池技术
 */
public class BinderPoolActivity extends AppCompatActivity {
    private static final String TAG = "BinderPoolActivity";

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;
    private BinderPool mBinderPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
    }

    public void getBinderPool(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBinderPool = BinderPool.getInstance(BinderPoolActivity.this);
            }
        }).start();
    }

    //服务端的Binder运行在Binder线程池中，可能是耗时的，这里应该使用异步任务
    public void getSecurityCenter(View view){
        IBinder binder = mBinderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = ISecurityCenter.Stub.asInterface(binder);
        Log.d(TAG, "加密通讯");
        String msg = "helloworld-安卓";
        Log.d(TAG, "加密信息:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            Log.d(TAG, "加密后:" + password);
            Log.d(TAG, "解密后:" + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getCompute(View view){
        IBinder binder = mBinderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ICompute.Stub.asInterface(binder);
        Log.d(TAG, "求和通讯");
        try {
            Log.d(TAG, "3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
