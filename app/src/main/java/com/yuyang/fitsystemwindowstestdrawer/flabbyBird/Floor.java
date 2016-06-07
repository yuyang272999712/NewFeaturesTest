package com.yuyang.fitsystemwindowstestdrawer.flabbyBird;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * 地板
 */
public class Floor {
    /**
     * 地板位置游戏面板高度的4/5到底部
     */
    private static final float FLOOR_Y_POS_RADIO = 4/5f;
    /**
     * 地板坐标
     */
    private int x;
    private int y;
    /**
     * 游戏屏幕宽高
     */
    private int mGameWidth;
    private int mGameHeight;
    /**
     * 地板填充物
     */
    private BitmapShader mFloorShader;

    public Floor(int gameWidth, int gameHeight, Bitmap floor){
        this.mGameWidth = gameWidth;
        this.mGameHeight = gameHeight;
        y = (int) (mGameHeight*FLOOR_Y_POS_RADIO);
        //BitmapShader横向重复，纵向拉伸（这里的拉伸是指，纵向的最后一个像素不断重复）。
        mFloorShader = new BitmapShader(floor, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    }

    /**
     * 绘制自己
     *
     * 在GameFlabbyBird定义一个变量，表示移动速度mSpeed，
     * 然后在draw中不断更新mFloor的x坐标为：mFloor.setX(mFloor.getX() - mSpeed);
     * 这样的画，每次绘制我们floor的起点，会向左移动mSpeed个位置，就形成了运行的效果；但是呢？不能一直减下去，不然最终我们的x岂不是负无穷了，那得绘制多大？
       所以我们：
         if (-x > mGameWidth){
            x = x % mGameWidth;
         }
       如果x的正值大于宽度了，我们取余一下~~~
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint){
        if (-x > mGameWidth){
            x = x%mGameWidth;
        }
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        //移动到指定位置
        canvas.translate(x, y);
        paint.setShader(mFloorShader);
        canvas.drawRect(x, 0, -x+mGameWidth, mGameHeight-y, paint);
        canvas.restore();
        paint.setShader(null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
}
