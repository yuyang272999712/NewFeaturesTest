package com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

/**
 * 只包含RecyclerView的Fragment
 */

public class RecyclerFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_just_recycler_view, null);
        mRecyclerView = (RecyclerView) content.findViewById(R.id.recycler_view);
        return content;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return CommonViewHolder.getViewHolder(parent, R.layout.item_just_text);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                CommonViewHolder viewHolder = (CommonViewHolder) holder;
                TextView textView = viewHolder.getViews(R.id.id_info);
                textView.setText("Item_"+position);
            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });
    }
}
