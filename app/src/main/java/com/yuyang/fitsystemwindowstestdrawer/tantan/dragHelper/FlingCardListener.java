package com.yuyang.fitsystemwindowstestdrawer.tantan.dragHelper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by yuyang on 16/3/14.
 */
public class FlingCardListener implements View.OnTouchListener {
    private static final String TAG = FlingCardListener.class.getSimpleName();
    private static final int INVALID_POINTER_ID = -1;
    /** frame控件左上角坐标*/
    private final float objectX;
    private final float objectY;
    /** frame控件高*/
    private final int objectH;
    /** frame控件宽*/
    private final int objectW;
    /** frame父控件宽*/
    private final int parentWidth;
    /** frame控件宽的一半*/
    private final float halfWidth;
    /**
     * Touch事件所触发的一系列动作
     */
    private final FlingCardListener.FlingListener mFlingListener;
    /**
     * adapter的getItem方法返回的值
     */
    private final Object dataObject;
    /**
     * 倾斜角度
     */
    private float BASE_ROTATION_DEGREES;
    /**
     * frame控件移动后的左上角
     */
    private float aPosX;
    private float aPosY;
    /**
     * ACTION_DOWN的位置
     */
    private float aDownTouchX;
    private float aDownTouchY;
    /**
     * ACTION_DOWN事件相对于frame的位置（点击的是frame的上部还是下部）
     * 赋值给touchPosition 做记录
     */
    private final int TOUCH_ABOVE = 0;
    private final int TOUCH_BELOW = 1;
    private int touchPosition;
    /**
     * 记录ACTION_DOWN事件的eventPointerId
     */
    private int mActivePointerId;
    /**
     * adapter的getView方法返回的控件
     */
    private View frame;
    /**
     * 动画是否完成
     */
    private boolean isAnimationRunning;
    /**
     * 最多旋转角度
     */
    private float MAX_COS = (float) Math.cos(Math.toRadians(45.0D));

    private int animDuration = 300;
    private float scale;

    public FlingCardListener(View frame, Object itemAtPosition, FlingCardListener.FlingListener flingListener) {
        this(frame, itemAtPosition, 10.0F, flingListener);
    }

    public FlingCardListener(View frame, Object itemAtPosition, float rotation_degrees, FlingCardListener.FlingListener flingListener) {
        this.mActivePointerId = -1;
        this.frame = null;
        this.isAnimationRunning = false;
        this.frame = frame;
        this.objectX = frame.getX();
        this.objectY = frame.getY();
        this.objectH = frame.getHeight();
        this.objectW = frame.getWidth();
        this.halfWidth = (float) this.objectW / 2.0F;
        this.dataObject = itemAtPosition;
        this.parentWidth = ((ViewGroup) frame.getParent()).getWidth();
        this.BASE_ROTATION_DEGREES = rotation_degrees;
        this.mFlingListener = flingListener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int pointerIndexMove;
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                //记录事件ID
                this.mActivePointerId = event.getPointerId(0);
                float x = 0.0F;
                float y = 0.0F;
                boolean success = false;

                try {
                    //获取该事件的坐标
                    x = event.getX(this.mActivePointerId);
                    y = event.getY(this.mActivePointerId);
                    success = true;
                } catch (IllegalArgumentException var15) {
                    Log.w(TAG, "Exception in onTouch(view, event) : " + this.mActivePointerId, var15);
                }

                if(success){
                    //记录点击初始坐标
                    this.aDownTouchX = x;
                    this.aDownTouchY = y;
                    //记录frame左上角变化中的坐标
                    if(this.aPosX == 0.0F){
                        this.aPosX = this.frame.getX();
                    }
                    if(this.aPosY == 0.0F){
                        this.aPosY = this.frame.getY();
                    }
                    if(y < (float)(this.objectH / 2)){//判断点击的是frame的上半部分
                        this.touchPosition = this.TOUCH_ABOVE;
                    }else {//判断点击的是frame的下半部分
                        this.touchPosition = this.TOUCH_BELOW;
                    }
                }
                //要求父view不可以拦截接下来的事件
                view.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndexMove = event.findPointerIndex(this.mActivePointerId);
                float xMove = event.getX(pointerIndexMove);
                float yMove = event.getY(pointerIndexMove);
                float dx = xMove - this.aDownTouchX;
                float dy = yMove - this.aDownTouchY;
                this.aPosX += dx;
                this.aPosY += dy;
                float distobjectX = this.aPosX - this.objectX;//x方向上移动距离
                float rotation = this.BASE_ROTATION_DEGREES * 2.0F * distobjectX / this.parentWidth;
                if(this.touchPosition == this.TOUCH_BELOW){//如果点击的是下半部分，旋转角度为负
                    rotation = -rotation;
                }

                this.frame.setX(aPosX);
                this.frame.setY(aPosY);
                this.frame.setRotation(rotation);
                this.frame.postInvalidate();
                mFlingListener.onScroll(this.getScrollProgress(), this.getScrollProgressPercent());
                break;
            case MotionEvent.ACTION_UP:
                this.mActivePointerId = -1;
                this.resetCardViewOnStack();
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                this.mActivePointerId = -1;
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == this.mActivePointerId) {
                    pointerIndexMove = pointerIndex == 0 ? 1 : 0;
                    this.mActivePointerId = event.getPointerId(pointerIndexMove);
                }
            default:
                break;
        }
        return true;
    }

    /**
     * 返回位移比例
     * @return
     */
    private float getScrollProgress() {
        float dx = aPosX - objectX;
        float dy = aPosY - objectY;
        float dis = Math.abs(dx) + Math.abs(dy);
        return Math.min(dis, 400f) / 400f;
    }

    /**
     * 返回X轴方向的位移比例（距离判断滑出的百分比）
     * @return
     */
    private float getScrollProgressPercent() {
        if(this.movedBeyondLeftBorder()){
            return -1.0F;
        }else if(this.movedBeyondRightBorder()){
            return 1.0F;
        }else {
            float zeroToOneValue = (this.aPosX + this.halfWidth - this.leftBorder()) / (this.rightBorder() - this.leftBorder());
            return zeroToOneValue * 2.0F - 1.0F;
        }
    }

    /**
     * action动作结束，根据滑动结果判断是否移除card，重置adapter
     */
    private void resetCardViewOnStack() {
        if(this.movedBeyondLeftBorder()){
            this.onSelected(true, this.getExitPoint(-this.objectW), 100L, false);
            this.mFlingListener.onScroll(1F, -1.0F);
        }else if(this.movedBeyondRightBorder()){
            this.onSelected(false, this.getExitPoint(this.parentWidth), 100L, false);
            this.mFlingListener.onScroll(1F, 1.0F);
        }else {
            float absXMoveDistance = Math.abs(this.aPosX - this.objectX);//移动距离
            float absYMoveDistance = Math.abs(this.aPosY - this.objectY);//移动距离
            if(absXMoveDistance < 4 && absYMoveDistance < 4){//如果移动距离小于4，就认为是点击
                this.mFlingListener.onClick(this.dataObject);
            }else {
                this.frame.animate()
                        .setDuration(200)
                        .setInterpolator(new OvershootInterpolator(1.5F))
                        .x(objectX)
                        .y(objectY)
                        .rotation(0)
                        .start();
                scale = getScrollProgress();
                this.frame.postDelayed(animRun, 0);
            }
            this.aPosX = 0;
            this.aPosY = 0;
            this.aDownTouchX = 0;
            this.aDownTouchY = 0;
        }
    }

    private Runnable animRun = new Runnable() {
        @Override
        public void run() {
            mFlingListener.onScroll(scale, 0);
            if (scale > 0) {
                scale = scale - 0.1f;
                if (scale < 0)
                    scale = 0;
                frame.postDelayed(this, animDuration / 20);
            }
        }
    };

    /**
     * 右侧滑出
     * @return 是否超过右边界
     */
    private boolean movedBeyondRightBorder() {
        return this.aPosX + this.halfWidth > rightBorder();
    }

    /**
     * 左侧滑出
     * @return 是否超过左边界
     */
    private boolean movedBeyondLeftBorder() {
        return this.aPosX + this.halfWidth < leftBorder();
    }

    /**
     * 左滑边界值（当frame的中点在父控件宽度的四分之一左侧时，左滑出）
     * @return
     */
    private float leftBorder(){
        return (float)this.parentWidth / 4.0F;
    }

    /**
     * 同上
     * @return
     */
    private float rightBorder(){
        return (float)this.parentWidth * 3 / 4.0F;
    }

    /**
     * 根据frame的左上角坐标与移动后的左上角坐标的线性回归函数，计算Y轴坐标（具体数学计算不懂啊！！）
     * @param exitXPoint
     * @return
     */
    private float getExitPoint(int exitXPoint) {
        float[] x = new float[]{this.objectX, this.aPosX};
        float[] y = new float[]{this.objectY, this.aPosY};
        LinearRegression regression = new LinearRegression(x, y);
        return (float) regression.slope() * (float) exitXPoint + (float) regression.intercept();
    }

    /**
     * 选中的card的选中结果，左滑出或者右滑出
     * @param isLeft 是否是左滑
     * @param exitY 滑出至位置，Y轴坐标
     * @param duration 动画持续时间
     * @param left_right_select 是否是外部点击执行左／右滑出
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onSelected(final boolean isLeft, float exitY, long duration, final boolean left_right_select){
        this.isAnimationRunning = true;
        float exitX;//滑出的位置x轴坐标
        if(isLeft){
            exitX = (float)(-this.objectW) - this.getRotationWidthOffset();
        }else {
            exitX = (float)this.parentWidth + this.getRotationWidthOffset();
        }

        frame.animate().setDuration(duration)
                .setInterpolator(new AccelerateInterpolator())
                .x(exitX)
                .y(exitY)
                .setListener(
                        new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (isLeft) {
                                    mFlingListener.onCardExited();
                                    mFlingListener.leftExit(dataObject);
                                } else {
                                    mFlingListener.onCardExited();
                                    mFlingListener.rightExit(dataObject);
                                }
                                isAnimationRunning = false;
                            }
                        })
                .rotation(this.getExitRotation(isLeft))
        .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (left_right_select) {
                    long total = animation.getDuration();
                    long current = animation.getCurrentPlayTime();
                    mFlingListener.onScroll((float) current / total, 0);
                }
            }
        });
    }

    /**
     * 旋转最大角度45度后，粗略计算宽度增加的长度
     * @return
     */
    private float getRotationWidthOffset() {
        return (float) this.objectW / this.MAX_COS - (float) this.objectW;
    }

    /**
     * 根据是否左滑与水平方向上滑动距离计算旋转角度
     * @param isLeft
     * @return
     */
    private float getExitRotation(boolean isLeft) {
        float rotation = this.BASE_ROTATION_DEGREES * 2.0F * ((float) this.parentWidth - this.objectX) / (float) this.parentWidth;
        if (this.touchPosition == this.TOUCH_BELOW) {
            rotation = -rotation;
        }

        if (isLeft) {
            rotation = -rotation;
        }

        return rotation;
    }

    /**
     * 供外部调用，调用后直接左滑出
     */
    public void selectLeft(){
        if(!this.isAnimationRunning){
            this.onSelected(true, this.objectY, 200, true);
        }
    }

    /**
     * 供外部调用，调用后直接右滑出
     */
    public void selectRight(){
        if(!this.isAnimationRunning){
            this.onSelected(false, this.objectY, 200, true);
        }
    }

    public interface FlingListener {
        /**
         * 第一个adapter滑出
         */
        void onCardExited();

        /**
         * 左滑出动作
         * @param dataObject
         */
        void leftExit(Object dataObject);

        /**
         * 右滑出动作
         * @param dataObject
         */
        void rightExit(Object dataObject);

        /**
         * 点击动作
         * @param dataObject
         */
        void onClick(Object dataObject);

        /**
         * 滑动过程中
         */
        void onScroll(float progress, float scrollXProgress);
    }
}
