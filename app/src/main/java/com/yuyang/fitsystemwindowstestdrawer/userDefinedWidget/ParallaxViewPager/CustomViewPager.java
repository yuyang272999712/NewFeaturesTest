package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.ParallaxViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 类似launcher的ViewPager背景视差效果
 */
public class CustomViewPager extends ViewPager {
    private Bitmap bg = BitmapFactory.decodeResource(getResources(), R.mipmap.view_pager_bg);
    private static final int INVALID_POS = -1;
    private int mFirstPos = INVALID_POS;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mFirstPos == INVALID_POS){
            mFirstPos = getCurrentItem();
        }
        if (bg != null){
            int width = bg.getWidth();
            int height = bg.getHeight();
            int count = getAdapter().getCount();
            int x = getScrollX() + mFirstPos * getWidth();
            //每个Item需要显示的图片宽度
            float widthForItem = width*1.0f/count;
            //控件每移动一个像素，图片应该移动的像素
            float widthForPerPx = widthForItem*1.0f/getWidth();
            Rect src = new Rect((int)(x*widthForPerPx),0,(int)(x*widthForPerPx+widthForItem),height);
            Rect dest = new Rect(getScrollX(),0,getScrollX()+getWidth(),getHeight());
            canvas.drawBitmap(bg, src, dest, null);
        }
        super.dispatchDraw(canvas);
    }
}
