package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout;

import android.app.Activity;
import android.os.Bundle;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * DrawerLayout 与 FoldingLayout结合
 */
public class DrawerLayoutSampleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_fold_layout);
    }
}
