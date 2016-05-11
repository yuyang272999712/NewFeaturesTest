package com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.itemTouchHelper;

import android.support.v7.widget.RecyclerView;

/**
 * 通知ItemTouchHelper调用startDrag(RecyclerView.ViewHolder) 方法来开始一个拖动
 */
public interface OnStartDragListener {
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
