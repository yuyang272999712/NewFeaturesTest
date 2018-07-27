package com.example.sortAlgorithm;

/**
 * ZHU yuyang 排序算法请参考：http://blog.csdn.net/gane_cheng/article/details/52652705
 */

public class SortTest {
    private static int[] a = new int[]{12,34,7,32,67,0,22,16,89,2,23,90,50,2};

    public static void main(String[] args){
        insertSort(a);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
    }

    /**
     * 插入排序 start
     *  直接插入
     *    将一个记录插入到已经排序的有序表中，从而得到一个新的、记录数增加1的有序表。其处理过程是，在排序刚开始的时候，
     *  把第一个元素当做是排序的记录，当依次插入后面的元素的时候，就获得其插入的位置，然后形成一个新的有序表。
     *  时间复杂度：O(n)~O(n2)（n的平方）
     *  空间：O(1)
     */
    public static void insertSort(int[] num){
        for (int i=1; i<num.length; i++){
            int temp = num[i];
            int j;
            for (j=i-1; j>=0; j--) {
                if (num[j] > temp) {
                    num[j+1] = num[j];
                }else {
                    break;
                }
            }
            num[j+1] = temp;
        }
    }

    /**
     * 堆排序 start
     *  实现堆排序的基本思想是：将待排序的序列构造成一个大顶堆或者小顶堆。此时整个堆满足根节点是最大值或者最小值。将根节点移走，
     *  并与堆数组的最后一个值进行交换，这样的话最后一个值就是最大值或者最小值了。然后将剩余n-1（假设原来总共有n个元素）
     *  未排序的序列重新构造成一个最大堆或者最小堆，再与除原来最大值之外的最后一个元素进行交换，得到次小值。如此反复进行，就得到一个排序的序列。
     *  时间O(nlogn)
     *  O(1)
     */
    public static void headSort(int[] num){
        for (int i = 0; i<num.length-1; i++){
            buildBigHead(num, num.length-i-1);
            swap(num, 0, num.length-i-1);
        }
    }

    public static void buildBigHead(int[] num, int lastIndex){
        for (int i=(lastIndex-1)/2; i>=0; i--){
            int k = i;
            while ((k*2)+1<=lastIndex){
                int bigIndex = k*2+1;
                if (bigIndex<lastIndex){
                    if (num[bigIndex] < num[bigIndex+1]) {
                        bigIndex += 1;
                    }
                }
                if (num[k]<num[bigIndex]){
                    swap(num, k, bigIndex);
                    k = bigIndex;
                }else {
                    break;
                }
            }
        }
    }
    /**堆排 end*****************/

    /**
     * 简单选择 start
     * 第i趟排序需要比较n−i次关键字的比较，所以总共需要比较n−1+n−2+...+1=n(n−1)2次。
     * 最好的情况下，交换0次，最差的情况是交换n−1次，所以最终的时间复杂度是O(n2)（n的平方）。
     * 空间复杂度O(1)
     */
    public static void selectSort(int[] num){
        for (int i=0; i<num.length; i++){
            int tempIndex = i;
            for (int j=i+1; j<num.length; j++){
                if (num[j] < num[tempIndex]){
                    tempIndex = j;
                }
            }
            swap(num, i, tempIndex);
        }
    }
    /**简单选择 end********************/

    /**
     * 冒泡 start
     *  在最坏的情况就是待排序的记录是逆序的，此时的时间复杂度是O(n2)；
     *  最好的情况是，排序表本身就是有序的，那么在这种情况下，时间复杂度是O(n)。
     *  空间复杂度O(1)
     *****************************
     */
    public static void bubbleSort(int[] num){
        printResult(num);
        boolean flag = false;
        for (int i=0; i<num.length; i++){
            for (int j=0; j<num.length-1-i; j++){
                if (a[j]>a[j+1]){
                    swap(num, j, j+1);
                    flag = true;
                    printResult(num);
                }
            }
            if (!flag){
                break;
            }
        }
    }
    /**冒泡 end*********************/

    /**
     * 快速排序 start
     *   快速排序算法的最优时间复杂度是O(nlogn)，在如下的情况下是最优的：对于一组具有n个关键字的记录，partition函数每次都划分得很均匀，也就是每次调用partition函数之后，比枢轴小的关键字和比枢轴大的关键字的数量基本相等。这样下次调用partition函数的时候所用的时间是上次调用partition函数的两倍加上比较元素的时间，所以这是最优的情况。
         在最坏的情况指的就是每次调用partition函数，只得到一个比上一次划分少一个记录的子序列（因为只要调用partition函数，必须至少会有一次关键字的交换）。由于只减少了一个关键字，所以后面还需要进行n-1次（总共有n个关键字）递归调用partition函数。所以到最后总共需要比较的次数是∑n−1i=1=n−1+n−2+...+1=n(n−1)2，所以最终的时间复杂度是O(n2)。
         在平均的情况下，枢轴的关键字应该在k位置(1≤k≤n)。所以最优情况下的时间复杂度是O(nlogn)。
         那么快速排序算法的空间复杂度又是如何呢？最优和平均情况下空间复杂度都是O(logn)，在最坏情况就是n−1次调用，所以空间复杂度是O(n)。
     **************************/
    public static void quickSort(int[] num, int start, int end){
        if (start < end){
            int mid = getMid(num, start, end);
            quickSort(num, start, mid-1);
            quickSort(num, mid+1, end);
        }
    }

    public static int getMid(int[] a, int start, int end){
        int temp = a[start];
        while (start < end){
            while (start<end && a[end]>=temp){
                end--;
            }
            a[start] = a[end];
            while (start<end && a[start]<=temp){
                start++;
            }
            a[end] = a[start];
        }
        a[start] = temp;
        return start;
    }
    /**快速排序 end******************/

    /**归并排序 start
     * 1）稳定性
     　归并排序是一种稳定的排序。
     （2）存储结构要求
     　可用顺序存储结构。也易于在链表上实现。
     （3）时间复杂度
     　对长度为n的文件，需进行趟二路归并，每趟归并的时间为O(n)，故其时间复杂度无论是在最好情况下还是在最坏情况下均是O(nlgn)。
     （4）空间复杂度
     　 需要一个辅助向量来暂存两有序子文件归并的结果，故其辅助空间复杂度为O(n)，显然它不是就地排序。
     * *********************************/
    public static void mergeSort(int[] a){
        mSort(a, 0, a.length - 1);
    }

    private static void mSort(int a[], int left, int right){
        int mid = 0;
        if (left<right){
            mid = (left+right)/2;
            //将数组a分为a[left...mid]并进行排序
            mSort(a,left,mid);
            //数组a[mid+1...right]部分进行排序
            mSort(a,mid+1,right);
            //将a[i...mid]部分和a[mid+1...j]部分的排序结果归并到a
            merge(a,left,mid,right);
        }
    }

    private static void merge(int[] a, int left, int mid, int right){
        int[] temp = new int[right-left+1];
        int k = 0;
        int i = left;
        int j = mid+1;
        while (i <= mid && j<=right){
            if (a[i]<a[j]){
                temp[k++] = a[i++];
            }else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while(i<=mid){
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while(j<=right){
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖a数组
        for(int x=0;x<temp.length;x++){
            a[x+left] = temp[x];
        }
    }
    /**归并排序 end*********************************/

    private static void swap(int[] num, int i, int j){
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    private static void printResult(int[] num){
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" ");
        System.out.println();
    }

}
