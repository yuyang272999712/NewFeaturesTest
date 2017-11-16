package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.textViewSpannable.mySpan;

import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * 透明效果的Span
 */

public class MutableForegroundColorSpan extends CharacterStyle implements UpdateAppearance {
    private int mAlpha = 255;

    public MutableForegroundColorSpan(int mAlpha) {
        this.mAlpha = mAlpha;
    }

    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(mAlpha);
    }

    public int getAlpha() {
        return mAlpha;
    }

    public void setAlpha(int mAlpha) {
        this.mAlpha = mAlpha;
    }
}
