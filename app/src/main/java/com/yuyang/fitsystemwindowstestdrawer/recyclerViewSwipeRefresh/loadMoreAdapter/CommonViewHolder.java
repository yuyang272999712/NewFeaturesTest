package com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.loadMoreAdapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用ViewHolder
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    private SparseArrayCompat<View> mViews;
    private View mConvertView;

    private CommonViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArrayCompat<>();
        this.mConvertView = itemView;
    }

    public static CommonViewHolder getViewHolder(View itemView){
        CommonViewHolder viewHolder = new CommonViewHolder(itemView);
        return viewHolder;
    }

    public static CommonViewHolder getViewHolder(ViewGroup parent, int layoutId){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        CommonViewHolder viewHolder = new CommonViewHolder(itemView);
        return viewHolder;
    }

    public <T extends View> T getViews(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public View getmConvertView(){
        return mConvertView;
    }
}
