package com.yuyang.fitsystemwindowstestdrawer.listView.swipeListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.listView.listViewCommonViewHolder.Item;
import com.yuyang.fitsystemwindowstestdrawer.listView.swipeListView.swipeLib.Attributes;
import com.yuyang.fitsystemwindowstestdrawer.listView.swipeListView.swipeLib.SwipeItemMangerImpl;
import com.yuyang.fitsystemwindowstestdrawer.listView.swipeListView.swipeLib.SwipeItemMangerInterface;
import com.yuyang.fitsystemwindowstestdrawer.listView.swipeListView.swipeLib.SwipeLayout;

import java.util.List;

/**
 * 可侧滑的adapter
 * 也可以不实现SwipeItemMangerInterface接口（但是这样就没法管理已侧滑打开的item了）
 */

public class SwipeAdapter extends BaseAdapter implements SwipeItemMangerInterface{
    private List<Item> items;
    private Context context;
    //ZHU yuyang
    private SwipeItemMangerImpl swipeItemManger;//侧滑管理类

    public SwipeAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
        //ZHU yuyang
        swipeItemManger = new SwipeItemMangerImpl(this);
        swipeItemManger.closeAllItems();
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
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_swipe_view, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_imageView);
            holder.title = (TextView) convertView.findViewById(R.id.item_titleTextView);
            holder.des = (TextView) convertView.findViewById(R.id.item_descriptionTextView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = getItem(position);
        holder.imageView.setImageResource(item.getmImgId());
        holder.title.setText(item.getmTitle());
        holder.des.setText(item.getmDescription());
        //ZHU yuyang
        swipeItemManger.bind(convertView, position);
        return convertView;
    }

    @Override
    public void openItem(int position) {
        swipeItemManger.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        swipeItemManger.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        swipeItemManger.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        swipeItemManger.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return swipeItemManger.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return swipeItemManger.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        swipeItemManger.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return swipeItemManger.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return swipeItemManger.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        swipeItemManger.setMode(mode);
    }

    private class ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView des;
    }
}
