package com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 类似联系人列表的 标题item 固定在列表最上方
 */
public class StickyHeaderRecyclerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    //固定的header
    private TextView mStickyHeader;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_header_recycler_view);
        findViews();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new StickyHeaderAdapter(this, DataUtil.getData()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //获取当前最上方显示的Item，根据这个item，来更新吸顶布局的内容，
                View stickyInfoView = recyclerView.findChildViewUnder(recyclerView.getMeasuredWidth()/2, 5);
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null){
                    mStickyHeader.setText(stickyInfoView.getContentDescription());
                }

                // 找到固定在屏幕上方的 mStickyHeader下面一个像素位置的RecyclerView的item，
                // 我们根据这个item来更新假的 mStickyHeader要translate多少距离.
                // 并且只处理HAS_STICKY_VIEW和NONE_STICKY_VIEW这两种tag，
                // 因为第一个item的StickyLayout虽然展示，但是一定不会引起FakeStickyLayout的滚动.
                View transInfoView = recyclerView.findChildViewUnder(mStickyHeader.getMeasuredWidth()/2,
                        mStickyHeader.getHeight()+1);
                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - mStickyHeader.getMeasuredHeight();
                    // 如果当前item(transInfoView)需要展示Header，
                    // 那么根据这个item(transInfoView)的getTop和 mStickyHeader 的高度相差的距离来滚动FakeStickyLayout.
                    // 这里有一处需要注意，如果这个item的getTop已经小于0，也就是滚动出了屏幕，
                    // 那么我们就要把假的mStickyHeader恢复原位，来覆盖住这个item对应的吸顶信息.
                    if (transViewStatus == StickyHeaderAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            mStickyHeader.setTranslationY(dealtY);
                        } else {
                            mStickyHeader.setTranslationY(0);
                        }
                    } else if (transViewStatus == StickyHeaderAdapter.NONE_STICKY_VIEW) {
                        // 如果当前item不需要展示Header，那么就不会引起 mStickyHeader 的滚动.
                        mStickyHeader.setTranslationY(0);
                    }
                }
            }
        });
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("顶部固定的列表");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mStickyHeader = (TextView) findViewById(R.id.tv_sticky_header_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.sticky_recycler_view);
    }
}
