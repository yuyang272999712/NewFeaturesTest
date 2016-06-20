package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterWaveEffect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付宝咻一咻、探探的主界面搜索 的 水波纹效果
 */
public class WaveView extends View{
    private long mDuration = 1500;//波纹持续时间，默认1500毫秒
    private float mMaxRadius;//波纹最大半径
    private float mInitRadius;//波纹初始半径
    private float mInitRadiusRate = 0.25f;//波纹的初始半径与最大半径的比率
    private boolean isAuto = false;//是否自动产生波纹，还是点击后产生一个波纹
    private int mStepTime = 300;//波纹的自动生成时间间隔
    private boolean isFill = false;//波纹是否填充
    private List<Circle> circles = new ArrayList<>();
    //用于没mStepTime发送一次生成波纹事件
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            circles.add(new Circle(mDuration, mInitRadius, mMaxRadius));
            handler.sendEmptyMessageDelayed(0, mStepTime);
            invalidate();
            return false;
        }
    });
    private Paint mPaint;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //TODO 获取给中属性值

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mMaxRadius = Math.min(width, height);
        mInitRadius = mMaxRadius*mInitRadiusRate;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
        handler.removeMessages(0);
    }
}
