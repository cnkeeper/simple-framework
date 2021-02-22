package com.github.cnkeep.dubbo.leetcode;


import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Question_2 {
    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * 你可以按任意顺序返回答案。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/two-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];
            Integer index = map.get(key);
            if (null != index && index != i) {
                res[0] = i;
                res[1] = index;
                break;
            }
        }

        return res;
    }

    @Test
    public void test() {
        int[] nums = {3, 2, 4};
        int target = 6;
        int[] res = twoSum(nums, target);
        System.out.println(Arrays.toString(res));
    }

    /**
     * 二分法查找第一个大于target的值
     *
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearchFirstGt(int[] nums, int target) {
        int index = -1;
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // 1,2,2,2,3,4,5

        return index;
    }

    /**
     * 二分法查找第一个小于target的值
     *
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearchFirstLt(int[] nums, int target) {
        int index = -1;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >>> 1);
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // 1,2,2,2,3,4,5

        return index;
    }

    @Test
    public void test1() {
        int[] nums = {1, 2, 2, 2, 3, 4, 5};
        int target = 2;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = ((right - left) >> 1) + left;
            if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        System.out.println(left);
    }

    @Test
    public void searchRange() {
        int[] nums = {5,7,7,8,8,10};
        int target = 8;
        int left = search(nums,target,true);
        int right = search(nums,target,false)-1;
        System.out.println();
    }

    public int search(int[] nums, int target,boolean lower) {
        int left = 0;
        int right = nums.length-1;
        while(left<=right){
            int mid = ((right-left)>>>1)+left;
            if(nums[mid]>target ||(lower && nums[mid]==target)){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        return left;
    }
}
