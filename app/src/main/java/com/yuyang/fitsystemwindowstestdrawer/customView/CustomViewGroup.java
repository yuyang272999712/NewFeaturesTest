package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 一、ViewGroup会为childView指定测量模式，下面简单介绍下三种测量模式：
 *  EXACTLY：表示设置了精确的值，一般当childView设置其宽、高为精确值、match_parent时，ViewGroup会将其设置为EXACTLY；
 *  AT_MOST：表示子布局被限制在一个最大值内，一般当childView设置其宽、高为wrap_content时，ViewGroup会将其设置为AT_MOST；
 *  UNSPECIFIED：表示子布局想要多大就多大，一般出现在AadapterView的item的heightMode中、ScrollView的childView的heightMode中；此种模式比较少见。
 * 注：上面的每一行都有一个一般，意思上述不是绝对的，对于childView的mode的设置还会和ViewGroup的测量mode有一定的关系；当然了，这是第一篇自定义ViewGroup，而且绝大部分情况都是上面的规则
 */
//硬性规定该viewGroup只包含4个childView
public class CustomViewGroup extends ViewGroup {

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获取次viewGroup上级容器为其推荐的宽高和测量模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //计算所有childView的高度
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        /**
         * 以下计算都在measureMode为wrap_content的模式下有用 begin
         */
        //计算childView测量完成后所占的宽高
        int width = 0;
        int height = 0;

        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        //计算childView左右各占的高度，取其中最大值
        int lHeight = 0;
        int rHeight = 0;
        //计算childView上下各占的宽度，取其中最大值
        int tWidth = 0;
        int bWidth = 0;

        for (int i=0;i<cCount;i++){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            if(i == 0 || i == 1){
                tWidth = tWidth + cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if(i == 2 || i == 3){
                bWidth = bWidth + cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if(i == 0 || i == 2){
                lHeight = lHeight + cHeight + cParams.topMargin + cParams.bottomMargin;
            }
            if(i == 1 || i == 3){
                rHeight = rHeight + cHeight + cParams.topMargin + cParams.bottomMargin;
            }
        }

        width = Math.max(tWidth,bWidth);
        height = Math.max(lHeight,rHeight);

        /**
         * 该方法必需调用，否则在布局时会抛出异常
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }
    /**end*/

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        for(int i=0;i<cCount;i++){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl=0,ct=0,cr=0,cb=0;

            switch (i){
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
            }
            cr = cl + cWidth;
            cb = cHeight + ct;
            childView.layout(cl, ct, cr, cb);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
