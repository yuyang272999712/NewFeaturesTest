package com.yuyang.fitsystemwindowstestdrawer.viewPager.changeAdapterList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考：https://blog.csdn.net/lyabc123456/article/details/79797552
 *
 * PagerAdapter 刷新的问题
 *  ViewPager 的 PagerAdapter 在数据源更新后，不会自动更新视图
 * PagerAdapter 的 notifyDataSetChanged()方法后会调用ViewPager的dataSetChanged()方法，该函数将使用PagerAdapter的
 * getItemPosition() 的返回值来进行判断，如果为 POSITION_UNCHANGED，则什么都不做；如果为 POSITION_NONE，则调用
 * PagerAdapter.destroyItem() 来去掉该对象，并设置为需要刷新 (needPopulate = true) 以便触发PagerAdapter.instantiateItem()
 * 来生成新的对象。
 * PagerAdapter的 getItemPosition()方法默认返回 POSITION_UNCHANGED ，从而对应的View不会重新加载，数据也就不会发生改变，
 * 我们需要对发生改变了的View在getItemPosition()返回 POSITION_NONE。
 */

public class ChangeAdapterListActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private int[] imageIds = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f, R.mipmap.g };
    private List<View> pages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_view_pager);

        setToolbar();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        initDatas();

        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(pages.get(position));
                return pages.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(pages.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                LogUtils.e("PagerAdapter发生变化", "该方法只会在调用了notifyDataSetChanged()方法后调用"+object.toString());
                return POSITION_NONE;
            }

            @Override
            public void finishUpdate(ViewGroup container) {
                super.finishUpdate(container);

            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("添加");
        menu.add("减少");
        menu.add("更新");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ("添加".equals(item.getTitle())){
            View itemView = LayoutInflater.from(this).inflate(R.layout.layout_imageview_text, null);
            ((ImageView)itemView.findViewById(R.id.image_view)).setImageResource(R.mipmap.h);
            ((TextView)itemView.findViewById(R.id.text)).setText("添加的页面");
            pages.add(itemView);
            mPagerAdapter.notifyDataSetChanged();
        }else if ("减少".equals(item.getTitle())){
            if (pages.size() > 0){
                pages.remove(0);
                mPagerAdapter.notifyDataSetChanged();
            }
        }else {
            ((TextView)pages.get(0).findViewById(R.id.text)).setText("更新的页面");
            mPagerAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDatas() {
        for (int i=0; i<imageIds.length; i++){
            int imageId = imageIds[i];
            View itemView = LayoutInflater.from(this).inflate(R.layout.layout_imageview_text, null);
            ((ImageView)itemView.findViewById(R.id.image_view)).setImageResource(imageId);
            ((TextView)itemView.findViewById(R.id.text)).setText("第"+i+"页");
            pages.add(itemView);
        }
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("PagerAdapter大小发生改变");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
