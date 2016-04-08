package com.yuyang.fitsystemwindowstestdrawer.cardViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/3/11.
 */
public class CardPagerAdapter extends FragmentStatePagerAdapter {
    private List<CardPager> pagers;
    private List<Fragment> fragments = new ArrayList<>();

    public CardPagerAdapter(FragmentManager fm, List<CardPager> pagers) {
        super(fm);
        for (CardPager cardPager:pagers){
            fragments.add(CardFragment.getInstance(cardPager));
        }
        this.pagers = pagers;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
