package com.yuyang.fitsystemwindowstestdrawer.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

public class SystemUtils {
    /**
     * 获取当前进程名。
     */
    public static String getCurrentProcessName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }
}
