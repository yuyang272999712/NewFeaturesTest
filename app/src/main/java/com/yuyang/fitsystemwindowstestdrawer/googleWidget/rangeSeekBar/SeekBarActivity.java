package com.yuyang.fitsystemwindowstestdrawer.googleWidget.rangeSeekBar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * SeekBar应用
 *
     android:max   设置值的大小 .
     android:progress    设置seekBar当前的默认值，范围0到max之间.
     android:thumb="@drawable/"   显示的那个可拖动图标，如果没有设置该参数则为系统默认,如果自己需要重新定义，则将自己需要的图标存放在资源目录 /res/drawable下，然后调用即可.
     android:thumbOffset   拖动图标的偏量值,可以让拖动图标超过bar的长度.
     android:progressDrawable 当我们不想使用系统默认的SeekBar时可以自己定义一个,这个资源文件就是用来调用我们自己定义的Seekbar图标的一般是在drawable下建立一个.xml文件s用layer-list来组织这些图标.
     android:secondaryProgress 用过的迅雷的都知道拖动图标随着当前的播放时间的走动而走动，同时我们也注意到了也有个缓冲看到的进度条，这个属性就是用来设置默认显示的值为多少,范围为0到max.
 */
public class SeekBarActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView rangeSliderMin;
    private TextView rangeSliderMax;
    private MaterialRangeSlider rangeSlider;
    private TextView rangeSeekBarMin;
    private TextView rangeSeekBarMax;
    private RangeSeekBar rangeSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        findViews();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.e("SeekBarActivity", "onProgressChanged:"+"fromUser:"+fromUser+";progress:"+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ToastUtils.showShort(SeekBarActivity.this, "当前数值："+seekBar.getProgress());
            }
        });

        rangeSlider.reset();
        rangeSlider.setStartingMinMax(20,100);
        rangeSlider.setRangeSliderListener(new MaterialRangeSlider.RangeSliderListener() {
            @Override
            public void onMaxChanged(int newValue) {
                rangeSliderMax.setText("最大值:"+newValue);
            }

            @Override
            public void onMinChanged(int newValue) {
                rangeSliderMin.setText("最小值:"+newValue);
            }
        });

        rangeSeekBar.setValue(108, 160);
        rangeSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max) {
                rangeSeekBarMin.setText("最小值:"+min);
                rangeSeekBarMax.setText("最大值:"+max);
            }
        });
    }

    private void findViews() {
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        rangeSliderMin = (TextView) findViewById(R.id.range_slider_min_value);
        rangeSliderMax = (TextView) findViewById(R.id.range_slider_max_value);
        rangeSlider = (MaterialRangeSlider) findViewById(R.id.material_range_slider);
        rangeSeekBarMin = (TextView) findViewById(R.id.range_seek_bar_min_value);
        rangeSeekBarMax = (TextView) findViewById(R.id.range_seek_bar_max_value);
        rangeSeekBar = (RangeSeekBar) findViewById(R.id.range_seek_bar);
    }
}
