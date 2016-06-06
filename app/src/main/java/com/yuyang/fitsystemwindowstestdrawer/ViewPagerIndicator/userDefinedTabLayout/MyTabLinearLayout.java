package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.userDefinedTabLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 类似TabLayout的ViewPager指示器
 */
public class MyTabLinearLayout extends LinearLayout {
    //绘制三角形相关方法
    /**
     * 三角形画笔
     */
    private Paint mPaint;
    /**
     * 三角形路径
     */
    private Path mPath;
    /**
     * 三角形宽度
     */
    private int mTriangleWidth;
    /**
     * 三角形高度
     */
    private int mTriangleHeight;
    /**
     * 三角形宽度与每个Tab宽度的比例
     */
    private static final float RADIO_TRIANGLE = 1.0f/6;
    /**
     * 三角形最大宽度
     */
    private final int DIMENSION_TRIANGLE_WIDTH = (int) (getScreenWidth()/3*RADIO_TRIANGLE);
    //三角形位置相关参数
    /**
     * 初始位置，三角形指示器X偏移亮
     */
    private int mInitTranslationX;
    //Tab相关参数
    /**
     * 默认的TAB显示数量
     */
    private static final int COUNT_DEFAULT_TAB = 4;
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;
    /**
     * 与Tab绑定的ViewPager
     */
    private ViewPager mViewPager;
    /**
     * 标题正常时的颜色
     */
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    /**
     * 标题选中时的颜色
     */
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xFFFFFFFF;
    /**
     * 父容器HorizontalScrollView的滚动回调
     */
    private ScrollToCallBack scrollCallBack;

    public MyTabLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));//设置Path参数，使折角变得圆滑
    }

    /**
     * 初始化三角形的宽度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w/mTabVisibleCount*RADIO_TRIANGLE);
        mTriangleWidth = Math.min(mTriangleWidth, DIMENSION_TRIANGLE_WIDTH);
        //初始化三角形
        initTriangle();
        //计算初始偏移量
        mInitTranslationX = getChildAt(0).getMeasuredWidth()/2 - mTriangleWidth/2;
    }

    /**
     * 初始化三角形指示器
     */
    private void initTriangle() {
        mPath = new Path();
        mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
        mPath.moveTo(0,0);
        mPath.lineTo(mTriangleWidth,0);//底边
        mPath.lineTo(mTriangleWidth/2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 绘制指示器
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX, getHeight()+1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 设置布局中view的一些必要属性；如果设置了setTabTitles，布局中view则无效
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count == 0){
            return;
        }
        for (int i=0; i<count; i++){
            View view = getChildAt(i);
            LayoutParams layout = (LayoutParams) view.getLayoutParams();
            layout.weight = 0;
            layout.width = getScreenWidth()/mTabVisibleCount;
            view.setLayoutParams(layout);
        }
        //设置点击事件
        setItemClickEvent();
    }

    private void setItemClickEvent() {
        int count = getChildCount();
        for (int i=0; i<count; i++){
            final int index = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(index);
                }
            });
        }
    }

    public void setViewPager(ViewPager viewPager, final int pos){
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                //设置字体颜色
                resetTextViewColor();
                highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        //设置tab
        setTabItemTitles(viewPager);
        // 高亮
        highLightTextView(pos);
    }

    /**
     * 指示器跟随手指滚动，以及容器滚动
     * @param position
     * @param offset
     */
    private void scroll(int position, float offset) {
        int translationX = 0;
        for (int i=0; i<position; i++){
            View view = getChildAt(i);
            translationX += view.getWidth();
        }
        int preWidth = 0;
        if(getChildAt(position)!=null) {
            preWidth = getChildAt(position).getWidth();
        }
        int nxtWidth = 0;
        if (getChildAt(position+1)!=null) {
            nxtWidth = getChildAt(position + 1).getWidth();
        }
        // 不断改变偏移量，invalidate
        mInitTranslationX = (int) (translationX + preWidth/2 + (preWidth+nxtWidth)/2*offset) - mTriangleWidth/2;
        //让三角永远位于屏幕中间
        int disX = mInitTranslationX - scrollCallBack.getWidth()/2 + mTriangleWidth/2;
        scrollCallBack.smoothScrollTo(disX, 0);
        invalidate();
    }

    /**
     * 高亮对应位置的文本
     * @param position
     */
    private void highLightTextView(int position) {
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
        }
    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    /**
     * 设置tab的标题内容 可选，可以自己在布局文件中写死
     *
     * @param viewPager
     */
    public void setTabItemTitles(ViewPager viewPager) {
        // 如果传入的list有值，则移除布局文件中设置的view
        if (viewPager != null && viewPager.getAdapter() != null) {
            this.removeAllViews();
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
            }
            for (int i=0; i<adapter.getCount(); i++){
                addView(generateTextView(adapter.getPageTitle(i).toString()));
            }
            // 设置item的click事件
            setItemClickEvent();
        }
    }

    /**
     * 根据标题生成我们的TextView
     *
     * @param text
     * @return
     */
    private TextView generateTextView(String text) {
        TextView tv = new TextView(getContext());
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        tv.setSingleLine(true);
        tv.setPadding(20,0,20,0);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void setCallBack(ScrollToCallBack callBack){
        this.scrollCallBack = callBack;
    }

    public interface ScrollToCallBack{
        int getWidth();
        void smoothScrollTo(int x,int y);
    }

}
