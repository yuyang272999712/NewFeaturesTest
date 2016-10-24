package com.yuyang.fitsystemwindowstestdrawer.listView.listViewCommonViewHolder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用通用的ViewHolder
 */
public class ListCommonViewHolderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private ItemAdapter itemAdapter;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("使用通用的ListView－ViewHolder");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listView = (ListView) findViewById(R.id.list_view);
        initDatas();
        itemAdapter = new ItemAdapter(this, items);
        listView.setAdapter(itemAdapter);
    }

    private void initDatas() {
        items = new ArrayList<>();
        for (int i=0; i<30; i++){
            Item item1 = new Item();
            item1.setmTitle("item"+i);
            item1.setmDescription("item"+i+"_description");
            item1.setmImgId(R.drawable.ic_menu_send);
            items.add(item1);
        }
    }
}
