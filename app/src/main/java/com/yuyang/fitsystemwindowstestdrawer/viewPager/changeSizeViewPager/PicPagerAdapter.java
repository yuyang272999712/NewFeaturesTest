package com.yuyang.fitsystemwindowstestdrawer.viewPager.changeSizeViewPager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/11/15.
 */

public class PicPagerAdapter extends PagerAdapter {
    private List<View> imageViews = new ArrayList<>();

    public PicPagerAdapter(List<View> imageViews) {
        this.imageViews.addAll(imageViews);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public View getItemView(int position){
        return imageViews.get(position);
    }
}
