package com.yuyang.fitsystemwindowstestdrawer.homeDemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.homeDemo.complexHome.RecyclerViewComplexHomeActivity;
import com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.notificationAbout.NotificationActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 常用的主界面布局实现
 */
public class HomeDemoActivity extends ListActivity {
    private List<String> homeDemos = Arrays.asList("传统的ViewPager实现","FragmentManager+Fragment实现",
            "ViewPager+Fragment实现","SlidingPaneLayout实现侧滑导航","RecyclerView实现主要复杂布局");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_just_text, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.id_info);
                textView.setText(homeDemos.get(position));
                return convertView;
            }

            @Override
            public int getCount() {
                return homeDemos.size();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(this, TraditionalViewPagerActivity.class);
                break;
            case 1:
                intent = new Intent(this, FragmentManagerActivity.class);
                break;
            case 2:
                intent = new Intent(this, ViewPagerFragmentActivity.class);
                break;
            case 3:
                intent = new Intent(this, SlidingPaneLayoutActivity.class);
                break;
            case 4:
                intent = new Intent(this, RecyclerViewComplexHomeActivity.class);
        }
        startActivity(intent);
    }
}
