package com.yuyang.fitsystemwindowstestdrawer.tantan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.listViewHolder.ViewHolderAdapter;

import java.util.List;

/**
 * Created by yuyang on 16/3/15.
 */
public class CardAdapter extends ViewHolderAdapter {
    private Context mContext;
    private List<CardMode> mCardList;

    public CardAdapter(Context mContext, List<CardMode> mCardList){
        this.mContext = mContext;
        this.mCardList = mCardList;
    }

    @Override
    public int getCount() {
        return mCardList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tantan, parent, false);
        }
        ImageView mCardImageView = get(convertView,R.id.helloText);
        TextView mCardName = get(convertView, R.id.card_name);
        TextView mCardImageNum = get(convertView, R.id.card_image_num);
        TextView mCardYear = get(convertView,R.id.card_year);

        Glide.with(mContext)
                .load(mCardList.get(position).getImages().get(0))
                .into(mCardImageView);
        mCardName.setText(mCardList.get(position).getName());
        mCardYear.setText(String.valueOf(mCardList.get(position).getYear()));
        return convertView;
    }
}
