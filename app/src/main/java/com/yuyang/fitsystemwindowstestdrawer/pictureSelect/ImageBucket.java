package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import java.util.List;

/**
 * 相册信息
 * @author yuyang 2015年7月15日
 *
 */
public class ImageBucket {
    public int count = 0;//此相册中有多少图片
    public String bucketName;//相册名称
    public String bucketPath;//相册路径
    public List<ImageItem> imageList;//相册中所有图片信息
}
