package com.yuyang.fitsystemwindowstestdrawer.recyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemTouchHelper.OnStartDragListener;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemTouchHelper.SimpleItemTouchHelperCallBack;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.LoadMoreFooterView;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loopviewpager.AutoLoopViewPager;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.recyclerItemClickListener.OnRecyclerItemClickListener;

import java.util.Arrays;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * ItemTouchHelper实现RecyclerView上添加拖动排序与滑动删除的事情
 * RecyclerLoadMoreAdapter 自带加载更多的adapter
 */
public class SwipeRefreshActivity extends AppCompatActivity implements OnStartDragListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private AutoLoopViewPager bannerViewPager;
    private CircleIndicator indicator;//banner指示器

    /**
     * 拖动操作helper
     */
    private ItemTouchHelper itemTouchHelper;
    private GridLayoutManager layoutManager;
    private RecyclerLoadMoreAdapter adapter;
    private List<String> mDatas;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            List<String> data = Arrays.asList(getResources().getStringArray(R.array.recycler_items_add));
            adapter.addmItems(data);
            refreshLayout.setRefreshing(false);
            //TODO yuyang 通知adapter加载完成，true加载成功，false加载失败
            adapter.setLoadMoreFinish(true);
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);

        findViews();
        initDatas();
        setAction();
    }

    private void findViews() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.swipe_recycler_view);
        bannerViewPager = (AutoLoopViewPager) findViewById(R.id.swipe_banner);
        indicator = (CircleIndicator) findViewById(R.id.swipe_indicator);
    }

    private void initDatas() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.recycler_items));
        adapter = new RecyclerLoadMoreAdapter(this, mDatas);
        //TODO yuyang 设置拖动事件,供ItemTouchHelper拖动调用
        adapter.setDragListener(this);
        layoutManager = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SimpleItemTouchHelperCallBack callBack = new SimpleItemTouchHelperCallBack(adapter);
        itemTouchHelper = new ItemTouchHelper(callBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        bannerViewPager.setAdapter(new BannerPagerAdapter());
        indicator.setViewPager(bannerViewPager);
    }

    private void setAction() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMore();
            }
        });
        /**
         * TODO yuyang 为adapter添加加载更多事件
         */
        adapter.setOnLoadMoreListener(new LoadMoreFooterView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        /**
         * TODO yuyang 为recyclerView添加单击事件
         * 这里没用用到adapter中回调的方式，只是一种解决办法，但会与ItemTouchHelper有冲突
         */
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                long itemId = viewHolder.getAdapterPosition();
                Toast.makeText(SwipeRefreshActivity.this, "单击了第"+itemId+"个item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder) {
                long itemId = viewHolder.getAdapterPosition();
                Toast.makeText(SwipeRefreshActivity.this, "长按了第"+itemId+"个item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadMore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("TAG","模拟网络请求数据");
                    Thread.sleep(3000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
