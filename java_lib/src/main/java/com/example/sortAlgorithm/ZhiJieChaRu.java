package com.example.sortAlgorithm;

/**
 * 插入排序
 *  直接插入
 *    将一个记录插入到已经排序的有序表中，从而得到一个新的、记录数增加1的有序表。其处理过程是，在排序刚开始的时候，
 *  把第一个元素当做是排序的记录，当依次插入后面的元素的时候，就获得其插入的位置，然后形成一个新的有序表。
 *  时间复杂度：O(n)~O(n2)（n的平方）
 *  空间：O(1)
 */

public class ZhiJieChaRu {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,2,89,2};

    public static void main(String[] args){
        insertSort2(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void insertSort(int[] a){
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

    private static void insertSort2(int[] a) {
        for (int i=1; i<a.length; i++){
            if (a[i-1] > a[i]) {
                int temp=a[i];
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
}
