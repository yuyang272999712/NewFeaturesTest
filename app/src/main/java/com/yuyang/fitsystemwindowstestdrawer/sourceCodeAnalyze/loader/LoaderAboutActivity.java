package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Loader源码浅析
 */
public class LoaderAboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_about);
    }

    public void gotoLoaderContactActivity(View view){
        Intent intent = new Intent(this, LoaderContactActivity.class);
        startActivity(intent);
    }

    public void gotoMyLoaderActivity(View view){
        Intent intent = new Intent(this, AppListActivity.class);
        startActivity(intent);
    }
}
