package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 多媒体相关
 *
 * 添加至媒体库
 *  MediaScannerConnection
 */
public class MediaAboutActivity extends AppCompatActivity {
    private Button btnVideo;
    private Button btnSurface;
    private Button btnAudio;
    private Button btnRawAudio;
    private Button btnSoundPool;
    private Button btnCameraIntent;
    private Button btnCamera;
    private Button btnVideoIntent;
    private Button btnRecorder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_about);

        findViews();
        setAction();
    }

    private void findViews() {
        btnVideo = (Button) findViewById(R.id.media_about_video);
        btnSurface = (Button) findViewById(R.id.media_about_surface);
        btnAudio = (Button) findViewById(R.id.media_audio_manager);
        btnRawAudio = (Button) findViewById(R.id.media_audio_raw);
        btnSoundPool = (Button) findViewById(R.id.media_audio_sound_pool);
        btnCameraIntent  = (Button) findViewById(R.id.media_audio_camera_intent);
        btnCamera  = (Button) findViewById(R.id.media_audio_camera);
        btnVideoIntent = (Button) findViewById(R.id.media_audio_video_intent);
        btnRecorder = (Button) findViewById(R.id.media_audio_recorder);
    }

    /**
     * 将新生成的多媒体文件扫描至媒体库
     */
    public void scanner(View view) {
        /*MediaScannerConnection.MediaScannerConnectionClient connectionClient = new MediaScannerConnection.MediaScannerConnectionClient() {
            private MediaScannerConnection msc = null;
            {msc = new MediaScannerConnection(MediaAboutActivity.this, this);
             msc.connect();}
            @Override
            public void onMediaScannerConnected() {
                //可以指定一个MIME类型，或者让Media Scanner根据文件名自己假定一种类型
                String mimeType = null;
                msc.scanFile(Environment.getExternalStorageDirectory().getPath(), mimeType);
            }

            @Override
            public void onScanCompleted(String path, Uri uri) {
                msc.disconnect();
                Log.e("MediaAboutActivity", uri.toString());
            }
        };*/

        ContentValues content = new ContentValues();
        content.put(MediaStore.Audio.Media.DATA, Environment.getExternalStorageDirectory().toString()+"/yuyang_intent_video.mp4");
        ContentResolver resolver = getContentResolver();
        Uri uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);
        //插入媒体库后公布其可用性
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    private void setAction() {
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        btnSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, SurfaceActivity.class);
                startActivity(intent);
            }
        });

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, AudioPlayerActivity.class);
                startActivity(intent);
            }
        });

        btnRawAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, RawAudioActivity.class);
                startActivity(intent);
            }
        });

        btnSoundPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, SoundPoolActivity.class);
                startActivity(intent);
            }
        });

        btnCameraIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, CameraIntentActivity.class);
                startActivity(intent);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        btnVideoIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, CameraVideoIntentActivity.class);
                startActivity(intent);
            }
        });

        btnRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaAboutActivity.this, MediaRecorderActivity.class);
                startActivity(intent);
            }
        });
    }
}
