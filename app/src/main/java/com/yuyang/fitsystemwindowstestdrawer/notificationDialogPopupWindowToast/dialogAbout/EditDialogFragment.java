package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.dialogAbout;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 使用DialogFragment至少需要实现onCreateView或者onCreateDialog方法。
 * onCreateView即使用定义的xml布局文件展示Dialog。onCreateDialog即利用AlertDialog或者Dialog创建出Dialog。
 */
public class EditDialogFragment extends DialogFragment {
    private Button sub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO yuyang 去掉DialogFragment中的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_fragment_layout, container, false);
        sub = (Button) view.findViewById(R.id.dialog_fragment_sub);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
