package com.yuyang.fitsystemwindowstestdrawer.viewPager.changeFragmentAdapterList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.PagerFragment;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.transforms.AccordionTransformer;

import java.util.ArrayList;

/**
 * 参考：https://blog.csdn.net/lyabc123456/article/details/79797552
 *
 * FragmentPagerAdapter刷新无效的解决方案
 *  FragmentPagerAdapter的源码当中，可以看到在instantiateItem()方法的内部，它是这样做的：根据tag查找对应的Fragment, 如果找到，
 * 那么就通过当前的Transaction进行attach操作，这个fragment就会显示了，如果没有找到呢，就去getItem()从你的Fragment列表中获取一个
 * 然后Transaction进行add操作。
 */

public class ChangeFragmentAdapterListActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private BaseFragmentPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = new String[] { "First Fragment!",
            "Second Fragment!", "Third Fragment!", "Fourth Fragment!" };
    private int[] mColors = new int[]{Color.parseColor("#FFFF7975"),Color.parseColor("#FF7AC171"),
            Color.parseColor("#FF5688B6"),Color.parseColor("#FFAF5994")};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_view_pager);

        setToolbar();
        initDatas();

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new AccordionTransformer());
    }

    private void initDatas() {
        for (int i=0; i<mTitles.length; i++){
            mFragments.add(PagerFragment.getInstance(mTitles[i], mColors[i]));
        }
        mAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("FragmentPagerAdapter大小发生改变");
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
        menu.add("添加");
        menu.add("减少");
        menu.add("替换");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ("添加".equals(item.getTitle())){
            Fragment fragment = PagerFragment.getInstance("新添加的页面", Color.parseColor("#FFFF7F75"));
            mAdapter.addFragment(fragment);
        }else if ("减少".equals(item.getTitle())){
            if (mFragments.size() > 0){
                mAdapter.removeFragment(0);
            }
        }else {
            Fragment fragment = PagerFragment.getInstance("替换的页面", Color.parseColor("#FFFFF975"));
            mAdapter.replaceFragment(0, fragment);
        }
        return true;
    }
}
