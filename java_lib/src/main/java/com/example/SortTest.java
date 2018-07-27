package com.example;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yuyang on 2017/3/2.
 */

public class SortTest {
    private static int[] nums = new int[]{355,4,7,11,19,3,6,45,23,0,4,6};

    public static void main(String[] args){
        int two[] = getTwoNum9(nums);
        System.out.println(two[0]+","+two[1]);
        guibing(nums, 0, nums.length-1);
//        kuaipai(nums, 0, nums.length-1);
//        maopao(nums);
//        xuanze(nums);
//        charu(nums);
        for(int i=0;i<nums.length;i++)
            System.out.print(nums[i]+" ");

        System.out.println();
        int j = 0;
        for(int i = 0; i < 100; i++)
            j = j++;
        System.out.println(j);

        AtomicInteger integer = new AtomicInteger(0);
        new StringThread(integer, "A", 0, 4).start();
        new StringThread(integer, "B", 1, 4).start();
        new StringThread(integer, "C", 2, 4).start();
        new StringThread(integer, "D", 3, 4).start();
    }

    private static void charu(int[] a){
        int len = a.length;
        for (int i=1; i<len; i++){
            int get = a[i];
            int j = i-1;
            for (; j>=0&&a[j]>get; j--){
                a[j+1] = a[j];
            }
            a[j+1] = get;
        }
    }

    private static void xuanze(int[] a){
        int len = a.length;
        for (int i=0; i<len-1; i++){
            int min = i;
            for (int j = i+1; j< len; j++){
                if (a[j] < a[min]){
                    min = j;
                }
            }
            if (min != i){
                swap(a, i, min);
            }
        }
    }

    private static void maopao(int[] a){
        int len = a.length;
        for (int i=0; i<len-1; i++){
            for (int j=0; j<len-1-i; j++){
                if (a[j] > a[j+1]){
                    swap(a, j, j+1);
                }
            }
        }
    }

    private static int[] getTwoNum9(int[] nums){
        if (nums != null && nums.length >= 2){
            for (int i=0; i<nums.length; i++){
                if (nums[i] <= 9){
                    for (int j=i+1;j<nums.length;j++){
                        if (nums[j] == 9-nums[i]){
                            return new int[]{i,j};
                        }
                    }
                }
            }
        }
        return null;
    }

    private static void kuaipai(int[] nums, int start, int end){
        if (start < end){
            int middle = getMiddle(nums, start, end);
            kuaipai(nums, start, middle - 1);
            kuaipai(nums, middle + 1, end);
        }
    }

    private static int getMiddle(int[] nums, int start, int end){
        int temp = nums[start];
        while (start < end){
            while (start < end && nums[end] >= temp){
                end--;
            }
            nums[start] = nums[end];
            while (start < end && nums[start] <= temp){
                start++;
            }
            nums[end] = nums[start];
        }
        nums[start] = temp;
        return start;
    }

    private static void guibing(int[] nums, int left, int right){
        if (left < right){
            int mid = (left + right)/2;
            guibing(nums, left, mid);
            guibing(nums, mid+1, right);
            merge(nums, left, mid, right);
        }
    }

    private static void merge(int[] nums, int left, int mid, int right) {
        int len = right-left+1;
        int[] tempNums = new int[len];
        int index = 0;
        int i = left, j = mid+1;
        while (i <= mid && j<=right){
            tempNums[index++] = nums[i]<=nums[j]?nums[i++]:nums[j++];
        }
        while (i<=mid){
            tempNums[index++] = nums[i++];
        }
        while (j<=right){
            tempNums[index++] = nums[j++];
        }
        for (int k=0; k<len; k++){
            nums[k+left] = tempNums[k];
        }
    }

    private static void swap(int[] A, int i, int j){
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    /**
     * 顺序打印ABCD
     */
    static class StringThread extends Thread{
        private AtomicInteger count;
        private String str;
        private int index;
        private int total;

        public StringThread(AtomicInteger count, String str, int index, int total){
            this.count = count;
            this.str = str;
            this.index = index;
            this.total = total;
        }

        @Override
        public void run() {
            while (true){
                synchronized (count){
                    if (count.get()%total == index){
                        System.out.println(str);
                        count.getAndAdd(1);
                        count.notifyAll();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            count.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
