package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.ArcMenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 圆弧菜单
 *  先布局好所有View的位置，即：此时itemView已经在目标位置，只是可见设置为GONE
 *  展开时itemView利用动画效果从mMenuBtn的位置滚动之目标位置，动画结束后将itemView设为可见，反之依然
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {
    private final String TAG = "ArcMenu";

    /**
     * 菜单位置，默认在又下
     */
    private Position mPosition = Position.RIGHT_BOTTOM;
    /**
     * 菜单展开半径，默认100dp
     */
    private int mRadius = 100;
    /**
     * 菜单状态，默认关闭
     */
    private State mState = State.CLOSE;
    /**
     * 菜单点击事件回调
     */
    private OnMenuItemClickListener itemClickListener;
    /**
     * 展开
     */
    private View mMenuBtn;

    public void setItemClickListener(OnMenuItemClickListener listener){
        this.itemClickListener = listener;
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        mRadius = a.getDimensionPixelSize(R.styleable.ArcMenu_radius,
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, getResources().getDisplayMetrics()));
        int position = a.getInt(R.styleable.ArcMenu_position, 3);
        switch (position){
            case 0:
                mPosition = Position.LEFT_TOP;
                break;
            case 1:
                mPosition = Position.RIGHT_TOP;
                break;
            case 2:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case 3:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            layoutButton();
            int count = getChildCount();
            /**
             * 设置所有子View的位置
             *  第1个：mRadius(sin0a , cos0a) 注：[a = Math.PI / 2 * (cCount - 1)] a为角度
             *  第2个：mRadius(sin1a ,cos1a)
             *  第3个：mRadius(sin2a ,cos2a)
             *  第4个：mRadius(sin3a , cos3a)
             */
            for (int i=0; i<count-1; i++){
                View view = getChildAt(i+1);//第一个为menu本身
                view.setVisibility(GONE);//先设置为不可见，以便动画结束后可以显示出View

                int childLeft = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int childTop = (int) (mRadius * Math.cos(Math.PI / 2 / (count -2) * i));
                int childWidth = view.getMeasuredWidth();
                int childHeight = view.getMeasuredHeight();

                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM){
                    childTop = getMeasuredHeight() - childHeight - childTop;
                }
                if (mPosition == Position.RIGHT_BOTTOM || mPosition == Position.RIGHT_TOP){
                    childLeft = getMeasuredWidth() - childWidth - childLeft;
                }

                Log.e(TAG, childLeft + " , " + childTop);
                view.layout(childLeft, childTop, childLeft+childWidth, childTop+childHeight);
            }
        }
    }

    /**
     * 如果是打开状态的即不往下分发触摸事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mState == State.OPEN){
            toggleMenu(300);
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置menuButton的位置
     */
    private void layoutButton() {
        View cButton = getChildAt(0);
        cButton.setOnClickListener(this);

        int l = 0;
        int t = 0;
        int width = cButton.getMeasuredWidth();
        int height = cButton.getMeasuredHeight();
        switch (mPosition){
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        cButton.layout(l,t,l+width,t+height);
    }

    @Override
    public void onClick(View v) {
        mMenuBtn = findViewById(R.id.menu_button);
        if(mMenuBtn == null){
            mMenuBtn = getChildAt(0);
        }
        rotateView(mMenuBtn, 0f, 270f, 300);
        toggleMenu(300);
    }

    /**
     * 开关menu动画
     * @param durationMillis
     */
    private void toggleMenu(int durationMillis) {
        int count = getChildCount();
        for (int i=0; i<count-1; i++){
            final View childView = getChildAt(i+1);//i=0为mMenuBtn
            childView.setVisibility(VISIBLE);
            //xFlag／yFlag用于判断childView往哪个方向滚动
            int xFlag = 1;
            int yFlag = 1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM){
                xFlag = -1;
            }
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP){
                yFlag = -1;
            }

            //child left
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            //child top
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            AnimationSet animatorSet = new AnimationSet(false);//设置动画集合（位移＋旋转）
            AlphaAnimation alpha = null;
            TranslateAnimation translate = null;
            if(mState == State.CLOSE){//to open
                alpha = new AlphaAnimation(0f, 1f);
                translate = new TranslateAnimation(cl*xFlag, 0, ct*yFlag, 0);
                translate.setInterpolator(new OvershootInterpolator());
                childView.setClickable(true);
                childView.setFocusable(true);
            }else {//to close
                alpha = new AlphaAnimation(1f, 0f);
                translate = new TranslateAnimation(0, cl*xFlag, 0, ct*yFlag);
            }
            alpha.setDuration(durationMillis);
            alpha.setFillAfter(true);
            alpha.setStartOffset((i * 100) / (count - 1));// 为动画设置一个开始延迟时间，这样每个itemView就会以此执行动画
            translate.setDuration(durationMillis);
            translate.setFillAfter(true);
            translate.setStartOffset((i * 100) / (count - 1));// 为动画设置一个开始延迟时间，这样每个itemView就会以此执行动画
            translate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mState == State.CLOSE) {
                        childView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(durationMillis);
            rotate.setFillAfter(true);
            rotate.setStartOffset((i * 100) / (count - 1));// 为动画设置一个开始延迟时间，这样每个itemView就会以此执行动画
            animatorSet.addAnimation(alpha);
            animatorSet.addAnimation(rotate);
            animatorSet.addAnimation(translate);
            childView.startAnimation(animatorSet);

            final int index = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null){
                        itemClickListener.onClick(v, index);
                    }
                    menuItemAnin(index);
                    changeState();
                }
            });
        }
        changeState();
    }

    /**
     * 点击item放大消失，其他的item缩小消失
     * @param index
     */
    private void menuItemAnin(int index) {
        for (int i=0; i<getChildCount()-1; i++){
            View childView = getChildAt(i+1);
            if (i == index){//被点击的item放大消失
                childView.startAnimation(scaleBigAnim(300));
            }else {//其他的item缩小消失
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    /**
     * 缩小消失
     * @param durationMillis
     * @return
     */
    private Animation scaleSmallAnim(int durationMillis) {
        ScaleAnimation scaleSmall = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleSmall.setDuration(durationMillis);
        scaleSmall.setFillAfter(true);
        return scaleSmall;
    }

    /**
     * 放大消失
     * @param durationMillis
     * @return
     */
    private Animation scaleBigAnim(int durationMillis) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleBig = new ScaleAnimation(1f, 4f, 1f, 4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alpha = new AlphaAnimation(1f, 0f);
        animationSet.setDuration(durationMillis);
        animationSet.addAnimation(scaleBig);
        animationSet.addAnimation(alpha);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 旋转动画
     * @param view
     * @param fromDegrees
     * @param toDegrees
     * @param durationMillis
     */
    private void rotateView(View view, float fromDegrees, float toDegrees, int durationMillis) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillAfter(true);
        view.startAnimation(rotate);
    }

    /**
     * menu状态设置
     */
    private void changeState() {
        if (mState == State.OPEN){
            mState = State.CLOSE;
        }else {
            mState = State.OPEN;
        }
    }

    /**
     * 菜单位置
     */
    public enum Position{
        LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM
    }

    /**
     * 菜单状态
     */
    public enum State{
        OPEN, CLOSE
    }

    /**
     * 菜单点击事件
     */
    public interface OnMenuItemClickListener{
        void onClick(View view, int pos);
    }
}
