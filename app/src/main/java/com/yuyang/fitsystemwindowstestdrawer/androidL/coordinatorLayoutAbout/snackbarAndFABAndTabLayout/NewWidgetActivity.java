package com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.snackbarAndFABAndTabLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.ListViewFragment;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.NestedScrollViewFragment;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.RecyclerFragment;

/**
 * SnackBar、FloatingActionButton、TabLayout使用
 */

public class NewWidgetActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private FloatingActionButton mFAB;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_widget);

        setToolbar();
        setViewPager();

        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(mFAB, "显示点什么。", Snackbar.LENGTH_LONG);
                resetSnackBar(snackbar);
                snackbar.show();
            }
        });

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i=0; i<mViewPager.getAdapter().getCount(); i++){
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);//!--yuyang TabLayout中ItemTab自定义显示
            if (itemTab!=null){
                itemTab.setCustomView(R.layout.item_tab_layout_custom);
                TextView itemTv = (TextView) itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText(mViewPager.getAdapter().getPageTitle(i));
            }
        }
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    private void setViewPager() {
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
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("SnackBar/FAB/TabLayout");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * !--yuyang SnackBar花式用法
     * @param snackbar
     */
    private void resetSnackBar(Snackbar snackbar){
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_category_0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(imageView, layoutParams);
        snackbarLayout.setBackgroundColor(Color.BLUE);
    }
}
