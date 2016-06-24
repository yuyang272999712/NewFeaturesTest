package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

import java.util.Calendar;

public class BackStackFragment2 extends Fragment {
    private String title = "title";
    private FTwoBtnClickListener fTwoBtnClickListener ;

    public interface FTwoBtnClickListener {
        void onFTwoBtnClick();
    }

    //设置回调接口
    public void setfTwoBtnClickListener(FTwoBtnClickListener fTwoBtnClickListener) {
        this.fTwoBtnClickListener = fTwoBtnClickListener;
    }

    public static BackStackFragment2 getInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString("title", text);
        BackStackFragment2 fragmentTest = new BackStackFragment2();
        fragmentTest.setArguments(bundle);
        return fragmentTest;
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
                if(fTwoBtnClickListener != null){
                    fTwoBtnClickListener.onFTwoBtnClick();
                }
            }
        });
        //TODO yuyang Fragment与ActionBar和MenuItem集成
        setHasOptionsMenu(true);
        return view;
    }

    //TODO yuyang 设置Fragment的菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.back_stack_fragment_menu, menu);
    }

    //TODO yuyang 设置Fragment的菜单事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_share:
                ToastUtils.showShort(getActivity(), "点击了share");
                //TODO yuyang 判断是否设置了targetFragment,如果设置了，意味我们要返回一些数据到targetFragment。我们创建intent封装好需要传递数据，最后手动调用onActivityResult进行返回数据~~
                if (getTargetFragment() != null) {
                    Intent intent = new Intent();
                    intent.putExtra("text", "Fragment返回值");
                    getTargetFragment().onActivityResult(100, Activity.RESULT_OK, intent);
                }
                break;
            case R.id.nav_send:
                ToastUtils.showShort(getActivity(), "点击了send");
                break;
        }
        return super.onOptionsItemSelected(item);
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
