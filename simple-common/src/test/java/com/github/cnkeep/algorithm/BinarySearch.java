package com.github.cnkeep.algorithm;

import org.junit.Test;

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
                index = left;
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
                index = right;
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
}
