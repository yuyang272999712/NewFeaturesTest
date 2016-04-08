package com.yuyang.fitsystemwindowstestdrawer.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yuyang.fitsystemwindowstestdrawer.service.BackgroundService;

/**
 * Created by yuyang on 16/3/4.
 */
public class PresentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("yuyang+++++", intent.getAction()+"广播");
        context.startService(new Intent(context, BackgroundService.class));
    }
}
