package com.yuyang.fitsystemwindowstestdrawer.viewPager.cardViewPager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/3/11.
 * 这里不是新控件CardView
 * 使用的时ViewPager，关键是使用如下两个属性：
 *      android:clipToPadding="false"
 *      android:clipChildren="false"
 *  使ViewPager的padding区域也可以绘制adapter内容，详见效果图
 */
public class CardViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<CardPager> pagers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_pager);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        viewPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        CardPager cardPager1 = new CardPager(R.mipmap.img1,"标题1","内容描述1");
        CardPager cardPager2 = new CardPager(R.mipmap.img2,"标题2","内容描述2");
        CardPager cardPager3 = new CardPager(R.mipmap.img3,"标题3","内容描述3");
        CardPager cardPager4 = new CardPager(R.mipmap.img1,"标题4","内容描述4");
        CardPager cardPager5 = new CardPager(R.mipmap.img2,"标题5","内容描述5");
        CardPager cardPager6 = new CardPager(R.mipmap.img3,"标题6","内容描述6");
        pagers.add(cardPager1);
        pagers.add(cardPager2);
        pagers.add(cardPager3);
        pagers.add(cardPager4);
        pagers.add(cardPager5);
        pagers.add(cardPager6);

        CardPagerAdapter adapter = new CardPagerAdapter(getSupportFragmentManager(), pagers);

        viewPager.setAdapter(adapter);
    }
}
