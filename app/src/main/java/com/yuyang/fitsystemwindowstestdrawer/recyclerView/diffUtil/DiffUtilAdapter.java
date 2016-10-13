package com.yuyang.fitsystemwindowstestdrawer.recyclerView.diffUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

import java.util.List;

/**
 * Created by yuyang on 16/10/12.
 */
public class DiffUtilAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private List<DiffutilItem> items;
    private LayoutInflater inflater;

    public DiffUtilAdapter(Context context, List<DiffutilItem> diffutilItems){
        inflater = LayoutInflater.from(context);
        items = diffutilItems;
    }

    public void setDatas(List<DiffutilItem> diffutilItems){
        this.items = diffutilItems;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_image_des_view);
    }

    /**
     * !--yuyang 源码中该方法只是直接调用了onBindViewHolder的两个参数的方法
     *  这里的第三个参数可以接收DiffUtil.Callback的getChangePayload()方法返回的内容
     * @param holder
     * @param position
     * @param payloads
     */
    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()){//payloads一定不为null
            onBindViewHolder(holder, position);
        }else {
            Bundle bundle = (Bundle) payloads.get(0);
            int imgId = bundle.getInt("IMG_ID", 0);
            if (imgId != 0){
                ((ImageView)holder.getViews(R.id.item_imageView)).setImageResource(imgId);
            }
            String des = bundle.getString("DES");
            if (!TextUtils.isEmpty(des)){
                ((TextView)holder.getViews(R.id.item_descriptionTextView)).setText(des);
            }
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        DiffutilItem item = items.get(position);
        ImageView imageView = holder.getViews(R.id.item_imageView);
        TextView title = holder.getViews(R.id.item_titleTextView);
        TextView des = holder.getViews(R.id.item_descriptionTextView);

        imageView.setImageResource(item.getImgId());
        title.setText(item.getTitle());
        des.setText(item.getDes());
    }

    @Override
    public int getItemCount() {
        return items == null?0:items.size();
    }
}
