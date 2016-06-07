package com.yuyang.fitsystemwindowstestdrawer.flabbyBird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

/**
 * 管道
 */
public class Pipe {
    /**
     * 上下管道间距
     */
    private static final float RADIO_BETWEEN_UP_DOWN = 1/5f;
    /**
     * 上管道最大高度
     */
    private static final float RADIO_MAX_HEIGHT = 2/5f;
    /**
     * 上管道最小高度
     */
    private static final float RADIO_MIN_HEIGHT = 1/5f;
    /**
     * 上管道坐标
     */
    private int x;
    private int y;
    /**
     * 上管道高度
     */
    private int height;
    /**
     * 上下管道间距
     */
    private int margin;
    /**
     * 上管道图片
     */
    private Bitmap mTop;
    /**
     * 下管道图片
     */
    private Bitmap mBottom;

    private static Random random = new Random();

    public Pipe(int gameWidth, int gameHeight, Bitmap top, Bitmap bottom){
        margin = (int) (gameHeight * RADIO_BETWEEN_UP_DOWN);
        //从最右侧开始
        x = gameWidth;

        mTop = top;
        mBottom = bottom;

        randomHeight(gameHeight);
    }

    /**
     * 随机生成一个高度
     * @param gameHeight
     */
    private void randomHeight(int gameHeight) {
        height = random.nextInt((int) (gameHeight * (RADIO_MAX_HEIGHT-RADIO_MIN_HEIGHT)));
        height = (int) (height + gameHeight*RADIO_MIN_HEIGHT);
    }

    /**
     * 绘制自己
     * 传入的rect是固定的一个矩形，我们的上下管道都是完整的绘制在这个rect中；
     * 然后根据height，去偏移canvas的y，让rect显示出height部分，主要是因为，
     * 这样可以保证每个管道样子是一样的（如果根据height，使用不同的rect，会产生缩放）；
     * @param canvas
     * @param rect
     */
    public void draw(Canvas canvas, RectF rect){
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        //rect为整个管道，假设完整管道为100，需要绘制20，则向上偏移80
        canvas.translate(x, -(rect.bottom-height));
        canvas.drawBitmap(mTop, null, rect, null);
        //绘制下管道，偏移量为 height+margin
        canvas.translate(0, (rect.bottom - height) + height + margin);
        canvas.drawBitmap(mBottom, null, rect, null);
        canvas.restore();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * 判断鸟是否碰到了管道
     * @param mBird
     * @return
     */
    public boolean touchBird(Bird mBird) {
        //如果鸟碰到了管道
        if (mBird.getX()+mBird.getWidth() > x
                && (mBird.getY() < height || mBird.getY()+mBird.getHeight() > height+margin)){
            return true;
        }
        return false;
    }
}
