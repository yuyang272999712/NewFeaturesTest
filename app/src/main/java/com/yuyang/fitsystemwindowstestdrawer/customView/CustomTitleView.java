package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 自定义View 文本View
 */
public class CustomTitleView extends View {

    private String titleText;
    private int titleSize;
    private int titleColor;

    private Rect mBound;
    private Paint mPaint;

    /**
     * 用代码动态创建一个view而不使用布局文件xml inflate，那么此实现就可以了。
     * @param context
     */
    public CustomTitleView(Context context) {
        this(context, null);
    }

    /**
     * 此构造函数多了一个AttributeSet类型的参数，在通过布局文件xml创建一个view时，
     * 这个参数会将xml里设定的属性传递给构造函数。如果采用xml inflate的方法却没有在代码里实现此构造，那么运行时就会报错
     * @param context
     * @param attrs
     */
    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, R.style.DefaultCustomTitleStyle);
        titleText = a.getString(R.styleable.CustomTitleView_titleText);
        titleColor = a.getColor(R.styleable.CustomTitleView_titleColor, Color.BLACK);
        titleSize = a.getDimensionPixelSize(R.styleable.CustomTitleView_titleSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        Log.i("－－CustomTitleView－－", titleText);
        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(titleSize);
        // mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);

        /**
         * 添加点击事件
         */
        this.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                titleText = randomText();
                postInvalidate();
            }

        });
    }

    /**
     * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * UNSPECIFIED：表示子布局想要多大就多大，很少使用
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
            float textWidth = mBound.width();
            width = (int)textWidth + getPaddingLeft() + getPaddingRight();
        }
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(titleText, 0, titleText.length(), mBound);
            float textHeight = mBound.height();
            height = (int)textHeight + getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(titleColor);
        canvas.drawText(titleText,getWidth()/2-(mBound.width()/2),getHeight()/2+(mBound.height()/2),mPaint);
    }


    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        return sb.toString();
    }

}
