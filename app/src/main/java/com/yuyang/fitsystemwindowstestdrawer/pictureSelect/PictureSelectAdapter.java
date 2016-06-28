package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.yuyang.fitsystemwindowstestdrawer.MyApplication;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.loadMoreAdapter.CommonViewHolder;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * 图片显示适配器
 */
public class PictureSelectAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private Context context;
    private int bucketPosition;//第几个相册
    private ImageBucket imageBucket;//当前显示的相册
    private ImageLoader imageLoader;//图片加载器
    private DisplayImageOptions imageOptions;
    private SelectPicAdapterCallBack callBack;//图片被选中的回调
    private OnItemClickListener itemClickListener;

    public PictureSelectAdapter(Context context, int bucketPosition, SelectPicAdapterCallBack callBack){
        this.context = context;
        this.bucketPosition = bucketPosition;
        this.imageBucket = ImageSelectHelper.allBucket.get(bucketPosition);
        this.callBack = callBack;
        imageLoader = ImageLoader.getInstance();
        imageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.picture_select_loading_default)
                .showImageForEmptyUri(R.mipmap.picture_select_loading_default).showImageOnFail(R.mipmap.picture_select_loading_default)
                .cacheInMemory(true).cacheOnDisk(false).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void updateBucker(int bucketPosition){
        this.bucketPosition = bucketPosition;
        this.imageBucket = ImageSelectHelper.allBucket.get(bucketPosition);
        notifyDataSetChanged();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_select_pic_grid);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        final ImageItem item = imageBucket.imageList.get(position);
        final View maskView = holder.getViews(R.id.rc_grid_mask);
        final CheckBox checkView = holder.getViews(R.id.rc_grid_item_check);
        ImageView imageView = holder.getViews(R.id.rc_grid_item_img);

        final String filePath = imageBucket.bucketPath + item.getImageName();
        String scheme_file = ImageDownloader.Scheme.FILE.wrap(filePath);
        imageLoader.displayImage(scheme_file, imageView, imageOptions);
        maskView.setVisibility(item.isSelected?View.VISIBLE: View.GONE);
        checkView.setChecked(item.isSelected);

        checkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = checkView.isChecked();
                if (isChecked && ImageSelectHelper.selectedPicture.size() >= ImageSelectHelper.MAX_NUM) {
                    ToastUtils.showShort(MyApplication.getInstance().getBaseContext(), "最多选择5张");
                    checkView.setChecked(!isChecked);
                    return;
                }
                item.setSelected(isChecked);
                maskView.setVisibility(isChecked ? View.VISIBLE:View.GONE);
                if (!isChecked) {//如果是取消选择
                    item.imagePath = "";
                    ImageSelectHelper.selectedPicture.remove(item);
                } else {
                    item.imagePath = filePath;
                    ImageSelectHelper.selectedPicture.add(item);
                }
                if (callBack != null){
                    callBack.callBack();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("itemPosition", position);
                intent.putExtra("bucketPosition", bucketPosition);
                intent.setClass(context, PreviewPicActivity.class);
                ((Activity)context).startActivityForResult(intent,PictureSelectActivity.REQUEST_PREVIEW_IMAGE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageBucket.imageList.size();
    }

    /**
     * 图片选择的回调
     */
    public interface SelectPicAdapterCallBack{
        void callBack();
    }

    private class OnItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
