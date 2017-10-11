package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.verticalLinearLayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义VerticalLinearLayout的布局
 */
public class VerticalLinearLayoutActivity extends AppCompatActivity {
    private VerticalLinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vertical_view_pager);

        findViews();
        initDatas();
        initAction();
    }

    private void findViews() {
        layout = (VerticalLinearLayout) findViewById(R.id.defined_view_3_layout);
    }

    private void initDatas() {
    }

    private void initAction() {
        layout.setOnPageChangeListener(new VerticalLinearLayout.OnPageChangeListener() {
            @Override
            public void onPageChange(int currentPage) {
                Toast.makeText(VerticalLinearLayoutActivity.this, "切换至第"+currentPage+"页", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
