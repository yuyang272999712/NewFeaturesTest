package com.yuyang.fitsystemwindowstestdrawer.horizontalFling;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by yuyang on 16/2/3.
 * 左滑现实更多
 */
public class HorizontalFlingLayout extends LinearLayout {
    private Scroller mScroller;

    private View mLeftView;
    private View mRightView;

    private float mInit;
    private float mInitX, mInitY;
    private float mOffsetX, mOffsetY;

    public HorizontalFlingLayout(Context context) {
        this(context, null);
    }

    public HorizontalFlingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalFlingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOrientation(LinearLayout.HORIZONTAL);

        mScroller = new Scroller(getContext(), null, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(getChildCount() != 2){
            throw new RuntimeException("Only need two child view! Please check you xml file!");
        }

        mLeftView = getChildAt(0);
        mRightView = getChildAt(1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("fuck--------", "HorizontalFlingLayout dispatchTouchEvent-- action=" + ev.getAction());
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mInit = ev.getX();
                mInitX = ev.getX();
                mInitY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mOffsetX = ev.getX() - mInitX;
                mOffsetY = ev.getY() - mInitY;
                //横向手势跟随移动
//                if(Math.abs(mOffsetX) - Math.abs(mOffsetY) > ViewConfiguration.getTouchSlop()){
                    int offset = (int) -mOffsetX;
                    if(getScaleX() + offset > mRightView.getWidth() || getScaleX() + offset < 0){

                    }else {
                        this.scrollBy(offset, 0);
                        mInitX = ev.getX();
                        mInitY = ev.getY();
                    }
                    break;
//                }
//                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //松开手时滑动
                int offset1 = ((getScrollX() / (float)mRightView.getWidth()) > 0.5) ? mRightView.getWidth() : 0;
                mScroller.startScroll(this.getScrollX(), this.getScrollY(), offset1 - this.getScrollX(), 0);
                invalidate();
                mInitX = 0;
                mInitY = 0;
                mOffsetX = 0;
                mOffsetY = 0;
                if (ev.getX() != mInit){
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
