package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.progressBars;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 圆形进度
 */
public class PointerCircleProgressBar extends ProgressBar {
    private Paint proPaint;//进度的画笔
    private Paint textPaint;//文字画笔
    private int width;//整个控件宽高
    private float radius;//整个控件半径
    private float potRadius;//进度圆点半径
    private int color = Color.parseColor("#BFBFBF");//未到达进度颜色
    private int proColor = Color.parseColor("#00EEFF");//已到达进度颜色
    private int textSize = 50;//中心文字大小
    private Rect textRect;

    public PointerCircleProgressBar(Context context) {
        this(context, null);
    }

    public PointerCircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointerCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        proPaint = new Paint();
        proPaint.setColor(color);
        proPaint.setStyle(Paint.Style.FILL);
        proPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(proColor);
        textPaint.setTextSize(DensityUtils.sp2px(context, textSize));

        textRect = new Rect();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(width, width);
        radius = width/2f;

        // 计算圆点半径
        float mSin_1 = (float) Math.sin(Math.toRadians(1.2));
        potRadius = mSin_1 * radius / (1 + mSin_1);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.translate(width/2f, width/2f);

        float currentPro = getProgress();
        float max = getMax();
        int progress = (int) (Math.floor(currentPro/max*100));
        proPaint.setColor(proColor);
        for (int i=1; i<=progress; i++){
            canvas.drawCircle(0, -(radius-potRadius), potRadius, proPaint);
            canvas.rotate(3.6f);
        }
        proPaint.setColor(color);
        for (int i=progress; i<100; i++){
            canvas.drawCircle(0, -(radius-potRadius), potRadius, proPaint);
            canvas.rotate(3.6f);
        }

        String proStr = progress+"%";
        textPaint.getTextBounds(proStr, 0, proStr.length(), textRect);
        int textWidth = textRect.width();

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float baseline = (fm.descent - fm.ascent)/2 - fm.descent;//如果文字带字母（入g），这样计算就会有点偏差

        canvas.drawText(proStr, -textWidth/2f, baseline, textPaint);
    }
}
