package com.yuyang.fitsystemwindowstestdrawer.tantan.layoutManager;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;
import com.yuyang.fitsystemwindowstestdrawer.tantan.CardMode;

import java.util.List;

/**
 * Created by yuyang on 2016/12/22.
 */

public class CardAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private List<CardMode> mCardList;
    private Context mContext;

    public CardAdapter(Context context, List<CardMode> cardModes){
        this.mContext = context;
        this.mCardList = cardModes;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getViewHolder(parent, R.layout.item_tantan);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        CardMode cardMode = mCardList.get(position);
        SimpleDraweeView mCardImageView = holder.getViews(R.id.portrait);
        TextView mCardName = holder.getViews(R.id.card_name);
        TextView mCardYear = holder.getViews(R.id.card_year);

        mCardImageView.setImageURI(Uri.parse(mCardList.get(position).getImages().get(0)));
        mCardName.setText(cardMode.getName());
        mCardYear.setText(String.valueOf(cardMode.getYear()));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }
}
