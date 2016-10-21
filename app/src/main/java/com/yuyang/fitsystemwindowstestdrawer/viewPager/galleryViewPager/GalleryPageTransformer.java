package com.yuyang.fitsystemwindowstestdrawer.viewPager.galleryViewPager;

import android.graphics.Camera;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 画廊效果的PageTransformer
 */

public class GalleryPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE=0.85f;
    private static final float MAX_ROTATE=30;

    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = Math.max(MIN_SCALE, 1-Math.abs(position));
        float rotate = MAX_ROTATE*Math.abs(position);
        if (position<-1){

        }else if (position<0){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(rotate);
        }else if (position>=0&&position<1){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }else if (position>=1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }
    }
}
