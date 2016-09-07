package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.IOException;

/**
 * SurfaceView播放视频
 * SurfaceView类是SurfaceHolder对象的包装器，后者是Surface的包装器，而Surface用于支持来自后台线程的可视化更新。
 * SurfaceHolder是异步创建的，因此必须等待直到surfaceCreated处理程序被触发，然后在通过实现SurfaceHolder.Callback
 * 接口将返回的SurfaceHolder对象分配给Media Player
 *
 * MediaController可以直接作用于VideoView。
 * MediaController要想控制MediaPlayer需要实现一个新的MediaController.MediaPlayerControl
 *
 * MediaPlayer
 *  常用方法：
 *      create()
 *      setDataSource()
 *      prepare()
 *      start\stop\pause
 *      getDuration()
 *      getCurrentPosition()
 *      seekTo() //跳到媒体的特定位置
 *      setVolume(0.5f,0.5f)  //设置声道音量0~1之间
 *      setScreenOnWhilePlaying(true) //强制屏幕在视频播放期间不变暗
 *      setLooping(true) //循环播放
 *      isLooping()
 *
 */
public class SurfaceActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String TAG = "SurfaceActivity";
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private Button play;
    private Button pause;
    private Button skip;

    private MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);

        mediaPlayer = new MediaPlayer();

        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaController.isShowing()) {
                    mediaController.show();
                }
                Toast.makeText(SurfaceActivity.this, "点击测试", Toast.LENGTH_SHORT).show();
            }
        });
        //配置SurfaceView
        surfaceView.setKeepScreenOn(true);
        //配置SurfaceHolder并注册回调
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.setFixedSize(400, 300);

        //连接播放等按钮
        play = (Button) findViewById(R.id.surface_play);
        pause = (Button) findViewById(R.id.surface_pause);
        skip = (Button) findViewById(R.id.surface_skip);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getDuration()/2);
            }
        });


        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(new MediaController.MediaPlayerControl() {
            public boolean canPause() {
                return true;
            }

            public boolean canSeekBackward() {
                return true;
            }

            public boolean canSeekForward() {
                return true;
            }

            @Override
            public int getAudioSessionId() {
                return 1;
            }

            public int getBufferPercentage() {
                return 0;
            }

            public int getCurrentPosition() {
                return mediaPlayer.getCurrentPosition();
            }

            public int getDuration() {
                return mediaPlayer.getDuration();
            }

            public boolean isPlaying() {
                return mediaPlayer.isPlaying();
            }

            public void pause() {
                mediaPlayer.pause();
            }

            public void seekTo(int pos) {
                mediaPlayer.seekTo(pos);
            }

            public void start() {
                mediaPlayer.start();
            }
        });
        //设置目标View，以在目标View上显示mediaController
        mediaController.setAnchorView(surfaceView);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //创建Surface后，将其作为显示界面，并分配和准备一个数据源
        mediaPlayer.setDisplay(holder);
        try {
            mediaPlayer.setDataSource("/sdcard/video_test.mp4");
            //Uri uri = Uri.parse("http://u.rui2.net/upload/13/2016/06/2016061614525418222p14p.mp4");
            //mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaController.show();
                }
            });
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    Toast.makeText(SurfaceActivity.this, "OnInfoListener情况:"+what, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "SurfaceHolder销毁了");
        mediaPlayer.release();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        surfaceView.destroyDrawingCache();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState");
    }
}
