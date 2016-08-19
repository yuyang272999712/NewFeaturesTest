package com.yuyang.fitsystemwindowstestdrawer.androidL.activitySwitchAnim.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/5/3.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AdapterViewHolder> {
    private List<Integer> imageIds = new ArrayList<>();
    private ItemClickListener clickListener;

    public RecyclerAdapter(List<Integer> imageIds, ItemClickListener clickListener){
        this.imageIds = imageIds;
        this.clickListener = clickListener;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, final int position) {
        holder.imageView.setImageResource(imageIds.get(position));
        if(clickListener != null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageIds.size();
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.option_item_img);
        }
    }
}
