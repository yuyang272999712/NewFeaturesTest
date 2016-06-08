package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.progressBars;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 自定义带数字的水平进度条
 */
public class HorizontalProgressBarWithNumber extends ProgressBar {
    /**
     * 各种默认值
     */
    private static final int DEFAULT_COLOR_UNREACHED_COLOR = 0xFFd3d6da;
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
    private static final int DEFAULT_SIZE_TEXT_OFFSET = 10;

    protected Paint mPaint = new Paint();
    //文字相关变量
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize = DensityUtils.sp2px(getContext(), DEFAULT_TEXT_SIZE);
    protected int mTextOffset = DensityUtils.dp2px(getContext(), DEFAULT_SIZE_TEXT_OFFSET);
    //进度条相关变量
    protected int mReachedBarColor = DEFAULT_TEXT_COLOR;
    protected int mReachedProgressBarHeight = DensityUtils.dp2px(getContext(), DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);
    protected int mUnReachedBarColor = DEFAULT_COLOR_UNREACHED_COLOR;
    protected int mUnReachedProgressBarHeight = DensityUtils.dp2px(getContext(), DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
    /**
     * View的真正宽度，不包括padding
     */
    protected int mRealWidth;
    /**
     * 是否绘制文字
     */
    protected boolean mIfDrawText = true;
    protected static final int VISIBLE = 0;

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs) {
        this(context, attrs, 0);//TODO yuyang 这里的父类方法中会带入一个默认的样式，这个默认样式中indeterminateOnly被设置为true，所有视图将不会更新
    }

    public HorizontalProgressBarWithNumber(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        obtainStyledAttributes(attrs);
        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    /**
     * 获取自定义属性
     * @param attrs
     */
    private void obtainStyledAttributes(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBarWithNumber);
        mTextColor = array.getColor(R.styleable.HorizontalProgressBarWithNumber_progress_text_color, mTextColor);
        mTextSize = (int) array.getDimension(R.styleable.HorizontalProgressBarWithNumber_progress_text_size, mTextSize);
        mTextOffset = (int) array.getDimension(R.styleable.HorizontalProgressBarWithNumber_progress_text_offset, mTextOffset);
        int textVisible = array.getInt(R.styleable.HorizontalProgressBarWithNumber_progress_text_visibility, VISIBLE);
        if (textVisible == VISIBLE){
            mIfDrawText = true;
        }else {
            mIfDrawText = false;
        }
        mReachedBarColor = array.getColor(R.styleable.HorizontalProgressBarWithNumber_progress_reached_color, mReachedBarColor);
        mUnReachedBarColor = array.getColor(R.styleable.HorizontalProgressBarWithNumber_progress_unreached_color, mUnReachedBarColor);
        mReachedProgressBarHeight = (int) array.getDimension(R.styleable.HorizontalProgressBarWithNumber_progress_reached_bar_height, mReachedProgressBarHeight);
        mUnReachedProgressBarHeight = (int) array.getDimension(R.styleable.HorizontalProgressBarWithNumber_progress_unreached_bar_height, mUnReachedProgressBarHeight);
        array.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //如果不是用户指定的高度，那么高度必须要满足绘制当前控件的组件高度
        if (heightMode != MeasureSpec.EXACTLY){
            float textHeight = mPaint.ascent() + mPaint.descent();
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom()
                                + Math.max(Math.max(mReachedProgressBarHeight,mUnReachedProgressBarHeight), Math.abs(textHeight)));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        //画笔平移到指定paddingLeft， getHeight() / 2位置，注意以后坐标都为以此为0，0
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        boolean noNeedBg = false;
        //当前进度和总值的比例
        float radio = getProgress()*1.0f/getMax();
        //已达到的宽度
        float progressPosX = mRealWidth*radio;
        //绘制的文笔
        String text = getProgress()+"%";
        //拿到文字的宽高
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.ascent()+mPaint.descent())/2;
        if (mIfDrawText) {
            //如果到达最后，则未到达的进度条不需要绘制
            if (progressPosX + textWidth > mRealWidth) {
                progressPosX = mRealWidth - textWidth;
                noNeedBg = true;
            }
            //绘制已到达的进度条
            float endX = progressPosX - mTextOffset / 2;
            if (endX > 0) {
                mPaint.setColor(mReachedBarColor);
                mPaint.setStrokeWidth(mReachedProgressBarHeight);
                canvas.drawLine(0, 0, endX, 0, mPaint);
            }
            //绘制文本
            mPaint.setColor(mTextColor);
            canvas.drawText(text, progressPosX, -textHeight, mPaint);
            //绘制未到达的进度条
            if (!noNeedBg) {
                float startX = progressPosX + mTextOffset / 2 + textWidth;
                mPaint.setColor(mUnReachedBarColor);
                mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
                canvas.drawLine(startX, 0, mRealWidth, 0, mPaint);
            }
        }else {
            //如果到达最后，则未到达的进度条不需要绘制
            if (progressPosX >= mRealWidth) {
                progressPosX = mRealWidth;
                noNeedBg = true;
            }
            //绘制已到达的进度条
            float endX = progressPosX;
            if (endX > 0) {
                mPaint.setColor(mReachedBarColor);
                mPaint.setStrokeWidth(mReachedProgressBarHeight);
                canvas.drawLine(0, 0, endX, 0, mPaint);
            }
            //绘制未到达的进度条
            if (!noNeedBg) {
                float startX = progressPosX;
                mPaint.setColor(mUnReachedBarColor);
                mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
                canvas.drawLine(startX, 0, mRealWidth, 0, mPaint);
            }
        }
        canvas.restore();
    }
}
