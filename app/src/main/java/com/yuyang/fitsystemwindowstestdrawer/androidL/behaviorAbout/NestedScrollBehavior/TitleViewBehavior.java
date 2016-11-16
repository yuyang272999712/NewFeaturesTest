package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout.nestedScrollBehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 顶部View根据内容滚动消失／显示的Behavior
 */

public class TitleViewBehavior extends BaseBehavior {
    private TranslateAnimateHelper mAnimateHelper;

    public TitleViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (isFirstMove){
            mAnimateHelper = TranslateAnimateHelper.get(child);
            mAnimateHelper.setMode(TranslateAnimateHelper.MODE_TITLE);
            isFirstMove = false;
        }
        if (Math.abs(dy) >= mTouchSlop){
            if (dy < 0 && mAnimateHelper.getState() == BaseAnimateHelper.STATE_HIDE){
                mAnimateHelper.show();
            }else if (dy > 0 && mAnimateHelper.getState()==BaseAnimateHelper.STATE_SHOW){
                mAnimateHelper.hide();
            }
        }
    }
}
