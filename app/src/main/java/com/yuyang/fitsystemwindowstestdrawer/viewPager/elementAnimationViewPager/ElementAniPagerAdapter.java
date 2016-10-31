package com.yuyang.fitsystemwindowstestdrawer.viewPager.elementAnimationViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 界面元素动画的ViewPager
 */

public class ElementAniPagerAdapter extends PagerAdapter {
    private int[] layoutIds;
    private LayoutInflater inflater;

    public ElementAniPagerAdapter(Context context, int... layoutIds){
        inflater = LayoutInflater.from(context);
        this.layoutIds = layoutIds;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(layoutIds[position], null);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return layoutIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
