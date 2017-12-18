package com.yuyang.fitsystemwindowstestdrawer.keepAlive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.keepAlive.receiver.ScreenReceiverUtil;
import com.yuyang.fitsystemwindowstestdrawer.keepAlive.services.DaemonService;
import com.yuyang.fitsystemwindowstestdrawer.keepAlive.services.PlayerMusicService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 保活界面，启动监听锁屏广播，判断是否开关1像素界面
 */

public class SportsActivity extends AppCompatActivity {
    private static final String TAG = "SportsActivity";
    private Toolbar mToolBar;
    private TextView mTvRunTime;
    private Button mBtnRun;

    private int timeSec;
    private int timeMin;
    private int timeHour;
    private Timer mRunTimer;
    private boolean isRunning;
    // 动态注册锁屏等广播
    private ScreenReceiverUtil mScreenListener;
    // 1像素Activity管理类
    private ScreenManager mScreenManager;

    private ScreenReceiverUtil.ScreenStateListener mScreenListenerer = new ScreenReceiverUtil.ScreenStateListener() {
        @Override
        public void onScreenOn() {//开屏
            // 移除"1像素"
            mScreenManager.finishActivity();
        }

        @Override
        public void onScreenOff() {//锁屏
            // 接到锁屏广播，将SportsActivity切换到可见模式
//            Intent intent = new Intent(SportsActivity.this,SportsActivity.class);
//            startActivity(intent);
            // 如果你觉得，直接跳出SportActivity很不爽
            // 那么，我们就制造个"1像素"Activity
            mScreenManager.startActivity();
        }

        @Override
        public void onUserPresent() {
            // 解锁，暂不用，保留
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        initView();
        //注册锁屏广播监听
        mScreenListener = new ScreenReceiverUtil(this);
        mScreenManager = ScreenManager.getScreenManagerInstance(this);
        mScreenListener.setScreenReceiverListener(mScreenListenerer);
    }

    private void initView() {
        mToolBar = (Toolbar)findViewById(R.id.toolbar_sports);
        mToolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        mToolBar.setTitle("跑步啦");
        mTvRunTime = (TextView)findViewById(R.id.tv_run_time);
        mBtnRun = (Button)findViewById(R.id.btn_run);
    }

    public void onRunningClick(View v){
        if(! isRunning){
            mBtnRun.setText("停止跑步");
            startRunTimer();
            //启动前台Service
            startDaemonService();
            //启动播放音乐Service
            startPlayMusicService();
        }else{
            mBtnRun.setText("开始跑步");
            stopRunTimer();
            //关闭前台Service
            stopDaemonService();
            //关闭启动播放音乐Service
            stopPlayMusicService();
        }
        isRunning = !isRunning;
    }

    /**
     * 开始播放无声音乐的Service
     */
    private void startPlayMusicService() {
        Intent intent = new Intent(SportsActivity.this,PlayerMusicService.class);
        startService(intent);
    }

    /**
     * 停止音乐
     */
    private void stopPlayMusicService() {
        Intent intent = new Intent(SportsActivity.this, PlayerMusicService.class);
        stopService(intent);
    }

    /**
     * 启动前台Service
     */
    private void startDaemonService() {
        Intent intent = new Intent(SportsActivity.this, DaemonService.class);
        startService(intent);
    }

    /**
     * 停止前台Service
     */
    private void stopDaemonService() {
        Intent intent = new Intent(SportsActivity.this, DaemonService.class);
        stopService(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 禁用返回键
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isRunning){
                Toast.makeText(SportsActivity.this,"正在跑步",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startRunTimer() {
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                timeSec++;
                if(timeSec == 60){
                    timeSec = 0;
                    timeMin++;
                }
                if(timeMin == 60){
                    timeMin = 0;
                    timeHour++;
                }
                if(timeHour == 24){
                    timeSec = 0;
                    timeMin = 0;
                    timeHour = 0;
                }
                // 更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvRunTime.setText(timeHour+" : "+timeMin+" : "+timeSec);
                    }
                });
            }
        };
        mRunTimer = new Timer();
        // 每隔1s更新一下时间
        mRunTimer.schedule(mTask,1000,1000);
    }

    private void stopRunTimer(){
        if(mRunTimer != null){
            mRunTimer.cancel();
            mRunTimer = null;
        }
        timeSec = 0;
        timeMin = 0;
        timeHour = 0;
        mTvRunTime.setText(timeHour+" : "+timeMin+" : "+timeSec);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"--->onDestroy");
        stopRunTimer();
        mScreenListener.stopScreenReceiverListener();
    }
}
