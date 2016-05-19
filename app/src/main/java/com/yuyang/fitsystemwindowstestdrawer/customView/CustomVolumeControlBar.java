package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义声音控件View
 */
public class CustomVolumeControlBar extends View {
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
     * 中间的图片
     */
    private Bitmap mImage;
    /**
     * 每个块块间的间隙(就是间隔的角度)
     */
    private int mSplitSize;
    /**
     * 个数
     */
    private int mCount;

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mCurrentCount = 3;

    private Rect mRect;

    public CustomVolumeControlBar(Context context) {
        this(context, null);
    }

    public CustomVolumeControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumeControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumeControlBar, defStyleAttr, 0);
        int n = typedArray.length();
        for (int i=0; i<n; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomVolumeControlBar_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CustomVolumeControlBar_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CustomVolumeControlBar_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr,
                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomVolumeControlBar_dotCount:
                    mCount = typedArray.getInt(attr, 20);
                    break;
                case R.styleable.CustomVolumeControlBar_splitSize:
                    mSplitSize = typedArray.getInt(attr, 20);
                    break;
                case R.styleable.CustomVolumeControlBar_bg:
                    mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环宽度
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置线段两头为圆形
        mPaint.setAntiAlias(true);//消除锯齿

        int center = getWidth()/2;//中心x坐标
        int radius = center - mCircleWidth / 2;//半径

        /**
         * 画点块
         */
        drawOval(canvas, center, radius);

        /**
         * 画内部的图片
         */
        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);
        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mImage.getWidth());
            mRect.bottom = (int) (mRect.top + mImage.getHeight());
        }
        // 绘图
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    /**
     * 根据参数画出每个小块
     * @param canvas
     * @param center
     * @param radius
     */
    private void drawOval(Canvas canvas, int center, int radius) {
        /**
         * 计算每块的长度(这里其实是所占的角度)
         */
        float itemSize = (360*1.0f - mSplitSize*mCount)/mCount;

        RectF oval = new RectF(center - radius,center - radius,center + radius,center + radius);

        mPaint.setColor(mFirstColor);//设置底色
        for (int i=0; i<mCount; i++){
            canvas.drawArc(oval, i*(itemSize+mSplitSize) - 90, itemSize, false, mPaint);
        }

        mPaint.setColor(mSecondColor);//设置覆盖色
        for (int i=0; i<mCurrentCount; i++){
            canvas.drawArc(oval, i*(itemSize+mSplitSize) - 90, itemSize, false, mPaint);
        }
    }

    /**
     * 当前数量+1
     */
    public void up() {
        if (mCurrentCount >= mCount){
            return;
        }
        mCurrentCount++;
        postInvalidate();
    }

    /**
     * 当前数量-1
     */
    public void down() {
        if (mCurrentCount <= 0){
            return;
        }
        mCurrentCount--;
        postInvalidate();
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown){// 下滑
                    down();
                } else{
                    up();
                }
                break;
        }
        return true;
    }
}
