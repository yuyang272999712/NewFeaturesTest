package com.yuyang.fitsystemwindowstestdrawer.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemTouchHelper.ItemTouchHelperAdapter;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemTouchHelper.OnStartDragListener;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.LoadMoreAdapter;

import java.util.Collections;
import java.util.List;

/**
 * 实现ItemTouchHelperAdapter类是为了ItemTouchHelper回调，以便通知adapter数据变化，并作出相应处理
 */
public class RecyclerLoadMoreAdapter extends LoadMoreAdapter<String>
        implements ItemTouchHelperAdapter {

    //拖动事件回调
    private OnStartDragListener dragListener;

    public RecyclerLoadMoreAdapter(Context context, @NonNull List<String> mItems) {
        super(context, mItems);
    }

    public void setDragListener(OnStartDragListener dragListener){
        this.dragListener = dragListener;
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }

    @Override
    public CommonViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_recycler);
        return viewHolder;
    }

    @Override
    public void onBindItemView(CommonViewHolder holder, String itemBean) {
        final CommonViewHolder viewHolder = (CommonViewHolder) holder;
        TextView name = viewHolder.getViews(R.id.item_recycler_name);
        ImageView dragFlag = viewHolder.getViews(R.id.item_recycler_drag_flag);
        name.setText(itemBean);
        if(dragListener != null){
            dragFlag.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(viewHolder);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }
}
