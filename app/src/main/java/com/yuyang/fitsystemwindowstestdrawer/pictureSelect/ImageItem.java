package com.yuyang.fitsystemwindowstestdrawer.pictureSelect;

import java.io.Serializable;

/**
 * 图片信息
 */
public class ImageItem implements Serializable {
    private static final long serialVersionUID = 1L;

    public String imageId;//图片ID
    public String imageName;
    public String imagePath;//图片路径
    public String size;//图片大小（单位：b）
    public boolean isSelected = false;
    public boolean is_upload_initial;//是否发送原图

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean is_upload_initial() {
        return is_upload_initial;
    }

    public void setIs_upload_initial(boolean is_upload_initial) {
        this.is_upload_initial = is_upload_initial;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageItem)) return false;

        ImageItem item = (ImageItem) o;

        return getImageId() != null ? getImageId().equals(item.getImageId()) : item.getImageId() == null;

    }

    @Override
    public int hashCode() {
        return getImageId() != null ? getImageId().hashCode() : 0;
    }
}
