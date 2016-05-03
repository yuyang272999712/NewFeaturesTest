package com.yuyang.fitsystemwindowstestdrawer.listViewHolder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/3/3.
 */
public class ItemView extends RelativeLayout {
    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private ImageView mImageView;

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_view_children, this, true);
        setupChildren();
    }

    public static ItemView inflate(ViewGroup parent) {
        ItemView itemView = (ItemView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return itemView;
    }

    private void setupChildren() {
        mTitleTextView = (TextView) findViewById(R.id.item_titleTextView);
        mDescriptionTextView = (TextView) findViewById(R.id.item_descriptionTextView);
        mImageView = (ImageView) findViewById(R.id.item_imageView);
    }

    public void setItem(Item item) {
        mTitleTextView.setText(item.getmTitle());
        mDescriptionTextView.setText(item.getmDescription());
        mImageView.setImageResource(item.getmImgId());
    }
}
