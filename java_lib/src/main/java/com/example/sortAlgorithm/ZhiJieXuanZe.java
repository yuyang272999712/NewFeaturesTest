package com.example.sortAlgorithm;

/**
 * 选择排序
 *  简单选择排序
 *  算法思想是：通过n−i次关键字间的比较，从n−i+1个记录中选出关键字最小的记录，并和第i(1≤i≤n)个记录交换之
 *
 *  第i趟排序需要比较n−i次关键字的比较，所以总共需要比较n−1+n−2+...+1=n(n−1)2次。
 * 最好的情况下，交换0次，最差的情况是交换n−1次，所以最终的时间复杂度是O(n2)（n的平方）。
 * 空间复杂度O(1)
 */

public class ZhiJieXuanZe {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,89,2};

    public static void main(String[] args){
        selectSort(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void selectSort(int[] a) {
        for (int i=0; i<a.length; i++){
            //假设i位置是最小的
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
