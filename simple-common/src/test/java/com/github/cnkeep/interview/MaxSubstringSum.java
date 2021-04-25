package com.github.cnkeep.interview;

import org.junit.Test;

import java.util.Scanner;

public class MaxSubstringSum {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String numStr = in.nextLine();
            String sumStr = in.nextLine();
            String[] split = numStr.split(",");
            if (split.length < 1) {
                System.out.println(-1);
                continue;
            }
            int[] nums = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                nums[i] = Integer.parseInt(split[i]);
            }
            int sum = Integer.parseInt(sumStr);
            int result = findMaxLength(nums, sum);
            System.out.println(result);
        }
    }

    public static int findMaxLength(int[] nums, int sum) {
        int ans = -1;
        int n = nums.length;

        for (int left = 0; left < n; left++) {
            for (int right = left; right < n; right++) {
                if (sum == getSum(nums, left, right)) {
                    ans = Math.max(ans, right - left + 1);
                }
            }
        }
        return ans;
    }

    public static int getSum(int[] nums, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += nums[i];
        }
        return sum;
    }

    @Test
    public void longestSameSubStrTest() {
        String str1 = "1abd";
        String str2 = "111111abc";
        System.out.println(longestSameSubStr(str1, str2));
    }

    /***
     * dp[i][j]=dp[i-1][j-1]+1, s1[i-1]=s2[j-1]
     * dp[i][j]=1, s1[i-1]!=s2[j-1]
     * 求连续最长公共子序列。
     * 存在返回公共子序列，不存在返回null
     **/
    public static String longestSameSubStr(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return null;
        }

        int len1 = str1.length();
        int len2 = str2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        int maxLen = 0;
        int maxEndIndex = -1;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    // 连续累加计数
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 不连续重置计数
                    dp[i][j] = 1;
                }
                if (maxLen < dp[i][j]) {
                    // 更新最大值
                    maxLen = dp[i][j];
                    maxEndIndex = i;
                }
            }
        }
        //
        if (maxLen == 0 || maxEndIndex == -1) {
            return null;
        }
        return str1.substring(maxEndIndex - maxLen, maxEndIndex);
    }
}