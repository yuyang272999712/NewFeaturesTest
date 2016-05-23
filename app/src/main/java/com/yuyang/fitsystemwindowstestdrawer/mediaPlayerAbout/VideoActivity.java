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
//        videoView.setVideoPath("/sdcard/video_test.mp4");//设置视频来源
        Uri uri = Uri.parse("https://allall01.baidupcs.com/file/8b18517e2727f8777d8773eeb2dc2772?bkt=p2-nj02-582&fid=2636345392-250528-503385350486456&time=1464016848&sign=FDTAXGERLBH-DCb740ccc5511e5e8fedcff06b081203-AKk3IT7C92NQZZyhJxZSTwpVncY%3D&to=wb&fm=Nan,B,G,bs&sta_dx=9&sta_cs=0&sta_ft=mp4&sta_ct=6&fm2=Nanjing02,B,G,bs&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=0000b1635992caed3591364d7d1e2ffc1532&sl=76480590&expires=8h&rt=pr&r=548179574&mlogid=3339135403386078657&vuk=2636345392&vbdid=2922756190&fin=1438707234571.mp4&fn=1438707234571.mp4&slt=pm&uta=0&rtype=1&iv=0&isw=0&dp-logid=3339135403386078657&dp-callid=0.1.1");
        videoView.setVideoURI(uri);
        //http://36.250.76.166/ws.cdn.baidupcs.com/file/41d6a946dd4bb24bacfffd2e5323b860?bkt=p2-nb-46&xcode=aa7cb4c02d55cbae43d955fbe64572531d32ee54a64c9734ed03e924080ece4b&fid=2636345392-250528-49066242055759&time=1464016570&sign=FDTAXGERLBH-DCb740ccc5511e5e8fedcff06b081203-xHIUw8OEQbEYMl%2BkxNCb21g9Yss%3D&to=lc&fm=Nin,B,G,bs&sta_dx=435&sta_cs=71&sta_ft=mp4&sta_ct=6&fm2=Ningbo,B,G,bs&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=00003ce80bef33ffc2b07232232ada3827d0&sl=76480590&expires=8h&rt=pr&r=281207761&mlogid=3339060586622902784&vuk=2636345392&vbdid=2922756190&fin=lzg159-%E5%BC%82%E5%9F%9F%E5%AD%97%E5%B9%95-dmxz.zerodm.tv.mp4&fn=lzg159-%E5%BC%82%E5%9F%9F%E5%AD%97%E5%B9%95-dmxz.zerodm.tv.mp4&slt=pm&uta=0&rtype=1&iv=0&isw=0&dp-logid=3339060586622902784&dp-callid=0.1.1&wshc_tag=0&wsts_tag=57431ebb&wsid_tag=76f7e03d&wsiphost=ipdbm

        //附加一个Media Controller
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
    }

}
