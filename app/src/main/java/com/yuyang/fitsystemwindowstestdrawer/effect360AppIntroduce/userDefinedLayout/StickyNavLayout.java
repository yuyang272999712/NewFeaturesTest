package com.yuyang.fitsystemwindowstestdrawer.effect360AppIntroduce.userDefinedLayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义360软件介绍页面布局
 */
public class StickyNavLayout extends LinearLayout {
    /**
     * 内部的三个主控件
     */
    private View mTop;
    private View mNav;
    private ViewPager mViewPager;
    //最上面控件mTop的高度
    private int mTopViewHeight;
    //viewPager的adapter内的滚动器，有可能是ScrollView／ListView／RecyclerView
    private ViewGroup mInnerScrollView;
    //最上面控件mTop是否隐藏了
    private boolean isTopHidden = false;
    /**
     * 滚动相关
     */
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    //区别用户是点击还是拖拽
    private int mTouchSlop;
    //最大速率
    private int mMaximumVelocity;
    //最小速率
    private int mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;
    //滑动事件是否交给内部滚动器mInnerScrollView处理
    private boolean isInControl = false;

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //竖直方向排列子控价
        setOrientation(LinearLayout.VERTICAL);

        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager)){
            throw new RuntimeException("id_stickynavlayout_viewpager必须是ViewPager");
        }
        mViewPager = (ViewPager) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //给ViewPager一个固定高度，否则他的高度可能不固定
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - mNav.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
    }

    /**
     * 当底层／外出View滑动结束后继续外出／底层的滑动
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();

                if (mInnerScrollView instanceof ScrollView) {
                    if (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0
                            && !isInControl) {
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    }
                } else if (mInnerScrollView instanceof ListView) {

                    ListView lv = (ListView) mInnerScrollView;
                    View c = lv.getChildAt(lv.getFirstVisiblePosition());

                    if (!isInControl && c != null && c.getTop() == 0 && isTopHidden
                            && dy > 0) {
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    }
                }else if (mInnerScrollView instanceof RecyclerView) {

                    RecyclerView rv = (RecyclerView) mInnerScrollView;

                    if (!isInControl && android.support.v4.view.ViewCompat.canScrollVertically(rv, -1) && isTopHidden
                            && dy > 0) {
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 对于拦截，要清楚的知道什么时候应该拦截，什么时候不需要，当前例子：
     * 1、如果顶部view只要没有完全隐藏，那么直接拦截上下的拖动；
     * 2、还有个需要拦截的地方，就是当顶部的view彻底隐藏了，现在内部的sc应该可以上下滑动了，
     *    但是如果sc滑动到顶部再往下的时候，此时又该拦截了，需要把顶部view可以下滑出来。
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y-mLastY;
                getCurrentScrollView();
                if (Math.abs(dy) > mTouchSlop){
                    mDragging = true;
                    if (mInnerScrollView instanceof ScrollView){
                        // 如果topView没有隐藏
                        // 或sc的scrollY = 0 && topView隐藏 && 下拉，则拦截
                        if (!isTopHidden
                                || (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0)){
                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }else if (mInnerScrollView instanceof ListView){
                        ListView lv = (ListView) mInnerScrollView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        // 如果topView没有隐藏
                        // 或sc的listView在顶部 && topView隐藏 && 下拉，则拦截
                        if (!isTopHidden
                                || (c!=null && c.getTop()==0 && isTopHidden && dy>0)){
                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }else if (mInnerScrollView instanceof RecyclerView){
                        RecyclerView rv = (RecyclerView) mInnerScrollView;
                        if (!isTopHidden || (!ViewCompat.canScrollVertically(rv, -1) && isTopHidden && dy > 0)) {
                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }
                }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragging = false;
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 获取当前pagerAdapter上的滚动容器
     */
    private void getCurrentScrollView(){
        int currentItem = mViewPager.getCurrentItem();
        PagerAdapter a = mViewPager.getAdapter();
        if (a instanceof FragmentPagerAdapter){
            FragmentPagerAdapter adapter = (FragmentPagerAdapter) a;
            Fragment item = (Fragment) adapter.instantiateItem(mViewPager, currentItem);
            mInnerScrollView = (ViewGroup) item.getView().findViewById(R.id.id_stickynavlayout_innerscrollview);
        }else if (a instanceof FragmentStatePagerAdapter){
            FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) a;
            Fragment item = (Fragment) adapter.instantiateItem(mViewPager, currentItem);
            mInnerScrollView = (ViewGroup) item.getView().findViewById(R.id.id_stickynavlayout_innerscrollview);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){//如果滚动还没完成那就停止滚动
                    mScroller.abortAnimation();
                }
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                if (!mDragging && Math.abs(dy)>mTouchSlop){
                    mDragging = true;
                }
                if (mDragging){//如果是在拖动
                    scrollBy(0, (int) -dy);
                    // 如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
                    if (getScrollY() == mTopViewHeight && dy<0){
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                    }
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity){
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 滑动～～～～
     * @param velocityY
     */
    private void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0){
            y = 0;
        }
        if (y > mTopViewHeight){
            y = mTopViewHeight;
        }
        if (y != getScrollY()){
            super.scrollTo(x, y);
        }
        isTopHidden = getScrollY() == mTopViewHeight;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 检测mVelocityTracker是否为空
     */
    private void initVelocityTrackerIfNotExists(){
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    /**
     * 释放mVelocityTracker
     */
    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
