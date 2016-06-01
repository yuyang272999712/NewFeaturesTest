package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.gestureLock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * 手势密码中每个点的绘制
 * GestureLockView有三个状态，没有手指触碰、手指触碰、和手指抬起，会根据这三个状态绘制不同的效果，
 * 以及抬起时的小箭头需要旋转的角度，会根据用户选择的GestureLockView，进行计算，
 * 在GestureLockViewGroup为每个GestureLockView设置
 */
public class GestureLockView extends View {
    private static final String TAG = "GestureLockView";

    /**
     * GestureLockView的三种状态
     */
    enum Mode {
        STATUS_NO_FINGER,//没有手指触碰
        STATUS_FINGER_ON,//手指触碰
        STATUS_FINGER_UP //手指抬起
    }
    /**
     * GestureLockView的当前状态
     */
    private Mode mCurrentStatus = Mode.STATUS_NO_FINGER;
    /**
     * 手指抬起后手势密码是否正确
     */
    private boolean checkAnswer = true;
    /**
     * 宽度
     */
    private int mWidth;
    /**
     * 高度
     */
    private int mHeight;
    /**
     * 外圆半径
     */
    private int mRadius;
    /**
     * 内圆的半径 = mInnerCircleRadiusRate * mRadius
     */
    private float mInnerCircleRadiusRate = 0.3F;
    /**
     * 画笔宽度
     */
    private int mStrokeWidth = 8;
    /**
     * 圆心坐标
     */
    private int mCenterX;
    private int mCenterY;
    /**
     * 箭头（小三角最长边的一半长度 = mArrowRate * mWidth / 2 ）
     */
    private float mArrowRate = 0.333f;
    /**
     * 箭头旋转角度
     */
    private int mArrowDegree = -1;
    /**
     * 箭头的绘制路径
     */
    private Path mArrowPath;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 四个颜色，可由用户自定义，初始化时由GestureLockViewGroup传入
     */
    private int mColorNoFingerInner;//无手指触摸状态下的内圆颜色
    private int mColorNoFingerOuter;//无手指触摸状态下的外圆颜色
    private int mColorFingerOn;//手指触摸的状态下内圆和外圆的颜色
    private int mColorFingerUpError;//手指抬起的状态下内圆和外圆的颜色(错误)
    private int mColorFingerUpRight;//手指抬起的状态下内圆和外圆的颜色(正确)

    public GestureLockView(Context context, int colorNoFingerInner , int colorNoFingerOuter , int colorFingerOn , int colorFingerUpError, int colorFingerUpRight) {
        super(context);
        this.mColorNoFingerInner = colorNoFingerInner;
        this.mColorNoFingerOuter = colorNoFingerOuter;
        this.mColorFingerOn = colorFingerOn;
        this.mColorFingerUpError = colorFingerUpError;
        this.mColorFingerUpRight = colorFingerUpRight;

        mPaint = new Paint();
        mArrowPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        //取长宽中最小的（其实在父ViewGroup中已经将长宽设置为相等了）
        mWidth = Math.min(mWidth, mHeight);
        mRadius = mCenterX = mCenterY = mWidth / 2;
        mRadius -= mStrokeWidth / 2;//TODO yuyang 这里除以2似乎有点问题

        // 绘制三角形，初始时是个默认箭头朝上的一个等腰三角形，用户绘制结束后，根据由两个GestureLockView决定需要旋转多少度
        float mArrowLength = mWidth / 2 * mArrowRate;
        mArrowPath.moveTo(mWidth/2, mStrokeWidth+2);//箭头顶点移动至外圆上面，这样就让箭头朝上了
        mArrowPath.lineTo(mWidth/2 - mArrowLength, mStrokeWidth+2 + mArrowLength);//画左边线
        mArrowPath.lineTo(mWidth/2 + mArrowLength, mStrokeWidth+2 + mArrowLength);//画底边线
        mArrowPath.close();//闭合，形成三角形
        mArrowPath.setFillType(Path.FillType.WINDING);//消除锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentStatus){
            case STATUS_FINGER_ON:
                //绘制外圆
                mPaint.setStyle(Paint.Style.STROKE);//设置空心的
                mPaint.setColor(mColorFingerOn);
                mPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);
                //绘制内圆
                mPaint.setStyle(Paint.Style.FILL);//设置有填充的实心圆
                canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate, mPaint);
                break;
            case STATUS_FINGER_UP:
                //绘制外圆
                mPaint.setStyle(Paint.Style.STROKE);//设置空心的
                if(checkAnswer){
                    mPaint.setColor(mColorFingerUpRight);
                }else {
                    mPaint.setColor(mColorFingerUpError);
                }
                mPaint.setStrokeWidth(mStrokeWidth);
                canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);
                //绘制内圆
                mPaint.setStyle(Paint.Style.FILL);//设置有填充的实心圆
                canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate, mPaint);
                //绘制箭头
                drawArrow(canvas);
                break;
            case STATUS_NO_FINGER:
                // 绘制外圆
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mColorNoFingerOuter);
                canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
                // 绘制内圆
                mPaint.setColor(mColorNoFingerInner);
                canvas.drawCircle(mCenterX, mCenterY, mRadius * mInnerCircleRadiusRate, mPaint);
                break;
        }
    }

    /**
     * 画箭头
     * @param canvas
     */
    private void drawArrow(Canvas canvas) {
        if (mArrowDegree != -1){
            mPaint.setStyle(Paint.Style.FILL);
            canvas.save();
            canvas.rotate(mArrowDegree, mCenterX, mCenterY);//以mCenterX, mCenterY为中心旋转
            canvas.drawPath(mArrowPath, mPaint);//画箭头
            canvas.restore();
        }
    }

    /**
     * 设置当前模式并重绘界面
     * @param mode
     */
    public void setMode(Mode mode) {
        this.mCurrentStatus = mode;
        invalidate();
    }

    /**
     * 设置箭头旋转角度
     * @param degree
     */
    public void setArrowDegree(int degree) {
        this.mArrowDegree = degree;
    }

    public int getArrowDegree() {
        return this.mArrowDegree;
    }

    /**
     * 手机抬起后密码是否正确
     * @param checkAnswer
     */
    public void setCheckAnswer(boolean checkAnswer) {
        this.checkAnswer = checkAnswer;
    }

    public boolean isCheckAnswer() {
        return checkAnswer;
    }
}
