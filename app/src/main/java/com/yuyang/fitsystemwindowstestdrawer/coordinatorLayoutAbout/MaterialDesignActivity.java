package com.yuyang.fitsystemwindowstestdrawer.coordinatorLayoutAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 知识点：
 * 1、Snackbar 只有使用CoordinatorLayout作为基本布局，FAB按钮才会自动产生向上移动的动画。
 *      FAB浮动操作按钮有一个 默认的 behavior来检测Snackbar的添加并让按钮在Snackbar之上呈现上移与Snackbar等高的动画。
 *
 * 2、CoordinatorLayout （[kəʊ'ɔ:dɪneɪtə] ）协调员
 *      该布局会检测他的所有子View，传递Behavior事件
 *
 */
public class MaterialDesignActivity extends AppCompatActivity {
    FloatingActionButton actionButton;
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        findView();
        initView();
        initAction();
    }

    private void initView() {
        toolbar.setTitle("开发测试");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void findView() {
        actionButton = (FloatingActionButton) findViewById(R.id.material_action_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initAction() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(actionButton,"snackbar测试",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });
    }
}
