package com.example.sortAlgorithm;

/**
 * 交换排序
 */

public class MaoPao {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,89,2};

    public static void main(String[] args){
        BubbleSort(a);

        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    public static void BubbleSort(int[] a){
        int temp = 0;
        int len = a.length;
        for (int i = 0; i < len; i++) {
            boolean flag = false;
            for (int j = 1; j < len - i; j++) {
                if (a[j - 1] > a[j]) {
                    //注意分清是a[j-1]还是a[j]不然容易出现边界问题
                    // 从小到大排序
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    flag = true;
                }
            }
            if (!flag){
                break;
            }
        }
    }
}
