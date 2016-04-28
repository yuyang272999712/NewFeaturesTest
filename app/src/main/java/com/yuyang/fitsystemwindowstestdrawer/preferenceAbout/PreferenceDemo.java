package com.yuyang.fitsystemwindowstestdrawer.preferenceAbout;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.List;

/**
 * Created by yuyang on 16/1/18.
 */
public class PreferenceDemo extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.preference_header, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return super.isValidFragment(fragmentName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            //当小于3.0版本时推荐重写该方法加载xml，当然大于时也可以用，只是不推荐而已
            addPreferencesFromResource(R.xml.preference);
        }
    }

}
