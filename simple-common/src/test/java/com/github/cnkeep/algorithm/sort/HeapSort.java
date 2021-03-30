package com.github.cnkeep.algorithm.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/29
 * @version:
 **/
public class HeapSort {
    public static void heapSort(int[] nums) {
        /**
         * 构建自下而上
         * 调整自上而下
         */
        for (int i = nums.length / 2; i >= 0; i--) {
            //从最后一个非叶子结点开始，自下而上的构建堆
            heapify(nums, i, nums.length);
        }

        // 每次将堆顶元素放到最后，重新开始调整
        for (int i = nums.length - 1; i >= 0; i--) {
            swap(nums, 0, i);
            heapify(nums, 0, i);
        }
    }

    private static void heapify(int[] nums, int i, int length) {
        int curParentNode = i;
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // max(left, right) 如果左子结点小于右子结点，k指向右子结点
            if (k + 1 < length && nums[k] < nums[k + 1]) {
                k++;
            }

            // 子节点最大值和父节点交换
            if (nums[k] > nums[curParentNode]) {
                swap(nums, curParentNode, k);
                curParentNode = k;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {0, 5, 1, 3, 1, 2};
        heapSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static class Mail {

    }

    public List<Mail> search(List<Mail> dataFlow, List<Predicate<Mail>> conditions) {
        if (dataFlow == null || dataFlow.isEmpty()) {
            return Collections.emptyList();
        }

        return dataFlow.parallelStream()
                .filter(mail -> doMatch(mail, conditions))
                .collect(Collectors.toList());
    }

    private boolean doMatch(Mail mail, List<Predicate<Mail>> conditions) {
        return conditions.parallelStream().allMatch(cond -> cond.test(mail));
    }
}
