package com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.colorTrackTextIndicator.ColorTrackView;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.colorTrackTextIndicator.TrackViewSimpleActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.userDefinedTab.ViewPagerIndicator;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.userDefinedTabLayout.MyTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ViewPager指示器（类似TabLayout）
 *
 * PagerTabStrip和PagerTitleStrip没人用，太难看了
 */
public class TopPagerIndicatorActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ViewPager viewPager;
    private List<String> mTitles = Arrays.asList("Fragment1", "Fragment2Fragment1", "Fragment3", "F4",
            "Fragment5", "Fragment6", "Fragment7", "Fragment8", "Fragment9");
    private List<String> mTitles2 = Arrays.asList("Fragment1", "Fragment2", "Fragment3");

    private Toolbar mToolbar;

    //系统的tabLayout
    private TabLayout tabLayout;
    //自定义的tab指示器
    private ViewPagerIndicator myIndicator;
    //自定义的tab，类似于系统的tabLayout
    private MyTabLayout myTabLayout;
    //类似于今日头条的
    private List<ColorTrackView> mTrackViewTabs = new ArrayList<ColorTrackView>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_indicator_top);

        setToolbar();
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

        ColorTrackView tabOne = (ColorTrackView) findViewById(R.id.indicator_color_track_1);
        ColorTrackView tabTwo = (ColorTrackView) findViewById(R.id.indicator_color_track_2);
        ColorTrackView tabThree = (ColorTrackView) findViewById(R.id.indicator_color_track_3);
        mTrackViewTabs.add(tabOne);
        mTrackViewTabs.add(tabTwo);
        mTrackViewTabs.add(tabThree);
        tabOne.setOnClickListener(this);
        tabTwo.setOnClickListener(this);
        tabThree.setOnClickListener(this);
    }

    public void gotoTrackViewSimpleActivity(View view){
        Intent intent = new Intent(this, TrackViewSimpleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.indicator_color_track_1:
                mTrackViewTabs.get(0).setProgress(1.0f);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.indicator_color_track_2:
                mTrackViewTabs.get(1).setProgress(1.0f);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.indicator_color_track_3:
                mTrackViewTabs.get(2).setProgress(1.0f);
                viewPager.setCurrentItem(2, false);
                break;
        }
    }

    /**
     * 重置所有微信tab状态
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTrackViewTabs.size(); i++) {
            mTrackViewTabs.get(i).setProgress(0);
        }
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ViewPager头部指示器");
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
