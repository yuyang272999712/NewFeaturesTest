package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTabLayout.MyTabLayout;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTab.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ViewPager指示器 Tab 和 原点指示器
 */
public class PagerIndicatorActivity extends AppCompatActivity {
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<String> mTitles = Arrays.asList("Fragment1", "Fragment2Fragment1", "Fragment3", "F4",
            "Fragment5", "Fragment6", "Fragment7", "Fragment8", "Fragment9");
    private List<String> mTitles2 = Arrays.asList("Fragment1", "Fragment2", "Fragment3");

    private ViewPagerIndicator myIndicator;
    private MyTabLayout myTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_indicator);

        findViews();
        initDatas();
        setAction();
    }

    private void initDatas() {
        for (String title:mTitles){
            PagerFragment fragment = PagerFragment.getInstance(title);
            fragments.add(fragment);
        }
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        };

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        myIndicator.setTabItemTitles(mTitles2);
        myIndicator.setViewPager(viewPager, 0);

        myTabLayout.setViewPager(viewPager, 0);
    }

    private void setAction() {

    }

    private void findViews() {
        viewPager = (ViewPager) findViewById(R.id.indicator_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.indicator_tab_layout);
        myIndicator = (ViewPagerIndicator) findViewById(R.id.indicator_my_indicator);
        myTabLayout = (MyTabLayout) findViewById(R.id.indicator_my_tab_layout);
    }
}
