package com.yuyang.fitsystemwindowstestdrawer.listViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by yuyang on 16/3/3.
 */
public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, List<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView = (ItemView)convertView;
        if(null == itemView){
            itemView = ItemView.inflate(parent);
        }
        itemView.setItem(getItem(position));
        return itemView;
    }
}
