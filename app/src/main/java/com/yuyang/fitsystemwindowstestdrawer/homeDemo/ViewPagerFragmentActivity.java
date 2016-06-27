package com.yuyang.fitsystemwindowstestdrawer.homeDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerTransformsAnimation.RotateDownTransformer;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerTransformsAnimation.StackTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager+Fragment实现
 *
 * FragmentPagerAdapter为Fragment容器
 *
 * 这里的方法比较传统，底部导航没有动画，动画效果的可参见 WeChatActivity.class
 */
public class ViewPagerFragmentActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton tab0;
    private RadioButton tab1;
    private RadioButton tab2;
    private RadioButton tab3;

    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_fragment);

        initViews();
        initDatas();
        setAction();
    }

    private void setAction() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tab0.setChecked(true);
                        break;
                    case 1:
                        tab1.setChecked(true);
                        break;
                    case 2:
                        tab2.setChecked(true);
                        break;
                    case 3:
                        tab3.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.home_tab_0:
                        mViewPager.setCurrentItem(0, true);
                        break;
                    case R.id.home_tab_1:
                        mViewPager.setCurrentItem(1, true);
                        break;
                    case R.id.home_tab_2:
                        mViewPager.setCurrentItem(2, true);
                        break;
                    case R.id.home_tab_3:
                        mViewPager.setCurrentItem(3, true);
                        break;
                }
            }
        });
    }

    private void initDatas() {
        fragments.add(new FragmentTab0());
        fragments.add(new FragmentTab1());
        fragments.add(new FragmentTab2());
        fragments.add(new FragmentTab3());

        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        mViewPager.setAdapter(pagerAdapter);
        //TODO yuyang 为ViewPager添加切换动画
        mViewPager.setPageTransformer(true, new StackTransformer());
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.home_view_pager);
        mRadioGroup = (RadioGroup) findViewById(R.id.home_tab_group);
        tab0 = (RadioButton) findViewById(R.id.home_tab_0);
        tab1 = (RadioButton) findViewById(R.id.home_tab_1);
        tab2 = (RadioButton) findViewById(R.id.home_tab_2);
        tab3 = (RadioButton) findViewById(R.id.home_tab_3);
    }
}
