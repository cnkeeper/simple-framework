package com.github.cnkeep.algorithm;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * @description: 二分法搜索
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/29
 * @version:
 **/
public class BinarySearch {
    public static int find(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分法查找第一个大于target的值（右边界，不包括target）
     *
     * @param nums
     * @param target
     * @return
     */
    public static int findRightBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] == target) {
                left = mid + 1;
//                index = mid; // 包含target
                index = left; // 不包含target
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }

    /**
     * 二分法查找最后一个小于target的值(左边界，不包括target)
     *
     * @param nums
     * @param target
     * @return
     */
    public static int findLeftBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        while (left <= right) {
            int mid = left + ((right - left) >>> 1);
            if (nums[mid] == target) {
                right = mid - 1; // 查找区间[left,right], 所以while使用left<=right保证let=right也能查找
//                index = mid;//包含target
                index = right;// 不包含target
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }


    @Test
    public void test1() {
        int[] nums = {1, 2, 2, 2, 3, 4, 5};
        int target = 2;
        int leftBound = findLeftBound(nums, target);
        int rightBound = findRightBound(nums, target);

        System.out.println();
    }

    @Test
    public void searchTest() {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target = 9;
        int search = search(nums, target);
        System.out.println(search);
    }

    public static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = ((right - left) >>> 1) + left;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    @Test
    public void searchRange() {
        int[] nums = {-1, 0, 3, 5, 9, 9, 9, 12};
        int target = 9;
        int leftIdx = findLeftBound(nums, target);
        int rightIdx = findRightBound(nums, target);
        System.out.println(MessageFormat.format("[{0},{1}]",leftIdx,rightIdx));
    }


}
