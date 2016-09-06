package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.eventConflict_2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 模拟发生冲突的child_view
 */
public class ListViewEx2 extends ListView {
    private static final String TAG = "ListViewEx2";

    private HorizontalScrollViewEx2 mHorizontalScrollViewEx2;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public ListViewEx2(Context context) {
        super(context);
    }

    public ListViewEx2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHorizontalScrollViewEx2(HorizontalScrollViewEx2 horizontalScrollViewEx2) {
        mHorizontalScrollViewEx2 = horizontalScrollViewEx2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //不允许父控件拦截
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
