package com.yuyang.fitsystemwindowstestdrawer.Canvas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.Canvas.Bezier.BezierActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.ColorMatrix.ColorMatrixValueSetActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.ColorMatrix.ColorMatrixActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.ColorMatrix.PixelsEffectActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix.MatrixActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix.MatrixValueSetActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix.PixelMatrixEffectActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.PathEffect.PathEffectActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.SaveLayerMethod.SaveLayerMethodActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.Xfermode.XfermodeActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.Shader.ShaderActivity;
import com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView.DrawBoardActivity;
import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;
import java.util.List;

/**
 * Canvas高阶用法
 */
public class CanvasAboutActivity extends ListActivity {
    private List<String> items = Arrays.asList("saveLayer()方法","ColorMatrix常用方法","ColorMatrix直接设置矩阵的数值",
            "直接修改像素位置的色值","Matrix常用方法","Matrix直接设置矩阵的数值","像素块分析","Xfermode混合效果","Shader着色器",
            "PathEffect路径效果","SurfaceView实现画板","贝塞尔曲线");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text, R.id.id_info, items));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(this, SaveLayerMethodActivity.class);
                break;
            case 1:
                intent = new Intent(this, ColorMatrixActivity.class);
                break;
            case 2:
                intent = new Intent(this, ColorMatrixValueSetActivity.class);
                break;
            case 3:
                intent = new Intent(this, PixelsEffectActivity.class);
                break;
            case 4:
                intent = new Intent(this, MatrixActivity.class);
                break;
            case 5:
                intent = new Intent(this, MatrixValueSetActivity.class);
                break;
            case 6:
                intent = new Intent(this, PixelMatrixEffectActivity.class);
                break;
            case 7:
                intent = new Intent(this, XfermodeActivity.class);
                break;
            case 8:
                intent = new Intent(this, ShaderActivity.class);
                break;
            case 9:
                intent = new Intent(this, PathEffectActivity.class);
                break;
            case 10:
                intent = new Intent(this, DrawBoardActivity.class);
                break;
            case 11:
                intent = new Intent(this, BezierActivity.class);
                break;
        }
        startActivity(intent);
    }
}
