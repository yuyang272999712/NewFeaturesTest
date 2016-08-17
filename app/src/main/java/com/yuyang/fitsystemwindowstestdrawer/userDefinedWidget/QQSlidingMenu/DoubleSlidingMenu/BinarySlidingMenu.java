package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.DoubleSlidingMenu;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.ScreenUtils;

/**
 * 双侧侧滑
 */
public class BinarySlidingMenu extends HorizontalScrollView {
    private int mScreenWidth;//屏幕宽度
    private int mMenuRightPadding = 50;//menu距屏幕右边距离
    private int mMenuWidth;//menu宽度
    private int mHalfMenuWidth;//menu宽度一半
    private boolean once;//是否是第一次加载
    private boolean isLeftMenuOpen = false;
    private boolean isRightMenuOpen = false;
    private boolean isOperateLeft;
    private boolean isOperateRight;
    private ViewGroup leftMenu;
    private ViewGroup rightMenu;
    private ViewGroup content;

    public BinarySlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu, 0, 0);
        mMenuRightPadding = array.getDimensionPixelSize(R.styleable.SlidingMenu_rightPadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, context.getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once){
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            leftMenu = (ViewGroup) wrapper.getChildAt(0);
            content = (ViewGroup) wrapper.getChildAt(1);
            rightMenu = (ViewGroup) wrapper.getChildAt(2);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth/2;
            leftMenu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;
            rightMenu.getLayoutParams().width = mMenuWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            scrollTo(mMenuWidth, 0);
            once = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
        if(ev.getAction() == MotionEvent.ACTION_UP){
            int scrollX = getScrollX();
            // 如果是操作左侧菜单
            if (isOperateLeft){
                if (scrollX > mHalfMenuWidth){
                    smoothScrollTo(mMenuWidth, 0);
                    isLeftMenuOpen = false;
                }else {
                    smoothScrollTo(0, 0);
                    isLeftMenuOpen = true;
                }
            }
            if (isOperateRight){
                // 打开右侧侧滑菜单
                if (scrollX > mHalfMenuWidth + mMenuWidth) {
                    this.smoothScrollTo(mMenuWidth + mMenuWidth, 0);
                    isRightMenuOpen = true;
                } else {
                    // 关闭右侧侧滑菜单
                    this.smoothScrollTo(mMenuWidth, 0);
                    isRightMenuOpen = false;
                }
            }

            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (l > mMenuWidth) {
            isOperateRight = true;
            isOperateLeft = false;
        } else {
            isOperateRight = false;
            isOperateLeft = true;
        }

        /**
         * 固定content，实现抽屉效果
         * 1、scale，在滑动左侧菜单时：值为1.0~0.0；mMenuWidth * scale 的值就是从 0.0~ -mMenuWidth（注意：负的） ； 那么mContent的向左偏移量，就是0到mMenuWidth ；也就是说，整个滑动的过程，我们强制让内容区域固定了。
         * 2、scale，在滑动右侧菜单时：值为：1.0~2.0；mMenuWidth * scale 的值就是从 0.0~ mMenuWidth（注意：正数） ；那么mContent的向右偏移量，就是0到mMenuWidth ；也就是说，整个滑动的过程，我们强制让内容区域固定了。
         */
        float scale = l * 1.0f / mMenuWidth;
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("x", mMenuWidth * scale);
        ObjectAnimator.ofPropertyValuesHolder(content, translationX).setDuration(0).start();

    }

}
