package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

import java.util.Calendar;

/**
 * 打印Fragment生命周期变化
 */
public class FragmentTest extends Fragment {
    private String title = "title";

    public static FragmentTest getInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString("title", text);
        FragmentTest fragmentTest = new FragmentTest();
        fragmentTest.setArguments(bundle);
        return fragmentTest;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        title = getArguments().getString("title")+calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND);
        View view = inflater.inflate(R.layout.fragment_test, null);
        TextView textView = (TextView) view.findViewById(R.id.fragment_test_text);
        textView.setText(title);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e(title, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.e(title, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e(title, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(title, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e(title, "onDetach");
    }
}
