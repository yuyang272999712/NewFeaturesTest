package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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
public class ManagerFragmentTest extends Fragment {
    private String title = "title";

    public static ManagerFragmentTest getInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString("title", text);
        ManagerFragmentTest fragmentTest = new ManagerFragmentTest();
        fragmentTest.setArguments(bundle);
        return fragmentTest;
    }

    @Override
    //TODO yuyang 坑爹啊，这个attach方法只有在6.0及以上版本才会被调用
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.e(title, "onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Calendar calendar = Calendar.getInstance();
        title = getArguments().getString("title")+calendar.get(Calendar.MINUTE)+":"+ calendar.get(Calendar.SECOND);
        LogUtils.e(title, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(title, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(title, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_test, null);
        TextView textView = (TextView) view.findViewById(R.id.fragment_test_text);
        textView.setText(title);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e(title, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e(title, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e(title, "onResume");
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.e(title, "onSaveInstanceState");
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
