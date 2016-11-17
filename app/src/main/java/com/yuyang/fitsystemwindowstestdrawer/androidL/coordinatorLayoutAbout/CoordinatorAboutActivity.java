package com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout;

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
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.appBarLayout.AppBarLayoutActivity;
import com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout.snackbarAndFABAndTabLayout.NewWidgetActivity;

import java.util.Arrays;
import java.util.List;

/**
 * CoordinatorLayout配合其他View的使用
 */

public class CoordinatorAboutActivity extends AppCompatActivity {
    private List<String> items = Arrays.asList("AppBarLayout的使用","SnackBar/FAB/TabLayout");

    private Toolbar toolbar;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_list);

        setToolbar();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text){
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(CoordinatorAboutActivity.this, AppBarLayoutActivity.class);
                        break;
                    case 1:
                        intent = new Intent(CoordinatorAboutActivity.this, NewWidgetActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Android 5 以后的新东西");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
