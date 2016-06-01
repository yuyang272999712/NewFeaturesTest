package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.pintu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 拼图游戏View
 */
public class GamePintuLayout extends RelativeLayout implements View.OnClickListener {
    /**
     * 拼图个数
     */
    private int mColumn = 3;
    /**
     * 布局宽
     */
    private int mWidth;
    /**
     * 布局边距
     */
    private int mPadding;
    /**
     * 存放每个拼图item的ImageView
     */
    private ImageView[] mGamePintuItems;
    /**
     * item的宽
     */
    private int mItemWidth;
    /**
     * item间距
     */
    private int mMargin = 2;
    /**
     * 原始图片
     */
    private Bitmap mBitmap;
    /**
     * 存放原始图片切完后的bitmap
     */
    private List<ImagePiece> mItemBitmaps;
    /**
     * 第一次加载
     */
    private boolean once;
    /**
     * 第一次点击的图片
     */
    private ImageView mFirst;
    /**
     * 第二次点击的图片（与第一次交换位置）
     */
    private ImageView mSecond;
    /**
     * 动画运行的标志
     */
    private boolean isAniming;
    /**
     * 动画层（如果直接使用动画的话会有一次闪烁）
     */
    private RelativeLayout mAnimLayout;

    public GamePintuLayout(Context context) {
        this(context, null);
    }

    public GamePintuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GamePintuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMargin,
                context.getResources().getDisplayMetrics());
        mPadding = Math.min(getPaddingLeft(), Math.min(getPaddingTop(), Math.min(getPaddingRight(),getPaddingBottom())));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取布局长度
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
        if(!once){
            initBitmap();
            initItems();
        }
        once = true;
        setPadding(mPadding,mPadding,mPadding,mPadding);
        setMeasuredDimension(mWidth, mWidth);
    }

    /**
     * 布局Item
     */
    private void initItems() {
        //item宽度
        mItemWidth = (mWidth - mPadding*2 - mMargin*(mColumn-1))/mColumn;
        mGamePintuItems = new ImageView[mColumn*mColumn];
        //布局每个Item
        for (int i=0; i<mGamePintuItems.length; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(mItemBitmaps.get(i).bitmap);
            imageView.setId(i+1);
            //通过tag中的i查找mItemBitmaps中的bitmap
            imageView.setTag(i+"_"+mItemBitmaps.get(i).index);
            imageView.setOnClickListener(this);
            mGamePintuItems[i] = imageView;

            LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
            //不是最后一列
            if ((i+1)%mColumn != 0){
                lp.rightMargin = mMargin;
            }
            //不是第一列
            if (i%mColumn != 0){
                lp.addRule(RIGHT_OF, mGamePintuItems[i-1].getId());
            }
            //不是第一行
            if (i>mColumn-1){
                lp.topMargin = mMargin;
                lp.addRule(BELOW, mGamePintuItems[i-mColumn].getId());
            }

            addView(imageView, lp);
        }
    }

    /**
     * 初始化拼图item数组
     */
    private void initBitmap() {
        if (mBitmap == null){
            mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.h);
        }
        mItemBitmaps = split(mBitmap, mColumn);
        //打乱数组顺序
        Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece lhs, ImagePiece rhs) {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });
    }

    /**
     * 分割原始图片
     * @param mBitmap
     * @param mColumn
     * @return
     */
    private List<ImagePiece> split(Bitmap mBitmap, int mColumn) {
        List<ImagePiece> pieces = new ArrayList<>(mColumn*mColumn);
        //图片原始宽高
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        //每个Item的宽
        int pieceWidth = Math.min(width, height)/mColumn;
        for (int i=0; i<mColumn; i++){
            for (int j=0; j<mColumn; j++){
                ImagePiece imagePiece = new ImagePiece();
                //index中存放的是正确的图片摆放顺序
                imagePiece.index = j+i*mColumn;

                int xValue = j*pieceWidth;
                int yValue = i*pieceWidth;
                Bitmap itemBitmap = Bitmap.createBitmap(mBitmap, xValue, yValue, pieceWidth, pieceWidth);
                imagePiece.bitmap = itemBitmap;

                pieces.add(imagePiece);
            }
        }
        return pieces;
    }

    @Override
    public void onClick(View v) {
        // 如果正在执行动画，则屏蔽
        if (isAniming) {
            return;
        }
        if (v == mFirst){
            mFirst.setColorFilter(null);
            mFirst = null;
            return;
        }
        if (mFirst == null){
            mFirst = (ImageView) v;
            mFirst.setColorFilter(Color.parseColor("#55FF0000"));
        }else {
            mFirst.setColorFilter(null);
            mSecond = (ImageView) v;
            exchangeView();
        }
    }

    /**
     * 交换两个ImageView
     */
    private void exchangeView() {
        setUpAnimLayout();
        //添加mFirst的复制
        final ImageView first = new ImageView(getContext());
        first.setImageBitmap(mItemBitmaps.get(getImageIndexByTag(mFirst)).bitmap);
        LayoutParams lpFirst = new LayoutParams(mItemWidth, mItemWidth);
        lpFirst.leftMargin = mFirst.getLeft() - mPadding;
        lpFirst.topMargin = mFirst.getTop() - mPadding;
        mAnimLayout.addView(first, lpFirst);
        //添加mSecond的复制
        ImageView second = new ImageView(getContext());
        second.setImageBitmap(mItemBitmaps.get(getImageIndexByTag(mSecond)).bitmap);
        LayoutParams lpSecond = new LayoutParams(mItemWidth, mItemWidth);
        lpSecond.leftMargin = mSecond.getLeft() - mPadding;
        lpSecond.topMargin = mSecond.getTop() - mPadding;
        mAnimLayout.addView(second, lpSecond);
        //设置动画
        TranslateAnimation animFirst = new TranslateAnimation(0, mSecond.getLeft()-mFirst.getLeft(),
                0, mSecond.getTop()-mFirst.getTop());
        animFirst.setDuration(300);
        animFirst.setFillAfter(true);
        first.startAnimation(animFirst);

        TranslateAnimation animSecond = new TranslateAnimation(0,
                mFirst.getLeft() - mSecond.getLeft(), 0, mFirst.getTop()
                - mSecond.getTop());
        animSecond.setDuration(300);
        animSecond.setFillAfter(true);
        second.startAnimation(animSecond);

        animFirst.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAniming = true;
                mFirst.setVisibility(INVISIBLE);
                mSecond.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String firstTag = (String) mFirst.getTag();
                String secondTag = (String) mSecond.getTag();

                String[] firstIndex = firstTag.split("_");
                String[] secondIndex = secondTag.split("_");

                mFirst.setImageBitmap(mItemBitmaps.get(Integer.parseInt(secondIndex[0])).bitmap);
                mSecond.setImageBitmap(mItemBitmaps.get(Integer.parseInt(firstIndex[0])).bitmap);
                mFirst.setTag(secondTag);
                mSecond.setTag(firstTag);
                mFirst.setVisibility(VISIBLE);
                mSecond.setVisibility(VISIBLE);

                mFirst = mSecond = null;
                mAnimLayout.removeAllViews();
                isAniming = false;
                //检测是否完成
                checkSuccess();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * 创建动画层
     */
    private void setUpAnimLayout() {
        if (mAnimLayout == null) {
            mAnimLayout = new RelativeLayout(getContext());
            addView(mAnimLayout);
        }
    }

    private int getImageIndexByTag(ImageView view) {
        String tag = (String) view.getTag();
        String[] split = tag.split("_");
        return Integer.parseInt(split[0]);

    }

    /**
     * 检测是否完成
     */
    private void checkSuccess() {
        boolean isSuccess = true;
        for (int i = 0; i < mGamePintuItems.length; i++) {
            ImageView itemView = mGamePintuItems[i];
            if (getIndexByTag((String) itemView.getTag()) != i) {
                isSuccess = false;
            }
        }

        if (isSuccess) {
            Toast.makeText(getContext(), "完成!",
                    Toast.LENGTH_LONG).show();
            // nextLevel();
        }
    }

    public void nextLevel() {
        this.removeAllViews();
        mAnimLayout = null;
        mColumn++;
        initBitmap();
        initItems();
    }

    /**
     * 获得图片的真正索引
     * @param tag
     * @return
     */
    private int getIndexByTag(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }
}
