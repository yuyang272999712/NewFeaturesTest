package com.yuyang.fitsystemwindowstestdrawer.recyclerView.removeItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 16/7/1.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Integer> lists = new ArrayList<>();


    public RecyclerAdapter(Context context, List<Integer> lists) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.lists = lists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_recycler_remove_item, null, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.txt.setText("第" + String.valueOf(lists.get(position)) + "项");
        viewHolder.layout.scrollTo(0, 0);
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        } else {
            return 0;
        }
    }

    public void removeRecycle(int position) {
        lists.remove(position);
        notifyDataSetChanged();
        if (lists.size() == 0) {
            Toast.makeText(context, "已经没数据啦", Toast.LENGTH_SHORT).show();
        }
    }
}
