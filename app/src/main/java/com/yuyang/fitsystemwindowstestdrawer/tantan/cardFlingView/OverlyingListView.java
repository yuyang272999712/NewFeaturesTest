package com.yuyang.fitsystemwindowstestdrawer.tantan.cardFlingView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;

/**
 * Created by yuyang on 16/3/11.
 * 叠加式的listview，卡片形式
 */
public class OverlyingListView extends BaseFlingAdapterView {
    private static final String TAG = "yuyang________";

    //View缓存
    private ArrayList<View> cacheItems = new ArrayList<>();

    //缩放层叠效果
    private int yOffsetStep = 29; // view叠加垂直偏移量的步长
    private static final float SCALE_STEP = 0.08f; // view叠加缩放的步长
    //缩放层叠效果

    private int MAX_VISIBLE = 4;
    private int MIN_ADAPTER_STACK = 6;
    private float ROTATION_DEGREES = 10.f;

    private int LAST_OBJECT_IN_STACK = 0;
    private BaseAdapter mAdapter;
    private View mActiveCard = null;//最顶层的card
    private AdapterDataSetObserver mDataSetObserver;
    private boolean mInLayout = false;//是否正在绘制布局
    private OnItemClickListener mOnItemClickListener;
    private FlingCardListener flingCardListener;
    private onFlingListener mFlingListener;

    private int initTop;
    private int initLeft;

    public OverlyingListView(Context context) {
        this(context, null);
    }

    public OverlyingListView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SwipeFlingStyle);
    }

    public OverlyingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeFlingAdapterView, defStyleAttr, 0);
        MAX_VISIBLE = typedArray.getInt(R.styleable.SwipeFlingAdapterView_max_visible, MAX_VISIBLE);
        MIN_ADAPTER_STACK = typedArray.getInt(R.styleable.SwipeFlingAdapterView_min_adapter_stack, MIN_ADAPTER_STACK);
        ROTATION_DEGREES = typedArray.getFloat(R.styleable.SwipeFlingAdapterView_rotation_degrees, 15f);
        yOffsetStep = typedArray.getDimensionPixelOffset(R.styleable.SwipeFlingAdapterView_y_offset_step, 29);
        typedArray.recycle();
    }

    private void removeAndAddToCache(int remain) {
        View view;
        for (int i = 0; i < getChildCount() - remain; ) {
            view = getChildAt(i);
            removeViewInLayout(view);
            cacheItems.add(view);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(mAdapter == null){
            return;
        }
        Log.i(TAG, "自定义控件OverLyingListView onLayout" + System.currentTimeMillis());
        mInLayout = true;

        int adapterCount = mAdapter.getCount();
        if(adapterCount == 0){
            //removeAllViewsInLayout();
            removeAndAddToCache(0);
        }else {
            View topCard = getChildAt(LAST_OBJECT_IN_STACK);
            if(mActiveCard != null && topCard != null && topCard == mActiveCard) {
                //removeViewsInLayout(0, LAST_OBJECT_IN_STACK);
                removeAndAddToCache(1);
                layoutChildren(1, adapterCount);
            }else{
                //removeAllViewsInLayout();
                removeAndAddToCache(0);
                layoutChildren(0, adapterCount);
                setTopView();
            }
        }

        mInLayout = false;

        if (initTop == 0 && initLeft == 0 && mActiveCard != null) {
            initTop = mActiveCard.getTop();
            initLeft = mActiveCard.getLeft();
        }

        if(adapterCount < MAX_VISIBLE){
            mFlingListener.onAdapterAboutToEmpty(adapterCount);
        }
    }

    private void layoutChildren(int startingIndex, int adapterCount) {
        while (startingIndex < Math.min(adapterCount, MAX_VISIBLE)){
            //View newCard = mAdapter.getView(startingIndex, null, this);
            View item = null;
            if (cacheItems.size() > 0) {
                item = cacheItems.get(0);
                cacheItems.remove(item);
            }
            View newCard = mAdapter.getView(startingIndex, item, this);
            if(newCard.getVisibility() != GONE){
                makeAndAddView(newCard, startingIndex);
                LAST_OBJECT_IN_STACK = startingIndex;
            }
            startingIndex ++;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void makeAndAddView(View child, int index) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
        addViewInLayout(child, 0, lp, true);

        boolean needToMeasure = child.isLayoutRequested();
        if(needToMeasure && lp!=null){
            int childWidthSpec = getChildMeasureSpec(getWidthMeasureSpec(),
                    getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin,
                    lp.width);
            int childHeightSpec = getChildMeasureSpec(getHeightMeasureSpec(),
                    getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin,
                    lp.height);
            child.measure(childWidthSpec, childHeightSpec);
        }else {
            cleanupLayoutState(child);
        }

        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();

        int gravity = -1;
        if(lp != null){
            gravity = lp.gravity;
        }
        if(gravity == -1){
            gravity = Gravity.TOP | Gravity.START;
        }

        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
        int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

        int childLeft;
        int childTop;
        switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK){
            case Gravity.CENTER_HORIZONTAL:
                childLeft = (getWidth() + getPaddingLeft() - getPaddingRight() - w)/2 +
                        lp.leftMargin - lp.rightMargin;
                break;
            case Gravity.END:
                childLeft = getWidth() + getPaddingRight() - w - lp.rightMargin;
                break;
            case Gravity.START:
            default:
                childLeft = getPaddingLeft() + lp.leftMargin;
                break;
        }
        switch (verticalGravity & Gravity.VERTICAL_GRAVITY_MASK){
            case Gravity.CENTER_VERTICAL:
                childTop = (getHeight() + getPaddingTop() - getPaddingBottom() - h) / 2 +
                        lp.topMargin - lp.bottomMargin;
                break;
            case Gravity.BOTTOM:
                childTop = getHeight() - getPaddingBottom() - h - lp.bottomMargin;
                break;
            case Gravity.TOP:
            default:
                childTop = getPaddingTop() + lp.topMargin;
                break;
        }

        child.layout(childLeft, childTop, childLeft + w, childTop + h);

        // 缩放层叠效果
        adjustChildView(child, index);
    }

    private void adjustChildView(View child, int index) {
        if (index > -1 && index < MAX_VISIBLE) {
            int multiple;
            if (index > 2) {
                multiple = 2;
            } else {
                multiple = index;
            }
            child.offsetTopAndBottom(yOffsetStep * multiple);
            child.setScaleX(1 - SCALE_STEP * multiple);
            child.setScaleY(1 - SCALE_STEP * multiple);
        }
    }

    /**
     * 处理topView下面的View动画显示
     * @param scrollRate
     */
    private void adjustChildrenOfUnderTopView(float scrollRate) {
        int count = getChildCount();
        if (count > 1) {
            int i;
            int multiple;
            if (count == 2) {
                i = LAST_OBJECT_IN_STACK - 1;
                multiple = 1;
            } else {
                i = LAST_OBJECT_IN_STACK - 2;
                multiple = 2;
            }
            float rate = Math.abs(scrollRate);
            for (; i < LAST_OBJECT_IN_STACK; i++, multiple--) {
                View underTopView = getChildAt(i);
                int offset = (int) (yOffsetStep * (multiple - rate));
                underTopView.offsetTopAndBottom(offset - underTopView.getTop() + initTop);
                underTopView.setScaleX(1 - SCALE_STEP * multiple + SCALE_STEP * rate);
                underTopView.setScaleY(1 - SCALE_STEP * multiple + SCALE_STEP * rate);
            }
        }
    }

    /**
     * Set the top view and add the fling listener
     */
    private void setTopView() {
        if(getChildCount() > 0){
            mActiveCard = getChildAt(LAST_OBJECT_IN_STACK);
            if(mActiveCard != null){
                flingCardListener = new FlingCardListener(mActiveCard, mAdapter.getItem(0),
                        ROTATION_DEGREES, new FlingCardListener.FlingListener() {
                    @Override
                    public void onCardExited() {
                        removeViewInLayout(mActiveCard);
                        mActiveCard = null;
                        mFlingListener.removeFirstObjectInAdapter();
                    }

                    @Override
                    public void leftExit(Object dataObject) {
                        mFlingListener.onLeftCardExit(dataObject);
                    }

                    @Override
                    public void rightExit(Object dataObject) {
                        mFlingListener.onRightCardExit(dataObject);
                    }

                    @Override
                    public void onClick(Object dataObject) {
                        if(mOnItemClickListener != null){
                            mOnItemClickListener.onItemClicked(0, dataObject);
                        }
                    }

                    @Override
                    public void onScroll(float progress, float scrollProgressPercent) {
                        adjustChildrenOfUnderTopView(progress);
                        mFlingListener.onScroll(scrollProgressPercent);
                    }
                });

                mActiveCard.setOnTouchListener(flingCardListener);
            }
        }
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        if(mAdapter != null && mDataSetObserver != null){
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }
        mAdapter = adapter;
        if(mAdapter != null && mDataSetObserver == null){
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    @Override
    public View getSelectedView() {
        return mActiveCard;
    }

    @Override
    public void requestLayout() {
        if(!mInLayout) {
            Log.i(TAG, "自定义控件OverLyingListView requestLayout");
            super.requestLayout();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FrameLayout.LayoutParams(getContext(), attrs);
    }

    public FlingCardListener getTopCardListener() throws NullPointerException {
        if (flingCardListener == null) {
            throw new NullPointerException();
        }
        return flingCardListener;
    }

    public void setMaxVisible(int MAX_VISIBLE) {
        this.MAX_VISIBLE = MAX_VISIBLE;
    }

    public void setMinStackInAdapter(int MIN_ADAPTER_STACK) {
        this.MIN_ADAPTER_STACK = MIN_ADAPTER_STACK;
    }

    public void setFlingListener(onFlingListener onFlingListener) {
        this.mFlingListener = onFlingListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 观察者，更新数据改变
     */
    class AdapterDataSetObserver extends DataSetObserver{
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
