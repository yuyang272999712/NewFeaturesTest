package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SimpleSlidingMenu;

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
 * 仿QQ的侧滑导航（早期版本未出现DrawerLayout布局）
 */
public class SlidingMenu extends HorizontalScrollView {
    private int mScreenWidth;//屏幕宽度
    private int mMenuRightPadding = 50;//menu距屏幕右边距离
    private int mMenuWidth;//menu宽度
    private int mHalfMenuWidth;//menu宽度一半
    private boolean once;//是否是第一次加载
    private boolean isOpen = false;
    private ViewGroup menu;
    private ViewGroup content;

    public SlidingMenu(Context context, AttributeSet attrs) {
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
            menu = (ViewGroup) wrapper.getChildAt(0);
            content = (ViewGroup) wrapper.getChildAt(1);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth/2;
            menu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;
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
            if (scrollX > mHalfMenuWidth){
                smoothScrollTo(mMenuWidth, 0);
                isOpen = false;
            }else {
                smoothScrollTo(0, 0);
                isOpen = true;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        float scale = l * 1.0f / mMenuWidth;
        float leftScale = 1.0f - 0.3f*scale;
        float rightScale = 0.8f + 0.2f*scale;

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", leftScale);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", leftScale);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.6f + 0.4f * (1 - scale));
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("x", mMenuWidth * scale * 0.6f);
        ObjectAnimator.ofPropertyValuesHolder(menu, scaleX, scaleY, alpha, translationX).setDuration(0).start();

        content.setPivotX(0);
        content.setPivotY(content.getHeight() / 2);
        PropertyValuesHolder scaleX2 = PropertyValuesHolder.ofFloat("scaleX", rightScale);
        PropertyValuesHolder scaleY2 = PropertyValuesHolder.ofFloat("scaleY", rightScale);
        ObjectAnimator.ofPropertyValuesHolder(content, scaleX2, scaleY2).setDuration(0).start();
    }

    public void openMenu(){
        if (!isOpen){
            smoothScrollTo(0, 0);
            isOpen = true;
        }
    }

    public void closeMenu(){
        if (isOpen){
            smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    /**
     * 开关侧边栏导航
     */
    public void toggle(){
        if (isOpen){
            closeMenu();
        }else {
            openMenu();
        }
    }
}
