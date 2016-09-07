package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.IOException;

/**
 * Activity的setVolumeControlStream方法可以使当前屏幕获取音量按键的监听权，从而控制媒体声音
 *
 * 由于设备上可能安装了多个应用，每个应用都被配置为接收媒体按键按下动作，因此
 * 还必须使用AudioManager的registerMediaButtonEventReceiver方法将接收者注册为媒体按键按下动作的唯一处理程序
 *
 * 设备上可能有多个媒体播放器，因此当另一个媒体应用获得焦点时，让你的应用程序暂停播放并交出媒体按键的控制权是很重要的，
 * 为了在开始播放前请求音频焦点，需要使用AudioManager的requestAudioFocus方法请求焦点，并通过回调处理失去焦点后的
 * 相应处理。
 *
 * 当输出由耳机切换至扬声器时，系统会广播一个ACTION_AUDIO_BECOMING_NOISY的Intent，监听该广播以便处理音频的输出
 */
public class AudioPlayerActivity extends AppCompatActivity {
    private Button playBtn;
    private Button bassBoostBtn;

    private ActivityMediaControlReceiver activityMediaControlReceiver;
    private MediaPlayer mediaPlayer;
    /**
     * 音频焦点发生变化时监听回调
     */
    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
            new AudioManager.OnAudioFocusChangeListener(){
                @Override
                public void onAudioFocusChange(int focusChange) {
                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    switch (focusChange){
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            //失去焦点降低音量
                            mediaPlayer.setVolume(0.2f, 0.2f);
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            //暂时失去焦点
                            pause();
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                            //永久失去焦点
                            stop();
                            ComponentName component = new ComponentName(AudioPlayerActivity.this,
                                    MediaControlReceiver.class);
                            //交出媒体按键的控制权
                            audioManager.unregisterMediaButtonEventReceiver(component);
                            break;
                        case AudioManager.AUDIOFOCUS_GAIN:
                            //重新获取焦点，将音量恢复到正常大小，并且如果音频流已被暂停，则恢复音频流
                            mediaPlayer.setVolume(1f,1f);
                            mediaPlayer.start();
                            break;
                    }
                }
            };

    /**
     * 当拔出耳机时暂停音频播放
     */
    private class NoisyAudioStreamReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                pause();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        //获取音量按键的监听权，从而控制媒体声音
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //创建mediaPlayer
        configureAudio();

        playBtn = (Button) findViewById(R.id.buttonPlay);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        bassBoostBtn = (Button) findViewById(R.id.buttonBassBoost);
        bassBoostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bassBoost();
            }
        });
    }

    /**
     * 设置音效
     * BassBoost 增强音频输出的低音音频。使用setStrength方法可以将音效的强度设置为0~1000
     */
    private void bassBoost() {
        int sessionId = mediaPlayer.getAudioSessionId();
        short boostStrength = 500;
        int priority = 0;

        BassBoost bassBoost = new BassBoost(priority, sessionId);
        bassBoost.setStrength(boostStrength);
        bassBoost.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        ComponentName component = new ComponentName(this, MediaControlReceiver.class);
        //将接收者注册为媒体按键按下动作的唯一处理程序
        audioManager.registerMediaButtonEventReceiver(component);

        //注册一个本地接收器，用于接收manifest文件中注册的Receiver媒体按键按下动作
        activityMediaControlReceiver = new ActivityMediaControlReceiver();
        IntentFilter filter = new IntentFilter(MediaControlReceiver.ACTION_MEDIA_BUTTON);
        registerReceiver(activityMediaControlReceiver, filter);

        //注册音频输出设备发生变化的广播
        IntentFilter noiseFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(new NoisyAudioStreamReceiver(), noiseFilter);
    }

    private void configureAudio(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource("/sdcard/media_test_1.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        /**
         * 请求音频焦点，以便使其他正在使用音频的应用停止
         */
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //请求获取音频焦点
        int result = am.requestAudioFocus(focusChangeListener,
                //使用音乐流
                AudioManager.STREAM_MUSIC,
                //请求永久焦点
                AudioManager.AUDIOFOCUS_GAIN);
        //请求音频焦点成功
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        mediaPlayer.stop();
        /**
         * 放弃音频焦点
         * 注：只有在暂时性获取音频焦点时才有必要这么做
         */
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        am.abandonAudioFocus(focusChangeListener);
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void skip() {
    }

    public void previous() {
    }

    public class ActivityMediaControlReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MediaControlReceiver.ACTION_MEDIA_BUTTON.equals(intent.getAction())){
                KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                switch (event.getKeyCode()){
                    case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                        if (mediaPlayer.isPlaying()){
                            pause();
                        }else {
                            play();
                        }
                }
            }
        }
    }
}
