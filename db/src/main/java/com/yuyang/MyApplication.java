package com.yuyang;

import android.app.Application;

/**
 * Created by yuyang on 2017/4/13.
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mInstance = this;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
