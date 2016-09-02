package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 温度表盘
 */
public class CustomTemperatureDial extends View {
    private Paint progressPaint;//外圈颜色画笔
    private Paint linePaint;//刻度画笔
    private Paint textPaint;//文字画笔
    private Paint needlePaint;//指针画笔
    private Paint leftPointerPaint; // 表针左半部分
    private Paint rightPointerPaint; // 表针右半部分

    private int progressWidth = DensityUtils.dp2px(getContext(), 15);//外圈颜色宽度
    private int dialWidthMin = DensityUtils.dp2px(getContext(), 1);//刻度宽
    private int dialWidthMax = DensityUtils.dp2px(getContext(), 2);//刻度宽
    private int dialCount = 40;//刻度数
    private int dialLong = DensityUtils.dp2px(getContext(), 10);//长刻度
    private int dialShort = DensityUtils.dp2px(getContext(), 5);//短刻度
    private int valueTextSize = DensityUtils.dp2px(getContext(), 15);//刻度文字大小
    private int warnTextSize = DensityUtils.dp2px(getContext(), 12);//警告文字大小
    private int needleRadius = DensityUtils.dp2px(getContext(), 17); // 中心圆半径

    private int size;//View的大小

    private float currentTemp = 0;//当前温度
    private float mAngle = 240f / dialCount;//每个刻度对应的角度

    public CustomTemperatureDial(Context context) {
        this(context, null);
    }

    public CustomTemperatureDial(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTemperatureDial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeJoin(Paint.Join.ROUND);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);

        needlePaint = new Paint();
        needlePaint.setAntiAlias(true);
        needlePaint.setColor(Color.GRAY);
        needlePaint.setStyle(Paint.Style.FILL);

        leftPointerPaint = new Paint();
        leftPointerPaint.setAntiAlias(true);
        leftPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        leftPointerPaint.setColor(Color.parseColor("#e1dcd6"));

        rightPointerPaint = new Paint();
        rightPointerPaint.setAntiAlias(true);
        rightPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        rightPointerPaint.setColor(Color.parseColor("#f4efe9"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY){
            widthSize = DensityUtils.dp2px(getContext(), 200);
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY){
            heightSize = DensityUtils.dp2px(getContext(), 200);
        }

        size = Math.min(widthSize, heightSize);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将画布移动到中间位置
        canvas.translate(size/2, size/2);

        //画颜色警告
        drawProgress(canvas);
        //画刻度
        drawDials(canvas);
        //画文字
        drawTexts(canvas);
        //画指针
        drawNeedle(canvas);
    }

    private void drawNeedle(Canvas canvas) {
        RectF rectF = new RectF(-needleRadius/2, -needleRadius/2, needleRadius/2, needleRadius/2);
        canvas.save();

        canvas.drawCircle(0, 0, needleRadius, needlePaint);

        // 先将指针与刻度0位置对齐
        canvas.rotate(-120, 0, 0);
        float angle = currentTemp * mAngle;
        canvas.rotate(angle, 0, 0);

        //为左右部分是为了画出来的指针有立体感,表针分左右两部分
        Path leftPath = new Path();
        leftPath.moveTo(-needleRadius/2, 0);
        leftPath.addArc(rectF, 90, 90);
        leftPath.lineTo(0, -(size/2 - progressWidth - dialWidthMax)+dialLong+valueTextSize);
        leftPath.lineTo(0 ,needleRadius/2);
        leftPath.close();
        canvas.drawPath(leftPath, leftPointerPaint);

        Path rightPath = new Path();
        rightPath.moveTo(needleRadius/2, 0);
        rightPath.addArc(rectF, 0, 90);
        rightPath.lineTo(0, -(size/2 - progressWidth - dialWidthMax)+dialLong+valueTextSize);
        rightPath.lineTo(needleRadius/2, 0);
        rightPath.close();
        canvas.drawPath(rightPath, rightPointerPaint);

        canvas.drawCircle(0, 0, needleRadius/4, needlePaint);

        canvas.restore();
    }

    private void drawTexts(Canvas canvas) {
        String normal = "正常";
        String warn = "预警";
        String dangr = "警告";
        int textRadius = size/2 - progressWidth/2 - warnTextSize/2;
        canvas.save();

        float textWidth;
        textPaint.setTextSize(warnTextSize);

        canvas.rotate(-60, 0, 0);
        textWidth = textPaint.measureText(normal);
        canvas.drawText(normal, -textWidth/2, -textRadius, textPaint);

        canvas.rotate(90, 0, 0);
        textWidth = textPaint.measureText(warn);
        canvas.drawText(warn, -textWidth/2, -textRadius, textPaint);

        canvas.rotate(60, 0, 0);
        textWidth = textPaint.measureText(dangr);
        canvas.drawText(dangr, -textWidth/2, -textRadius, textPaint);
        canvas.restore();
    }

    private void drawDials(Canvas canvas) {
        int dialRadius = size/2 - progressWidth - dialWidthMax/2;
        canvas.save();
        //外弧线
        linePaint.setStrokeWidth(dialWidthMax);
        RectF rectF = new RectF(-dialRadius, -dialRadius, dialRadius, dialRadius);
        canvas.drawArc(rectF, 150, 240, false, linePaint);
        //刻度
        textPaint.setTextSize(valueTextSize);
        canvas.rotate(-120, 0, 0);
        for (int i=0; i<=dialCount; i++){
            //5的倍数就画长刻度，并标上刻度数值
            if (i%5 == 0){//长刻度
                String value = i + "";
                float valueWidth = textPaint.measureText(value);
                linePaint.setStrokeWidth(dialWidthMax);
                canvas.drawLine(0, -dialRadius, 0, -dialRadius+dialLong, linePaint);
                canvas.drawText(value, -valueWidth/2, -dialRadius+dialLong+valueTextSize, textPaint);
            }else {
                linePaint.setStrokeWidth(dialWidthMin);
                canvas.drawLine(0, -dialRadius, 0, -dialRadius+dialShort, linePaint);
            }
            canvas.rotate(mAngle, 0, 0);
        }
        canvas.restore();
    }

    private void drawProgress(Canvas canvas) {
        int progressRadius = size/2-progressWidth/2;
        canvas.save();
        RectF rectF = new RectF(-progressRadius, -progressRadius, progressRadius, progressRadius);

        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(Color.GREEN);
        canvas.drawArc(rectF, 150, 120, false, progressPaint);
        progressPaint.setColor(Color.RED);
        canvas.drawArc(rectF, 330, 60, false, progressPaint);
        progressPaint.setStrokeCap(Paint.Cap.BUTT);
        progressPaint.setColor(Color.YELLOW);
        canvas.drawArc(rectF, 270, 60, false, progressPaint);

        canvas.restore();
    }

    /*********************************对外公布的方法*****************************************************/
    /**
     * 设置当前温度
     * @param currentTemp
     */
    public void setCurrentTemp(float currentTemp) {
        if (currentTemp < 0) {
            currentTemp = 0;
        } else if (currentTemp > 40) {
            currentTemp = 40;
        } else {
            this.currentTemp = currentTemp;
            postInvalidate();
        }
    }

}
