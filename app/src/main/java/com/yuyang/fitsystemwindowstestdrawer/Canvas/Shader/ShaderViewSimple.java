package com.yuyang.fitsystemwindowstestdrawer.Canvas.Shader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 2016/8/9.
 */
public class ShaderViewSimple extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private boolean type;

    public ShaderViewSimple(Context context) {
        this(context, null);
    }

    public ShaderViewSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (type){
            BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            mPaint.setShader(bitmapShader);
            canvas.drawOval(0, 0, mWidth, mHeight, mPaint);
        }else {
            LinearGradient linearGradient = new LinearGradient(0, 0, mWidth/2, mHeight/2, Color.BLUE, Color.YELLOW, Shader.TileMode.MIRROR);
            mPaint.setShader(linearGradient);
            canvas.drawOval(0, 0, mWidth, mHeight, mPaint);
        }
    }

    public void setType(boolean type){
        this.type = type;
        invalidate();
    }
}
