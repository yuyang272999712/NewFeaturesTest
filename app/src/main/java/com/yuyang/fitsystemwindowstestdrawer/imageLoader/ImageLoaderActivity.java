package com.yuyang.fitsystemwindowstestdrawer.imageLoader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.imageLoader.imageLoaderUtil.ImageLoader;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;

/**
 * 自定义ImageLoader
 */
public class ImageLoaderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private GridLayoutManager layoutManager;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        imageLoader = ImageLoader.getIntance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.image_loader_recycler_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(layoutManager);
    }

    private class MyAdapter extends RecyclerView.Adapter<CommonViewHolder> {
        @Override
        public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return CommonViewHolder.getViewHolder(parent, R.layout.item_image_loader_view);
        }

        @Override
        public void onBindViewHolder(CommonViewHolder holder, int position) {
            ImageView imageView = holder.getViews(R.id.image_loader_image_view);
            imageLoader.loadImage(Images.imageThumbUrls[position], imageView, true);
        }

        @Override
        public int getItemCount() {
            return Images.imageThumbUrls.length;
        }
    }
}
