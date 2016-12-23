package com.yuyang.fitsystemwindowstestdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.androidL.AndroidLActivity;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.AnimationActivity;
import com.yuyang.fitsystemwindowstestdrawer.customView.CustomActivity;
import com.yuyang.fitsystemwindowstestdrawer.destWidget.DestWidgetActivity;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.GoogleWidgetActivity;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.InternetAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.largeImage.LargeImageTestActivity;
import com.yuyang.fitsystemwindowstestdrawer.listView.ListViewAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.mediaPlayerAbout.MediaAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.metricsAbout.MetricsAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.mvp.activity.UserInfoActivity;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.RecyclerViewDemoActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.SourceCodeAnalyzeActivity;
import com.yuyang.fitsystemwindowstestdrawer.swipeBackActivity.SimpleSwipeBackActivity;
import com.yuyang.fitsystemwindowstestdrawer.tantan.TantanDemoActivity;
import com.yuyang.fitsystemwindowstestdrawer.tantan.cardFlingView.TantanAdapterViewActivity;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.UserDefinedWidgetActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.ViewPagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.ViewPagerIndicatorActivity;
import com.yuyang.fitsystemwindowstestdrawer.webview.WebViewActivity;

/**
 * Created by yuyang on 2016/6/2.
 */
public class MainFragment1 extends Fragment {
    private View mContentView;

    private Button button;
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
        mContentView = inflater.inflate(R.layout.fragment_main_1, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        button = (Button) mContentView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AndroidLActivity.class);
                //TODO yuyang 此为兼容模式
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        button2 = (Button) mContentView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SourceCodeAnalyzeActivity.class);
                startActivity(intent);
            }
        });

        button3 = (Button) mContentView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MetricsAboutActivity.class);
                startActivity(intent);
            }
        });

        button4 = (Button) mContentView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button5 = (Button) mContentView.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserDefinedWidgetActivity.class);
                startActivity(intent);
            }
        });

        button6 = (Button) mContentView.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        button7 = (Button) mContentView.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListViewAboutActivity.class);
                startActivity(intent);
            }
        });

        button8 = (Button) mContentView.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LargeImageTestActivity.class);
                startActivity(intent);
            }
        });

        button9 = (Button) mContentView.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewPagerActivity.class);
                startActivity(intent);
            }
        });

        button10 = (Button) mContentView.findViewById(R.id.button10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewPagerIndicatorActivity.class);
                startActivity(intent);
            }
        });

        button11 = (Button) mContentView.findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AnimationActivity.class);
                startActivity(intent);
            }
        });

        button12 = (Button) mContentView.findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TantanDemoActivity.class);
                startActivity(intent);
            }
        });

        button13 = (Button) mContentView.findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DestWidgetActivity.class);
                startActivity(intent);
            }
        });

        button14 = (Button) mContentView.findViewById(R.id.button14);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                startActivity(intent);
            }
        });

        button15 = (Button) mContentView.findViewById(R.id.button15);
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RecyclerViewDemoActivity.class);
                startActivity(intent);
            }
        });

        button16 = (Button) mContentView.findViewById(R.id.button16);
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SimpleSwipeBackActivity.class);
                startActivity(intent);
            }
        });

        button17 = (Button) mContentView.findViewById(R.id.button17);
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomActivity.class);
                startActivity(intent);
            }
        });

        button18 = (Button) mContentView.findViewById(R.id.button18);
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GoogleWidgetActivity.class);
                startActivity(intent);
            }
        });

        button19 = (Button) mContentView.findViewById(R.id.button19);
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MediaAboutActivity.class);
                startActivity(intent);
            }
        });

        button20 = (Button) mContentView.findViewById(R.id.button20);
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InternetAboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
