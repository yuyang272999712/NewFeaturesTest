package com.yuyang.statusbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yuyang.R;

/**
 * System Bars课程
 *
 * Hiding the Status Bar
 *
 * 1、android4.0以前的设备
 *  可通过设置theme来隐藏StatusBar
 *   <application
         ...
         android:theme="@android:style/Theme.Holo.NoActionBar.Fullscreen" >
         ...
     </application>
    也可以通过代码的形式来隐藏StatusBar
     if (Build.VERSION.SDK_INT < 16) {
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
     }

    通过这两种方式设置的全屏会一直保持，除非你手动去掉这个Flag

    ZHU yuyang 你可以使用FLAG_LAYOUT_IN_SCREEN 来让你的Activity占满整个屏幕空间
 */

public class SystemBars2Activity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_flag_status_bar);

        imageView = (ImageView) findViewById(R.id.image_view);
    }
}
