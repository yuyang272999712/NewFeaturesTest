package com.yuyang.fitsystemwindowstestdrawer.IPC.binderpool;

import android.os.RemoteException;

/**
 * 计算两个数的和的远程通讯
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a+b;
    }
}
