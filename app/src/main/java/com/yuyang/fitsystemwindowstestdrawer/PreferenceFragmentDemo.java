package com.yuyang.fitsystemwindowstestdrawer;

import android.os.Bundle;
import android.preference.PreferenceFragment;

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
