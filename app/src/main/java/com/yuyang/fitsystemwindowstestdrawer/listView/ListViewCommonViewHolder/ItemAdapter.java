package com.yuyang.fitsystemwindowstestdrawer.listView.listViewCommonViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.List;

/**
 * 使用通用的ViewHolder
 */
public class ItemAdapter extends ViewHolderAdapter {
    private List<Item> items;
    private Context context;

    public ItemAdapter(Context context, List<Item> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image_des_view, parent, false);
        }
        ImageView imageView = get(convertView, R.id.item_imageView);
        TextView title = get(convertView, R.id.item_titleTextView);
        TextView des = get(convertView, R.id.item_descriptionTextView);

        Item item = getItem(position);
        imageView.setImageResource(item.getmImgId());
        title.setText(item.getmTitle());
        des.setText(item.getmDescription());

        return convertView;
    }
}
