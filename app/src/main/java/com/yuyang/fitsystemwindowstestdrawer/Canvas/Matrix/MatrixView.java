package com.yuyang.fitsystemwindowstestdrawer.Canvas.Matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 变形矩阵
 */
public class MatrixView extends View {
    Bitmap bitmap;
    float bitmapWidth;
    float bitmapHeight;
    final int HEIGHT = 10;
    final int WIDTH = 10;
    float k = 1;
    BIANHUAN_TYPE type = BIANHUAN_TYPE.NORMAL;
    Matrix matrix = new Matrix();

    private enum BIANHUAN_TYPE{
        NORMAL,
        SCALE,
        TRANSLATE,
        ROTATE,
        SKEW,
        MESH
    }

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(type == BIANHUAN_TYPE.NORMAL){
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.SCALE){
            matrix.setScale(2, 2);
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.TRANSLATE){
            matrix.setTranslate(200, 200);
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.ROTATE){
            matrix.setRotate(90, bitmap.getWidth()/2, bitmap.getHeight()/2);
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.SKEW){
            matrix.setSkew(2, 4);
            canvas.drawBitmap(bitmap, matrix, null);
        }else if (type == BIANHUAN_TYPE.MESH){//像素块分析
            float[] orig = new float[(HEIGHT+1)*(WIDTH+1)*2];
            float[] verts = new float[(HEIGHT+1)*(WIDTH+1)*2];
            //获取交叉点坐标，并保存到orig数组
            int index = 0;
            for (int y=0; y<=HEIGHT; y++){
                float fy = bitmapHeight*y/HEIGHT;
                for (int x=0; x<=WIDTH; x++){
                    float fx = bitmapWidth*x/WIDTH;
                    orig[index*2+0] = verts[index*2+0] = fx;
                    //这里将坐标人为的+100是为了让图像下移，避免扭曲后被屏幕遮挡
                    orig[index*2+1] = verts[index*2+1] = fy+100;
                    index += 1;
                }
            }
            //使用一个正弦函数Sin x来改变交叉点纵坐标的值
            for (int j=0; j<=HEIGHT; j++){
                for (int i=0; i<=WIDTH; i++){
                    verts[(j*(WIDTH+1)+i)*2+0] += 0;
                    float offsetY = (float)Math.sin((float)i/WIDTH*2*Math.PI*k);
                    verts[(j*(WIDTH+1)+i)*2+1] = orig[(j*(WIDTH+1)+i)*2+1]+offsetY*2;
                }
            }

            canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);

            k += 0.1F;
            invalidate();
        }
    }

    public void scale(){
        type = BIANHUAN_TYPE.SCALE;
        invalidate();
    }

    public void translate(){
        type = BIANHUAN_TYPE.TRANSLATE;
        invalidate();
    }

    public void rotate(){
        type = BIANHUAN_TYPE.ROTATE;
        invalidate();
    }

    public void skew(){
        type = BIANHUAN_TYPE.SKEW;
        invalidate();
    }

    public void reset(){
        type = BIANHUAN_TYPE.NORMAL;
        matrix = new Matrix();
        invalidate();
    }

    public void setMatrix(float[] matrixValue) {
        matrix.setValues(matrixValue);
        invalidate();
    }

    public void drawMesh() {
        type = BIANHUAN_TYPE.MESH;
        invalidate();
    }
}
