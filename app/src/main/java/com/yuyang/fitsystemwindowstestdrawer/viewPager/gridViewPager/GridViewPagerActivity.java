package com.yuyang.fitsystemwindowstestdrawer.viewPager.gridViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * GridView样式的ViewPager
 */

public class GridViewPagerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager4Grid;
    private ViewPager mViewPager4Recycler;

    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};
    private List<View> mGridViews;
    private List<View> mRecyclerViews;
    private List<GridItem> mDatas;
    private LayoutInflater inflater;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每页显示的个数
     */
    private int pageSize = 12;
    /**
     * 当前显示的是第几页
     */
    private int currentPage = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_pager);
        setToolbar();

        mViewPager4Grid = (ViewPager) findViewById(R.id.view_pager_1);
        mViewPager4Recycler = (ViewPager) findViewById(R.id.view_pager_2);
        initDatas();//初始化数据（相当于数据加载）

        inflater = LayoutInflater.from(this);

        pageCount = (int) Math.ceil(titles.length*1.0d/pageSize);
        mGridViews = new ArrayList<>();
        mRecyclerViews = new ArrayList<>();
        for (int i=0; i<pageCount; i++){
            /**
             * GridView布局
             */
            GridView pageView = (GridView) inflater.inflate(R.layout.layout_grid_view, mViewPager4Grid, false);
            pageView.setAdapter(new GridAdapter(this, mDatas, pageSize, i));
            mGridViews.add(pageView);

            pageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(GridViewPagerActivity.this, mDatas.get((int) id).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            /**
             * RecyclerView布局
             */
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.layout_recycler_view, mViewPager4Recycler, false);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            recyclerView.setAdapter(new RecyclerAdapter(this, mDatas, pageSize, i));
            mRecyclerViews.add(recyclerView);
        }
        mViewPager4Grid.setAdapter(new ViewPagerAdapter(mGridViews));
        mViewPager4Grid.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager4Recycler.setAdapter(new ViewPagerAdapter(mRecyclerViews));
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i=0; i<titles.length; i++){
            int imgId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            mDatas.add(new GridItem(titles[i], imgId));
        }
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ViewPager实现Grid列表");
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
