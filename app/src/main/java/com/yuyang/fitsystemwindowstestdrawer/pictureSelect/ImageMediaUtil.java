package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 系统图片抓取工具
 */
public class ImageMediaUtil {
    final String TAG = getClass().getSimpleName();
    ContentResolver contentResolver;
    //所有照片（string-相册ID/imageBucket-相册中的图片图片）
    HashMap<String, ImageBucket> bucketList = new HashMap<String, ImageBucket>();
    //保存系统相册的相册ID
    String bucketCameraId = null;

    public ImageMediaUtil(Context context) {
        contentResolver = context.getContentResolver();
    }

    /**
     * 获取图片库以及图片库中对应的图片
     */
    private void buildImagesBucketList() {
        //设置要返回的字段
        String columns[] = new String[] { MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        //MediaStore.Images.Media.INTERNAL_CONTENT_URI和MediaStore.Images.Media.EXTERNAL_CONTENT_URI两个基础地址
        //其值分别是content://media/internal/images/media和content://media/external/images/media
        //对应内部库和外部库地址
        Cursor cur = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                MediaStore.Images.Media.DISPLAY_NAME + " like ?"
                        + " or " + MediaStore.Images.Media.DISPLAY_NAME + " like ?"
                        + " or " + MediaStore.Images.Media.DISPLAY_NAME + " like ?",
                new String[] { "%.jpg","%.jpeg","%.png"}, null);
        if (cur.moveToFirst()) {
            // 获得图片的id
            int photoIDIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            // 获得图片所在的路径(可以使用路径构建URI)
            int photoPathIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 获得图片显示的名称
            int photoNameIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            // 获得图片的大小
            int photoSizeIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
            int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cur.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);

            String dcim_dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+"/Camera";
            do {
                String _id = cur.getString(photoIDIndex);
                String name = cur.getString(photoNameIndex);
                String path = cur.getString(photoPathIndex);
                String size = cur.getString(photoSizeIndex);
                String bucketName = cur.getString(bucketDisplayNameIndex);
                String bucketId = cur.getString(bucketIdIndex);
                if(path.contains(dcim_dir) && this.bucketCameraId == null){
                    this.bucketCameraId = bucketId;
                }

                ImageBucket bucket = bucketList.get(bucketId);
                if (bucket == null) {
                    bucket = new ImageBucket();
                    bucketList.put(bucketId, bucket);
                    bucket.imageList = new ArrayList<ImageItem>();
                    bucket.bucketName = bucketName;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    String bucketPath = parentFile.getAbsolutePath();
                    bucket.bucketPath = bucketPath + File.separator;
                }
                bucket.count++;
                ImageItem imageItem = new ImageItem();
                imageItem.imageId = _id;
                imageItem.size = size;
                imageItem.imageName = name;
                bucket.imageList.add(imageItem);

            } while (cur.moveToNext());
        }
        cur.close();
    }

    /**
     * 获取手机中所有相册以及相册下的图片
     * @return
     */
    public List<ImageBucket> getImagesBucketList() {
        bucketCameraId = null;
        bucketList.clear();

        buildImagesBucketList();

        List<ImageBucket> tmpList = new ArrayList<ImageBucket>();
        Iterator<Map.Entry<String, ImageBucket>> itr = bucketList.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, ImageBucket> entry = (Map.Entry<String, ImageBucket>) itr.next();
            if (entry.getValue().count != 0) {
                if(entry.getKey().equals(this.bucketCameraId)){
                    tmpList.add(0,entry.getValue());
                }else {
                    tmpList.add(entry.getValue());
                }
            }
        }
        return tmpList;
    }

    /**
     * 通过imageID 获取真正的存储地址
     * @param image_id
     * @return
     */
    String getOriginalImagePath(String image_id) {
        String path = null;
        Log.i(TAG, "---(^o^)----" + image_id);
        String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                MediaStore.Images.Media._ID + "=" + image_id, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

        }
        return path;
    }
}
