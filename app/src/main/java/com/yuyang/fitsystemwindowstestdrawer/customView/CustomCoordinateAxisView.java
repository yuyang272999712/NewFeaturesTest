package com.yuyang.fitsystemwindowstestdrawer.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义坐标轴
 */
public class CustomCoordinateAxisView extends View {
    private int width;
    private int height;
    private Point center;//中心点
    private List<PointF> points = new ArrayList<>();//四个坐标轴上的点，一次存储上右下左四个坐标
    private List<String> titles = new ArrayList<>();//四个点对应的文字信息
    private Path path;//四个点围成的线路
    private Paint paint;
    //坐标轴点选中时圆的半径
    private float pointRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
    //坐标轴宽度
    private float axisWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
    //选中的点圆的线条宽度
    private float circleWidht = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics());
    private int choosed = 0;
    //文字区域大小
    private Rect textBound;
    //文字大小
    private float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
    //各种颜色
    private int axisColor = Color.parseColor("#a2a2a2");
    private int fillColor = Color.parseColor("#b052a198");
    private int circleColor = Color.parseColor("#FF00FBFF");
    private int textColor = Color.RED;
    private int textBgColor = Color.parseColor("#9000BFFF");

    public CustomCoordinateAxisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AxisView);
        axisColor = array.getColor(R.styleable.AxisView_axis_color, axisColor);
        fillColor = array.getColor(R.styleable.AxisView_axis_fill_color, fillColor);
        circleColor = array.getColor(R.styleable.AxisView_axis_circle_color, circleColor);
        array.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);

        path = new Path();

        textBound = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        center = new Point(width/2, height/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(axisColor);
        paint.setStrokeWidth(axisWidth);
        //横坐标
        canvas.drawLine(0, center.y, width, center.y, paint);
        //纵坐标
        canvas.drawLine(center.x, 0, center.x, height, paint);
        //画区域
        if (points.size() > 0){
            path.moveTo(points.get(0).x, points.get(0).y);
            path.lineTo(points.get(1).x, points.get(1).y);
            path.lineTo(points.get(2).x, points.get(2).y);
            path.lineTo(points.get(3).x, points.get(3).y);
            path.close();
            //画围住的区域
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawPath(path, paint);
            //画选中的点
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(circleWidht);
            paint.setColor(circleColor);
            PointF choosePoint = points.get(choosed);
            canvas.drawCircle(choosePoint.x, choosePoint.y, pointRadius, paint);
            //画文字
            paint.setTextSize(textSize);
            paint.setColor(textColor);
            paint.setStyle(Paint.Style.FILL);
            for (int i=0;i<4;i++){
                String title = titles.get(i);
                paint.getTextBounds(title, 0, title.length(), textBound);
                float x=0,y=0;
                PointF point;
                switch (i){
                    case 0:
                        point = points.get(i);
                        x = point.x+10;
                        y = point.y+textBound.height()/2;
                        canvas.drawText(title, x, y, paint);
                        break;
                    case 1:
                        point = points.get(i);
                        x = point.x+10;
                        y = point.y-10;
                        canvas.drawText(title, x, y, paint);
                        break;
                    case 2:
                        point = points.get(i);
                        x = point.x+10;
                        y = point.y+10;
                        canvas.drawText(title, x, y, paint);
                        break;
                    case 3:
                        point = points.get(i);
                        x = point.x+10;
                        y = point.y-10;
                        canvas.drawText(title, x, y, paint);
                        break;
                }
            }
        }
    }

    /**
     * 共外部调用设置四个坐标点
     * @param products
     */
    public void setPercentAndText(List<Product> products) {
        if (products == null || products.size() != 4){
            throw new RuntimeException("必须传入四个坐标值");
        }
        for (int i=0;i<4;i++){
            Product product = products.get(i);
            titles.add(product.getTitle());
            float x=center.x,y=center.y;
            switch (i){
                case 0:
                    x = center.x;
                    y = center.y*(1-product.getPercent());
                    break;
                case 1:
                    x = center.x*(1+product.getPercent());
                    y = center.y;
                    break;
                case 2:
                    x = center.x;
                    y = center.y*(1+product.getPercent());
                    break;
                case 3:
                    x = center.x*(1-product.getPercent());
                    y = center.y;
                    break;
            }
            PointF point = new PointF(x,y);
            points.add(point);
        }
        invalidate();
    }

    /**
     * 共外部更改选中点
     * @param choose
     */
    public void setChoosed(int choose){
        if (choose<0 || choose>3){
            throw new RuntimeException("所选点必须在[0,3]指间");
        }
        choosed = choose;
        invalidate();
    }
}
