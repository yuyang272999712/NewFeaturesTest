package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private Button play;
    private Button pause;
    private Button skip;

    private MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);

        mediaPlayer = new MediaPlayer();

        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SurfaceActivity.this, "点击测试", Toast.LENGTH_SHORT).show();
            }
        });
        //配置SurfaceView
        surfaceView.setKeepScreenOn(true);
        //配置SurfaceHolder并注册回调
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //创建Surface后，将其作为显示界面，并分配和准备一个数据源
        mediaPlayer.setDisplay(holder);
        try {
            //mediaPlayer.setDataSource("/sdcard/video_test.mp4");
            Uri uri = Uri.parse("");
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaController.show();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mediaPlayer.release();
    }
}
