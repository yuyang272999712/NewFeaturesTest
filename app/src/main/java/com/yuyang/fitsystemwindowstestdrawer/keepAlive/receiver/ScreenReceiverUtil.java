package com.yuyang.fitsystemwindowstestdrawer.keepAlive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * 静态监听锁屏、解锁、开屏广播
 *  a) 当用户锁屏时，将SportsActivity置于前台，同时开启1像素悬浮窗；
 *  b) 当用户解锁时，关闭1像素悬浮窗；
 *
 * 由于静态注册广播接收器，无法接收到系统的锁屏(Intent.ACTION_SCREEN_OFF)和开屏(Intent.ACTION_SCREEN_ON)广播，因此必须通过动态注册来监听。
 */

public class ScreenReceiverUtil {
    private Context mContext;
    // 锁屏广播接收器
    private ScreenBroadcastReceiver mScreenReceiver;
    // 屏幕状态改变回调接口
    private ScreenStateListener mStateListener;

    public ScreenReceiverUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void setScreenReceiverListener(ScreenStateListener stateListener){
        this.mStateListener = stateListener;
        // 动态启动广播接收器
        this.mScreenReceiver = new ScreenBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mScreenReceiver, filter);
    }

    public void stopScreenReceiverListener(){
        mContext.unregisterReceiver(mScreenReceiver);
    }

    public class ScreenBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("KeepAppAlive","ScreenLockReceiver-->监听到系统广播："+action);
            if (mStateListener == null){
                return;
            }
            if(Intent.ACTION_SCREEN_ON.equals(action)){         // 开屏
                mStateListener.onScreenOn();
            }else if(Intent.ACTION_SCREEN_OFF.equals(action)){  // 锁屏
                mStateListener.onScreenOff();
            }else if(Intent.ACTION_USER_PRESENT.equals(action)){ // 解锁
                mStateListener.onUserPresent();
            }
        }
    }

    // 监听screen状态对外回调接口
    public interface ScreenStateListener {
        void onScreenOn();
        void onScreenOff();
        void onUserPresent();
    }
}
