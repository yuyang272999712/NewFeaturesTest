package com.yuyang.fitsystemwindowstestdrawer.Canvas.Shader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Shader着色器的使用
 */
public class ShaderActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ShaderViewSimple simple1;
    private ShaderViewSimple simple2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shader);
        findViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Shader在Paint中的使用");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        simple1 = (ShaderViewSimple) findViewById(R.id.shader_view_1);
        simple2 = (ShaderViewSimple) findViewById(R.id.shader_view_2);
        simple1.setType(true);
    }
}
