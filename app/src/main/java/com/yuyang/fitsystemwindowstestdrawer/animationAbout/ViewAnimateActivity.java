package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * View的animate方法
 */
public class ViewAnimateActivity extends AppCompatActivity {
    private ImageView tagertImg;
    private float mScreenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animate);

        tagertImg = (ImageView) findViewById(R.id.view_animate_img);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
    }

    //View Animate方法实现动画
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void viewAnimate(View view){
        tagertImg.animate()
                .alpha(0)
                .y(mScreenHeight)
                .setDuration(2000)
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ViewAnimateActivity.this, "动画开始", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tagertImg.setY(0);
                                tagertImg.setAlpha(1f);
                            }
                        });
                    }
                });
    }

    //属性动画实现相同的动画
    public void propertyAnimator(View view){
        /*ObjectAnimator animator1 = ObjectAnimator.ofFloat(tagertImg, "alpha", 1f, 0f, 1f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(tagertImg, "y", 0f, mScreenHeight, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.play(animator1).with(animator2);
        animatorSet.start();*/

        //和上面那么多行代码是一个效果
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("y", 0f, mScreenHeight, 0f);
        ObjectAnimator.ofPropertyValuesHolder(tagertImg, holder1, holder2).setDuration(2000).start();

    }
}
