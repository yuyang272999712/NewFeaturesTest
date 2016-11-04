package com.yuyang.statusbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.R;

/**
 * System Bars课程
 *
 * Dimming the System Bars(变暗，不是隐藏)
 *
 * 1、View.SYSTEM_UI_FLAG_LOW_PROFILE
 * ZHU yuyang 作用是减少 StatusBar 中的图标并使其变暗，将 NavigationBar 中的按钮减弱成 3 个点
 *  下拉（或点击）状态栏后系统会清空这个属性（setSystemUiVisibility(0)），要想别回来还需要手动再设置一次这个属性
 */

public class SystemBars1Activity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_status_bar);

        imageView = (ImageView) findViewById(R.id.image_view);

        // This example uses decor view, but you can use any visible view.
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        //imageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }
}
