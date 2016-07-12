package com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemAnimator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DebugUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * Created by yuyang on 2016/7/12.
 */
public class SlideRelativeLayout extends RelativeLayout {
    public static final String TAG = SlideRelativeLayout.class.getSimpleName();
    private CheckBox mCheckBox;
    private RelativeLayout mContentSlide;
    private int mOffset;

    public SlideRelativeLayout(Context context) {
        super(context);
    }

    public SlideRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCheckBox = (CheckBox) findViewById(R.id.item_checkbox);
        mContentSlide = (RelativeLayout) findViewById(R.id.item_content_rl);
        setOffset(45);
    }

    private void setOffset(int offset) {
        mOffset = DensityUtils.dp2px(getContext(), offset);
    }

    public void openAnimator(){
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0,1);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                int endX = (int) (-mOffset*fraction);
                doAnimationSet(endX, fraction);
            }
        });
        animator.start();
    }

    public void closeAnimator(){
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0, 1);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                int endX = (int) (-mOffset * (1 - fraction));
                doAnimationSet(endX, (1 - fraction));
            }
        });
        animator.start();
    }

    private void doAnimationSet(int endX, float fraction) {
        mContentSlide.scrollTo(endX, 0);
        mCheckBox.setScaleX(fraction);
        mCheckBox.setScaleY(fraction);
        mCheckBox.setAlpha(fraction*255);
    }

    public void open() {
        mContentSlide.scrollTo(-mOffset, 0);
    }

    public void close() {
        mContentSlide.scrollTo(0, 0);
    }
}
