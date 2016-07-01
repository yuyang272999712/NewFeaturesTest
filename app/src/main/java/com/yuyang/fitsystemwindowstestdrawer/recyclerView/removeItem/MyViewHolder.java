package com.yuyang.fitsystemwindowstestdrawer.recyclerView.removeItem;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/7/1.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView txt;
    public ImageView img;
    public LinearLayout layout;
    public MyViewHolder(View itemView) {
        super(itemView);
        txt= (TextView) itemView.findViewById(R.id.item_recycler_txt);
        img= (ImageView) itemView.findViewById(R.id.item_delete_img);
        layout= (LinearLayout) itemView.findViewById(R.id.item_recycler_ll);
    }
}
