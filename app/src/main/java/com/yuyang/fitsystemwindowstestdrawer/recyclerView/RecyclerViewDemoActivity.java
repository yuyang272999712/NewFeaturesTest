package com.yuyang.fitsystemwindowstestdrawer.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.ViewInjectUtils;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ContentView;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ViewInject;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager.CustomLayoutManagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.diffUtil.DiffUtilRecyclerViewActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemAnimator.SlideRecyclerListActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.removeItem.RecyclerViewRemoveItemActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeader.StickyHeaderRecyclerActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.totalEffect.SwipeRefreshTouchHelperActivity;

import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView 应用
 */
@ContentView(R.layout.activity_just_list)
public class RecyclerViewDemoActivity extends AppCompatActivity {
    private List<String> items = Arrays.asList("侧滑删除Item","编辑动画效果","item拖动效果","粘性表头效果",
            "DiffUtil工具更新RecyclerView内容","自定义LayoutManager");

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.list_view)
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);

        setToolbar();

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text){
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_just_text, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.id_info);
                textView.setText(items.get(position));
                return convertView;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(RecyclerViewDemoActivity.this, RecyclerViewRemoveItemActivity.class);
                        break;
                    case 1:
                        intent = new Intent(RecyclerViewDemoActivity.this, SlideRecyclerListActivity.class);
                        break;
                    case 2:
                        intent = new Intent(RecyclerViewDemoActivity.this, SwipeRefreshTouchHelperActivity.class);
                        break;
                    case 3:
                        intent = new Intent(RecyclerViewDemoActivity.this, StickyHeaderRecyclerActivity.class);
                        break;
                    case 4:
                        intent = new Intent(RecyclerViewDemoActivity.this, DiffUtilRecyclerViewActivity.class);
                        break;
                    case 5:
                        intent = new Intent(RecyclerViewDemoActivity.this, CustomLayoutManagerActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("RecyclerView应用");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
