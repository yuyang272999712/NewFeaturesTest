package com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * Created by yuyang on 2016/8/9.
 */
public class DrawBoardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawSurfaceView drawSurfaceView = new DrawSurfaceView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        drawSurfaceView.setLayoutParams(params);
        setContentView(drawSurfaceView);
    }
}
