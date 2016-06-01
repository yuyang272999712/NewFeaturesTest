package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SlidingMenuActivity1;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SlidingMenuActivity2;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.flowLayout.FlowLayout;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.flowLayout.TagAdapter;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.flowLayout.TagFlowLayout;

import java.util.Set;

/**
 * 自定义ViewDragHelper的布局
 * 自定义FlowLayout的流失布局
 * 自定义带清楚按钮的EditText的View
 * CountDownTimer定时工具的使用
 */
public class UserDefinedWidgetActivity extends AppCompatActivity {
    private Button flowLayoutBtn;
    private Button nextButton;
    private Button verticalButton;
    private Button gestureLockButton;
    private Button gotoArcMenuButton;
    private Button hualangBtn;
    private Button qqSlidingBtn;
    private Button qqDoubleSlidingBtn;
    private Button clipImgBtn;
    private Button gamePintuBtn;
    private Button circleImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defind_view_test);

        findViews();
        initDatas();
        initAction();
    }

    private void findViews() {
        flowLayoutBtn = (Button) findViewById(R.id.defind_flow_layout);
        nextButton = (Button) findViewById(R.id.defind_next_drag_helper);
        verticalButton = (Button) findViewById(R.id.defind_vertical_layout);
        gestureLockButton = (Button) findViewById(R.id.defind_gesture_lock);
        gotoArcMenuButton = (Button) findViewById(R.id.defind_goto_ArcMenu);
        hualangBtn = (Button) findViewById(R.id.defind_hualang_view);
        qqSlidingBtn = (Button) findViewById(R.id.defind_qq_sliding);
        qqDoubleSlidingBtn = (Button) findViewById(R.id.defind_qq_double_sliding);
        clipImgBtn = (Button) findViewById(R.id.defind_clip_image);
        gamePintuBtn = (Button) findViewById(R.id.defind_game_pintu);
        circleImgBtn = (Button) findViewById(R.id.defind_circle_img);
    }

    private void initDatas() {}

    private void initAction() {
        flowLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, FlowLayoutActivity.class);
                startActivity(intent);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ViewDragHelperActivity.class);
                startActivity(intent);
            }
        });
        verticalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, VerticalLinearLayoutActivity.class);
                startActivity(intent);
            }
        });
        gestureLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, GestureLockActivity.class);
                startActivity(intent);
            }
        });
        gotoArcMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ArcMenuAndZoomImageActivity.class);
                startActivity(intent);
            }
        });
        hualangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, HuaLangActivity.class);
                startActivity(intent);
            }
        });
        qqSlidingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, SlidingMenuActivity1.class);
                startActivity(intent);
            }
        });
        qqDoubleSlidingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, SlidingMenuActivity2.class);
                startActivity(intent);
            }
        });
        clipImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ClipImageActivity.class);
                startActivity(intent);
            }
        });
        gamePintuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, GamePintuActivity.class);
                startActivity(intent);
            }
        });
        circleImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, CircleImageActivity.class);
                startActivity(intent);
            }
        });

    }
}
