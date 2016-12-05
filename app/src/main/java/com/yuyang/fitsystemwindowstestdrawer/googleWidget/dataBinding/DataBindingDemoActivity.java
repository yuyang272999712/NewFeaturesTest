package com.yuyang.fitsystemwindowstestdrawer.googleWidget.dataBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.databinding.ActivityDataBindingDemoBinding;

/**
 * DataBinding实例
 */

public class DataBindingDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingDemoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_demo);
        UserBean userBean = new UserBean("于", "洋", false);
        binding.setUser(userBean);

        binding.firstName.setText("测试");
    }
}
