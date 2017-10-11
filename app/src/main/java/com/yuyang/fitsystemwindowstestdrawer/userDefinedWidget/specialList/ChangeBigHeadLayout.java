package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动顶部item增大显示的list
 */

public class ChangeBigHeadLayout extends FrameLayout {
    private int animationTime = 400;//动画执行时长
    private float resistance = 0.8f;//阻尼系数
    private int minVelocity = 350;//最小滑动速率

    private boolean isInitFinish;//是否初始化完成

    private int touchSlop;//滑动判断（大于这个值就认为是滑动）
    private int mWidth, mHeight;//控件宽高
    private int childTotalHeight;//折叠状态下总高度
    private int childExpandedHeight;//adapterItem展开高度
    private int childNormalHeight;//adapterItem折叠高度

    private Adapter mAdapter;
    private final Scroller mScroller;
    private final GestureDetector mGestureDetector;//手势监听
    private final RelativeLayout layBottomContainer;//底部ViewGroup（用于显示没有更多adapter的提示）

    private List<OnScrollChangedListener> scrollCallbacks = new ArrayList<>();//存储所有Item的滑动回调

    private boolean isAnimMoving;//是否在动画过程中
    private boolean isTouching;//是否在触摸过程中
    private boolean isFling;//是否在fling过程中
    private float interceptDownY;
    private float touchDownY;//手指放下时事件的Y坐标
    private int touchDownScrollY;//手指放下时的竖直方向滚动的值
    private int touchUpScrollY;//手指抬起时的Y值

    private int realIndex;//实际的index
    private float realPercent;//实际滚动的百分比

    public ChangeBigHeadLayout(@NonNull Context context) {
        this(context, null);
    }

    public ChangeBigHeadLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeBigHeadLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layBottomContainer = new RelativeLayout(context);
        mScroller = new Scroller(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetectorWithOnUp(context, new GestureListenerWithOnUp());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        childExpandedHeight = (int) (mWidth / 588f * 440);
        childNormalHeight = (int) (mWidth / 588f * 144);

        if (!isInitFinish) {
            dataSetChanged();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                interceptDownY = ev.getRawY();
                if (isAnimMoving){//如果正在动画的执行过程中
                    isAnimMoving = false;//停止动画
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getRawY()-interceptDownY) > touchSlop){//如果滑动距离大于最小滑动距离
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        return intercept;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            if (isFling && mScroller.getCurrVelocity() < minVelocity){//如果滚动的速率小于最小速率了，则停止滚动
                isFling = false;
                mScroller.forceFinished(true);
                int currIndex = realIndex + (realPercent>0.5f?1:0);//当前的index
                int flingAbortScrollY = mScroller.getCurrY();//当前的Y值
                scrollerStartScroll(flingAbortScrollY, -flingAbortScrollY + currIndex * childNormalHeight, animationTime);
                return;
            }

            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            float scrollResult = mScroller.getCurrY();
            float percent = scrollResult / childNormalHeight;
            realIndex = (int) percent;
            realPercent = percent % 1;
            if (realIndex < 0){
                realIndex = 0;
            }
            if (realPercent < 0){
                realPercent = 0;
            }
            for (OnScrollChangedListener c:scrollCallbacks){
                c.onScroll(realIndex, realPercent);
            }
            postInvalidate();
            isAnimMoving = true;
        }else {
            isAnimMoving = false;
        }
        super.computeScroll();
    }

    /*-------------------------------------自定义方法-----------------------------*/
    /**
     * 用于手指抬起后内容的滚动，以便最后显示在完整位置
     * @param startY
     * @param dy
     * @param duration
     */
    private void scrollerStartScroll(int startY, int dy, int duration) {
        isAnimMoving = true;
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();
    }

    /**
     * 数据发生变化后填充视图
     */
    private void dataSetChanged(){
        if (mWidth * mHeight < 1) {
            isInitFinish = false;
            return;
        }
        isInitFinish = true;

        final int dataSize = mAdapter.getCount();
        childTotalHeight = (dataSize < 1 ? 0 : childNormalHeight * (dataSize - 1));

        scrollCallbacks.clear();
        removeAllViewsInLayout();
        for (int i = 0; i < dataSize; i++) {
            RelativeLayout retailMeNotView = new RelativeLayout(getContext());
            View itemView = mAdapter.getView(i, retailMeNotView, childExpandedHeight, childNormalHeight);

            if (!(itemView instanceof OnScrollChangedListener))
                throw new IllegalArgumentException("adapter getView return child -> must impl RetailMeNotLayout.OnScrollChangedListener");
            else
                registerScrollChangedCallback((OnScrollChangedListener) itemView);

            retailMeNotView.addView(itemView);
            retailMeNotView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, childExpandedHeight));
            retailMeNotView.setTranslationY(i * childNormalHeight);
            addView(retailMeNotView);
        }

        layBottomContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight - (dataSize < 1 ? 0 : childExpandedHeight)));
        int bottomTranslateY;
        if (dataSize < 1) {
            bottomTranslateY = 0;
        } else if (dataSize == 1) {
            bottomTranslateY = childExpandedHeight;
        } else {
            bottomTranslateY = childExpandedHeight + childNormalHeight * (dataSize - 1);
        }

        layBottomContainer.setTranslationY(bottomTranslateY);
        layBottomContainer.setBackgroundColor(Color.BLUE);
        addView(layBottomContainer);
    }

    /**
     * 为每个item注册滑动回调
     * @param callback
     */
    private void registerScrollChangedCallback(OnScrollChangedListener callback) {
        if (!scrollCallbacks.contains(callback)) {
            scrollCallbacks.add(callback);
        }
    }

    /**
     * 添加滑动到最底部的视图
     * @param vBottomContent
     */
    public void addBottomContent(View vBottomContent) {
        layBottomContainer.addView(vBottomContent);
    }

    /**
     * 设置适配器
     * @param adapter
     */
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mAdapter.mHolder = this;
        }
    }

    /*---------------------------------------内部类------------------------*/
    /**
     * item适配器
     */
    public static abstract class Adapter {
        private ChangeBigHeadLayout mHolder;

        public abstract int getCount();

        public void notifyDataSetChanged() {
            mHolder.dataSetChanged();
        }

        public abstract View getView(int position, ViewGroup parent, int expendedHeight, int normalHeight);
    }

    /**
     * 滑动过程监听类
     */
    public interface OnScrollChangedListener {
        /**
         * 滑动回调，用于item动画
         * @param currIndex 变化item index
         * @param percent 百分比
         */
        void onScroll(int currIndex, float percent);
    }

    /**
     * 手势监听
     */
    private class GestureListenerWithOnUp extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            isTouching = true;
            isFling = false;
            touchDownScrollY = getScrollY();
            touchDownY = e.getRawY();
            mScroller.forceFinished(true);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!isTouching){
                isTouching = true;
                isFling = false;
                touchDownScrollY = getScrollY();
                touchDownY = e2.getRawY();
                mScroller.forceFinished(true);
            }else {
                float offset = (e2.getRawY() - touchDownY) * resistance;
                float scrollResult = touchDownScrollY - offset;

                int touchMoveScrollY = getScrollY();
                if (touchMoveScrollY < 0) {
                    scrollTo(0, (int) scrollResult / 2);
                } else if (touchMoveScrollY > childTotalHeight) {
                    scrollTo(0, childTotalHeight + (int) (scrollResult - childTotalHeight) / 2);
                } else {
                    scrollTo(0, (int) scrollResult);
                }

                float percent = scrollResult / childNormalHeight;
                realIndex = (int) (percent);
                realPercent = percent % 1;
                if (realIndex < 0) realIndex = 0;
                if (realPercent < 0) realPercent = 0;
                for (OnScrollChangedListener c : scrollCallbacks) {
                    c.onScroll(realIndex, realPercent);
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            isFling = true;
            touchUpScrollY = getScrollY();
            if (touchUpScrollY < 0) {
                scrollerStartScroll(touchUpScrollY, 0 - touchUpScrollY, animationTime);
            } else if (touchUpScrollY > childTotalHeight) {
                scrollerStartScroll(touchUpScrollY, childTotalHeight - touchUpScrollY, animationTime);
            } else {
                if (Math.abs(velocityY) > minVelocity) {
                    isAnimMoving = true;
                    mScroller.fling(0, touchUpScrollY, 0, (int) -velocityY, 0, 0, 0, childTotalHeight);
                    computeScroll();
                } else {
                    int currIndex = realIndex + (realPercent > 0.5f ? 1 : 0);
                    scrollerStartScroll(touchUpScrollY, -touchUpScrollY + currIndex * childNormalHeight, animationTime);
                }
            }
            return true;
        }

        public boolean onUp(MotionEvent e) {
            isTouching = false;
            touchUpScrollY = getScrollY();
            if (!isFling) {
                if (touchUpScrollY < 0) {
                    scrollerStartScroll(touchUpScrollY, 0 - touchUpScrollY, animationTime);
                } else if (touchUpScrollY > childTotalHeight) {
                    scrollerStartScroll(touchUpScrollY, childTotalHeight - touchUpScrollY, animationTime);
                } else {
                    int currIndex = realIndex + (realPercent > 0.5f ? 1 : 0);
                    scrollerStartScroll(touchUpScrollY, -touchUpScrollY + currIndex * childNormalHeight, animationTime);

                }
            }
            return false;
        }
    }

    /**
     * 手势监听特殊处理手指抬起时的事件
     */
    private class GestureDetectorWithOnUp extends GestureDetector{
        private final GestureListenerWithOnUp mListener;

        public GestureDetectorWithOnUp(Context context, GestureListenerWithOnUp listener) {
            super(context, listener);
            this.mListener = listener;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            boolean handled = super.onTouchEvent(ev);
            switch (ev.getAction()) {
                case MotionEvent.ACTION_UP:
                    mListener.onUp(ev);
                    break;
            }
            return handled;
        }
    }
}
