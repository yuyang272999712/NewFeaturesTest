package com.yuyang.fitsystemwindowstestdrawer.listView.swipeListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.listView.listViewCommonViewHolder.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * 模仿QQ列表侧滑
 */

public class SwipeListViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private List<Item> items;
    private SwipeAdapter itemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setToolbar();
        listView = (ListView) findViewById(R.id.list_view);
        initDatas();
        itemAdapter = new SwipeAdapter(this, items);
        listView.setAdapter(itemAdapter);
    }

    private void initDatas() {
        items = new ArrayList<>();
        for (int i=0; i<60; i++){
            Item item1 = new Item();
            item1.setmTitle("item"+i);
            item1.setmDescription("item"+i+"_description");
            item1.setmImgId(R.drawable.ic_menu_send);
            items.add(item1);
        }
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("模仿QQ列表侧滑");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("全部关闭");
        menu.add("打开第10个item");
        menu.add("关闭第10个item");
        menu.add("获取当前打开的item");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        if ("全部关闭".equals(title)){
            itemAdapter.closeAllItems();
        }else if ("打开第10个item".equals(title)){
            itemAdapter.openItem(9);
        }else if ("关闭第10个item".equals(title)){
            itemAdapter.closeItem(9);
        }else if ("获取当前打开的item".equals(title)){
            List<Integer> openItes = itemAdapter.getOpenItems();
            if (openItes != null && openItes.size()>0) {
                Item item1 = itemAdapter.getItem(openItes.get(0));
                Toast.makeText(this, item1.getmTitle() + " 打开着", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "没有打开的item", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
