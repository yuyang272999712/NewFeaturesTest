package com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.clipImage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 图片剪切的遮罩
 */
public class ClipImageBorderView extends View {
    /**
     * 裁剪框水平方向与View的距离
     */
    private int mHorizontalPadding = 20;
    /**
     * 裁剪框垂直方向与View的距离
     */
    private int mVerticalPadding;
    /**
     * 裁剪框宽度
     */
    private int mWidth;
    /**
     * 裁剪框边颜色
     */
    private int mBorderColor = Color.parseColor("#FFFFFF");
    /**
     * 裁剪框边宽
     */
    private int mBorderWidth = 1;
    private Paint mPaint;

    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClipImageLayout, defStyleAttr, 0);
        mHorizontalPadding = (int) array.getDimension(R.styleable.ClipImageLayout_horizontal_padding,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, context.getResources().getDisplayMetrics()));
        mBorderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                mBorderWidth, context.getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算矩形宽度
        mWidth = getWidth() - mHorizontalPadding * 2;
        //计算上下边框距离
        mVerticalPadding = (getHeight()-mWidth)/2;
        //绘制遮罩区域
        mPaint.setColor(Color.parseColor("#aa000000"));
        mPaint.setStyle(Paint.Style.FILL);
        //绘制上遮罩
        canvas.drawRect(mHorizontalPadding, 0, mHorizontalPadding+mWidth, mVerticalPadding, mPaint);
        //绘制下遮罩
        canvas.drawRect(mHorizontalPadding, mVerticalPadding+mWidth, mHorizontalPadding+mWidth, getHeight(), mPaint);
        //绘制左遮罩
        canvas.drawRect(0, 0, mHorizontalPadding, getHeight(), mPaint);
        //绘制右遮罩
        canvas.drawRect(mHorizontalPadding+mWidth, 0, getWidth(), getHeight(), mPaint);
        //绘制裁剪框
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        canvas.drawRect(mHorizontalPadding, mVerticalPadding, mHorizontalPadding+mWidth, mVerticalPadding+mWidth, mPaint);
    }
}
