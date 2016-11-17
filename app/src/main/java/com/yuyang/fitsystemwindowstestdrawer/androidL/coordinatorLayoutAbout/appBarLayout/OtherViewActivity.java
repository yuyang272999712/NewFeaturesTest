package com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.appBarLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.ListViewFragment;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.NestedScrollViewFragment;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.RecyclerFragment;

/**
 * AppBarLayout配合其他View使用
 */

public class OtherViewActivity extends AppCompatActivity {
    private ImageView mBackIcon;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout_other_view);

        mBackIcon = (ImageView) findViewById(R.id.back_icon);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new RecyclerFragment();
                        break;
                    case 1:
                        fragment = new NestedScrollViewFragment();
                        break;
                    case 2:
                        fragment = new ListViewFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "Title"+position;
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
