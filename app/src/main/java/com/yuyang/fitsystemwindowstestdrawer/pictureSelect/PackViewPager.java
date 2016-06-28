package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 用于捕获photoView抛出的IllegalArgumentException
 */
public class PackViewPager extends ViewPager {

    public PackViewPager(Context context) {
        super(context);
    }

    public PackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

}
