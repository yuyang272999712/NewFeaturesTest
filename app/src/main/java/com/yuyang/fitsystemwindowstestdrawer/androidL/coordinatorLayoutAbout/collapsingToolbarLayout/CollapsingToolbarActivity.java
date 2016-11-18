package com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.collapsingToolbarLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.changeSizeViewPager.PicPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * CollapsingToolbarLayout与CoordinatorLayout配合使用
 */

public class CollapsingToolbarActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private ViewPager mViewPager;

    private int[] pages = new int[]{R.mipmap.color_matrix_test1, R.mipmap.h, R.mipmap.color_matrix_test3, R.mipmap.j, R.mipmap.color_matrix_test1};
    private List<View> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar);

        setToolbar();
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle("标题");
        setViewPager();

        toolbarLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
    }

    private void setViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        for (int i=0; i<pages.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setAdjustViewBounds(true);//!--yuyang 保证ImageView的在wrap_content的情况下能够显示全图
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(pages[i]);
            imageView.setTag(i);
            imageViews.add(imageView);
        }
        mViewPager.setAdapter(new PicPagerAdapter(imageViews));
        mViewPager.setOffscreenPageLimit(1);
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
