package com.example.MultipartThreadDownloador;

import java.io.IOException;

/**
 * 多线程下载
 */
public class DownloadSimple {
    public static void main(String[] args) throws IOException {
        String url = "http://ftp-idc.pconline.com.cn/f9e6ecdd2255a53ea5b3dca5fa40349f/pub/download/201010/ChromeSetup.exe";
        //ZHU yuyang 这是对应的Mac OS路径
        String dir = "/Users/yuyang/Desktop";
        String fileName = "Mac操作手册.pdf";
        int threadCount = 4;

        MultipartThreadDownloador downloador = new MultipartThreadDownloador(url, dir, fileName, threadCount);
        downloador.download();
    }
}
