package com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.itemTouchHelper;

/**
 * 用于通知adapter底层数据的更新的回调
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
