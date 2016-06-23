package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomStateView
 *  自定义控件状态，使用selector即可根据自定义状态改变控件颜色
 */
public class CustomActivity2 extends AppCompatActivity {
    private CustomStateView stateView;
    private CustomCoordinateAxisView axisView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_2);

        stateView = (CustomStateView) findViewById(R.id.custom_2_state);
        stateView.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View v) {
                i++;
                if(i%3 == 1){
                    stateView.setState1(true);
                }else if(i%3 == 2){
                    stateView.setState2(true);
                }else if(i%3 == 0){
                    stateView.setState3(true);
                }
            }
        });

        axisView = (CustomCoordinateAxisView) findViewById(R.id.custom_2_axis);
        axisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = new ArrayList<Product>();
                products.add(new Product("测试1", 0.75f));
                products.add(new Product("测试2", 0.24f));
                products.add(new Product("测试3", 0.3f));
                products.add(new Product("测试4", 0.8f));
                axisView.setPercentAndText(products);
            }
        });
    }
}
