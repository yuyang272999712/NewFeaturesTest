package com.yuyang.network.volley.baseView;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yuyang.network.R;

/**
 * 加载等待框Dialog
 */
public class LoadingDialogFragment extends DialogFragment {
    private String mMsg = "加载中，请稍后";

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_loading, null);
        TextView title = (TextView) view
                .findViewById(R.id.id_dialog_loading_msg);
        title.setText(mMsg);
        Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyle);
        dialog.setContentView(view);
        return dialog;
    }
}
