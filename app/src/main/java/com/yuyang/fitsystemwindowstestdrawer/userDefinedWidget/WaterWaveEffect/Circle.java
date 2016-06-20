package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterWaveEffect;

import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.Interpolator;

/**
 * 波纹变化的圆
 *
 * TODO yuyang Interpolator插值器
 * 这里用到了插值器，插值器主要使用其 getInterpolation 方法
 * 该方法的作用就是根据传入参数(x值)返回其对应的值(y值)，不同的插值器x、y的对应关系不同
 * 这里的插值器主要是为了实现水波纹的越来越慢的效果
 */
public class Circle {
    private Interpolator mInterpolator = new LinearOutSlowInInterpolator();
    private long mCreateTime;
    private long mDuration;//持续时间
    private float mRadius;//半径
    private float mMaxRadius;//最大半径

    public Circle(long duration, float radius, float maxRadius){
        this.mDuration = duration;
        this.mRadius = radius;
        this.mMaxRadius = maxRadius;
        mCreateTime = System.currentTimeMillis();
    }

    public int getAlpha(){
        float parent = (System.currentTimeMillis() - mCreateTime)*1.0f/mDuration;
        return (int) ((1-mInterpolator.getInterpolation(parent))*255);
    }

    public float getCurrentRadius(){
        float parent = (System.currentTimeMillis() - mCreateTime)*1.0f/mDuration;
        return mRadius + mInterpolator.getInterpolation(parent)*(mMaxRadius-mRadius);
    }
}
