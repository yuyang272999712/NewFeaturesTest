package com.yuyang.fitsystemwindowstestdrawer.recyclerView.removeItem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.ViewInjectUtils;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ContentView;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧滑删除的Item
 */
@ContentView(R.layout.activity_recycler_remove_item)
public class RecyclerViewRemoveItemActivity extends AppCompatActivity {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.remove_item_recycler_view)
    private RecyclerViewSlidingItem recyclerView;

    private List<Integer> lists = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);

        initData();

        toolbar.setTitle("侧滑删除的Item");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(this, lists);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 1; i < 20; i++) {
            lists.add(i);
        }
    }
}
