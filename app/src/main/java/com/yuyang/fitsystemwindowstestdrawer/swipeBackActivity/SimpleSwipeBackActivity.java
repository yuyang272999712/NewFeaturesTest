package com.yuyang.fitsystemwindowstestdrawer.swipeBackActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.swipeBackActivity.baseActivity.SwipeBackActivity;
import com.yuyang.fitsystemwindowstestdrawer.swipeBackActivity.baseActivity.SwipeBackLayout;

/**
 * 左滑返回
 * TODO yuyang 其在AndroidManifest.xml中的theme必需设置为SwipeBackTheme。详见styles.xml
 */
public class SimpleSwipeBackActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_recycler);
        /**
         * TODO yuyang
         * 该方式设置滑动退出方向，不设置的话默认是向右滑动退出
         */
        setDragEdge(SwipeBackLayout.DragEdge.RIGHT);
    }
}
