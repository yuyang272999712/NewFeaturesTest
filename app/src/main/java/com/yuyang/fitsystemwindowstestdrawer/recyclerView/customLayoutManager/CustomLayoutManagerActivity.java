package com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义RecyclerView的LayoutManager
 */

public class CustomLayoutManagerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private int groupSize = 9;
    private int totalSize = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tantan_custom_layout_manager);
        setToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new DiamondLayoutManager(groupSize, true));
        mRecyclerView.setAdapter(new DiamondManagerAdapter(this, totalSize));
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("自定义LayoutManager");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 0 ,"DiamondLayoutManager");
        menu.add(Menu.NONE, 2, 0 ,"FixedLayoutManager");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                mRecyclerView.setLayoutManager(new DiamondLayoutManager(groupSize, true));
                mRecyclerView.setAdapter(new DiamondManagerAdapter(this, totalSize));
                break;
            case 2:
                mRecyclerView.setLayoutManager(new FixedLayoutManager(groupSize));
                mRecyclerView.setAdapter(new FixedManagerAdapter(this, totalSize));
                break;
        }
        return true;
    }
}
