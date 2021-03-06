package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.ArcMenu.ArcMenuAndZoomImageActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.CircleMenu.simple.CircleMenuActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.DefinedKeyBoard.DefinedKeyBoardActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.FoldingLayout.FoldingLayoutActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SlidingMenuActivity1;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.QQSlidingMenu.SlidingMenuActivity2;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterRippleEffect.WaterRippleActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.WaterWaveEffect.WaterWaveActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.blurredEffect.BlurredImageActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.circleImageView.CircleImageActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.clipImage.ClipImageActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.clockView.MyClockActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.clockViewMIUI.MiuiClockActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.colorPicker.ColorPickerActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.dragHelperViews.ViewDragHelperActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.flowLayout.FlowLayoutActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.galleryEffect.HuaLangActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.gestureLock.GestureLockActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.guaguaka.GuaguakaActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.parallaxRecyclerImageView.sample.ParallaxRecyclerImageActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.pintu.GamePintuActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.polygonsStatisticsView.RadarChartActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.progressBars.ProgressBarActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.quickSearchSideBar.CitySelectActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.singleItemScrollView.SingleItemScrollActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.simple.DemoChangeBigHeadActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.stereoView.StereoViewActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.verticalLinearLayout.VerticalLinearLayoutActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.zhuanpan.LuckyPanActivity;

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
    private Button luckyPanBtn;
    private Button circleMenuBtn;
    private Button progressBarBtn;
    private Button foldingLayoutBtn;
    private Button waterRippleBtn;
    private Button waterWaveBtn;
    private Button sloopVerticalScrollItem;
    private Button colourImageBtn;
    private Button stereoBtn;
    private Button colorPicker;
    private Button blurredImage;
    private Button clockBtn;
    private Button miuiClockBtn;
    private Button letterBar;
    private Button radarChartBtn;
    private Button parallaxRecyclerBtn;
    private Button bigHeadBtn;
    private Button definedKeyboard;

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
        luckyPanBtn = (Button) findViewById(R.id.defind_lucky_pan);
        circleMenuBtn = (Button) findViewById(R.id.defind_circle_menu);
        progressBarBtn = (Button) findViewById(R.id.defind_progress_bar);
        foldingLayoutBtn = (Button) findViewById(R.id.defind_folding_layout);
        waterRippleBtn = (Button) findViewById(R.id.defind_water_ripple);
        waterWaveBtn = (Button) findViewById(R.id.defind_water_wave);
        sloopVerticalScrollItem = (Button) findViewById(R.id.defind_sloop_vertical_scroll);
        colourImageBtn = (Button) findViewById(R.id.defind_colour_image);
        stereoBtn = (Button) findViewById(R.id.defind_stereo);
        colorPicker = (Button) findViewById(R.id.defind_color_picker);
        blurredImage = (Button) findViewById(R.id.defind_blurred_image);
        clockBtn = (Button) findViewById(R.id.defind_clock);
        miuiClockBtn = (Button) findViewById(R.id.defind_miui_clock);
        letterBar = (Button) findViewById(R.id.defind_letter_bar);
        radarChartBtn = (Button) findViewById(R.id.defind_radar_chart);
        parallaxRecyclerBtn = (Button) findViewById(R.id.defind_parallax_recycler);
        bigHeadBtn = (Button) findViewById(R.id.defind_big_head_list);
        definedKeyboard = (Button) findViewById(R.id.defind_keyboard);
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
        clockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, MyClockActivity.class);
                startActivity(intent);
            }
        });
        miuiClockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, MiuiClockActivity.class);
                startActivity(intent);
            }
        });
        letterBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, CitySelectActivity.class);
                startActivity(intent);
            }
        });
        radarChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, RadarChartActivity.class);
                startActivity(intent);
            }
        });
        parallaxRecyclerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, ParallaxRecyclerImageActivity.class);
                startActivity(intent);
            }
        });
        bigHeadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, DemoChangeBigHeadActivity.class);
                startActivity(intent);
            }
        });
        definedKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDefinedWidgetActivity.this, DefinedKeyBoardActivity.class);
                startActivity(intent);
            }
        });
    }
}
