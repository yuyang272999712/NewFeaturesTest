package com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipRefresh.base;

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
            tipsTextView.setText("没有您的包裹了！");
        }else{
            progressBar.setVisibility(View.VISIBLE);
            tipsTextView.setText("别着急，您的包裹马上就来！");
        }
        loading = false;
    }

    public void loadFinished(boolean success){
        if(!success){
            progressBar.setVisibility(View.GONE);
            tipsTextView.setText("Sorry！您的包裹运送失败！");
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFooterView();
                }
            });
        }
        loading = false;
    }

    private void setFooterView() {
        if(end){
            progressBar.setVisibility(View.GONE);
            tipsTextView.setText("没有您的包裹了！");
        }else{
            progressBar.setVisibility(View.VISIBLE);
            tipsTextView.setText("别着急，您的包裹马上就来！");
        }

        if(onLoadMoreListener == null || end || pause || loading){
            return;
        }

        onLoadMoreListener.onLoadMore(this);
        loading = true;
    }

    public void loadMore(){
        setFooterView();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(LoadMoreFooterView loadMoreFooterView);
    }
}