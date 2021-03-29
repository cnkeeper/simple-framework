package com.github.cnkeep.algorithm;

import org.junit.Test;

/**
 * @description: 二分法搜索
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/29
 * @version:
 **/
public class BinarySearch {

    /**
     * 二分法查找第一个大于target的值
     *
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearchFirstGt(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] == target) {
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // 1,2,2,2,3,4,513010000179

        // 求第一个返回left, 请最后一个返回right
        return left >= nums.length ? -1 : left;
    }

    /**
     * 二分法查找最后一个小于target的值
     *
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearchFirstLt(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >>> 1);
            if (nums[mid] == target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 1,2,2,2,3,4,5

        return right < 0 ? -1 : right;
    }

    @Test
    public void test1() {
        int[] nums = {1, 2, 2, 2, 3, 4, 5};
        int target = 10;
        int index = binarySearchFirstLt(nums, target);
        index = binarySearchFirstGt(nums, target);

        System.out.println(index);
    }

    @Test
    public void searchRange() {
        int[] nums = {5, 7, 7, 8, 8, 10};
        int target = 8;
        int left = search(nums, target, true);
        int right = search(nums, target, false) - 1;
        System.out.println();
    }

    public int search(int[] nums, int target, boolean lower) {
        int left = 0;
        int right = nums.length - 1;
        // 截止条件[right,left] left=right+1
        while (left <= right) {
            int mid = ((right - left) >>> 1) + left;
            if (nums[mid] > target || (lower && nums[mid] == target)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
