package com.example.sortAlgorithm;

/**
 * 归并排序
 */

public class GuiBing {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,89,2};

    public static void main(String[] args){
        mergeSort(a, 0, a.length-1);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    public static void mergeSort(int a[], int left, int right){
        if (left >= right)
            return;
        //找出中间值
        int center = (left+right)/2;
        //对左边数组进行归并排序
        mergeSort(a, left, center);
        //对右边数组进行归并排序
        mergeSort(a, center+1, right);
        //合并
        merge(a, left, center, right);
    }

    /**
     * 将两个数组进行归并，归并前面2个数组已有序，归并后依然有序
     * @param a 数组对象
     * @param left 左数组的第一个元素的索引
     * @param center 左数组的最后一个元素的索引，center+1是右数组第一个元素的索引
     * @param right 右数组最后一个元素的索引
     */
    private static void merge(int[] a, int left, int center, int right) {
        // 临时数组
        int[] tmpArr = new int[a.length];
        // 右数组第一个元素索引
        int mid = center + 1;
        // third 记录临时数组的索引
        int third = left;
        // 缓存左数组第一个元素的索引
        int tmp = left;
        while (left <= center && mid <= right) {
            // 从两个数组中取出最小的放入临时数组
            if(a[left] <= a[mid]) {
                tmpArr[third++] = a[left++];
            } else{
                tmpArr[third++] = a[mid++];
            }
        }
        // 剩余部分依次放入临时数组（实际上两个while只会执行其中一个）
        while (mid <= right) {
            tmpArr[third++] = a[mid++];
        }
        while (left <= center) {
            tmpArr[third++] = a[left++];
        }

        // 将临时数组中的内容拷贝回原数组中
        // （原left-right范围的内容被复制回原数组）
        while (tmp <= right) {
            a[tmp] = tmpArr[tmp++];
        }
    }
}
