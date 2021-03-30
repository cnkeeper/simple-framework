package com.github.cnkeep.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序：
 * 平均时间复杂度: nlogn
 * 最差时间复杂度: n^2 (基本有序的时候最差)
 * 空间复杂度：nlogn
 */
public class QuickSort {

    public static void sort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    private static void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int partition = partition(nums, left, right);
            quickSort(nums, left, partition - 1);
            quickSort(nums, partition + 1, right);
        }
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

    private static void swap(int[] arr, int sourceIndex, int destIndex) {
        int tmp = arr[sourceIndex];
        arr[sourceIndex] = arr[destIndex];
        arr[destIndex] = tmp;
    }

    public static void main(String[] args) {
        int[] nums = {0, 5, 1, 3, 1, 2, 1, 1};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }
}