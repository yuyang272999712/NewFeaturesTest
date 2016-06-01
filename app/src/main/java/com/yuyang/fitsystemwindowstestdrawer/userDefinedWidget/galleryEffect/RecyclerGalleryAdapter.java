package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.galleryEffect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.loadMoreAdapter.CommonViewHolder;

import java.util.List;

/**
 * Created by yuyang on 16/5/23.
 */
public class RecyclerGalleryAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private List<Integer> mDatas;

    public RecyclerGalleryAdapter(List<Integer> datas){
        this.mDatas = datas;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_4_gallery, parent, false);
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        TextView textView = holder.getViews(R.id.gallery_item_text);
        ImageView imageView = holder.getViews(R.id.gallery_item_image);
        textView.setText("Some info"+position);
        imageView.setImageResource(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
