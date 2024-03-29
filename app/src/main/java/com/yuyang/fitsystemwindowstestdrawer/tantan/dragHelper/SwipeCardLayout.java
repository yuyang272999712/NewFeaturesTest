package com.yuyang.fitsystemwindowstestdrawer.tantan.dragHelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

import java.util.ArrayList;

/**
 * 探探效果的布局
 */

public class SwipeCardLayout extends FrameLayout {
    private static final String TAG = "SwipeCardLayout";

    private ViewDragHelper mViewDragHelper;//滑动帮助累
    private ArrayList<View> cacheItems = new ArrayList<>();//View缓存
    //缩放层叠效果
    private int yOffsetStep = DensityUtils.dp2px(getContext(), 30); // view叠加垂直偏移量的步长
    private static final float SCALE_STEP = 0.08f; // view叠加缩放的步长
    private float ROTATION_DEGREES = 10.f;//倾斜角度
    private int MAX_VISIBLE = 4;//叠加几个card

    private CardAdapter mAdapter;//数据集
    private View mTopCard = null;//最顶层的card
    private AdapterDataSetObserver mDataSetObserver;//数据监听
    private boolean mInLayout = false;//是否正在绘制布局
    private OnFlingListener mFlingListener;//滑动、左右滑出、加载数据的监听

    //顶层card的初始左上坐标
    private int cardTop;
    private int cardLeft;
    //顶层card移动后的左上坐标
    private int cardMovedLeft;
    private int cardMovedTop;
    //顶层card的宽高
    private int cardW;
    private int cardH;

    /*
     * ACTION_DOWN事件相对于card的位置（点击的是card的上部还是下部）
     * 赋值给touchPosition 做记录
     */
    private final int TOUCH_ABOVE = 0;
    private final int TOUCH_BELOW = 1;
    private int touchPosition;
    //最多旋转角度
    private float MAX_COS = (float) Math.cos(Math.toRadians(45.0D));
    //card父控件宽
    private int parentWidth;
    //card控件宽的一半
    private float halfWidth;
    //被拦截的事件
    private MotionEvent event;

    public SwipeCardLayout(Context context) {
        this(context, null);
    }

    public SwipeCardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeFlingAdapterView, defStyleAttr, 0);
        MAX_VISIBLE = typedArray.getInt(R.styleable.SwipeFlingAdapterView_max_visible, MAX_VISIBLE);
        ROTATION_DEGREES = typedArray.getFloat(R.styleable.SwipeFlingAdapterView_rotation_degrees, 15f);
        yOffsetStep = typedArray.getDimensionPixelOffset(R.styleable.SwipeFlingAdapterView_y_offset_step, yOffsetStep);
        typedArray.recycle();

        mDataSetObserver = new AdapterDataSetObserver();
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                if (!mInLayout) {
                    return child == mTopCard;
                }
                return false;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                if (!mInLayout) {
                    cardMovedLeft = capturedChild.getLeft();
                    cardLeft = capturedChild.getLeft();
                    cardTop = capturedChild.getTop();
                    cardW = capturedChild.getWidth();
                    cardH = capturedChild.getHeight();
                    if (event.getPointerId(0) == activePointerId) {
                        float y = event.getY();
                        if (y < halfWidth) {//判断点击的是frame的上半部分
                            touchPosition = TOUCH_ABOVE;
                        } else {//判断点击的是frame的下半部分
                            touchPosition = TOUCH_BELOW;
                        }
                    }
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                mInLayout = true;
                if (movedBeyondLeftBorder()){
                    onSelected(true, getExitPoint(-cardW), 100L, false);
                    adjustChildrenOfUnderTopView(1.0f);
                    callbackScroll(getScrollProgressPercent());
                }else if (movedBeyondRightBorder()){
                    onSelected(false, getExitPoint(parentWidth), 100L, false);
                    adjustChildrenOfUnderTopView(1.0f);
                    callbackScroll(getScrollProgressPercent());
                }else {
                    mViewDragHelper.settleCapturedViewAt(cardLeft, cardTop);
                    invalidate();
                }
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                cardMovedLeft = left;
                cardMovedTop = top;
                int offset = left - cardLeft;
                float rotation = ROTATION_DEGREES * 2.0F * offset / parentWidth;
                if(touchPosition == TOUCH_BELOW){//如果点击的是下半部分，旋转角度为负
                    rotation = -rotation;
                }
                changedView.setRotation(rotation);
                //topCard下面的View缩放动画
                adjustChildrenOfUnderTopView(getScrollProgress());
                //回调滑动监听,以便更新界面元素
                callbackScroll(getScrollProgressPercent());
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        parentWidth = getWidth();
        halfWidth = parentWidth/2.0f;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)){
            invalidate();
        }else {
            mInLayout = false;
        }
    }

    /**
     * 设置显示数据
     * @param adapter
     */
    public void setAdapter(CardAdapter adapter){
        if (adapter != null) {
            this.mAdapter = adapter;
            this.mAdapter.registerDataSetObserver(mDataSetObserver);
            layoutCards();
        }
    }

    /**
     * 布局Cards
     */
    private void layoutCards() {
        Log.i(TAG, "layoutCards");
        if (mAdapter == null){
            return;
        }
        for (int i=0; i<Math.min(mAdapter.getCount(), MAX_VISIBLE); i++){
            View cardView = null;
            if (cacheItems.size() > 0){
                cardView = cacheItems.get(0);
                cacheItems.remove(0);
            }
            cardView = mAdapter.getView(i, cardView, this);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) cardView.getLayoutParams();
            addViewInLayout(cardView, 0, lp, true);

            // 缩放层叠效果
            adjustChildView(cardView, i);

            if (i == 0){
                mTopCard = cardView;
            }
        }
    }

    /**
     * 调整布局，使card实现层叠效果
     * @param child
     * @param index
     */
    private void adjustChildView(View child, int index) {
        if (index > -1 && index < MAX_VISIBLE) {
            int multiple;
            if (index > 2) {
                multiple = 2;
            } else {
                multiple = index;
            }
            child.setTranslationY(yOffsetStep * multiple);
            child.setScaleX(1 - SCALE_STEP * multiple);
            child.setScaleY(1 - SCALE_STEP * multiple);
        }
    }

    /**
     * 处理topCard下面的View动画显示
     * @param scrollRate
     */
    private void adjustChildrenOfUnderTopView(float scrollRate) {
        int count = getChildCount();
        if (count > 1) {
            int multiple = 1;
            float rate = Math.abs(scrollRate);
            for (int i=1; i<3; i++, multiple++){//最有一个是topCard
                View underView = getChildAt(count-1-i);
                if (underView != null){
                    int offset = (int) (yOffsetStep * (multiple - rate));
                    underView.setTranslationY(offset - underView.getTop() + cardTop);
                    underView.setScaleX(1 - SCALE_STEP * multiple + SCALE_STEP * rate);
                    underView.setScaleY(1 - SCALE_STEP * multiple + SCALE_STEP * rate);
                }
            }
        }
    }

    /**
     * 移除当前的topCard，并添加新的card
     */
    private void updateTopCardAndAddNewCard(){
        //TODO yuyang view缓存没做好
        detachViewFromParent(mTopCard);
        mTopCard = getChildAt(getChildCount()-1);//更新topCard引用
        mAdapter.remove(0);//移除数据
        if (mAdapter.getCount() <= MAX_VISIBLE+1 && mFlingListener!=null){
            mFlingListener.onAdapterAboutToEmpty(mAdapter.getCount());
        }
        if (mAdapter.getCount() >= MAX_VISIBLE){
            View cardView = null;
            if (cacheItems.size() > 0){
                cardView = cacheItems.get(0);
                cacheItems.remove(0);
            }
            cardView = mAdapter.getView(MAX_VISIBLE-1, cardView, this);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) cardView.getLayoutParams();
            addViewInLayout(cardView, 0, lp, true);

            // 缩放层叠效果
            adjustChildView(cardView, MAX_VISIBLE-1);
        }
    }

    /**
     * 选中的card的选中结果，左滑出或者右滑出
     * @param isLeft 是否是左滑
     * @param exitY 滑出至位置，Y轴坐标
     * @param duration 动画持续时间
     * @param left_right_select 是否是外部点击执行左／右滑出
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onSelected(final boolean isLeft, float exitY, long duration, final boolean left_right_select){
        if (mTopCard == null){
            return;
        }
        mInLayout = true;
        float exitX;//滑出的位置x轴坐标
        if(isLeft){
            exitX = (float)(-cardW) - this.getRotationWidthOffset();
        }else {
            exitX = (float)parentWidth + this.getRotationWidthOffset();
        }

        mTopCard.animate().setDuration(duration)
                .setInterpolator(new AccelerateInterpolator())
                .x(exitX)
                .y(exitY)
                .setListener(
                        new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (mFlingListener != null) {
                                    if (isLeft) {
                                        mFlingListener.onLeftCardExit(mAdapter.getItem(0));
                                    } else {
                                        mFlingListener.onRightCardExit(mAdapter.getItem(0));
                                    }
                                }
                                updateTopCardAndAddNewCard();
                                mInLayout = false;
                            }
                        })
                .rotation(getExitRotation(isLeft))
                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (left_right_select) {
                            long total = animation.getDuration();
                            long current = animation.getCurrentPlayTime();
                            adjustChildrenOfUnderTopView((float) current / total);
                            callbackScroll(0);
                        }
                    }
                });
    }

    /**
     * 回调滑动监听
     * @param scrollProgressPercent
     */
    private void callbackScroll(float scrollProgressPercent){
        if (mFlingListener != null){
            mFlingListener.onScroll(scrollProgressPercent);
        }
    }

    /**
     * 返回x、y方向上的总位移比例
     * @return
     */
    private float getScrollProgress() {
        float dx = cardMovedLeft - cardLeft;
        float dy = cardMovedTop - cardTop;
        float dis = Math.abs(dx) + Math.abs(dy);
        return Math.min(dis, 400f) / 400f;
    }

    /**
     * 返回X轴方向的位移比例（距离判断滑出的百分比）
     * @return
     */
    private float getScrollProgressPercent() {
        if(this.movedBeyondLeftBorder()){
            return -1.0F;
        }else if(this.movedBeyondRightBorder()){
            return 1.0F;
        }else {
            float zeroToOneValue = (cardLeft + this.halfWidth - this.leftBorder()) / (this.rightBorder() - this.leftBorder());
            return zeroToOneValue * 2.0F - 1.0F;
        }
    }

    /**
     * 右侧滑出
     * @return 是否超过右边界
     */
    private boolean movedBeyondRightBorder() {
        return cardMovedLeft + this.halfWidth > rightBorder();
    }

    /**
     * 左侧滑出
     * @return 是否超过左边界
     */
    private boolean movedBeyondLeftBorder() {
        return cardMovedLeft + this.halfWidth < leftBorder();
    }

    /**
     * 左滑边界值（当frame的中点在父控件宽度的四分之一左侧时，左滑出）
     * @return
     */
    private float leftBorder(){
        return (float)this.parentWidth / 4.0F;
    }

    /**
     * 同上
     * @return
     */
    private float rightBorder(){
        return (float)this.parentWidth * 3 / 4.0F;
    }

    /**
     * 旋转最大角度45度后，粗略计算宽度增加的长度
     * @return
     */
    private float getRotationWidthOffset() {
        return (float) this.cardW / this.MAX_COS - (float) this.cardW;
    }

    /**
     * 根据是否左滑与水平方向上滑动距离计算旋转角度
     * @param isLeft
     * @return
     */
    private float getExitRotation(boolean isLeft) {
        float rotation = ROTATION_DEGREES * 2.0F * ((float) this.parentWidth - this.cardLeft) / (float) this.parentWidth;
        if (this.touchPosition == this.TOUCH_BELOW) {
            rotation = -rotation;
        }

        if (isLeft) {
            rotation = -rotation;
        }

        return rotation;
    }

    /**
     * 根据frame的左上角坐标与移动后的左上角坐标的线性回归函数，计算Y轴坐标（具体数学计算不懂啊！！）
     * @param exitXPoint
     * @return
     */
    private float getExitPoint(int exitXPoint) {
        float[] x = new float[]{this.cardLeft, this.cardMovedLeft};
        float[] y = new float[]{this.cardTop, this.cardMovedTop};
        LinearRegression regression = new LinearRegression(x, y);
        return (float) regression.slope() * (float) exitXPoint + (float) regression.intercept();
    }

    /**
     * 观察者，更新数据改变
     */
    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        this.event = event;
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    public void setFlingLIstener(OnFlingListener listener){
        this.mFlingListener = listener;
    }

    public interface OnFlingListener {
        /**
         * 左滑item
         * @param dataObject 该adapter内容
         */
        public void onLeftCardExit(Object dataObject);

        /**
         * 右滑item
         * @param dataObject 该adapter内容
         */
        public void onRightCardExit(Object dataObject);

        /**
         * 当adapter剩余数量少于MAX_VISIBLE后调用该方法
         * （有可能是该通过网络获取新数据了）
         * @param itemsInAdapter 当前adapter中剩余数量
         */
        public void onAdapterAboutToEmpty(int itemsInAdapter);

        /**
         * 活动过程中动作处理（例如图片的渐渐显示效果）
         * @param scrollProgressPercent 滑动百分比
         */
        public void onScroll(float scrollProgressPercent);
    }
}
