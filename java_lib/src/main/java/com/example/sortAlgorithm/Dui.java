package com.example.sortAlgorithm;

/**
 * 选择排序
 *  堆排序
 *   实现堆排序的基本思想是：将待排序的序列构造成一个大顶堆或者小顶堆。此时整个堆满足根节点是最大值或者最小值。将根节点移走，
 *  并与堆数组的最后一个值进行交换，这样的话最后一个值就是最大值或者最小值了。然后将剩余n-1（假设原来总共有n个元素）
 *  未排序的序列重新构造成一个最大堆或者最小堆，再与除原来最大值之外的最后一个元素进行交换，得到次小值。如此反复进行，就得到一个排序的序列。
 *  时间O(nlogn)
 *  O(1)
 */

public class Dui {
    private static int[] a = new int[]{12,34,7,2,32,67,0,23,16,89,2};

    public static void main(String[] args){
        headSort(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void headSort(int[] a) {
        int arrayLength = a.length;
        // 循环建堆
        for (int i = 0; i < arrayLength - 1; i++) {
            // 建堆
            buildMaxHeap(a, arrayLength - 1 - i);
            // 交换堆顶和最后一个元素
            swap(a, 0, arrayLength - 1 - i);
        }
    }

    private static void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private static void buildMaxHeap(int[] data, int lastIndex) {
        // 从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            // k保存正在判断的节点
            int k = i;
            // 如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                // k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                // 如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    // 若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        // biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                // 如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    // 交换他们
                    swap(data, k, biggerIndex);
                    // 将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }
}
