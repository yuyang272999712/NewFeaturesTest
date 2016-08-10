package com.yuyang.fitsystemwindowstestdrawer.animationAbout.customAnim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 测试自定义动画
 */
public class CustomAnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animation);
    }

    public void btnAnim(View view) {
        Custom3DAnim customAnim = new Custom3DAnim();
        customAnim.setRotateY(30);
        view.startAnimation(customAnim);
    }

    public void imgClose(View view) {
        CustomTVAnim customTV = new CustomTVAnim();
        view.startAnimation(customTV);
    }
}
