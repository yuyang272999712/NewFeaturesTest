package com.yuyang.fitsystemwindowstestdrawer.IPC.binderpool.pool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yuyang.fitsystemwindowstestdrawer.IPC.binderpool.ComputeImpl;
import com.yuyang.fitsystemwindowstestdrawer.IPC.binderpool.SecurityCenterImpl;

/**
 * 服务端只提供这么一个service，实现Binder连接池
 */
public class BinderPoolService extends Service {
    private static final String TAG = "BinderPoolService";

    public static class BinderPoolImpl extends IBinderPool.Stub{
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode){
                case BinderPool.BINDER_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                case BinderPool.BINDER_COMPUT:
                    binder = new ComputeImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }

    private Binder mBinderPool = new BinderPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "有客户端请求连接");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
}
