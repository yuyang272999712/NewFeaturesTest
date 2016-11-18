package com.yuyang.fitsystemwindowstestdrawer.viewPager.changeSizeViewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自适应大小的ViewPager
 * TODO yuyang 这么做不好，滑动过程中总是在测量ImageView的高度，很消耗资源
 *             可以一次性测量好所有View的高度，然后用Map<position,viewHeight>保存起来
 */

public class ChangeSizeViewPager extends ViewPager {
    private int currentPosition;
    private int height = 0;

    private int widthMeasureSpec;
    private boolean isFirstLayout = false;

    public ChangeSizeViewPager(Context context) {
        this(context, null);
    }

    public ChangeSizeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentPosition = 0;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isFirstLayout = true;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int height = 0;//滑动过程中的高度（通过 目标高度、当前高度、滑动比率 计算而得）
                int targetHeight = 0;//目标高度
                float offset = 0;//滑动比率
                View currentView = ((PicPagerAdapter) getAdapter()).getItemView(currentPosition);
                currentView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int currentHeight = currentView.getMeasuredHeight();//当前高度
                if (positionOffset == 0){//滑动结束
                    View targetView = ((PicPagerAdapter) getAdapter()).getItemView(position);
                    targetView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    targetHeight = targetView.getMeasuredHeight();
                    height = targetHeight;
                }else {
                    if (currentPosition == position) {//向右
                        offset = Math.abs(positionOffset);
                        View targetView = ((PicPagerAdapter) getAdapter()).getItemView(currentPosition + 1);
                        targetView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                        targetHeight = targetView.getMeasuredHeight();
                    } else {
                        offset = Math.abs(1-positionOffset);
                        View targetView = ((PicPagerAdapter) getAdapter()).getItemView(currentPosition - 1);
                        targetView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                        targetHeight = targetView.getMeasuredHeight();
                    }
                    height = (int) (currentHeight+(targetHeight-currentHeight)*offset);
                }

                resetHeight(height);
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        if (isFirstLayout) {
            View child = ((PicPagerAdapter) getAdapter()).getItemView(currentPosition);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            height = h;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            isFirstLayout = false;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        } else {
            layoutParams.height = height;
        }
        setLayoutParams(layoutParams);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        Parcelable superData = super.onSaveInstanceState();
        bundle.putParcelable("super_data", superData);
        bundle.putInt("current_position", currentPosition);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable superData = bundle.getParcelable("super_data");
        currentPosition = bundle.getInt("current_position");
        super.onRestoreInstanceState(superData);
    }
}
