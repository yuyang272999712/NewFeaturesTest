package com.yuyang.fitsystemwindowstestdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.MetricsAbout.MetricsActivity;
import com.yuyang.fitsystemwindowstestdrawer.androidLAnimationAbout.OptionsCompatActivity;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.AnimationActivity;
import com.yuyang.fitsystemwindowstestdrawer.cardViewPager.CardViewPagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.coordinatorLayoutAbout.MaterialDesignActivity;
import com.yuyang.fitsystemwindowstestdrawer.customView.CustomActivity;
import com.yuyang.fitsystemwindowstestdrawer.destWidget.DestWidgetActivity;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.GoogleWidgetActivity;
import com.yuyang.fitsystemwindowstestdrawer.horizontalFling.HorizontalFlingActivity;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.manager.ConnectivityStateActivity;
import com.yuyang.fitsystemwindowstestdrawer.largeImage.LargeImageTestActivity;
import com.yuyang.fitsystemwindowstestdrawer.listViewHolder.ListTestActivity;
import com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout.MediaAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.mvp.activity.UserInfoMvpTest;
import com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.SwipeRefreshActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.SourceCodeAnalyzeActivity;
import com.yuyang.fitsystemwindowstestdrawer.swipeBackActivity.SimpleSwipeBackActivity;
import com.yuyang.fitsystemwindowstestdrawer.tantan.TantanActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.UserDefinedWidgetActivity;
import com.yuyang.fitsystemwindowstestdrawer.webview.WebViewActivity;

/**
 * Created by yuyang on 2016/6/2.
 */
public class MainFragment2 extends Fragment {
    private View mContentView;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;
    private Button button15;
    private Button button16;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button20;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_main_2, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        button1 = (Button) mContentView.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OptionsCompatActivity.class);
                startActivity(intent);
            }
        });

    }
}
