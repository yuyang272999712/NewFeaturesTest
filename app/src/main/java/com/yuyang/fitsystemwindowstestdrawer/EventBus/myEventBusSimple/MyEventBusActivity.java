package com.yuyang.fitsystemwindowstestdrawer.eventBus.myEventBusSimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 简单实现EventBus框架
 * 事件注册在fragment中
 */
public class MyEventBusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_bus);
    }
}
