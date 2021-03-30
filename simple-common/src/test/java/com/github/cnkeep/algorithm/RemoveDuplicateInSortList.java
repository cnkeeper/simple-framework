package com.github.cnkeep.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @description: 去除有序数组中的重复项
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/30
 * @version:
 **/
public class RemoveDuplicateInSortList {

    public static int[] removeDuplicate(int[] nums) {
        int index = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[index] != nums[j]) {
                index++;
                nums[index] = nums[j];
            }
        }
        return Arrays.copyOf(nums, index + 1);
    }


    @Test
    public void removeDuplicateTest() {
        int[] arr = {1, 2, 3, 4, 2, 1, 0, 3, 4, 3};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        int[] len = removeDuplicate(arr);
        System.out.println(Arrays.toString(len));
    }
}
