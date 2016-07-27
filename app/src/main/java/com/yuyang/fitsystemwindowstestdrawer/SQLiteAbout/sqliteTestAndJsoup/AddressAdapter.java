package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.AddressBaseBean;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/7/27.
 */
public class AddressAdapter<T extends AddressBaseBean> extends RecyclerView.Adapter<CommonViewHolder> {
    private List<T> addresses = new ArrayList<>();
    private OnItemClickCallBack callBack;

    public AddressAdapter(List<T> addresses, OnItemClickCallBack callBack){
        if (addresses != null){
            this.addresses = addresses;
        }
        this.callBack = callBack;
    }

    public void setAddresses(List<T> addresses){
        if (addresses != null){
            this.addresses = addresses;
            notifyDataSetChanged();
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_just_text);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        TextView name = holder.getViews(R.id.id_info);
        name.setText(addresses.get(position).getName());
        holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null){
                    callBack.onItemClickCallBack(addresses.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public interface OnItemClickCallBack{
        void onItemClickCallBack(AddressBaseBean address);
    }
}
