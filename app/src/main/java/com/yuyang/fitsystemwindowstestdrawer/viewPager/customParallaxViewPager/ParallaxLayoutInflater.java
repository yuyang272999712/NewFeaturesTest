package com.yuyang.fitsystemwindowstestdrawer.viewPager.customParallaxViewPager;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * 自定义LayoutInflater，重新设置LayoutInflater.Factory
 */

public class ParallaxLayoutInflater extends LayoutInflater {
    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        setUpLayoutFactory();
    }

    private void setUpLayoutFactory() {
        if (!(getFactory() instanceof ParallaxFactory)) {
            setFactory(new ParallaxFactory(this, getFactory()));
        }
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallaxLayoutInflater(this, newContext);
    }
}
