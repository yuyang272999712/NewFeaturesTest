package com.yuyang.fitsystemwindowstestdrawer.Canvas.Bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 三阶贝塞尔曲线
 *  最少三个点
 */
public class Bezier3View extends View {
    private static final String TAG = "Bezier3View";
    private static final int LINEWIDTH = 5;
    private static final int POINTWIDTH = 10;
    private static final int POINTCONUT = 6;

    /** 即将要穿越的点集合 */
    private List<Point> mPoints = new ArrayList<>();
    /** 中点集合 */
    private List<Point> mMidPoints = new ArrayList<>();
    /** 中点的中点集合 */
    private List<Point> mMidMidPoints = new ArrayList<>();
    /** 移动后的点集合(控制点) */
    private List<Point> mControlPoints = new ArrayList<>();

    private Paint mPaint;
    private Path mPath;

    public Bezier3View(Context context) {
        this(context, null);
    }

    public Bezier3View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier3View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);

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
        //画原始点
        drawPoints(canvas);
        if (mPoints.size() >= POINTCONUT){
            // 根据原始点初始化其它各点
            initMidPoints(this.mPoints);
            initMidMidPoints(this.mMidPoints);
            initControlPoints(this.mPoints, this.mMidPoints , this.mMidMidPoints);
            // 画穿越原始点的折线
            drawCrossPointsBrokenLine(canvas);
            // 画中间点
            drawMidPoints(canvas);
            // 画中间点的中间点
            drawMidMidPoints(canvas);
            // 画控制点
            drawControlPoints(canvas);
            // 画贝塞尔曲线
            drawBezier(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (action == MotionEvent.ACTION_DOWN){
            if (mPoints.size() < POINTCONUT){
                Point point = new Point(x, y);
                mPoints.add(point);
                invalidate();
                return true;
            }
        }

        return true;
    }

    /** 初始化中点集合 */
    private void initMidPoints(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            Point midPoint = null;
            if (i == points.size()-1){
                return;
            }else {
                midPoint = new Point((points.get(i).x + points.get(i + 1).x)/2, (points.get(i).y + points.get(i + 1).y)/2);
            }
            mMidPoints.add(midPoint);
        }
    }

    /** 初始化中点的中点集合 */
    private void initMidMidPoints(List<Point> midPoints){
        for (int i = 0; i < midPoints.size(); i++) {
            Point midMidPoint = null;
            if (i == midPoints.size()-1){
                return;
            }else {
                midMidPoint = new Point((midPoints.get(i).x + midPoints.get(i + 1).x)/2, (midPoints.get(i).y + midPoints.get(i + 1).y)/2);
            }
            mMidMidPoints.add(midMidPoint);
        }
    }

    /** 初始化控制点集合 */
    private void initControlPoints(List<Point> points, List<Point> midPoints, List<Point> midMidPoints){
        for (int i = 0; i < points.size(); i ++){
            if (i ==0 || i == points.size()-1){
                continue;
            }else{
                Point before = new Point();
                Point after = new Point();
                before.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i - 1).x;
                before.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i - 1).y;
                after.x = points.get(i).x - midMidPoints.get(i - 1).x + midPoints.get(i).x;
                after.y = points.get(i).y - midMidPoints.get(i - 1).y + midPoints.get(i).y;
                mControlPoints.add(before);
                mControlPoints.add(after);
            }
        }
    }

    /** 画原始点 */
    private void drawPoints(Canvas canvas) {
        mPaint.setStrokeWidth(POINTWIDTH);
        for (int i = 0; i < mPoints.size(); i++) {
            canvas.drawPoint(mPoints.get(i).x, mPoints.get(i).y, mPaint);
        }
    }

    /** 画穿越原始点的折线 */
    private void drawCrossPointsBrokenLine(Canvas canvas) {
        mPaint.setStrokeWidth(LINEWIDTH);
        mPaint.setColor(Color.RED);
        // 重置路径
        mPath.reset();
        // 画穿越原始点的折线
        mPath.moveTo(mPoints.get(0).x, mPoints.get(0).y);
        for (int i = 0; i < mPoints.size(); i++) {
            mPath.lineTo(mPoints.get(i).x, mPoints.get(i).y);
        }
        canvas.drawPath(mPath, mPaint);
    }

    /** 画中间点 */
    private void drawMidPoints(Canvas canvas) {
        mPaint.setStrokeWidth(POINTWIDTH);
        mPaint.setColor(Color.BLUE);
        for (int i = 0; i < mMidPoints.size(); i++) {
            canvas.drawPoint(mMidPoints.get(i).x, mMidPoints.get(i).y, mPaint);
        }
    }

    /** 画中间点的中间点 */
    private void drawMidMidPoints(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        for (int i = 0; i < mMidMidPoints.size(); i++) {
            canvas.drawPoint(mMidMidPoints.get(i).x, mMidMidPoints.get(i).y, mPaint);
        }

    }

    /** 画控制点 */
    private void drawControlPoints(Canvas canvas) {
        mPaint.setColor(Color.GRAY);
        // 画控制点
        for (int i = 0; i < mControlPoints.size(); i++) {
            canvas.drawPoint(mControlPoints.get(i).x, mControlPoints.get(i).y, mPaint);
        }
    }

    /** 画贝塞尔曲线 */
    private void drawBezier(Canvas canvas) {
        mPaint.setStrokeWidth(LINEWIDTH);
        mPaint.setColor(Color.BLACK);
        // 重置路径
        mPath.reset();
        for (int i = 0; i < mPoints.size(); i++){
            if (i == 0){// 第一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(i).x, mControlPoints.get(i).y,// 控制点
                        mPoints.get(i + 1).x,mPoints.get(i + 1).y);
            }else if(i < mPoints.size() - 2){// 三阶贝塞尔
                mPath.cubicTo(mControlPoints.get(2*i-1).x,mControlPoints.get(2*i-1).y,// 控制点
                        mControlPoints.get(2*i).x,mControlPoints.get(2*i).y,// 控制点
                        mPoints.get(i+1).x,mPoints.get(i+1).y);// 终点
            }else if(i == mPoints.size() - 2){// 最后一条为二阶贝塞尔
                mPath.moveTo(mPoints.get(i).x, mPoints.get(i).y);// 起点
                mPath.quadTo(mControlPoints.get(mControlPoints.size()-1).x,mControlPoints.get(mControlPoints.size()-1).y,
                        mPoints.get(i+1).x,mPoints.get(i+1).y);// 终点
            }
        }
        canvas.drawPath(mPath,mPaint);
    }

    public void reset(){
        mPoints.clear();
        mMidPoints.clear();
        mMidMidPoints.clear();
        mControlPoints.clear();

        mPath.reset();
        invalidate();
    }
}
