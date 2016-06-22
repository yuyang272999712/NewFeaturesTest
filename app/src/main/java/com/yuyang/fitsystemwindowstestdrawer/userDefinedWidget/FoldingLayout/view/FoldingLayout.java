package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 折叠效果布局
 */
public class FoldingLayout extends ViewGroup {
    private static final int NUM_OF_POINT = 8;
    /**
     * 折叠后宽度与原图宽度比例
     */
    private float mFactor = 1f;
    /**
     * 折叠后宽度
     */
    private float mTranslateDis;
    /**
     * 折叠块数
     */
    private int mNumOfFolds = NUM_OF_POINT;

    private Matrix[] mMatrices = new Matrix[mNumOfFolds];
    /**
     * 绘制黑色透明区域
     */
    private Paint mSolidPaint;
    /**
     * 绘制阴影
     */
    private Paint mShadowPaint;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;
    /**
     * 原图每块宽度
     */
    private float mFlodWidth;
    /**
     * 折叠时每块宽度
     */
    private float mTranslateDisPerFlod;

    private Canvas mCanvas = new Canvas();
    private Bitmap mBitmap;
    private boolean isReady;

    //为DrawerLayout偏移量添加的变量
    private float mAnchor = 0;

    public FoldingLayout(Context context) {
        this(context, null);
    }

    public FoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化mMatrices
        for (int i = 0; i < mMatrices.length; i++) {
            mMatrices[i] = new Matrix();
        }

        //初始化阴影画笔
        mSolidPaint = new Paint();
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0,0,0.5f,0,Color.BLACK,Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        mShadowGradientMatrix = new Matrix();
        this.setWillNotDraw(false);
    }

    /**
     * FoldLayout只能有一个直接子元素
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //TODO yuyang Specify a bitmap for the canvas to draw into.
        //这样他的子View就绘制在了mBitmap上
        mCanvas.setBitmap(mBitmap);
        updateFold();
    }

    private void updateFold() {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        //折叠后宽度
        mTranslateDis = w * mFactor;
        //原图每块宽度
        mFlodWidth = w / mNumOfFolds;
        //折叠时每块宽度
        mTranslateDisPerFlod = mTranslateDis / mNumOfFolds;

        int alpha = (int) (255 * (1 - mFactor));
        mSolidPaint.setColor(Color.argb((int) (alpha * 0.8F), 0, 0, 0));
        mShadowGradientMatrix.setScale(mFlodWidth, 1);
        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
        mShadowPaint.setAlpha(alpha);
        //纵轴减小的高度，用勾股定理计算
        float depth = (float) (Math.sqrt(mFlodWidth*mFlodWidth - mTranslateDisPerFlod*mTranslateDisPerFlod)/2);

        float anchorPoint = mAnchor * w;
        float midFold = (anchorPoint / mFlodWidth);

        //转换点
        float[] src = new float[mNumOfFolds];
        float[] dst = new float[mNumOfFolds];
        /**
         * 原图的每一块，对应折叠后的每一块，方向为左上、右上、右下、左下
         */
        for (int i=0; i<mNumOfFolds; i++){
            mMatrices[i].reset();

            src[0] = i*mFlodWidth;
            src[1] = 0;
            src[2] = src[0] + mFlodWidth;
            src[3] = 0;
            src[4] = src[2];
            src[5] = h;
            src[6] = src[0];
            src[7] = src[5];

            boolean isEven = i%2==0;//波峰波谷
            dst[0] = i*mTranslateDisPerFlod;
            dst[1] = isEven ? 0 : depth;
            dst[2] = dst[0] + mTranslateDisPerFlod;
            // 引入anchor
            dst[0] = (anchorPoint > i * mFlodWidth) ? anchorPoint
                    + (i - midFold) * mTranslateDisPerFlod : anchorPoint
                    - (midFold - i) * mTranslateDisPerFlod;
            dst[2] = (anchorPoint > (i + 1) * mFlodWidth) ? anchorPoint
                    + (i + 1 - midFold) * mTranslateDisPerFlod : anchorPoint
                    - (midFold - i - 1) * mTranslateDisPerFlod;

            dst[3] = isEven ? depth:0;
            dst[4] = dst[2];
            dst[5] = isEven ? h-depth:h;
            dst[6] = dst[0];
            dst[7] = isEven ? h:h-depth;
            for (int y = 0; y < 8; y++) {
                dst[y] = Math.round(dst[y]);
            }

            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length>>1);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mFactor == 0) {
            return;
        }
        if (mFactor == 1) {
            super.dispatchDraw(canvas);
            return;
        }
        //绘制mNumOfFolds次
        for (int i=0; i<mNumOfFolds; i++){
            canvas.save();
            //将matrix应用到canvas
            canvas.concat(mMatrices[i]);
            //控制显示的大小
            canvas.clipRect(i*mFlodWidth, 0, mFlodWidth*i+mFlodWidth, mBitmap.getHeight());
            if (isReady) {
                //绘制图片
                canvas.drawBitmap(mBitmap, 0, 0, null);
            }else {
                super.dispatchDraw(mCanvas);//获取mBitmap
                canvas.drawBitmap(mBitmap, 0, 0, null);
                isReady = true;
            }
            //移动绘制阴影
            canvas.translate(mFlodWidth*i, 0);
            if (i % 2 == 0) {
                //绘制黑色遮盖
                canvas.drawRect(0, 0, mFlodWidth, mBitmap.getHeight(),
                        mSolidPaint);
            }else {
                //绘制阴影
                canvas.drawRect(0, 0, mFlodWidth, mBitmap.getHeight(),
                        mShadowPaint);
            }
            canvas.restore();
        }
    }

    public void setFactor(float factor) {
        this.mFactor = factor;
        updateFold();
        invalidate();
    }

    public float getFactor() {
        return mFactor;
    }

    public void setAnchor(float anchor) {
        this.mAnchor = anchor;
        updateFold();
        invalidate();
    }
}
