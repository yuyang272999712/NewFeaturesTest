package com.yuyang.fitsystemwindowstestdrawer.tantan.cardFlingView;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.listView.listViewCommonViewHolder.ViewHolderAdapter;
import com.yuyang.fitsystemwindowstestdrawer.tantan.CardMode;

import java.util.Collection;
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

    public void remove(int index) {
        if (index > -1 && index < mCardList.size()) {
            mCardList.remove(index);
            notifyDataSetChanged();
        }
    }

    public void addAll(Collection<CardMode> collection) {
        if (isEmpty()) {
            mCardList.addAll(collection);
            notifyDataSetChanged();
        } else {
            mCardList.addAll(collection);
        }
    }

    @Override
    public boolean isEmpty() {
        return mCardList.isEmpty();
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
        SimpleDraweeView mCardImageView = get(convertView,R.id.portrait);
        TextView mCardName = get(convertView, R.id.card_name);
        TextView mCardImageNum = get(convertView, R.id.card_image_num);
        TextView mCardYear = get(convertView,R.id.card_year);

        Log.i("yuyang______", "CardAdapter getView");
        mCardImageView.setImageURI(Uri.parse(mCardList.get(position).getImages().get(0)));
        mCardName.setText(mCardList.get(position).getName());
        mCardYear.setText(String.valueOf(mCardList.get(position).getYear()));
        return convertView;
    }
}
