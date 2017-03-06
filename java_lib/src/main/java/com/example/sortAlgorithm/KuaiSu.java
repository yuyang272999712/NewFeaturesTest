package com.example.sortAlgorithm;

/**
 * 交换排序
 */

public class KuaiSu {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,89,2};

    public static void main(String[] args){
        quickSort(a, 0, a.length-1);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    public static void quickSort(int a[], int start, int end){
        //判断是否需要排序
        if (start < end){
            int middle = getMiddle(a, start, end);
            quickSort(a, start, middle - 1);
            quickSort(a, middle + 1, end);
        }
    }

    public static int getMiddle(int[] a, int start, int end) {
        int temp = a[start];// 基准元素
        while (start < end) {
            // 找到比基准元素小的元素位置
            while (start < end && a[end] >= temp) {
                end--;
            }
            a[start] = a[end];
            while (start < end && a[start] <= temp) {
                start++;
            }
            a[end] = a[start];
        }
        a[start] = temp;
        return start;
    }
}
