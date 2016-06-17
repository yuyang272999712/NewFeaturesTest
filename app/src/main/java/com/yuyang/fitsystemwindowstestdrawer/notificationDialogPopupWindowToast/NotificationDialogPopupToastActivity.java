package com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.dialogAbout.DialogActivity;
import com.yuyang.fitsystemwindowstestdrawer.notificationDialogPopupWindowToast.notificationAbout.NotificationActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 各种通知方式：Notification、Dialog、PopupWindow、Toast
 */
public class NotificationDialogPopupToastActivity extends ListActivity {
    private List<String> items = Arrays.asList("Notification-通知相关","Dialog-对话框相关");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public String getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = LayoutInflater.from(NotificationDialogPopupToastActivity.this)
                            .inflate(R.layout.item_just_text, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.id_info);
                textView.setText(items.get(position));
                return convertView;
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = null;
        switch (position){
            case 0:
                intent = new Intent(this, NotificationActivity.class);
                break;
            case 1:
                intent = new Intent(this, DialogActivity.class);
                break;
        }
        startActivity(intent);
    }
}
