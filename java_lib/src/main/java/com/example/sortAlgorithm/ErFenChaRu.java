package com.example.sortAlgorithm;

/**
 * 插入排序
 */

public class ErFenChaRu {
    private static int[] a = new int[]{12,34,7,32,67,0,23,2,16,89,2};

    public static void main(String[] args){
        erFenChaRu(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    private static void erFenChaRu(int[] a) {
        for (int i=0; i<a.length; i++){
            int temp = a[i];
            int left = 0;
            int right = i-1;
            int mid;
            while (left <= right){
                mid = (left+right)/2;
                if (temp < a[mid]){
                    right = mid-1;
                }else {
                    left = mid+1;
                }
            }
            for (int j=i-1; j>=left; j--){
                a[j+1] = a[j];
            }
            if (left != i){
                a[left] = temp;
            }
        }
    }
}
