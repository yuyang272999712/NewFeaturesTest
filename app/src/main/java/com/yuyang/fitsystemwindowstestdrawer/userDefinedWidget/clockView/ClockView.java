package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.clockView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

/**
 * 模拟表盘效果
 */
public class ClockView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private boolean isRunning;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;

    // 默认半径
    private static final int DEFAULT_RADIUS = 200;
    //圆和刻度画笔
    private Paint mPaint;
    //指针画笔
    private Paint mPointerPaint;
    // 画布的宽高
    private int mCanvasWidth, mCanvasHeight;
    // 时钟半径
    private int mRadius = DEFAULT_RADIUS;
    // 秒针长度
    private int mSecondPointerLength;
    // 分针长度
    private int mMinutePointerLength;
    // 时针长度
    private int mHourPointerLength;
    // 时刻度长度
    private int mHourDegreeLength;
    // 秒刻度长度
    private int mSecondDegreeLength;
    // 时钟显示的时、分、秒
    private int mHour, mMinute, mSecond;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);

        //获取当前时、分、秒
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mSecond = Calendar.getInstance().get(Calendar.SECOND);

        mPaint = new Paint();
        mPointerPaint = new Paint();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);

        mPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointerPaint.setColor(Color.BLACK);
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setTextSize(22);
        mPointerPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int desiredWidth, desiredHeight;
        if (widthMode == MeasureSpec.EXACTLY){
            desiredWidth = widthSize;
        }else {
            desiredWidth = mRadius*2+getPaddingRight()+getPaddingLeft();
            if (widthMode == MeasureSpec.AT_MOST){
                desiredWidth = Math.min(desiredWidth, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY){
            desiredHeight = heightSize;
        }else {
            desiredHeight = mRadius*2+getPaddingTop()+getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST){
                desiredHeight = Math.min(desiredHeight, heightSize);
            }
        }

        // +4是为了设置默认的2px的内边距，因为绘制时钟的圆的画笔设置的宽度是2px
        setMeasuredDimension(mCanvasWidth=desiredWidth+4, mCanvasHeight=desiredHeight+4);

        mRadius = (int)(Math.min(desiredWidth-getPaddingRight()-getPaddingLeft(), desiredHeight-getPaddingTop()-getPaddingBottom())*1.0f/2);

        // 这里我们定义时刻度长度为半径的1/7
        mHourDegreeLength = (int)(mRadius*1.0f/7);
        // 秒刻度长度为时刻度长度的一半
        mSecondDegreeLength = (int)(mHourDegreeLength*1.0f/2);

        // 时针长度为半径一半
        // 指针长度比 hour : minute : second = 1 : 1.25 : 1.5
        mHourPointerLength = (int)(mRadius*1.0f/2);
        mMinutePointerLength = (int)(mHourPointerLength*1.25f);
        mSecondPointerLength = (int)(mHourPointerLength*1.5f);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning){
            long start = System.currentTimeMillis();
            logic();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 1000){
                    Thread.sleep(1000-(end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 逻辑处理
     * 秒数+1到60的时候归0，同时分钟数+1，分钟数到60的时候归0，小时数+1，小时数到24的时候归0.
     */
    private void logic() {
        mSecond++;
        if (mSecond == 60){
            mSecond = 0;
            mMinute++;
            if (mMinute == 60){
                mMinute = 0;
                mHour++;
                if (mHour == 24){
                    mHour = 0;
                }
            }
        }
    }

    private void draw() {
        try {
            // 获得canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //刷屏
                mCanvas.drawColor(Color.WHITE);
                // 1.将坐标系原点移至去除内边距后的画布中心
                mCanvas.translate(mCanvasWidth*1.0f/2+getPaddingLeft()-getPaddingRight(), mCanvasHeight*1.0f/2+getPaddingTop()-getPaddingBottom());
                // 2.绘制圆盘
                mPaint.setStrokeWidth(2f);// 画笔设置2个像素的宽度
                mCanvas.drawCircle(0, 0, mRadius, mPaint);
                // 3.绘制时刻度
                for (int i=0;i<12;i++){
                    mCanvas.drawLine(0, mRadius, 0, mRadius-mHourDegreeLength, mPaint);
                    mCanvas.rotate(30);//旋转30度
                }
                // 4.绘制秒刻度
                mPaint.setStrokeWidth(1.5f);
                for (int i=0; i<60; i++){
                    if (i%5 != 0){
                        mCanvas.drawLine(0, mRadius, 0, mRadius-mSecondDegreeLength, mPaint);
                    }
                    mCanvas.rotate(6);//旋转6度
                }
                // 5.绘制数字
                mPointerPaint.setColor(Color.BLACK);
                for (int i=0; i<12; i++){
                    String number = 6 + i < 12 ? String.valueOf(6 + i) : (6 + i) > 12
                            ? String.valueOf(i - 6) : "12";
                    mCanvas.drawText(number, 0, mRadius*5.5f/7, mPointerPaint);
                    mCanvas.rotate(30);
                }
                // 6.绘制上下午
                mCanvas.drawText(mHour < 12 ? "AM" : "PM", 0, mRadius * 1.5f / 4, mPointerPaint);
                // 7.绘制时针
                Path path = new Path();
                path.moveTo(0, 0);
                int[] hourPointerCoordinates = getPointerCoordinates(mHourPointerLength);
                path.lineTo(hourPointerCoordinates[0], hourPointerCoordinates[1]);
                path.lineTo(hourPointerCoordinates[2], hourPointerCoordinates[3]);
                path.lineTo(hourPointerCoordinates[4], hourPointerCoordinates[5]);
                path.close();
                mCanvas.save();
                mCanvas.rotate(180 + mHour % 12 * 30 + mMinute * 1.0f / 60 * 30);
                mCanvas.drawPath(path, mPointerPaint);
                mCanvas.restore();
                // 8.绘制分针
                path.reset();
                path.moveTo(0, 0);
                int[] minutePointerCoordinates = getPointerCoordinates(mMinutePointerLength);
                path.lineTo(minutePointerCoordinates[0], minutePointerCoordinates[1]);
                path.lineTo(minutePointerCoordinates[2], minutePointerCoordinates[3]);
                path.lineTo(minutePointerCoordinates[4], minutePointerCoordinates[5]);
                path.close();
                mCanvas.save();
                mCanvas.rotate(180 + mMinute * 6);
                mCanvas.drawPath(path, mPointerPaint);
                mCanvas.restore();
                // 9.绘制秒针
                mPointerPaint.setColor(Color.RED);
                path.reset();
                path.moveTo(0, 0);
                int[] secondPointerCoordinates = getPointerCoordinates(mSecondPointerLength);
                path.lineTo(secondPointerCoordinates[0], secondPointerCoordinates[1]);
                path.lineTo(secondPointerCoordinates[2], secondPointerCoordinates[3]);
                path.lineTo(secondPointerCoordinates[4], secondPointerCoordinates[5]);
                path.close();
                mCanvas.save();
                mCanvas.rotate(180 + mSecond * 6);
                mCanvas.drawPath(path, mPointerPaint);
                mCanvas.restore();
            }
        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * 获取指针坐标
     *
     * @param pointerLength 指针长度
     * @return int[]{x1,y1,x2,y2,x3,y3}
     */
    private int[] getPointerCoordinates(int pointerLength) {
        int y = (int) (pointerLength * 3.0f / 4);
        int x = (int) (y * Math.tan(Math.PI / 180 * 5));
        return new int[]{-x, y, 0, pointerLength, x, y};
    }
}
