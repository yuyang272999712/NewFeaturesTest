package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/6/13.
 */
public class CustomStateDrawableActivity extends ListActivity {
    private Message[] messages = new Message[] {
            new Message("Gas bill overdue", true),
            new Message("Congratulations, you've won!", true),
            new Message("I love you!", false),
            new Message("Please reply!", false),
            new Message("You ignoring me?", false),
            new Message("Not heard from you", false),
            new Message("Electricity bill", true),
            new Message("Gas bill", true), new Message("Holiday plans", false),
            new Message("Marketing stuff", false), };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setAdapter(new ArrayAdapter<Message>(this, -1, messages){
            private LayoutInflater mInflater = LayoutInflater.from(getContext());
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.item_message_state_list,
                            parent, false);
                }
                MyStateRelativeLayout layout = (MyStateRelativeLayout) convertView;
                TextView textView = (TextView) layout.findViewById(R.id.message_state_text);
                textView.setText(messages[position].message);
                layout.setMessageReaded(messages[position].readed);
                return convertView;
            }
        });
    }
}
