package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTabLayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 自定义TabLayout，类似于系统的TabLayout，可以修改底部的指示器形状
 */
public class MyTabLayout extends HorizontalScrollView implements MyTabLinearLayout.ScrollToCallBack {
    private MyTabLinearLayout linearLayout;

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        linearLayout = new MyTabLinearLayout(context, attrs);
        addView(linearLayout);
        linearLayout.setCallBack(this);
    }

    public void setViewPager(ViewPager viewPager, final int pos){
        linearLayout.setViewPager(viewPager, pos);
    }

}
