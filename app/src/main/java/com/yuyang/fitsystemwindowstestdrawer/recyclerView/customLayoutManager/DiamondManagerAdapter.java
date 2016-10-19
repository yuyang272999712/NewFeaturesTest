package com.yuyang.fitsystemwindowstestdrawer.recyclerView.customLayoutManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

/**
 * 菱形的自定义布局
 */

public class DiamondManagerAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private int totalSize;
    private Context mContext;

    public DiamondManagerAdapter(Context context, int totalSize){
        this.mContext = context;
        this.totalSize = totalSize;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("视图更新", "新生成了一个ItemView");
        return CommonViewHolder.getViewHolder(parent, R.layout.item_custom_layout_manager);
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, int position) {
        DiamondView diamondView = holder.getViews(R.id.item);
        final TextView textView = holder.getViews(R.id.text);
        diamondView.setCardColor(getColor(position));
        textView.setText("菜单"+position);
        diamondView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("视图更新", "使用了回收的ItemView:"+position);
    }

    @Override
    public int getItemCount() {
        return totalSize;
    }

    private static final int[] COLORS = {0xff00FFFF, 0xffDEB887, 0xff5F9EA0,
            0xff7FFF00, 0xff6495ED, 0xffDC143C,
            0xff008B8B, 0xff006400, 0xff2F4F4F,
            0xffFF69B4, 0xffFF00FF, 0xffCD5C5C,
            0xff90EE90, 0xff87CEFA, 0xff800000};


    private int getColor(int position) {
        return COLORS[position%COLORS.length];
    }

}
