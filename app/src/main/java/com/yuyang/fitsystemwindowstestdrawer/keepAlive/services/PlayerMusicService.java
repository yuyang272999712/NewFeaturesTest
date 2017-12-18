package com.yuyang.fitsystemwindowstestdrawer.keepAlive.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 循环播放一段无声音频，以提升进程优先级
 */

public class PlayerMusicService extends Service {
    private final static String TAG = "PlayerMusicService";
    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,TAG+"---->onCreate,启动服务");
        mMediaPlayer = MediaPlayer.create(this, R.raw.silent);
        mMediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startPlayMusic();
            }
        }).start();
        return START_STICKY;
    }

    private void startPlayMusic() {
        if(mMediaPlayer != null){
            Log.d(TAG,"启动后台播放音乐");
            mMediaPlayer.start();
        }
    }

    private void stopPlayMusic() {
        if(mMediaPlayer != null){
            Log.d(TAG,"停止后台播放音乐");
            mMediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayMusic();
        Log.e(TAG,TAG+"---->onCreate,停止服务");
    }

}
