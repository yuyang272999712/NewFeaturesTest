package com.yuyang.fitsystemwindowstestdrawer.effect360AppIntroduce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTab.ViewPagerIndicator;

import java.util.Arrays;
import java.util.List;

/**
 * 模拟360软件介绍页面
 */
public class AppIntroduce360Activity extends AppCompatActivity {
    private List<String> mTitles = Arrays.asList("简介", "评价", "相关");
    private ViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private Fragment[] mFragments = new Fragment[mTitles.size()];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_360_app_introduce);

        initViews();
        initDatas();
    }

    private void initDatas() {
        for (int i = 0; i < mTitles.size(); i++) {
            if (i == 1){
                mFragments[i] = ListViewFragment.newInstance(mTitles.get(i));
            }else {
                mFragments[i] = ScrollViewFragment.newInstance(mTitles.get(i));
            }
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);

        mIndicator.setTabItemTitles(mTitles);
        mIndicator.setViewPager(mViewPager, 0);
    }

    private void initViews() {
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
    }
}
