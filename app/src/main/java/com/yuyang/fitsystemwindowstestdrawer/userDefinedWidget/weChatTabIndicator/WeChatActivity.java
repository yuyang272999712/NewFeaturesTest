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
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.weChatTabIndicator.ChangeColorLayout.BottomItemChangeColor;

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

    private List<ChangeColorIconWithTextView> mTabIndicator1 = new ArrayList<ChangeColorIconWithTextView>();
    private List<BottomItemChangeColor> mTabIndicator2 = new ArrayList<BottomItemChangeColor>();

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
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
     * 重置所有tab状态
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator1.size(); i++) {
            mTabIndicator1.get(i).setIconAlpha(0);
            mTabIndicator2.get(i).setIconAlpha(0);
        }
    }
}
