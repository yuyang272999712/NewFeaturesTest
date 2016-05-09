package com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipRefresh.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自带加载更多，可以包含header的基础Adapter
 */
class BaseLoadMoreRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = Integer.MIN_VALUE;
    public static final int TYPE_FOOTER = Integer.MAX_VALUE;

    private List<T> mDatas = new ArrayList<>();
    private Context mContext;
    private boolean hasHeader = false;
    private boolean hasFooter = true;
    private LoadMoreFooterView footerView;
    private int headerLayoutId;

    public BaseLoadMoreRecyclerAdapter(Context mContext, List<T> datas){
        this.mDatas = datas;
        this.mContext = mContext;
//        initFooterView();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
