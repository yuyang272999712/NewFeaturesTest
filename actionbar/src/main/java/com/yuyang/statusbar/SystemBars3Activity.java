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
 * 1、android4.1(API16)及以后的设备
 *  !--yuyang View.SYSTEM_UI_FLAG_FULLSCREEN
 *  可通过setSystemUiVisibility()方法来实现隐藏状态栏，这个方法比WindowManager.flags方法的控制能力更好
 *
 *  ZHU yuyang 这种方式设置全屏以后，以各种方式离开这个页面后，setSystemUiVisibility()都会被清除，
 *      所以回到这个页面以后如果你想继续保留全屏请在 onResume() 或 onWindowFocusChanged()方法中设置SYSTEM_UI_FLAG_FULLSCREEN属性
 *
 * 2、Make Content Appear Behind the Status Bar
    On Android 4.1 and higher, you can set your application's content to appear behind the status bar,
 so that the content doesn't resize as the status bar hides and shows. To do this,
 !--yuyang use SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN. You may also need to
 !--yuyang use SYSTEM_UI_FLAG_LAYOUT_STABLE to help your app maintain a stable layout.
    When you use this approach, it becomes your responsibility to ensure that critical parts of
 your app's UI (for example, the built-in controls in a Maps application) don't end up getting
 covered by system bars. This could make your app unusable. In most cases you can handle this
 by adding the android:fitsSystemWindows attribute to your XML layout file, set to true.
 This adjusts the padding of the parent ViewGroup to leave space for the system windows.
 This is sufficient for most applications.
    In some cases, however, you may need to modify the default padding to get the desired layout
 for your app. To directly manipulate how your content lays out relative to the system bars
 (which occupy a space known as the window's "content insets"), override fitSystemWindows(Rect insets).
 The fitSystemWindows() method is called by the view hierarchy when the content insets for a
 window have changed, to allow the window to adjust its content accordingly. By overriding this
 method you can handle the insets (and hence your app's layout) however you want.

 翻译：
    2、使内容可以显示在StatusBar区域下层
    在android4.1及以后，你可以是你的应用内容显示在StatusBar下，这样在StatusBar隐藏／显示时你的布局内容就不需要
 改变大小。你可以通过View的 SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 值来实现这中显示，也许你还需要View的 SYSTEM_UI_FLAG_LAYOUT_STABLE
 值来帮助你固定布局不需要根据StatusBar的隐藏／显示而引起改变。
    当你使用这种方式时，你必须确保你的UI最上边的内容不会被StatusBar遮挡，否则用户将不能发现／使用这部分被遮挡的内容，
 大多数情况下你可以通过在layout的xml中设置 android:fitsSystemWindows 的属性为 true 来使View腾出足够的padding
 以使UI内容得意显示。
    在默写情况下，你或许想自己定义这个padding的大小，你可以重写View的fitSystemWindows(Rect insets)方法，
 这个方法会在window绘制（第一次或者重绘）时调用。
 */

public class SystemBars3Activity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_status_bar);

        imageView = (ImageView) findViewById(R.id.image_view);

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //内容要填充至状态栏
        int flags = View.SYSTEM_UI_FLAG_FULLSCREEN //隐藏StatusBar
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN; //内容占满全屏
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

}
