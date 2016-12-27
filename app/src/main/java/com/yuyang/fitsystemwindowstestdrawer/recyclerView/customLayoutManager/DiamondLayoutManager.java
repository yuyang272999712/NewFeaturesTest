package com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager;

import android.graphics.Rect;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义RecyclerView的LayoutManager
 */

public class DiamondLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "DiamondLayoutManager";

    public static final int DEFAULT_GROUP_SIZE = 5;//每组默认最多显示多少（一组分两行显示）

    private int mGroupSize;//每行最多显示多少个
    private int mHorizontalOffset;//布局水平偏移量
    private int mVerticalOffset;//布局竖直便宜量
    private int mTotalWidth;//所有item显示后所占宽度
    private int mTotalHeight;//高度
    private int mGravityOffset;//居中显示需要的偏移量
    private boolean isGravityCenter;//是否居中显示

    private SparseArrayCompat<Rect> mItemFrames;//存储每个Item的坐标位置
    private boolean mCanHorizontalScroll = false;//是否可以水平滑动

    public DiamondLayoutManager(boolean center){
        this(DEFAULT_GROUP_SIZE, center);
    }

    public DiamondLayoutManager(int groupSize, boolean center){
        this.mGroupSize = groupSize;
        this.isGravityCenter = center;
        mItemFrames = new SparseArrayCompat<>();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * onLayoutChildren() 是 LayoutManager 的主入口。 它会在 view 需要初始化布局时调用，
     * 当适配器的数据改变时(或者整个适配器被换掉时)会再次调用。 注意！这个方法不是在每次你对布局作出改变时调用的。
     * 它是 初始化布局 或者 在数据改变时重置子视图布局的好位置。
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) {
            return;
        }
        Log.e("绘制","onLayoutChildren进行了绘制，绘制时mVerticalOffset＝"+mVerticalOffset);
        /**
         * Recycler 有两级视图缓存系统： scrap heap 和 recycle pool (垃圾堆和回收池)，
         *      Scrap heap 是一个轻量的集合，视图可以不经过适配器直接返回给 LayoutManager 。通常被 detach
         *  但会在同一布局重新使用的视图会临时储存在这里。
         *      Recycle pool 存放的 是那些假定并没有得到正确数据(相应位置的数据)的视图， 因此它们都要经过适配器
         *  重新绑定后才能返回给 LayoutManager。
         *
         *  通常来说,如果你想要临时整理并且希望稍后在同一布局中重新使用某个 view 的话，可以对它调用
         * detachAndScrapView() 。如果基于当前布局 你不再需要某个 view 的话，对其调用removeAndRecycleView()。
         */
        /**
         * 这个方法是RecyclerView.LayoutManager的, 它的作用是将界面上的所有item都detach掉, 并缓存在scrap中,
         * 以便下次直接拿出来显示.
         */
        detachAndScrapAttachedViews(recycler);
        //通过代码来获取第一个item view并测量它.因为在我们的这个效果中所有的item大小都是一样的,
        //所以我们只要获取第一个的大小, 就知道所有的item的大小了.
        View first = recycler.getViewForPosition(0);
        /**
         * LayoutManager 中提供了一些辅助方法操作 decorations ，不需要我们自己实现：
         用getDecoratedLeft()代替child.getLeft()获取子视图的 left 边缘。
         用getDecoratedTop()代替getTop()获取子视图的 top 边缘。
         用getDecoratedRight()代替getRight()获取子视图的 right 边缘。
         用getDecoratedBottom()代替getBottom()获取子视图的 bottom 边缘。
         使用 measureChild() 或 measureChildWithMargins() 代替child.measure() 测量来自 Recycler 的新视图。
         使用layoutDecorated()代替 child.layout() 布局来自 Recycler 的新视图。
         使用 getDecoratedMeasuredWidth()或 getDecoratedMeasuredHeight() 代替 child.getMeasuredWidth()或child.getMeasuredHeight()获取 子视图的测量数据。
         只要你使用了正确的方法去获取视图的属性和测量数据，RecyclerView 会自己搞定细节部分的处理。
         */
        measureChildWithMargins(first, 0, 0);
        int itemWidth = getDecoratedMeasuredWidth(first);
        int itemHeight = getDecoratedMeasuredHeight(first);
        //获取每一组中第一行和第二行中item的个数.
        int firstLineSize = mGroupSize / 2 + 1;
        int secondLineSize = firstLineSize + mGroupSize / 2;
        //item最大横向宽度是否超过了布局的最大宽度
        if (isGravityCenter && firstLineSize * itemWidth < getHorizontalSpace()) {
            mGravityOffset = (getHorizontalSpace() - firstLineSize * itemWidth) / 2;
            mCanHorizontalScroll = false;
        } else {
            mGravityOffset = 0;
            mCanHorizontalScroll = true;
        }

        for (int i = 0; i < getItemCount(); i++) {
            Rect item = new Rect();
            float coefficient = isFirstGroup(i) ? 1.5f : 1.f;
            int offsetHeight = (int) ((i / mGroupSize) * itemHeight * coefficient);

            // 每一组的第一行
            if (isItemInFirstLine(i)) {
                int offsetInLine = i < firstLineSize ? i : i % mGroupSize;
                item.set(mGravityOffset + offsetInLine * itemWidth,
                        offsetHeight,
                        mGravityOffset + offsetInLine * itemWidth + itemWidth,
                        itemHeight + offsetHeight);
            }else {
                int lineOffset = itemHeight / 2;
                int offsetInLine = (i < secondLineSize ? i : i % mGroupSize) - firstLineSize;
                item.set(mGravityOffset + offsetInLine * itemWidth + itemWidth / 2,
                        offsetHeight + lineOffset,
                        mGravityOffset + offsetInLine * itemWidth + itemWidth  + itemWidth / 2,
                        itemHeight + offsetHeight + lineOffset);
            }
            mItemFrames.put(i, item);
        }

        mTotalWidth = Math.max(firstLineSize * itemWidth, getHorizontalSpace());
        int totalHeight = getGroupSize() * itemHeight;
        if (!isItemInFirstLine(getItemCount() - 1)) {
            totalHeight += itemHeight / 2;
        }
        mTotalHeight = Math.max(totalHeight, getVerticalSpace());
        fill(recycler, state);
    }

    /**
     * 上个方法中得到了存储所有item坐标文字的集合mItemFrames
     * 真正的layout在这个fill方法中
     * @param recycler
     * @param state
     */
    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0 || state.isPreLayout()) { return;}
        //标记当前显示的区域, 因为RecyclerView是可滑动的, 所以这个区域不能简单的是0~高度/宽度这么一个值,
        //还要加上当前滑动的偏移量.
        Log.e("绘制","fill进行了绘制，绘制时mVerticalOffset＝"+mVerticalOffset);
        Rect displayRect1 = new Rect(mHorizontalOffset, mVerticalOffset,
                getHorizontalSpace() + mHorizontalOffset,
                getVerticalSpace() + mVerticalOffset);
        Rect displayRect2 = new Rect(getPaddingLeft(), getPaddingTop(), getHorizontalSpace(), getVerticalSpace());

        //子View移动后会有些View移出了屏幕
        //接下来, 通过getChildCount获取RecyclerView中的所有子view, 并且依次判断这些view是否在当前显示范围内,
        //如果不在, 就通过removeAndRecycleView将它移除并回收掉, recycle的作用是回收一个view, 并等待下次使用,
        //这里可能会被重新绑定新的数据. 而scrap的作用是缓存一个view, 并等待下次显示, 这里的view会被直接显示出来.
        List<View> removeViews = new ArrayList<>();//记录需要回收的childView
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View item = getChildAt(i);
            rect.left = getDecoratedLeft(item);
            rect.top = getDecoratedTop(item);
            rect.right = getDecoratedRight(item);
            rect.bottom = getDecoratedBottom(item);
            if (!Rect.intersects(displayRect2, rect)) {
                removeViews.add(item);
            }
        }
        for (View view:removeViews){
            removeAndRecycleView(view, recycler);
        }

        //回收所有经过上面步骤后屏幕上还剩下的View，由于接下来直接显示
        detachAndScrapAttachedViews(recycler);
        //这里是循环的getItemCount, 也就是所有的item个数, 依然判断它是不是在显示区域, 如果在,
        //则通过recycler.getViewForPosition(i)拿到这个view, 并且通过addView添加到RecyclerView中,
        //添加进去了还没完, 还需要调用measureChildWithMargins方法对这个view进行测量.
        //最后调用layoutDecorated对item view进行layout操作.
        for (int i=0; i<getItemCount(); i++){
            Rect frame = mItemFrames.get(i);
            if (Rect.intersects(displayRect1, frame)){
                View scrap = recycler.getViewForPosition(i);
                addView(scrap);
                //!--yuyang 子View的测量
                measureChildWithMargins(scrap, 0, 0);
                //!--yuyang 子View的layout
                layoutDecorated(scrap, frame.left-mHorizontalOffset, frame.top-mVerticalOffset, frame.right-mHorizontalOffset, frame.bottom-mVerticalOffset);
            }
        }
    }

    /**
     * 如果想RecyclerView可以滑动，必须重写这个方法
     * @return
     */
    @Override
    public boolean canScrollHorizontally() {
        return mCanHorizontalScroll;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mHorizontalOffset + dx < 0) {
            dx = -mHorizontalOffset;
        } else if (mHorizontalOffset + dx > mTotalWidth - getHorizontalSpace()) {
            dx = mTotalWidth - getHorizontalSpace() - mHorizontalOffset;
        }

        offsetChildrenHorizontal(-dx);
        fill(recycler, state);
        mHorizontalOffset += dx;

        if (dx != 0) {
            fill(recycler, state);
        }

        return dx;
    }

    /**
     * 先计算偏移量，然后重新布局子View
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mVerticalOffset + dy < 0) {
            dy = -mVerticalOffset;
        } else if (mVerticalOffset + dy > mTotalHeight - getVerticalSpace()) {
            dy = mTotalHeight - getVerticalSpace() - mVerticalOffset;
        }
        Log.e("测量1","偏移量："+dy
                +"\nmVerticalOffset+dy:"+(mVerticalOffset + dy)
                +"\nmTotalHeight - getVerticalSpace():"+(mTotalHeight - getVerticalSpace()));
        //子View整体移动
        offsetChildrenVertical(-dy);
        mVerticalOffset += dy;
        if (dy != 0) {
            fill(recycler, state);
        }
        Log.e("测量2","mVerticalOffset："+mVerticalOffset);
        return dy;
    }

    private int getGroupSize() {
        return (int) Math.ceil(getItemCount() / (float)mGroupSize);
    }

    private boolean isItemInFirstLine(int index) {
        int firstLineSize = mGroupSize / 2 + 1;
        return index < firstLineSize || (index >= mGroupSize && index % mGroupSize < firstLineSize);
    }


    private boolean isFirstGroup(int index) {
        return index < mGroupSize;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
