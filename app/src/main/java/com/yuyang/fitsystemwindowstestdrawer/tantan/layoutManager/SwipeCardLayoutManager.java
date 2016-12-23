package com.yuyang.fitsystemwindowstestdrawer.tantan.layoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义探探效果的卡片
 */

public class SwipeCardLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = "SwipeCardLayoutManager";
    
    //缩放层叠效果
    private int yOffsetStep = 40; // view叠加垂直偏移量的步长
    private static final float SCALE_STEP = 0.08f; // view叠加缩放的步长
    /* 显示的View个数 */
    private int MAX_VISIBLE = 4;
    /* 所有子View的宽高，这里假设所有的子View都是同样大小 */
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;
    /* 当前的第一个item*/
    private int currentItem = 0;
    /* 偏移量 */
    private int offsetX;
    private int offsetY;

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

        offsetX = (getWidth() - mDecoratedChildWidth)/2;
        offsetY = (getHeight() - mDecoratedChildHeight)/2;

        SparseArray<View> viewCache = new SparseArray<>();
        if (getChildCount() != 0) {
            //根据position缓存所有childView
            for (int i = 0; i < getChildCount(); i++) {
                //!--yuyang 这里的索引用的是position而不是index，是因为在childView在移动后可能不在布局中了，
                // 那么index此时对应的View就不是原来的View了
                int position = currentItem + MAX_VISIBLE - i;
                View child = getChildAt(i);
                viewCache.put(position, child);
            }
            //临时的剥离这些View（这些View在之后的会之中我们还会将屏幕上显示的View添加回屏幕）
            for (int i=0; i < viewCache.size(); i++) {
                detachView(viewCache.valueAt(i));
            }
        }

        for(int i=MAX_VISIBLE;i>=0; i--){
            int position = currentItem+i;
            if (position >= state.getItemCount()){
                continue;
            }
            //从缓存中取view
            View view = viewCache.get(position);
            if (view == null){
                /*
                 * Recycler会决定view是从Scrap还是Recycle缓存中取View，还是重新生成一个View
                 */
                view = recycler.getViewForPosition(position);
                addView(view);
                /*
                 * 重新测量和布局这个View
                 */
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, offsetX, offsetY, offsetX+mDecoratedChildWidth, offsetY+mDecoratedChildHeight);
            }else {
                //如果缓存中有这个View，就直接将这个View添加至布局中，不用在进行测量和重新布局了
                attachView(view);
                viewCache.remove(position);
            }
            adjustChildView(view, i);
        }

        /*
         * 最后一步：将之前缓存的但没有再添加回布局中的View回收至Recycle缓存中，以便Recycler对象可以回收利用
         */
        for (int i=0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }
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
}
