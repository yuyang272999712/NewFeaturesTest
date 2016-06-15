package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.dragHelperViews;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 使用ViewDragHelper实现系统的DrawerLayout布局
 */
public class MyDrawerLayout extends ViewGroup {
    private static final int MIN_DRAWER_MARGIN = 64;//menu距离右侧的最小距离
    private static final int MIN_FLING_VELOCITY = 400;//水平拖拽的最小速率
    private int mMinDrawerMargin;//menu距离右侧的最小距离
    private View mLeftMenuView;
    private View mContentView;
    private ViewDragHelper mHelper;
    private float mLeftMenuOnScreen;//drawer显示出来的占自身的百分比

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMinDrawerMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIN_DRAWER_MARGIN, getResources().getDisplayMetrics());;
        float minVel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIN_FLING_VELOCITY, getResources().getDisplayMetrics());

        mHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.e("--yuyang--","tryCaptureView");
                return child == mLeftMenuView;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                Log.e("--yuyang--","getViewHorizontalDragRange");
                return child == mLeftMenuView ? child.getWidth():0;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.e("--yuyang--","clampViewPositionHorizontal");
                int newLeft = Math.max(-child.getWidth(), Math.min(left, 0));
                return newLeft;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                Log.e("--yuyang--","onViewReleased");
                mHelper.captureChildView(mLeftMenuView, pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.e("--yuyang--","onViewReleased");
                int childWidth = releasedChild.getWidth();
                //滑出所占总宽度的百分比
                float offset = (childWidth + releasedChild.getLeft())*1.0f/childWidth;
                mHelper.settleCapturedViewAt(xvel>0 || xvel==0&&offset>0.5f ? 0:-childWidth, releasedChild.getTop());
                invalidate();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                Log.e("--yuyang--","onViewPositionChanged");
                int childWidth = changedView.getWidth();
                float offset = (childWidth + changedView.getLeft())*1.0f/childWidth;
                mLeftMenuOnScreen = offset;
                changedView.setVisibility(offset==0?INVISIBLE:VISIBLE);
                invalidate();
            }
        });
        //设置左边界拖拽
        mHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        //设置最小拖拽速率
        mHelper.setMinVelocity(minVel);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mContentView = getChildAt(0);
        MarginLayoutParams layoutParams = (MarginLayoutParams) mContentView.getLayoutParams();
        int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize-layoutParams.leftMargin-layoutParams.rightMargin, MeasureSpec.EXACTLY);
        int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize-layoutParams.topMargin-layoutParams.bottomMargin, MeasureSpec.EXACTLY);
        mContentView.measure(contentWidthSpec, contentHeightSpec);

        mLeftMenuView = getChildAt(1);
        layoutParams = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
        int menuWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                mMinDrawerMargin+layoutParams.leftMargin+layoutParams.rightMargin,
                layoutParams.width);
        int menuHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                layoutParams.topMargin+layoutParams.bottomMargin,
                layoutParams.height);
        mLeftMenuView.measure(menuWidthSpec, menuHeightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams) mContentView.getLayoutParams();
        mContentView.layout(lp.leftMargin, lp.topMargin,
                lp.leftMargin+mContentView.getMeasuredWidth(),
                lp.topMargin+mContentView.getMeasuredHeight());

        lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
        int menuWidth = mLeftMenuView.getMeasuredWidth();
        int chileLeft = -menuWidth+(int)(menuWidth*mLeftMenuOnScreen);
        mLeftMenuView.layout(chileLeft, lp.topMargin,
                chileLeft+menuWidth, lp.topMargin+mLeftMenuView.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public boolean getOpenState(){
        return mLeftMenuOnScreen == 1.0f;
    }

    public void openDrawer(){
        mLeftMenuOnScreen = 1.0f;
        mHelper.smoothSlideViewTo(mLeftMenuView, 0, mLeftMenuView.getTop());
        invalidate();
    }

    public void closeDrawer(){
        mLeftMenuOnScreen = 0f;
        mHelper.smoothSlideViewTo(mLeftMenuView, -mLeftMenuView.getWidth(), mLeftMenuView.getTop());
        invalidate();
    }
}
