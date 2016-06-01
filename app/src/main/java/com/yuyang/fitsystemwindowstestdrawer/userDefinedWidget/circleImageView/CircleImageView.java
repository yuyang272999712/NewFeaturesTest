package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.circleImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 圆形／圆角 图片
 * 最好继承自ImageView
 */
public class CircleImageView extends View {
    private int mType;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    private Bitmap mSrc;
    private int mRadius;
    private int mWidth;
    private int mHeight;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        for (int i=0; i<array.getIndexCount(); i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.CircleImageView_imageSrc:
                    mSrc = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, 0));
                    break;
                case R.styleable.CircleImageView_imageRadius:
                    mRadius = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleImageView_type:
                    mType = array.getInt(attr, TYPE_CIRCLE);//默认为圆
                    break;
            }
        }
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){//match_parent/accurate
            mWidth = widthSize;
        }else {
            // 由图片决定的宽
            int width = mSrc.getWidth()+getPaddingRight()+getPaddingLeft();
            if (widthMode == MeasureSpec.AT_MOST){//wrap_content
                mWidth = Math.min(width, widthSize);
            }else {
                mWidth = width;
            }
        }
        //设置高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else {
            int height = mSrc.getHeight()+getPaddingTop()+getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST){
                mHeight = Math.min(height, heightSize);
            }else {
                mHeight = height;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mType == TYPE_ROUND){
            canvas.drawBitmap(createRoundCornerImage(mSrc),0,0,null);
        }else {
            int min = Math.min(mWidth,mHeight);
            mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
            canvas.drawBitmap(createCircleImage(mSrc, min),0,0,null);
        }
    }

    /**
     * 获取圆形图
     * @param source
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         * TODO yuyang 重点设置Paint的Xfermode，以使两次绘制的图形产生叠加效果
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 获取圆角图
     * @param source
     * @return
     */
    private Bitmap createRoundCornerImage(Bitmap source) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);
        //TODO yuyang 重点设置Paint的Xfermode，以使两次绘制的图形产生叠加效果
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source,0,0,paint);
        return target;
    }
}
