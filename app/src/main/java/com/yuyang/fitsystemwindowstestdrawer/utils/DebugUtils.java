package com.yuyang.fitsystemwindowstestdrawer.utils;

import android.content.pm.ApplicationInfo;

import com.yuyang.fitsystemwindowstestdrawer.MyApplication;

/**
 * 是否开启Debug模式
 */
public class DebugUtils {
    public static boolean DEBUGABLE = true && isDebugAble();

    public static boolean isDebugAble() {
        ApplicationInfo appInfo = MyApplication.getInstance().getApplicationInfo();
        if (appInfo == null) {
            return false;
        }
        if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            return true;
        } else {
            return false;
        }
    }
}
