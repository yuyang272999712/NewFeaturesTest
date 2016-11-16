package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout.nestedScrollBehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 缩小／放大来表现消失／显示的Behavior
 */

public class FloatActionButtonBehavior extends BaseBehavior {
    private ScaleAnimateHelper animateHelper;

    public FloatActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (isFirstMove){
            animateHelper = ScaleAnimateHelper.get(child);
            isFirstMove = false;
        }

        if (Math.abs(dy) >= mTouchSlop){
            if (dy>0 && animateHelper.getState()==BaseAnimateHelper.STATE_SHOW){
                animateHelper.hide();
            }else if (dy<0 && animateHelper.getState()==BaseAnimateHelper.STATE_HIDE){
                animateHelper.show();
            }
        }
    }
}
