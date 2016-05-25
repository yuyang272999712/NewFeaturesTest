package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * SoundPool
 */
public class SoundPoolActivity extends AppCompatActivity {
    private Button play1;
    private Button play2;
    private Button play3;
    private Button stopAll;
    private Button chipmunk;

    SoundPool sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundpool);

        play1 = (Button) findViewById(R.id.sound_pool_1);
        play2 = (Button) findViewById(R.id.sound_pool_2);
        play3 = (Button) findViewById(R.id.sound_pool_3);
        stopAll = (Button) findViewById(R.id.sound_pool_stop);
        chipmunk = (Button) findViewById(R.id.sound_pool_rate);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int maxStreams = 10;
        sp = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder build = new SoundPool.Builder();
            sp = build.setMaxStreams(maxStreams).build();
        }

        final int sound1 = sp.load(this, R.raw.track1, 0);
        final int sound2 = sp.load(this, R.raw.track2, 0);
        final int sound3 = sp.load(this, R.raw.track3, 0);

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(sound1, 1, 1, 0, -1, 1);
            }
        });
        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(sound2, 1, 1, 0, 0, 1);
            }
        });
        play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.play(sound3, 1, 1, 0, 0, 0.5f);
            }
        });
        stopAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.stop(sound1);
                sp.stop(sound2);
                sp.stop(sound3);
            }
        });
        chipmunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.setRate(sound1, 2f);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO yuyang 一定要释放资源
        sp.release();
    }
}
