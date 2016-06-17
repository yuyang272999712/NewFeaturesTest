package com.yuyang.fitsystemwindowstestdrawer.MetricsAbout;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/2/1.
 * 界面测量
 */
public class MetricsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textView;
    private TextView viewInfo;
    private TextView viewInfo1;
    private TextView viewInfo2;
    private TextView viewInfo3;
    private TextView viewInfo4;
    private View viewFrame;
    private View viewLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        toolbar = (Toolbar) findViewById(R.id.metrics_toolbar);
        toolbar.setTitle("开发");
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.metrics_text);

        viewInfo = (TextView) findViewById(R.id.viewInfo);
        viewInfo1 = (TextView) findViewById(R.id.viewInfo1);
        viewInfo2 = (TextView) findViewById(R.id.viewInfo2);
        viewInfo3 = (TextView) findViewById(R.id.viewInfo3);
        viewInfo4 = (TextView) findViewById(R.id.viewInfo4);
        viewFrame = findViewById(R.id.metrics_frame);
        viewLinear = findViewById(R.id.metrics_linear);

        //水平方向滑动
        textView.setTranslationX(10);

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewInfo3.setText("触摸位置（相对触摸view）：" + event.getX() + ";触摸位置（相对屏幕）：" + event.getRawX());
                return false;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewFrame.offsetLeftAndRight(50);
//                viewFrame.offsetTopAndBottom(50);
                viewFrame.scrollTo(50, 50);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //获取屏幕信息
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPx = metrics.widthPixels;
        int heightPx = metrics.heightPixels;
        viewInfo.setText("屏幕宽高：" + widthPx + ";" + heightPx);
        //获取app区域信息
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        viewInfo1.setText("状态栏高度：" + statusBarHeight);
        //获取某个view布局区域宽高等尺寸信息
        Rect rect1 = new Rect();
        getWindow().findViewById(R.id.metrics_text).getDrawingRect(rect1);
        viewInfo2.setText("text位置：" + rect1.top + ";" + rect1.bottom);

        int left = textView.getLeft();
        float left_x = textView.getX();
        int width = textView.getWidth();
        viewInfo3.setText("textview距离parent左侧距离：" + left + ";移动后距离：" + left_x + "。textview宽度：" + width);

        Rect localRect = new Rect();
        viewLinear.getLocalVisibleRect(localRect);
        Rect globalRect = new Rect();
        viewLinear.getGlobalVisibleRect(globalRect);
        int[] locationInWindow = new int[2];
        viewLinear.getLocationInWindow(locationInWindow);
        int[] locationOnScreen = new int[2];
        viewLinear.getLocationOnScreen(locationOnScreen);
        viewInfo4.setText("viewLinear信息：" + localRect.toString() + ";" + globalRect.toString()
                            + ";" + locationOnScreen[0] + "/" + locationOnScreen[1] + ";" + viewLinear.getBottom()
                            + ";" + viewLinear.getHeight());
    }

}
