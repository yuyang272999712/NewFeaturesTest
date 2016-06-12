package com.yuyang.fitsystemwindowstestdrawer.effect360AppIntroduce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * ScrollView的情况
 */
public class ScrollViewFragment extends Fragment {
    TextView titleView;
    String Title = "title";

    ScrollView scrollView;

    public static ScrollViewFragment newInstance(String title) {
        ScrollViewFragment tabFragment = new ScrollViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Title = bundle.getString("title", Title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll_view, container, false);
        titleView = (TextView) view.findViewById(R.id.scroll_fragment_title);
        titleView.setText(Title);
        scrollView = (ScrollView) view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        return view;
    }

}
