package com.yuyang.fitsystemwindowstestdrawer.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.ViewInjectUtils;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ContentView;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ViewInject;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.diffUtil.DiffUtilRecyclerViewActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemAnimator.SlideRecyclerListActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.removeItem.RecyclerViewRemoveItemActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeader.StickyHeaderRecyclerActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.totalEffect.SwipeRefreshActivity;

/**
 * RecyclerView 应用
 */
@ContentView(R.layout.activity_recycler_view_demo)
public class RecyclerViewDemoActivity extends AppCompatActivity {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * RecyclerView的item侧滑操作
     * @param view
     */
    public void gotoRecyclerViewRemoveItem(View view){
        Intent intent = new Intent(this, RecyclerViewRemoveItemActivity.class);
        startActivity(intent);
    }

    /**
     * RecyclerView的item动画效果
     * @param view
     */
    public void gotoSlideAnimator(View view){
        Intent intent = new Intent(this, SlideRecyclerListActivity.class);
        startActivity(intent);
    }

    /**
     * 效果综合
     * @param view
     */
    public void gotoSwipeRefresh(View view){
        Intent intent = new Intent(this, SwipeRefreshActivity.class);
        startActivity(intent);
    }

    /**
     * 联系人列表固定Title样式
     * @param view
     */
    public void gotoStickyHeaderRecyclerView(View view){
        Intent intent = new Intent(this, StickyHeaderRecyclerActivity.class);
        startActivity(intent);
    }

    /**
     * 使用DiffUtil工具更新RecyclerView内容
     * @param view
     */
    public void gotoDiffUtilRecyclerView(View view){
        Intent intent = new Intent(this, DiffUtilRecyclerViewActivity.class);
        startActivity(intent);
    }
}
