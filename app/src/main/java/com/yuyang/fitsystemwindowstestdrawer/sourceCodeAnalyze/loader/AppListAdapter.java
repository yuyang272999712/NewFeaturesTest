package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader.MyLoader.AppEntry;

import java.util.List;

/**
 * Created by yuyang on 16/7/28.
 */
public class AppListAdapter extends ArrayAdapter<AppEntry> {
    private final LayoutInflater mInflater;

    public AppListAdapter(Context context) {
        super(context, R.layout.item_icon_text);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<AppEntry> data) {
        clear();
        if (data != null) {
            addAll(data);
        }
    }

    /**
     * Populate new items in the list.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_icon_text, parent, false);
        } else {
            view = convertView;
        }

        AppEntry item = getItem(position);
        ((ImageView)view.findViewById(R.id.icon)).setImageDrawable(item.getIcon());
        ((TextView)view.findViewById(R.id.text)).setText(item.getLabel());

        return view;
    }
}
