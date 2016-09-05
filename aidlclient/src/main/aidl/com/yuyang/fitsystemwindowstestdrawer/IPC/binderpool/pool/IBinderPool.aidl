// IBinderPool.aidl
package com.yuyang.fitsystemwindowstestdrawer.IPC.binderpool.pool;

// Declare any non-default types here with import statements

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
