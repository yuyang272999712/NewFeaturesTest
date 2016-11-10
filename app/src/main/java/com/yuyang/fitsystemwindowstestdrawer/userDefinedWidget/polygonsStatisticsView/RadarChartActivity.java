package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.polygonsStatisticsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 雷达图
 */

public class RadarChartActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private Toolbar mToolbar;
    private RadarChartView radarChartView;
    private SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5,seekBar6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_cahrt);

        setToolbar();

        radarChartView = (RadarChartView) findViewById(R.id.radar_chart);
        radarChartView.setDataValues(0.3f, 0.5f, 0.8f, 0.1f, 0.2f, 0.9f);

        seekBar1 = (SeekBar) findViewById(R.id.value_1);
        seekBar2 = (SeekBar) findViewById(R.id.value_2);
        seekBar3 = (SeekBar) findViewById(R.id.value_3);
        seekBar4 = (SeekBar) findViewById(R.id.value_4);
        seekBar5 = (SeekBar) findViewById(R.id.value_5);
        seekBar6 = (SeekBar) findViewById(R.id.value_6);
        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);
        seekBar4.setOnSeekBarChangeListener(this);
        seekBar5.setOnSeekBarChangeListener(this);
        seekBar6.setOnSeekBarChangeListener(this);
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("雷达图");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float percent = progress*1f/seekBar.getMax();
        switch (seekBar.getId()){
            case R.id.value_1:
                radarChartView.setDataValue(percent, 0);
                break;
            case R.id.value_2:
                radarChartView.setDataValue(percent, 1);
                break;
            case R.id.value_3:
                radarChartView.setDataValue(percent, 2);
                break;
            case R.id.value_4:
                radarChartView.setDataValue(percent, 3);
                break;
            case R.id.value_5:
                radarChartView.setDataValue(percent, 4);
                break;
            case R.id.value_6:
                radarChartView.setDataValue(percent, 5);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
