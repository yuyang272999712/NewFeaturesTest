package com.yuyang.fitsystemwindowstestdrawer.imageLoader.imageLoaderUtil;

import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

public class ImageSizeUtil {
    /**
     * 根据图片的实际宽高和显示的宽高，获取图片缩放比
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //获取图片的实际宽高
        int width = options.outWidth;
        int height = options.outHeight;
        int inSimpleSize = 1;
        if (width > reqWidth || height > reqHeight){
            int widthRadio = Math.round(width*1.0f/reqWidth);
            int heightRadio = Math.round(height*1.0f/reqHeight);
            inSimpleSize = Math.max(widthRadio, heightRadio);
        }
        return inSimpleSize;
    }

    /**
     * 获取ImageView要显示的大小
     * @param imageView
     * @return
     */
    public static ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        int width = imageView.getWidth();// 获取imageView的实际宽度
        if (width <= 0){
            width = lp.width;// 获取imageView在layout中声明的宽度
        }
        if (width <= 0){
            //如果布局文件中也没有精确值，那么我们再去看看它有没有设置最大值；
            /*width = imageView.getMaxWidth(); 要求API16*/
            width = getImageViewFieldValue(imageView, "mMaxWidth");
        }
        if (width <= 0){
            //如果最大值也没设置，那么我们只有拿出我们的终极方案，使用我们的屏幕宽度；
            width = displayMetrics.widthPixels;
        }

        int height = imageView.getHeight();
        if (height <= 0) {
            height = lp.height;
        }
        if (height <= 0) {
            height = getImageViewFieldValue(imageView, "mMaxHeight");
        }
        if (height <= 0) {
            height = displayMetrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    /**
     * TODO yuyang 反射获得ImageView设置的最大宽度和高度
     * 为了兼容API16以下的android版本
     * @param imageView
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(ImageView imageView, String fieldName) {
        int value = 0;
        try {
            //TODO yuyang 反射获取成员变量
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(imageView);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                value = fieldValue;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

}
