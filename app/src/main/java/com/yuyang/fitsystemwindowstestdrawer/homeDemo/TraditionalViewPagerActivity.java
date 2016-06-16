package com.yuyang.fitsystemwindowstestdrawer.homeDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 传统的ViewPager实现
 *
 * 不推荐这么用，这样所有ViewPager的View事物都要放在这个Activity中，代码太过复杂了
 */
public class TraditionalViewPagerActivity extends Activity implements View.OnClickListener {
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews;//适配器的内容
    private LayoutInflater mInflater;
    /**
     * 底部四个按钮
     */
    private LinearLayout mTab0;
    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private LinearLayout mTab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional_view_pager);

        mInflater = LayoutInflater.from(this);
        findViews();
        setAction();
        initDatas();
    }

    private void setAction() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                resetTabBtn();
                switch (position) {
                    case 0:
                        ((ImageView) mTab0.findViewById(R.id.tab_img_chat))
                                .setImageResource(R.mipmap.tab_chat1);
                        break;
                    case 1:
                        ((ImageView) mTab1.findViewById(R.id.tab_img_contact))
                                .setImageResource(R.mipmap.tab_contact1);
                        break;
                    case 2:
                        ((ImageView) mTab2.findViewById(R.id.tab_img_found))
                                .setImageResource(R.mipmap.tab_found1);
                        break;
                    case 3:
                        ((ImageView) mTab3.findViewById(R.id.tab_img_me))
                                .setImageResource(R.mipmap.tab_me1);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        mTab0.setOnClickListener(this);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
    }

    /**
     * 重置所有tab
     */
    private void resetTabBtn() {
        ((ImageView) mTab0.findViewById(R.id.tab_img_chat))
                .setImageResource(R.mipmap.tab_chat2);
        ((ImageView) mTab1.findViewById(R.id.tab_img_contact))
                .setImageResource(R.mipmap.tab_contact2);
        ((ImageView) mTab2.findViewById(R.id.tab_img_found))
                .setImageResource(R.mipmap.tab_found2);
        ((ImageView) mTab3.findViewById(R.id.tab_img_me))
                .setImageResource(R.mipmap.tab_me2);
    }

    private void initDatas() {
        mAdapter = new PagerAdapter() {
            // PagerAdapter只缓存三个View，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            //来判断显示的是否是同一个View，这里我们将两个参数相比较返回即可
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        mViewPager.setAdapter(mAdapter);

        resetTabBtn();
        ((ImageView) mTab0.findViewById(R.id.tab_img_chat))
                .setImageResource(R.mipmap.tab_chat1);
    }

    private void findViews() {
        mViewPager = (ViewPager) findViewById(R.id.home_view_pager);
        mTab0 = (LinearLayout) findViewById(R.id.home_tab_0);
        mTab1 = (LinearLayout) findViewById(R.id.home_tab_1);
        mTab2 = (LinearLayout) findViewById(R.id.home_tab_2);
        mTab3 = (LinearLayout) findViewById(R.id.home_tab_3);

        mViews = new ArrayList<View>();
        View first = mInflater.inflate(R.layout.fragment_tab_00, null);
        View second = mInflater.inflate(R.layout.fragment_tab_01, null);
        View third = mInflater.inflate(R.layout.fragment_tab_02, null);
        View fourth = mInflater.inflate(R.layout.fragment_tab_03, null);
        mViews.add(first);
        mViews.add(second);
        mViews.add(third);
        mViews.add(fourth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_tab_0:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.home_tab_1:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.home_tab_2:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.home_tab_3:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
}
