package com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/5/9.
 */
public abstract class LoadMoreAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    private static final int ITEM_TYPE_FOOTER = Integer.MIN_VALUE;
    private LoadMoreFooterView loadMoreFooterView;
    public List<T> mItems = new ArrayList<>();

    public LoadMoreAdapter(Context context){
        loadMoreFooterView = new LoadMoreFooterView(context);
    }

    public LoadMoreAdapter(Context context,@NonNull List<T> mItems){
        if(mItems != null){
            this.mItems.addAll(mItems);
        }
        loadMoreFooterView = new LoadMoreFooterView(context);
    }

    public void setmItems(@NonNull List<T> items){
        mItems.clear();
        if(items != null) {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void addmItems(@NonNull List<T> items){
        if(items != null){
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    /**
     * 加载更多完成后调用，更多加载成功的话传入true，反之传入false
     * @param success
     */
    public void setLoadMoreFinish(boolean success){
        loadMoreFooterView.loadFinished(success);
    }

    /**
     * 是否还有更多
     * @param end
     */
    public void setEnd(boolean end){
        loadMoreFooterView.setEnd(end);
    }

    /**
     * 设置加载更多回调
     * @param listener
     */
    public void setOnLoadMoreListener(LoadMoreFooterView.OnLoadMoreListener listener){
        loadMoreFooterView.setOnLoadMoreListener(listener);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_FOOTER){
            return CommonViewHolder.getViewHolder(loadMoreFooterView);
        }else {
            return onCreateHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == ITEM_TYPE_FOOTER){
            //TODO yuyang footerView 控制加载更多
            ((LoadMoreFooterView)holder.getmConvertView()).loadMore();
        }else {
            onBindItemView(holder, mItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(mItems == null || mItems.size() <= 0){
            return 0;
        }else {
            return mItems.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount()-1){//最后一个item
            return ITEM_TYPE_FOOTER;
        }else {
            return getItemType(position);
        }
    }

    /**
     * 特殊处理GridLayoutManager情况footer占整行
     * TODO yuyang 如果不起作用请在RecyclerView上先设置setLayoutManager再setAdapter
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(getItemViewType(position) == ITEM_TYPE_FOOTER){
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    /**
     * StaggeredGridLayoutManager情况下footer占整行
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(CommonViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if(layoutParams != null
                && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){
            if(getItemType(holder.getAdapterPosition()) == ITEM_TYPE_FOOTER){
                StaggeredGridLayoutManager.LayoutParams staggeredLayoutParams = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                staggeredLayoutParams.setFullSpan(true);
            }
        }
    }

    public abstract int getItemType(int position);
    public abstract CommonViewHolder onCreateHolder(ViewGroup parent, int viewType);
    public abstract void onBindItemView(CommonViewHolder holder, T itemBean);
}
