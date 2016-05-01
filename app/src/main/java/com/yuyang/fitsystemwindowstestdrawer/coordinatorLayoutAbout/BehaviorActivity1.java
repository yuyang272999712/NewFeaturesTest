package com.yuyang.fitsystemwindowstestdrawer.coordinatorLayoutAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/5/1.
 * 自定义behavior，详见布局文件，
 * 本例主要使用了“MyDefineScrollBehavior”“MyDefineDependBehavior”两个累
 */
public class BehaviorActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_1);
    }
}
