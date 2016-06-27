package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.loadMoreAdapter.CommonViewHolder;

/**
 * 图片显示适配器
 */
public class PictureSelectAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private ImageBucket imageBucket;//当前显示的相册
    private ImageLoader imageLoader;//图片加载器
    private DisplayImageOptions imageOptions;
    private SelectPicAdapterCallBack callBack;//图片被选中的回调

    public PictureSelectAdapter(ImageBucket imageBucket, SelectPicAdapterCallBack callBack){
        this.imageBucket = imageBucket;
        this.callBack = callBack;
        imageLoader = ImageLoader.getInstance();
        imageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.picture_select_loading_default)
                .showImageForEmptyUri(R.mipmap.picture_select_loading_default).showImageOnFail(R.mipmap.picture_select_loading_default)
                .cacheInMemory(true).cacheOnDisk(false).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder viewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_select_pic_grid);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        final ImageItem item = imageBucket.imageList.get(position);
        String filePath = imageBucket.bucketPath + item.getImageName();
        imageLoader.displayImage(filePath, (ImageView) holder.getViews(R.id.rc_grid_item_img), imageOptions);
        holder.getViews(R.id.rc_grid_mask).setVisibility(item.isSelected?View.VISIBLE: View.GONE);
        CheckBox checkView = holder.getViews(R.id.rc_grid_item_check);
        checkView.setSelected(item.isSelected);
        checkView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (callBack != null){
                    callBack.callBack(item, isChecked);
                }
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
        void callBack(ImageItem item, boolean isChecked);
    }
}
