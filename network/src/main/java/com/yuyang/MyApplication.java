package com.yuyang;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.yuyang.network.BuildConfig;
import com.yuyang.network.volley.baseView.AppConfig;
import com.yuyang.network.volley.baseView.ServiceConfig;

/**
 * Created by yuyang on 2017/4/10.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initServiceIP();
        initAppStatus();
    }

    private void initAppStatus() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            AppConfig.setVersionCode(pi.versionCode);
            AppConfig.setVersionName(pi.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initServiceIP() {
        ServiceConfig.initIp(BuildConfig.IP, BuildConfig.PORT);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
