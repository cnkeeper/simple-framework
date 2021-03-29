package com.github.cnkeep.algorithm;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Question_316 {
    /**
     * Question: 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
     * 1. 假设从字符串中删除一个字符，要保证结果字符串字典序最小，最好就保证字符间是递增的，找到一个破坏递增的位置删除.这种递增需求最适合用单调栈
     * 我们从左到右遍历字符串，递增式入栈，遇到小于栈顶的元素，说明递增被破坏，删除栈顶一直到栈顶元素小于当前元素
     * <p>
     * 为了实现数据去重，则栈中已经存在的值不入栈；
     * 为了保证留下的字符是所有不重复的字符，则假如后面还有栈顶字符，则栈顶字符不弹出
     *
     * @param str
     * @return
     */
    public static String removeDuplicateLetters(String str) {
        char[] chars = str.toCharArray();
        boolean[] visited = new boolean[26];
        int[] counter = new int[26];

        for (int i = 0; i < chars.length; i++) {
            counter[chars[i] - 'a']++;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < chars.length; i++) {
            counter[chars[i] - 'a']--;
            if (visited[chars[i] - 'a']) {
                // 栈中已经存在不处理
                continue;
            }

            while (!stack.isEmpty() && stack.peek() > chars[i]) {
                if (counter[stack.peek() - 'a'] > 0) {
                    Character top = stack.pop();
                    visited[top - 'a'] = false;
                } else {
                    break;
                }
            }

            stack.push(chars[i]);
            visited[chars[i] - 'a'] = true;
        }

        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        return res.reverse().toString();
    }

    @Test
    public void solution() {
        System.out.println(removeDuplicateLetters("bbcaac"));
    }

    @Test
    public void minCoin() {
        int res = 10;
        int[] coins = {5, 2, 1};

        // dp[i]=min(dp[i-1], dp[i-2], dp[i-5])+1
        int[] dp = new int[res + 1];

        for (int i = 1; i < dp.length; i++) {
            dp[i] = i;
            for (int coin : coins) {
                if (i >= coin) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }

        System.out.println(Arrays.toString(dp));
    }
}
