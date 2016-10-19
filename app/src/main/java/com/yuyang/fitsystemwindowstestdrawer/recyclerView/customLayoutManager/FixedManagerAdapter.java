package com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

/**
 * 平铺Manager的adapter
 */

public class FixedManagerAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private int totalSize;
    private Context mContext;

    public FixedManagerAdapter(Context context, int totalSize){
        this.mContext = context;
        this.totalSize = totalSize;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_just_text_bg);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        final TextView textView = holder.getViews(R.id.text_view);
        textView.setText("菜单"+position);
        holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return totalSize;
    }
}
