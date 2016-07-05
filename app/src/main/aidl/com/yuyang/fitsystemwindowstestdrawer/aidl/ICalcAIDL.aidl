package com.yuyang.fitsystemwindowstestdrawer.aidl;

//TODO yuyang 通过AIDL实现IPC通信
interface ICalcAIDL {
    int add(int x , int y);
    int min(int x , int y);
}
