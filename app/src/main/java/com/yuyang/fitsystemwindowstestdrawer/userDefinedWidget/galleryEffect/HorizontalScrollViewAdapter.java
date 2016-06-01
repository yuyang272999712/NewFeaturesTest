package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.galleryEffect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.List;

/**
 * 自定义HorizontalScrollView的适配器
 */
public class HorizontalScrollViewAdapter  {
    private Context mContext;
    private LayoutInflater inflater;
    private List<Integer> mDatas;

    public HorizontalScrollViewAdapter(Context mContext, List<Integer> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mContext);
    }

    public Object getItem(int position){
        return mDatas.get(position);
    }

    public int getCount(){
        return mDatas.size();
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        if(convertView != null){
            holder = (ViewHolder) convertView.getTag();
        }else {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_4_gallery, parent, false);
            holder.mImg = (ImageView) convertView.findViewById(R.id.gallery_item_image);
            holder.mText = (TextView) convertView.findViewById(R.id.gallery_item_text);
            convertView.setTag(holder);
        }
        holder.mText.setText("Some info"+position);
        holder.mImg.setImageResource(mDatas.get(position));
        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }
}
