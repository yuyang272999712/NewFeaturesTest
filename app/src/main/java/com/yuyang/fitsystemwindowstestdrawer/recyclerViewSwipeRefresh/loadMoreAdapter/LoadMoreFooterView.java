package com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.loadMoreAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

public class LoadMoreFooterView extends FrameLayout{
    private boolean loading;
    private boolean end;
    private OnLoadMoreListener onLoadMoreListener;
    private ProgressBar progressBar;
    private TextView tipsTextView;
    private boolean pause;

    public LoadMoreFooterView(Context context) {
        super(context);
        init();
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, this);
        progressBar = (ProgressBar) findViewById(R.id.footer_progress);
        tipsTextView = (TextView) findViewById(R.id.footer_text);
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isEnd() {
        return end;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setEnd(boolean end) {
        this.end = end;
        if(end){
            progressBar.setVisibility(View.GONE);
            tipsTextView.setText("没有更多了");
        }else{
            progressBar.setVisibility(View.VISIBLE);
            tipsTextView.setText("加载中，请稍后...");
        }
        loading = false;
    }

    public void loadFinished(boolean success){
        if(!success){
            progressBar.setVisibility(View.GONE);
            tipsTextView.setText("Sorry！加载失败，点我重试！");
            setClickable(true);
        }else {
            setClickable(false);
        }
        loading = false;
    }

    public void loadMore(){
        if(end){
            progressBar.setVisibility(View.GONE);
            tipsTextView.setText("没有更多了");
        }else{
            progressBar.setVisibility(View.VISIBLE);
            tipsTextView.setText("加载中，请稍后...");
        }

        if(onLoadMoreListener == null || end || pause || loading){
            return;
        }

        onLoadMoreListener.onLoadMore();
        loading = true;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}