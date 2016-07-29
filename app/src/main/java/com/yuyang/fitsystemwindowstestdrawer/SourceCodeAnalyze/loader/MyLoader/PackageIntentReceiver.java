package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader.MyLoader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 安装／卸载App广播的监听
 */
public class PackageIntentReceiver extends BroadcastReceiver {
    final AppListLoader mLoader;

    public PackageIntentReceiver(AppListLoader loader) {
        mLoader = loader;
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        mLoader.getContext().registerReceiver(this, filter);
        // Register for events related to sdcard installation.
        IntentFilter sdFilter = new IntentFilter();
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        sdFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        mLoader.getContext().registerReceiver(this, sdFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //监听到应用变化，调用loader的方法通知其重新查询数据
        mLoader.onContentChanged();
    }
}
