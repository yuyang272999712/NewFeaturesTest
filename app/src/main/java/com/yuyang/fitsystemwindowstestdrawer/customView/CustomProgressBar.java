package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义View
 */
public class CustomProgressBar extends View {
    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 速度
     */
    private int mSpeed;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mProgress;
    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;

    private boolean isStop = false;

    private Thread thread = new Thread() {
        public void run()
        {
            while (!isStop)
            {
                mProgress++;
                if (mProgress == 360)
                {
                    mProgress = 0;
                    if (!isNext)
                        isNext = true;
                    else
                        isNext = false;
                }
                postInvalidate();
                try
                {
                    Thread.sleep(mSpeed);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            Log.i("－－CustomProgressBar－－", "停止progressBar");
        }
    };

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        int n = typedArray.length();
        for (int i=0; i<n; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr,
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = typedArray.getInt(attr, 20);
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth()/2;//获取圆心X坐标
        int radius = center - mCircleWidth/2;//获取半径
        mPaint.setStrokeWidth(mCircleWidth);//这是圆环的宽度
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置空心

        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);//设置绘制区域
        if (isNext){
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(center,center,radius,mPaint);//画圆环
            mPaint.setColor(mSecondColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);//画圆弧
        }else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(center,center,radius,mPaint);//画圆环
            mPaint.setColor(mFirstColor);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);//画圆弧
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 绘图线程
        thread.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStop = true;
    }
}
