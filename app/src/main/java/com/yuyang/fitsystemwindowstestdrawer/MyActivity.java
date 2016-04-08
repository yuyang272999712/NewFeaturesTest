package com.yuyang.fitsystemwindowstestdrawer;

import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

/**
 * Created by yuyang on 16/1/7.
 */
public class MyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private ImageView imageView;
    private ImageButton imageButton;
    private TextView textView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("开发测试");
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        button = (Button) findViewById(R.id.myButton);
        imageView = (ImageView) findViewById(R.id.myImage);
        imageButton = (ImageButton) findViewById(R.id.myImageButton);
        textView = (TextView) findViewById(R.id.myTextView);
        relativeLayout = (RelativeLayout) findViewById(R.id.myRelativeLayout);

        ObjectAnimator a1 = ObjectAnimator.ofFloat(button, "alpha", 1.0f, 0f);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(button, "translationX", 0f, 100f);
        final AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(5000);
        animationSet.setInterpolator(new LinearInterpolator());
//        animationSet.play(a1).after(a2);
        animationSet.playTogether(a1, a2);

        final ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        valueAnimator.setObjectValues(new float[2]);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<float[]>() {
            @Override
            public float[] evaluate(float fraction, float[] startValue, float[] endValue) {
                //实现自定义规则计算的float[]类型的属性值
                float[] temp = new float[2];
                temp[0] = fraction * 2;
                temp[1] = (float) Math.random() * 10 * fraction;
                return temp;
            }

        });
        valueAnimator.setTarget(imageButton);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] xyPos = (float[]) animation.getAnimatedValue();
                imageButton.setX(xyPos[0]);//通过属性值设置View属性动画
                imageButton.setY(xyPos[1]);//通过属性值设置View属性动画
            }
        });

//        LayoutTransition layoutTransition = new LayoutTransition();
//        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, animationSet);
//        relativeLayout.setLayoutTransition(layoutTransition);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationSet.start();
                valueAnimator.start();
                relativeLayout.removeView(textView);
            }
        });

        getSharedPreferences("sdf",MODE_PRIVATE).edit();
    }
}
