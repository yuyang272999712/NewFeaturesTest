package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.colorTrackTextIndicator.ColorTrackView;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.colorTrackTextIndicator.TrackViewSimpleActivity;
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
    private List<String> mTitles = Arrays.asList("Fragment1", "Fragment2Fragment1", "Fragment3", "F4",
            "Fragment5", "Fragment6", "Fragment7", "Fragment8", "Fragment9");
    private List<String> mTitles2 = Arrays.asList("Fragment1", "Fragment2", "Fragment3");

    //系统的tabLayout
    private TabLayout tabLayout;
    //自定义的tab指示器
    private ViewPagerIndicator myIndicator;
    //自定义的tab，类似于系统的tabLayout
    private MyTabLayout myTabLayout;
    //类似于微信底部的
    private List<ColorTrackView> mTrackViewTabs = new ArrayList<ColorTrackView>();

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
        //系统的tabLayout
        tabLayout.setupWithViewPager(viewPager);
        //自定义的tab指示器，可以改成MyTabLayout那样不用指定标题的
        myIndicator.setTabItemTitles(mTitles2);
        myIndicator.setViewPager(viewPager, 0);
        //自定义的tab，类似于系统的tabLayout
        myTabLayout.setViewPager(viewPager, 0);
    }

    private void setAction() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0 && position+1<mTrackViewTabs.size()) {
                    ColorTrackView left = mTrackViewTabs.get(position);
                    ColorTrackView right = mTrackViewTabs.get(position + 1);
                    left.setDirection(ColorTrackView.DIRECTION_RIGHT);
                    right.setDirection(ColorTrackView.DIRECTION_LEFT);
                    left.setProgress(1 - positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void findViews() {
        viewPager = (ViewPager) findViewById(R.id.indicator_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.indicator_tab_layout);
        myIndicator = (ViewPagerIndicator) findViewById(R.id.indicator_my_indicator);
        myTabLayout = (MyTabLayout) findViewById(R.id.indicator_my_tab_layout);
        mTrackViewTabs.add((ColorTrackView) findViewById(R.id.indicator_color_track_1));
        mTrackViewTabs.add((ColorTrackView) findViewById(R.id.indicator_color_track_2));
        mTrackViewTabs.add((ColorTrackView) findViewById(R.id.indicator_color_track_3));
    }

    public void gotoTrackViewSimpleActivity(View view){
        Intent intent = new Intent(this, TrackViewSimpleActivity.class);
        startActivity(intent);
    }
}
