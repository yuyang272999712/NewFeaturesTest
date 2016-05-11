package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义View测试，
 *  1、自定义View的属性
 *  2、在View的构造方法中获得我们自定义的属性
 *  [ 3、重写onMeasure ]
 *  4、重写onDraw
 */
public class CustomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }
}
