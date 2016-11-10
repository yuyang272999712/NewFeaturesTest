package com.yuyang.fitsystemwindowstestdrawer.metricsAbout.textFontMetrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 文字测量
 */

public class TextMetricsView extends View {
    public static final int DEFAULT_TEXT_SIZE = 30;//sp
    public static final String DEFAULT_STRING = "aAGgZzJj测试";

    private Paint textPaint;
    private Paint linePaint;
    private Paint bgPaint;
    private int textSize;
    private Rect textRect;
    private FontMetrics fontMetrics;
    private String text = DEFAULT_STRING;
    private float top;
    private float ascent;
    private float descent;
    private float bottom;
    private float leading;

    public TextMetricsView(Context context) {
        this(context, null);
    }
    public TextMetricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public TextMetricsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linePaint = new Paint();
        textPaint = new Paint();
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#773CFF00"));
        textSize = DensityUtils.sp2px(context, DEFAULT_TEXT_SIZE);
        textPaint.setTextSize(textSize);
        fontMetrics = textPaint.getFontMetrics();

        top = fontMetrics.top;
        ascent = fontMetrics.ascent;
        descent = fontMetrics.descent;
        bottom = fontMetrics.bottom;
        leading = fontMetrics.leading;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (height < textSize){
            height = textSize;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("TextMetricsView", "top      is:" + top);
        Log.d("TextMetricsView", "ascent   is:" + ascent);
        Log.d("TextMetricsView", "descent  is:" + descent);
        Log.d("TextMetricsView", "bottom   is:" + bottom);
        Log.d("TextMetricsView", "leading  is:" + leading);

        textRect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textRect);

        //View的中心点坐标
        int centerY = getHeight()/2;
        int centerX = getWidth()/2;
        int height = getHeight();
        int width = getWidth();

        //画中心线
        linePaint.setColor(Color.WHITE);
        canvas.drawLine(0, centerY, width, centerY, linePaint);

        //!--yuyang 一般情况下都是这么计算中心文字的y线的,但是Canvas的drawText方法中y值是text的baseline，所有y值需要整体上移descent高度
        // 即: float baselineY = centerY+textRect.height()/2-descent;
        float baselineY = centerY+textRect.height()/2;

        //画baseline
        linePaint.setColor(Color.RED);
        canvas.drawLine(0, baselineY, width, baselineY, linePaint);
        //画ascent
        linePaint.setColor(Color.GREEN);
        canvas.drawLine(0, baselineY+ascent, width, baselineY+ascent, linePaint);
        //画top
        linePaint.setColor(Color.GRAY);
        canvas.drawLine(0, baselineY+top, width, baselineY+top, linePaint);
        //画descent
        linePaint.setColor(Color.YELLOW);
        canvas.drawLine(0, baselineY+descent, width, baselineY+descent, linePaint);
        //画bottom
        linePaint.setColor(Color.BLUE);
        canvas.drawLine(0, baselineY+bottom, width, baselineY+bottom, linePaint);

        //画文字
        canvas.drawText(text, centerX-textRect.width()/2, baselineY, textPaint);

        //画文字的Rect
        canvas.drawRect(centerX-textRect.width()/2,centerY-textRect.height()/2,
                centerX+textRect.width()/2,centerY+textRect.height()/2, bgPaint);
    }

    public float getFontLeading() {
        return leading;
    }

    public float getFontTop() {
        return top;
    }

    public float getFontAscent() {
        return ascent;
    }

    public float getFontDescent() {
        return descent;
    }

    public float getFontBottom() {
        return bottom;
    }

    public void setText(String text){
        this.text = text;
        invalidate();
    }
}
