package com.yuyang.fitsystemwindowstestdrawer.coordinatorLayoutAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/5/1.
 * 自定义behavior，详见布局文件
 * 本例主要使用了“MyDefineLikeSysBehavior”  该behavior相当于系统提供的“ScrollingViewBehavior”
 *
 * @string/appbar_scrolling_view_behavior(android.support.design.widget.AppBarLayout$ScrollingViewBehavior)
 * ScrollingViewBehavior的源码不多，唯一的作用是把自己放到AppBarLayout的下面
 */
public class BehaviorActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_2);
    }
}
