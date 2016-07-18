package com.yuyang.fitsystemwindowstestdrawer.SensorAbout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 传感器相关
 * 所有的传感器获取都类似：
 *  1、获取SensorManager实例
 *      SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
 *  2、通过TYPE获取任意传感器
 *      Sensor sensor = sensorManger.getDefaultSensor(Sensor.TYPE_LIGHT);
 *  3、注册传感器输出信号监听
 *      SensorEventListener listener = new SensorEventListener(){
 *          //传感器精度发生变化时调用
 *          public void onAccuracyChanged(Sensor sensor, int accuracy){
 *          }
 *          //传感器检测到数值发生变化时调用
 *          public void onSensorChanged(SensorEvent event){
 *          }
 *      }
 *      SensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
 *      **第三个参数回调速率。共有四个值可选(更新速率递增)：
 *      SENSOR_DELAY_UI
 *      SENSOR_DELAY_NORMAL
 *      SENSOR_DELAY_GAME
 *      SENSOR_DELAY_FASTEST
 *  4、一定不要忘记注销监听
 *      sensorManager.unregisterListener(listener);
 */
public class SensorAboutActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button lightBtn;
    private Button accelerometerBtn;
    private Button orientationBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_about);

        findViews();

        lightBtn.setOnClickListener(this);
        accelerometerBtn.setOnClickListener(this);
        orientationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.sensor_light:
                intent = new Intent(this, LightSensorActivity.class);
                break;
            case R.id.sensor_accelerometer:
                intent = new Intent(this, AccelerometerSensorActivity.class);
                break;
            case R.id.sensor_orientation:
                intent = new Intent(this, OrientationSensorActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lightBtn = (Button) findViewById(R.id.sensor_light);
        accelerometerBtn = (Button) findViewById(R.id.sensor_accelerometer);
        orientationBtn = (Button) findViewById(R.id.sensor_orientation);

        toolbar.setTitle("传感器相关");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
