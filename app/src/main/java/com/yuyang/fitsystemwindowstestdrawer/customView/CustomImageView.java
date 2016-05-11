package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/5/11.
 */
public class CustomImageView extends View {
    private final int IMAGE_SCALE_FITXY = 0;

    private String titleText;
    private int titleSize;
    private int titleColor;
    private Bitmap image;
    private int imageScale;

    private Rect textBound;//文字区域大小
    private Rect imageRect;//image区域
    private Paint mPaint;

    private int width;
    private int height;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
        int n = array.length();
        for (int i=0; i<n; i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.CustomImageView_titleText:
                    titleText = array.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleColor:
                    titleColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleSize:
                    titleSize = array.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomImageView_image:
                    image = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    imageScale = array.getInt(attr, 0);
                    break;
            }
        }
        array.recycle();

        imageRect = new Rect();
        textBound = new Rect();
        mPaint = new Paint();

        mPaint.setTextSize(titleSize);
        mPaint.getTextBounds(titleText, 0, titleText.length(), textBound);// 计算了描绘字体需要的范围
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            //图片宽
            int imgWidth = image.getWidth() + getPaddingLeft() + getPaddingRight();
            //文字宽
            int textWidth = textBound.width() + getPaddingLeft() + getPaddingRight();
            if(widthMode == MeasureSpec.AT_MOST){
                int contextWidth = Math.max(imgWidth, textWidth);//获取内容的最小宽度
                width = Math.min(contextWidth, widthSize);//widthSize宽度为父控件所建议的宽度，该宽度可能不够显示整个自定义控价了，因为父控件就剩下这么多宽度了
            }
        }
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            int contextHeight = image.getHeight() + textBound.height() + getPaddingBottom() + getPaddingTop();
            if (heightMode == MeasureSpec.AT_MOST){
                height = Math.min(contextHeight, heightSize);
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 绘制边框
         */
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        /**
         * 绘制文字
         */
        mPaint.setColor(titleColor);
        mPaint.setStyle(Paint.Style.FILL);
        if(textBound.width() > width){//如果文字宽度大于控价宽度
            TextPaint paint = new TextPaint(mPaint);
            //TODO yuyang 根据宽度与paint设置转化text内容是否需要省略
            String msg = TextUtils.ellipsize(titleText, paint, (float)width-getPaddingLeft()-getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), height-getPaddingBottom(), mPaint);
        }else {
            canvas.drawText(titleText, width/2 - textBound.width()/2, height-getPaddingBottom(), mPaint);
        }

        /**
         * 绘制图片
         */
        imageRect.left = getPaddingLeft();
        imageRect.top = getPaddingTop();
        imageRect.right = width - getPaddingRight();
        imageRect.bottom = height - getPaddingBottom() - textBound.height();

        if(imageScale == IMAGE_SCALE_FITXY){
            canvas.drawBitmap(image, null, imageRect, mPaint);
        }else {
            //计算居中的矩形范围 !!这样计算的话图片会被绘制出自定义view界面
            imageRect.left = width / 2 - image.getWidth() / 2;
            imageRect.right = width / 2 + image.getWidth() / 2;
            imageRect.top = (height - textBound.height()) / 2 - image.getHeight() / 2;
            imageRect.bottom = (height - textBound.height()) / 2 + image.getHeight() / 2;

            canvas.drawBitmap(image, null, imageRect, mPaint);
        }
    }

}
