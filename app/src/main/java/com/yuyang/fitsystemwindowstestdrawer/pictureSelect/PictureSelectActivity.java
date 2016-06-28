package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.SDCardUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * 仿微信的照片选择
 */
public class PictureSelectActivity extends AppCompatActivity {
    public static final int REQUEST_PREVIEW_IMAGE = 0x0000001;

    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private RecyclerView mRecycleView;
    private Button sendBtn;
    private TextView preview;

    private GridLayoutManager gridLayoutManager;
    private PictureSelectAdapter mAdapter;
    private ImageMediaUtil mediaHelper;//图片扫描帮助类

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (ImageSelectHelper.allBucket.size() > 0) {
                mAdapter = new PictureSelectAdapter(PictureSelectActivity.this, 0, new CheckBoxClickCallBack());
                mRecycleView.setAdapter(mAdapter);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader();
        setContentView(R.layout.activity_picture_select);
        //清空所有图片
        ImageSelectHelper.cleanAll();
        findViews();

        mToolbar.setTitle("图片选择");
        setSupportActionBar(mToolbar);
        //TODO yuyang Toolbar显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelectActivity.this.onBackPressed();
            }
        });

        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));

        mediaHelper = new ImageMediaUtil(this);
        getImages();

        setActions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            reSetSelectPicBut();
        }
    }

    private void setActions() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PictureSelectActivity.this, PreviewPicActivity.class);
                intent.putExtra("bucketPosition", -1);
                intent.putExtra("itemPosition", 0);
                startActivityForResult(intent, REQUEST_PREVIEW_IMAGE);
            }
        });
    }

    private void findViews() {
        sendBtn = (Button) findViewById(R.id.picture_select_send);
        preview = (TextView) findViewById(R.id.picture_select_preview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecycleView = (RecyclerView) findViewById(R.id.picture_select_recycler);
    }

    /**
     * TODO 初始化图片加载器（可使用别的图片加载方式）
     */
    private void initImageLoader(){
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)
                .diskCacheFileCount(100)  //可以缓存的文件数量
                .writeDebugLogs()
                .build());
    }

    /**
     * 获取手机上的所有图片
     */
    public void getImages() {
        if (!SDCardUtils.isSDCardEnable()){
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * 扫描过程可能会耗时，需要启动异步任务
         */
        AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = ProgressDialog.show(PictureSelectActivity.this,null,"加载中...");
            }

            @Override
            protected Void doInBackground(Void... params) {
                ImageSelectHelper.allBucket = mediaHelper.getImagesBucketList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mProgressDialog.dismiss();
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);
            }
        };
        task.execute();
    }

    /**
     * adapter的回调
     */
    private class CheckBoxClickCallBack implements PictureSelectAdapter.SelectPicAdapterCallBack {
        @Override
        public void callBack() {
            reSetSelectPicBut();
        }
    }

    /**
     * 重置完成按钮状态
     */
    private void reSetSelectPicBut(){
        sendBtn.setEnabled(ImageSelectHelper.selectedPicture.size()>0);
        preview.setEnabled(ImageSelectHelper.selectedPicture.size()>0);
        if(ImageSelectHelper.selectedPicture.size() > 0){
            sendBtn.setText(getResources().getString(R.string.picture_select_send_count,ImageSelectHelper.selectedPicture.size()));
            preview.setText(getResources().getString(R.string.picture_select_preview_count,ImageSelectHelper.selectedPicture.size()));
        }else{
            sendBtn.setText(R.string.picture_select_send);
            preview.setText(R.string.picture_select_preview);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if (responseCode == Activity.RESULT_OK && requestCode == REQUEST_PREVIEW_IMAGE) {
            uploadImage();
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage(){
        if (ImageSelectHelper.selectedPicture.size() > 0) {
            //TODO 启动service上传图片
            ToastUtils.showLong(this,"开始上传图片，共："+ImageSelectHelper.selectedPicture.size()+"张");
        }
        ImageSelectHelper.cleanAll();
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageSelectHelper.cleanAll();
    }
}
