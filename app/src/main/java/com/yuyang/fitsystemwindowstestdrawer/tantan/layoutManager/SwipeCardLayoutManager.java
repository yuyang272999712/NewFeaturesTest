package com.yuyang.fitsystemwindowstestdrawer.tantan.layoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义探探效果的卡片
 */

public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "SwipeCardLayoutManager";
    
    //缩放层叠效果
    private int yOffsetStep = 80; // view叠加垂直偏移量的步长
    private static final float SCALE_STEP = 0.08f; // view叠加缩放的步长
    /* 显示的View个数 */
    private int MAX_VISIBLE = 3;
    /* 所有子View的宽高，这里假设所有的子View都是同样大小 */
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;
    /* 当前的第一个item*/
    private int currentItem = 0;
    /* 偏移量 */
    private int offsetX;
    private int offsetY = 0;
    /* 可滑动的View*/
    private View selectedView;

    private OnItemClickListener mOnItemClickListener;
    private FlingCardListener flingCardListener;
    private onFlingListener mFlingListener;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }

        Log.i(TAG, "onLayoutChildren: layout");
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler);

        if (getChildCount() == 0) {
            //获取第一个childView，并进行测量
            View scrap = recycler.getViewForPosition(0);
            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);
            //假设所有的childView都是同样大小
            mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
            mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);

            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) scrap.getLayoutParams();
            offsetY = lp.topMargin;
            offsetX = (getWidth() - mDecoratedChildWidth)/2;
            //回收这个View，下次使直接拿出来显示
            detachAndScrapView(scrap, recycler);
        }

        if (currentItem+MAX_VISIBLE > getItemCount() && mFlingListener!=null){
            mFlingListener.onAdapterAboutToEmpty(currentItem);
        }

        for(int i=MAX_VISIBLE;i>=0; i--){
            int position = currentItem+i;
            if (position >= state.getItemCount()){
                continue;
            }
            /*
             * Recycler会决定view是从Scrap还是Recycle缓存中取View，还是重新生成一个View
             */
            View view = recycler.getViewForPosition(position);
            if (view != null){
                addView(view);
                /*
                 * 重新测量和布局这个View
                 */
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, offsetX, offsetY, offsetX+mDecoratedChildWidth, offsetY+mDecoratedChildHeight);
                //平移、缩放View
                adjustChildView(view, i);
            }
        }
        Log.i(TAG, "onLayoutChildren: getChildCount="+getChildCount());
        //给第一个View设置触摸事件监听
        setTopView(recycler);
    }

    /**
     * 布局平移，实现叠加效果
     * @param child
     * @param index
     */
    private void adjustChildView(View child, int index) {
        if (index > -1 && index <= MAX_VISIBLE) {
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

    private void setTopView(final RecyclerView.Recycler recycler) {
        if(getChildCount() > 0){
            selectedView = findViewByPosition(currentItem);
            if(selectedView != null){
                flingCardListener = new FlingCardListener(selectedView, currentItem,
                        10.0f, new FlingCardListener.FlingListener() {
                    @Override
                    public void onCardExited() {
                        currentItem++;
                        selectedView.setOnTouchListener(null);//释放空间
                        removeAndRecycleView(selectedView, recycler);
                        if (mFlingListener != null){
                            mFlingListener.removeFirstObjectInAdapter();
                        }
                    }

                    @Override
                    public void leftExit(Integer position) {
                        if (mFlingListener != null){
                            mFlingListener.onLeftCardExit(position);
                        }
                    }

                    @Override
                    public void rightExit(Integer position) {
                        if (mFlingListener != null){
                            mFlingListener.onRightCardExit(position);
                        }
                    }

                    @Override
                    public void onClick(Integer position) {
                        if (mOnItemClickListener != null){
                            mOnItemClickListener.onItemClicked(position);
                        }
                    }

                    @Override
                    public void onScroll(float progress, float scrollProgressPercent) {
                        adjustChildrenOfUnderTopView(progress);
                        if (mFlingListener != null){
                            mFlingListener.onScroll(scrollProgressPercent);
                        }
                    }
                });

                selectedView.setOnTouchListener(flingCardListener);
            }
        }
    }

    /**
     * 处理topView下面的View动画显示
     * @param rate
     */
    private void adjustChildrenOfUnderTopView(float rate) {
        int count = getChildCount();
        if (count > 1) {
            View firstUnderTopView = findViewByPosition(currentItem+1);
            if (firstUnderTopView != null){
                int offset = (int) (yOffsetStep * (1 - rate));
                firstUnderTopView.offsetTopAndBottom(offset - firstUnderTopView.getTop() + offsetY);
                firstUnderTopView.setScaleX(1 - SCALE_STEP * 1 + SCALE_STEP * rate);
                firstUnderTopView.setScaleY(1 - SCALE_STEP * 1 + SCALE_STEP * rate);
            }
            View secondUnderTopView = findViewByPosition(currentItem+2);
            if (secondUnderTopView != null){
                int offset = (int) (yOffsetStep * (2 - rate));
                secondUnderTopView.offsetTopAndBottom(offset - secondUnderTopView.getTop() + offsetY);
                secondUnderTopView.setScaleX(1 - SCALE_STEP * 2 + SCALE_STEP * rate);
                secondUnderTopView.setScaleY(1 - SCALE_STEP * 2 + SCALE_STEP * rate);
            }
        }
    }

    public FlingCardListener getTopCardListener() throws NullPointerException {
        if (flingCardListener == null) {
            throw new NullPointerException();
        }
        return flingCardListener;
    }

    public void setFlingListener(onFlingListener onFlingListener) {
        this.mFlingListener = onFlingListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public View getSelectedView(){
        return selectedView;
    }

    public interface OnItemClickListener {
        public void onItemClicked(Integer position);
    }

    public interface onFlingListener {
        /**
         * 移除第一个adapter
         */
        public void removeFirstObjectInAdapter();

        /**
         * 左滑item
         * @param position
         */
        public void onLeftCardExit(Integer position);

        /**
         * 右滑item
         * @param position
         */
        public void onRightCardExit(Integer position);

        /**
         * 当adapter剩余数量少于MAX_VISIBLE后调用该方法
         * （有可能是该通过网络获取新数据了）
         * @param itemsInAdapter 当前view在adapter中的位置
         */
        public void onAdapterAboutToEmpty(int itemsInAdapter);

        /**
         * 活动过程中动作处理（例如图片的渐渐显示效果）
         * @param scrollProgressPercent 滑动百分比
         */
        public void onScroll(float scrollProgressPercent);
    }
}
