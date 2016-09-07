package com.yuyang.fitsystemwindowstestdrawer.androidL.behaviorAbout.myBehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 该behavior相当于系统提供的“ScrollingViewBehavior”
 * “ScrollingViewBehavior”使目标View永远在AppBarLayout的下面
 * “MyDefineLikeSysBehavior”使目标View永远在id为targetId的下面
 */
public class MyDefineLikeSysBehavior extends CoordinatorLayout.Behavior<View> {
    private int targetId;

    public MyDefineLikeSysBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.Follow);
        for (int i=0;i<array.length();i++){
            int arr = array.getIndex(i);
            if(arr == R.styleable.Follow_target){
                targetId = array.getResourceId(arr, -1);
            }
        }
        array.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return targetId == dependency.getId();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //使child永远在dependency下方
        child.setY(dependency.getY()+dependency.getHeight());
        return true;
    }
}
