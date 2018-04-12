package com.example.sortAlgorithm;

/**
 * 归并排序
 *  归并排序是所有常用内部排序算法中稳定性最好的，无论是平均时间复杂度、最坏时间复杂度还是最好时间复杂度，
 * 其时间复杂度都是O(nlogn)。由于这个特性，在需要考虑排序稳定性的情况下，归并排序是所有优化算法（直接插入排序、
 * 冒泡排序和简单选择排序）使用最多的。
 *  其实归并排序算法的思想很简单：假设初始序列含有n个记录，则可以看成是n个有序的子序列，每一个子序列的长度都是1，
 * 然后把这些子序列两两归并，得到⌈n/2⌉（⌈x⌉表示不小于x的最小整数）个长度为2或者1的有序子序列；再两两归并，……，
 * 如此重复，直至得到一个长度为n的有序序列为止。
 *
 * 1）稳定性
 　归并排序是一种稳定的排序。
 （2）存储结构要求
 　可用顺序存储结构。也易于在链表上实现。
 （3）时间复杂度
 　对长度为n的文件，需进行趟二路归并，每趟归并的时间为O(n)，故其时间复杂度无论是在最好情况下还是在最坏情况下均是O(nlgn)。
 （4）空间复杂度
 　 需要一个辅助向量来暂存两有序子文件归并的结果，故其辅助空间复杂度为O(n)，显然它不是就地排序。
 */

public class MergeSort {
    private static int[] a = new int[]{12,34,7,32,67,0,22,16,89,2,23,90,50,2};

    public static void main(String[] args){
        sort(a, 0, a.length-1);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void sort(int[] a, int left, int right){
        if (left<right){
            int mid = (left+right)/2;
            //将数组a分为a[left...mid]并进行排序
            sort(a,left,mid);
            //数组a[mid+1...right]部分进行排序
            sort(a,mid+1,right);
            //将a[i...mid]部分和a[mid+1...j]部分的排序结果归并到a
            merge(a, left, mid, right);
        }
    }

    private static void merge(int[] a, int left, int mid, int right) {
        int[] temp = new int[right-left+1];
        int k=0;
        int i=left,j=mid+1;
        while (i<=mid && j<=right){
            if (a[i]<a[j]){
                temp[k++] = a[i++];
            }else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i<=mid){
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j<=right){
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖a数组
        for (int n=0;n<temp.length;n++){
            a[left+n] = temp[n];
        }
    }
}
