package com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 控制输入框位置，防止软键盘与表情键盘切换时输入框位置闪烁
 */
public class EmotionInputDetector {
    private static final String SHARE_PREFERENCE_NAME = "com.yuyang.emotioninputdetector";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";

    private Activity mActivity;
    private InputMethodManager mInputManager;//软键盘管理类
    private SharedPreferences sp;//记录软键盘高度，下次就直接从sp中读取就行了
    private View mEmotionLayout;//表情布局
    private EditText mEditText;//输入框
    private View mContentView;//内容布局view,即除了表情布局或者软键盘布局以外的布局，用于固定输入框的高度，防止跳闪

    private EmotionInputDetector(){}

    /**
     * 外部静态调用
     * @param activity
     * @return
     */
    public static EmotionInputDetector with(Activity activity) {
        EmotionInputDetector emotionInputDetector = new EmotionInputDetector();
        emotionInputDetector.mActivity = activity;
        emotionInputDetector.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        emotionInputDetector.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return emotionInputDetector;
    }

    /**
     * 绑定内容view，此view用于固定bar的高度，防止跳闪
     * @param contentView
     * @return
     */
    public EmotionInputDetector bindToContent(View contentView){
        mContentView = contentView;
        return this;
    }

    /**
     * 设置表情内容布局
     * @param emotionView
     * @return
     */
    public EmotionInputDetector setEmotionView(View emotionView) {
        mEmotionLayout = emotionView;
        return this;
    }

    /**
     * 绑定编辑框事件
     * @param editText
     * @return
     */
    public EmotionInputDetector bindToEditText(EditText editText){
        mEditText = editText;
        mEditText.requestFocus();
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //如果表情布局已经显示
                if (event.getAction() == MotionEvent.ACTION_UP && mEmotionLayout.isShown()){
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                }
                return false;
            }
        });
        return this;
    }

    /**
     * 绑定表情按钮事件
     * @param emotionButton
     * @return
     */
    public EmotionInputDetector bindToEmotionButton(View emotionButton){
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmotionLayout.isShown()) {//点击了表情按钮后，如果表情布局已经显示了
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {//表情布局未显示
                    if (isSoftInputShown()) {//软键盘已经显示
                        lockContentHeight();//锁定内容列表高度
                        showEmotionLayout();//显示表情布局
                        unlockContentHeightDelayed();//解锁内容高度
                    } else {
                        showEmotionLayout();//显示表情布局
                    }
                }
            }
        });
        return this;
    }

    /**
     * 软键盘是否显示
     * @return
     */
    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ////TODO 这里假设内容布局（mContentView）是包裹在LinearLayout中的
                ((LinearLayout.LayoutParams) mContentView.getLayoutParams()).weight = 1.0F;//weight设置为1
            }
        }, 200L);
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        //TODO 这里假设内容布局（mContentView）是包裹在LinearLayout中的
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.height = mContentView.getHeight();
        params.weight = 0.0F;//weight设置为0
    }

    /**
     * 显示表情布局
     */
    private void showEmotionLayout() {
        //TODO 获取软键盘高度－如果从来没打开过软键盘，此时的高度为0
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = sp.getInt(SHARE_PREFERENCE_TAG, 400);
        }
        hideSoftInput();
        mEmotionLayout.getLayoutParams().height = softInputHeight;
        mEmotionLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏表情布局
     * @param showSoftInput 是否显示软件盘
     */
    private void hideEmotionLayout(boolean showSoftInput) {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    /**
     * 显示软键盘
     */
    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 获取软件盘的高度
     * @return
     */
    private int getSupportSoftInputHeight() {
        Rect rect = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //获取屏幕高度
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - rect.bottom;
        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        if (softInputHeight < 0) {
            Log.w("EmotionInputDetector", "Warning: value of softInputHeight is below zero!");
        }
        //存一份到本地sp
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_TAG, softInputHeight).apply();
        }
        return softInputHeight;
    }

    /**
     * 获取底部虚拟导航栏高度（如：华为手机底部的返回／主页／菜单按钮）
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //TODO yuyang 这个方法获取可能不是真实屏幕的高度
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //TODO yuyang 获取当前屏幕的真实高度
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 点击返回键时先隐藏表情布局
     * @return
     */
    public boolean interceptBackPress() {
        if (mEmotionLayout.isShown()) {
            hideEmotionLayout(false);
            return true;
        }
        return false;
    }

}
