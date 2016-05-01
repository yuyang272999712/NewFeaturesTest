package com.yuyang.fitsystemwindowstestdrawer.coordinatorLayoutAbout;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * 滑动 处理
 * 功能：根据滑动判断显示还是隐藏
 *
 * 注意被依赖的View只有实现了 NestedScrollingChild 接口的才可以将事件传递给CoordinatorLayout。
 * 但注意这个滑动事件是对于CoordinatorLayout的。所以只要CoordinatorLayout有NestedScrollingChild的滑动，
 * 他滑动就会触发这几个回调。无论你是否依赖了那个View。
 */
public class MyDefineScrollBehavior extends CoordinatorLayout.Behavior<View> {
    private Interpolator interpolator = new FastOutLinearInInterpolator();
    private boolean mIsHiding = false;

    public MyDefineScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //1.判断滑动的方向 我们需要垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    //2.根据滑动的距离显示和隐藏footer view
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0  && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            show(child);
        }
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
