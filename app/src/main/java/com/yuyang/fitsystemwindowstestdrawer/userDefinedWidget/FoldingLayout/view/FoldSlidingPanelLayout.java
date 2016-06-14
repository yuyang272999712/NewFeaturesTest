package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout.view;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * SlidingPaneLayout侧滑添加折叠效果
 */
public class FoldSlidingPanelLayout extends SlidingPaneLayout {
    public FoldSlidingPanelLayout(Context context) {
        super(context);
    }

    public FoldSlidingPanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FoldSlidingPanelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        View child = getChildAt(0);
        if (child != null){
            removeView(child);
            final FoldingLayout foldingLayout = new FoldingLayout(getContext());
            foldingLayout.addView(child);
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            addView(foldingLayout, 0, layoutParams);

            setPanelSlideListener(new PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    foldingLayout.setFactor(slideOffset);
                }

                @Override
                public void onPanelOpened(View panel) {

                }

                @Override
                public void onPanelClosed(View panel) {

                }
            });
        }
    }
}
