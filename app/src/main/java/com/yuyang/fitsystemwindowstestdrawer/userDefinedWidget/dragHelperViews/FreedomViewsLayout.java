package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.dragHelperViews;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * ViewDragHelper的几种用法
 */
public class FreedomViewsLayout extends LinearLayout {
    private ViewDragHelper dragHelper;

    private View mDragView;//随意拖拽
    private View mAutoBackView;//拖拽后回弹
    private View mEdgeTrackerView;//边界拖拽
    /**
     * mAutoBackView的原始位置
     */
    private Point mAutoBackOriginPos = new Point();

    public FreedomViewsLayout(Context context) {
        this(context, null);
    }

    public FreedomViewsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FreedomViewsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

            /**
             * 当释放拖动的View时调用
             * @param releasedChild
             * @param xvel x方向加速度
             * @param yvel y方向加速度
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if(releasedChild == mAutoBackView){
                    dragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                    invalidate();
                }
            }

            /**
             * 边界拖动时调用
             * 主动通过captureChildView对其进行捕获，该方法可以绕过tryCaptureView
             * @param edgeFlags
             * @param pointerId
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                //TODO yuyang 手动设置mEdgeTrackerView为捕获对象
                dragHelper.captureChildView(mEdgeTrackerView, pointerId);
            }
        });
        /**
         * 设置左边界可拖动
         */
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}
