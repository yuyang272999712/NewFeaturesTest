package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0330/7757.html
 * SpringAnimation 类是最近（25.3.0版本）才添加在支持库中的一个类，它主要是为了让实现弹性动画变得更加方便
 *
 * setDampingRatio(float dampingRatio)方法设置弹性阻尼，dampingRatio越大，摆动次数越少，当到1的时候完全不摆动，注意它体验出来的最明显的特征是摆动次数这个概念
 * setStiffness(float stiffness)方法设置弹性的生硬度，stiffness值越小，弹簧越容易摆动，摆动的时间越长，反之摆动时间越短，注意它体验出来的最明显的特征是摆动时间这个概念
 */

public class DynamicAnimationActivity extends AppCompatActivity {
    float STIFFNESS = SpringForce.STIFFNESS_MEDIUM;//硬度
    float DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY;//阻尼

    SpringAnimation xAnimation;//x方向动画
    SpringAnimation yAnimation;//y方向动画

    float dX = 0f;
    float dY = 0f;

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_animation);

        mImageView = (ImageView) findViewById(R.id.movingView);

        //以图片的初始位置创建动画
        mImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                xAnimation = createSpringAnimation(mImageView, SpringAnimation.X, mImageView.getX(), STIFFNESS, DAMPING_RATIO);
                yAnimation = createSpringAnimation(mImageView, SpringAnimation.Y, mImageView.getY(), STIFFNESS, DAMPING_RATIO);
                mImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        xAnimation.cancel();
                        yAnimation.cancel();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.animate().x(event.getRawX()+dX)
                                .y(event.getRawY()+dY)
                                .setDuration(0)
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        xAnimation.start();
                        yAnimation.start();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 创建弹性动画
     * @param view 动画关联的控件
     * @param property 动画作用的属性
     * @param finalPosition 动画结束的位置
     * @param stiffness 硬度
     * @param dampingRatio 阻尼
     * @return
     */
    private SpringAnimation createSpringAnimation(View view,
                                                  DynamicAnimation.ViewProperty property,
                                                  Float finalPosition,
                                                  @FloatRange(from = 0.0) Float stiffness,
                                                  @FloatRange(from = 0.0) Float dampingRatio){
        //创建弹性动画
        SpringAnimation animation = new SpringAnimation(view, property);
        //创建动画特质
        SpringForce springForce = new SpringForce(finalPosition);
        springForce.setStiffness(stiffness);
        springForce.setDampingRatio(dampingRatio);
        //关联弹性特质
        animation.setSpring(springForce);
        return animation;
    }
}
