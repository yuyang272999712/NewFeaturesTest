package com.yuyang.fitsystemwindowstestdrawer.RxAndroid;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.RxAndroid.Learn1.RxJavaLearnActivity1;

import java.util.Arrays;
import java.util.List;

/**
 * RxJava学习
 */
public class RxJavaActivity extends ListActivity{
    private List<String> items = Arrays.asList("RxJava基本使用","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text, R.id.id_info, items));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(this, RxJavaLearnActivity1.class);
                break;
        }
        startActivity(intent);
    }
}
