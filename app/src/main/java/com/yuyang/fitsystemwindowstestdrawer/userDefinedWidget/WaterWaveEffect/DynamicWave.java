package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterWaveEffect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 实现动态波浪效果
 *
 * 正余弦函数方程为：
 * y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；
 *
 *  类似于数学里面的细分法，一条波纹，如果横向以一个像素点为单位进行细分，则形成view总宽度条直线，并且每条直线的起点和
 * 终点我们都能知道，在此基础上我们只需要循环绘制出所有细分出来的直线（直线都是纵向的），则形成了一条静态的水波纹
 */
public class DynamicWave extends View {
    //波浪颜色
    private final int WAVE_PAINT_COLOR = 0x880000aa;
    private int wave_color;
    //y = Asin(wx+b)+h 参数
    private final int STRETCH_FACTOR_A = 20;//相当于A
    private final int OFFSET_Y = 0;//相当于h
    private float mCycleFactorW;//相当于w
    //第一条波浪的速度
    private final int TRANSLATE_X_SPEED_ONE = 7;
    //第二条波浪的速度
    private final int TRANSLATE_X_SPEED_TWO = 5;

    //当前View宽高
    private int mTotalWidth, mTotalHeight;
    //记录正余弦函数水平方向上所有点的Y坐标
    private float[] mYPositions;
    //第一条波浪的偏移坐标
    private float[] mResetOneYPositions;
    //第二条波浪的偏移坐标
    private float[] mResetTwoYPositions;
    //第一条波浪偏移量
    private int mXOffsetSpeedOne;
    //第二条波浪偏移量
    private int mXOffsetSpeedTwo;
    //累计偏移量
    private int mXOneOffset;
    private int mXTwoOffset;

    private Paint mWavePaint;
    private DrawFilter mDrawFilter;

    public DynamicWave(Context context) {
        this(context, null);
    }

    public DynamicWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义波浪颜色
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DynamicWave);
        wave_color = array.getColor(R.styleable.DynamicWave_wave_color, WAVE_PAINT_COLOR);

        // 将dp转化为px，用于控制不同分辨率上移动速度基本一致
        mXOffsetSpeedOne = DensityUtils.dp2px(getContext(), TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = DensityUtils.dp2px(getContext(), TRANSLATE_X_SPEED_TWO);

        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setColor(wave_color);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //记录当前View的宽高
        mTotalHeight = h;
        mTotalWidth = w;
        // 用于保存原始波纹的y值
        mYPositions = new float[mTotalWidth];
        // 用于保存波纹一的y值
        mResetOneYPositions = new float[mTotalWidth];
        // 用于保存波纹二的y值
        mResetTwoYPositions = new float[mTotalWidth];

        // 将周期定为view总宽度
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

        // 根据view总宽度得出所有对应的y值
        for (int i=0; i<mTotalWidth; i++){
            mYPositions[i] = (float) (STRETCH_FACTOR_A*Math.sin(mCycleFactorW*i)+OFFSET_Y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 从canvas层面去除绘制时锯齿
        canvas.setDrawFilter(mDrawFilter);
        //根据累计偏移量重新给第一、二条波浪的数组赋值
        resetPositonY();
        for (int i=0; i<mTotalWidth; i++) {
            //TODO 减200只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
            //绘制第一条波浪
            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - 400, i, mTotalHeight, mWavePaint);
            //绘制第二条波浪
            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 400, i, mTotalHeight, mWavePaint);
        }

        //改变两条波浪的偏移量
        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;

        //如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mTotalWidth){
            mXOneOffset = 0;
        }
        if (mXTwoOffset >= mTotalWidth){
            mXTwoOffset = 0;
        }

        invalidate();
    }

    private void resetPositonY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        //同样的方式处理第二条波浪
        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0, yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }
}
