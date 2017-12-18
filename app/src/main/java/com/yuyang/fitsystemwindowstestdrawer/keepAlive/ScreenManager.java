package com.yuyang.fitsystemwindowstestdrawer.keepAlive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * 1像素Activity管理类
 */

public class ScreenManager {
    private static final String TAG = "ScreenManager";
    private Context mContext;
    private static ScreenManager mSreenManager;
    // 使用弱引用，防止内存泄漏
    private WeakReference<Activity> mActivityRef;

    private ScreenManager(Context context){
        this.mContext = context;
    }

    public static ScreenManager getScreenManagerInstance(Context context){
        if (mSreenManager == null){
            mSreenManager = new ScreenManager(context);
        }
        return mSreenManager;
    }

    public void setActivity(Activity mActivity){
        mActivityRef = new WeakReference<>(mActivity);
    }

    public void startActivity(){
        Log.e(TAG,"准备启动SinglePixelActivity...");
        Intent intent = new Intent(mContext,SinglePixelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void finishActivity(){
        if (mActivityRef != null){
            Activity mActivity = mActivityRef.get();
            if(mActivity != null){
                mActivity.finish();
            }
        }
    }
}
