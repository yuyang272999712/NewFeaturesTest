package com.yuyang.fitsystemwindowstestdrawer.preferenceAbout;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/1/20.
 */
public class PreferenceFragmentDemo extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
