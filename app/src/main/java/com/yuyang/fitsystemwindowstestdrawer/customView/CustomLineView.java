package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 绘制折线动态图
 */
public class CustomLineView extends View {
    private static final String X_KEY = "Xpos";
    private static final String Y_KEY = "Ypos";

    private List<Map<String, Integer>> mListPoint = new ArrayList<>();

    private Paint mPaint = new Paint();

    public CustomLineView(Context context) {
        super(context);
    }

    public CustomLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);

        for (int index=0; index<mListPoint.size(); index++){
            if (index > 0){
                canvas.drawLine(mListPoint.get(index-1).get(X_KEY), mListPoint.get(index-1).get(Y_KEY),
                        mListPoint.get(index).get(X_KEY), mListPoint.get(index).get(Y_KEY), mPaint);
                canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
            }
        }
    }

    /**
     * 设置直线点
     * @param curX
     * @param curY
     */
    public void setLinePoint(int curX, int curY){
        Map<String, Integer> temp = new HashMap<>();
        temp.put(X_KEY, curX);
        temp.put(Y_KEY, curY);
        mListPoint.add(temp);
        invalidate();
    }
}
