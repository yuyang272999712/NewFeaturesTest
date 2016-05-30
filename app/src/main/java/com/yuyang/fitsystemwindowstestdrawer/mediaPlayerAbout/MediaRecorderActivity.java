package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.IOException;

/**
 * 使用摄像头录制视频
 *  需要权限：
 */
public class MediaRecorderActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);

        surfaceView = (SurfaceView) findViewById(R.id.media_recorder_surface);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(400, 300);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setRecordingHint(true);
        camera.setParameters(parameters);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaRecorder.reset();
        mediaRecorder.release();
        camera.lock();
        camera.release();
    }

    private void prepareVideoCamera() throws IOException {
        mediaRecorder = new MediaRecorder();
        //解锁摄像头以允许Media Recorder拥有它
        camera.unlock();
        //将摄像头分配给Media Recorder
        mediaRecorder.setCamera(camera);
        //配置输入源
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //配置录制配置文件
        CamcorderProfile profile = null;
        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)){
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
        }else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH)){
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        }
        if (profile != null){
            mediaRecorder.setProfile(profile);
        }
        //指定输出文件(该方法必需在prepare之前，setOutputFormat之后调用)
        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory()+"/yuyang_video.mp4");

        mediaRecorder.setPreviewDisplay(holder.getSurface());
        //准备录制
        mediaRecorder.prepare();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        try {
            prepareVideoCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.holder = null;
    }

    /**
     * 开始录制
     * @param view
     */
    public void beginRecord(View view){
        mediaRecorder.start();
    }

    /**
     * 停止录制
     * @param view
     */
    public void stopRecord(View view){
        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        camera.lock();
        try {
            prepareVideoCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
