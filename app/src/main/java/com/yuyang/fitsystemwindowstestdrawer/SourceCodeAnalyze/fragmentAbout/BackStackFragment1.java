package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

import java.util.Calendar;

public class BackStackFragment1 extends Fragment {
    private String title = "title";

    public static BackStackFragment1 getInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString("title", text);
        BackStackFragment1 fragmentTest = new BackStackFragment1();
        fragmentTest.setArguments(bundle);
        return fragmentTest;
    }

    public interface FOneBtnClickListener{
        void startNextFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(title, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_test_2, null);
        TextView textView = (TextView) view.findViewById(R.id.fragment_test_text);
        textView.setText(title);
        Button button = (Button) view.findViewById(R.id.fragment_test_2_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof FOneBtnClickListener){
                    ((FOneBtnClickListener)getActivity()).startNextFragment();
                }
            }
        });
        //TODO yuyang Fragment与ActionBar和MenuItem集成
        setHasOptionsMenu(true);
        return view;
    }

    //TODO yuyang Fragment懒加载方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.e(title, "setUserVisibleHint:"+getUserVisibleHint()+";isVisibleToUser:"+isVisibleToUser);
    }

    //TODO yuyang 设置Fragment的菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();//清空Activity的菜单，否则它将与Fragment的菜单同时显示
        inflater.inflate(R.menu.activity_main_drawer, menu);
    }

    //TODO yuyang 设置Fragment的菜单事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_camera:
                ToastUtils.showShort(getActivity(), "点击了camera");
                break;
            case R.id.nav_gallery:
                ToastUtils.showShort(getActivity(), "点击了gallery");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            String evaluate = data.getStringExtra("text");
            Toast.makeText(getActivity(), "接收的Fragment2返回的信息："+evaluate, Toast.LENGTH_SHORT).show();
        }
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
