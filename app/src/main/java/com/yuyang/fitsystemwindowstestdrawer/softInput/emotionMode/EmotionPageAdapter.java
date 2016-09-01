package com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 表情布局
 */
public class EmotionPageAdapter extends FragmentStatePagerAdapter {
    String[] titles = new String[]{"兔斯基", "嘻哈猴"};
    List<Fragment> mFragments = new ArrayList<>();

    public EmotionPageAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new TuzkiFragment());
        mFragments.add(new XiHaHouFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
