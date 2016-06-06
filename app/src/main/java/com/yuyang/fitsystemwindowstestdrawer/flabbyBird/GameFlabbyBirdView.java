package com.yuyang.fitsystemwindowstestdrawer.flabbyBird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 通过SurfaceView实现
 * FlabbyBird
 */
public class GameFlabbyBirdView extends SurfaceView implements Callback,Runnable {
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Thread t;
    private boolean isRunning;
    /**
     * 当情View的尺寸
     */
    private int mWidth;
    private int mHeight;
    private RectF mGamePanelRect = new RectF();
    /**
     * 背景
     */
    private Bitmap mBg;
    /**
     * 鸟相关
     */
    private Bird mBird;
    private Bitmap mBirdBitmap;

    public GameFlabbyBirdView(Context context) {
        this(context, null);
    }
    public GameFlabbyBirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);// 设置画布 背景透明
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);

        //初始化需要的图片资源
        initBitmaps();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mGamePanelRect.set(0,0,mWidth,mHeight);
        // 初始化mBird
        mBird = new Bird(getContext(), mWidth, mHeight, mBirdBitmap);
    }

    /**
     * 初始化图片
     */
    private void initBitmaps() {
        mBg = loadImageByResId(R.mipmap.flabby_bird_bg1);
        mBirdBitmap = loadImageByResId(R.mipmap.flabby_bird_b1);
    }

    /**
     * 根据resId加载图片
     * @param resId
     * @return
     */
    private Bitmap loadImageByResId(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning){
            mCanvas = mHolder.lockCanvas();
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        try
        {
            // 获得canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //绘制背景
                drawBg();
                //绘制鸟
                drawBird();
            }
        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * 绘制鸟
     */
    private void drawBird() {
        mBird.draw(mCanvas);
    }

    /**
     * 绘制背景
     */
    private void drawBg() {
        mCanvas.drawBitmap(mBg, null, mGamePanelRect, null);
    }
}
