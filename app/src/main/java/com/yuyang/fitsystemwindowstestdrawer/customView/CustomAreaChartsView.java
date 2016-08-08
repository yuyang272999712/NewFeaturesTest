package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 画着玩的
 */
public class CustomAreaChartsView extends View {
    private Paint mPaint;

    //坐标原点、X轴最大坐标、Y轴最大坐标
    private int[] mZeroPos = new int[2];
    private int[] mMaxXPos = new int[2];
    private int[] mMaxYPos = new int[2];

    //View的宽、高
    private int mWidth, mHight;
    //坐标绘制范围的宽高
    private int mRealWidth, mRealHight;
    //X轴标题、Y轴标题
    private String mTitleY, mTitleX;

    //X、Y轴分级的值
    private ArrayList<Integer> mXLevel = new ArrayList<>();
    private ArrayList<Integer> mYLevel = new ArrayList<>();
    //每个范围的描述文字
    private ArrayList<String> mGridLevelText = new ArrayList<>();
    //每个范围的颜色值
    private ArrayList<Integer> mGridColorLevel = new ArrayList<>();
    //每个范围描述文字的颜色值
    private ArrayList<Integer> mGridTxtColorLevel = new ArrayList<>();
    //一共有多少个绘制范围
    private int mGridLevel = 0;
    //title字符大小
    private int mXYTitleTextSize = 40;
    //当前的指示点
    private int mCurrentXpos, mCurrentYpos;

    public CustomAreaChartsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);//图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示速度

        //TODO 可以写一个attr自己配置
        initValues();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPosition();
        drawXYTitle(canvas);
        drawXYLines(canvas);
        drawContent(canvas);
    }

    /**
     * 绘制坐标系中的范围
     * @param canvas
     */
    private void drawContent(Canvas canvas) {
        mGridLevel = mXLevel.size() - 1;
        //计算出偏移title等显示尺标后的真实XY轴长度，便于接下来等分
        mRealWidth = mWidth - mXYTitleTextSize*2;
        mRealHight = mHight - mXYTitleTextSize*6;
        //算出等分间距
        int offsetX = mRealWidth/mGridLevel;
        int offsetY = mRealHight/mGridLevel;
        //绘制Content
        for (int index=0; index<mGridLevel+1; index++){
            mPaint.setColor(Color.DKGRAY);
            mPaint.setTextAlign(Paint.Align.RIGHT);
            mPaint.setTextSize(mXYTitleTextSize-5);
            //绘制X轴的那些坐标区间点，包含0点坐标
            canvas.drawText(String.valueOf(mXLevel.get(index)), mZeroPos[0]+(index*offsetX), mZeroPos[1] + mXYTitleTextSize, mPaint);
            //绘制Y轴的坐标区间点，不包含0点
            if (index != 0){
                canvas.drawText(String.valueOf(mYLevel.get(index)), mZeroPos[0], mZeroPos[1]-(index*offsetY), mPaint);
            }

            if (index == mGridLevel) {
                //坐标区间 = 真实区间 + 1
                break;
            }

            //填充范围颜色值
            mPaint.setColor(mGridColorLevel.get(mGridLevel - 1 - index));
            mPaint.setStyle(Paint.Style.FILL);
            //绘制区间叠加图谱方块，从远到0坐标，因为小的图会覆盖大的图
            canvas.drawRect(mMaxYPos[0], mMaxYPos[1]+(index*offsetY), mMaxXPos[0]-(index*offsetX), mMaxXPos[1], mPaint);
            //绘制区间上的文字
            mPaint.setColor(mGridTxtColorLevel.get(index));
            mPaint.setTextAlign(Paint.Align.RIGHT);
            mPaint.setTextSize(mXYTitleTextSize);
            canvas.drawText(mGridLevelText.get(index), mMaxXPos[0] - index * offsetX - mXYTitleTextSize,
                    mMaxYPos[1] + index * offsetY + mXYTitleTextSize, mPaint);
        }
        //绘制当前坐标
        drawNotice(canvas, offsetX, offsetY);
    }

    private void drawNotice(Canvas canvas, int offsetX, int offsetY) {
        int realPosX = 0;
        int realPosY = 0;
        //计算传入的x值与真实屏幕坐标的像素值的百分比差值转换
        for (int index=0; index<mGridLevel; index++) {
            if (mCurrentXpos >= mXLevel.get(index) && mCurrentXpos < mXLevel.get(index+1)) {
                int subValue = mCurrentXpos - mXLevel.get(index);
                int offset = mXLevel.get(index+1) - mXLevel.get(index);
                realPosX = mZeroPos[0] + index*offsetX + (subValue / offset);
                break;
            }
        }
        //计算传入的y值与真实屏幕坐标的像素值的百分比差值转换
        for (int index=0; index<mGridLevel; index++) {
            if (mCurrentYpos >= mYLevel.get(index) && mCurrentYpos < mYLevel.get(index+1)) {
                int subValue = mCurrentYpos - mYLevel.get(index);
                int offset = mYLevel.get(index+1) - mYLevel.get(index);
                realPosY = mZeroPos[1] - index*offsetY - (offsetY - (subValue / offset));
                break;
            }
        }
        //画我们传入的坐标点的标记小红点
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(realPosX, realPosY, 8, mPaint);

        int[] centerPos = {mZeroPos[0] + mRealWidth/2, mZeroPos[1] - mRealHight/2};

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        RectF rectF = null;
        Path path = new Path();
        //画红点旁边的提示框和文字，有四个区域，然后提示框的小三角指标方位不同
        if (realPosX <= centerPos[0] && realPosY >= centerPos[1]) {
            //left-bottom
            //画三角形
            path.moveTo(realPosX+5, realPosY+5);
            path.lineTo(realPosX+15, realPosY+15);
            path.lineTo(realPosX+15, realPosY-15);
            //画矩形背景
            rectF = new RectF(realPosX+15, realPosY-40, realPosX+200, realPosY + 30);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);
            //画提示框的文字
            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("("+mCurrentXpos+", "+mCurrentYpos+")", realPosX+30, realPosY, mPaint);
        }
        else if (realPosX <= centerPos[0] && realPosY < centerPos[1]) {
            //left-top
            path.moveTo(realPosX+5, realPosY+5);
            path.lineTo(realPosX+15, realPosY+15);
            path.lineTo(realPosX + 15, realPosY - 15);

            rectF = new RectF(realPosX+15, realPosY - 20, realPosX+200, realPosY + 50);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);

            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("("+mCurrentXpos+", "+mCurrentYpos+")", realPosX+30, realPosY+20, mPaint);
        }
        else if (realPosX > centerPos[0] && realPosY >= centerPos[1]) {
            //right-bottom
            path.moveTo(realPosX-5, realPosY+5);
            path.lineTo(realPosX-15, realPosY+15);
            path.lineTo(realPosX - 15, realPosY - 15);

            rectF = new RectF(realPosX-200, realPosY-40, realPosX-15, realPosY + 30);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);

            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("("+mCurrentXpos+", "+mCurrentYpos+")", realPosX-180, realPosY, mPaint);
        }
        else if (realPosX > centerPos[0] && realPosY < centerPos[1]) {
            //right-top
            path.moveTo(realPosX-5, realPosY+5);
            path.lineTo(realPosX-15, realPosY+15);
            path.lineTo(realPosX - 15, realPosY - 15);

            rectF = new RectF(realPosX-200, realPosY - 20, realPosX-15, realPosY + 50);
            canvas.drawRoundRect(rectF, 15, 15, mPaint);

            mPaint.reset();
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(mXYTitleTextSize - 5);
            canvas.drawText("("+mCurrentXpos+", "+mCurrentYpos+")", realPosX-180, realPosY+30, mPaint);
        }

        path.close();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 绘制X、Y坐标
     * @param canvas
     */
    private void drawXYLines(Canvas canvas) {
        mPaint.setColor(Color.DKGRAY);
        canvas.drawLine(mZeroPos[0], mZeroPos[1], mMaxXPos[0], mMaxXPos[1], mPaint);
        canvas.drawLine(mZeroPos[0], mZeroPos[1], mMaxYPos[0], mMaxYPos[1], mPaint);
    }

    /**
     * 绘制XY轴顶点标题
     * @param canvas
     */
    private void drawXYTitle(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#1FB0E7"));
        mPaint.setTextSize(mXYTitleTextSize);

        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(mTitleY, 0, mXYTitleTextSize, mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(mTitleX, mMaxXPos[0], mMaxXPos[1] + mXYTitleTextSize * 2, mPaint);
    }

    /**
     * 确定坐标原点、X轴顶点、Y轴顶点坐标
     */
    private void initPosition() {
        mZeroPos[0] = mXYTitleTextSize * 2;
        mZeroPos[1] = mHight - mXYTitleTextSize * 4;

        mMaxXPos[0] = mWidth;
        mMaxXPos[1] = mHight - mXYTitleTextSize * 4;

        mMaxYPos[0] = mXYTitleTextSize * 2;
        mMaxYPos[1] = mXYTitleTextSize * 2;
    }

    //设置当前比值
    public void updateValues(int x, int y) {
        mCurrentXpos = x;
        mCurrentYpos = y;
        postInvalidate();
    }


    private void initValues() {
        //初始化x轴坐标区间
        mXLevel.add(0);
        mXLevel.add(60);
        mXLevel.add(90);
        mXLevel.add(100);
        mXLevel.add(110);
        mXLevel.add(120);
        //初始化y轴坐标区间
        mYLevel.add(0);
        mYLevel.add(90);
        mYLevel.add(140);
        mYLevel.add(160);
        mYLevel.add(180);
        mYLevel.add(200);
        //初始化区间颜色
        mGridColorLevel.add(Color.parseColor("#1FB0E7"));
        mGridColorLevel.add(Color.parseColor("#4FC7F4"));
        mGridColorLevel.add(Color.parseColor("#4FDDF2"));
        mGridColorLevel.add(Color.parseColor("#90E9F4"));
        mGridColorLevel.add(Color.parseColor("#B2F6F1"));
        //初始化区间文字提示颜色
        mGridTxtColorLevel.add(Color.parseColor("#EA8868"));
        mGridTxtColorLevel.add(Color.parseColor("#EA8868"));
        mGridTxtColorLevel.add(Color.parseColor("#EA8868"));
        mGridTxtColorLevel.add(Color.WHITE);
        mGridTxtColorLevel.add(Color.BLACK);
        //初始化区间文字
        mGridLevelText.add("异常");
        mGridLevelText.add("过高");
        mGridLevelText.add("偏高");
        mGridLevelText.add("正常");
        mGridLevelText.add("偏低");
        //X、Y标题
        mTitleX="投入量(H)";
        mTitleY="产出量(H)";
    }
}
