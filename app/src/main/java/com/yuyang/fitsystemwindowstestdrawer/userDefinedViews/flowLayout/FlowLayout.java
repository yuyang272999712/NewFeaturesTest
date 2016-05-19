package com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.flowLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 水平流式布局
 * (Google最新发布了FlexboxLayout控件与FlowLayout类似,
 *  compile 'com.google.android:flexbox:0.1.2' )
 */
public class FlowLayout extends ViewGroup {
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    //以行为单位记录所有的childView
    private List<List<View>> mAllViews = new ArrayList<>();
    //记录一行所有的childView
    private List<View> lineViews = new ArrayList<>();
    //记录每行的行高
    private List<Integer> mLineHeight = new ArrayList<>();
    //记录每行的行宽
    private List<Integer> mLineWidth = new ArrayList<Integer>();
    //标记布局排版
    private int mGravity = LEFT;

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        mGravity = ta.getInt(R.styleable.TagFlowLayout_gravity, LEFT);
        ta.recycle();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams()
    {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p)
    {
        return new MarginLayoutParams(p);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        //如果是warp_content的情况，用于记录该ViewGroup的宽高
        int width = 0;
        int height = 0;

        int lineWidth = 0;//不断更新，根据每个childView的宽度累加，大于widthSize后换行。取最宽等于width
        int lineHeight = 0;//不断更新，根据每行最高的childView高度，取最高。最后每行的高度都累加到height上

        int cCount = getChildCount();

        for (int i = 0; i<cCount; i++){
            View childView = getChildAt(i);
            if(childView.getVisibility() == GONE){
                //如果是最后一个
                if(i == cCount - 1){
                    width = Math.max(width, lineWidth);
                    height += lineHeight;
                }
                continue;
            }
            //测量该childView宽高
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            //获取childView的layout
            MarginLayoutParams childLP = (MarginLayoutParams) childView.getLayoutParams();
            //childView实际占据的宽度和高度
            int childWidth = childView.getMeasuredWidth() + childLP.leftMargin + childLP.rightMargin;
            int childHeight = childView.getMeasuredHeight() + childLP.topMargin + childLP.bottomMargin;

            /**
             * 如果加上该childView后宽度超过parentWidth后，
             * 另起一行,取目前最长的行宽，累加现在的lineHeight
             */
            if(lineWidth + childWidth > parentWidth - getPaddingLeft() - getPaddingRight()){
                width = Math.max(lineWidth, childWidth);//取最大的
                lineWidth = childWidth;//另起一行
                height += lineHeight;//累计高度
                lineHeight = childHeight;//开启下一行的行高
            }else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }
            /**
             * 如果是最后一个childView
             */
            if(i == cCount-1){
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                (widthMode == MeasureSpec.EXACTLY) ? parentWidth : width+getPaddingLeft()+getPaddingRight(),
                (heightMode == MeasureSpec.EXACTLY) ? parentHeight : height+getPaddingTop()+getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        mLineWidth.clear();
        lineViews.clear();

        int parentWidth  = getWidth();

        int cCount = getChildCount();
        int lineHeight = 0;
        int lineWidth = 0;

        for(int i=0; i<cCount; i++){
            View childView = getChildAt(i);
            if(childView.getVisibility() == GONE){
                continue;
            }
            MarginLayoutParams childLP = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            //如果需要换行了
            if(childWidth + childLP.leftMargin + childLP.rightMargin + lineWidth > parentWidth - getPaddingLeft() - getPaddingRight()){
                mAllViews.add(lineViews);//添加这行所有的childViews
                mLineHeight.add(lineHeight);
                mLineWidth.add(lineWidth);

                lineViews = new ArrayList<>();
                lineHeight = childHeight + childLP.topMargin + childLP.bottomMargin;
                lineWidth = 0;
            }
            //如果不需要换行
            lineWidth += childWidth + childLP.leftMargin + childLP.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + childLP.topMargin + childLP.bottomMargin);
            lineViews.add(childView);
        }
        //记录最后一行
        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);
        mAllViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineCount = mAllViews.size();//行数
        for (int i=0; i<lineCount; i++){
            lineViews = mAllViews.get(i);//获取当前行的所有view
            lineHeight = mLineHeight.get(i);//获取当前行的行高
            //根据gravity设置对其
            int currentLineWidth = mLineWidth.get(i);
            switch (mGravity){
                case LEFT:
                    left = getPaddingLeft();
                    break;
                case CENTER:
                    left = (parentWidth - currentLineWidth)/2;
                    break;
                case RIGHT:
                    left = parentWidth - currentLineWidth - getPaddingRight();
                    break;
            }
            //layout当前行的view
            for (int j=0;j<lineViews.size();j++){
                View childView = lineViews.get(j);
                if(childView.getVisibility() == GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

                int cl = left + lp.leftMargin;
                int ct = top + lp.topMargin;
                int cr = cl + childView.getMeasuredWidth();
                int cb = ct + childView.getMeasuredHeight();

                childView.layout(cl,ct,cr,cb);

                left += lp.leftMargin + lp.rightMargin + childView.getMeasuredWidth();
            }
            //下一行
            top += lineHeight;
            left = 0;
        }
    }
}
