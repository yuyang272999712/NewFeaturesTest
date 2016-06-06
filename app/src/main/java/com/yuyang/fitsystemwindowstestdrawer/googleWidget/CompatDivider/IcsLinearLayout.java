package com.yuyang.fitsystemwindowstestdrawer.googleWidget.CompatDivider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * divider、showDividers 3.0以下的LinearLayout肯定是无法识别的
 * 此类就是为了解决3.0以下版本的兼容问题
 */
public class IcsLinearLayout extends LinearLayout {
    private static final int[] LL = new int[]{
                    android.R.attr.divider,
                    android.R.attr.showDividers,
                    android.R.attr.dividerPadding};

    private static final int LL_DIVIDER = 0;
    private static final int LL_SHOW_DIVIDER = 1;
    private static final int LL_DIVIDER_PADDING = 2;

    /**
     * android:dividers
     */
    private Drawable mDivider;
    /**
     * 对应：android:showDividers
     */
    private int mShowDividers;
    /**
     * 对应：android:dividerPadding
     */
    private int mDividerPadding;

    private int mDividerWidth;
    private int mDividerHeight;

    public IcsLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, LL);
        setDividerDrawable(a.getDrawable(LL_DIVIDER));
        mDividerPadding = a.getDimensionPixelSize(LL_DIVIDER_PADDING, 0);
        mShowDividers = a.getInteger(LL_SHOW_DIVIDER, SHOW_DIVIDER_NONE);
        a.recycle();
    }

    /**
     * 设置分隔元素，初始化宽高等
     */
    public void setDividerDrawable(Drawable divider) {
        if (divider == mDivider) {
            return;
        }
        mDivider = divider;
        if (divider != null) {
            mDividerWidth = divider.getIntrinsicWidth();
            mDividerHeight = divider.getIntrinsicHeight();
        } else {
            mDividerWidth = 0;
            mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //将分隔元素的宽高转化为对应的margin
        setChildrenDivider();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setChildrenDivider() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //遍历每个子View
            View child = getChildAt(i);
            //拿到索引
            final int index = indexOfChild(child);
            //方向
            final int orientation = getOrientation();

            final LayoutParams params = (LayoutParams) child.getLayoutParams();
            //判断是否需要在子View左边绘制分隔
            if (hasDividerBeforeChildAt(index)) {
                if (orientation == VERTICAL) {
                    //如果需要，则设置topMargin为分隔元素的高度（垂直时）
                    params.topMargin = mDividerHeight;
                } else {
                    //如果需要，则设置leftMargin为分隔元素的宽度（水平时）
                    params.leftMargin = mDividerWidth;
                }
            }
        }
    }

    /**
     * 判断是否需要在子View左边绘制分隔
     */
    private boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0 || childIndex == getChildCount()) {
            return false;
        }
        if ((mShowDividers & SHOW_DIVIDER_MIDDLE) != 0) {
            boolean hasVisibleViewBefore = false;
            for (int i = childIndex - 1; i >= 0; i--) {
                //当前index的前一个元素不为GONE则认为需要
                if (getChildAt(i).getVisibility() != GONE) {
                    hasVisibleViewBefore = true;
                    break;
                }
            }
            return hasVisibleViewBefore;
        }
        return false;
    }

    /**
     * 最后会在这个空的区域进行绘制divider，别忘了，我们的divider是个drawable。
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (mDivider != null) {
            if (getOrientation() == VERTICAL) {
                //绘制垂直方向的divider
                drawDividersVertical(canvas);
            } else {
                //绘制水平方向的divider
                drawDividersHorizontal(canvas);
            }
        }
        super.onDraw(canvas);
    }

    /**
     * 绘制水平方向的divider
     * @param canvas
     */
    private void drawDividersHorizontal(Canvas canvas) {
        final int count = getChildCount();
        //遍历所有的子View
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                //如果需要绘制divider
                if (hasDividerBeforeChildAt(i)) {
                    final android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) child
                            .getLayoutParams();
                    //得到开始的位置，getLeft为当前View的左侧，而左侧有margin，所以之差为divider绘制的开始区域
                    final int left = child.getLeft() - lp.leftMargin;
                    //绘制divider
                    drawVerticalDivider(canvas, left);
                }
            }
        }
    }

    /**
     * 绘制divider，根据left，水平方向绘制
     * @param canvas
     * @param left
     */
    public void drawVerticalDivider(Canvas canvas, int left) {
        //设置divider的范围
        mDivider.setBounds(left, getPaddingTop() + mDividerPadding, left
                + mDividerWidth, getHeight() - getPaddingBottom()
                - mDividerPadding);
        //绘制
        mDivider.draw(canvas);
    }

    private void drawDividersVertical(Canvas canvas) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) child
                            .getLayoutParams();
                    final int top = child.getTop() - lp.topMargin;
                    drawHorizontalDivider(canvas, top);
                }
            }
        }
    }
    private void drawHorizontalDivider(Canvas canvas, int top) {
        mDivider.setBounds(getPaddingLeft() + mDividerPadding, top, getWidth()
                - getPaddingRight() - mDividerPadding, top + mDividerHeight);
        mDivider.draw(canvas);
    }
}
