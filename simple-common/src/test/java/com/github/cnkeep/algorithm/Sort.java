package com.github.cnkeep.algorithm;


import java.util.Arrays;

public class Sort {
    /**
     * 冒泡排序
     * 时间复杂度：O(N^2)
     * 最好：O(N)
     *
     * @param nums
     */
    public static void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                }
            }
        }
    }


    public static void insertSort(int[] nums) {
        int preIndex;
        for (int i = 1; i < nums.length; i++) {
            preIndex = i - 1;
            int current = nums[i];
            while (preIndex >= 0 && nums[preIndex] < current) {
                nums[preIndex + 1] = nums[preIndex];
                preIndex--;
            }
            nums[preIndex + 1] = current;
        }
    }

    public static void selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = tmp;
        }
    }

    /**
     * 快速排序
     * 时间复杂度：O(nlogn)
     *
     * @param nums
     */
    public static void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    private static void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(nums, left, right);
            quickSort(nums, left, partitionIndex - 1);
            quickSort(nums, partitionIndex + 1, right);
        }

        return;
    }

    private static int partition(int[] nums, int left, int right) {
        int pivot = left;
        int index = left + 1;
        for (int i = index; i <= right; i++) {
            if (nums[i] < nums[pivot]) {
                swap(nums, i, index);
                index++;
            }
        }
        swap(nums, pivot, index - 1);
        return index - 1;
    }


    public static void mergeSort(int[] nums) {

    }


    public static void heapSort(int[] nums) {
        buildMaxHeap(nums);
        for (int i = nums.length - 1; i >= 0; i--) {
            swap(nums, 0, i);
            heapify(nums, 0, i);
        }
    }

    private static void buildMaxHeap(int[] nums) {
        /**
         * 从最后一个非叶子结点开始，自下而上的构建堆
         */
        for (int i = nums.length / 2; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }
    }

    private static void heapify(int[] nums, int i, int length) {
        int temp = nums[i];
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // max(left, right) 如果左子结点小于右子结点，k指向右子结点
            if (k + 1 < length && nums[k] < nums[k + 1]) {
                k++;
            }

            if (nums[k] > temp) {
                nums[i] = nums[k];
                i = k;
            }
        }
        nums[i] = temp;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {0, 5, 1, 3, 1, 2};
        quickSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
