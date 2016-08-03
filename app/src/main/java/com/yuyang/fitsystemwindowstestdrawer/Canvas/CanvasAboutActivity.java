package com.yuyang.fitsystemwindowstestdrawer.Canvas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix.ColorMatrixActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix.ColorMatrixHelperActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.SaveLayerMethod.SaveLayerMethodActivity;
import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

/**
 * Canvas高阶用法
 */
public class CanvasAboutActivity extends ListActivity {
    private List<String> items = Arrays.asList("saveLayer()方法","ColorMatrix常用方法","直接设置矩阵的数值");

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
            case 1:
                intent = new Intent(this, ColorMatrixHelperActivity.class);
                break;
            case 2:
                intent = new Intent(this, ColorMatrixActivity.class);
                break;
        }
        startActivity(intent);
    }
}
