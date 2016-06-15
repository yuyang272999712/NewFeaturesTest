package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.dragHelperViews.MyDrawerLayout;

/**
 * 使用ViewDragHelper自定义DrawerLayout布局
 */
public class MyDrawerLayoutActivity extends Activity {
    private MyDrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drawer_layout);

        drawerLayout = (MyDrawerLayout) findViewById(R.id.drawer_layout);
    }

    /**
     * 打开／关闭菜单
     * @param view
     */
    public void openOrCloseMenu(View view){
        if (drawerLayout.getOpenState()){
            drawerLayout.closeDrawer();
        }else {
            drawerLayout.openDrawer();
        }
    }
}
