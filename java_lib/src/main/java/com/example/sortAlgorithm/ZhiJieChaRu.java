package com.example.sortAlgorithm;

/**
 * 插入排序
 */

public class ZhiJieChaRu {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,89,2};

    public static void main(String[] args){
        zhiJieChaRu(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void zhiJieChaRu(int[] a) {
        for (int i=1; i<a.length; i++){
            int temp = a[i];
            int j;
            for (j=i-1; j>=0; j--){
                if (a[j] > temp){
                    a[j+1] = a[j];
                }else {
                    break;
                }
            }
            a[j+1] = temp;
        }
    }
}
