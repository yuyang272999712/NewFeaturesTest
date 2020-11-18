package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader.MyLoader;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * 手机配置发生变化
 */
public class InterestingConfigChanges {
    final Configuration mLastConfiguration = new Configuration();
    int mLastDensity;//最近一次的像素密度（按理说这东西在一个手机上应该是不会变化的）

    boolean applyNewConfig(Resources res) {
        int configChanges = mLastConfiguration.updateFrom(res.getConfiguration());
        boolean densityChanged = mLastDensity != res.getDisplayMetrics().densityDpi;
        if (densityChanged || (configChanges&(ActivityInfo.CONFIG_LOCALE
                |ActivityInfo.CONFIG_UI_MODE|ActivityInfo.CONFIG_SCREEN_LAYOUT)) != 0) {
            mLastDensity = res.getDisplayMetrics().densityDpi;
            return true;
        }
        return false;
    }
}
