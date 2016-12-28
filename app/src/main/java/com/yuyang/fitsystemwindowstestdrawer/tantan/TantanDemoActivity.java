package com.yuyang.fitsystemwindowstestdrawer.tantan;

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
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.decoration.DecorationActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.diffUtil.DiffUtilRecyclerViewActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.itemAnimator.SlideRecyclerListActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.removeItem.RecyclerViewRemoveItemActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeader.StickyHeaderRecyclerActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeaderUseDecoration.simple.StickyHeaderDecorationActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.totalEffect.SwipeRefreshTouchHelperActivity;
import com.yuyang.fitsystemwindowstestdrawer.tantan.cardFlingView.TantanAdapterViewActivity;
import com.yuyang.fitsystemwindowstestdrawer.tantan.dragHelper.TantanViewDragHelperActivity;
import com.yuyang.fitsystemwindowstestdrawer.tantan.layoutManager.TantanLayoutManagerActivity;

import java.util.Arrays;
import java.util.List;

@ContentView(R.layout.activity_just_list)
public class TantanDemoActivity extends AppCompatActivity {
    private List<String> items = Arrays.asList("重写AdapterView","重写RecyclerView的LayoutManager",
            "重写Layout");

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
                        intent = new Intent(TantanDemoActivity.this, TantanAdapterViewActivity.class);
                        break;
                    case 1:
                        intent = new Intent(TantanDemoActivity.this, TantanLayoutManagerActivity.class);
                        break;
                    case 2:
                        intent = new Intent(TantanDemoActivity.this, TantanViewDragHelperActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("仿探探");
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
