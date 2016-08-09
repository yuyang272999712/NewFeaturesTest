package com.yuyang.fitsystemwindowstestdrawer.Canvas.Xfermode;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Xfermode混合效果
 */
public class XfermodeActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private PorterDuffXfermodeView xfermodeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_fermode);

        findViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Xfermode混合效果");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        xfermodeView = (PorterDuffXfermodeView) findViewById(R.id.xfermode_view);
    }

    /**
     * 改变混合模式
     * @param view
     */
    public void btnChangePorterDuffMode(View view){
        PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.SRC_OVER;//默认正常模式
        switch (view.getId()){
            case R.id.clear:
                mPorterDuffMode = PorterDuff.Mode.CLEAR;
                break;
            case R.id.src:
                mPorterDuffMode = PorterDuff.Mode.SRC;
                break;
            case R.id.dst:
                mPorterDuffMode = PorterDuff.Mode.DST;
                break;
            case R.id.src_over:
                mPorterDuffMode = PorterDuff.Mode.SRC_OVER;
                break;
            case R.id.dst_over:
                mPorterDuffMode = PorterDuff.Mode.DST_OVER;
                break;
            case R.id.src_in:
                mPorterDuffMode = PorterDuff.Mode.SRC_IN;
                break;
            case R.id.dst_in:
                mPorterDuffMode = PorterDuff.Mode.DST_IN;
                break;
            case R.id.src_out:
                mPorterDuffMode = PorterDuff.Mode.SRC_OUT;
                break;
            case R.id.dst_out:
                mPorterDuffMode = PorterDuff.Mode.DST_OUT;
                break;
            case R.id.src_atop:
                mPorterDuffMode = PorterDuff.Mode.SRC_ATOP;
                break;
            case R.id.dst_atop:
                mPorterDuffMode = PorterDuff.Mode.DST_ATOP;
                break;
            case R.id.xor:
                mPorterDuffMode = PorterDuff.Mode.XOR;
                break;
            case R.id.darken:
                mPorterDuffMode = PorterDuff.Mode.DARKEN;
                break;
            case R.id.lighten:
                mPorterDuffMode = PorterDuff.Mode.LIGHTEN;
                break;
            case R.id.multiply:
                mPorterDuffMode = PorterDuff.Mode.MULTIPLY;
                break;
            case R.id.screen:
                mPorterDuffMode = PorterDuff.Mode.SCREEN;
                break;
            case R.id.add:
                mPorterDuffMode = PorterDuff.Mode.ADD;
                break;
            case R.id.overlay:
                mPorterDuffMode = PorterDuff.Mode.OVERLAY;
                break;
        }
        xfermodeView.setPorterDuffMode(mPorterDuffMode);
    }
}
