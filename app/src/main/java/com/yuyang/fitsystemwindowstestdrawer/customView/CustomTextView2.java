package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 色彩闪烁效果
 */
public class CustomTextView2 extends TextView {
    int mViewWidth;
    Paint mPaint;
    LinearGradient mLinearGradient;
    Matrix mGradientMatrix;
    int mTranslate;

    public CustomTextView2(Context context) {
        super(context);
    }

    public CustomTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0){
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0,0,mViewWidth,0,new int[]{Color.GREEN, 0xffffffff,Color.RED},null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null){
            mTranslate += mViewWidth/5;
            if (mTranslate>2*mViewWidth){
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }
}
