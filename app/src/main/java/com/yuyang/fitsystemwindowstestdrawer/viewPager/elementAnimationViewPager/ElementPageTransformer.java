package com.yuyang.fitsystemwindowstestdrawer.viewPager.elementAnimationViewPager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 两个Pager都不动的transformer
 */

public class ElementPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(-page.getWidth() * position);
    }
}
