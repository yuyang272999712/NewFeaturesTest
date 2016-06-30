package com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.ViewInjectUtils;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ContentView;
import com.yuyang.fitsystemwindowstestdrawer.myIOCforAndroid.ioc.annotation.ViewInject;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * 利用依赖注入机制查找控件（就像xUtils一样注入）
 */
@ContentView(value = R.layout.activity_my_ioc)
public class MyIOCActivity extends AppCompatActivity {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.my_ioc_btn1)
    private Button btn1;
    @ViewInject(R.id.my_ioc_btn2)
    private Button btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO yuyang 依赖注入这句是必须的
        ViewInjectUtils.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong(MyIOCActivity.this, "依赖注入");
            }
        });
    }
}
