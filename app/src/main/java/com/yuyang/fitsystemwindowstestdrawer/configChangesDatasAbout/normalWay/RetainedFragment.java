package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.normalWay;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * 用于保存数据的Fragment
 * //TODO yuyang 这样Activity中被标识保持的fragments不会被销毁。
 * 注：一定要在onCreate调用setRetainInstance(true)
 */
public class RetainedFragment extends Fragment {
    //Activity的onSaveInstanceState()不适合来携带大量数据
    private Bitmap data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Bitmap getData() {
        return data;
    }

    public void setData(Bitmap data) {
        this.data = data;
    }
}
