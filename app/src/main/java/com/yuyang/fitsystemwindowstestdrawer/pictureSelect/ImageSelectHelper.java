package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择保持类
 */
public class ImageSelectHelper {
    /** 最多选择图片的个数*/
    public static int MAX_NUM = 5;
    /** 所有相册信息*/
    public static List<ImageBucket> allBucket = new ArrayList<ImageBucket>();
    /** 已选择的图片*/
    public static ArrayList<ImageItem> selectedPicture = new ArrayList<ImageItem>();
    /** 是否发送原图*/
    public static boolean is_upload_initial = false;

    public static double getAllPicSize(){
        double size = 0;
        for (ImageItem item:selectedPicture){
            size += Double.parseDouble(item.size);
        }
        return size;
    }

    public static void cleanAll(){
        is_upload_initial = false;
        allBucket.clear();
        selectedPicture.clear();
    }
}
