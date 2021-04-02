package com.github.cnkeep.algorithm;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Question_215 {
    /**
     * 第K大数，不存在并列排名
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest(int[] nums, int k) {
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (tree.size() < k) {
                tree.add(nums[i]);
                continue;
            }
            if (tree.contains(nums[i])) {
                // 去重, 并列第一的情况
                continue;
            }
            if (tree.first() > nums[i]) {
                continue;
            }
            tree.add(nums[i]);
            tree.pollFirst();
        }
        return tree.first();
    }

    /**
     * 第K大数，存在并列排名
     *
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest1(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            if (queue.size() < k) {
                queue.add(nums[i]);
                continue;
            }

            if (queue.peek() > nums[i]) {
                continue;
            }
            queue.add(nums[i]);
            queue.remove(queue.peek());
        }
        return queue.peek();
    }

    /**
     * 取最小的k个数
     *
     * @param nums
     * @param k
     * @return
     */
    public static int[] getLeastNumbers(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -1 * o1.compareTo(o2);
            }
        });

        for (int i = 0; i < nums.length; i++) {
            if (queue.size() < k) {
                queue.add(nums[i]);
                continue;
            }
            if (queue.peek() < nums[i]) {
                continue;
            }
            queue.add(nums[i]);
            queue.remove(queue.peek());
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = queue.poll();
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 2, 4, 2, 2, 3, 1, 4};
        int k = 8;
        System.out.println(findKthLargest(nums, k));
        getLeastNumbers(nums, k);
    }
}
