package com.yuyang.fitsystemwindowstestdrawer.recyclerView.decoration;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

/**
 * Created by yuyang on 16/11/7.
 */

public class SimpleTextAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private int itemCount;

    public SimpleTextAdapter(int itemCount){
        this.itemCount = itemCount;
    }

    public void addItemCount(int i){
        this.itemCount += i;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_px_text);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        TextView textView = holder.getViews(R.id.text_view);
        textView.setText("item"+position);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}
