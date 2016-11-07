package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.quickSearchSideBar;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.List;

/**
 * 城市选择列表
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private ArrayMap<String, List<City>> datas;
    private LayoutInflater mInflater;

    public ExpandableAdapter(Context context, ArrayMap<String, List<City>> datas) {
        this.datas = datas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return datas != null?datas.size():0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datas.valueAt(groupPosition).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return datas.keyAt(groupPosition);
    }

    @Override
    public City getChild(int groupPosition, int childPosition) {
        return datas.valueAt(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_just_text, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.id_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setBackgroundColor(Color.BLUE);
        holder.textView.setTextColor(Color.WHITE);
        holder.textView.setText(getGroup(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_just_text, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.id_info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(getChild(groupPosition, childPosition).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean containsKey(String s) {
        return datas.containsKey(s);
    }

    public int getKeyPosition(String s) {
        return datas.indexOfKey(s);
    }

    private class ViewHolder {
        private TextView textView;
    }
}
