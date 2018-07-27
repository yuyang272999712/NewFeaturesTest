package com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemTouchHelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * ItemTouchHelper的CallBack
 * 需要复写的方法已列出请看实现类
 */
public class SimpleItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    //通知adapter更新数据
    private ItemTouchHelperAdapter adapter;

    public SimpleItemTouchHelperCallBack(ItemTouchHelperAdapter adapter){
        this.adapter = adapter;
    }

    /**
     * 要支持长按RecyclerView item进入拖动操作，你必须在isLongPressDragEnabled()方法中返回true。
     * 或者，也可以调用ItemTouchHelper.startDrag(RecyclerView.ViewHolder) 方法来开始一个拖动。
     * 详见OnStartDragListener.java类的实现
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 要在view任意位置触摸事件发生时启用滑动操作，则直接在sItemViewSwipeEnabled()中返回true就可以了。
     * 或者，你也主动调用ItemTouchHelper.startSwipe(RecyclerView.ViewHolder) 来开始滑动操作。
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 重写getMovementFlags()方法来指定可以支持的拖放和滑动的方向。
     * 使用ItemTouchHelper.makeMovementFlags(int, int)来构造返回的flag。
     * 这里我们启用了上下左右两种方向。
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * 在每次View Holder的状态变成拖拽 (ACTION_STATE_DRAG) 或者 滑动 (ACTION_STATE_SWIPE)的时候被调用。
     * 这是把你的item view变成激活状态的最佳地点。
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            Log.d("－－TouchHelperCallBack－－","您可以进行拖动／滑动");
        }
    }

    /**
     * 在一个view被拖拽然后被放开的时候被调用，同时也会在滑动被取消或者完成ACTION_STATE_IDLE)的时候被调用。
     * 这里是恢复item view idle状态的最佳地方。
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d("－－TouchHelperCallBack－－","拖动／滑动结束");
    }
}
