package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.ColourImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * 使用LayerDrawable实现着色器
 */
public class ColourImageBaseLayerView extends View {
    private LayerDrawable mDrawables;

    public ColourImageBaseLayerView(Context context) {
        this(context, null);
    }

    public ColourImageBaseLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColourImageBaseLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDrawables = (LayerDrawable) getBackground();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mDrawables.getIntrinsicWidth(), mDrawables.getIntrinsicHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Drawable drawable = findDrawableBy(x, y);
            if (drawable != null) {
                drawable.setColorFilter(getRandomColor(), PorterDuff.Mode.SRC_IN);
            }
        }
        return super.onTouchEvent(event);
    }

    private Drawable findDrawableBy(float x, float y) {
        int count = mDrawables.getNumberOfLayers();
        Drawable drawable = null;
        Bitmap bitmap = null;
        for (int i=count-1; i>=0; i--){
            drawable = mDrawables.getDrawable(i);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            int pixel = bitmap.getPixel((int) x, (int) y);
            if (pixel == Color.TRANSPARENT){
                continue;
            }
            return drawable;
        }
        return null;
    }

    public int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
