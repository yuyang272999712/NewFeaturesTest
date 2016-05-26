package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.List;

/**
 * Created by yuyang on 16/5/3.
 */
public class MyAdapter2 extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> mDatas;

    public MyAdapter2(Context context, List<String> datas)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder = null;
        if (convertView == null)
        {
            holder = new ViewHolder();
//          convertView = mInflater.inflate(R.layout.layout_button_for_inflate, null);
            convertView = mInflater.inflate(R.layout.layout_button_for_inflate, parent ,false);
//          convertView = mInflater.inflate(R.layout.layout_button_for_inflate, parent ,true);
            holder.mBtn = (Button) convertView.findViewById(R.id.layout_item_btn);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mBtn.setText(mDatas.get(position));

        return convertView;
    }

    private final class ViewHolder
    {
        Button mBtn;
    }
}
