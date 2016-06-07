package com.yuyang.fitsystemwindowstestdrawer.flabbyBird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过SurfaceView实现
 * FlabbyBird
 */
public class GameFlabbyBirdView extends SurfaceView implements Callback,Runnable {
    /**
     * 游戏状态
     * 1、默认情况下，是WAITING状态，屏幕静止，上面就一只静止的鸟~~
     * 2、当用户触摸屏幕时：进入RUNNING状态，游戏开始根据用户的触摸情况进行交互；
     * 3、当鸟触碰到管道或者落到地上，那么进入OVER状态，OVER时，如果触碰的是管道，则让鸟落到地上以后，立即切换为WAITING状态。
     */
    private enum GameStatus{
        WAITING,RUNNING,OVER
    }
    /**
     * **************游戏相关**************
     */
    private GameStatus mStatus = GameStatus.WAITING;
    /**
     * 每次触碰鸟上升的距离，因为是上升所有为负数
     */
    private static final int TOUCH_UP_SIZE = -16;
    /**
     * 上升距离转化为px,点击一次上升的距离
     */
    private final int mBirdUpDis = DensityUtils.dp2px(getContext(), TOUCH_UP_SIZE);
    /**
     * 记录鸟的垂直下落速度，为了实现加速下落，该数值为递增mAutoDownSpeed
     */
    private int mTmpBirdDis;
    /**
     * 鸟的下落速度
     */
    private final int mAutoDownSpeed = DensityUtils.dp2px(getContext(), 2);
    /**
     * 管道间距,默认300dp
     */
    private final int PIPE_DIS_BETWEEN_TWO = DensityUtils.dp2px(getContext(), 300);
    /**
     * 记录管道移动的距离，达到 PIPE_DIS_BETWEEN_TWO 则生成一个新的管道
     * 初始为PIPE_DIS_BETWEEN_TWO是为了游戏一开始就绘制一个管道
     */
    private int mTmpMoveDistance = PIPE_DIS_BETWEEN_TWO;


    /**
     * **************SurfaceView相关**************
     */
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Thread t;
    private boolean isRunning;
    private Paint mPaint;
    /**
     * 当情View的尺寸
     */
    private int mWidth;
    private int mHeight;
    private RectF mGamePanelRect = new RectF();
    /**
     * 背景
     */
    private Bitmap mBg;
    /**
     * **************鸟相关**************
     */
    private Bird mBird;
    private Bitmap mBirdBitmap;
    /**
     * **************地板相关**************
     */
    private Floor mFloor;
    private Bitmap mFloorBitmap;
    /**
     * 地板移动速度
     */
    private int mSpeed = DensityUtils.dp2px(getContext(), 3);
    /**
     * **************管道相关**************
     */
    /**
     * 记录界面中所有的管道
     */
    private List<Pipe> mPipes = new ArrayList<>();
    /**
     * 记录需要移除的管道
     */
    private List<Pipe> mNeedRemovePipe = new ArrayList<>();
    private Bitmap mPipeTop;
    private Bitmap mPipeBottom;
    /**
     * 上下管道的绘制范围
     */
    private RectF mPipeRect;
    /**
     * 管道宽度
     */
    private int mPipeWidth = DensityUtils.dp2px(getContext(), PIPE_WIDTH);
    /**
     * 管道的宽度默认60dp
     */
    private static final int PIPE_WIDTH = 60;
    /**
     * **************分数相关**************
     */
    private Grades mGrades;
    private final int[] mNums = new int[] {R.mipmap.flabby_bird_n0, R.mipmap.flabby_bird_n1,
            R.mipmap.flabby_bird_n2, R.mipmap.flabby_bird_n3, R.mipmap.flabby_bird_n4, R.mipmap.flabby_bird_n5,
            R.mipmap.flabby_bird_n6, R.mipmap.flabby_bird_n7, R.mipmap.flabby_bird_n8, R.mipmap.flabby_bird_n9 };
    private Bitmap[] mNumBitmap;
    /**
     * 分数
     */
    private int mGrade = 0;
    /**
     * 已经看不到的管道数量
     */
    private int mRemovedPipe = 0;

    public GameFlabbyBirdView(Context context) {
        this(context, null);
    }
    public GameFlabbyBirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        setZOrderOnTop(true);// 设置画布 背景透明
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        //初始化需要的图片资源
        initBitmaps();
    }

    /**
     * 初始化图片
     */
    private void initBitmaps() {
        mBg = loadImageByResId(R.mipmap.flabby_bird_bg1);
        mBirdBitmap = loadImageByResId(R.mipmap.flabby_bird_b1);
        mFloorBitmap = loadImageByResId(R.mipmap.flabby_bird_floor_bg2);
        mPipeTop = loadImageByResId(R.mipmap.flabby_bird_g2);
        mPipeBottom = loadImageByResId(R.mipmap.flabby_bird_g1);
        mNumBitmap = new Bitmap[mNums.length];
        for (int i = 0; i < mNumBitmap.length; i++) {
            mNumBitmap[i] = loadImageByResId(mNums[i]);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mGamePanelRect.set(0,0,mWidth,mHeight);
        //初始化mBird
        mBird = new Bird(getContext(), mWidth, mHeight, mBirdBitmap);
        //初始化mFloor
        mFloor = new Floor(mWidth, mHeight, mFloorBitmap);
        //初始化pipe
        mPipeRect = new RectF(0, 0, mPipeWidth, mHeight);
        //初始化分数
        mGrades = new Grades(mWidth, mHeight, mNumBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            switch (mStatus){
                case WAITING:
                    mStatus = GameStatus.RUNNING;
                    break;
                case RUNNING:
                    mTmpBirdDis = mBirdUpDis;
                    break;
            }
        }
        return true;
    }

    /**
     * 根据resId加载图片
     * @param resId
     * @return
     */
    private Bitmap loadImageByResId(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning){
            long start = System.currentTimeMillis();
            logic();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理一些逻辑上的计算
     */
    private void logic(){
        switch (mStatus){
            case RUNNING:
                //每次都将分数置0
                mGrade = 0;

                // 更新地板绘制的x坐标
                mFloor.setX(mFloor.getX() - mSpeed);

                //管道处理相关逻辑
                logicPipe();

                //鸟的位置处理，默认情况越降越快，当用户点击的时候瞬间上升一段距离，继续往下掉~
                mTmpBirdDis += mAutoDownSpeed;//这里是为了加速下落，所以mTmpBirdDis设为递增mAutoDownSpeed
                mBird.setY(mBird.getY() + mTmpBirdDis);

                //计算分数
                //然后加上已经看不到的管道数量（都看不到了，肯定是通过的），然后再加上屏幕上在鸟左边的管道数量~
                mGrade += mRemovedPipe;
                for (Pipe pipe:mPipes){
                    if (mBird.getX() > pipe.getX()+mPipeWidth){
                        mGrade++;
                    }
                }

                //检测游戏是否结束
                checkGameOver();
                break;
            case OVER://游戏结束，鸟落到地上
                //如果鸟还没有落地
                if (mBird.getY() < mFloor.getY()-mBird.getHeight()){
                    mTmpBirdDis += mAutoDownSpeed;//这里是为了加速下落
                    mBird.setY(mBird.getY() + mTmpBirdDis);
                }else {
                    try {
                        Thread.sleep(500);//睡半分钟，看一下死状
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mStatus = GameStatus.WAITING;
                    //重置所有参数，恢复游戏初始时的游戏元素位置
                    initPos();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 重置相关数据
     */
    private void initPos() {
        mPipes.clear();
        mNeedRemovePipe.clear();
        //鸟移动至初始位置
        mBird.resetY();
        //重置下落速度
        mTmpBirdDis = 0;
        mTmpMoveDistance = PIPE_DIS_BETWEEN_TWO;
        mRemovedPipe = 0;
        mGrade = 0;
    }

    /**
     * 检查游戏是否结束
     */
    private void checkGameOver(){
        //鸟碰到地板，游戏结束
        if (mBird.getY() > mFloor.getY() - mBird.getHeight()){
            mStatus = GameStatus.OVER;
        }
        for (Pipe pipe:mPipes){
            //鸟已经穿过了这道墙
            if (pipe.getX()+mPipeWidth < mBird.getX()){
                continue;
            }
            //鸟碰到了管道
            if (pipe.touchBird(mBird)){
                mStatus = GameStatus.OVER;
                break;
            }
        }
    }

    /**
     * 管道处理相关逻辑
     */
    private void logicPipe(){
        /**
         * 更新管道位置
         */
        for (Pipe pipe : mPipes) {
            if (pipe.getX() < -mPipeWidth){
                mNeedRemovePipe.add(pipe);
                mRemovedPipe++;
                continue;
            }
            //使管道移动
            pipe.setX(pipe.getX() - mSpeed);
        }
        //移除已经超出界面的管道
        mPipes.removeAll(mNeedRemovePipe);
        mNeedRemovePipe.clear();

        //管道移动距离
        mTmpMoveDistance += mSpeed;
        //大于等于管道间距，生成一个管道
        if (mTmpMoveDistance >= PIPE_DIS_BETWEEN_TWO){
            Pipe pipe = new Pipe(mWidth, mHeight, mPipeTop, mPipeBottom);
            mPipes.add(pipe);
            mTmpMoveDistance = 0;
        }
    }

    /**
     * 绘制游戏中的各个元素
     */
    private void draw() {
        try
        {
            // 获得canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //绘制背景
                drawBg();
                //绘制鸟
                drawBird();
                //绘制管道
                drawPipes();
                //绘制地板
                drawFloor();
                //绘制分数
                drawGrades();
            }
        } catch (Exception e) {
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * 绘制分数
     */
    private void drawGrades() {
        mGrades.draw(mCanvas, mGrade);
    }

    /**
     * 绘制管道
     */
    private void drawPipes() {
        for (Pipe pipe : mPipes) {
            pipe.draw(mCanvas, mPipeRect);
        }
    }

    /**
     * 绘制地板
     */
    private void drawFloor() {
        mFloor.draw(mCanvas, mPaint);
    }

    /**
     * 绘制鸟
     */
    private void drawBird() {
        mBird.draw(mCanvas);
    }

    /**
     * 绘制背景
     */
    private void drawBg() {
        mCanvas.drawBitmap(mBg, null, mGamePanelRect, null);
    }
}
