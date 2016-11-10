package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.polygonsStatisticsView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 雷达统计图
 */

public class RadarChartView extends View {
    private final static String TAG = "RadarChartView";
    private final static int DEFAULT_TEXT_SIZE = 14;//默认文字大小sp
    private final static int DEFAULT_VIEW_LINE_WIDTH = 1;//默认网格线宽dp
    private final static int DEFAULT_VALUE_LINE_WIDTH = 3;//默认进度线宽dp
    private final static int DEFAULT_STEP_COUNT = 4;//默认雷达图颜色阶梯数

    private Paint mPaint;
    private int mDataCount;//数据集个数
    private List<CharSequence> mDataNames = new ArrayList<>();//数据名称
    private List<Float> mDataValues = new ArrayList<>();//数据对应的数值
    private float textSize;//文字大小
    private int textColor;//文字颜色
    private int stepLayerColor;//最外层背景色（必须半透明，如果不是透明的那就强制转换为75%透明度）
    private int valueLinesColor;//进度线颜色
    private CharSequence[] dataNames;
    private int defaultSize = 300;//默认大小
    private int height;//View宽高
    private int width;
    private float textHeight;//字符串宽高
    private int textWidth;
    private float textBottomHeight;//文字baseLine与文字最底部的距离
    private float radius;//半径
    private PointF centerPoint;//中心点
    private float viewLineWidth = 3;//分割线宽度
    private float valueLineWidth;//进度线宽度
    private float includedAngle;//夹角
    private int stepCount = DEFAULT_STEP_COUNT;
    private Path path = new Path();

    public RadarChartView(Context context) {
        this(context, null);
    }

    public RadarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RadarChart);
        textColor = array.getColor(R.styleable.RadarChart_polygons_text_color, Color.BLACK);
        textSize = array.getDimension(R.styleable.RadarChart_polygons_text_size, DensityUtils.sp2px(context, DEFAULT_TEXT_SIZE));
        stepLayerColor = array.getColor(R.styleable.RadarChart_polygons_bg_color, Color.parseColor("#2648afb6"));
        if (stepLayerColor < 0x00ffffff){//如果所给的背景色是不透明的
            stepLayerColor += 0x26000000;
        }
        valueLinesColor = array.getColor(R.styleable.RadarChart_polygons_line_color, Color.RED);
        viewLineWidth = array.getDimension(R.styleable.RadarChart_polygons_view_line_width, DensityUtils.dp2px(context, DEFAULT_VIEW_LINE_WIDTH));
        valueLineWidth = array.getDimension(R.styleable.RadarChart_polygons_value_line_width, DensityUtils.dp2px(context, DEFAULT_VALUE_LINE_WIDTH));
        dataNames = array.getTextArray(R.styleable.RadarChart_polygons_datas);
        if (dataNames == null || dataNames.length == 0){
            Log.e(TAG, "必须设定要显示的数据项");
            throw new RuntimeException("必须设定要显示的数据项");
        }else {
            mDataNames.addAll(Arrays.asList(dataNames));
            mDataCount = mDataNames.size();
            includedAngle = 360f/mDataCount;
            for (int i=0; i<mDataCount; i++){//初始化进度值
                mDataValues.add(0f);
            }
        }
        if (mDataCount < 3){
            Log.e(TAG, "dataNameResourceId对应的数据项不能少于3个");
            throw new RuntimeException("dataNameResourceId对应的数据项不能少于3个");
        }
        if (mDataNames.size() > 8){
            Log.w(TAG, "数据项太多了吧，我猜绘制出来肯定不好看");
        }
        array.recycle();

        defaultSize = DensityUtils.dp2px(context, defaultSize);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(textSize);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        textHeight = fontMetrics.bottom-fontMetrics.top;
        textBottomHeight = fontMetrics.bottom;

        Rect textRect = new Rect();
        //获取最长的字符串
        int position = 0;
        for (int i=1; i<mDataNames.size(); i++){
            String first = mDataNames.get(position).toString();
            String second = mDataNames.get(i).toString();
            if (second.length() > first.length()){
                position = i;
            }
        }
        mPaint.getTextBounds(mDataNames.get(position).toString(), 0, mDataNames.get(position).length(), textRect);
        textWidth = textRect.width();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY){
            height = (int) (Math.min(height, defaultSize+textHeight*2)+0.5);
        }
        if (widthMode != MeasureSpec.EXACTLY){
            width = Math.min(width, defaultSize+textWidth*2);
        }
        radius = Math.min(height/2-textHeight, width/2-textWidth);//半径
        centerPoint = new PointF(width/2, height/2);//中心点

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDataNames(canvas);
        drawRadarChart(canvas);
        drawCenterLines(canvas);
        drawValueLines(canvas);
    }

    /**
     * 绘制进度线（各个数据的实际值连线）
     * @param canvas
     */
    private void drawValueLines(Canvas canvas) {
        mPaint.setColor(valueLinesColor);
        mPaint.setStrokeWidth(valueLineWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        path.reset();
        float radius = this.radius*mDataValues.get(0);
        float angle = 0*includedAngle;
        float[] vertex = getXYByAngleAndRadius(angle, radius);
        path.moveTo(vertex[0], vertex[1]);//第一个点
        for (int i=1; i<mDataCount; i++){
            angle = i*includedAngle;
            radius = this.radius*mDataValues.get(i);
            vertex = getXYByAngleAndRadius(angle, radius);
            path.lineTo(vertex[0], vertex[1]);//第一个点
        }
        path.close();
        canvas.drawPath(path, mPaint);
    }

    /**
     * 画雷达图背景
     * @param canvas
     */
    private void drawRadarChart(Canvas canvas) {
        mPaint.setColor(stepLayerColor);
        mPaint.setStyle(Paint.Style.FILL);
        float[] vertex;
        for (int i=0; i<stepCount; i++){//画背景的阶梯渐变色
            path.reset();
            float radius = this.radius*(stepCount-i)/stepCount;
            vertex = getXYByAngleAndRadius(0, radius);
            path.moveTo(vertex[0], vertex[1]);
            for (int j=1; j<mDataCount; j++){
                float angle = j*includedAngle;
                vertex = getXYByAngleAndRadius(angle, radius);
                path.lineTo(vertex[0], vertex[1]);
            }
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 画文字
     * @param canvas
     */
    private void drawDataNames(Canvas canvas) {
        mPaint.setColor(textColor);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i=0; i<mDataCount; i++){
            float angle = i*includedAngle;
            String name = mDataNames.get(i).toString();
            float textWidth = mPaint.measureText(name);
            float x = 0,y = 0;//文字起始坐标
            if (angle == 0){//第一条竖线
                x = centerPoint.x-textWidth/2;
                y = textHeight;
            }else if (angle>0 && angle<180){//在第一四象限，文字显示在右面
                x = centerPoint.x + (float) (radius*Math.sin(Math.toRadians(angle)));
                y = centerPoint.y - (float) (radius*Math.cos(Math.toRadians(angle))) + textHeight/2;
            }else if (angle == 180){//竖直向下的线
                x = centerPoint.x-textWidth/2;
                y = centerPoint.y+radius+textHeight;
            }else {//第二三象限，文字显示在左面
                x = centerPoint.x + (float) (radius*Math.sin(Math.toRadians(angle))) - textWidth;
                y = centerPoint.y - (float) (radius*Math.cos(Math.toRadians(angle))) + textHeight/2;
            }
            canvas.drawText(name, x, y-textBottomHeight, mPaint);
        }
    }

    /**
     * 画中心星形线
     */
    private void drawCenterLines(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(viewLineWidth);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.save();
        for (int i=0; i<mDataCount; i++){
            canvas.drawLine(centerPoint.x, centerPoint.y, centerPoint.x, centerPoint.y-radius, mPaint);
            canvas.rotate(includedAngle, centerPoint.x, centerPoint.y);
        }
        canvas.restore();
    }

    /**
     * 通过角度和半径获取顶点坐标
     * @param angle
     * @param radius
     * @return
     */
    private float[] getXYByAngleAndRadius(float angle, float radius){
        float x = 0,y = 0;//顶点坐标
        if (angle == 0){//第一条竖线
            x = centerPoint.x;
            y = centerPoint.y - radius;
        }else if (angle>0 && angle<180){//在第一四象限
            x = centerPoint.x + (float) (radius*Math.sin(Math.toRadians(angle)));
            y = centerPoint.y - (float) (radius*Math.cos(Math.toRadians(angle)));
        }else if (angle == 180){//竖直向下的线
            x = centerPoint.x;
            y = centerPoint.y + radius;
        }else {//第二三象限
            x = centerPoint.x + (float) (radius*Math.sin(Math.toRadians(angle)));
            y = centerPoint.y - (float) (radius*Math.cos(Math.toRadians(angle)));
        }
        return new float[]{x, y};
    }

    /**供外部调用的方法********************************************/
    public void setDataValues(Float... values){
        setDataValues(Arrays.asList(values));
    }

    public void setDataValues(List<Float> values){
        if (values.size() != mDataCount){
            Log.w(TAG, "数据无法实现一一对应关系！");
            return;
        }
        if (values != null){
            mDataValues.clear();
            mDataValues.addAll(values);
            postInvalidate();
        }
    }

    public void setDataValue(float value, int index){
        if (index >= mDataCount){
            Log.w(TAG, "数据无法实现一一对应关系！");
            return;
        }
        mDataValues.remove(index);
        mDataValues.add(index, value);
        postInvalidate();
    }

}
