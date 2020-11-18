package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.textViewSpannable.mySpan;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

/**
 * 自定义Span
 *  给相应的字符序列添加边框的效果
 */

public class FrameSpan extends ReplacementSpan {
    private Paint myPaint;
    private int mWidth;

    public FrameSpan(){
        myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        myPaint.setColor(Color.BLUE);
        myPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, @Nullable Paint.FontMetricsInt fm) {
        //计算字符宽度
        mWidth = (int) paint.measureText(text, start, end);
        return mWidth;
    }

    /**
     * @param canvas 用来绘制的画布；
     * @param text 整个text；
     * @param start 这个Span起始字符在text中的位置；
     * @param end 这个Span结束字符在text中的位置；
     * @param x 这个Span的起始水平坐标；
     * @param y 这个Span的baseline的垂直坐标；
     * @param top 这个Span的起始垂直坐标；
     * @param bottom 这个Span的结束垂直坐标；
     * @param paint 这个Span的结束垂直坐标；
     */
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        canvas.drawRect(x, top, x+mWidth, bottom, myPaint);
        canvas.drawText(text, start, end, x, y, paint);
    }
}
