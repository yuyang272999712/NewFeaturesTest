package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.yuyang.fitsystemwindowstestdrawer.MyApplication;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;
import com.yuyang.photoviewlibrary.PhotoView;
import com.yuyang.photoviewlibrary.PhotoViewAttacher;

import java.util.List;

/**
 * 图片预览界面
 */
public class PreviewPicActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button sendBtn;
    private PackViewPager viewPager;
    private View bottomView;
    private CheckBox originalCheck;
    private CheckBox picCheck;

    private int bucketPosition;//ZHU yuyang 如果相册标示是-1，说明预览的是已选择图片列表
    private int itemPosition;
    private List<ImageItem> imageItems;
    private int imageCount;

    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.picture_select_loading_default)
            .showImageForEmptyUri(R.mipmap.picture_select_loading_default).showImageOnFail(R.mipmap.picture_select_loading_default)
            .cacheInMemory(true).cacheOnDisk(false).considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_picture);

        getIntentDatas();
        findViews();
        initDatas();
        setActions();
    }

    private void setActions() {
        toolbar.setTitle(itemPosition+"/"+imageCount);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                itemPosition = position;
                toolbar.setTitle(itemPosition + 1 + "/" + imageItems.size());
                updateCheckBoxAndBtnViews();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        originalCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageItem item = imageItems.get(itemPosition);
                boolean isChecked = originalCheck.isChecked();
                if (isChecked && ImageSelectHelper.selectedPicture.size() == 0) {
                    ImageSelectHelper.is_upload_initial = true;
                    item.isSelected = true;
                    ImageSelectHelper.selectedPicture.add(item);
                }else {
                    ImageSelectHelper.is_upload_initial = isChecked;
                }
                updateCheckBoxAndBtnViews();
            }
        });

        picCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = picCheck.isChecked();
                ImageItem item = imageItems.get(itemPosition);
                if (isChecked && ImageSelectHelper.selectedPicture.size() >= 5){
                    ToastUtils.showShort(MyApplication.getInstance().getBaseContext(), "最多选择5张");
                    picCheck.setChecked(!isChecked);
                    return;
                }
                item.isSelected = isChecked;
                if (!isChecked) {//如果是取消选择
                    ImageSelectHelper.selectedPicture.remove(item);
                } else {
                    ImageSelectHelper.selectedPicture.add(item);
                }
                updateCheckBoxAndBtnViews();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageItem item = imageItems.get(itemPosition);
                if (ImageSelectHelper.selectedPicture.size() == 0) {
                    ImageSelectHelper.selectedPicture.add(item);
                }
                setResult(RESULT_OK);
                onBackPressed();
            }
        });
    }

    private void initDatas() {
        if (bucketPosition != -1) {
            imageItems = ImageSelectHelper.allBucket.get(bucketPosition).imageList;
        }else {
            imageItems = (List<ImageItem>) ImageSelectHelper.selectedPicture.clone();
        }
        imageCount = imageItems.size();
        updateCheckBoxAndBtnViews();
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(itemPosition);
        viewPager.setOffscreenPageLimit(2);
    }

    /**
     * 更新“选择”“原图”“发送”按钮的显示
     */
    private void updateCheckBoxAndBtnViews(){
        picCheck.setChecked(imageItems.get(itemPosition).isSelected ? true:false);
        if (ImageSelectHelper.is_upload_initial){
            originalCheck.setChecked(true);
            double size = ImageSelectHelper.getAllPicSize();
            originalCheck.setText(getResources().getString(R.string.preview_pic_original_size, size/1024/1024));
        }else {
            originalCheck.setChecked(false);
            originalCheck.setText(getResources().getString(R.string.preview_pic_original));
        }
        if (ImageSelectHelper.selectedPicture.size() > 0) {
            sendBtn.setText(getResources().getString(R.string.picture_select_send_count, ImageSelectHelper.selectedPicture.size()));
        }else {
            sendBtn.setText(getResources().getString(R.string.picture_select_send));
        }
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sendBtn = (Button) findViewById(R.id.preview_pic_send);
        viewPager = (PackViewPager) findViewById(R.id.preview_pic_view_pager);
        bottomView = findViewById(R.id.preview_bottom_layout);
        originalCheck = (CheckBox) findViewById(R.id.preview_pic_original);
        picCheck = (CheckBox) findViewById(R.id.preview_pic_check);
    }

    public void getIntentDatas() {
        bucketPosition = getIntent().getIntExtra("bucketPosition", 0);
        itemPosition = getIntent().getIntExtra("itemPosition", 0);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageItems.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageItem item = imageItems.get(position);
            String filePath;
            if (bucketPosition != -1) {
                filePath = ImageSelectHelper.allBucket.get(bucketPosition).bucketPath + item.getImageName();
            }else {
                filePath = item.imagePath;
            }
            String scheme_path = ImageDownloader.Scheme.FILE.wrap(filePath);
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    if (bottomView.getVisibility() == View.GONE) {
                        bottomView.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                    } else {
                        bottomView.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                    }
                }
            });
            mImageLoader.displayImage(scheme_path, photoView, imageOptions);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mImageLoader.clearMemoryCache();
            PhotoView imageView = (PhotoView) object;
            mImageLoader.cancelDisplayTask(imageView);
            imageView.destroyDrawingCache();
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
