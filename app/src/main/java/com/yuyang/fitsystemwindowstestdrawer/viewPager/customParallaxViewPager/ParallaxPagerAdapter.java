package com.yuyang.fitsystemwindowstestdrawer.viewPager.customParallaxViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * 只含有空View的PagerAdapter
 */

public class ParallaxPagerAdapter extends PagerAdapter {
    private int count = 0;
    private LinkedList<View> viewCache = new LinkedList<>();
    private Context context;

    public ParallaxPagerAdapter(Context context){
        this.context = context;
    }

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if (!viewCache.isEmpty()){
            view = viewCache.pop();
        }else {
            view = new View(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        viewCache.push(view);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
