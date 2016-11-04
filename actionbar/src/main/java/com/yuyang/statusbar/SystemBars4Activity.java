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
 * Hide the Navigation Bar
 *
 * 1、NavigationBar是android4.0(API14)以后添加的
 *  !--yuyang View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
 *  可通过setSystemUiVisibility()方法来实现隐藏导航栏
 *
 *  ZHU yuyang 这种方式设置导航栏隐藏以后，以各种方式离开这个页面后，setSystemUiVisibility()都会被清除，
 *      所以回到这个页面以后如果你想继续保留全屏请在 onResume() 或 onWindowFocusChanged()方法中重新设置
 *
 *  2、Make Content Appear Behind the Navigation Bar
        On Android 4.1 and higher, you can set your application's content to appear behind the
    navigation bar, so that the content doesn't resize as the navigation bar hides and shows.
    To do this, use !--yuyang SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION. You may also need to
    use !--yuyang SYSTEM_UI_FLAG_LAYOUT_STABLE to help your app maintain a stable layout.
        When you use this approach, it becomes your responsibility to ensure that critical parts
    of your app's UI don't end up getting covered by system bars

 翻译：
    2、是你的布局内容可以绘制到NavigationBar下层
        在Android4.1或更高版本上，你可以是你的布局内容绘制到NavigationBar下层，这样就不需要因为NavigationBar
 的隐藏／显示而改变布局大小。你可以使用 View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION 来这样做。
 */

public class SystemBars4Activity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_status_bar);

        imageView = (ImageView) findViewById(R.id.image_view);

        int flags = View.SYSTEM_UI_FLAG_FULLSCREEN //隐藏StatusBar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏NavigationBar
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //内容绘制到StatusBar下
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION; //内容绘制到NavigationBar下
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

}
