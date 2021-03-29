package com.github.cnkeep.algorithm;


import java.util.TreeSet;

public class Question_215 {
    public static int findKthLargest(int[] nums, int k) {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (tree.size() < k) {
                tree.add(nums[i]);
                continue;
            }
            if (tree.first() > nums[i] || tree.contains(nums[i])) {
                continue;
            }
            tree.add(nums[i]);
            tree.remove(tree.first());
        }
        return tree.first();
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 4, 5, 5, 3};
        int k = 2;
        System.out.println(findKthLargest(nums, k));
    }
}
