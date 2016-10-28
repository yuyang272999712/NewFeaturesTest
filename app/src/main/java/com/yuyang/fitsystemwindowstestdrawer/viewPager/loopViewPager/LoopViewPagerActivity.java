package com.yuyang.fitsystemwindowstestdrawer.viewPager.loopViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Banner图展示：无限循环的ViewPager
 */

public class LoopViewPagerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private AutoLoopViewPager mAutoLoopVp;//自动循环播放的ViewPager
    private LoopViewPager mLoopVp;//无限滑动的ViewPager

    private PagerAdapter mAdapter;
    private int[] imageViewIds = new int[]{R.mipmap.banner_background, R.mipmap.color_matrix_test1, R.mipmap.color_matrix_test3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_view_pager);

        setTooBar();

        mAutoLoopVp = (AutoLoopViewPager) findViewById(R.id.loop_view_pager_auto);
        mLoopVp = (LoopViewPager) findViewById(R.id.loop_view_pager);

        mAdapter = new BannerPagerAdapter();
        mAutoLoopVp.setAdapter(mAdapter);
        mLoopVp.setAdapter(mAdapter);
    }

    private void setTooBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("无限循环的ViewPager");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class BannerPagerAdapter extends PagerAdapter{
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(container.getContext());
            item.setImageResource(imageViewIds[position]);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return imageViewIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
