package com.yuyang.fitsystemwindowstestdrawer.service;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

/**
 * Service相关
 */
public class ServiceActivity extends ListActivity {
    List<String> items = Arrays.asList("IntentService");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this,R.layout.item_just_text, R.id.id_info, items));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position){
            case 0:
                Intent intent = new Intent(this, UploadImgActivity.class);
                startActivity(intent);
                break;
        }
    }
}
