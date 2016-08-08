package com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 像素块分析
 * canvas.drawBitmapMesh(Bitmap bitmap, int meshWidth, int meshHeight, float[] verts, int versOffset,
 *      int[] colors, int colorOffset, Paint paint);
 * 参数含义：
 *      bitmap：将要扭曲的图像。
 *      meshWidth：需要的横向网格数目
 *      meshHeight：需要的竖向网格数目
 *      verts：网格交叉点坐标数组
 *      vertOffset：verts数组中开始跳过的(x,y)坐标对的数目
 *
 */
public class PixelMatrixEffectActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private MatrixView matrixView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_value_set);
        findViews();

    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("像素块分析");
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

    public void btnChange(View view) {
        matrixView.drawMesh();
    }

    public void btnReset(View view) {
        matrixView.reset();
    }
}
