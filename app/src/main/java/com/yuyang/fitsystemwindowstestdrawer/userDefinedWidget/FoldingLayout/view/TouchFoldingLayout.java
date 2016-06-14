package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 添加手势的折叠效果布局
 */
public class TouchFoldingLayout extends FoldingLayout {
    private GestureDetector mScrollGestureDetector;
    private int mTranslation = -1;

    public TouchFoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScrollGestureDetector = new GestureDetector(context, new ScrollGestureDetectorListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScrollGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mTranslation == -1){
            mTranslation = getWidth();
        }
        super.dispatchDraw(canvas);
    }

    private class ScrollGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mTranslation -= distanceX;
            if (mTranslation < 0){
                mTranslation = 0;
            }
            if (mTranslation > getWidth()) {
                mTranslation = getWidth();
            }
            float factor = Math.abs(((float) mTranslation) / ((float) getWidth()));
            setFactor(factor);
            return true;
        }
    }
}
