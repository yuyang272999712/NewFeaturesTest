package com.yuyang.fitsystemwindowstestdrawer.Canvas.PathEffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * PathEffect实例
 */
public class PathEffectView extends View {
    Path mPath;
    Paint mPaint;
    PathEffect[] mEffect;

    public PathEffectView(Context context) {
        this(context, null);
    }

    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        for (int i=0; i<=30; i++){
            mPath.lineTo(i*35, (float) (Math.random()*100));
        }

        mEffect = new PathEffect[6];
        mEffect[0] = null;
        mEffect[1] = new CornerPathEffect(10);
        mEffect[2] = new DiscretePathEffect(3, 5);
        mEffect[3] = new DashPathEffect(new float[]{20, 10, 5, 10}, 0);
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        mEffect[4] = new PathDashPathEffect(path, 12, 0, PathDashPathEffect.Style.ROTATE);
        mEffect[5] = new ComposePathEffect(mEffect[1], mEffect[3]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<mEffect.length; i++){
            mPaint.setPathEffect(mEffect[i]);
            canvas.drawPath(mPath, mPaint);
            canvas.translate(0, 200);
        }
    }
}
