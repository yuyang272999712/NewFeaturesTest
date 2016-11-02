package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterWaveEffect;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 通过图形矩阵变换来实现波浪效果（主要用到了Canvas的drawBitmapMesh()方法）
 */

public class MatrixWave extends View {
    private static final int HORIZONTAL_COUNT = 6;//水平／竖直方向划分的矩阵数量
    private static final int VERTICAL_COUNT = 1;
    private int mTotalCount;//总共需要计算的网格顶点个数
    private Bitmap mBitmap;//水面原图
    private float[] drawingVerts;//改变后的Verts网格坐标
    private float[] origVerts;//最初始的Verts网格坐标
    private Paint mPaint = new Paint();
    private ValueAnimator mValueAnimator;//用于不断的重复波浪动画
    private float mWidth;//图片宽高
    private float mHeight;
    private float mWaveHeight;//波浪落差
    private float offsetX = 0;//水平面在整体View的X坐标开始位置
    private float offsetY;//水平面在整体View的Y坐标开始位置
    private Path mWaterPath = new Path();//水面的三阶贝塞尔曲线
    protected float[] coordsX = new float[2];//用于获取Path上原始的X／Y坐标
    protected float[] coordsY = new float[2];//用于获取Path上便以后的X／Y坐标
    protected float pathOffsetPercent;//Path上的偏移量

    public MatrixWave(Context context) {
        this(context, null);
    }

    public MatrixWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTotalCount = (HORIZONTAL_COUNT+1)*(VERTICAL_COUNT+1)*2;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.water);
        drawingVerts = new float[mTotalCount];
        origVerts = new float[mTotalCount];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getWidth() != 0) {
            init();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init();
    }

    private void init() {
        mWidth = getWidth();
        mHeight = getHeight();
        offsetY = mHeight*0.4f;
        mWaveHeight = mHeight/25;

        createPath();
        initVert();
        startValuesAnim(1500);
    }

    /**
     * 初始化波浪的Path路径
     */
    private void createPath() {
        mWaterPath.reset();
        mWaterPath.moveTo(0, offsetY);
        int step = (int) (mWidth / HORIZONTAL_COUNT);
        boolean changeDirection = true;//控制波峰波谷
        for (int i = 0; i < HORIZONTAL_COUNT; i++) {
            if (changeDirection) {
                mWaterPath.cubicTo(offsetX + step * i, offsetY, offsetX + step * i + step / 2f, offsetY + mWaveHeight, offsetX + step * i + step, offsetY);
            } else {
                mWaterPath.cubicTo(offsetX + step * i, offsetY, offsetX + step * i + step / 2f, offsetY - mWaveHeight, offsetX + step * i + step, offsetY);
            }
            changeDirection = !changeDirection;
        }
    }

    /**
     * 初始化矩阵坐标
     */
    private void initVert() {
        float bitmapWidth = (float) mBitmap.getWidth();
        float bitmapHeight = (float) mBitmap.getHeight();
        int index = 0;
        for (int y = 0; y <= VERTICAL_COUNT; y++) {
            float fy = bitmapHeight / VERTICAL_COUNT * y;
            for (int x = 0; x <= HORIZONTAL_COUNT; x++) {
                float fx = bitmapWidth / HORIZONTAL_COUNT * x;
                origVerts[index*2+0] = drawingVerts[index*2+0] = fx;//该点的x坐标
                origVerts[index*2+1] = drawingVerts[index*2+1] = fy;//该点的y坐标
                index++;
            }
        }
    }

    /**
     * 矩阵变换
     * extraOffset 水平偏移量
     */
    public void matchVertsToPath() {
        PathMeasure pm = new PathMeasure(mWaterPath, false);
        for (int i = 0; i < origVerts.length / 2; i++) {
            float orignX = origVerts[2 * i];
            float orignY = origVerts[2 * i + 1];
            float percentOffsetX = orignX / mBitmap.getWidth();
            float percentOffsetXTemp = orignX / (mBitmap.getWidth() + (mBitmap.getWidth() / HORIZONTAL_COUNT) * 8f);
            percentOffsetXTemp += pathOffsetPercent;
            pm.getPosTan(pm.getLength() * (percentOffsetX), coordsX, null);
            pm.getPosTan(pm.getLength() * (percentOffsetXTemp), coordsY, null);
            if (orignY == 0) {//不为零说明是最下面那个点
                drawingVerts[i*2+0] = coordsX[0];//该点的x坐标
                drawingVerts[i*2+1] = coordsY[1];//该点的y坐标
            } else {
                drawingVerts[i*2+0] = coordsX[0];//该点的x坐标
                drawingVerts[i*2+1] = mHeight;//该点的y坐标
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmapMesh(mBitmap, HORIZONTAL_COUNT, VERTICAL_COUNT, drawingVerts, 0, null, 0, mPaint);
    }

    private void startValuesAnim(int duration) {
        mValueAnimator = ValueAnimator.ofFloat(0, 1 / 3f);
        mValueAnimator.setDuration(duration);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pathOffsetPercent = (float) animation.getAnimatedValue();
                matchVertsToPath();
                postInvalidate();
            }
        });
        mValueAnimator.start();
    }
}
