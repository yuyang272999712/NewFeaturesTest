package com.yuyang.fitsystemwindowstestdrawer.Canvas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.Canvas.SaveLayerMethod.SaveLayerMethodActivity;
import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

/**
 * Canvas高阶用法
 */
public class CanvasAboutActivity extends ListActivity {
    private List<String> items = Arrays.asList("saveLayer()方法");

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
                intent = new Intent(this, SaveLayerMethodActivity.class);
                break;
        }
        startActivity(intent);
    }
}
