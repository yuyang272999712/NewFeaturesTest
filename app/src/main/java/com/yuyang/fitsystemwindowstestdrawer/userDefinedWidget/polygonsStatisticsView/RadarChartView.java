package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.polygonsStatisticsView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
    private final static int DEFAULT_TEXT_SIZE = 14;//默认文字大小sp
    private final static int DEFAULT_VIEW_LINE_WIDTH = 1;//默认网格线宽dp
    private final static int DEFAULT_VALUE_LINE_WIDTH = 3;//默认进度线宽dp

    private Paint mPaint;
    private int mDataCount;//数据集个数
    private List<CharSequence> mDataNames = new ArrayList<>();//数据名称
    private float textSize;//文字大小
    private int textColor;//文字颜色
    private int outerLayerColor;//最外层背景色（必须半透明，如果不是透明的那就强制转换为75%透明度）
    private CharSequence[] dataNames;
    private int defaultSize = 300;//默认大小
    private int height;//View宽高
    private int width;
    private int textHeight;//字符串宽高
    private int textWidth;
    private float radius;//半径
    private PointF centerPoint;//中心点
    private float viewLineWidth = 3;//网格线宽度
    private float valueLineWidth;//进度线宽度
    private float angle;//夹角
    private Path firstPath;//最外层圆
    private Path secondPath;
    private Path thirdPath;
    private Path fourthPath;

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
        outerLayerColor = array.getColor(R.styleable.RadarChart_polygons_bg_color, Color.parseColor("#2648afb6"));
        if (outerLayerColor < 0x00ffffff){//如果所给的背景色是不透明的
            outerLayerColor += 0x26000000;
        }
        viewLineWidth = array.getDimension(R.styleable.RadarChart_polygons_view_line_width, DensityUtils.dp2px(context, DEFAULT_VIEW_LINE_WIDTH));
        valueLineWidth = array.getDimension(R.styleable.RadarChart_polygons_value_line_width, DensityUtils.dp2px(context, DEFAULT_VALUE_LINE_WIDTH));
        dataNames = array.getTextArray(R.styleable.RadarChart_polygons_datas);
        if (dataNames == null || dataNames.length == 0){
            Log.e("PolygonsStatisticsView", "必须设定要显示的数据项");
            throw new RuntimeException("必须设定要显示的数据项");
        }else {
            mDataNames.addAll(Arrays.asList(dataNames));
            mDataCount = mDataNames.size();
            angle = 360f/mDataCount;
        }
        /*if (dataNameResourceId > 0){
            mDataNames.addAll(Arrays.asList(context.getResources().getStringArray(dataNameResourceId)));
            mDataCount = mDataNames.size();
            angle = 360f/mDataCount;
        }else {
            Log.e("PolygonsStatisticsView", "必须设定要显示的数据项");
            throw new RuntimeException("必须设定要显示的数据项");
        }*/
        if (mDataCount < 3){
            Log.e("PolygonsStatisticsView", "dataNameResourceId对应的数据项不能少于3个");
            throw new RuntimeException("dataNameResourceId对应的数据项不能少于3个");
        }
        if (mDataNames.size() > 8){
            Log.e("PolygonsStatisticsView", "数据项太多了吧，我猜绘制出来肯定不好看");
        }
        array.recycle();

        defaultSize = DensityUtils.dp2px(context, defaultSize);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(textSize);

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
        textHeight = textRect.height();
        textWidth = textRect.width();
    }

    /**
     * 添加数据点个数
     */
    /*public void setDataNames(String... names){
        setDataNames(Arrays.asList(names));
    }

    public void setDataNames(Collection<? extends String> names){
        if (names != null){
            mDataNames.addAll(names);
            mDataCount = mDataNames.size();
            invalidate();
        }
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY){
            height = Math.min(height, defaultSize+textHeight*2);
        }
        if (widthMode != MeasureSpec.EXACTLY){
            width = Math.min(width, defaultSize+textWidth*2);
        }
        radius = Math.min(height/2-textHeight, width/2-textWidth);//半径
        centerPoint = new PointF(width/2, height/2);//中心点

        firstPath = new Path();
        firstPath.moveTo(width/2, height/2-radius);
        firstPath.addCircle(width/2, height/2, radius, Path.Direction.CW);
        secondPath = new Path();
        secondPath.moveTo(width/2, height/2-radius*3/4);
        secondPath.addCircle(width/2, height/2, radius*3/4, Path.Direction.CW);
        thirdPath = new Path();
        thirdPath.moveTo(width/2, height/2-radius/2);
        thirdPath.addCircle(width/2, height/2, radius/2, Path.Direction.CW);
        fourthPath = new Path();
        fourthPath.moveTo(width/2, height/2-radius/4);
        fourthPath.addCircle(width/2, height/2, radius/4, Path.Direction.CW);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCenterLines(canvas);
        drawDataNames(canvas);
    }

    /**
     * 画文字
     * @param canvas
     */
    private void drawDataNames(Canvas canvas) {
        mPaint.setColor(textColor);
        PathMeasure pathMeasure = new PathMeasure(firstPath, true);
        float pathLength = pathMeasure.getLength();
        for (int i=0; i<mDataCount; i++){
            float[] textPosition = new float[2];
            pathMeasure.getPosTan(pathLength/mDataCount*i, textPosition, null);
            if (i == 0){//第一个点，文字肯定绘制在最上面
                canvas.drawText(mDataNames.get(i).toString(), centerPoint.x-textWidth/2, centerPoint.y-radius, mPaint);
            }else if (mDataCount%2==0 && i==mDataCount/2){//整除说明最下面正中需要绘制文字
                canvas.drawText(mDataNames.get(i).toString(), centerPoint.x-textWidth/2, centerPoint.y+radius+textHeight, mPaint);
            }else if (mDataCount*1.0f/2>i){//右侧文字
                canvas.drawText(mDataNames.get(i).toString(), textPosition[0], textPosition[1]-textHeight/2, mPaint);
            }else {//左侧文字
                canvas.drawText(mDataNames.get(i).toString(), textPosition[0]+textWidth, textPosition[1]-textHeight/2, mPaint);
            }
        }
    }

    /**
     * 画中心星形线
     */
    private void drawCenterLines(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(viewLineWidth);
        canvas.save();
        for (int i=0; i<mDataCount; i++){
            canvas.drawLine(centerPoint.x, centerPoint.y, centerPoint.x, centerPoint.y-radius, mPaint);
            canvas.rotate(angle, centerPoint.x, centerPoint.y);
        }
        canvas.restore();
    }
}
