package com.yuyang.fitsystemwindowstestdrawer.animationAbout.shoppingCartAnimator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

/**
 * 购物车添加商品的抛物线动画
 */
public class CartAddGoodAnimator {

    public static void startAnimatorAddCart(View good, View cart, Activity activity, final Object object) {
        //复制一个商品，给这复制品添加动画即可
        good.setDrawingCacheEnabled(true);
        good.buildDrawingCache();
        Bitmap bitmap = good.getDrawingCache();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(good.getWidth(), good.getHeight());
        final ImageView imageView = new ImageView(activity);
        imageView.setLayoutParams(params);
        imageView.setImageBitmap(bitmap);
        //获取activity的根布局
        final ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.addView(imageView);

        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rootView.getLocationInWindow(parentLocation);
        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        good.getLocationInWindow(startLoc);
        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        cart.getLocationInWindow(endLoc);

        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0];
        float startY = startLoc[1] - parentLocation[1];
        //商品掉落后的终点坐标
        float toX = endLoc[0] - parentLocation[0] + (cart.getWidth()/2-good.getWidth()/2);
        float toY = endLoc[1] - parentLocation[1];
        //计算贝塞尔曲线
        Path path = new Path();
        path.moveTo(startX, startY);
        path.quadTo((startX+toX)/2, startY-300, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        //TODO yuyang PathMeasure是Path的测量工具
        // 如果是true，path会形成一个闭环
        final PathMeasure mPathMeasure = new PathMeasure(path, false);

        //开始动画
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] mCurrentPosition = new float[2];
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                imageView.setTranslationX(mCurrentPosition[0]);
                imageView.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.removeView(imageView);
                EventBus.getDefault().post(object);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        valueAnimator.start();
    }
}
