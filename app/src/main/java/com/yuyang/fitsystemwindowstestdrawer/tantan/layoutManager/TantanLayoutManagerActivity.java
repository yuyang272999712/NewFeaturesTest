package com.yuyang.fitsystemwindowstestdrawer.tantan.layoutManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager.DiamondLayoutManager;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager.DiamondManagerAdapter;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager.FixedLayoutManager;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager.FixedManagerAdapter;
import com.yuyang.fitsystemwindowstestdrawer.tantan.CardMode;
import com.yuyang.fitsystemwindowstestdrawer.tantan.cardFlingView.TantanAdapterViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义RecyclerView的LayoutManager
 */

public class TantanLayoutManagerActivity extends AppCompatActivity {
    private static final String TAG = "TantanLayoutManagerActi";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeCardLayoutManager swipeCardManager;

    private ArrayList<CardMode> al;
    private List<List<String>> imgList = new ArrayList<>();
    private CardAdapter adapter;
    private ImageView left, right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_manager);
        setToolbar();
        setLeftAndRightBtn();

        initDatas();

        swipeCardManager = new SwipeCardLayoutManager();
        swipeCardManager.setFlingListener(new SwipeCardLayoutManager.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Integer position) {
                makeToast(TantanLayoutManagerActivity.this, "不喜欢");
            }

            @Override
            public void onRightCardExit(Integer position) {
                makeToast(TantanLayoutManagerActivity.this, "喜欢");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                loadData();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = swipeCardManager.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        swipeCardManager.setOnItemClickListener(new SwipeCardLayoutManager.OnItemClickListener() {
            @Override
            public void onItemClicked(Integer position) {
                makeToast(TantanLayoutManagerActivity.this, al.get(position).toString());
            }
        });
        adapter = new CardAdapter(this, al);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(swipeCardManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void setLeftAndRightBtn() {
        left = (ImageView) findViewById(R.id.left);
        right = (ImageView) findViewById(R.id.right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeCardManager.getTopCardListener().selectLeft();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeCardManager.getTopCardListener().selectRight();
            }
        });
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("使用LayoutManager实现");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    private void initDatas() {
        al = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            List<String> s = new ArrayList<>();
            s.add(imageUrls[i]);
            imgList.add(s);
        }
        al.add(new CardMode("胡欣语", 21, imgList.get(0)));
        al.add(new CardMode("Norway", 21, imgList.get(1)));
        al.add(new CardMode("王清玉", 18, imgList.get(2)));
        al.add(new CardMode("测试1", 21, imgList.get(3)));
        al.add(new CardMode("测试2", 21, imgList.get(4)));
        al.add(new CardMode("测试3", 21, imgList.get(5)));
        al.add(new CardMode("测试4", 21, imgList.get(6)));
        al.add(new CardMode("测试5", 21, imgList.get(7)));
        al.add(new CardMode("测试6", 21, imgList.get(8)));
        al.add(new CardMode("测试7", 21, imgList.get(9)));
        al.add(new CardMode("胡欣语", 21, imgList.get(10)));
        al.add(new CardMode("Norway", 21, imgList.get(11)));
        al.add(new CardMode("王清玉", 18, imgList.get(12)));
        al.add(new CardMode("测试1", 21, imgList.get(13)));
        al.add(new CardMode("测试2", 21, imgList.get(14)));
        al.add(new CardMode("测试3", 21, imgList.get(15)));
        al.add(new CardMode("测试4", 21, imgList.get(16)));
        al.add(new CardMode("测试5", 21, imgList.get(17)));
        al.add(new CardMode("测试6", 21, imgList.get(18)));
        al.add(new CardMode("测试7", 21, imgList.get(19)));
    }

    /**
     * 模拟数据加载
     */
    private void loadData() {
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, List<CardMode>>() {
            @Override
            protected List<CardMode> doInBackground(Void... params) {
                ArrayList<CardMode> list = new ArrayList<>(10);
                list.add(new CardMode("胡欣语", 21, imgList.get(0)));
                list.add(new CardMode("Norway", 21, imgList.get(1)));
                list.add(new CardMode("王清玉", 18, imgList.get(2)));
                list.add(new CardMode("测试1", 21, imgList.get(3)));
                list.add(new CardMode("测试2", 21, imgList.get(4)));
                list.add(new CardMode("测试3", 21, imgList.get(5)));
                list.add(new CardMode("测试4", 21, imgList.get(6)));
                list.add(new CardMode("测试5", 21, imgList.get(7)));
                list.add(new CardMode("测试6", 21, imgList.get(8)));
                list.add(new CardMode("测试7", 21, imgList.get(9)));
                return list;
            }

            @Override
            protected void onPostExecute(List<CardMode> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
            }
        });
    }

    public final String[] imageUrls = new String[]{
            "http://www.5djiaren.com/uploads/2015-04/17-115301_29.jpg",
            "http://img1.dzwww.com:8080/tupian_pl/20160106/32/4152697013403556460.jpg",
            "http://c.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40f191362cfad64034f79f019ce.jpg",
            "http://img.article.pchome.net/new/w600/00/35/15/66/pic_lib/wm/122532981493137o3iegiyx.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3382799710,1639843170&fm=21&gp=0.jpg",
            "http://i2.sinaimg.cn/travel/2014/0918/U7398P704DT20140918143217.jpg",
            "http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1305/15/c2/20949108_20949108_1368599174341.jpg",
            "http://pic29.nipic.com/20130501/12558275_114724775130_2.jpg",
            "http://photo.l99.com/bigger/20/1415193157174_j2fa5b.jpg",
            "http://www.5djiaren.com/uploads/2015-04/17-115301_29.jpg",
            "http://img1.dzwww.com:8080/tupian_pl/20160106/32/4152697013403556460.jpg",
            "http://c.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40f191362cfad64034f79f019ce.jpg",
            "http://img.article.pchome.net/new/w600/00/35/15/66/pic_lib/wm/122532981493137o3iegiyx.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3382799710,1639843170&fm=21&gp=0.jpg",
            "http://i2.sinaimg.cn/travel/2014/0918/U7398P704DT20140918143217.jpg",
            "http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg",
            "http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1305/15/c2/20949108_20949108_1368599174341.jpg",
            "http://pic29.nipic.com/20130501/12558275_114724775130_2.jpg",
            "http://photo.l99.com/bigger/20/1415193157174_j2fa5b.jpg"};

}
