package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.File;
import java.io.IOException;

/**
 * 调用第三方摄像头，录制视频
 */
public class CameraVideoIntentActivity extends AppCompatActivity {
    private static final int RECORD_VIDEO = 0;

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_video_intent);

        surfaceView = (SurfaceView) findViewById(R.id.video_view);
        surfaceView.setKeepScreenOn(true);

        mediaPlayer = new MediaPlayer();
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                surfaceView.setKeepScreenOn(true);
                mediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mediaPlayer.release();
            }
        });
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        holder.setFixedSize(400, 300);
    }

    public void startRecording(View view){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //指定存储路径，可以不指定
        File file = new File(Environment.getExternalStorageDirectory(), "yuyang_intent_video.mp4");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        //指定视频质量(0表示低质量视频，1表示高质量视频)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        //指定视频最大长度(单位为秒)
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);

        startActivityForResult(intent, RECORD_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_VIDEO && resultCode == RESULT_OK){
            try {
                mediaPlayer.setDataSource(CameraVideoIntentActivity.this, data.getData());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playVideo(View view){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "yuyang_intent_video.mp4");
            mediaPlayer.setDataSource(CameraVideoIntentActivity.this, Uri.fromFile(file));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
