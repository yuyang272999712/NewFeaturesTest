package com.yuyang.fitsystemwindowstestdrawer.homeDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/6/16.
 */
public class FragmentTab1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_01, container, false);
        return view;
    }
}
