package com.yuyang.fitsystemwindowstestdrawer.flabbyBird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 分数
 */
public class Grades {
    /**
     * 分数图片
     */
    private Bitmap[] mNumBitmap;
    /**
     * 单个数字的高度的1/15
     */
    private static final float RADIO_SINGLE_NUM_HEIGHT = 1 / 15f;
    /**
     * 单个数字的宽度
     */
    private int mSingleGradeWidth;
    /**
     * 单个数字的高度
     */
    private int mSingleGradeHeight;
    /**
     * 单个数字的范围
     */
    private RectF mSingleNumRectF;
    /**
     * 游戏界面宽高
     */
    private int gameWidth;
    private int gameHeight;

    public Grades(int gameWidth, int gameHeight, Bitmap[] mNumBitmap){
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.mNumBitmap = mNumBitmap;
        // 初始化分数
        mSingleGradeHeight = (int) (gameHeight * RADIO_SINGLE_NUM_HEIGHT);
        mSingleGradeWidth = (int) (mSingleGradeHeight * 1.0f
                / mNumBitmap[0].getHeight() * mNumBitmap[0].getWidth());
        mSingleNumRectF = new RectF(0, 0, mSingleGradeWidth, mSingleGradeHeight);
    }

    public void draw(Canvas canvas, int grades){
        String grade = grades + "";
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.translate(gameWidth / 2 - grade.length() * mSingleGradeWidth / 2,
                1f / 8 * gameHeight);
        //挨个绘制数字
        for (int i = 0; i < grade.length(); i++) {
            String numStr = grade.substring(i, i + 1);
            int num = Integer.valueOf(numStr);
            canvas.drawBitmap(mNumBitmap[num], null, mSingleNumRectF, null);
            canvas.translate(mSingleGradeWidth, 0);
        }
        canvas.restore();
    }
}
