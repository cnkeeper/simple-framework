package com.github.cnkeep.dubbo.leetcode;

import java.util.Arrays;

public class QuickSort {

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int base = partition(arr, left, right);
            sort(arr, left, base - 1);
            sort(arr, base + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int base = left;
        int index = left + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] <= arr[base]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, base, index - 1);
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