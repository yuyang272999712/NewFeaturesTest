package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 2016/5/16.
 */
public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoView) findViewById(R.id.video_view);

        videoView.setKeepScreenOn(true);//使屏幕常亮
        videoView.setVideoPath("/sdcard/video_test.mp4");//设置视频来源
//        videoView.setVideoURI(uri);

        //附加一个Media Controller
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
    }

}
