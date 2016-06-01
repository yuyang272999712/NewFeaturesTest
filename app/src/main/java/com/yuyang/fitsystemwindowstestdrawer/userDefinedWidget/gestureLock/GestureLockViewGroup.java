package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.gestureLock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 整体包含n*n个GestureLockView,每个GestureLockView间间隔mMarginBetweenLockView，
 * 最外层的GestureLockView与容器存在mMarginBetweenLockView的外边距
 * 注：mMarginBetweenLockView = mGestureLockViewWidth * 0.25 可以自己调节但会影响GestureLockView的边长的计算
 *
 * 关于GestureLockView的边长（n*n）：
 *  n * mGestureLockViewWidth + ( n + 1 ) * mMarginBetweenLockView = mWidth ;
 * 得：mGestureLockViewWidth = 4 * mWidth / ( 5 * mCount + 1 )
 */
public class GestureLockViewGroup extends RelativeLayout {
    private static final String TAG = "GestureLockViewGroup";

    public enum Mode{
        STATUS_CHECK,//验证手势密码
        STATUS_SET //设置手势密码
    }
    private Mode status = Mode.STATUS_SET;
    /**
     * 保存所有的GestureLockView
     */
    private GestureLockView[] mGestureLockViews;
    /**
     * 保存用户选中的GestureLockView的id
     */
    private List<Integer> mChoose = new ArrayList<>();
    /**
     * 存储正确答案
     * TODO yuyang 选中的数字从1开始，因为这里记录的是ID，而ID不能为0
     */
    private List<Integer> mAnswer;
    /**
     * 每个边上的GestureLockView的个数
     */
    private int mCount = 4;
    /**
     * 最大尝试次数
     */
    private int mTryTimes = 4;
    /**
     * 最小链接点数
     */
    private int mMinPoint = 4;
    /**
     * GestureLockView无手指触摸的状态下内圆的颜色
     */
    private int mNoFingerInnerCircleColor = 0xFF939090;
    /**
     * GestureLockView无手指触摸的状态下外圆的颜色
     */
    private int mNoFingerOuterCircleColor = 0xFFE0DBDB;
    /**
     * GestureLockView手指触摸的状态下内圆和外圆的颜色
     */
    private int mFingerOnColor = 0xFF378FC9;
    /**
     * GestureLockView手指抬起的状态下内圆和外圆的颜色(验证错误)
     */
    private int mFingerUpErrorColor = 0xFFFF0000;
    /**
     * GestureLockView手指抬起的状态下内圆和外圆的颜色(验证正确)
     */
    private int mFingerUpRightColor = 0xFF00FF00;
    /**
     * GestureLockView变长 ＝ 4 * mWidth / ( 5 * mCount + 1 )
     */
    private int mGestureLockViewWidth;
    /**
     * 每个GestureLockView中间的间距 设置为：mGestureLockViewWidth * 25%
     */
    private int mMarginBetweenLockView = 30;
    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;
    /**
     * 指引线的开始位置x
     */
    private int mLastPathX;
    /**
     * 指引线的开始位置y
     */
    private int mLastPathY;
    /**
     * 指引线的结束位置
     */
    private Point mTmpTarget = new Point();

    private Paint mPaint;
    /**
     * 指引线
     */
    private Path mPath;
    /**
     * 回调接口
     */
    private OnGestureLockViewListener mOnGestureLockViewListener;

    public GestureLockViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockViewGroup, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i=0; i<n; i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.GestureLockViewGroup_count:
                    mCount = a.getInt(attr, 4);
                    break;
                case R.styleable.GestureLockViewGroup_tryTimes:
                    mTryTimes = a.getInt(attr, 5);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_inner_circle:
                    mNoFingerInnerCircleColor = a.getColor(attr, mNoFingerInnerCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_color_no_finger_outer_circle:
                    mNoFingerOuterCircleColor = a.getColor(attr, mNoFingerOuterCircleColor);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_on:
                    mFingerOnColor = a.getColor(attr, mFingerOnColor);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up_right:
                    mFingerUpRightColor = a.getColor(attr, mFingerUpRightColor);
                    break;
                case R.styleable.GestureLockViewGroup_color_finger_up_error:
                    mFingerUpErrorColor = a.getColor(attr, mFingerUpErrorColor);
                    break;
                case R.styleable.GestureLockViewGroup_minPoint:
                    mMinPoint = a.getInt(attr, 4);
                    break;
            }
        }
        a.recycle();

        /**
         * 初始化画笔
         */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(20);
//        mPaint.setColor(Color.parseColor("#aaffffff"));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(mWidth, mHeight);
        setMeasuredDimension(mWidth, mHeight);

        // 初始化mGestureLockViews
        if(mGestureLockViews == null){
            mGestureLockViews = new GestureLockView[mCount * mCount];
            //计算每个GestureLockView宽度
            mGestureLockViewWidth = (int) (4 * mWidth * 1.0f / (5 * mCount + 1));
            //计算每个GestureLockView间距
            mMarginBetweenLockView = (int) (mGestureLockViewWidth * 0.25);
            // 设置画笔的宽度为GestureLockView的内圆直径稍微小点（可以随意设置）
            mPaint.setStrokeWidth(mGestureLockViewWidth * 0.25f);

            for (int i=0; i<mGestureLockViews.length; i++){
                //初始化每个GestureLockView
                mGestureLockViews[i] = new GestureLockView(getContext(), mNoFingerInnerCircleColor, mNoFingerOuterCircleColor,
                        mFingerOnColor, mFingerUpErrorColor, mFingerUpRightColor);
                //TODO yuyang ID不能为0
                mGestureLockViews[i].setId(i+1);
                //设置参数，用于定位GestureLockView间的位置
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mGestureLockViewWidth, mGestureLockViewWidth);
                //不是每行的第一个，则设置位置为前一个的右边
                if (i%mCount != 0){
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, mGestureLockViews[i-1].getId());
                }
                //从第二行开始，设置为上一行同一位置View的下面
                if (i > mCount-1){
                    layoutParams.addRule(RelativeLayout.BELOW, mGestureLockViews[i-mCount].getId());
                }
                //设置右下左上的边距
                int rightMargin = mMarginBetweenLockView;
                int bottomMargin = mMarginBetweenLockView;
                int leftMargin = 0;
                int topMargin = 0;
                /**
                 * 每个View都有右外边距和底外边距 第一行的有上外边距 第一列的有左外边距
                 */
                //第一行有上边距
                if (i>=0 && i<mCount){
                    topMargin = mMarginBetweenLockView;
                }
                //第一列有左边距
                if (i%mCount == 0){
                    leftMargin = mMarginBetweenLockView;
                }
                layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                //设置状态
                mGestureLockViews[i].setMode(GestureLockView.Mode.STATUS_NO_FINGER);
                addView(mGestureLockViews[i], layoutParams);
            }
            Log.e(TAG, "mWidth = " + mWidth + " ,  mGestureViewWidth = "
                    + mGestureLockViewWidth + " , mMarginBetweenLockView = "
                    + mMarginBetweenLockView);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //绘制GestureLockView间的连线
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint);
        }
        //绘制指引线
        if (mChoose.size() > 0) {
            if (mLastPathX != 0 && mLastPathY != 0)
                canvas.drawLine(mLastPathX, mLastPathY, mTmpTarget.x,
                        mTmpTarget.y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                //重置
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                mPaint.setColor(mFingerOnColor);
                mPaint.setAlpha(50);
                //获取是哪个GestureLockView被选中
                GestureLockView child = getChildIdByPos(x, y);
                if(child != null){
                    int cId = child.getId();
                    //如果该点还未被经过
                    if(!mChoose.contains(cId)){
                        mChoose.add(cId);
                        //设置GestureLockView为被选中状态
                        child.setMode(GestureLockView.Mode.STATUS_FINGER_ON);
                        if(mOnGestureLockViewListener != null){
                            mOnGestureLockViewListener.onBlockSelected(cId);
                        }
                        //设置指引线起点
                        mLastPathX = child.getLeft()/2 + child.getRight()/2;
                        mLastPathY = child.getTop()/2 + child.getBottom()/2;
                        if(mChoose.size() == 1){//当前child为第一个
                            mPath.moveTo(mLastPathX, mLastPathY);
                        }else{// 非第一个，将两者使用线连上
                            mPath.lineTo(mLastPathX, mLastPathY);
                        }
                    }
                }
                //指引线终点
                mTmpTarget.x = x;
                mTmpTarget.y = y;
                break;
            case MotionEvent.ACTION_UP:
                //设置手势密码
                if (status == Mode.STATUS_SET){
                    if (mChoose.size() > 0 && mChoose.size() < mMinPoint){
                        mPaint.setColor(mFingerUpErrorColor);
                        setChoosedItemCheckStatus(false);
                        Toast.makeText(getContext(), "最少连接"+mMinPoint+"个点", Toast.LENGTH_LONG).show();
                        if (mOnGestureLockViewListener != null) {
                            mOnGestureLockViewListener.setGestureError();
                        }
                    }else {
                        mPaint.setColor(mFingerUpRightColor);
                        if (mOnGestureLockViewListener != null && mChoose.size() > 0) {
                            mOnGestureLockViewListener.setGestureResult(mChoose);
                        }
                    }
                }else {//验证手势密码
                    if (mAnswer == null || mAnswer.size() <= 0){
                        Toast.makeText(getContext(), "验证手势密码，mAnswer不能为空", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    //检查手势结果是否正确
                    boolean checkAnswer = checkAnswer();
                    if (checkAnswer) {
                        mPaint.setColor(mFingerUpRightColor);
                    } else {
                        mPaint.setColor(mFingerUpErrorColor);
                    }
                    // 回调是否成功
                    if (mOnGestureLockViewListener != null && mChoose.size() > 0) {
                        this.mTryTimes--;
                        mOnGestureLockViewListener.onGestureEvent(checkAnswer);
                        if (this.mTryTimes == 0) {
                            mOnGestureLockViewListener.onUnmatchedExceedBoundary();
                        }
                    }
                    //设置每个元素的验证状态
                    setChoosedItemCheckStatus(checkAnswer);
                }
                mPaint.setAlpha(50);
                // 将终点设置位置为起点，即取消指引线
                mTmpTarget.x = mLastPathX;
                mTmpTarget.y = mLastPathY;

                // 改变子元素的状态为UP
                changeItemMode();
                //计算每个元素中箭头需要旋转的角度
                for (int i = 0; i + 1 < mChoose.size(); i++) {
                    int childId = mChoose.get(i);
                    int nextChildId = mChoose.get(i + 1);

                    GestureLockView startChild = (GestureLockView) findViewById(childId);
                    GestureLockView nextChild = (GestureLockView) findViewById(nextChildId);

                    int dx = nextChild.getLeft() - startChild.getLeft();
                    int dy = nextChild.getTop() - startChild.getTop();
                    //计算角度
                    int angle = (int) (Math.toDegrees(Math.atan2(dy, dx)) + 90);
                    startChild.setArrowDegree(angle);
                }
                break;
        }

        invalidate();
        return true;
    }

    /**
     * 设置每个点的状态
     */
    private void changeItemMode() {
        for (GestureLockView gestureLockView : mGestureLockViews) {
            if (mChoose.contains(gestureLockView.getId())) {
                gestureLockView.setMode(GestureLockView.Mode.STATUS_FINGER_UP);
            }
        }
    }

    /**
     * 设置每个选中的item的验证状态（手势是否验证通过）
     * @param checkAnswer
     */
    private void setChoosedItemCheckStatus(boolean checkAnswer){
        //设置每个元素的验证状态
        for (int i = 0; i < mChoose.size(); i++) {
            GestureLockView lockView = (GestureLockView) findViewById(mChoose.get(i));
            lockView.setCheckAnswer(checkAnswer);
        }
    }

    /**
     * 检查用户绘制的手势是否正确
     * @return
     */
    private boolean checkAnswer() {
        if (mAnswer != null && mAnswer.size() != mChoose.size()) {
            return false;
        }
        for (int i = 0; i < mAnswer.size(); i++) {
            if (mAnswer.get(i) != mChoose.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据触摸位置获取GestureLockView
     * @param x
     * @param y
     * @return
     */
    private GestureLockView getChildIdByPos(int x, int y) {
        for (GestureLockView gestureLockView:mGestureLockViews){
            if(checkPositionInChild(gestureLockView,x,y)){
                return gestureLockView;
            }
        }
        return null;
    }

    private boolean checkPositionInChild(View child, int x, int y) {
        //设置了内边距，即x,y必须落入GestureLockView的内部中间的小区域中，
        // 可以通过调整padding使得x,y落入范围不变大，或者不设置padding
        int padding = (int) (mGestureLockViewWidth * 0.15);
        if (x >= child.getLeft() + padding
                && x <= child.getRight() - padding
                && y >= child.getTop() + padding
                && y <= child.getBottom() - padding) {
            return true;
        }
        return false;
    }

    /**
     *
     * 做一些必要的重置
     */
    private void reset() {
        mChoose.clear();
        mPath.reset();
        for (GestureLockView gestureLockView : mGestureLockViews) {
            gestureLockView.setMode(GestureLockView.Mode.STATUS_NO_FINGER);
            gestureLockView.setArrowDegree(-1);
        }
    }

    /**
     * 设置回调接口
     * @param listener
     */
    public void setOnGestureLockViewListener(OnGestureLockViewListener listener) {
        this.mOnGestureLockViewListener = listener;
    }

    /**
     * 设置正确手势
     * @param answer
     */
    public void setAnswer(List<Integer> answer) {
        this.mAnswer = answer;
    }

    /**
     * 设置最大实验次数
     * @param mTryTimes
     */
    public void setTryTimes(int mTryTimes) {
        this.mTryTimes = mTryTimes;
    }

    /**
     * 设置状态（手势验证／手势设置）
     * @return
     */
    public void setStatus(Mode status) {
        this.status = status;
    }

    /**
     * 清除手势
     */
    public void clearGesture(){
        reset();
        invalidate();
    }

    public interface OnGestureLockViewListener {
        /**
         * 单独选中元素的Id
         * @param cId
         */
        public void onBlockSelected(int cId);

        /**
         * 是否匹配
         * @param matched
         */
        public void onGestureEvent(boolean matched);

        /**
         * 超过尝试次数
         */
        public void onUnmatchedExceedBoundary();

        /**
         * 返回手机绘制结果
         * @param result
         */
        public void setGestureResult(List<Integer> result);

        /**
         * 设置密码错误
         */
        public void setGestureError();
    }
}
