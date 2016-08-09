package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterRippleEffect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 使用Surface实现水波纹效果，这种方式不好用，背景无法控制
 */
public class RippleSurfaceView extends SurfaceView implements Runnable,SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Canvas canvas;
    private Thread thread;
    private boolean isRunning;

    private long mDuration = 6000;//波纹持续时间，默认2000毫秒
    private float mMaxRadius;//波纹最大半径
    private float mInitRadius;//波纹初始半径
    private float mInitRadiusRate = 0.05f;//波纹的初始半径与最大半径的比率
    private int mColor = Color.parseColor("#ff0000");
    private boolean isAuto = false;//是否自动产生波纹，还是点击后产生一个波纹
    private int mStepTime = 500;//波纹的自动生成时间间隔
    private boolean isFill = false;//波纹是否填充
    private float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getContext().getResources().getDisplayMetrics());
    private List<Circle> circles = new ArrayList<>();
    private Interpolator outSlowInterpolator = new LinearOutSlowInInterpolator();//越来越慢的渐变
    private Interpolator linearInterpolator = new LinearInterpolator();//均匀渐变
    private Paint mPaint;
    private long lastAddTime;

    public RippleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
        setFocusable(true);
        setFocusableInTouchMode(true);

        //TODO 获取给中属性值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        mColor = array.getColor(R.styleable.RippleView_ripple_color, mColor);
        isAuto = array.getBoolean(R.styleable.RippleView_ripple_auto, false);
        isFill = array.getBoolean(R.styleable.RippleView_ripple_fill, false);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        if (isFill){
            mPaint.setStyle(Paint.Style.FILL);
        }else {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius = Math.min(w, h)/2;
        mInitRadius = mMaxRadius*mInitRadiusRate;
        if (isFill){//如果是填充模式就开启颜色由内向外的 镜像渐变
            RadialGradient lg = new RadialGradient(w/2,h/2,mMaxRadius,mColor,Color.TRANSPARENT, Shader.TileMode.CLAMP);
            mPaint.setShader(lg);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
    }

    @Override
    public void run() {
        lastAddTime = System.currentTimeMillis();
        while (isRunning){
            long start = System.currentTimeMillis();
            if (isAuto){
                addWave();
            }
            draw();
            long end = System.currentTimeMillis();
            if (end - start < 50){
                try {
                    Thread.sleep(50-(end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addWave() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAddTime >= mStepTime) {
            if (isFill) {//如果是填充的效果，就是用越来越的渐变效果
                circles.add(new Circle(mDuration, mInitRadius, mMaxRadius, outSlowInterpolator));
            } else {
                circles.add(new Circle(mDuration, mInitRadius, mMaxRadius, linearInterpolator));
            }
            lastAddTime = currentTime;
        }
    }

    private void draw() {
        try {
            // 获得canvas
            canvas = holder.lockCanvas();
            //TODO yuyang SurfaceView的Canvas如果不设置背景进行覆盖，那么会看到上次绘制的缓存
            canvas.drawColor(Color.WHITE);
            if (canvas != null) {
                Iterator<Circle> iterator = circles.iterator();
                while (iterator.hasNext()){
                    Circle circle = iterator.next();
                    if (System.currentTimeMillis() - circle.mCreateTime < mDuration){
                        if (!isFill){//如果不是填充模式,那么圆形边就逐渐变细
                            mPaint.setStrokeWidth(strokeWidth * circle.getStrokeWidthRate());
                        }else {
                            mPaint.setAlpha(circle.getAlpha());
                        }
                        canvas.drawCircle(getWidth()/2, getHeight()/2, circle.getCurrentRadius(), mPaint);
                    }else {
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * 给外部调用
     */
    public void startOneWave(){
        if (isFill) {//如果是填充的效果，就是用越来越的渐变效果
            circles.add(new Circle(mDuration, mInitRadius, mMaxRadius, outSlowInterpolator));
        }else {
            circles.add(new Circle(mDuration, mInitRadius, mMaxRadius, linearInterpolator));
        }
        invalidate();
    }
}
