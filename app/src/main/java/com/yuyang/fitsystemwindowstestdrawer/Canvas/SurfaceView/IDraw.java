package com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView;

import android.graphics.Canvas;

/**
 * 绘制命令接口
 */

public interface IDraw {
    /**
     * 绘制命令
     * @param canvas
     */
    void draw(Canvas canvas);

    /**
     * 撤销命令
     */
    void undo();
}
