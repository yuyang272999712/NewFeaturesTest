package com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.changeColorIcon.ChangeColorIconWithTextView;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.changeColorLayout.BottomItemChangeColor;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.colorTrackTextIndicator.TrackViewSimpleActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ViewPager指示器(类似微信底部tab)
 */
public class BottomPagerIndicatorActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ViewPager viewPager;
    private List<String> mTitles = Arrays.asList("Fragment1", "Fragment2", "Fragment3", "Fragment4");
    private Toolbar mToolbar;

    //类似于微信底部的
    private List<ChangeColorIconWithTextView> mTabIndicator1 = new ArrayList<ChangeColorIconWithTextView>();
    private List<BottomItemChangeColor> mTabIndicator2 = new ArrayList<BottomItemChangeColor>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_indicator_bottom);

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
    }

    private void setAction() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
            case R.id.wechat_indicator_1:
            case R.id.wechat_tab0:
                mTabIndicator1.get(0).setIconAlpha(1.0f);
                mTabIndicator2.get(0).setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.wechat_indicator_2:
            case R.id.wechat_tab1:
                mTabIndicator1.get(1).setIconAlpha(1.0f);
                mTabIndicator2.get(1).setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.wechat_indicator_3:
            case R.id.wechat_tab2:
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
            mTabIndicator1.get(i).setIconAlpha(0);
            mTabIndicator2.get(i).setIconAlpha(0);
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
