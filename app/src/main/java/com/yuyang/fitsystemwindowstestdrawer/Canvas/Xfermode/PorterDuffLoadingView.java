package com.yuyang.fitsystemwindowstestdrawer.Canvas.Xfermode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

public class PorterDuffLoadingView extends View {

    private Resources mResources;
    private Paint mBitPaint;
    private Bitmap mBitmap;

    private int mTotalWidth, mTotalHeight;
    private int mBitWidth, mBitHeight;
    private Rect mSrcRect, mDestRect;
    private PorterDuffXfermode mXfermode;

    private Rect mDynamicRect;
    private int mCurrentTop;
    private int mStart, mEnd;

    public PorterDuffLoadingView(Context context) {
        this(context, null);
    }

    public PorterDuffLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResources = getResources();
        initBitmap();
        initPaint();
        initXfermode();
    }


    private void initXfermode() {
        // 叠加处绘制源图
        mXfermode = new PorterDuffXfermode(Mode.SRC_IN);
    }

    private void initPaint() {
        // 初始化paint
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        mBitPaint.setColor(Color.RED);
    }

    private void initBitmap() {
        // 初始化bitmap
        mBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.xfermode_loading)).getBitmap();
        mBitWidth = mBitmap.getWidth();
        mBitHeight = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 存为新图层
        int saveLayerCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mBitPaint,
                Canvas.ALL_SAVE_FLAG);
        // 绘制目标图
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mBitPaint);
        // 设置混合模式
        mBitPaint.setXfermode(mXfermode);
        // 绘制源图形
        canvas.drawRect(mDynamicRect, mBitPaint);
        // 清除混合模式
        mBitPaint.setXfermode(null);
        // 恢复保存的图层；
        canvas.restoreToCount(saveLayerCount);

        // 改变Rect区域，真实情况下时提供接口传入进度，计算高度
        mCurrentTop -= 8;
        if (mCurrentTop <= mEnd) {
            mCurrentTop = mStart;
        }
        mDynamicRect.top = mCurrentTop;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        // 让左边和上边有些距离
        int left = (int) TypedValue.complexToDimension(20, mResources.getDisplayMetrics());
        mDestRect = new Rect(left, left, left + mBitWidth, left + mBitHeight);
        mStart = left + mBitHeight;
        mCurrentTop = mStart;
        mEnd = left;
        mDynamicRect = new Rect(left, mStart, left + mBitWidth, left + mBitHeight);
    }
}
