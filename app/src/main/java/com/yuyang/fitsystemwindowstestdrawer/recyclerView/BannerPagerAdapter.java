package com.yuyang.fitsystemwindowstestdrawer.recyclerView;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

//轮播图适配器
public class BannerPagerAdapter extends PagerAdapter {
    private int[] imageViewIds;

    public BannerPagerAdapter(){
        imageViewIds = new int[] {R.mipmap.banner_background, R.mipmap.banner_background, R.mipmap.banner_background};
    }

    @Override
    public int getCount() {
        return imageViewIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(container.getContext());
        item.setImageResource(imageViewIds[position]);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);

        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}
