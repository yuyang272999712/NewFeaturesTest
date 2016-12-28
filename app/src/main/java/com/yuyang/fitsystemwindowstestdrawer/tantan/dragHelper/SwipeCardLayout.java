package com.yuyang.fitsystemwindowstestdrawer.tantan.dragHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
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

    private BaseAdapter mAdapter;//数据集
    private View mTopCard = null;//最顶层的card
    private AdapterDataSetObserver mDataSetObserver;//数据监听
    private boolean mInLayout = false;//是否正在绘制布局
    private onFlingListener mFlingListener;//左右滑出、加载数据的监听
    private FlingCardListener flingCardListener;

    //顶层card的左上坐标
    private int cardTop;
    private int cardLeft;
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
                if (event.getPointerId(0) == activePointerId){
                    float y = event.getY();
                    if(y < halfWidth){//判断点击的是frame的上半部分
                        touchPosition = TOUCH_ABOVE;
                    }else {//判断点击的是frame的下半部分
                        touchPosition = TOUCH_BELOW;
                    }
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                mViewDragHelper.settleCapturedViewAt(cardLeft, cardTop);
                invalidate();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                int offset = left - cardLeft;
                float rotation = ROTATION_DEGREES * 2.0F * offset / parentWidth;
                if(touchPosition == TOUCH_BELOW){//如果点击的是下半部分，旋转角度为负
                    rotation = -rotation;
                }
                changedView.setRotation(rotation);
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
        }
    }

    public void setAdapter(BaseAdapter adapter){
        if (adapter != null) {
            this.mAdapter = adapter;
            layoutCards();
        }
    }

    /**
     * 布局Cards
     */
    private void layoutCards() {
        mInLayout = true;
        for (int i=0; i<Math.min(mAdapter.getCount(), MAX_VISIBLE); i++){
            View cardView = null;
            if (cacheItems.size() > 0){
                cardView = cacheItems.get(0);
                cacheItems.remove(0);
            }
            cardView = mAdapter.getView(i, cardView, this);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) cardView.getLayoutParams();
            addViewInLayout(cardView, 0, lp, true);

            boolean needToMeasure = cardView.isLayoutRequested();
            if(needToMeasure && lp!=null){
                int childWidthSpec = getChildMeasureSpec(getMeasuredWidth(),
                        getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin,
                        lp.width);
                int childHeightSpec = getChildMeasureSpec(getMeasuredHeight(),
                        getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin,
                        lp.height);
                cardView.measure(childWidthSpec, childHeightSpec);
            }else {
                cleanupLayoutState(cardView);
            }
            int w = cardView.getMeasuredWidth();
            int h = cardView.getMeasuredHeight();
            int childLeft = (getWidth() + getPaddingLeft() - getPaddingRight() - w)/2 +
                    lp.leftMargin - lp.rightMargin;
            int childTop = getPaddingTop() + lp.topMargin;

            cardView.layout(childLeft, childTop, childLeft + w, childTop + h);

            // 缩放层叠效果
            adjustChildView(cardView, i);

            if (i == 0){
                mTopCard = cardView;
                //获取初始位置的值
                cardLeft = mTopCard.getLeft();
                cardTop = mTopCard.getTop();
            }
        }
        mInLayout = false;
    }

    private void adjustChildView(View child, int index) {
        if (index > -1 && index < MAX_VISIBLE) {
            int multiple;
            if (index > 2) {
                multiple = 2;
            } else {
                multiple = index;
            }
            //child.offsetTopAndBottom(yOffsetStep * multiple);
            child.setTranslationY(yOffsetStep * multiple);
            child.setScaleX(1 - SCALE_STEP * multiple);
            child.setScaleY(1 - SCALE_STEP * multiple);
        }
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

    public interface OnItemClickListener {
        public void onItemClicked(int itemPosition, Object dataObject);
    }

    public interface onFlingListener {
        /**
         * 移除第一个adapter
         */
        public void removeFirstObjectInAdapter();

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
