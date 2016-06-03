package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTab;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.List;

/**
 * Created by yuyang on 16/6/3.
 */
public class MyTabLayout extends HorizontalScrollView {
    private MyTabLinearLayout linearLayout;

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        linearLayout = new MyTabLinearLayout(context, attrs);
        addView(linearLayout);
    }

    public void setViewPager(ViewPager viewPager, final int pos){
        linearLayout.setViewPager(viewPager, pos);
    }

    public void setTabItemTitles(List<String> datas) {
        linearLayout.setTabItemTitles(datas);
    }
}
