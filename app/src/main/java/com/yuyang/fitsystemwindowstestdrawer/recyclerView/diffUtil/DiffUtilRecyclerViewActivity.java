package com.yuyang.fitsystemwindowstestdrawer.recyclerView.diffUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用DiffUtil更新Recycler数据
 *  使用方法：
 *      步骤一
            在将newDatas 设置给Adapter之前，先调用DiffUtil.calculateDiff()方法，计算出新老数据集转化的最小更新集，
        就是DiffUtil.DiffResult对象。
                DiffUtil.calculateDiff()方法定义如下：
                    第一个参数是DiffUtil.Callback对象，
                    第二个参数代表是否检测Item的移动，改为false算法效率更高，按需设置。
        步骤二
            然后利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，
        替代普通青年才用的mAdapter.notifyDataSetChanged()方法。
            查看源码可知，该方法内部，就是根据情况调用了adapter的四大定向刷新方法。
 */
public class DiffUtilRecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiffUtilAdapter mAdapter;
    private List<DiffutilItem> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diff_util);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffUtilAdapter(this, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new DiffutilItem("张旭童1", R.mipmap.a, "Android"));
        mDatas.add(new DiffutilItem("张旭童2", R.mipmap.b, "Java"));
        mDatas.add(new DiffutilItem("张旭童3", R.mipmap.c, "背锅"));
        mDatas.add(new DiffutilItem("张旭童4", R.mipmap.d, "手撕产品"));
        mDatas.add(new DiffutilItem("张旭童5", R.mipmap.e, "手撕测试"));
    }

    /**
     * 模拟刷新操作
     */
    public void onRefresh() {
        try {
            List<DiffutilItem> newDatas = new ArrayList<>();
            for (DiffutilItem bean : mDatas) {
                newDatas.add(bean.clone());//clone一遍旧数据 ，模拟刷新操作
            }
            newDatas.add(new DiffutilItem("赵子龙", R.mipmap.f, "帅"));//模拟新增数据
            newDatas.get(0).setDes("Android+");
            newDatas.get(0).setImgId(R.mipmap.f);//模拟修改数据
            DiffutilItem testBean = newDatas.get(1);//模拟数据位移
            newDatas.remove(testBean);
            newDatas.add(testBean);

            //!--yuyang 利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，
            //参数2-是否检测 item的移动boolean变量
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, newDatas), true);
            //!--yuyang 利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
            diffResult.dispatchUpdatesTo(mAdapter);
            //别忘了将新数据给Adapter
            mDatas = newDatas;
            mAdapter.setDatas(mDatas);
            //!--yuyang 这里就不用在调用adapter的数据变化通知方法了
            //mAdapter.notifyDataSetChanged();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("刷新").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                onRefresh();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
