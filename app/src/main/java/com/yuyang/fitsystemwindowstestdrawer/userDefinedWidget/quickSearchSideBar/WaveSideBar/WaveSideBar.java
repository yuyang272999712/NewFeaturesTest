package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.quickSearchSideBar.WaveSideBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.Arrays;

/**
 * 波浪字母导航
 */
public class WaveSideBar extends View {
    private final static int DEFAULT_TEXT_SIZE = 14; // sp
    private final static int DEFAULT_MAX_OFFSET = 80; //dp

    /**
     * 默认的侧边栏列表
     */
    private final static String[] DEFAULT_INDEX_ITEMS = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 侧边栏列表数组
     */
    private String[] mIndexItems;

    /**
     * 侧边栏列表{@link #mIndexItems}中当前被选中项的index，如果手指抬起此值为1
     */
    private int mCurrentIndex = -1;

    /**
     * 当前触摸位置的Y坐标（相对于 {@link #mStartTouchingArea} 的顶部）
     */
    private float mCurrentY = -1;

    private Paint mPaint;
    private int mTextColor;
    private float mTextSize;

    /**
     * 每个item的高度
     */
    private float mIndexItemHeight;

    /**
     * 当前选中项的水平最大偏移量(波峰最大值)
     */
    private float mMaxOffset;

    /**
     * true－{@link OnSelectIndexItemListener#onSelectIndexItem(String)}回调会在手指抬起后才调用
     * false－回调会在手指触摸期间不断调用
     */
    private boolean mLazyRespond = false;

    /**
     * SideBar是靠左还是靠右，默认是{@link #POSITION_RIGHT}靠右
     */
    private int mSideBarPosition;
    public static final int POSITION_RIGHT = 0;
    public static final int POSITION_LEFT = 1;

    /**
     * 当触摸事件为 {@link MotionEvent#ACTION_DOWN} 并且 此触摸区域中，{@link #mStartTouching}将会被设置为true，SideBar将会开始工作
     */
    private RectF mStartTouchingArea = new RectF();

    /**
     * 可点击区域的高和宽 {@link #mStartTouchingArea}
     */
    private float mBarHeight;
    private float mBarWidth;

    /**
     * 如果是true，说明手指在触摸过程中，当时手指抬起后这个值会重置为false
     */
    private boolean mStartTouching = false;

    /**
     * 选中index的回调
     */
    private OnSelectIndexItemListener onSelectIndexItemListener;

    /**
     * 第一个字母绘制的baseline(这样以后每个字母的baseLine就可以通过 mIndexItemHeight＊index＋mFirstItemBaseLineY 来获取)
     */
    private float mFirstItemBaseLineY;

    private DisplayMetrics mDisplayMetrics;


    public WaveSideBar(Context context) {
        this(context, null);
    }

    public WaveSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDisplayMetrics = context.getResources().getDisplayMetrics();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveSideBar);
        mLazyRespond = typedArray.getBoolean(R.styleable.WaveSideBar_sidebar_lazy_respond, false);
        mTextColor = typedArray.getColor(R.styleable.WaveSideBar_sidebar_text_color, Color.GRAY);
        mMaxOffset = typedArray.getDimension(R.styleable.WaveSideBar_sidebar_max_offset, dp2px(DEFAULT_MAX_OFFSET));
        mSideBarPosition = typedArray.getInt(R.styleable.WaveSideBar_sidebar_position, POSITION_RIGHT);
        typedArray.recycle();

        mTextSize = sp2px(DEFAULT_TEXT_SIZE);

        mIndexItems = DEFAULT_INDEX_ITEMS;

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        mIndexItemHeight = fontMetrics.bottom - fontMetrics.top;

        mBarHeight = mIndexItems.length * mIndexItemHeight;
        //获取最宽的 item 的宽度
        for (String indexItem : mIndexItems) {
            mBarWidth = Math.max(mBarWidth, mPaint.measureText(indexItem));
        }

        float areaLeft = (mSideBarPosition == POSITION_LEFT) ? 0 : (width - mBarWidth - getPaddingRight());
        float areaRight = (mSideBarPosition == POSITION_LEFT) ? (getPaddingLeft() + areaLeft + mBarWidth) : width;
        float areaTop = height/2 - mBarHeight/2;
        float areaBottom = areaTop + mBarHeight;
        mStartTouchingArea.set(
                areaLeft,
                areaTop,
                areaRight,
                areaBottom);

        mFirstItemBaseLineY = (height/2 - mIndexItems.length*mIndexItemHeight/2)
                + (mIndexItemHeight/2 - (fontMetrics.descent-fontMetrics.ascent)/2)
                - fontMetrics.ascent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw each item
        for (int i = 0; i < mIndexItems.length; i++) {
            float baseLineY = mFirstItemBaseLineY + mIndexItemHeight*i;

            //获取当前位置的字母缩放大小
            float scale = getScale(i);

            int alphaScale = (i == mCurrentIndex) ? (255) : (int) (255 * (1-scale));
            mPaint.setAlpha(alphaScale);
            mPaint.setTextSize(mTextSize + mTextSize*scale);
            //因为区分左右，所以这里需要计算下左右显示情况下的其实X值
            float drawX = (mSideBarPosition == POSITION_LEFT) ?
                    (getPaddingLeft() + mBarWidth/2 + mMaxOffset*scale) :
                    (getWidth() - getPaddingRight() - mBarWidth/2 - mMaxOffset*scale);

            canvas.drawText(
                    mIndexItems[i], //item text to draw
                    drawX, //center text X
                    baseLineY, // baseLineY
                    mPaint);
        }
    }

    /**
     * 计算缩放因子（选中的字母会变大）
     *
     * @param index 字母位置 {@link #mIndexItems}
     * @return
     */
    private float getScale(int index) {
        float scale = 0;
        if (mCurrentIndex != -1) {
            float distance = Math.abs(mCurrentY - (mIndexItemHeight*index+mIndexItemHeight/2)) / mIndexItemHeight;
            scale = 1 - distance*distance/16;
            scale = Math.max(scale, 0);
        }
        return scale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIndexItems.length == 0) {
            return super.onTouchEvent(event);
        }

        float eventY = event.getY();
        float eventX = event.getX();
        mCurrentIndex = getSelectedIndex(eventY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mStartTouchingArea.contains(eventX, eventY)) {
                    mStartTouching = true;
                    if (!mLazyRespond && onSelectIndexItemListener != null) {
                        onSelectIndexItemListener.onSelectIndexItem(mIndexItems[mCurrentIndex]);
                    }
                    invalidate();
                    return true;
                } else {
                    mCurrentIndex = -1;
                    return false;
                }

            case MotionEvent.ACTION_MOVE:
                if (mStartTouching && !mLazyRespond && onSelectIndexItemListener != null) {
                    onSelectIndexItemListener.onSelectIndexItem(mIndexItems[mCurrentIndex]);
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mLazyRespond && onSelectIndexItemListener != null) {
                    onSelectIndexItemListener.onSelectIndexItem(mIndexItems[mCurrentIndex]);
                }
                mCurrentIndex = -1;
                mStartTouching = false;
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    private int getSelectedIndex(float eventY) {
        mCurrentY = eventY - (getHeight()/2 - mBarHeight /2);
        if (mCurrentY <= 0) {
            return 0;
        }

        int index = (int) (mCurrentY / this.mIndexItemHeight);
        if (index >= this.mIndexItems.length) {
            index = this.mIndexItems.length - 1;
        }
        return index;
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.mDisplayMetrics);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.mDisplayMetrics);
    }

    public void setIndexItems(String... indexItems) {
        mIndexItems = Arrays.copyOf(indexItems, indexItems.length);
        requestLayout();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    public void setPosition(int position) {
        if (position != POSITION_RIGHT && position != POSITION_LEFT) {
            throw new IllegalArgumentException("显示位置必须是POSITION_RIGHT／POSITION_LEFT");
        }

        mSideBarPosition = position;
        requestLayout();
    }

    public void setMaxOffset(int offset) {
        mMaxOffset = offset;
        invalidate();
    }

    public void setLazyRespond(boolean lazyRespond) {
        mLazyRespond = lazyRespond;
    }

    public void setOnSelectIndexItemListener(OnSelectIndexItemListener onSelectIndexItemListener) {
        this.onSelectIndexItemListener = onSelectIndexItemListener;
    }

    public interface OnSelectIndexItemListener {
        void onSelectIndexItem(String s);
    }
}
