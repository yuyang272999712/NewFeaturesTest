package com.example.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
    字符流有两个抽象类：Writer   Reader
 其对应子类FileWriter和FileReader可实现文件的读写操作
 BufferedWriter和BufferedReader能够提供缓冲区功能，用以提高效率

    字节流也有两个抽象类：InputStream   OutputStream
 其对应子类有FileInputStream和FileOutputStream实现文件读写
 BufferedInputStream和BufferedOutputStream提供缓冲区功能
 */

public class IOTest {
    public static void main(String[] args){
        inputStreamOpt();
        bufferInputStreamOpt();
        readerOpt();
    }

    private static void readerOpt() {
        File filein = new File("/Users/yuyang/Desktop/baidu.rtf");
        File fileout = new File(filein.getParentFile()+File.separator+(filein.getName().split("\\.")[0]+"_temp2"+"."+filein.getName().split("\\.")[1]));

        FileReader fileReader = null;
        FileWriter fileWriter = null;    //写字符到控制台的流
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;


        try {
            fileReader = new FileReader(filein);
            fileWriter = new FileWriter(fileout);
            bufferedReader = new BufferedReader(fileReader);
            bufferedWriter = new BufferedWriter(fileWriter);
            String str;
            while ((str = bufferedReader.readLine()) != null){
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferInputStreamOpt() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        byte[] buffer = new byte[1024];

        File filein = new File("/Users/yuyang/Desktop/baidu.rtf");
        File fileout = new File(filein.getParentFile()+File.separator+(filein.getName().split("\\.")[0]+"_temp1"+"."+filein.getName().split("\\.")[1]));
        try {
            bis = new BufferedInputStream(new FileInputStream(filein));
            bos = new BufferedOutputStream(new FileOutputStream(fileout));
            int len = 0;
            while ((len = bis.read(buffer))>0){
                bos.write(buffer, 0, len);
            }
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void inputStreamOpt() {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        byte[] buffer=new byte[512];   //一次取出的字节数大小,缓冲区大小

        File file = new File("/Users/yuyang/Desktop/baidu.rtf");
        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(file.getParentFile()+File.separator+(file.getName().split("\\.")[0]+"_temp"+"."+file.getName().split("\\.")[1]));
            int len = 0, count = 0;
            while ((len = inputStream.read(buffer)) > 0 ){
                count += len;
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
