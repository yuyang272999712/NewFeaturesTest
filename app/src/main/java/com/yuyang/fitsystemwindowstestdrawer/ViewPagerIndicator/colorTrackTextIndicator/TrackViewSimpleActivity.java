package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.colorTrackTextIndicator;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * ColorTrackView 简单使用
 */
public class TrackViewSimpleActivity extends Activity {
    private ColorTrackView trackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_view_simple);

        trackView = (ColorTrackView) findViewById(R.id.track_view);
    }

    public void startLeftChange(View view){
        trackView.setDirection(0);
        //TODO yuyang 注意ColorTrackView中的setProgress()方法
        ObjectAnimator animator = ObjectAnimator.ofFloat(trackView, "progress", 0, 1);
        animator.setDuration(2000).start();
    }

    public void startRightChange(View view){
        trackView.setDirection(1);
        ObjectAnimator animator = ObjectAnimator.ofFloat(trackView, "progress", 0, 1);
        animator.setDuration(2000).start();
    }
}
