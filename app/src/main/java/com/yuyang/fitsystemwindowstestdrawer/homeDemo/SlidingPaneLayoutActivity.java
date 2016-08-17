package com.yuyang.fitsystemwindowstestdrawer.homeDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * SlidingPaneLayout实现侧滑导航
 *
 * SlidingPaneLayout 一些主要方法
 - setParallaxDistance(int parallaxBy) 设置滑动视差
 - setCoveredFadeColor(int color) 导航菜单视图的滑动颜色渐变
 - setSliderFadeColor(int color) 主视图的滑动颜色渐变
 - setPanelSlideListener(SlidingPaneLayout.PanelSlideListener listener) 滑动监听
 - openPane() 打开导航菜单
 - closePane() 关闭导航菜单
 */
public class SlidingPaneLayoutActivity extends AppCompatActivity {
    private SlidingPaneLayout slidingPaneLayout;
    private LinearLayout menuView,mainView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_pane_layout);

        slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.SlidingPane);
        menuView = (LinearLayout)findViewById(R.id.ll_menu);
        mainView = (LinearLayout)findViewById(R.id.ll_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("slidingPaneLayout导航");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //设置滑动视差 可选
        slidingPaneLayout.setParallaxDistance(200);
        //菜单滑动的颜色渐变设置 可选
        slidingPaneLayout.setCoveredFadeColor(getResources().getColor(R.color.colorAccent));
        //主视图滑动的颜色渐变设置 可选
        slidingPaneLayout.setSliderFadeColor(0);
        //滑动监听 可选
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                // slideOffset这个参数 是跟随滑动0-1变化的 通过这个数值变化我们可以做出一些不一样的滑动效果
                menuView.setScaleY(slideOffset / 2 + 0.5F);
                menuView.setScaleX(slideOffset/ 2 +  0.5F);
                mainView.setScaleY(1 - slideOffset / 5);
            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });
    }
}
