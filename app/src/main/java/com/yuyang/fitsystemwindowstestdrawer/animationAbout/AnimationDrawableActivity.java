package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 帧动画 AnimationDrawable
 */
public class AnimationDrawableActivity extends AppCompatActivity {
    private ImageView targetImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);

        targetImg = (ImageView) findViewById(R.id.drawable_anim_img);

        targetImg.setBackgroundResource(R.drawable.animation_drawable);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            AnimationDrawable drawable = (AnimationDrawable) targetImg.getBackground();
            drawable.start();
        }
    }
}
