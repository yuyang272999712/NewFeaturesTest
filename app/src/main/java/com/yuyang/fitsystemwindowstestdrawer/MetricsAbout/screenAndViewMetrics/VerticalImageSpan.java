package com.yuyang.fitsystemwindowstestdrawer.metricsAbout.screenAndViewMetrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.style.ImageSpan;

/**
 *    Google提供的ImageSpan和DynamicDrawableSpan只能实现图片和文字底部对齐或者是baseline对齐，
 * 现在VerticalImageSpan可以实现图片和文字居中对齐。
 */

public class VerticalImageSpan extends ImageSpan {

    public VerticalImageSpan(Context context, @DrawableRes int resourceId) {
        super(context, resourceId);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable mDrawable = getDrawable();
        Rect rect = mDrawable.getBounds();
        if (fm != null){
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int fontHeight = fontMetricsInt.bottom - fontMetricsInt.top;
            int drHeight = rect.height();

            int top = drHeight/2 - fontHeight/4;
            int bottom = drHeight/2 + fontHeight/4;

            fm.top = -bottom;
            fm.ascent = -bottom;
            fm.descent = top;
            fm.bottom = top;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        canvas.save();
        int transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }
}
