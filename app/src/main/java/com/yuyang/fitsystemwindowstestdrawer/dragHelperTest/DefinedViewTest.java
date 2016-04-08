package com.yuyang.fitsystemwindowstestdrawer.dragHelperTest;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/2/22.
 */
public class DefinedViewTest extends AppCompatActivity {
    private TextView mTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.defind_view_test);

        mTextView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        final ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        valueAnimator.setObjectValues(new float[2]); //设置属性值类型
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<float[]>() {
            @Override
            public float[] evaluate(float fraction, float[] startValue,
                                    float[] endValue) {
                //实现自定义规则计算的float[]类型的属性值
                float[] temp = new float[2];
                temp[0] = fraction * 2;
                temp[1] = (float) Math.random() * 10 * fraction;
                return temp;
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] xyPos = (float[]) animation.getAnimatedValue();
                mTextView.setX(xyPos[0]);   //通过属性值设置View属性动画
                mTextView.setY(xyPos[1]);    //通过属性值设置View属性动画
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
                button.animate().x(50f).y(100f).start();
            }
        });

        //按键倒计时
        CountDownTimer timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText("倒计时" + millisUntilFinished/1000 + "秒");
            }

            @Override
            public void onFinish() {
                button.setText("倒计时完成");
            }
        };
        timer.start();
    }
}
