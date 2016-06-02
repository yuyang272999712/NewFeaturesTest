package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.circleImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 使用BitmapShader绘制圆形image
 */
public class CircleImageView2 extends ImageView {
    /**
     * 图片类型
     */
    private int mType;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    /**
     * 圆角半径
     */
    private int mBorderRadius = 10;
    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆的半径
     */
    private int mRadius;
    /**
     * 用于缩放
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitemapShader;
    /**
     * View的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    /**
     * 状态保存使用
     */
    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    public CircleImageView2(Context context) {
        this(context, null);
    }

    public CircleImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        mBorderRadius = array.getDimensionPixelSize(R.styleable.CircleImageView_imageRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBorderRadius, getResources().getDisplayMetrics()));
        mType = array.getInt(R.styleable.CircleImageView_type, TYPE_CIRCLE);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 如果是圆形就强行让宽和高一致
         */
        if (mType == TYPE_CIRCLE){
            mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mType == TYPE_ROUND){
            mRoundRect = new RectF(0, 0, w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }
        setUpShader();
        if (mType == TYPE_ROUND){
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);
        }else {
            canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
        }
    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader(){
        Drawable drawable = getDrawable();
        if (drawable == null){
            return;
        }
        Bitmap bmp = drawableToBitmap(drawable);
        //将bmp转换为着色器
        mBitemapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if(mType == TYPE_CIRCLE){
            int bmtSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth*1.0f/bmtSize;
        }else if(mType == TYPE_ROUND){
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth()*1.0f/bmp.getWidth(), getHeight()*1.0f/bmp.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        //设置变化矩阵
        mBitemapShader.setLocalMatrix(mMatrix);
        //设置画笔
        mBitmapPaint.setShader(mBitemapShader);
    }

    /**
     * drawable转bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, mType);
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
            this.mType = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
