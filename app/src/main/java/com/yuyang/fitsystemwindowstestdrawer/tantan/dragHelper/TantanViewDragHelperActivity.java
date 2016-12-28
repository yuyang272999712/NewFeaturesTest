package com.yuyang.fitsystemwindowstestdrawer.tantan.dragHelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.tantan.CardMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 2016/12/28.
 */

public class TantanViewDragHelperActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private SwipeCardLayout swipeCardLayout;

    private ArrayList<CardMode> al;
    private List<List<String>> imgList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tantan_custom_layout);
        setToolbar();
        initDatas();

        swipeCardLayout = (SwipeCardLayout) findViewById(R.id.swipe_layout);
        swipeCardLayout.setAdapter(new CardAdapter(this, al));
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("使用自定义Layout实现");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
