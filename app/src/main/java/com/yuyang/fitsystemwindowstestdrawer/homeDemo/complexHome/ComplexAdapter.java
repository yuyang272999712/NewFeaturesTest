package com.yuyang.fitsystemwindowstestdrawer.homeDemo.complexHome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.loopViewPager.AutoLoopViewPager;
import com.yuyang.fitsystemwindowstestdrawer.viewPagerIndicator.bannerIndicator.CircleIndicator;

/**
 * 复杂首页布局
 */

public class ComplexAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private static final int ITEM_TYPE_VIEW_PAGE           = 0x000;
    private static final int ITEM_TYPE_LINEAR_VIEW         = 0x001;
    private static final int ITEM_TYPE_ONE_SPAN            = 0x002;
    private static final int ITEM_TYPE_TWO_SPAN            = 0x003;
    private static final int ITEM_TYPE_THREE_SPAN          = 0x004;
    private static final int ITEM_TYPE_FOUR_SPAN           = 0x005;
    private static final int ITEM_TYPE_FIVE_SPAN           = 0x006;
    private static final int ITEM_TYPE_RECYCLER_HORIZONTAL = 0x007;
    private static final int ITEM_TYPE_LABEL               = 0x008;
    private static final int ITEM_TYPE_FOOTER              = 0xfff;

    private Context mContext;
    private boolean initViewPager = false;
    private boolean initRecycler = false;

    public ComplexAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE_VIEW_PAGE:
                return CommonViewHolder.getViewHolder(parent, R.layout.layout_complex_view_pager);
            case ITEM_TYPE_LINEAR_VIEW:
                return CommonViewHolder.getViewHolder(parent, R.layout.layout_linear_view);
            case ITEM_TYPE_ONE_SPAN:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_image_text_small);
            case ITEM_TYPE_THREE_SPAN:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_image_text_big);
            case ITEM_TYPE_TWO_SPAN:
            case ITEM_TYPE_FOUR_SPAN:
            case ITEM_TYPE_FIVE_SPAN:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_image_text);
            case ITEM_TYPE_RECYCLER_HORIZONTAL:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_recycler_view);
            case ITEM_TYPE_LABEL:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_text_label);
            case ITEM_TYPE_FOOTER:
                return CommonViewHolder.getViewHolder(parent, R.layout.item_footer);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM_TYPE_VIEW_PAGE:
                if (!initViewPager) {
                    AutoLoopViewPager viewPager = holder.getViews(R.id.loop_view_pager_auto);
                    CircleIndicator indicator = holder.getViews(R.id.indicator_default);
                    PagerAdapter mAdapter = new BannerPagerAdapter();
                    viewPager.setAdapter(mAdapter);
                    indicator.setViewPager(viewPager);
                    initViewPager = true;
                }
                break;
            case ITEM_TYPE_RECYCLER_HORIZONTAL:
                if (!initRecycler) {
                    RecyclerView recyclerView = holder.getViews(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.addItemDecoration(new LinearHorizontalItemDecor());
                    recyclerView.setAdapter(new RecyclerAdapter(mContext));
                    initRecycler = true;
                }
                break;
            case ITEM_TYPE_LABEL:
                TextView textView = holder.getViews(R.id.label);
                textView.setText("区块"+position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return ITEM_TYPE_VIEW_PAGE;
            case 1:
                return ITEM_TYPE_LINEAR_VIEW;
            case 2:
            case 5:
            case 9:
            case 17:
            case 22:
                return ITEM_TYPE_LABEL;
            case 3:
            case 4:
                return ITEM_TYPE_THREE_SPAN;
            case 6:
            case 7:
            case 8:
                return ITEM_TYPE_TWO_SPAN;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return ITEM_TYPE_ONE_SPAN;
            case 16:
                return ITEM_TYPE_RECYCLER_HORIZONTAL;
            case 18:
            case 20:
                return ITEM_TYPE_FOUR_SPAN;
            case 19:
            case 21:
                return ITEM_TYPE_TWO_SPAN;
            case 34:
                return ITEM_TYPE_FOOTER;
            default:
                return ITEM_TYPE_THREE_SPAN;
        }
    }

    @Override
    public int getItemCount() {
        return 35;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case ITEM_TYPE_VIEW_PAGE:
                        case ITEM_TYPE_LINEAR_VIEW:
                        case ITEM_TYPE_FOOTER:
                        case ITEM_TYPE_RECYCLER_HORIZONTAL:
                        case ITEM_TYPE_LABEL:
                            return 6;
                        case ITEM_TYPE_FIVE_SPAN:
                            return 5;
                        case ITEM_TYPE_FOUR_SPAN:
                            return 4;
                        case ITEM_TYPE_THREE_SPAN:
                            return 3;
                        case ITEM_TYPE_TWO_SPAN:
                            return 2;
                        case ITEM_TYPE_ONE_SPAN:
                            return 1;
                        default:
                            return 1;
                    }
                }
            });
        }
    }
}
