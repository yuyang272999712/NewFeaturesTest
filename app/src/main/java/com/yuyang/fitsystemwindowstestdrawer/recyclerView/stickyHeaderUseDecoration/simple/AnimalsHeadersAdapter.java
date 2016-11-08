package com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeaderUseDecoration.simple;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeaderUseDecoration.StickyRecyclerHeadersAdapter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 实现粘性表头StickyRecyclerHeadersAdapter接口的Adapter
 */

public class AnimalsHeadersAdapter extends RecyclerView.Adapter<CommonViewHolder> implements StickyRecyclerHeadersAdapter<CommonViewHolder> {
    private ArrayList<String> items = new ArrayList<String>();

    public AnimalsHeadersAdapter() {
        //!--yuyang 标示每个Item都有唯一的id标示
        setHasStableIds(true);
    }

    /**自定义方法********************************/

    public void add(String object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, String object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends String> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public String getItem(int position) {
        return items.get(position);
    }

    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });
    }

    /**以下是RecyclerView.Adapter的方法**************************************/

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return CommonViewHolder.getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(getItem(position));
    }

    /**以下是StickyRecyclerHeadersAdapter的方法**************************************/

    @Override
    public long getHeaderId(int position) {
        if (position == 0) {
            return -1;
        } else {
            return getItem(position).charAt(0);
        }
    }

    @Override
    public CommonViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header, parent, false);
        return CommonViewHolder.getViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(CommonViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(String.valueOf(getItem(position).charAt(0)));
        holder.itemView.setBackgroundColor(getRandomColor());
    }

}
