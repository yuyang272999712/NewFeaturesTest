package com.yuyang.fitsystemwindowstestdrawer.tantan.cardFlingView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.tantan.CardMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/3/15.
 */
public class TantanAdapterViewActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ArrayList<CardMode> al;
    private CardAdapter adapter;
    private OverlyingListView flingContainer;
    private List<List<String>> imgList = new ArrayList<>();
    private ImageView left, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tantan);
        setToolbar();

        left = (ImageView) findViewById(R.id.left);
        right = (ImageView) findViewById(R.id.right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });

        initDatas();

        flingContainer = (OverlyingListView) findViewById(R.id.frame);
        adapter = new CardAdapter(this, al);
        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new OverlyingListView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                adapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(TantanAdapterViewActivity.this, "不喜欢");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(TantanAdapterViewActivity.this, "喜欢");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                if (itemsInAdapter == 3) {
                    loadData();
                }
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                try {
                    View view = flingContainer.getSelectedView();
                    view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                    view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        flingContainer.setOnItemClickListener(new OverlyingListView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(TantanAdapterViewActivity.this, "点击图片");
            }
        });
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("使用AdapterView实现");
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
    }

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    public void right() {
        flingContainer.getTopCardListener().selectRight();
    }

    public void left() {
        flingContainer.getTopCardListener().selectLeft();
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
            "http://photo.l99.com/bigger/20/1415193157174_j2fa5b.jpg"};
}
