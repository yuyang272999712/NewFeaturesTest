package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.CircleMenu.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 圆形菜单的适配器
 */

public class MenuItemAdapter extends BaseAdapter {
    private int[] mItemImgs;
    private LayoutInflater mInflater;

    public MenuItemAdapter(@NonNull Context context, int[] imgs){
        mInflater = LayoutInflater.from(context);
        if (imgs != null){
            mItemImgs = imgs;
        }else {
            mItemImgs = new int[]{};
        }
    }

    @Override
    public int getCount() {
        return mItemImgs.length;
    }

    @Override
    public Integer getItem(int position) {
        return mItemImgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.custom_layout_circle_menu_item, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_circle_menu_item_image);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(getItem(position));
        return view;
    }
}
