package com.yuyang.fitsystemwindowstestdrawer.homeDemo.complexHome;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 2017/3/7.
 */
public class BannerPagerAdapter extends PagerAdapter {
    private int[] imageViewIds = new int[]{R.mipmap.banner_background, R.mipmap.color_matrix_test1, R.mipmap.color_matrix_test3};

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(container.getContext());
        item.setImageResource(imageViewIds[position]);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return imageViewIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
