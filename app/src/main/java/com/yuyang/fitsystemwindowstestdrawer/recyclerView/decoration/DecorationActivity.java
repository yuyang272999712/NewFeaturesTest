package com.yuyang.fitsystemwindowstestdrawer.recyclerView.decoration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import static com.yuyang.fitsystemwindowstestdrawer.recyclerView.decoration.DividerLinearItemDecor.VERTICAL_LIST;

/**
 * RecyclerView的ItemView装饰:ItemDecoration
 */

public class DecorationActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private DividerLinearItemDecor linearItemDecor;
    private DividerGridItemDecoration gridItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_recycler_view);

        setToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        gridLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this);
        linearItemDecor = new DividerLinearItemDecor(this, VERTICAL_LIST, 10, ContextCompat.getColor(this, R.color.state_2));
        gridItemDecoration = new DividerGridItemDecoration(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(linearItemDecor);

        adapter = new SimpleTextAdapter(100);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("切换布局");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        recyclerView.removeItemDecoration(gridItemDecoration);
        recyclerView.removeItemDecoration(linearItemDecor);
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(linearItemDecor);
        }else {
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.addItemDecoration(gridItemDecoration);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ItemDecoration RecyclerView的ItemView装饰");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
