package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterRippleEffect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterRippleEffect.RippleView;

/**
 * 模拟探探的水波纹效果
 */
public class WaterRippleActivity extends Activity {
    private RippleView fillView;
    private RippleView notFillView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_ripple);

        fillView = (RippleView) findViewById(R.id.water_wave_fill);
        notFillView = (RippleView) findViewById(R.id.water_wave_not_fill);

        fillView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillView.startOneWave();
            }
        });
        notFillView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notFillView.startOneWave();
            }
        });
    }
}
