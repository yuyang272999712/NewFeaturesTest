package com.example.sortAlgorithm;

/**
 * 选择排序
 */

public class ZhiJieXuanZe {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,89,2};

    public static void main(String[] args){
        choseSort(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void choseSort(int[] a) {
        for (int i=0; i<a.length; i++){
            int tempIndex = i;
            for (int j=i+1; j<a.length; j++){
                if (a[j]<a[tempIndex]){
                    tempIndex = j;
                }
            }
            //将当前第一个元素与它后面序列中的最小的一个 元素交换，也就是将最小的元素放在最前端
            int tempValue = a[i];
            a[i] = a[tempIndex];
            a[tempIndex] = tempValue;
        }
    }
}
