package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout.nestedScrollBehavior;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by yuyang on 16/11/16.
 */

public class ScaleAnimateHelper implements BaseAnimateHelper {
    public View mTarget;
    public int mCurrentState = STATE_SHOW;

    private ScaleAnimateHelper(View view){
        mTarget = view;
    }

    public static ScaleAnimateHelper get(View view){
        return new ScaleAnimateHelper(view);
    }

    @Override
    public void show() {
        mTarget.setVisibility(View.VISIBLE);
        ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);
        mTarget.startAnimation(animation);
        mCurrentState = STATE_SHOW;
    }

    @Override
    public void hide() {
        ScaleAnimation animation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(300);
        mTarget.startAnimation(animation);
        mTarget.setVisibility(View.GONE);
        mCurrentState = STATE_HIDE;
    }

    public int getState(){
        return mCurrentState;
    }
}
