package com.yuyang.fitsystemwindowstestdrawer.viewPager.elementAnimationViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * ViewPager页面元素动画效果
 *  !--yuyang 可以结合ParallaxContainer.java的设计思路，给View自定义属性，从而控制动画执行
 */

public class ElementAnimationActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ElementAniPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_view_pager);

        setToolbar();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new ElementAniPagerAdapter(this, R.layout.layout_view_page_element_ani_1, R.layout.layout_view_page_element_ani_2,
                R.layout.layout_view_page_element_ani_3, R.layout.layout_view_page_element_ani_4);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setTranslationX(-page.getWidth() * position);//这样设置每个Page在滑动时其相对于屏幕的位置是不变的（即永远在视线范围内）
                //设置Page中的元素的动画
                ViewGroup container = (ViewGroup) page;
                int count = container.getChildCount();
                for (int i=0; i<count; i++){
                    View view = container.getChildAt(i);
                    switch (i%4){
                        case 0:
                            view.setTranslationX(page.getWidth() * position);
                            view.setTranslationY(page.getHeight() * position);
                            break;
                        case 1:
                            view.setTranslationX(-page.getWidth() * position);
                            view.setTranslationY(page.getHeight() * position);
                            break;
                        case 2:
                            view.setTranslationX(page.getWidth() * position);
                            view.setTranslationY(-page.getHeight() * position);
                            break;
                        case 3:
                            view.setTranslationX(-page.getWidth() * position);
                            view.setTranslationY(-page.getHeight() * position);
                    }
                    view.setAlpha(1-Math.abs(position));
                }
            }
        });
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ViewPager界面元素进出动画");
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
