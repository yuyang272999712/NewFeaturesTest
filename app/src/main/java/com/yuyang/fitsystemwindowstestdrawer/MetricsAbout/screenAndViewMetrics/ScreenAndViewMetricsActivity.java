package com.yuyang.fitsystemwindowstestdrawer.metricsAbout.screenAndViewMetrics;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/2/1.
 * 界面测量
 *
 * View的 getX 方法返回的是 getLeft＋translationX；
 *       getLeft 方法返回的是 当前View相对于父View的横坐标
 *       setX 方法实际上调用的是 setTranslationX 方法，传入的值是 x-getLeft
 *       setTranslation 方法移动View（相对于getLeft的位置）
 */
public class ScreenAndViewMetricsActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView textView;
    private View parenView;
    private View rectangleView;
    private View screenInfo;
    private View textViewInfo;
    private View rectangleInfo;

    private TextView windowMetrics;
    private TextView windowRealMetrics;
    private TextView windowDensity;
    private TextView systemBarHeight;
    private TextView decorViewTop;

    private TextView textViewRect;
    private TextView textViewGetMethod;
    private TextView textViewInWindow;
    private TextView textViewOnScreen;
    private TextView textViewRect1;
    private TextView textViewGetMethod1;
    private TextView textViewInWindow1;
    private TextView textViewOnScreen1;

    private TextView viewInfo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);

        setToolbar();

        screenInfo = findViewById(R.id.screenInfo);
        textViewInfo = findViewById(R.id.textViewInfo);
        rectangleInfo = findViewById(R.id.rectangleInfo);
        parenView = findViewById(R.id.parenView);
        rectangleView = findViewById(R.id.rectangleView);
        textView = (TextView) findViewById(R.id.metrics_text);

        windowMetrics = (TextView) findViewById(R.id.windowMetrics);
        windowRealMetrics = (TextView) findViewById(R.id.windowRealMetrics);
        windowDensity = (TextView) findViewById(R.id.windowDensity);
        systemBarHeight = (TextView) findViewById(R.id.systemBarHeight);
        decorViewTop = (TextView) findViewById(R.id.decorViewTop);

        textViewRect = (TextView) findViewById(R.id.textViewRect);
        textViewGetMethod = (TextView) findViewById(R.id.textViewGetMethod);
        textViewInWindow = (TextView) findViewById(R.id.textViewInWindow);
        textViewOnScreen = (TextView) findViewById(R.id.textViewOnScreen);
        textViewRect1 = (TextView) findViewById(R.id.textViewRect1);
        textViewGetMethod1 = (TextView) findViewById(R.id.textViewGetMethod1);
        textViewInWindow1 = (TextView) findViewById(R.id.textViewInWindow1);
        textViewOnScreen1 = (TextView) findViewById(R.id.textViewOnScreen1);

        viewInfo4 = (TextView) findViewById(R.id.viewInfo4);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScreenAndViewMetricsActivity.this, "点击有效。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.metrics_toolbar);
        toolbar.setTitle("屏幕相关测量");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //获取屏幕信息
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        windowMetrics.setText("getMetrics()屏幕宽/高：" + metrics.widthPixels + "/" + metrics.heightPixels);
        //android4.2(API17)以后才有getRealMetrics()方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics1 = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics1);
            windowRealMetrics.setText("getRealMetrics()屏幕宽/高："+metrics1.widthPixels+"/"+metrics1.heightPixels);
        }
        windowDensity.setText("density像素密度："+metrics.density+"\n"
                +"densityDpi每英寸的像素点素："+metrics.densityDpi+"\n"
                +"scaledDensity字体缩放比率："+metrics.scaledDensity);
        int statusBarResourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(statusBarResourceId);
        int navigationBarResourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationBarHeight = getResources().getDimensionPixelSize(navigationBarResourceId);
        systemBarHeight.setText("StatusBar高度："+statusBarHeight+"\n"
                +"NavigationBar高度："+navigationBarHeight+"(NavigationBar是4.0以后添加的)");
        //获取app区域信息
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int top = rect.top;
        decorViewTop.setText("内容区域头部Y位置：" + top+"(其实就是差了个StatusBar高度)");


        //获取TextView布局区域宽高等尺寸信息
        Rect rect1 = new Rect();
        textView.getDrawingRect(rect1);
        textViewRect.setText("Text绘制区域,top:"+rect1.top+"；left:"+rect1.left+"；bottom:"+rect1.bottom+"；right:"+rect1.right);
        //View的各种get方法
        int left = textView.getLeft();
        float x = textView.getX();
        int width = textView.getWidth();
        float translationX = textView.getTranslationX();
        int scrollX = textView.getScrollX();
        float rotationX = textView.getRotationX();
        float scaleX = textView.getScaleX();
        textViewGetMethod.setText("TextView各种get方法值：" +"\n 1、getLeft()："+left+"(相对于parentView的值)"
                +"\n 2、getX()："+x+"(相对于parentView的值)"
                +"\n 3、getWidth()："+width
                +"\n 4、getTranslationX()："+translationX+"\n 5、getScrollX()："+scrollX
                +"\n 6、getRotationX()："+rotationX+"\n 7、getScaleX()："+scaleX);
        int[] textViewLocationInWindow = new int[2];
        textView.getLocationInWindow(textViewLocationInWindow);//如果为普通Activity则Y坐标为View左上角到屏幕顶部（此时Window与屏幕一样大）；如果为对话框式的Activity则Y坐标为当前Dialog模式Activity的标题栏顶部到View左上角的距离。
        textViewInWindow.setText("LocationInWindow："+textViewLocationInWindow[0]+"/"+textViewLocationInWindow[1]);
        int[] textViewLocationOnScreen = new int[2];
        textView.getLocationOnScreen(textViewLocationOnScreen);//坐标是相对整个屏幕而言，Y坐标为View左上角到屏幕顶部的距离。
        textViewOnScreen.setText("LocationOnScreen："+textViewLocationOnScreen[0]+"/"+textViewLocationOnScreen[1]);


        Rect localRect = new Rect();
        rectangleView.getLocalVisibleRect(localRect);//获取View自身可见的坐标区域，坐标以自己的左上角为原点(0,0)，另一点为可见区域右下角相对自己(0,0)点的坐标
        Rect globalRect = new Rect();
        rectangleView.getGlobalVisibleRect(globalRect);//获取View在屏幕绝对坐标系中的可视区域，坐标以屏幕左上角为原点(0,0)，另一个点为可见区域右下角相对屏幕原点(0,0)点的坐标。
        Rect focusedRect = new Rect();
        rectangleView.getFocusedRect(focusedRect);
        Rect hitRect = new Rect();
        rectangleView.getHitRect(hitRect);
        viewInfo4.setText("RectangleView信息：\n"
                +"getLocalVisibleRect()：(可见区域的Rect)"+localRect.toString() + ";\n"
                +"getGlobalVisibleRect()：(可见区域相对于父View的位置)"+globalRect.toString() + ";\n"
                +"getFocusedRect()：(与getDrawingRect相同，绘制区域的Rect)"+focusedRect.toString() + ";\n"
                +"getHitRect()："+hitRect.toString());
    }

    private void getCurrentTextViewInfo(){
        Rect rect1 = new Rect();
        textView.getDrawingRect(rect1);
        textViewRect1.setText("Text绘制区域,top:"+rect1.top+"；left:"+rect1.left+"；bottom:"+rect1.bottom+"；right:"+rect1.right);
        //View的各种get方法
        int left = textView.getLeft();
        float x = textView.getX();
        int width = textView.getWidth();
        float translationX = textView.getTranslationX();
        int scrollX = textView.getScrollX();
        float rotationX = textView.getRotationX();
        float scaleX = textView.getScaleX();
        textViewGetMethod1.setText("TextView各种get方法值：" +"\n 1、getLeft()："+left+"(相对于parentView的值)"
                +"\n 2、getX()："+x+"(相对于parentView的值)"
                +"\n 3、getWidth()："+width
                +"\n 4、getTranslationX()："+translationX+"\n 5、getScrollX()："+scrollX
                +"\n 6、getRotationX()："+rotationX+"\n 7、getScaleX()："+scaleX);
        int[] textViewLocationInWindow = new int[2];
        textView.getLocationInWindow(textViewLocationInWindow);
        textViewInWindow1.setText("LocationInWindow："+textViewLocationInWindow[0]+"/"+textViewLocationInWindow[1]);
        int[] textViewLocationOnScreen = new int[2];
        textView.getLocationOnScreen(textViewLocationOnScreen);
        textViewOnScreen1.setText("LocationOnScreen："+textViewLocationOnScreen[0]+"/"+textViewLocationOnScreen[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.addSubMenu(Menu.NONE, 1, Menu.NONE, "屏幕信息");
        menu.addSubMenu(Menu.NONE, 2, Menu.NONE, "TextView信息");
        menu.addSubMenu(Menu.NONE, 3, Menu.NONE, "蓝色长条位置信息");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                screenInfo.setVisibility(View.VISIBLE);
                textViewInfo.setVisibility(View.GONE);
                rectangleInfo.setVisibility(View.GONE);
                break;
            case 2:
                screenInfo.setVisibility(View.GONE);
                textViewInfo.setVisibility(View.VISIBLE);
                rectangleInfo.setVisibility(View.GONE);
                break;
            case 3:
                screenInfo.setVisibility(View.GONE);
                textViewInfo.setVisibility(View.GONE);
                rectangleInfo.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.translationX:
                //!--yuyang 相对于当前位置移动（getLeft＋translationX）
                textView.setTranslationX(200);
                break;
            case R.id.setX:
                //!--yuyang 相对于父View的位置
                textView.setX(200);
                break;
            //!--yuyang scrollTo和scrollBy都是移动内容的
            case R.id.scrollTo:
                textView.scrollTo(50, 50);
                break;
            case R.id.scrollBy:
                textView.scrollBy(10, 10);
                break;
        }
        getCurrentTextViewInfo();
    }
}
