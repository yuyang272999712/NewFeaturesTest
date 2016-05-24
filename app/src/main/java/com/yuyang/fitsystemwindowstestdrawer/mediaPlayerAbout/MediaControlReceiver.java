package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 设备带有的播放、停止、暂停、下一首、前一首等媒体播放按键，在按下时系统会广播一个带有
 * ACTION_MEDIA_BUTTON动作的Intent，为接收次广播必须在manifest文件中申明一个监听器
 *
 * 媒体按键广播接收器(例如：耳机上的暂停按钮)
 * 接收到按键广播后将按键内容同样以广播的形式发送出去（这里被AudioPlayerActivity注册的广播接收器所接受）
 */
public class MediaControlReceiver extends BroadcastReceiver {
    public static final String ACTION_MEDIA_BUTTON = "com.yuyang.ACTION_MEDIA_BUTTON";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())){
            Intent internalIntent = new Intent(ACTION_MEDIA_BUTTON);
            internalIntent.putExtras(intent.getExtras());
            context.sendBroadcast(internalIntent);
        }
    }
}
