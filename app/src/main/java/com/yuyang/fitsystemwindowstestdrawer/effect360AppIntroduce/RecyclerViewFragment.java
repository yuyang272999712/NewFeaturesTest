package com.yuyang.fitsystemwindowstestdrawer.effect360AppIntroduce;

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
import com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.loadMoreAdapter.CommonViewHolder;

/**
 * RecyclerView的情况
 */
public class RecyclerViewFragment extends Fragment {
    private String TITLE = "TITLE";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    public static RecyclerViewFragment newInstance(String title){
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            TITLE = getArguments().getString("title");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                CommonViewHolder holder = CommonViewHolder.getViewHolder(parent, R.layout.item_just_text);
                return holder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                CommonViewHolder viewHolder = (CommonViewHolder) holder;
                TextView textView = viewHolder.getViews(R.id.id_info);
                textView.setText(TITLE+"-->"+position);
            }

            @Override
            public int getItemCount() {
                return 50;
            }
        });
        return view;
    }

}
