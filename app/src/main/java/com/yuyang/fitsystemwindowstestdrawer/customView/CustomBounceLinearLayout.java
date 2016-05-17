package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Scroller实验
 */
public class CustomBounceLinearLayout extends LinearLayout {
    private Scroller scroller;
    private GestureDetector gestureDetector;

    public CustomBounceLinearLayout(Context context) {
        this(context, null);
    }

    public CustomBounceLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBounceLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setLongClickable(true);
        scroller = new Scroller(context);
        gestureDetector = new GestureDetector(context, new GestureListenerImpl());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            prepareScroll(0, 0);
        }else {
            gestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){//如果滚动未完成
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    private void prepareScroll(int x, int y) {
        int dx = x - scroller.getFinalX();
        int dy = y - scroller.getFinalY();
        beginScroll(dx, dy);
    }

    private void beginScroll(int dx, int dy) {
        //第一、二个参数是起始位置，后两个参数为便宜量
        scroller.startScroll(scroller.getFinalX(), scroller.getFinalY(), dx, dy);
        invalidate();
    }

    private class GestureListenerImpl implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int dy = (int) ((distanceY - 0.5)/2);
            beginScroll(0, dy);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
