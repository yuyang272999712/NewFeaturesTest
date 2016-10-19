package com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.List;

/**
 * 平铺布局的LayoutManager
 */

public class FixedLayoutManager extends RecyclerView.LayoutManager {
    /* 默认列数 */
    private static final int DEFAULT_COUNT = 1;

    /* 方向标示 */
    private static final int DIRECTION_NONE = -1;
    private static final int DIRECTION_START = 0;
    private static final int DIRECTION_END = 1;
    private static final int DIRECTION_UP = 2;
    private static final int DIRECTION_DOWN = 3;

    /* View Removal Constants */
    private static final int REMOVE_VISIBLE = 0;
    private static final int REMOVE_INVISIBLE = 1;

    /* 默认 列 数 */
    private int mTotalColumnCount = DEFAULT_COUNT;
    /* 所有子View的宽高，这里假设所有的子View都是同样大小 */
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;
    /* 记录 列／行 能显示View的数量 */
    private int mVisibleColumnCount;
    private int mVisibleRowCount;
    /* 左上角一个可见的View的Position */
    private int mFirstVisiblePosition;
    /* Used for tracking off-screen change events */
    private int mFirstChangedPosition;
    private int mChangedPositionCount;

    public FixedLayoutManager(int columnCount){
        if (columnCount != 0) {
            this.mTotalColumnCount = columnCount;
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return new LayoutParams(c, attrs);
    }
    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) lp);
        } else {
            return new LayoutParams(lp);
        }
    }
    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    /**
     * 每次在RecyclerView需要初始化布局时 或者 当适配器的数据改变时(或者整个适配器被换掉时) 会被调用。
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        //如果是重新布局，重置这两个值
        if (!state.isPreLayout()) {
            mFirstChangedPosition = mChangedPositionCount = 0;
        }

        if (getChildCount() == 0) {
            //获取第一个childView，并进行测量
            View scrap = recycler.getViewForPosition(0);
            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);
            //假设所有的childView都是同样大小
            mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
            mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
            //回收这个View，下次使直接拿出来显示
            detachAndScrapView(scrap, recycler);
        }

        /* 重新计算可现实的行列大小 */
        updateWindowSizing();

        SparseIntArray removedCache = null;
        /*
         * During pre-layout, we need to take note of any views that are
         * being removed in order to handle predictive animations
         */
        if (state.isPreLayout()) {
            removedCache = new SparseIntArray(getChildCount());
            for (int i=0; i < getChildCount(); i++) {
                final View view = getChildAt(i);
                LayoutParams lp = (LayoutParams) view.getLayoutParams();

                if (lp.isItemRemoved()) {
                    //Track these view removals as visible
                    removedCache.put(lp.getViewLayoutPosition(), REMOVE_VISIBLE);
                }
            }

            //Track view removals that happened out of bounds (i.e. off-screen)
            if (removedCache.size() == 0 && mChangedPositionCount > 0) {
                for (int i = mFirstChangedPosition; i < (mFirstChangedPosition + mChangedPositionCount); i++) {
                    removedCache.put(i, REMOVE_INVISIBLE);
                }
            }
        }

        /* 重置ChildView位置 */
        int childLeft = 0;
        int childTop = 0;
        if (getChildCount() == 0) { //First or empty layout
            //Reset the visible and scroll positions
            mFirstVisiblePosition = 0;
            childLeft = getPaddingLeft();
            childTop = getPaddingTop();
        } else if (!state.isPreLayout()
                && getVisibleChildCount() >= state.getItemCount()) {
            //Data set is too small to scroll fully, just reset position
            mFirstVisiblePosition = 0;
            childLeft = getPaddingLeft();
            childTop = getPaddingTop();
        } else { //Adapter data set changes
            /*
             * Keep the existing initial position, and save off
             * the current scrolled offset.
             */
            final View topChild = getChildAt(0);
            childLeft = getDecoratedLeft(topChild);
            childTop = getDecoratedTop(topChild);

            /*
             * When data set is too small to scroll vertically, adjust vertical offset
             * and shift position to the first row, preserving current column
             */
            if (!state.isPreLayout() && getVerticalSpace() > (getTotalRowCount() * mDecoratedChildHeight)) {
                mFirstVisiblePosition = mFirstVisiblePosition % getTotalColumnCount();
                childTop = getPaddingTop();

                //If the shift overscrolls the column max, back it off
                if ((mFirstVisiblePosition + mVisibleColumnCount) > state.getItemCount()) {
                    mFirstVisiblePosition = Math.max(state.getItemCount() - mVisibleColumnCount, 0);
                    childLeft = getPaddingLeft();
                }
            }

            /*
             * Adjust the visible position if out of bounds in the
             * new layout. This occurs when the new item count in an adapter
             * is much smaller than it was before, and you are scrolled to
             * a location where no items would exist.
             */
            int maxFirstRow = getTotalRowCount() - (mVisibleRowCount-1);
            int maxFirstCol = getTotalColumnCount() - (mVisibleColumnCount-1);
            boolean isOutOfRowBounds = getFirstVisibleRow() > maxFirstRow;
            boolean isOutOfColBounds =  getFirstVisibleColumn() > maxFirstCol;
            if (isOutOfRowBounds || isOutOfColBounds) {
                int firstRow;
                if (isOutOfRowBounds) {
                    firstRow = maxFirstRow;
                } else {
                    firstRow = getFirstVisibleRow();
                }
                int firstCol;
                if (isOutOfColBounds) {
                    firstCol = maxFirstCol;
                } else {
                    firstCol = getFirstVisibleColumn();
                }
                mFirstVisiblePosition = firstRow * getTotalColumnCount() + firstCol;

                childLeft = getHorizontalSpace() - (mDecoratedChildWidth * mVisibleColumnCount);
                childTop = getVerticalSpace() - (mDecoratedChildHeight * mVisibleRowCount);

                //Correct cases where shifting to the bottom-right overscrolls the top-left
                // This happens on data sets too small to scroll in a direction.
                if (getFirstVisibleRow() == 0) {
                    childTop = Math.min(childTop, getPaddingTop());
                }
                if (getFirstVisibleColumn() == 0) {
                    childLeft = Math.min(childLeft, getPaddingLeft());
                }
            }
        }

        //清理所有的ChildView，放入Scrap集合中
        detachAndScrapAttachedViews(recycler);

        //布局绘制
        fillGrid(DIRECTION_NONE, childLeft, childTop, recycler, state, removedCache);

        //Evaluate any disappearing views that may exist
        if (!state.isPreLayout() && !recycler.getScrapList().isEmpty()) {
            final List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
            final HashSet<View> disappearingViews = new HashSet<View>(scrapList.size());

            for (RecyclerView.ViewHolder holder : scrapList) {
                final View child = holder.itemView;
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!lp.isItemRemoved()) {
                    disappearingViews.add(child);
                }
            }

            for (View child : disappearingViews) {
                layoutDisappearingView(child);
            }
        }
    }

    private void fillGrid(int direction, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fillGrid(direction, 0, 0, recycler, state, null);
    }

    /**
        通常来说，在这类方法之中你需要完成的主要步骤如下：
     a、在滚动事件结束后检查所有附加视图当前的偏移位置。
     b、判断是否需要添加新视图填充由滚动屏幕产生的空白部分。并从 Recycler 中获取视图。
     c、判断当前视图是否不再显示。移除它们并放置到 Recycler 中。
     d、判断剩余视图是否需要整理。发生上述变化后可能 需要你修改视图的子索引来更好地和它们的适配器位置校准。
     * @param direction
     * @param childLeft
     * @param childTop
     * @param recycler
     */
    private void fillGrid(int direction, int childLeft, int childTop,
                          RecyclerView.Recycler recycler,
                          RecyclerView.State state,
                          SparseIntArray removedPositions) {
        //正常情况下这两种情况都不应该出现，因为mFirstVisiblePosition的值是由我们自己来控制的
        if (mFirstVisiblePosition < 0) {
            mFirstVisiblePosition = 0;
        }
        if (mFirstVisiblePosition >= getItemCount()) {
            mFirstVisiblePosition = (getItemCount() - 1);
        }
        /*
          第一步：清点目前我们所有的视图。将他们 Detach 以便稍后重新连接。
         */
        SparseArray<View> viewCache = new SparseArray<>();
        //显示的初始位置偏移量
        int startLeftOffset = childLeft;
        int startTopOffset = childTop;
        if (getChildCount() != 0) {
            final View topView = getChildAt(0);//获取第一个childView
            startLeftOffset = getDecoratedLeft(topView);
            startTopOffset = getDecoratedTop(topView);
            switch (direction) {
                case DIRECTION_START:
                    startLeftOffset -= mDecoratedChildWidth;
                    break;
                case DIRECTION_END:
                    startLeftOffset += mDecoratedChildWidth;
                    break;
                case DIRECTION_UP:
                    startTopOffset -= mDecoratedChildHeight;
                    break;
                case DIRECTION_DOWN:
                    startTopOffset += mDecoratedChildHeight;
                    break;
            }
            //根据position缓存所有childView
            for (int i = 0; i < getChildCount(); i++) {
                //!--yuyang 这里的索引用的是position而不是index，是因为在childView在移动后可能不在布局中了，
                // 那么index此时对应的View就不是原来的View了
                int position = positionOfIndex(i);
                View child = getChildAt(i);
                viewCache.put(position, child);
            }
            //临时的剥离这些View（这些View在之后的会之中我们还会将屏幕上显示的View添加回屏幕）
            for (int i=0; i < viewCache.size(); i++) {
                detachView(viewCache.valueAt(i));
            }
        }

        /*
         第二步：测量/布局每一个当前可见的子视图。重新连接已有的视图，新的视图是从 Recycler 之中获取的。
         */
        //根据移动的方向改变mFirstVisiblePosition的值
        switch (direction) {
            case DIRECTION_START:
                mFirstVisiblePosition--;
                break;
            case DIRECTION_END:
                mFirstVisiblePosition++;
                break;
            case DIRECTION_UP:
                mFirstVisiblePosition -= getTotalColumnCount();
                break;
            case DIRECTION_DOWN:
                mFirstVisiblePosition += getTotalColumnCount();
                break;
        }
        int leftOffset = startLeftOffset;
        int topOffset = startTopOffset;
        for (int i = 0; i < getVisibleChildCount(); i++) {
            int nextPosition = positionOfIndex(i);

            /*
             * When a removal happens out of bounds, the pre-layout positions of items
             * after the removal are shifted to their final positions ahead of schedule.
             * We have to track off-screen removals and shift those positions back
             * so we can properly lay out all current (and appearing) views in their
             * initial locations.
             */
            int offsetPositionDelta = 0;
            if (state.isPreLayout()) {
                int offsetPosition = nextPosition;
                for (int offset = 0; offset < removedPositions.size(); offset++) {
                    //Look for off-screen removals that are less-than this
                    if (removedPositions.valueAt(offset) == REMOVE_INVISIBLE
                            && removedPositions.keyAt(offset) < nextPosition) {
                        //Offset position to match
                        offsetPosition--;
                    }
                }
                offsetPositionDelta = nextPosition - offsetPosition;
                nextPosition = offsetPosition;
            }

            if (nextPosition < 0 || nextPosition >= state.getItemCount()) {
                //Item space beyond the data set, don't attempt to add a view
                continue;
            }

            //从缓存中取view
            View view = viewCache.get(nextPosition);
            if (view == null){
                /*
                 * Recycler会决定view是从Scrap还是Recycle缓存中取View，还是重新生成一个View
                 */
                view = recycler.getViewForPosition(nextPosition);
                addView(view);
                /*
                 * Update the new view's metadata, but only when this is a real
                 * layout pass.
                 */
                if (!state.isPreLayout()) {
                    LayoutParams lp = (LayoutParams) view.getLayoutParams();
                    lp.row = getGlobalRowOfPosition(nextPosition);
                    lp.column = getGlobalColumnOfPosition(nextPosition);
                }
                /*
                 * 重新测量和布局这个View
                 */
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, leftOffset, topOffset,
                        leftOffset + mDecoratedChildWidth,
                        topOffset + mDecoratedChildHeight);
            }else {
                //如果缓存中有这个View，就直接将这个View添加至布局中，不用在进行测量和重新布局了
                attachView(view);
                viewCache.remove(nextPosition);
            }

            if (i % mVisibleColumnCount == (mVisibleColumnCount - 1)) {
                leftOffset = startLeftOffset;
                topOffset += mDecoratedChildHeight;

                //During pre-layout, on each column end, apply any additional appearing views
                if (state.isPreLayout()) {
                    layoutAppearingViews(recycler, view, nextPosition, removedPositions.size(), offsetPositionDelta);
                }
            } else {
                leftOffset += mDecoratedChildWidth;
            }
        }

        /**
         * 最后一步：将之前缓存的但没有再添加回布局中的View回收至Recycle缓存中，以便Recycler对象可以回收利用
         */
        for (int i=0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }

    }

    /**
     * 允许水平滑动
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    /**
     * 处理水平滑动
     *  需要完成以下几步：
        a、将所有的子视图移动适当的位置 (对的，你得自己做这个)。
        b、决定移动视图后 添加/移除 视图。
        c、返回滚动的实际距离。框架会根据它判断你是否触碰到边界。
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        //获取左上角的View
        final View topView = getChildAt(0);
        //后去右下角的View
        final View bottomView = getChildAt(getChildCount()-1);

        //获取右下角View与左上角View的水平跨度
        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if (viewSpan <= getHorizontalSpace()) {//如果水平跨度小于RecyclerView的水平宽度，那么不能水平滑动
            return 0;
        }

        int delta;
        boolean leftBoundReached = getFirstVisibleColumn() == 0;//是否到达第一例
        boolean rightBoundReached = getLastVisibleColumn() >= getTotalColumnCount();//是否到达最后一列
        if (dx > 0) {//如果是向左滑动
            if (rightBoundReached) {//如果已到达最后一列
                int rightOffset = getHorizontalSpace() - getDecoratedRight(bottomView) + getPaddingRight();
                delta = Math.max(-dx, rightOffset);
            } else {
                //如果没到达，向左移动dx距离
                delta = -dx;
            }
        }else {
            if (leftBoundReached){
                int leftOffset = -getDecoratedLeft(topView) + getPaddingLeft();
                delta = Math.min(-dx, leftOffset);
            } else {
                delta = -dx;
            }
        }

        offsetChildrenHorizontal(delta);

        if (dx > 0) {
            if (getDecoratedRight(topView) < 0 && !rightBoundReached) {
                fillGrid(DIRECTION_END, recycler, state);
            } else if (!rightBoundReached) {
                fillGrid(DIRECTION_NONE, recycler, state);
            }
        } else {
            if (getDecoratedLeft(topView) > 0 && !leftBoundReached) {
                fillGrid(DIRECTION_START, recycler, state);
            } else if (!leftBoundReached) {
                fillGrid(DIRECTION_NONE, recycler, state);
            }
        }

        /*
            最后，将实际位移距离应用给子视图。RecyclerView 根据这个值判断是否 绘制到达边界的效果。一般意义上，如果返回值不等于传入的值就意味着
        需要绘制边缘的发光效果了。 如果你返回了一个带有错误方向的值，框架的函数会把这个当做一个大的变化 你将不能获得正确的边缘发光特效。
            除了用来判断绘制边界特效外，返回值还被用来决定什么时候取消 flings。 返回错误的值会让你失去对 content fling 的控制。框架会认为你
        已经提前 触碰到边缘并取消了 fling。
         */
        return -delta;
    }

    /**
     * 允许竖直滑动
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }

        //获取左上角的View
        final View topView = getChildAt(0);
        //后去右下角的View
        final View bottomView = getChildAt(getChildCount()-1);

        //上下View的距离
        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
        if (viewSpan < getVerticalSpace()) {
            return 0;
        }

        int delta;
        int maxRowCount = getTotalRowCount();
        boolean topBoundReached = getFirstVisibleRow() == 0;
        boolean bottomBoundReached = getLastVisibleRow() >= maxRowCount;
        if (dy > 0) { //向上滚动
            if (bottomBoundReached) {
                int bottomOffset;
                if (rowOfIndex(getChildCount() - 1) >= (maxRowCount - 1)) {
                    bottomOffset = getVerticalSpace() - getDecoratedBottom(bottomView)
                            + getPaddingBottom();
                } else {
                    bottomOffset = getVerticalSpace() - (getDecoratedBottom(bottomView)
                            + mDecoratedChildHeight) + getPaddingBottom();
                }

                delta = Math.max(-dy, bottomOffset);
            } else {
                delta = -dy;
            }
        } else {
            if (topBoundReached) {
                int topOffset = -getDecoratedTop(topView) + getPaddingTop();
                delta = Math.min(-dy, topOffset);
            } else {
                delta = -dy;
            }
        }

        offsetChildrenVertical(delta);

        if (dy > 0) {
            if (getDecoratedBottom(topView) < 0 && !bottomBoundReached) {
                fillGrid(DIRECTION_DOWN, recycler, state);
            } else if (!bottomBoundReached) {
                fillGrid(DIRECTION_NONE, recycler, state);
            }
        } else {
            if (getDecoratedTop(topView) > 0 && !topBoundReached) {
                fillGrid(DIRECTION_UP, recycler, state);
            } else if (!topBoundReached) {
                fillGrid(DIRECTION_NONE, recycler, state);
            }
        }

        return -delta;
    }

    /**－－－－－－－－－－－－－－动画相关－－－－－－－－－－－－－－*/
    /* Helper to obtain and place extra appearing views */
    private void layoutAppearingViews(RecyclerView.Recycler recycler, View referenceView, int referencePosition, int extraCount, int offset) {
        //Nothing to do...
        if (extraCount < 1) return;

        //FIXME: This code currently causes double layout of views that are still visible…
        for (int extra = 1; extra <= extraCount; extra++) {
            //Grab the next position after the reference
            final int extraPosition = referencePosition + extra;
            if (extraPosition < 0 || extraPosition >= getItemCount()) {
                //Can't do anything with this
                continue;
            }

            /*
             * Obtain additional position views that we expect to appear
             * as part of the animation.
             */
            View appearing = recycler.getViewForPosition(extraPosition);
            addView(appearing);

            //Find layout delta from reference position
            final int newRow = getGlobalRowOfPosition(extraPosition + offset);
            final int rowDelta = newRow - getGlobalRowOfPosition(referencePosition + offset);
            final int newCol = getGlobalColumnOfPosition(extraPosition + offset);
            final int colDelta = newCol - getGlobalColumnOfPosition(referencePosition + offset);

            layoutTempChildView(appearing, rowDelta, colDelta, referenceView);
        }
    }

    /* Helper to place a disappearing view */
    private void layoutDisappearingView(View disappearingChild) {
        /*
         * LayoutManager has a special method for attaching views that
         * will only be around long enough to animate.
         */
        addDisappearingView(disappearingChild);

        //Adjust each disappearing view to its proper place
        final LayoutParams lp = (LayoutParams) disappearingChild.getLayoutParams();

        final int newRow = getGlobalRowOfPosition(lp.getViewAdapterPosition());
        final int rowDelta = newRow - lp.row;
        final int newCol = getGlobalColumnOfPosition(lp.getViewAdapterPosition());
        final int colDelta = newCol - lp.column;

        layoutTempChildView(disappearingChild, rowDelta, colDelta, disappearingChild);
    }


    /* Helper to lay out appearing/disappearing children */
    private void layoutTempChildView(View child, int rowDelta, int colDelta, View referenceView) {
        //Set the layout position to the global row/column difference from the reference view
        int layoutTop = getDecoratedTop(referenceView) + rowDelta * mDecoratedChildHeight;
        int layoutLeft = getDecoratedLeft(referenceView) + colDelta * mDecoratedChildWidth;

        measureChildWithMargins(child, 0, 0);
        layoutDecorated(child, layoutLeft, layoutTop,
                layoutLeft + mDecoratedChildWidth,
                layoutTop + mDecoratedChildHeight);
    }


    /**－－－－－－－－－－－－－测量相关－－－－－－－－－－－－－－－*/
    /**
     * 每次重新布局时，都重新计算一下屏幕上能够显示的列／行数。
     * 注：列／行 分别加1，是为了多显示一列和一行，以保证滑动顺畅
     */
    private void updateWindowSizing() {
        mVisibleColumnCount = (getHorizontalSpace() / mDecoratedChildWidth) + 1;
        if (getHorizontalSpace() % mDecoratedChildWidth > 0) {
            mVisibleColumnCount++;
        }
        if (mVisibleColumnCount > getTotalColumnCount()) {//如果允许显示的列数大于了总列数
            mVisibleColumnCount = getTotalColumnCount();
        }

        mVisibleRowCount = (getVerticalSpace()/ mDecoratedChildHeight) + 1;
        if (getVerticalSpace() % mDecoratedChildHeight > 0) {
            mVisibleRowCount++;
        }
        if (mVisibleRowCount > getTotalRowCount()) {
            mVisibleRowCount = getTotalRowCount();
        }
    }

    /**
     * 计算共有多少列
     * @return
     */
    private int getTotalColumnCount() {
        if (getItemCount() < mTotalColumnCount) {
            return getItemCount();
        }
        return mTotalColumnCount;
    }

    /**
     * 计算共有多少行
     * @return
     */
    private int getTotalRowCount() {
        if (getItemCount() == 0 || mTotalColumnCount == 0) {
            return 0;
        }
        int maxRow = getItemCount() / mTotalColumnCount;
        //可能最后一行不满一整行
        if (getItemCount() % mTotalColumnCount != 0) {
            maxRow++;
        }
        return maxRow;
    }

    /**
     * 后去当前屏幕可以显示下多少ChildView
     * @return
     */
    private int getVisibleChildCount() {
        return mVisibleColumnCount * mVisibleRowCount;
    }

    /**
     * 根据childView的index映射出该childView绑定的item的position
     */
    private int positionOfIndex(int childIndex) {
        int row = childIndex / mVisibleColumnCount;
        int column = childIndex % mVisibleColumnCount;
        return mFirstVisiblePosition + (row * getTotalColumnCount()) + column;
    }

    /**
     * 根据childView的index映射出该childView所在的行
     * @param childIndex
     * @return
     */
    private int rowOfIndex(int childIndex) {
        int position = positionOfIndex(childIndex);
        return position / getTotalColumnCount();
    }

    /**
     * 获取可显示的第一列
     * @return
     */
    private int getFirstVisibleColumn() {
        return (mFirstVisiblePosition % getTotalColumnCount());
    }

    /**
     * 获取可显示的最后一列
     * @return
     */
    private int getLastVisibleColumn() {
        return getFirstVisibleColumn() + mVisibleColumnCount;
    }

    /**
     * 获取可显示的第一行
     * @return
     */
    private int getFirstVisibleRow() {
        return (mFirstVisiblePosition / getTotalColumnCount());
    }

    /**
     * 获取可显示的最后一行
     * @return
     */
    private int getLastVisibleRow() {
        return getFirstVisibleRow() + mVisibleRowCount;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    /* Return the overall column index of this position in the global layout */
    private int getGlobalColumnOfPosition(int position) {
        return position % mTotalColumnCount;
    }
    /* Return the overall row index of this position in the global layout */
    private int getGlobalRowOfPosition(int position) {
        return position / mTotalColumnCount;
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        //当前所在行
        public int row;
        //当前所在列
        public int column;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
        public LayoutParams(int width, int height) {
            super(width, height);
        }
        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
        public LayoutParams(RecyclerView.LayoutParams source) {
            super(source);
        }
    }
}
