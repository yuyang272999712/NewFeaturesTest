package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.pictureSelect.GridSpacingItemDecoration;
import com.yuyang.fitsystemwindowstestdrawer.recyclerView.loadMoreAdapter.CommonViewHolder;
import com.yuyang.fitsystemwindowstestdrawer.utils.SDCardUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 浏览本地相册
 */
public class LoadLocationAlbumActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    private MyAlbumAdapter adapter;
    private GridLayoutManager layoutManager;
    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    private MyImageLoader imageLoader = MyImageLoader.getInstance();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            layoutManager = new GridLayoutManager(LoadLocationAlbumActivity.this, 3);
            adapter = new MyAlbumAdapter();
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));
            recyclerView.setLayoutManager(layoutManager);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_location_album);

        findViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadImages();
    }

    /**
     * 加载图片
     */
    private void loadImages() {
        if (!SDCardUtils.isSDCardEnable()){
            ToastUtils.showLong(this, "暂无外部存储卡！");
            return;
        }
        progressDialog = ProgressDialog.show(this, null, "加载中...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver resolver = LoadLocationAlbumActivity.this.getContentResolver();
                //查询图片
                Cursor cursor = resolver.query(imageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "+MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                while (cursor.moveToNext()){
                    //获取图片路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //获取该图片的父路径
                    File parentFile = new File(path).getParentFile();
                    String dirPath = parentFile.getAbsolutePath();
                    //如果该文件夹已扫描过
                    if (mDirPaths.contains(dirPath)){
                        continue;
                    }else {
                        mDirPaths.add(dirPath);
                    }
                    int picCount = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith("jpg"))
                                return true;
                            return false;
                        }
                    }).length;
                    if (picCount > mPicsSize){
                        mPicsSize = picCount;
                        mImgDir = parentFile;
                    }
                }
                cursor.close();
                mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith("jpg"))
                            return true;
                        return false;
                    }
                }));
                //扫描完成就可以释放辅助类
                mDirPaths.clear();
                //通知主线程扫描完成
                mHandler.sendEmptyMessage(0);
            }
        }).run();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.load_location_album_recycler_view);
    }

    private class MyAlbumAdapter extends RecyclerView.Adapter<CommonViewHolder> {
        @Override
        public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return CommonViewHolder.getViewHolder(parent, R.layout.item_load_location_album);
        }

        @Override
        public void onBindViewHolder(CommonViewHolder holder, int position) {
            ImageView imageView = holder.getViews(R.id.id_item_image);
            //使用自定义的ImageLoader去加载图片
            imageLoader.loadImage(mImgDir.getAbsolutePath() + "/" + mImgs.get(position), imageView);
        }

        @Override
        public int getItemCount() {
            return mImgs.size();
        }
    }
}
