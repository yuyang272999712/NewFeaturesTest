package com.example.sortAlgorithm;

/**
 * 交换排序
 * 快速排序：
 *  快速排序算法的思想是：通过一趟快速排序将待排序的记录分割成独立的两部分，其中一部分记录的关键字均比另一部分的
 * 记录的关键字小，则可分别对这两部分记录继续进行排序，达到整个记录有序。实现快速排序算法的核心是partition函数，
 * 这个函数的主要目的先选取当中的一个关键字（称为枢轴），然后尽可能将他放在一个位置，使得它左边的值都比它小，
 * 右边的值比它大。
 *
 *  快速排序算法的最优时间复杂度是O(nlogn)，在如下的情况下是最优的：对于一组具有n个关键字的记录，partition函数每次都划分得很均匀，也就是每次调用partition函数之后，比枢轴小的关键字和比枢轴大的关键字的数量基本相等。这样下次调用partition函数的时候所用的时间是上次调用partition函数的两倍加上比较元素的时间，所以这是最优的情况。
 在最坏的情况指的就是每次调用partition函数，只得到一个比上一次划分少一个记录的子序列（因为只要调用partition函数，必须至少会有一次关键字的交换）。由于只减少了一个关键字，所以后面还需要进行n-1次（总共有n个关键字）递归调用partition函数。所以到最后总共需要比较的次数是n−1+n−2+...+1=n(n−1)/2，所以最终的时间复杂度是O(n2)（n的平方）。
 在平均的情况下，枢轴的关键字应该在k位置(1≤k≤n)。所以最优情况下的时间复杂度是O(nlogn)。
 那么快速排序算法的空间复杂度又是如何呢？最优和平均情况下空间复杂度都是O(logn)，在最坏情况就是n−1次调用，所以空间复杂度是O(n)。
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
