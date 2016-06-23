package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/6/23.
 */
public class FragmentAboutActivity extends Activity {
    private FragmentManager fragmentManager;

    private FragmentTest fragmentTest1;
    private FragmentTest fragmentTest2;
    private FragmentTest fragmentTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_about);

        fragmentManager = getFragmentManager();
    }

    public void add1(View view){
        if (fragmentTest1 == null) {
            fragmentTest1 = FragmentTest.getInstance("第一个Fragment");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_content_1, fragmentTest1);
        transaction.commit();
    }

    public void replace1(View view){
        if (fragmentTest2 == null) {
            fragmentTest2 = FragmentTest.getInstance("第二个Fragment");
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_content_1, fragmentTest2);
        transaction.commit();
    }

}
