package com.yuyang.fitsystemwindowstestdrawer.recyclerView.stickyHeader;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class StickyHeaderAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    // RecyclerView 的第一个item，肯定是展示StickyLayout的.
    public static final int FIRST_STICKY_VIEW = 1;
    // RecyclerView 除了第一个item以外，要展示StickyLayout的.
    public static final int HAS_STICKY_VIEW = 2;
    // RecyclerView 的不展示StickyLayout的item.
    public static final int NONE_STICKY_VIEW = 3;

    private Context context;
    private List<SimpleBean> beanList = new ArrayList<>();

    public StickyHeaderAdapter(Context context, List<SimpleBean> beanList){
        this.context = context;
        if (beanList != null){
            this.beanList = beanList;
        }
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder holder = CommonViewHolder.getViewHolder(parent, R.layout.item_sticky_recycler_view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View itemView = holder.getmConvertView();
        //表头
        TextView stickyHeader = holder.getViews(R.id.tv_sticky_header_view);
        //item内容
        RelativeLayout rlContentWrapper = holder.getViews(R.id.rl_content_wrapper);
        TextView tvName = holder.getViews(R.id.tv_name);
        TextView tvGender = holder.getViews(R.id.tv_gender);
        TextView tvProfession = holder.getViews(R.id.tv_profession);

        if (position%2 == 0){
            rlContentWrapper.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        }else {
            rlContentWrapper.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        tvName.setText(beanList.get(position).name);
        tvGender.setText(beanList.get(position).gender);
        tvProfession.setText(beanList.get(position).profession);


        if (position == 0){
            stickyHeader.setVisibility(View.VISIBLE);
            stickyHeader.setText(beanList.get(position).sticky);
            // 第一个item的吸顶信息肯定是展示的，并且标记tag为FIRST_STICKY_VIEW
            itemView.setTag(FIRST_STICKY_VIEW);
        }else {
            // 之后的item都会和前一个item要展示的吸顶信息进行比较，不相同就展示，并且标记tag为HAS_STICKY_VIEW
            if(!TextUtils.equals(beanList.get(position).sticky, beanList.get(position-1).sticky)){
                stickyHeader.setVisibility(View.VISIBLE);
                stickyHeader.setText(beanList.get(position).sticky);
                itemView.setTag(HAS_STICKY_VIEW);
            }else {
                // 相同就不展示，并且标记tag为NONE_STICKY_VIEW
                stickyHeader.setVisibility(View.GONE);
                itemView.setTag(NONE_STICKY_VIEW);
            }
        }

        // ContentDescription 用来记录并获取要吸顶展示的信息
        itemView.setContentDescription(beanList.get(position).sticky);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }
}
