package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterRippleEffect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 支付宝咻一咻、探探的主界面搜索 的 水波纹效果
 */
public class RippleView extends View{
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
    //用于没mStepTime发送一次生成波纹事件
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (isFill) {//如果是填充的效果，就是用越来越的渐变效果
                circles.add(new Circle(mDuration, mInitRadius, mMaxRadius, outSlowInterpolator));
            }else {
                circles.add(new Circle(mDuration, mInitRadius, mMaxRadius, linearInterpolator));
            }
            handler.sendEmptyMessageDelayed(0, mStepTime);
            invalidate();
            return false;
        }
    });
    private Paint mPaint;

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //ZHU yuyang 获取给中属性值
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mMaxRadius = Math.min(width, height)/2;
        mInitRadius = mMaxRadius*mInitRadiusRate;
        if (isFill){//如果是填充模式就开启颜色由内向外的 镜像渐变
            RadialGradient lg = new RadialGradient(width/2,height/2,mMaxRadius,mColor,Color.TRANSPARENT, Shader.TileMode.CLAMP);
            mPaint.setShader(lg);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
        if (circles.size() > 0) {
            postInvalidateDelayed(10);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isAuto) {
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
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
