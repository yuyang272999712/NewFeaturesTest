package com.yuyang.fitsystemwindowstestdrawer.viewPager.changeSizeViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager大小变换
 */

public class ChangeSizePagerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;

    private int[] pages = new int[]{R.mipmap.color_matrix_test1, R.mipmap.h, R.mipmap.color_matrix_test3, R.mipmap.j, R.mipmap.color_matrix_test1};
    private List<View> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_size_view_pager);

        setToolbar();

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
        mToolbar.setTitle("ViewPager每页大小不一");
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
