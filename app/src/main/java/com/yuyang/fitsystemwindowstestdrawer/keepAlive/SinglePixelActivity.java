package com.yuyang.fitsystemwindowstestdrawer.keepAlive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yuyang.fitsystemwindowstestdrawer.utils.SystemUtils;

/**
 * 1像素Activity
 */

public class SinglePixelActivity extends Activity {
    private static final String TAG = "SinglePixelActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate--->启动1像素保活");

        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT|Gravity.TOP);
        WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = 1;
        layoutParams.height = 1;
        mWindow.setAttributes(layoutParams);
        // 绑定SinglePixelActivity到ScreenManager
        ScreenManager.getScreenManagerInstance(this).setActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy--->1像素保活被终止");
        if(!SystemUtils.isAPPALive(this,SystemUtils.getCurrentProcessName(this))){
            Intent intentAlive = new Intent(this, SportsActivity.class);
            intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentAlive);
            Log.i(TAG,"SinglePixelActivity---->APP被干掉了，我要重启它");
        }
    }
}
