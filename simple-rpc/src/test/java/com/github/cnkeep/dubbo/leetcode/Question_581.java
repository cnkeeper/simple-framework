package com.github.cnkeep.dubbo.leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-26 22:37
 * @version:
 **/
public class Question_581 {

    /**
     * Q-581:给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
     * 请你找出符合题意的 最短 子数组，并输出它的长度。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray
     *
     * 整个数组可以分为3个部分：左侧递增区，中间乱序区，右侧递增区, 所以问题的关键就是如何确定中间区域的边界。
     * 左侧的最大值，一定小于乱序区的最小值；
     * 右侧的最小值，一定大于乱序区的最大值;
     * 我们的目标就是找到不满足这2个条件的极值.
     */
    @Test
    public void findUnsortedSubarray() {
        int[] nums = new int[]{2, 6, 4, 8, 10, 9, 15};
        int min = nums[0];
        int max = nums[nums.length - 1];
        int begin = 0;
        int end = 0;
        for (int i = 0; i < nums.length; i++) {
            // 从左到右找到最后一个破坏递增的位置
            if (nums[i] < max) {
                end = i;
            }
            max = Math.max(nums[i], max);

            // 从右到左找到最后一个破坏递减的位置
            if (nums[nums.length - i - 1] > min) {
                begin = nums.length - i - 1;
            }
            min = Math.min(nums[nums.length - i - 1], min);
        }

        int[] res = new int[end - begin];
        System.arraycopy(nums, begin, res, 0, res.length);
        System.out.println(Arrays.toString(res));
    }
}
