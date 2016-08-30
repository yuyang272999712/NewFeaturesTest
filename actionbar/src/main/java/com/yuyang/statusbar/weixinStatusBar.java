package com.yuyang.statusbar;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuyang.R;

/**
 * 微信漂流瓶效果
 */
public class WeixinStatusBar extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView;
    private LinearLayout linearLayout;

    private boolean statusBar = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_statusbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        setSupportActionBar(toolbar);
        toolbar.setTitle("微信漂流瓶效果");

        final View decorView = getWindow().getDecorView();
        final int[] flag = {View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE};
        decorView.setSystemUiVisibility(flag[0]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusBar) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        int[] locationInWindow = new int[2];
                        toolbar.getLocationOnScreen(locationInWindow);
                        Log.e("位置信息", locationInWindow[0] + ";" + locationInWindow[1]+",高："+toolbar.getHeight());
                        toolbar.animate().y(-toolbar.getHeight()).setListener(new Animator.AnimatorListener() {
                            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                statusBar = false;
                                flag[0] |= View.SYSTEM_UI_FLAG_FULLSCREEN;
                                decorView.setSystemUiVisibility(flag[0]);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        int[] locationInWindow = new int[2];
                        toolbar.getLocationOnScreen(locationInWindow);
                        Log.e("位置信息", locationInWindow[0] + ";" + locationInWindow[1]);
                        toolbar.animate().y(toolbar.getHeight()).setListener(new Animator.AnimatorListener() {
                            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                            @Override
                            public void onAnimationStart(Animator animation) {
                                flag[0] &= ~View.SYSTEM_UI_FLAG_FULLSCREEN;
                                decorView.setSystemUiVisibility(flag[0]);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                statusBar = true;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                    }
                }
            }
        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.e("WeinxinStatusBar", "onSystemUiVisibilityChange");
            }
        });
    }


}
