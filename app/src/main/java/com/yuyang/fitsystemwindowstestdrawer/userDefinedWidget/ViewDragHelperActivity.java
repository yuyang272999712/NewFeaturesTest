package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义ViewDragHelper的布局
 */
public class ViewDragHelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defind_view_test_2);

        findViews();
        initDatas();
        initAction();
    }

    private void findViews() {
    }

    private void initDatas() {
    }

    private void initAction() {
    }
}
