package com.yuyang.fitsystemwindowstestdrawer.androidL;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 卡片效果，需要引入com.android.support:cardview-v7:23.+包
 * CardView 就是一个卡片样式的FrameLayout。
 *      参数介绍：
 *          app:cardBackgroundColor  :   背景颜色
 *          app:cardCornerRadius    ：   设置圆角。
 *          app:cardElevation       ：    阴影。
 *          app:cardMaxElevation    ：       最大阴影。
 *          app:cardPreventCornerOverlap  ： 在v20和之前的版本中添加内边距，这个属性是为了防止卡片内容和边角的重叠。
 *          app:cardUseCompatPadding  ：  设置内边距，v21+的版本和之前的版本仍旧具有一样的计算方式
 */
public class CardViewActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        setToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CardViewAdapter());
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("CardView");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ContentHolder>{
        @Override
        public CardViewAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false));
        }

        @Override
        public void onBindViewHolder(CardViewAdapter.ContentHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ContentHolder extends RecyclerView.ViewHolder{
            public ContentHolder(View itemView) {
                super(itemView);

            }
        }
    }
}
