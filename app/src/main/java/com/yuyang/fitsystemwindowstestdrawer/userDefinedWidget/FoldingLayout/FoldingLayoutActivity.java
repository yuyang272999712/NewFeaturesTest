package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

/**
 * 折叠布局实现
 */
public class FoldingLayoutActivity extends ListActivity {
    private List<String> items = Arrays.asList("原理分析", "简单实现", "FoldingLayout测试",
            "FoldingLayout添加手势测试", "应用在SlidingPaneLayout中", "应用在DrawerLayout中");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public String getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = LayoutInflater.from(FoldingLayoutActivity.this)
                            .inflate(R.layout.item_just_text, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.id_info);
                textView.setText(items.get(position));
                return convertView;
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(this, MatrixPolyToPolyActivity.class);
                break;
            case 1:
                intent = new Intent(this, SimpleUseActivity.class);
                break;
            case 2:
                intent = new Intent(this, FoldLayoutSimpleActivity.class);
                break;
            case 3:
                intent = new Intent(this, TouchFoldLayoutSimpleActivity.class);
                break;
            case 4:
                intent = new Intent(this, SlidingPanelLayoutSampleActivity.class);
                break;
            case 5:
                intent = new Intent(this, DrawerLayoutSampleActivity.class);
                break;
        }
        startActivity(intent);
    }
}
