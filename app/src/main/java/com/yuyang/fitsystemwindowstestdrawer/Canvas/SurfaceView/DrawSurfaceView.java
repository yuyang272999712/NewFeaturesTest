package com.yuyang.fitsystemwindowstestdrawer.Canvas.SurfaceView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 画布
 */
public class DrawSurfaceView extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    private IBrush mBrush;
    private DrawPath mPath;
    private Paint mPaint;

    private SurfaceHolder holder;
    private Canvas canvas;
    private Thread thread;
    private boolean isRunning;
    private boolean isDrawing;
    private boolean isTouching;

    private DrawInvoker mInvoker;//绘制命令请求对象

    private Handler mHandler;
    private InvokerStateChangeListener invokerStateChangeListener;

    public DrawSurfaceView(Context context) {
        this(context, null);
    }

    public DrawSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mBrush = new NormalBrush();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setPathEffect(new CornerPathEffect(10));

        mInvoker = new DrawInvoker();

        mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (invokerStateChangeListener != null){
                    invokerStateChangeListener.stateChanged();
                }
                return true;
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        boolean retry = true;
        while (retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (isRunning){
            if (isDrawing) {
                long start = System.currentTimeMillis();
                draw();
                long end = System.currentTimeMillis();
                if (end - start < 50) {
                    try {
                        Thread.sleep(50 - (end - start));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isDrawing = false;
            }
            if (isTouching){
                draw();
                isTouching = false;
            }
        }
    }

    private void draw(){
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            mInvoker.execute(canvas);
            //canvas.drawPath(mPath, mPaint);
        }catch (Exception e){
        }finally {
            if (canvas != null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouching = true;
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath = new DrawPath();
                mPath.paint = new Paint(mPaint);
                mPath.path = new Path();
                mBrush.down(mPath.path, x, y);
                add(mPath);
                break;
            case MotionEvent.ACTION_MOVE:
                mBrush.move(mPath.path, x, y);
                break;
            case MotionEvent.ACTION_UP:
                mBrush.up(mPath.path, x, y);
                break;
        }
        return true;
    }

    /**
     * 增加一条绘制路径
     * @param path
     */
    private void add(DrawPath path){
        mInvoker.add(path);
    }

    /**
     * 重做上一步
     */
    public void redo(){
        isDrawing = true;
        mInvoker.redo();
    }

    /**
     * 撤销上一步的绘制
     */
    public void undo(){
        isDrawing = true;
        mInvoker.undo();
    }

    /**
     * 是否可以撤销
     * @return
     */
    public boolean canUndo(){
        return mInvoker.canUndo();
    }

    /**
     * 是否可以重做
     * @return
     */
    public boolean canRedo(){
        return mInvoker.canRedo();
    }

    /**
     * 设置画笔颜色
     * @param color
     */
    public void setPaintColor(int color){
        mPaint.setColor(color);
    }

    /**
     * 设置笔触
     * @param brush
     */
    public void setBrush(IBrush brush){
        mBrush = brush;
    }

    public void setInvokerStateChangeListener(InvokerStateChangeListener listener){
        invokerStateChangeListener = listener;
    }

    public interface InvokerStateChangeListener{
        void stateChanged();
    }
}
