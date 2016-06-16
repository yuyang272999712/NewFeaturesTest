package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.colorTrackTextIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 渐变色彩的TextView，类似于今日头条APP的Tab
 * 主要使用了canvas.clipRect方法对绘制区域进行截取
 */
public class ColorTrackView extends View {
    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;
    //文字开始的坐标X
    private int mTextStartX;
    //颜色渐变的方向
    private int mDirection = DIRECTION_LEFT;

    private String mText = "测试";
    private Paint mPaint;
    private int mTextSize = DensityUtils.sp2px(getContext(), 20);
    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;
    private Rect mTextBound = new Rect();
    private int mTextWidth;
    //颜色区移动的百分比
    private float mProgress;

    public ColorTrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackView);
        mText = a.getString(R.styleable.ColorTrackView_color_track_text);
        mTextSize = a.getDimensionPixelSize(R.styleable.ColorTrackView_color_track_text_size, mTextSize);
        mTextOriginColor = a.getColor(R.styleable.ColorTrackView_color_track_origin_color, mTextOriginColor);
        mTextChangeColor = a.getColor(R.styleable.ColorTrackView_color_track_change_color, mTextChangeColor);
        mProgress = a.getFloat(R.styleable.ColorTrackView_color_track_progress, 0);
        mDirection = a.getInt(R.styleable.ColorTrackView_color_track_direction, DIRECTION_LEFT);
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTextWidth = (int) mPaint.measureText(mText);
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mTextStartX = getMeasuredWidth()/2 - mTextWidth/2;
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextBound.height();
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = heightMode == MeasureSpec.AT_MOST ? Math.min(result, heightSize):result;
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextWidth;
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = widthMode == MeasureSpec.AT_MOST ? Math.min(result, widthSize):result;
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r = (int) (mProgress * mTextWidth + mTextStartX);
        if (mDirection == DIRECTION_LEFT){
            drawChangeLeft(canvas, r);
            drawOriginLeft(canvas, r);
        }else {
            drawOriginRight(canvas, r);
            drawChangeRight(canvas, r);
        }
    }

    private void drawChangeRight(Canvas canvas, int r) {
        drawText(canvas, mTextChangeColor, (int)(mTextStartX +(1-mProgress)*mTextWidth), mTextStartX+mTextWidth);
    }

    private void drawOriginRight(Canvas canvas, int r) {
        drawText(canvas, mTextOriginColor, mTextStartX, (int)(mTextStartX +(1-mProgress)*mTextWidth));
    }

    private void drawOriginLeft(Canvas canvas, int r) {
        drawText(canvas, mTextOriginColor, r, mTextStartX+mTextWidth);
    }

    private void drawChangeLeft(Canvas canvas, int r) {
        drawText(canvas, mTextChangeColor, mTextStartX, r);
    }

    private void drawText(Canvas canvas, int color, int startX, int endX){
        mPaint.setColor(color);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());
        canvas.drawText(mText, mTextStartX, getMeasuredHeight()/2+mTextBound.height()/2, mPaint);
        canvas.restore();
    }

    public void setDirection(int direction){
        mDirection = direction;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    //TODO 注意状态保存
    private static final String KEY_STATE_PROGRESS = "key_progress";
    private static final String KEY_DEFAULT_STATE = "key_default_state";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putFloat(KEY_STATE_PROGRESS, mProgress);
        bundle.putParcelable(KEY_DEFAULT_STATE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mProgress = bundle.getFloat(KEY_STATE_PROGRESS);
            super.onRestoreInstanceState(bundle
                    .getParcelable(KEY_DEFAULT_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
