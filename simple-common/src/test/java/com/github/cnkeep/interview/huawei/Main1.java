package com.github.cnkeep.interview.huawei;

import org.junit.Test;

import java.util.Arrays;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/29
 * @version:
 **/
public class Main1 {
    /***
     * 给定礼物价值列表Values和一个总金额amount。写一个方法来计算可以凑成总金额所需的最少礼物数。
     * 备注：礼物的数量是无限的。
     * **示例：**
     * **输入：**Values = [1, 2, 5], amount = 11
     * **输出：**3
     * **解释：**11 = 5 + 5 + 1
     *
     * 状态转移方程：
     * amount = n1*v1+n2*v2+...
     * dp[i]=min(dp[i-v1]+1, dp[i-v2]+1,....)
     * dp[5] = 1
     * dp[3] = min(dp[3-2]+1,dp[3-1]+1)
     * dp[2] = 2
     * dp[1] = 1
     */
    /**
     * @param values
     * @param amount
     * @return
     */
    public static int findMinGiftCount(int[] values, int amount) {
        // 1.先按照价值排序
        Arrays.sort(values);

        // 2. dp初始值设置（从最小的开始算）
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < values.length; j++) {
                if (values[j] > i) {
                    // 越界处理
                    continue;
                }

                // dp[i]=min(dp[i-v1]+1, dp[i-v2]+1,....)
                dp[i] = Math.min(dp[i - values[j]] + 1, dp[i]);
            }
        }

        System.out.println(Arrays.toString(dp));

        return dp[amount] > amount ? -1 : dp[amount];
    }

    @Test
    public void findMinGiftCountTest() {
        int[] values = new int[]{2, 3, 4, 10};

        System.out.println(findMinGiftCount(values, 12));
//        Assert.assertEquals(0, findMinGiftCount(values, 0));
//        Assert.assertEquals(1, findMinGiftCount(values, 1));
//        Assert.assertEquals(1, findMinGiftCount(values, 2));
//        Assert.assertEquals(2, findMinGiftCount(values, 3));
//        Assert.assertEquals(2, findMinGiftCount(values, 4));
//        Assert.assertEquals(1, findMinGiftCount(values, 5));
//        Assert.assertEquals(2, findMinGiftCount(values, 6));
//        Assert.assertEquals(2, findMinGiftCount(values, 7));
//        Assert.assertEquals(3, findMinGiftCount(values, 8));
//        Assert.assertEquals(3, findMinGiftCount(values, 9));
//        Assert.assertEquals(2, findMinGiftCount(values, 10));
//        Assert.assertEquals(3, findMinGiftCount(values, 11));
//        Assert.assertEquals(3, findMinGiftCount(values, 12));


//        values = new int[]{1, 1, 2, 5};
//        Assert.assertEquals(0, findMinGiftCount(values, 0));
//        Assert.assertEquals(1, findMinGiftCount(values, 1));
//        Assert.assertEquals(1, findMinGiftCount(values, 2));
//        Assert.assertEquals(2, findMinGiftCount(values, 3));
//        Assert.assertEquals(2, findMinGiftCount(values, 4));
//        Assert.assertEquals(1, findMinGiftCount(values, 5));
//        Assert.assertEquals(2, findMinGiftCount(values, 6));
//        Assert.assertEquals(2, findMinGiftCount(values, 7));
//        Assert.assertEquals(3, findMinGiftCount(values, 8));
//        Assert.assertEquals(3, findMinGiftCount(values, 9));
//        Assert.assertEquals(2, findMinGiftCount(values, 10));
//        Assert.assertEquals(3, findMinGiftCount(values, 11));
//        Assert.assertEquals(3, findMinGiftCount(values, 12));
//
//        values = new int[]{3, 1, 2, 5};
//        Assert.assertEquals(0, findMinGiftCount(values, 0));
//        Assert.assertEquals(1, findMinGiftCount(values, 1));
//        Assert.assertEquals(1, findMinGiftCount(values, 2));
//        Assert.assertEquals(1, findMinGiftCount(values, 3));
//        Assert.assertEquals(2, findMinGiftCount(values, 4));
//        Assert.assertEquals(1, findMinGiftCount(values, 5));
//        Assert.assertEquals(2, findMinGiftCount(values, 6));
//        Assert.assertEquals(2, findMinGiftCount(values, 7));
//        Assert.assertEquals(3, findMinGiftCount(values, 8));
//        Assert.assertEquals(3, findMinGiftCount(values, 9));
//        Assert.assertEquals(2, findMinGiftCount(values, 10));
//        Assert.assertEquals(3, findMinGiftCount(values, 11));
//        Assert.assertEquals(3, findMinGiftCount(values, 12));
    }
}
