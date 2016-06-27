package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.SDCardUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * 仿微信的照片选择
 */
public class PictureSelectActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private RecyclerView mRecycleView;
    private GridLayoutManager gridLayoutManager;
    private PictureSelectAdapter mAdapter;
    private ImageMediaHelper mediaHelper;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mAdapter = new PictureSelectAdapter(ImageSelectHelper.allBucket.get(0), new CheckBoxClickCallBack());
            mRecycleView.setAdapter(mAdapter);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_picture_select);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecycleView = (RecyclerView) findViewById(R.id.picture_select_recycler);
        gridLayoutManager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));

        mediaHelper = new ImageMediaHelper(this);

        getImages();
    }

    public void getImages() {
        if (!SDCardUtils.isSDCardEnable()){
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }

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
    }

    private class CheckBoxClickCallBack implements PictureSelectAdapter.SelectPicAdapterCallBack {
        @Override
        public void callBack(ImageItem item, boolean checked) {
            if (!checked && ImageSelectHelper.selectedPicture.size() >= ImageSelectHelper.MAX_NUM) {
                ToastUtils.showShort(PictureSelectActivity.this, "最多选择5张");
                return;
            }
            if (!checked) {//如果是取消选择
                ImageSelectHelper.selectedPicture.remove(item);
                item.setSelected(false);
            } else {
                ImageSelectHelper.selectedPicture.add(item);
                item.setSelected(true);
            }
            mAdapter.notifyDataSetChanged();
//            mSelectPicComplete.setEnabled(ImageSelectHelper.selectedPicture.size()>0);
//            mSelectPreview.setEnabled(ImageSelectHelper.selectedPicture.size()>0);
//            reSetSelectPicBut();
        }

    }
}
