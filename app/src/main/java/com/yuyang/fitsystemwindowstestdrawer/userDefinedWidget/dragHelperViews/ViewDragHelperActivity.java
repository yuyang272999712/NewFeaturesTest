package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.dragHelperViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.MyDrawerLayoutActivity;

/**
 * 自定义ViewDragHelper的布局
 */
public class ViewDragHelperActivity extends AppCompatActivity {
    private View mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drag_helper_test);

        findViews();
        initDatas();
        initAction();
    }

    private void findViews() {
        mBtnTest = findViewById(R.id.btn_test);
    }

    private void initDatas() {
    }

    private void initAction() {
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "点击事件测试", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 启动自定义的DrawerLayout，使用ViewDragHelper实现
     * @param view
     */
    public void gotoMyDrawerLayout(View view){
        Intent intent = new Intent(this, MyDrawerLayoutActivity.class);
        startActivity(intent);
    }
}
