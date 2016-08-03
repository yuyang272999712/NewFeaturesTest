package com.yuyang.fitsystemwindowstestdrawer.Canvas.SaveLayerMethod;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * saveLayer(),saveLayerAlpha() 方法将一个图层入栈，使用restore(),restoreToCount()方法将一个图层出栈。
 * 入栈的时候，后面所有的操作都发生在这个图层上，而出栈的时候，则会把图像绘制到上层Canvas上。
 */
public class SaveLayerView extends View {
    Paint paint;

    public SaveLayerView(Context context) {
        this(context, null);
    }

    public SaveLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(150,150, 100, paint);

        paint.setColor(Color.YELLOW);
        canvas.saveLayer(0,0,400,400,paint);
        paint.setColor(Color.RED);
        paint.setAlpha(127);
        canvas.drawCircle(200,150,100,paint);
        canvas.restore();

        canvas.saveLayerAlpha(0,0,400,400,127,Canvas.ALL_SAVE_FLAG);
        paint.setColor(Color.RED);
        canvas.drawCircle(200,200,100,paint);
        canvas.restore();
    }
}
