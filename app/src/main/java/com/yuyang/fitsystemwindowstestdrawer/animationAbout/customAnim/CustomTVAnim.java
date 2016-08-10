package com.yuyang.fitsystemwindowstestdrawer.animationAbout.customAnim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 创建电视机关闭效果的动画
 */
public class CustomTVAnim extends Animation {
    private int mCenterWidth;
    private int mCenterHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        // 设置默认时长
        setDuration(1000);
        // 动画结束后保留状态
        setFillAfter(true);
        // 设置默认插值器
        setInterpolator(new AccelerateInterpolator());
        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();
        matrix.preScale(1,
                1 - interpolatedTime,
                mCenterWidth,
                mCenterHeight);
    }
}
