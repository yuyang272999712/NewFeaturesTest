package com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView;

import android.graphics.Path;

/**
 * 圆形画笔
 */

public class CircleBrush implements IBrush {
    @Override
    public void down(Path path, float x, float y) {

    }

    @Override
    public void move(Path path, float x, float y) {
        path.addCircle(x, y, 10, Path.Direction.CW);
    }

    @Override
    public void up(Path path, float x, float y) {

    }
}
