package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterWaveEffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 波浪效果
 */
public class XfermodeWaveView extends View {
    //位移速度
    private final int WAVE_TRANS_SPEED = 2;
    private Paint mPaint;
    private int mTotalWidth,mTotalHeight;
    private int mCenterX,mCenterY;
    private int mSpeed;

    private Bitmap mWaveSrc;
    private Rect mSrcRect, mDestRect;

    private PorterDuffXfermode mPorterDuffXfermode;
    private PaintFlagsDrawFilter mDrawFilter;

    private int mCurrentPosition;

    private boolean isStop = false;

    public XfermodeWaveView(Context context) {
        this(context, null);
    }

    public XfermodeWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWaveSrc = ((BitmapDrawable)getResources().getDrawable(R.mipmap.water_wave, null)).getBitmap();
        }else {
            mWaveSrc = ((BitmapDrawable)getResources().getDrawable(R.mipmap.water_wave)).getBitmap();
        }

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mDrawFilter = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, Paint.DITHER_FLAG);
        mSpeed = DensityUtils.dp2px(context, WAVE_TRANS_SPEED);

        new Thread() {
            public void run() {
                while (!isStop) {
                    // 不断改变绘制的波浪的位置
                    mCurrentPosition += mSpeed;
                    if (mCurrentPosition >= mWaveSrc.getWidth()) {
                        mCurrentPosition = 0;
                    }
                    try {
                        // 为了保证效果的同时，尽可能将cpu空出来，供其他部分使用
                        Thread.sleep(20);
                    } catch (InterruptedException e) {}

                    postInvalidate();
                }

            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(heightMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mCenterX = mTotalWidth/2;
        mCenterY = mTotalHeight/2;

        mSrcRect = new Rect();
        mDestRect = new Rect(0, 0, mTotalWidth, mTotalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        canvas.drawColor(Color.TRANSPARENT);

        /*
         * 将绘制操作保存到新的图层
         */
        int sc = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, null, Canvas.ALL_SAVE_FLAG);

        // 设定要绘制的波纹部分
        mSrcRect.set(mCurrentPosition, 0, mCurrentPosition + mCenterX, mTotalHeight);
        // 绘制目标图像－圆形
        canvas.drawCircle(mCenterX, mCenterY, mCenterX, mPaint);

        // 设置图像的混合模式
        mPaint.setXfermode(mPorterDuffXfermode);
        // 绘制圆内的源图
        canvas.drawBitmap(mWaveSrc, mSrcRect, mDestRect, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStop = true;
    }
}
