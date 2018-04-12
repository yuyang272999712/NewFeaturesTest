package com.example.sortAlgorithm;

/**
 * 交换排序
 * 冒泡：
 *  在最坏的情况就是待排序的记录是逆序的，此时的时间复杂度是O(n2)；
 *  最好的情况是，排序表本身就是有序的，那么在这种情况下，时间复杂度是O(n)。
 *  空间复杂度O(1)
 */

public class MaoPao {
    private static int[] a = new int[]{12,34,7,2,32,67,0,23,16,89,2};

    public static void main(String[] args){
        BubbleSort(a);

        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    public static void BubbleSort(int[] a){
        int len = a.length;
        boolean flag = false;
        for (int i=0; i<len; i++){
            for (int j=0; j<len-1-i; j++){
                if (a[j]>a[j+1]){
                    swap(a, j, j+1);
                    flag = true;
                }
            }
            if (!flag){
                break;
            }
        }
    }

    private static void swap(int[] num, int i, int j){
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
}
