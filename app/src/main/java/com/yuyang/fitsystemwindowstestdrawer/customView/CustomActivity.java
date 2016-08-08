package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义View测试，
 *  1、自定义View的属性
 *  2、在View的构造方法中获得我们自定义的属性
 *  [ 3、重写onMeasure ]
 *  4、重写onDraw
 */
public class CustomActivity extends AppCompatActivity {
    private Button nextPage1;
    private Button nextPage2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        nextPage1 = (Button) findViewById(R.id.custom_next_page_1);
        nextPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomActivity.this, CustomActivity2.class);
                startActivity(intent);
            }
        });

        nextPage2 = (Button) findViewById(R.id.custom_next_page_2);
        nextPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomActivity.this, CustomActivity3.class);
                startActivity(intent);
            }
        });
    }
}
