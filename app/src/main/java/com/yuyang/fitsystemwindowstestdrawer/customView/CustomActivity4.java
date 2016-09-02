package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * CustomStateView
 *  自定义控件
 */
public class CustomActivity4 extends AppCompatActivity {
    private CustomTemperatureDial temperatureDial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_4);

        temperatureDial = (CustomTemperatureDial) findViewById(R.id.temperatureDial);
    }

    @Override
    protected void onResume() {
        super.onResume();
        temperatureDial.setCurrentTemp(20.5f);
    }
}
