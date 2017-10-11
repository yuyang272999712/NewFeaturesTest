package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.ChangeBigHeadLayout;

/**
 * 顶部item放大显示
 */

public class DemoChangeBigHeadActivity extends AppCompatActivity {
    private ChangeBigHeadLayout mChangeBigHeadLayout;
    private ChangeBigHeadAdapter mAdapter;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_change_big_head);

        mChangeBigHeadLayout = (ChangeBigHeadLayout) findViewById(R.id.change_big_head_layout);

        TextView tMoreInfo = new TextView(this);
        tMoreInfo.setText("没用更多了..");
        mChangeBigHeadLayout.addBottomContent(tMoreInfo);
        mChangeBigHeadLayout.setAdapter(mAdapter = new ChangeBigHeadAdapter());

        mAdapter.setData();
    }
}
