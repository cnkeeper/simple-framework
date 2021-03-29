package com.github.cnkeep.algorithm;

import java.util.PriorityQueue;

/**
 * @description: TopN求解
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/11
 * @version:
 **/
public class KthNum {
    /**
     * 求TopN，使用堆结构
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest(int[] nums, int k) {
        // 这里使用PriorityQueue而不是TreeMap是因为要考虑数据存在重复的情况
        PriorityQueue<Integer> tree = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            if (tree.size() < k) {
                tree.add(nums[i]);
                continue;
            }
            if (tree.peek() > nums[i]) {
                continue;
            }
            tree.add(nums[i]);
            tree.poll();
            System.out.println(tree);
        }
        return tree.peek();
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        int kthLargest = findKthLargest(arr, 4);
        System.out.println(kthLargest);
    }
}
