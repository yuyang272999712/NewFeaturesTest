package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout.myBehavior;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * 依赖 处理
 * 功能：根据其他控件（这里指AppBarLayout）的状态来判断动作
 */
public class MyDefineDependBehavior extends CoordinatorLayout.Behavior<View> {
    private Rect mTmpRect;
    private Interpolator interpolator = new FastOutLinearInInterpolator();
    private boolean mIsHiding = false;

    public MyDefineDependBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //1、重写Behavior的这个方法来确定你依赖哪些View。
    /**
     * @param parent 担任触发behavior的角色
     * @param child 指应用behavior的View
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    //2、这个方法很重要。当所依赖的View变动时会回调这个方法。
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (mTmpRect == null) {
            mTmpRect = new Rect();
        }

        // First, let's get the visible rect of the dependency
        final Rect rect = mTmpRect;
        ViewGroupUtils.getDescendantRect(parent, dependency, rect);

        if (rect.bottom < dependency.getHeight()) {
            // If the anchor's bottom is below the seam, we'll animate our FAB out
            hide(child);
        } else {
            // Else, we'll animate our FAB back in
            show(child);
        }
        return true;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    private void show(final View child) {
        ViewPropertyAnimator animator = child.animate().translationY(0).setInterpolator(interpolator).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                child.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private void hide(final View child) {
        if(mIsHiding){
            return;
        }
        ViewPropertyAnimator animator = child.animate().translationY(child.getHeight()).setInterpolator(interpolator).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsHiding = true;
                child.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsHiding = false;
                child.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mIsHiding = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animator.start();
    }
}
