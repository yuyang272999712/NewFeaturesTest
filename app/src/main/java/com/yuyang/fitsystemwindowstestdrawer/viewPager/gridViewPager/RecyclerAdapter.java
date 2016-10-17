package com.yuyang.fitsystemwindowstestdrawer.viewPager.gridViewPager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

import java.util.List;

/**
 * Created by yuyang on 16/10/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private Context mContext;
    private List<GridItem> items;
    private int pageSize;
    private int pageIndex;

    public RecyclerAdapter(Context mContext, List<GridItem> items, int pageSize, int pageIndex){
        this.mContext = mContext;
        this.items = items;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_4_grid);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        ImageView imageView = holder.getViews(R.id.imageView);
        TextView textView = holder.getViews(R.id.textView);

        final GridItem item = items.get(pageIndex*pageSize+position);

        imageView.setImageResource(item.getImgId());
        textView.setText(item.getTitle());

        holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size() > (pageIndex+1)*pageSize?pageSize:(items.size()-pageSize*pageIndex);
    }
}
