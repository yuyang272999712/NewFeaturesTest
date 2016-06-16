package com.yuyang.fitsystemwindowstestdrawer.homeDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.List;

/**
 * FragmentManager+Fragment实现
 * 主要利用了Fragment在主内容界面对Fragment的add,hide等事务操作。
 */
public class FragmentManagerActivity extends AppCompatActivity {
    private RadioGroup mRadioGroup;
    private RadioButton tab0;
    private RadioButton tab1;
    private RadioButton tab2;
    private RadioButton tab3;

    private FragmentTab0 fragment0;
    private FragmentTab1 fragment1;
    private FragmentTab2 fragment2;
    private FragmentTab3 fragment3;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_manager);
        fragmentManager = getSupportFragmentManager();

        initViews();
        setAction();
        tab0.setChecked(true);
    }

    private void setAction() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.home_tab_0:
                        changeFragment(fragment0);
                        break;
                    case R.id.home_tab_1:
                        changeFragment(fragment1);
                        break;
                    case R.id.home_tab_2:
                        changeFragment(fragment2);
                        break;
                    case R.id.home_tab_3:
                        changeFragment(fragment3);
                        break;
                }
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(fragmentList != null) {
            for (Fragment fragment1 : fragmentList) {
                transaction.hide(fragment1);
            }
        }
        //如果fragment已经添加了
        if(fragment.isAdded()){
            transaction.show(fragment);
        }else {
            transaction.add(R.id.home_content_layout,fragment);
        }
        transaction.commit();
    }

    private void initViews() {
        mRadioGroup = (RadioGroup) findViewById(R.id.home_tab_group);
        tab0 = (RadioButton) findViewById(R.id.home_tab_0);
        tab1 = (RadioButton) findViewById(R.id.home_tab_1);
        tab2 = (RadioButton) findViewById(R.id.home_tab_2);
        tab3 = (RadioButton) findViewById(R.id.home_tab_3);

        fragment0 = new FragmentTab0();
        fragment1 = new FragmentTab1();
        fragment2 = new FragmentTab2();
        fragment3 = new FragmentTab3();
    }
}
