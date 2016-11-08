package com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 绘制路径命令
 */

public class DrawPath implements IDraw {
    public Path path;//需要绘制的路径
    public Paint paint;//绘制画笔

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public void undo() {

    }
}
