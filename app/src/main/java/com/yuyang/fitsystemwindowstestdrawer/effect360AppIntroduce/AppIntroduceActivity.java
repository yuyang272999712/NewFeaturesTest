package com.yuyang.fitsystemwindowstestdrawer.effect360AppIntroduce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

/**
 * 通过现有控件，模拟360软件介绍页面
 */
public class AppIntroduceActivity extends AppCompatActivity {
    private List<String> mTitles = Arrays.asList("简介", "评价", "相关");
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentPagerAdapter mAdapter;
    private Fragment[] mFragments = new Fragment[mTitles.size()];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_introduce);

        initViews();
        initDatas();
    }

    private void initDatas() {
        for (int i = 0; i < mTitles.size(); i++) {
            mFragments[i] = RecyclerViewFragment.newInstance(mTitles.get(i));
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

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        };

        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViews() {
        tabLayout = (TabLayout) findViewById(R.id.app_introduce_tab);
        viewPager = (ViewPager) findViewById(R.id.app_introduce_view_pager);
    }
}
