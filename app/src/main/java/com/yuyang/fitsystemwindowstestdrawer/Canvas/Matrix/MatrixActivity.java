package com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 变形矩阵效果
 */
public class MatrixActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private MatrixView matrixView;

    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        findViews();

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.color_matrix_test1);
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ColorMatrix值修改");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        matrixView = (MatrixView) findViewById(R.id.matrixView);
    }

    //重置
    public void btnReset(View view) {
        matrixView.reset();
    }
    //缩放
    public void btnScale(View view){
        matrixView.scale();
    }
    //平移
    public void btnTrans(View view){
        matrixView.translate();
    }
    //旋转
    public void btnRotate(View view){
        matrixView.rotate();
    }
    //侧切
    public void btnSkew(View view){
        matrixView.skew();
    }
}
