package com.yuyang.fitsystemwindowstestdrawer.viewPager.gridViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.List;

/**
 * Created by yuyang on 16/10/14.
 */

public class GridAdapter extends BaseAdapter {
    private List<GridItem> items;
    private int pageSize;
    private int pageIndex;
    private LayoutInflater inflater;

    public GridAdapter(Context context, List<GridItem> datas, int pageSize, int pageIndex){
        inflater = LayoutInflater.from(context);
        this.items = datas;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页,如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项就返回几,(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return items.size() > (pageIndex+1)*pageSize ? pageSize : (items.size() - pageIndex * pageSize);
    }

    @Override
    public GridItem getItem(int position) {
        return items.get(pageIndex*pageSize+position);
    }

    @Override
    public long getItemId(int position) {
        return pageIndex*pageSize+position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_4_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.iv.setImageResource(getItem(position).getImgId());
        viewHolder.tv.setText(getItem(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }
}
