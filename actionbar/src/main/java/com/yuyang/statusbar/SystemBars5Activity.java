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
 * Using Immersive Full-Screen Mode
 *
 * 沉浸式效果是!--yuyang android4.4（API19）以后引入效果
 *  可以通过 View.SYSTEM_UI_FLAG_IMMERSIVE (ZHU yuyang SystemBar重新显示后不会再隐藏)
 *  和 View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY (ZHU yuyang SystemBar重新显示后过几秒钟会再隐藏)
 *  来实现
 *
 *  ZHU yuyang 这种方式设置沉浸效果必须要配合 View.SYSTEM_UI_FLAG_FULLSCREEN／View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
 *      来隐藏状态／导航栏，而且同样以各种方式离开这个页面后，setSystemUiVisibility()都会被清除，
 *      所以回到这个页面以后如果你想继续保留全屏请在 onResume() 或 onWindowFocusChanged()方法重新设置
 *
 */

public class SystemBars5Activity extends AppCompatActivity {
    private ImageView imageView;
    private View mDecorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_status_bar);

        imageView = (ImageView) findViewById(R.id.image_view);
        mDecorView = getWindow().getDecorView();

        hideSystemUI();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSystemUI();
            }
        });
    }

    // 隐藏SystemBar
    private void hideSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //内容绘制到NavigationBar下
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //内容绘制到StatusBar下
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE); //设置沉浸效果
    }

    //显示SystemBar
    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


}
