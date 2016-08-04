package com.example.MultipartThreadDownloador;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yuyang on 16/8/4.
 */
public class MultipartThreadDownloador {
    /**
     * 要下载的资源地址
     */
    private String urlStr;
    /**
     * 下载的文件
     */
    private File localFile;
    /**
     * 本地保存路径
     */
    private String dirStr;
    /**
     * 下载到本地的文件名
     */
    private String fileName;
    /**
     * 开启的线程数量
     */
    private int threadCount;
    /**
     * 文件大小
     */
    private long fileSize;

    public MultipartThreadDownloador(String urlStr, String dirStr, String fileName, int threadCount){
        this.urlStr = urlStr;
        this.dirStr = dirStr;
        this.fileName = fileName;
        this.threadCount = threadCount;
    }

    public void download() throws IOException {
        createFileByUrl();

        /**
         * 计算每个线程需要下载的数据长度
         */
        long block = fileSize % threadCount == 0 ? fileSize / threadCount : fileSize / threadCount + 1;
        /**
         * 开启下载线程
         */
        for (int i = 0; i < threadCount; i++) {
            long start = i * block;
            long end = start + block >= fileSize ? fileSize : start + block - 1;

            new DownloadThread(new URL(urlStr), localFile, start, end).start();
        }
    }

    /**
     * 根据资源的URL获取资源的大小，以及在本地创建文件
     */
    private void createFileByUrl() throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(15 * 1000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
        connection.setRequestProperty("Accept-Language", "zh-CN");
        connection.setRequestProperty("Referer", urlStr);
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.connect();

        if (connection.getResponseCode() == 200){
            this.fileSize = connection.getContentLength();//获取资源文件大小
            if (fileSize <= 0){
                throw new RuntimeException("the file that you download has a wrong size ... ");
            }
            File dir = new File(dirStr);
            if (!dir.exists()){
                dir.mkdirs();
            }
            this.localFile = new File(dir, fileName);
            RandomAccessFile raf = new RandomAccessFile(localFile, "rw");
            raf.setLength(fileSize);
            raf.close();

            System.out.println("需要下载的文件大小为 :" + this.fileSize + " , 存储位置为： "
                    + dirStr + "/" + fileName);
        }else {
            throw new RuntimeException("url that you connected has error ...");
        }
    }
}
