package com.yuyang.fitsystemwindowstestdrawer.internetAbout.downFileService;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * 要下载的文件信息
 */
public class FileInfo implements Serializable {
    private int fileId;//文件唯一标识
    private String fileName;//文件名
    private String fileUrl;//文件地址
    private int startPos;//下载其实位置（用于断点续传）

    public FileInfo() {
    }

    public FileInfo(int fileId, String fileName, String fileUrl, int startPos) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.startPos = startPos;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
}
