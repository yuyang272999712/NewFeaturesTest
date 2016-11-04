package com.yuyang.statusbar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.R;

/**
 * 微信漂流瓶效果
 *
 * Responding to UI Visibility Changes
 * 通过设置监听来动态改变界面元素的状态
 *  ZHU yuyang View.setOnSystemUiVisibilityChangeListener()
 */
public class SystemBars7Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView;
    private ActionBar actionBar;
    private View decorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_statusbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("微信漂流瓶效果");
        imageView = (ImageView) findViewById(R.id.imageView);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        decorView = getWindow().getDecorView();
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//绘制到状态栏
        decorView.setSystemUiVisibility(flag);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionBar.isShowing()) {
                    hideActionBar();
                }else {
                    showActionBar();
                }
            }
        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.e("WeinxinStatusBar", "onSystemUiVisibilityChange");
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {//状态栏可见

                } else {//状态栏不可见

                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private void hideActionBar(){
        toolbar.animate().y(-toolbar.getHeight()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                actionBar.hide();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void showActionBar(){
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        decorView.setFitsSystemWindows(true);
        actionBar.show();
        ObjectAnimator animator = ObjectAnimator.ofFloat(toolbar, "y", 0+getStatusBarHeight());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
