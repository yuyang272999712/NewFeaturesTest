package com.yuyang.fitsystemwindowstestdrawer.Canvas.Bezier;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 二阶贝塞尔曲线
 */
public class Bezier2View extends View {
    private Paint mPaint;
    private Path mPath;
    private Point startPoint;
    private Point endPoint;
    private Point assistPoint;

    public Bezier2View(Context context) {
        this(context, null);
    }

    public Bezier2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 5));
        mPaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            int size = Math.min(widthSize,heightSize);
            setMeasuredDimension(size, size);
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, heightSize);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, widthSize);
        }else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (startPoint != null){
            canvas.drawCircle(startPoint.x, startPoint.y, DensityUtils.dp2px(getContext(), 7), mPaint);
        }
        if (endPoint != null){
            canvas.drawCircle(endPoint.x, endPoint.y, DensityUtils.dp2px(getContext(), 7), mPaint);
        }
        if (assistPoint != null){
            mPath.reset();
            //辅助点
            canvas.drawCircle(assistPoint.x, assistPoint.y, DensityUtils.dp2px(getContext(), 7), mPaint);
            //获取贝塞尔曲线
            mPath.moveTo(startPoint.x, startPoint.y);
            mPath.quadTo(assistPoint.x, assistPoint.y, endPoint.x, endPoint.y);
            //绘制贝塞尔曲线
            canvas.drawPath(mPath, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (action == MotionEvent.ACTION_DOWN){
            if (startPoint == null){
                startPoint = new Point(x, y);
                invalidate();
                return true;
            }
            if (endPoint == null){
                endPoint = new Point(x, y);
                invalidate();
                return true;
            }
            if (assistPoint == null){
                assistPoint = new Point(x, y);
                invalidate();
            }
        }

        if (action == MotionEvent.ACTION_MOVE && startPoint!=null && endPoint!=null){
            assistPoint = new Point(x, y);
            invalidate();
        }

        return true;
    }

    public void reset(){
        startPoint = null;
        endPoint = null;
        assistPoint = null;
        mPath.reset();
        invalidate();
    }
}
