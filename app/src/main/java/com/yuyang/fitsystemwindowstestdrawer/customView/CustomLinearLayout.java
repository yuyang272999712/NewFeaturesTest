package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 上下带锯齿的Layout
 */
public class CustomLinearLayout extends LinearLayout {
    private Paint paint;
    private float space = 8;//间距
    private float radius = 10;//半径
    private int circleNum;//半圆数量
    private float remain;//计算完成后两端的剩余长度

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleNum = (int) ((w-space)/(2*radius+space));
//        remain = w - circleNum * (2*radius+space);
        if (remain==0){
            remain = (int)(w-space)%(2*radius+space);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<circleNum; i++){
            float x = space+radius+remain/2+((space+radius*2)*i);
            canvas.drawCircle(x,0,radius,paint);
            canvas.drawCircle(x,getHeight(),radius,paint);
        }
    }
}
