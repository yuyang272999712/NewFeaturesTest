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
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.ChangeColorIcon.ChangeColorIconWithTextView;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.ChangeColorLayout.BottomItemChangeColor;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.colorTrackTextIndicator.ColorTrackView;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.colorTrackTextIndicator.TrackViewSimpleActivity;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTab.ViewPagerIndicator;
import com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTabLayout.MyTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ViewPager指示器
 *
 * PagerTabStrip和PagerTitleStrip没人用，太难看了
 */
public class PagerIndicatorActivity extends AppCompatActivity implements View.OnClickListener {
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
    //类似于今日头条的
    private List<ColorTrackView> mTrackViewTabs = new ArrayList<ColorTrackView>();
    //类似于微信底部的
    private List<ChangeColorIconWithTextView> mTabIndicator1 = new ArrayList<ChangeColorIconWithTextView>();
    private List<BottomItemChangeColor> mTabIndicator2 = new ArrayList<BottomItemChangeColor>();

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
                //!--yuyang 设置PagerView变化tab也要跟着变化
                if (positionOffset > 0 && position+1<mTabIndicator1.size()) {
                    ChangeColorIconWithTextView left = mTabIndicator1.get(position);
                    ChangeColorIconWithTextView right = mTabIndicator1.get(position + 1);
                    left.setIconAlpha(1 - positionOffset);
                    right.setIconAlpha(positionOffset);

                    BottomItemChangeColor leftTab = mTabIndicator2.get(position);
                    BottomItemChangeColor rightTab = mTabIndicator2.get(position+1);
                    leftTab.setIconAlpha(1 - positionOffset);
                    rightTab.setIconAlpha(positionOffset);
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

        ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_1);
        ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_2);
        ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_3);
        ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_4);
        mTabIndicator1.add(one);
        mTabIndicator1.add(two);
        mTabIndicator1.add(three);
        mTabIndicator1.add(four);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        //初始化第一个选中
        one.setIconAlpha(1.0f);

        BottomItemChangeColor tab0 = (BottomItemChangeColor) findViewById(R.id.wechat_tab0);
        BottomItemChangeColor tab1 = (BottomItemChangeColor) findViewById(R.id.wechat_tab1);
        BottomItemChangeColor tab2 = (BottomItemChangeColor) findViewById(R.id.wechat_tab2);
        BottomItemChangeColor tab3 = (BottomItemChangeColor) findViewById(R.id.wechat_tab3);
        mTabIndicator2.add(tab0);
        mTabIndicator2.add(tab1);
        mTabIndicator2.add(tab2);
        mTabIndicator2.add(tab3);
        tab0.setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        //初始化第一个选中
        tab0.setIconAlpha(1);
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
            case R.id.wechat_indicator_1:
            case R.id.wechat_tab0:
                mTrackViewTabs.get(0).setProgress(1.0f);
                mTabIndicator1.get(0).setIconAlpha(1.0f);
                mTabIndicator2.get(0).setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.indicator_color_track_2:
            case R.id.wechat_indicator_2:
            case R.id.wechat_tab1:
                mTrackViewTabs.get(1).setProgress(1.0f);
                mTabIndicator1.get(1).setIconAlpha(1.0f);
                mTabIndicator2.get(1).setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.indicator_color_track_3:
            case R.id.wechat_indicator_3:
            case R.id.wechat_tab2:
                mTrackViewTabs.get(2).setProgress(1.0f);
                mTabIndicator1.get(2).setIconAlpha(1.0f);
                mTabIndicator2.get(2).setIconAlpha(1.0f);
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.wechat_indicator_4:
            case R.id.wechat_tab3:
                mTabIndicator1.get(3).setIconAlpha(1.0f);
                mTabIndicator2.get(3).setIconAlpha(1.0f);
                viewPager.setCurrentItem(3, false);
                break;

        }
    }

    /**
     * 重置所有微信tab状态
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator1.size(); i++) {
            mTrackViewTabs.get(i).setProgress(0);
            mTabIndicator1.get(i).setIconAlpha(0);
            mTabIndicator2.get(i).setIconAlpha(0);
        }
    }
}
