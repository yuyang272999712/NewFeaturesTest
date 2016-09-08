package com.yuyang.fitsystemwindowstestdrawer.eventBus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.eventBus.myEventBusSimple.MyEventBusActivity;

import java.util.Arrays;
import java.util.List;

/**
 * EventBus使用
 */
public class EventBusAboutActivity extends AppCompatActivity {
    private List<String> items = Arrays.asList("EventBus使用","自定义模拟EventBus");

    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_list);

        setToolbar();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text, R.id.id_info, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(EventBusAboutActivity.this, EventBusActivity.class);
                        break;
                    case 1:
                        intent = new Intent(EventBusAboutActivity.this, MyEventBusActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("EventBus使用");
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
