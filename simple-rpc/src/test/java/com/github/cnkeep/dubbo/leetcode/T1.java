package com.github.cnkeep.dubbo.leetcode;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Random;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/1/26
 * @version:
 **/
public class T1 {
    public static int[] randomArr(int len) {
        Random random = new Random();
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10);
        }

        return arr;
    }

    /**
     * 题目：寻找最近一个大于自己的数
     * 求解：单调栈(栈顶->栈底，单调递增)
     * 1.单调递增栈可以求右侧最近的大于自己的值，出栈时栈顶元素的右侧最近大值就确定了，就是当前值
     * 2.单调递减栈可以求左侧最近小于自己的值，入栈时栈顶元素就是当前值的最近小值
     *
     */
    @Test
    public void test() {
        int[] arr = randomArr(10);
        System.out.println(Arrays.toString(arr));

        Deque<Integer> stack = new ArrayDeque<Integer>();
        stack.push(0);
        int[] res = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            // 栈空/当前元素<栈顶元素，入栈
            if (stack.isEmpty() || arr[stack.peek()] >= arr[i]) {
                stack.push(i);
            } else {
                // 当前元素>=栈顶元素, 栈顶元素弹出
                while (!stack.isEmpty() && !(arr[stack.peek()] > arr[i])) {
                    int top = stack.pop();
                    res[top] = i;
                }
                stack.push(i);
            }
        }

        System.out.println(Arrays.toString(res));
    }
}
