package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.progressBars;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 自定义带数字的圆形进度条
 */
public class RoundProgressBarWidthNumber extends HorizontalProgressBarWithNumber {
    /**
     * 默认半径30dp
     */
    private int mRadius = DensityUtils.dp2px(getContext(), 30);
    private int paintWidth;

    public RoundProgressBarWidthNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
        mReachedProgressBarHeight = (int) (mUnReachedProgressBarHeight*2.5f);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWidthNumber);
        mRadius = (int) array.getDimension(R.styleable.RoundProgressBarWidthNumber_progress_radius, mRadius);
        array.recycle();

        mTextSize = DensityUtils.sp2px(context, 14);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        paintWidth = Math.max(mReachedProgressBarHeight, mUnReachedProgressBarHeight);

        if (widthMode != MeasureSpec.EXACTLY){
            int exceptWidth = getPaddingLeft() + getPaddingRight() + 2*mRadius + paintWidth;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY){
            int exceptHeight = getPaddingTop() + getPaddingBottom() + 2*mRadius + paintWidth;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress()+"%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.ascent()+mPaint.descent())/2;

        canvas.save();
        canvas.translate(getPaddingLeft()+paintWidth/2,getPaddingTop()+paintWidth/2);
        mPaint.setStyle(Paint.Style.STROKE);
        //先画未到达的进度
        mPaint.setColor(mUnReachedBarColor);
        mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        //绘制已到达进度
        mPaint.setColor(mReachedBarColor);
        mPaint.setStrokeWidth(mReachedProgressBarHeight);
        float sweepAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,mRadius*2,mRadius*2), 0, sweepAngle, false, mPaint);
        //绘制文字
        if (mIfDrawText){
            mPaint.setColor(mTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);
        }
        canvas.restore();
    }
}
