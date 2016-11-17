package com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.appBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.ListViewFragment;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.NestedScrollViewFragment;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.RecyclerFragment;

/**
 * AppBarLayout控件应用
 *      注意：AppBarLayout必须与CoordinatorLayout协同使用才会有效果，因为CoordinatorLayout对Behavior的支持
 */

public class AppBarLayoutActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);

        setToolbar();
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

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

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("配合TabLayout的传统用法");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "配合其他View使用");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1){
            Intent intent = new Intent(this, OtherViewActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
