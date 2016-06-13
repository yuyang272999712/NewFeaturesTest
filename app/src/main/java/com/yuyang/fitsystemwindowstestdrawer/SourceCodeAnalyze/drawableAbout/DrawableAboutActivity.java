package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

/**
 * drawable相关解析
 */
public class DrawableAboutActivity extends ListActivity {
    private List<String> mTitles = Arrays.asList("圆角drawable", "圆形drawable",
            "自定义drawable状态");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mTitles));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, CircleImageDrawableActivity.class);
                break;
            case 1:
                intent = new Intent(this, RoundImageDrawableActivity.class);
                break;
            case 2:
//                intent = new Intent(this, CustomStateDrawableActivity.class);
                break;
        }

        startActivity(intent);
    }
}
