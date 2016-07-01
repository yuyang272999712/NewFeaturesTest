package com.yuyang.fitsystemwindowstestdrawer.Game2048;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 2048中的每个块
 */
public class Game2048Item extends View {
    private int mNumber;
    private String mNumberValue;
    private Paint mPaint;
    /**
     * 绘制文字的区域
     */
    private Rect mBound;
    private int textSize = 30;

    public Game2048Item(Context context) {
        this(context, null);
    }

    public Game2048Item(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Game2048Item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        textSize = DensityUtils.sp2px(context, textSize);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    public int getmNumber() {
        return mNumber;
    }

    public void setmNumber(int mNumber) {
        this.mNumber = mNumber;
        this.mNumberValue = mNumber+"";
        mBound = new Rect();
        mPaint.getTextBounds(mNumberValue, 0, mNumberValue.length(), mBound);
        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String mBgColor = "#CCC0B3";
        switch (mNumber){
            case 0:
                mBgColor = "#CCC0B3";
                break;
            case 2:
                mBgColor = "#EEE4DA";
                break;
            case 4:
                mBgColor = "#EDE0C8";
                break;
            case 8:
                mBgColor = "#F2B179";
                break;
            case 16:
                mBgColor = "#F49563";
                break;
            case 32:
                mBgColor = "#F5794D";
                break;
            case 64:
                mBgColor = "#F55D37";
                break;
            case 128:
                mBgColor = "#EEE863";
                break;
            case 256:
                mBgColor = "#EDB04D";
                break;
            case 512:
                mBgColor = "#ECB04D";
                break;
            case 1024:
                mBgColor = "#EB9437";
                break;
            case 2048:
                mBgColor = "#EA7821";
                break;
            default:
                mBgColor = "#EA7821";
                break;
        }
        //绘制背景
        mPaint.setColor(Color.parseColor(mBgColor));
        mPaint.setStyle(Paint.Style.FILL);
        RectF bgRect = new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(bgRect,5,5,mPaint);

        //绘制数字
        if (mNumber != 0){
            mPaint.setColor(Color.BLACK);
            float x = (getWidth()-mBound.width())/2;
            float y = getHeight() / 2 + mBound.height() / 2;
            canvas.drawText(mNumberValue, x, y, mPaint);
        }
    }
}
