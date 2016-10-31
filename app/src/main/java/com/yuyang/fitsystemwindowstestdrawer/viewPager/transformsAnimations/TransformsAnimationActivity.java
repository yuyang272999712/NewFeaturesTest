package com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.AccordionTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.BackgroundToForegroundTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.CubeInTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.CubeOutTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.DefaultTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.DepthPageTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.FlipHorizontalTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.FlipVerticalTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.ForegroundToBackgroundTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.RotateDownTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.RotateUpTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.ScaleInOutTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.StackTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.TabletTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.ZoomInTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.ZoomOutSlideTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.ZoomOutTranformer;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager切换动画效果综合
 */

public class TransformsAnimationActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = new String[] { "First Fragment!",
            "Second Fragment!", "Third Fragment!", "Fourth Fragment!" };
    private int[] mColors = new int[]{Color.parseColor("#FFFF7975"),Color.parseColor("#FF7AC171"),
            Color.parseColor("#FF5688B6"),Color.parseColor("#FFAF5994")};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_view_pager);

        setToolbar();
        initDatas();

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new AccordionTransformer());
    }

    private void initDatas() {
        for (int i=0; i<mTitles.length; i++){
            mFragments.add(PagerFragment.getInstance(mTitles[i], mColors[i]));
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ViewPager切换动画效果");
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
        getMenuInflater().inflate(R.menu.transforms_animations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.accordion_transformer:
                mViewPager.setPageTransformer(true, new AccordionTransformer());
                break;
            case R.id.background_to_foreground_transformer:
                mViewPager.setPageTransformer(true, new BackgroundToForegroundTransformer());
                break;
            case R.id.foreground_to_background_transformer:
                mViewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());
                break;
            case R.id.cube_in_transformer:
                mViewPager.setPageTransformer(true, new CubeInTransformer());
                break;
            case R.id.cube_out_transformer:
                mViewPager.setPageTransformer(true, new CubeOutTransformer());
                break;
            case R.id.default_transformer:
                mViewPager.setPageTransformer(true, new DefaultTransformer());
                break;
            case R.id.depth_page_transformer:
                mViewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case R.id.flip_horizontal_transformer:
                mViewPager.setPageTransformer(true, new FlipHorizontalTransformer());
                break;
            case R.id.flip_vertical_transformer:
                mViewPager.setPageTransformer(true, new FlipVerticalTransformer());
                break;
            case R.id.rotate_down_transformer:
                mViewPager.setPageTransformer(true, new RotateDownTransformer());
                break;
            case R.id.rotate_up_transformer:
                mViewPager.setPageTransformer(true, new RotateUpTransformer());
                break;
            case R.id.scale_in_out_transformer:
                mViewPager.setPageTransformer(true, new ScaleInOutTransformer());
                break;
            case R.id.stack_transformer:
                mViewPager.setPageTransformer(true, new StackTransformer());
                break;
            case R.id.tablet_transformer:
                mViewPager.setPageTransformer(true, new TabletTransformer());
                break;
            case R.id.zoom_in_transformer:
                mViewPager.setPageTransformer(true, new ZoomInTransformer());
                break;
            case R.id.zoom_out_transformer:
                mViewPager.setPageTransformer(true, new ZoomOutTranformer());
                break;
            case R.id.zoom_out_slide_transformer:
                mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                break;
        }
        return true;
    }
}
