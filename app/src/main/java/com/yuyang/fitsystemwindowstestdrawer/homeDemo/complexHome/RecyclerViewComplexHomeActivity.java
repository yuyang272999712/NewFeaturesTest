package com.yuyang.fitsystemwindowstestdrawer.homeDemo.complexHome;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * RecyclerView实现淘宝、京东的复杂页面效果
 */

public class RecyclerViewComplexHomeActivity extends AppCompatActivity {
    private View toolbar;
    private RecyclerView recyclerView;

    private GridLayoutManager gridLayoutManager;
    private ComplexAdapter adapter;

    private int toolbarHeight = 0;
    private int green = 0xffa6e9a6;
    private int tuming = 0x00000000;
    private int jianbian = green - tuming;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_home);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(this, 6);
        recyclerView.addItemDecoration(new RightBottomItemDecor());
        adapter = new ComplexAdapter(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbarHeight = toolbar.getHeight();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int overallXScroll = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                overallXScroll = overallXScroll + dy;
                if (toolbarHeight == 0){
                    return;
                }
                if (overallXScroll >= toolbarHeight) {
                    toolbar.setBackgroundColor(Color.argb(255, 166, 233, 166));
                }else {
                    toolbar.setBackgroundColor(Color.argb(255*overallXScroll/toolbarHeight, 166, 233, 166));
                }
            }
        });
    }
}
