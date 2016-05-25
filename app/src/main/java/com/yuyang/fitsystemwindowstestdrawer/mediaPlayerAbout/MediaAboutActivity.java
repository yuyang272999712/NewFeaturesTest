package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 多媒体相关
 */
public class MediaAboutActivity extends AppCompatActivity {
    private Button btnVideo;
    private Button btnSurface;
    private Button btnAudio;
    private Button btnRawAudio;
    private Button btnSoundPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_about);

        findViews();
        initDatas();
        setAction();
    }

    private void findViews() {
        btnVideo = (Button) findViewById(R.id.media_about_video);
        btnSurface = (Button) findViewById(R.id.media_about_surface);
        btnAudio = (Button) findViewById(R.id.media_audio_manager);
        btnRawAudio = (Button) findViewById(R.id.media_audio_raw);
        btnSoundPool = (Button) findViewById(R.id.media_audio_sound_pool);
    }

    private void initDatas() {

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
    }
}
