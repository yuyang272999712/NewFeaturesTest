package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SimpleSlidingMenu.SlidingMenu;

/**
 * QQ侧滑导航（早期版本未出现DrawerLayout布局）
 */
public class SlidingMenuActivity1 extends AppCompatActivity {
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu_1);

        slidingMenu = (SlidingMenu) findViewById(R.id.sliding_menu);
    }

    public void toggleMenu(View view){
        slidingMenu.toggle();
    }
}
