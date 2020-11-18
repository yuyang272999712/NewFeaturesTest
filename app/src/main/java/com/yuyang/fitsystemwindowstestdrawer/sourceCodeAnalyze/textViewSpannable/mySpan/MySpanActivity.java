package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.textViewSpannable.mySpan;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.metricsAbout.screenAndViewMetrics.VerticalImageSpan;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 自定义Span
 */

public class MySpanActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    //估值器
    private static final Property<AnimatedColorSpan, Float> ANIMATED_COLOR_SPAN_FLOAT_PROPERTY
            = new Property<AnimatedColorSpan, Float>(Float.class, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY") {
        @Override
        public void set(AnimatedColorSpan span, Float value) {
            span.setTranslateXPercentage(value);
        }
        @Override
        public Float get(AnimatedColorSpan span) {
            return span.getTranslateXPercentage();
        }
    };
    private static final Property<FireworksSpanGroup, Float> FIREWORKS_GROUP_PROGRESS_PROPERTY
            = new Property<FireworksSpanGroup, Float>(Float.class, "FIREWORKS_GROUP_PROGRESS_PROPERTY") {
        @Override
        public void set(FireworksSpanGroup spanGroup, Float value) {
            spanGroup.setAlpha(value);
        }
        @Override
        public Float get(FireworksSpanGroup spanGroup) {
            return spanGroup.getAlpha();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_span);
        findViews();
        initSpanView();
    }

    private void initSpanView() {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        final SpannableStringBuilder builder2 = new SpannableStringBuilder();

        TextView frameText = (TextView) findViewById(R.id.frame_span);
        FrameSpan frameSpan = new FrameSpan();
        builder.append("FrameSpan-给相应的字符序列添加边框的效果");
        builder.setSpan(frameSpan, 0, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        frameText.setText(builder);

        builder.clear();
        builder.clearSpans();

        TextView verticalImageText = (TextView) findViewById(R.id.vertical_image_span);
        VerticalImageSpan verticalImageSpan = new VerticalImageSpan(this, R.drawable.intro3_item_4);
        builder.append("VerticalImageSpan-图片居中显示的效果");
        builder.setSpan(verticalImageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        verticalImageText.setText(builder);

        builder.clear();
        builder.clearSpans();

        final TextView animatedColorText = (TextView) findViewById(R.id.animated_color_span);
        AnimatedColorSpan animatedColorSpan = new AnimatedColorSpan();
        builder.append("AnimatedColorSpan-前景色彩虹效果");
        builder.setSpan(animatedColorSpan, 0, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(animatedColorSpan, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0, 100);
        objectAnimator1.setEvaluator(new FloatEvaluator());
        objectAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedColorText.setText(builder);
            }
        });
        objectAnimator1.setInterpolator(new LinearInterpolator());
        objectAnimator1.setDuration(DateUtils.MINUTE_IN_MILLIS * 3);
        objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator1.start();


        final FireworksSpanGroup spanGroup = new FireworksSpanGroup(0f);
        final TextView fireWorksText = (TextView) findViewById(R.id.fire_works_span);
        MutableForegroundColorSpan alphaSpan1 = new MutableForegroundColorSpan(0);
        MutableForegroundColorSpan alphaSpan2 = new MutableForegroundColorSpan(0);
        MutableForegroundColorSpan alphaSpan3 = new MutableForegroundColorSpan(0);
        MutableForegroundColorSpan alphaSpan4 = new MutableForegroundColorSpan(0);
        MutableForegroundColorSpan alphaSpan5 = new MutableForegroundColorSpan(0);
        spanGroup.addSpan(alphaSpan1);
        spanGroup.addSpan(alphaSpan2);
        spanGroup.addSpan(alphaSpan3);
        spanGroup.addSpan(alphaSpan4);
        spanGroup.addSpan(alphaSpan5);
        spanGroup.init();
        builder2.append("“烟火”动画是让文字随机淡入");
        builder2.setSpan(alphaSpan1, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder2.setSpan(alphaSpan2, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder2.setSpan(alphaSpan3, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder2.setSpan(alphaSpan4, 7, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        builder2.setSpan(alphaSpan5, 8, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(spanGroup, FIREWORKS_GROUP_PROGRESS_PROPERTY, 0.0f, 1.0f);
        objectAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fireWorksText.setText(builder2);
            }
        });
        objectAnimator2.setDuration(2000);
        objectAnimator2.start();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("SpannableString的使用");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private static final class FireworksSpanGroup {
        private final float mAlpha;
        private final ArrayList<MutableForegroundColorSpan> mSpans;
        private FireworksSpanGroup(float alpha) {
            mAlpha = alpha;
            mSpans = new ArrayList<MutableForegroundColorSpan>();
        }
        public void addSpan(MutableForegroundColorSpan span) {
            span.setAlpha((int) (mAlpha * 255));
            mSpans.add(span);
        }
        public void init() {
            Collections.shuffle(mSpans);
        }
        public void setAlpha(float alpha) {
            int size = mSpans.size();
            float total = 1.0f * size * alpha;
            for(int index = 0 ; index < size; index++) {
                MutableForegroundColorSpan span = mSpans.get(index);
                if(total >= 1.0f) {
                    span.setAlpha(255);
                    total -= 1.0f;
                } else {
                    span.setAlpha((int) (total * 255));
                    total = 0.0f;
                }
            }
        }
        public float getAlpha() { return mAlpha; }
    }
}
