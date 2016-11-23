package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.CircleMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 圆形菜单
 */
public class CircleMenuLayout extends ViewGroup {
    /**
     * itemView的适配器
     */
    private ListAdapter mAdapter;
    /**
     * ViewGroup的宽度（直径）
     */
    private int mRadius;
    /**
     * 该容器内child item的默认尺寸
     */
    private float RADIO_DEFAULT_CHILD_DIMENSION = 1/4f;
    /**
     * 菜单的中心child的默认尺寸
     */
    private float RADIO_DEFAULT_CNETER_ITME_DIMENSION = 1/3f;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private float RADIO_PADDING_LAYOUT = 1/12f;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private float mPadding;
    /**
     * 布局时的开始角度
     */
    private double mStartAngle = 0;
    /**
     * 菜单项文本
     */
    private String[] mItemTexts;
    /**
     * 菜单项图标
     */
    private int[] mItemImgs;
    /**
     * 菜单数量
     */
    private int mMenuItemCount;
    /**
     * 设置item的点击监听
     */
    private OnMenuItemClickListener onMenuItemClickListener;
    /**
     * ＊＊＊＊＊＊＊＊＊＊＊＊＊滚动相关＊＊＊＊＊＊＊＊＊＊＊＊＊
     */
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private final int FLINGABLE_VALUE = 300;
    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private final int NOCLICK_VALUE = 3;
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;
    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;
    /**
     * 判断菜单是否在自动滚动
     */
    private boolean isFling;
    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;
    /**
     * 自动滚动的Runnable
     */
    private AutoFlingRunnable mFlingRunnable;

    public CircleMenuLayout(Context context) {
        this(context, null);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //无视padding
        setPadding(0,0,0,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;
        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY){
            //设置为背景图的宽度
            resWidth = getSuggestedMinimumWidth();
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resWidth = resWidth == 0 ? getDefaultWidth():resWidth;

            resHeight = getSuggestedMinimumHeight();
            resHeight = resHeight == 0 ? getDefaultWidth():resHeight;
        }else {
            resWidth = resHeight = Math.min(width, height);
        }
        setMeasuredDimension(resWidth, resHeight);

        //获取直径
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
        //menu item数量
        int count = getChildCount();
        //menu item尺寸
        int childSize = (int) (mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
        //menu item的测量模式
        int childMode = MeasureSpec.EXACTLY;
        //测量每个子View
        for (int i=0; i<count; i++){
            View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }
            //通过计算menu item的尺寸以及和设置好的模式，去对item进行测量
            int makeMeasureSpec = -1;
            if (child.getId() == R.id.id_circle_menu_item_center){
                makeMeasureSpec = MeasureSpec.makeMeasureSpec((int) (mRadius*RADIO_DEFAULT_CNETER_ITME_DIMENSION),
                        childMode);
            }else {
                makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
            }
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }
        mPadding = mRadius*RADIO_PADDING_LAYOUT;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float layoutRadius = mRadius;
        int childCount = getChildCount();
        int left,top;
        //menuItem的尺寸
        int childWidth = (int) (layoutRadius*RADIO_DEFAULT_CHILD_DIMENSION);
        if (mMenuItemCount != 0) {
            //根据menuItem个数计算每个item所占的角度
            float angleDelay = 360 / mMenuItemCount;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getId() == R.id.id_circle_menu_item_center) {
                    continue;
                }
                if (child.getVisibility() == GONE) {
                    continue;
                }
                mStartAngle %= 360;
                //计算布局中心点到menuItem中心点的距离
                float tmp = layoutRadius / 2f - childWidth / 2f - mPadding;
                //menuItem的横坐标
                left = (int) (layoutRadius / 2 + Math.round(tmp * Math.cos(Math.toRadians(mStartAngle)) - 1 / 2f * childWidth));
                //menuItem的纵坐标
                top = (int) (layoutRadius / 2 + Math.round(tmp * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f * childWidth));
                child.layout(left, top, left + childWidth, top + childWidth);
                //叠加角度
                mStartAngle += angleDelay;
            }
        }

        //找到中心View
        View centerView = findViewById(R.id.id_circle_menu_item_center);
        if (centerView != null){
            centerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null){
                        onMenuItemClickListener.itemCenterClick(v);
                    }
                }
            });
            // 设置center item位置
            left = (int) (layoutRadius/2 - centerView.getMeasuredWidth()/2);
            top = (int) (layoutRadius/2 - centerView.getMeasuredHeight()/2);
            centerView.layout(left, top, left+centerView.getMeasuredWidth(), top+centerView.getMeasuredHeight());
        }
    }

    /**
     * 重写dispatchTouchEvent事件，在其中编写跟随手指移动的代码~~
     *  什么不在onTouchEvent里面写，因为如果我在onTouchEvent里面写，用户触摸item时，我们的菜单无法移动，
     *  因为item是可点击，会作为我们的targetView，然后消耗掉我们的MOVE事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;
                //如果正在转动，停止转动，消费掉这个事件
                if (isFling){
                    // 移除快速滚动的回调
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 获取开始时的角度（DOWN事件时手指触摸点的角度）
                 */
                float start = getAngle(mLastX,mLastY);
                /**
                 * 获取当前的角度
                 */
                float end = getAngle(x, y);
                //如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x,y)==1 || getQuadrant(x,y)==4){
                    mStartAngle += end - start;//改变该值以后重新布局，画面就转动起来了
                    mTmpAngle += end - start;
                }else {
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }
                //重新布局
                requestLayout();

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                //计算每秒移动的角度
                float anglePreSecond = mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime);
                //如果达到该值认为是快速移动
                if (Math.abs(anglePreSecond) > mFlingableValue && !isFling){
                    //post一个任务去自动转动
                    post(mFlingRunnable = new AutoFlingRunnable(anglePreSecond));
                    return true;//消费掉此事件
                }
                //如果当前旋转角度超过 NOCLICK_VALUE，屏蔽掉点击事件
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE){
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据当前位置计算象限
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {//x大于0，说明只可能在1、4象限
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }
    }

    /**
     * 根据触摸的位置计算角度
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    //ZHU yuyang setAdapter()与addMenuItemViews()方法可以替换掉setMenuItemIconsAndTexts()与addMenuItems()方法
    /**
     * 设置要现实的item
     * @param adapter
     */
    public void setAdapter(ListAdapter adapter){
        if (adapter != null) {
            this.mAdapter = adapter;
        }else {
            throw new IllegalArgumentException("Adapter不能设置为null");
        }
        mMenuItemCount = mAdapter.getCount();
        addMenuItemViews();
    }

    /**
     * 添加菜单项
     */
    private void addMenuItemViews(){
        /**
         * 根据用户设置的参数初始化View
         */
        for (int i=0; i<mMenuItemCount; i++){
            final int index = i;
            View view = mAdapter.getView(index, null, this);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null){
                        onMenuItemClickListener.itemClick(v, index);
                    }
                }
            });
            //添加View
            addView(view);
        }
    }

    /**
     * 设置menu item的图标和文字
     * @param resIds
     * @param texts
     */
    public void setMenuItemIconsAndTexts(int[] resIds, String[] texts){
        this.mItemImgs = resIds;
        this.mItemTexts = texts;
        //检测参数
        if ((mItemImgs == null || mItemImgs.length == 0)
                && (mItemTexts == null || mItemTexts.length == 0)){
            throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
        }
        //初始化mMenuItemCount
        mMenuItemCount = resIds==null?texts.length:resIds.length;
        if (resIds != null && texts != null){
            mMenuItemCount = Math.min(texts.length,resIds.length);
        }
        addMenuItems();
    }

    /**
     * 添加菜单项
     */
    private void addMenuItems() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        /**
         * 根据用户设置的参数初始化View
         */
        for (int i=0; i<mMenuItemCount; i++){
            final int index = i;
            View view = inflater.inflate(R.layout.custom_layout_circle_menu_item, this, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.id_circle_menu_item_image);
            TextView textView = (TextView) view.findViewById(R.id.id_circle_menu_item_text);
            if (imageView != null){
                imageView.setVisibility(VISIBLE);
                imageView.setImageResource(mItemImgs[i]);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMenuItemClickListener != null){
                            onMenuItemClickListener.itemClick(v, index);
                        }
                    }
                });
            }
            if (textView != null){
                textView.setVisibility(VISIBLE);
                textView.setText(mItemTexts[i]);
            }
            //添加View
            addView(view);
        }
    }

    private int getDefaultWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * MenuItem的点击事件接口
     *
     * @author zhy
     *
     */
    public interface OnMenuItemClickListener {
        void itemClick(View view, int pos);
        void itemCenterClick(View view);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    /**
     * 手机放开后自动滚动的任务
     */
    private class AutoFlingRunnable implements Runnable {
        //角速度
        private float angelPerSecond;

        public AutoFlingRunnable(float anglePreSecond) {
            this.angelPerSecond = anglePreSecond;
        }

        /**
         * 传入每秒移动的角度这个值，然后根据这个值去增加mStartAngle，然后requestLayout();
         * 就是自动滚动了~~当然了需要越滚越慢和停止，所以需要逐渐减小这个值angelPerSecond /= 1.0666F;
         * 以及最后移动很慢的时候，就停下来
         */
        @Override
        public void run() {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                return;
            }
            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 30);
            // 逐渐减小这个值
            angelPerSecond /= 1.0666F;
            postDelayed(this, 30);
            // 重新布局
            requestLayout();
        }
    }
}
