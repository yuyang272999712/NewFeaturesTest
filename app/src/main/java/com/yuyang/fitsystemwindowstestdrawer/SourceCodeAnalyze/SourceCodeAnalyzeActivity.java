package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.layoutInflate.LayoutInflateActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.ViewTouchEventActivity;

/**
 * 源码解析
 */
public class SourceCodeAnalyzeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_code);
    }

    public void gotoLayoutInflateActivity(View view){
        Intent intent = new Intent(this, LayoutInflateActivity.class);
        startActivity(intent);
    }

    public void gotoViewTouchEvent(View view){
        Intent intent = new Intent(this, ViewTouchEventActivity.class);
        startActivity(intent);
    }
}