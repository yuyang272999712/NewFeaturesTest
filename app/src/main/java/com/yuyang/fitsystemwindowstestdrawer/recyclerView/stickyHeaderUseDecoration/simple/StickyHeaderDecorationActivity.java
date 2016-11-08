package com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeaderUseDecoration.simple;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeaderUseDecoration.StickyRecyclerHeadersDecoration;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeaderUseDecoration.StickyRecyclerHeadersTouchListener;

import java.util.Arrays;
import java.util.List;

public class StickyHeaderDecorationActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private Button mButton;
    private ToggleButton mToggleButton;
    private AnimalsHeadersAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_header_decoration);

        setToolbar();
        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mButton = (Button) findViewById(R.id.button_update);
        mToggleButton = (ToggleButton) findViewById(R.id.button_is_reverse);

        //设置Adapter，该Adapter需要实现StickyRecyclerHeadersAdapter类来确定头部View
        mAdapter = new AnimalsHeadersAdapter();
        mAdapter.add("Animals below!");
        mAdapter.addAll(mDatas);
        mRecyclerView.setAdapter(mAdapter);

        //为RecyclerView设置LayoutManager
        int orientation = getLayoutManagerOrientation(getResources().getConfiguration().orientation);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, mToggleButton.isChecked());
        mRecyclerView.setLayoutManager(layoutManager);

        //添加粘性表头的ItemDecoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        mRecyclerView.addItemDecoration(headersDecor);

        //添加Header的TouchListener
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(mRecyclerView, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(StickyHeaderDecorationActivity.this, "点击了头部: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        mRecyclerView.addOnItemTouchListener(touchListener);

        //为adapter添加数据变化监听，以便更新header
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        //添加Item点击事件
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapter.remove(mAdapter.getItem(position));
            }
        }));
        //添加自定义的分割线
        mRecyclerView.addItemDecoration(new DividerDecoration(this));

        //倒序显示
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mToggleButton.isChecked();
                mToggleButton.setChecked(isChecked);
                layoutManager.setReverseLayout(isChecked);
                mAdapter.notifyDataSetChanged();
            }
        });

        //更新Adapter
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler(Looper.getMainLooper());
                for (int i = 0; i < mAdapter.getItemCount(); i++) {
                    final int index = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyItemChanged(index);
                        }
                    }, 50);
                }
            }
        });
    }

    private void initDatas() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.animals));
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("使用ItemDecoration实现粘性表头");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 根据屏幕方向确定LayoutManager方向
     * @param activityOrientation
     * @return
     */
    private int getLayoutManagerOrientation(int activityOrientation) {
        if (activityOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            return LinearLayoutManager.VERTICAL;
        } else {
            return LinearLayoutManager.HORIZONTAL;
        }
    }

}
