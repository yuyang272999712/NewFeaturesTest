package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.parallaxRecyclerImageView.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.parallaxRecyclerImageView.ParallaxViewHolder;

/**
 * Created by yuyang
 */
public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    private int[] imageIds = new int[]{R.mipmap.a,
            R.mipmap.b, R.mipmap.c,
            R.mipmap.f, R.mipmap.e};

    public TestRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ViewHolder(inflater.inflate(R.layout.item_parallax_recycler_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.getBackgroundImage().setImageResource(imageIds[position % imageIds.length]);
        viewHolder.getTextView().setText("Row " + position);
        // 这个方法非常重要
        viewHolder.getBackgroundImage().reuse();
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public static class ViewHolder extends ParallaxViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.label);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.backgroundImage;
        }

        public TextView getTextView() {
            return textView;
        }
    }


}
