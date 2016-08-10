package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 代码实现属性动画
 */
public class PropertyAniActivity extends AppCompatActivity {
    private static final String TAG = PropertyAniActivity.class.getName();

    private ImageView object1;
    private ImageView object2;
    private ImageView object3;
    private ImageView object4;
    private ImageView value1;

    private ObjectAnimator objAni1;
    private ObjectAnimator objAni2;
    private ObjectAnimator objAni3;
    private ObjectAnimator objAni4;

    private ValueAnimator valueAni1;
    private ValueAnimator valueAni2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);

        findViews();
    }

    //TODO mark 常规方式创建ObjectAnimator
    public void rotateObjAnim1(View view){
        objAni1 = ObjectAnimator.ofFloat(object1, "rotationX", 0f, 360f);
        objAni1.setRepeatCount(0);
        objAni1.setDuration(1000).setInterpolator(new AccelerateDecelerateInterpolator());
        //TODO yuyang 为animator添加监听事件
        objAni1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "rotationX:"+object1.getRotationX());
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        objAni1.start();
    }

    //TODO mark ObjectAnimator多属性变化
    public void noParamsObjAnim2(View view){
        //TODO yuyang 这里设置属性“yuyang”的变化，由于ImageView中找不到“yuyang”属性的get、set方法，所有需要自己更新变化
        objAni2 = ObjectAnimator.ofFloat(object2, "yuyang", 1f, 0f).setDuration(500);
        objAni2.setRepeatCount(1);
        objAni2.setRepeatMode(ValueAnimator.REVERSE);
        //TODO yuyang 设置动画中心点
        object2.setPivotX(0);
        object2.setPivotY(0);
        object2.invalidate();

        objAni2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                object2.setAlpha(val);
                object2.setScaleX(val);
                object2.setScaleY(val);
            }
        });
        objAni2.start();
    }

    //TODO mark 使用propertyValuesHolder,设置多属性变化
    public void propertyValuesHolderObjAnim3(View view){
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f);
        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("translationX", 0, 150);
        objAni3 = ObjectAnimator.ofPropertyValuesHolder(object3, valuesHolder, valuesHolder1, valuesHolder2);
        objAni3.start();
    }

    //TODO mark 使用Keyframe定义指定时间点的指定值
    public void keyframeObjAnim4(View view) {
        // 属性1：Y坐标运动：下落
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", object4.getY(), object4.getY()+1000);
        float objX = object4.getLeft();
        //TODO yuyang 三个关键帧
        Keyframe kf0 = Keyframe.ofFloat(0f, objX);
        Keyframe kf1 = Keyframe.ofFloat(0.5f, objX + 100f);
        kf1.setInterpolator(new AccelerateInterpolator());
        Keyframe kf2 = Keyframe.ofFloat(1f, objX + 50f);
        // 属性2：X坐标运动：曲折
        // 用三个关键帧构造PropertyValuesHolder对象
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("x", kf0, kf1, kf2);
        // 再用两个PropertyValuesHolder对象构造一个ObjectAnimator对象
        objAni4 = ObjectAnimator.ofPropertyValuesHolder(object4, pvhY, pvhX).setDuration(2000);
        objAni4.setRepeatCount(1);//重复一次
        objAni4.setRepeatMode(ValueAnimator.REVERSE);
        objAni4.start();
    }

    /**
     * 自由落体 ValueAnimator
     * @param view
     */
    public void verticalRun(View view) {
        valueAni1 = ValueAnimator.ofFloat(0, 200*3);
        valueAni1.setTarget(value1);
        valueAni1.setInterpolator(new AccelerateInterpolator());
        valueAni1.setDuration(2000).start();
        valueAni1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value1.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }

    /**
     * 抛物线 ValueAnimator
     */
    public void paowuxianRun(View view){
        valueAni2 = new ValueAnimator();
        valueAni2.setDuration(2000);
        valueAni2.setInterpolator(new AccelerateInterpolator());
        //相当于ObjectAnimator的设置 初始／最终 值
        valueAni2.setObjectValues(new PointF(0,100), new PointF(100,0));
        valueAni2.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                Log.e(TAG, fraction * 3 + "");
                //fraction是duration的百分比
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });
        valueAni2.start();
        valueAni2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                value1.setX(point.x);
                value1.setY(point.y);
            }
        });
    }

    /**
     * 同时执行动画
     */
    public void togetherRun(View view) {
        if(objAni4 == null){
            Toast.makeText(this,"请先依次点击四个图片", Toast.LENGTH_SHORT).show();
            return;
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(2000);
        animSet.setInterpolator(new LinearInterpolator());
        //两个动画同时执行
        animSet.playTogether(objAni1, objAni2, objAni3, objAni4);
        animSet.start();
    }

    /**
     * 顺序执行
     * @param view
     */
    public void playWithAfter(View view) {
        if(objAni4 == null){
            Toast.makeText(this,"请先依次点击四个图片", Toast.LENGTH_SHORT).show();
            return;
        }
        /**
         * anim1在anim2之前执行
         * anim2，anim3同时执行
         * anim4接着执行
         */
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(objAni1).before(objAni2);
        animSet.play(objAni2).with(objAni3);
        animSet.play(objAni4).after(objAni3);
        animSet.setDuration(2000);
        animSet.start();
    }

    private void findViews() {
        object1 = (ImageView) findViewById(R.id.object_ani_1);
        object2 = (ImageView) findViewById(R.id.object_ani_2);
        object3 = (ImageView) findViewById(R.id.object_ani_3);
        object4 = (ImageView) findViewById(R.id.object_ani_4);
        value1 = (ImageView) findViewById(R.id.value_anim);
    }
}
