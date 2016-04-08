package com.yuyang.fitsystemwindowstestdrawer;

import android.app.Application;

/**
 * Created by yuyang on 16/3/1.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
