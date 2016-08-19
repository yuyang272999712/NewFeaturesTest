package com.yuyang.fitsystemwindowstestdrawer.androidL.activitySwitchAnim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/5/3.
 */
public class AdapterDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_detail);

        findViews();
        initViewData();
        initViewAction();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initViewData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewAction() {

    }
}
