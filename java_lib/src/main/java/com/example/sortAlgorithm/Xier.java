package com.example.sortAlgorithm;

/**
 * 希尔排序
 * 原理
 *  由于希尔排序是在直接插入排序的基础上进行改进的，所以为了更好理解希尔排序，需要再次将直接插入排序请出来。
 * 我们知道直接插入排序的基本思想是将一个记录插入到有序表中，构成一个新的有序表。而且直接插入排序的使用情况是
 * 基本有序、记录少。在实际的情况中，这两个条件是有点苛刻的，很多情况下都是无序在记录数比较大。这种情况下再使用
 * 直接插入排序无疑是影响效率的。
 *  基于此，希尔提出了把大记录数的列表分割为一组组更少的记录列表，这个过程的实现采用的跳跃分割策略。
 * 所谓跳跃分割策略，就是将相距某个增量的记录组成一个子序列，这样才能保证在子序列内分别进行直接插入排序后得到的
 * 结果是基本有序而不是局部有序。
 *
 *  先取一个小于n的证书d1作为第一个增量，把文件的全部记录分成d1组。所有距离为d1的倍数的记录放在同一组中。
 * 先在各组内进行直接插入排序，然后取第二 个增量d2<d1重复上述的分组和排序，直到所取的增量dt=1，
 * 即所有记录放在同一组中进行直接插入排序为止。该方法实际上是一种分组插入方法。
 *
 * 时间复杂度：O(n3/2)（n的1.5次方）
 * 空间：O(1)
 */

public class Xier {
    private static int[] a = new int[]{12,34,7,32,67,0,23,16,2,89,2};

    public static void main(String[] arg){
        shellSort(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void shellSort(int[] a){
        int inc = a.length, i, j;//inc 为增量
        do {
            inc = inc/3+1;
            for (i=inc; i<a.length; i++){
                if (a[i-inc] > a[i]){
                    int temp = a[i];
                    for (j=i-inc; j>=0; j-=inc){
                        if (a[j] > temp){
                            a[j+inc] = a[j];
                        }else {
                            break;
                        }
                    }
                    a[j+inc] = temp;
                }
            }
        }while (inc > 1);
    }

}
