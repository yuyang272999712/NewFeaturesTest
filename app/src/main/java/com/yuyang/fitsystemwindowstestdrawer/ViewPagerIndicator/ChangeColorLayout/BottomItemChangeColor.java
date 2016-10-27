package com.yuyang.fitsystemwindowstestdrawer.ViewPagerIndicator.ChangeColorLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 自定义颜色可变的tab标签(仿微信)
 * @author yuyang 2015年6月11日
 *
 */
public class BottomItemChangeColor extends LinearLayout {
    private LinearLayout backLayout;
    private LinearLayout frontLayout;
    private ImageView backImgView;
    private TextView backTextView;
    private ImageView frontImgView;
    private TextView frontTextView;

    private int frontColor;
    private int backColor;
    private BitmapDrawable frontImg;
    private BitmapDrawable backImg;
    private String text;
    private int mTextSize;

    private float mAlpha;

    private LayoutInflater inflater;

    public BottomItemChangeColor(Context context) {
        this(context, null);
    }

    public BottomItemChangeColor(Context context, AttributeSet attr) {
        super(context, attr);
        this.inflater = LayoutInflater.from(context);
        int defColorValue = Color.parseColor("#ff45C01A");
        int defTextSize = 12;
        /**
         * 获取自定义属性的值
         */
        TypedArray typeArray = context.obtainStyledAttributes(attr,
                R.styleable.BottomItemChangeColor);
        int n = typeArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int type = typeArray.getIndex(i);
            switch (type) {
                case R.styleable.BottomItemChangeColor_bottom_item_front_icons:
                    frontImg = (BitmapDrawable) typeArray.getDrawable(type);
                    break;
                case R.styleable.BottomItemChangeColor_bottom_item_front_color:
                    frontColor = typeArray.getColor(type, defColorValue);
                    break;
                case R.styleable.BottomItemChangeColor_bottom_item_back_icons:
                    backImg = (BitmapDrawable) typeArray.getDrawable(type);
                    break;
                case R.styleable.BottomItemChangeColor_bottom_item_back_color:
                    backColor = typeArray.getColor(type, defColorValue);
                    break;
                case R.styleable.BottomItemChangeColor_bottom_item_text:
                    text = typeArray.getString(type);
                    break;
                case R.styleable.BottomItemChangeColor_bottom_item_text_size:
                    mTextSize = (int) typeArray.getDimension(type, defTextSize);
                    break;
            }
        }
        typeArray.recycle();
        init();
    }

    /**
     * 初始化页面
     */
    private void init() {
        View view = inflater.inflate(R.layout.custom_layout_bottom_item_change_color, null);
        backLayout = (LinearLayout) view.findViewById(R.id.bottom_item_back);
        frontLayout = (LinearLayout) view.findViewById(R.id.bottom_item_front);
        backImgView = (ImageView) view.findViewById(R.id.bottom_item_back_img);
        backTextView = (TextView) view.findViewById(R.id.bottom_item_back_text);
        frontImgView = (ImageView) view.findViewById(R.id.bottom_item_front_img);
        frontTextView = (TextView) view.findViewById(R.id.bottom_item_front_text);

        backImgView.setImageDrawable(backImg);
        frontImgView.setImageDrawable(frontImg);
        backTextView.setText(text);
        backTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        backTextView.setTextColor(backColor);
        frontTextView.setText(text);
        frontTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        frontTextView.setTextColor(frontColor);
        this.addView(view);
    }

    @SuppressLint("NewApi")
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        backLayout.setAlpha(mAlpha);
        frontLayout.setAlpha(1-mAlpha);
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
