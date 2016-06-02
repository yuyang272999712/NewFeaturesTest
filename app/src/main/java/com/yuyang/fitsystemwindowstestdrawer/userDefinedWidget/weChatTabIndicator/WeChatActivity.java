package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.weChatTabIndicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.weChatTabIndicator.ChangeColorIcon.ChangeColorIconWithTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信底部图标界面
 */
public class WeChatActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;

    private String[] mTitles = new String[] { "First Fragment!",
            "Second Fragment!", "Third Fragment!", "Fourth Fragment!" };

    private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);

        viewPager = (ViewPager) findViewById(R.id.wechat_viewpager);

        initDatas();

        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void initDatas() {
        for (String title:mTitles){
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            TabFragment fragment = new TabFragment();
            fragment.setArguments(bundle);
            mTabs.add(fragment);
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };

        initTabIndicator();
    }

    private void initTabIndicator() {
        ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_1);
        ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_2);
        ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_3);
        ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.wechat_indicator_4);

        mTabIndicator.add(one);
        mTabIndicator.add(two);
        mTabIndicator.add(three);
        mTabIndicator.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithTextView left = mTabIndicator.get(position);
            ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.wechat_indicator_1:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.wechat_indicator_2:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.wechat_indicator_3:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.wechat_indicator_4:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                viewPager.setCurrentItem(3, false);
                break;

        }
    }

    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator.size(); i++) {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }
}
