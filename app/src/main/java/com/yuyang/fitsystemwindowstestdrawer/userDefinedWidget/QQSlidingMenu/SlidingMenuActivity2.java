package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.DoubleSlidingMenu.BinarySlidingMenu;

/**
 * QQ侧滑导航（早期版本未出现DrawerLayout布局）
 */
public class SlidingMenuActivity2 extends AppCompatActivity {
    private BinarySlidingMenu slidingMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu_2);

        slidingMenu = (BinarySlidingMenu) findViewById(R.id.sliding_menu);
    }
}
