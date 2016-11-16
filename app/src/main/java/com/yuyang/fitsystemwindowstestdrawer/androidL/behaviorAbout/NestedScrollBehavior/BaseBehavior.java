package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout.nestedScrollBehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

/**
 *  嵌套滚动Behavior的基类
 */

public abstract class BaseBehavior extends CoordinatorLayout.Behavior<View> {
    protected final int mTouchSlop;//判断是移动（不是点击）的最小距离
    protected boolean isFirstMove = true;

    public BaseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 只关心竖直方向的滚动
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }
}
