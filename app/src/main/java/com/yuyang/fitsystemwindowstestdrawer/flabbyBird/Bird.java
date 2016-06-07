package com.yuyang.fitsystemwindowstestdrawer.flabbyBird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;

/**
 * 绘制bird
 * 鸟在我们的屏幕中，初始化时需要一个位置，x上，肯定是居中，y上我们取2/3的高度；
 */
public class Bird {
    /**
     * 鸟在屏幕高度的1/2位置
     */
    private static final float RADIO_POS_HEIGHT = 1/2f;
    /**
     * 鸟宽度默认30dp
     */
    private static final int BIRD_SIZE = 30;
    /**
     * 鸟的横竖坐标
     */
    private int x;
    private int y;
    /**
     * 鸟的宽高
     */
    private int mWidth;
    private int mHeight;
    /**
     * 鸟的图片
     */
    private Bitmap bitmap;
    /**
     * 鸟绘制的范围
     */
    private RectF rect = new RectF();
    /**
     * 游戏屏幕高度
     */
    private int gameHeight;

    public Bird(Context context, int gameWith, int gameHeight, Bitmap bitmap){
        this.gameHeight = gameHeight;
        this.bitmap = bitmap;
        //鸟的位置
        this.x = gameWith/2 - bitmap.getWidth()/2;
        this.y = (int) (gameHeight*RADIO_POS_HEIGHT);
        //鸟的宽高
        this.mWidth = DensityUtils.dp2px(context, BIRD_SIZE);
        this.mHeight = (int) (mWidth*1.0f/bitmap.getWidth()*bitmap.getHeight());
    }

    /**
     * 绘制自己
     *
     * @param canvas
     */
    public void draw(Canvas canvas){
        rect.set(x, y, x+mWidth, y+mHeight);
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    public void resetY(){
        setY((int) (gameHeight*RADIO_POS_HEIGHT));
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getX() {
        return x;
    }
}
