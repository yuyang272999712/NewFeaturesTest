package com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * VideoView播放视频
 */
public class VideoActivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoView) findViewById(R.id.video_view);

        videoView.setKeepScreenOn(true);//使屏幕常亮
        //videoView.setVideoPath("/sdcard/video_test.mp4");//设置视频来源
        Uri uri = Uri.parse("http://u.rui2.net/upload/13/2016/06/2016061614525418222p14p.mp4");
        videoView.setVideoURI(uri);

        //附加一个Media Controller
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
    }

}
