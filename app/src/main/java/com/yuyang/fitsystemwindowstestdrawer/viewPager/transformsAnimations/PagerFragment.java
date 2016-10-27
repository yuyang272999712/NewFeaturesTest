package com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yuyang on 16/6/3.
 */
public class PagerFragment extends Fragment {
    public static final String BUNDLE_TITLE = "title";
    public static final String BG_COLOR = "color";
    private String mTitle = "DefaultValue";
    private int mColor = Color.BLUE;

    public static PagerFragment getInstance(String title, int color){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putInt(BG_COLOR, color);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTitle = getArguments().getString(BUNDLE_TITLE, mTitle);
        mColor = getArguments().getInt(BG_COLOR, mColor);
        TextView textView = new TextView(getContext());
        textView.setText(mTitle);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setBackgroundColor(mColor);
        return textView;
    }
}
