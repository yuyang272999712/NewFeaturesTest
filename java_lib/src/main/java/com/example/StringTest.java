package com.example;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by yuyang on 2017/3/29.
 */

public class StringTest {

    public static void main(String[] args){
        equalsTest();
        System.out.println();
        splicTest();
        String x = new String("asdf");
        resetStr(x);
        System.out.println(x);
    }

    private static void resetStr(String x){
        x = "qwer";
    }

    private static void equalsTest(){
        String str = "abc";
        String s = new String("abc");
        System.out.println(str == s);

        String s1 = new String("111");
        String s2 = "sss111";
        String s3 = "sss" + "111";
        String s4 = "sss" + s1;
        System.out.println(s2 == s3); //true
        System.out.println(s2 == s4); //false
        System.out.println(s2 == s4.intern()); //true
    }

    private static void splicTest(){
        String orginStr = getOriginStr(10);

        //////////////String.splic()表现//////////////////////////////////////////////
        System.out.println("使用String.splic()的切分字符串");
        long st1 = System.nanoTime();
        String [] result = orginStr.split("\\.");
        System.out.println("String.splic()截取字符串用时：" + (System.nanoTime()-st1));
        System.out.println("String.splic()截取字符串结果个数：" + result.length);
        System.out.println();

        //////////////StringTokenizer表现//////////////////////////////////////////////
        System.out.println("使用StringTokenizer的切分字符串");
        long st3 = System.nanoTime();
        StringTokenizer token=new StringTokenizer(orginStr,".");
        System.out.println("StringTokenizer截取字符串用时:"+(System.nanoTime()-st3));
        System.out.println("StringTokenizer截取字符串结果个数：" + token.countTokens());
        System.out.println();

        ////////////////////String.substring()表现//////////////////////////////////////////
        long st5 = System.nanoTime();
        int len = orginStr.lastIndexOf(".");
        System.out.println("使用String.substring()切分字符串");
        int k=0,count=0;

        for (int i = 0; i <= len; i++) {
            if(orginStr.substring(i, i+1).equals(".")){
                if(count==0){
                    orginStr.substring(0, i);
                }else{
                    orginStr.substring(k+1, i);
                    if(i == len){
                        orginStr.substring(len+1, orginStr.length());
                    }
                }
                k=i;count++;
            }
        }
        System.out.println("String.substring()截取字符串用时"+(System.nanoTime()-st5));
        System.out.println("String.substring()截取字符串结果个数：" + (count + 1));
    }

    /**
     * 构造目标字符串
     * eg：10.123.12.154.154
     * @param len 目标字符串组数(每组由3个随机数组成)
     * @return
     */
    private static String getOriginStr(int len){
        StringBuffer sb = new StringBuffer();
        StringBuffer result = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < len; i++){
            sb.append(random.nextInt(9)).append(random.nextInt(9)).append(random.nextInt(9));
            result.append(sb.toString());
            sb.delete(0, sb.length());
            if(i != len-1)
                result.append(".");
        }

        return result.toString();
    }
}
