package com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义菱形，以确保点击事件接收正确
 */

public class DiamondView extends View {
    private Paint mPaint;
    private Path mDrawPath;
    private Region mRegion;//Region用来表示Canvas图层上的某个封闭的区域。

    public DiamondView(Context context) {
        this(context, null);
    }

    public DiamondView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiamondView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mRegion = new Region();
        mDrawPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(size, size);

        mDrawPath.moveTo(0, size/2);//菱形最左点
        mDrawPath.lineTo(size/2, 0);//菱形最上点
        mDrawPath.lineTo(size, size/2);
        mDrawPath.lineTo(size/2, size);
        mDrawPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawPath(mDrawPath, mPaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            RectF rectF = new RectF();
            mDrawPath.computeBounds(rectF, true);
            mRegion.setPath(mDrawPath, new Region((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom));
            if (!mRegion.contains((int)event.getX(), (int)event.getY())){
                return false;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void setCardColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }
}
