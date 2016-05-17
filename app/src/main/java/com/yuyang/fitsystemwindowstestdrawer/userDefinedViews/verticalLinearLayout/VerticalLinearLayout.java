package com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.verticalLinearLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * 竖直排版的引导页，类似竖直的ViewPager
 */
public class VerticalLinearLayout extends ViewGroup {
    //屏幕高度
    private int mScreenHeight;
    //手指按下时的getScrollY
    private int mScrollStart;
    //手指抬起时的getScrollY
    private int mScrollEnd;
    //记录当前的Y，手指滑动过程中不断变化
    private int mLastY;
    //滚动辅助类
    private Scroller mScroller;
    //是否正在滚动
    private boolean isScrolling;
    //加速检测
    private VelocityTracker mVelocityTracker;
    //记录当前页
    private int currentPage = 0;
    //page切换监听
    private OnPageChangeListener mOnPageChangeListener;

    public VerticalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 获取屏幕高度
         */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        /**
         * 初始化滚动辅助
         */
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0; i<getChildCount(); i++){
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int childCount = getChildCount();
            /**
             * 设置主布局高度
             */
            MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
            layoutParams.height = mScreenHeight * childCount;
            setLayoutParams(layoutParams);

            for (int i=0; i<childCount; i++){
                View view = getChildAt(i);
                if(view.getVisibility() == VISIBLE){
                    view.layout(l, mScreenHeight * i, r, mScreenHeight * (i + 1));
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 如果当前正在滚动，调用父类的onTouchEvent
        if(isScrolling){
            return super.onTouchEvent(event);
        }

        int action = event.getAction();
        int y = (int) event.getY();

        obtainVelocity(event);

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mScrollStart = getScrollY();
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                int dy = mLastY - y;
                // 边界值检查
                int scrollY = getScrollY();
                // 已经到达顶端
                if (dy < 0 && scrollY + dy < 0) {
                    dy = 0;
                }
                // 已经到达底部
                if (dy > 0 && scrollY + dy > getHeight() - mScreenHeight) {
                    dy = 0;
                }

                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mScrollEnd = getScrollY();

                int dScrollY = mScrollEnd - mScrollStart;

                if (wantScrollToNext()){//上滑
                    if (shouldScrollToNext()){
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                if (wantScrollToPre()){//下滑
                    if (shouldScrollToPre()){
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    }else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }
                isScrolling = true;
                postInvalidate();
                recyclerVelocity();
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }else {
            int position = getScrollY() / mScreenHeight;
            Log.i("－－VerticalLinearLayout－", position + "," + currentPage);
            if (position != currentPage) {
                if (mOnPageChangeListener != null) {
                    currentPage = position;
                    mOnPageChangeListener.onPageChange(currentPage);
                }
            }
            isScrolling = false;
        }
    }

    /**
     * 判断用户是否想滚动到下一页
     * @return
     */
    private boolean wantScrollToNext(){
        return mScrollEnd > mScrollStart;
    }

    /**
     * 判断用户是否想滚动到上一页
     * @return
     */
    private boolean wantScrollToPre(){
        return mScrollEnd < mScrollStart;
    }

    /**
     * 根据滚动距离和滑动速度判断是否应该滚动至下一页
     * @return
     */
    private boolean shouldScrollToNext(){
        return mScrollEnd - mScrollStart > mScreenHeight/2 || Math.abs(getVelocity()) > 600;
    }

    /**
     * 根据滚动距离和滑动速度判断是否应该滚动至上一页
     * @return
     */
    private boolean shouldScrollToPre(){
        return mScrollStart - mScrollEnd > mScreenHeight/2 || Math.abs(getVelocity()) > 600;
    }

    /**
     * 获取Y方向上的加速度
     * @return
     */
    private int getVelocity(){
        if(mVelocityTracker != null){
            mVelocityTracker.computeCurrentVelocity(1000);
            return (int) mVelocityTracker.getYVelocity();
        }else {
            return 0;
        }
    }

    /**
     * 释放加速检测器
     */
    private void recyclerVelocity(){
        if(mVelocityTracker != null){
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 初始化加速检测器
     * @param event
     */
    private void obtainVelocity(MotionEvent event) {
        if(mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 设置回调接口
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 回调接口
     *
     * @author zhy
     *
     */
    public interface OnPageChangeListener {
        void onPageChange(int currentPage);
    }
}
