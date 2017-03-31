package com.example;

/**
 * Created by yuyang on 2017/3/2.
 */

public class SortTest {
    private static int[] nums = new int[]{4,7,11,19,3,6,45,23,0};

    public static void main(String[] args){
        int two[] = getTwoNum9(nums);
        System.out.println(two[0]+","+two[1]);
//        kuaipai(nums, 0, nums.length-1);
//        maopao(nums);
//        xuanze(nums);
        charu(nums);
        for(int i=0;i<nums.length;i++)
            System.out.print(nums[i]+" ");

        System.out.println();
        int j = 0;
        for(int i = 0; i < 100; i++)
            j = j++;
        System.out.println(j);
    }

    private static void charu(int[] a){
        for (int i=1;i<a.length;i++){
            int temp = a[i];
            int j;
            for (j=i-1; j>=0;j--){
                if (temp < a[j]){
                    a[j+1] = a[j];
                }else {
                    break;
                }
            }
            a[j+1] = temp;
        }
    }

    private static void xuanze(int[] a){
        for (int i=0; i<a.length; i++){
            int tempIndex = i;
            for (int j=i+1; j<a.length; j++){
                if (a[tempIndex] > a[j]){
                    tempIndex = j;
                }
            }
            int temp = a[i];
            a[i] = a[tempIndex];
            a[tempIndex] = temp;
        }
    }

    private static void maopao(int[] a){
        int len = a.length;
        for (int i = 0; i < len; i++) {
            for (int j = 1; j < len - i; j++) {
                if (a[j - 1] > a[j]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
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
            kuaipai(nums, start, middle-1);
            kuaipai(nums, middle+1, end);
        }
    }

    private static int getMiddle(int[] nums, int start, int end){
        System.out.println(start);
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
}
