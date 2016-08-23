package com.yuyang.fitsystemwindowstestdrawer.androidL.activityOptions;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 2016/8/22.
 */
public class SecondActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        int flag = getIntent().getExtras().getInt("flag");
        //TODO yuyang 设置进入动画效果
        switch (flag){
            case 0:
                getWindow().setEnterTransition(new Explode());
                break;
            case 1:
                getWindow().setEnterTransition(new Slide());
                break;
            case 2:
                getWindow().setEnterTransition(new Fade());
                getWindow().setReturnTransition(new Explode());
                break;
            case 3:
                break;
            case 4:
                Transition myTransition = TransitionInflater.from(this).inflateTransition(R.transition.my_test_transition);
                getWindow().setEnterTransition(myTransition);
                getWindow().setReturnTransition(myTransition);
                break;
        }
        setContentView(R.layout.activity_options_second);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
