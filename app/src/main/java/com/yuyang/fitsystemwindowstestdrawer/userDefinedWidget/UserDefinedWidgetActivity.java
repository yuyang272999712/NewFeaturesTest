package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout.FoldingLayoutActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SlidingMenuActivity1;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SlidingMenuActivity2;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterWaveEffect.DynamicWave;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect.BlurredImageActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.singleItemScrollView.SingleItemScrollActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.stereoView.StereoViewActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.weChatTabIndicator.WeChatActivity;

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
    private Button guaguakaBtn;
    private Button wechatBtn;
    private Button luckyPanBtn;
    private Button circleMenuBtn;
    private Button progressBarBtn;
    private Button foldingLayoutBtn;
    private Button waterRippleBtn;
    private Button waterWaveBtn;
    private Button sloopVerticalScrollItem;
    private Button parallaxViewPagerBtn;
    private Button colourImageBtn;
    private Button stereoBtn;
    private Button colorPicker;
    private Button blurredImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defind_view_test);

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
        guaguakaBtn = (Button) findViewById(R.id.defind_guaguaka);
        wechatBtn = (Button) findViewById(R.id.defind_wechat);
        luckyPanBtn = (Button) findViewById(R.id.defind_lucky_pan);
        circleMenuBtn = (Button) findViewById(R.id.defind_circle_menu);
        progressBarBtn = (Button) findViewById(R.id.defind_progress_bar);
        foldingLayoutBtn = (Button) findViewById(R.id.defind_folding_layout);
        waterRippleBtn = (Button) findViewById(R.id.defind_water_ripple);
        waterWaveBtn = (Button) findViewById(R.id.defind_water_wave);
        sloopVerticalScrollItem = (Button) findViewById(R.id.defind_sloop_vertical_scroll);
        parallaxViewPagerBtn = (Button) findViewById(R.id.defind_parallax_view_pager);
        colourImageBtn = (Button) findViewById(R.id.defind_colour_image);
        stereoBtn = (Button) findViewById(R.id.defind_stereo);
        colorPicker = (Button) findViewById(R.id.defind_color_picker);
        blurredImage = (Button) findViewById(R.id.defind_blurred_image);
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
        guaguakaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, GuaguakaActivity.class);
                startActivity(intent);
            }
        });
        wechatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, WeChatActivity.class);
                startActivity(intent);
            }
        });
        luckyPanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, LuckyPanActivity.class);
                startActivity(intent);
            }
        });
        circleMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, CircleMenuActivity.class);
                startActivity(intent);
            }
        });
        progressBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ProgressBarActivity.class);
                startActivity(intent);
            }
        });
        foldingLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, FoldingLayoutActivity.class);
                startActivity(intent);
            }
        });
        waterRippleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, WaterRippleActivity.class);
                startActivity(intent);
            }
        });
        waterWaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, WaterWaveActivity.class);
                startActivity(intent);
            }
        });
        sloopVerticalScrollItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, SingleItemScrollActivity.class);
                startActivity(intent);
            }
        });
        parallaxViewPagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ParallaxViewPagerActivity.class);
                startActivity(intent);
            }
        });
        colourImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ColourImageActivity.class);
                startActivity(intent);
            }
        });
        stereoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, StereoViewActivity.class);
                startActivity(intent);
            }
        });
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ColorPickerActivity.class);
                startActivity(intent);
            }
        });
        blurredImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, BlurredImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
