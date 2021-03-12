package com.github.cnkeep.dubbo.leetcode;

import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/11
 * @version:
 **/
public class KthNum {
    public static int findKthLargest(int[] nums, int k) {
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
        int[] arr = {3,2,3,1,2,4,5,5,6};
        int kthLargest = findKthLargest(arr, 4);
        System.out.println(kthLargest);

        int cap = 17;
        System.out.println(Integer.toBinaryString(cap));
        int n = cap;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println(Integer.toBinaryString(n));
    }
}
