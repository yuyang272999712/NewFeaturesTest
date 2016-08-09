package com.yuyang.fitsystemwindowstestdrawer.Canvas.Xfermode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Xfermode实例
 *
 *  底层－目标图DST－是蓝色的
 *  上层－源图SRC－是红色的
 */
public class PorterDuffXfermodeView extends View {
    private Paint mPaint;
    private Bitmap mBottomBitmap, mTopBitmap;
    private Rect mBottomSrcRect, mBottomDestRect;
    private Rect mTopSrcRect, mTopDestRect;

    private Xfermode mPorterDuffXfermode;
    // 图层混合模式
    private PorterDuff.Mode mPorterDuffMode;
    // 总宽高
    private int mTotalWidth, mTotalHeight;
    private Resources mResources;

    public PorterDuffXfermodeView(Context context) {
        this(context,null);
    }

    public PorterDuffXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResources = getResources();
        initBitmap();
        initPaint();
        initXfermode();
    }

    //设置混合模式－MODE
    public void setPorterDuffMode(PorterDuff.Mode mode){
        mPorterDuffMode = mode;
        mPorterDuffXfermode = new PorterDuffXfermode(mPorterDuffMode);
        invalidate();
    }

    //TODO yuyang 初始化混合模式
    private void initXfermode() {
        mPorterDuffMode = PorterDuff.Mode.SRC_OVER;//默认正常模式
        mPorterDuffXfermode = new PorterDuffXfermode(mPorterDuffMode);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//反锯齿
    }

    // 初始化bitmap
    private void initBitmap() {
        mBottomBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.xfermode_blue);
        mTopBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.xfermode_red);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        int halfHeight = h/2;

        mBottomSrcRect = new Rect(0, 0, mBottomBitmap.getWidth(), mBottomBitmap.getHeight());
        mBottomDestRect = new Rect(0, 0, mTotalWidth, halfHeight);// 矩形只画屏幕一半

        mTopSrcRect = new Rect(0, 0, mTopBitmap.getWidth(), mTopBitmap.getHeight());
        mTopDestRect = new Rect(0, 0, mTotalWidth, mTotalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //View 级别如下关闭硬件加速，view 层级上没法单独开启硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // 背景铺白
        canvas.drawColor(Color.WHITE);
        //保存为单独的层
        int saveCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mPaint, Canvas.ALL_SAVE_FLAG);
        // 绘制目标图
        canvas.drawBitmap(mBottomBitmap, mBottomSrcRect, mBottomDestRect, mPaint);
        //TODO yuyang xfermode的关键 设置混合模式
        mPaint.setXfermode(mPorterDuffXfermode);
        //绘制源图
        canvas.drawBitmap(mTopBitmap, mTopSrcRect, mTopDestRect, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }
}
