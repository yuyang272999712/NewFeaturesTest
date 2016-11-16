package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout.nestedScrollBehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 底部View随内容滚动而消失／显示
 */

public class BottomViewBehavior extends BaseBehavior {
    private TranslateAnimateHelper mAnimateHelper;

    public BottomViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (isFirstMove){
            mAnimateHelper = TranslateAnimateHelper.get(child);
            mAnimateHelper.setMode(TranslateAnimateHelper.MODE_BOTTOM);
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
