package com.yuyang.fitsystemwindowstestdrawer.Canvas.SaveLayerMethod;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * 查看SaveLayerView学习Canvas的saveLayer用法
 */
public class SaveLayerMethodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        SaveLayerView saveLayerView = new SaveLayerView(this);
        saveLayerView.setLayoutParams(lp);
        setContentView(saveLayerView);
    }
}
