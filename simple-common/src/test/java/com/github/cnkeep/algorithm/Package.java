package com.github.cnkeep.algorithm;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description: 背包问题
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-28 11:29
 * @version: v0.0.1
 **/
public class Package {

    /**
     * Q: 01背包
     * 有 N 种物品，一个容量为 V 的背包，第 i 件物品的体积为 cap[i]，价值为 val[i]，求在不超过背包容量限制的情况下所能获得的最大物品价值和为多少？
     * 这种一件物品选或者不选的问题就称为01背包问题
     * <p>
     * 从i间物品中选择容量不超过j的最大价值, dp[i-1][j]-不选择第i件物品，dp[i-1][j-cap[i]]+val[i]-选择第i件物品
     * dp[i][j]=Max(dp[i-1][j], dp[i-1][j-cap[i]]+val[i]), j>=cap[i]
     * dp[i][j]=dp[i-1][j], j<cap[j]
     */
    @Test
    public void _01pack() {
        int N = 5;
        int maxCap = 12;
        int[] cap = new int[]{3, 1, 2, 4, 5};
        int[] val = new int[]{2, 4, 1, 5, 1};

        int[][] dp = new int[N + 1][maxCap + 1];
        Set<Integer> selected = new LinkedHashSet<>(N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < maxCap + 1; j++) {
                if (j >= cap[i]) {
                    dp[i + 1][j] = Math.max(dp[i][j], dp[i][j - cap[i]] + val[i]);
                    if (dp[i + 1][j] != dp[i][j]) {
                        selected.add(cap[i]);
                    }
                } else {
                    dp[i + 1][j] = dp[i][j];
                }
            }
        }

        System.out.println(dp[N][maxCap]);
    }

    /**
     * 上面的dp数组使用了2维数组, 有点浪费，画图发现，更新当前一行的值只需要知道上一行的值即可，所以可以简化只使用2个1维数组
     */
    @Test
    public void _01pack_1() {
        int N = 5;
        int maxCap = 12;
        int[] cap = new int[]{3, 1, 2, 4, 5};
        int[] val = new int[]{2, 4, 1, 5, 1};

        int[] pre = new int[maxCap + 1];
        int[] cur = new int[maxCap + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < maxCap + 1; j++) {
                if (j >= cap[i]) {
                    cur[j] = Math.max(pre[j], pre[j - cap[i]] + val[i]);
                } else {
                    cur[j] = pre[j];
                }
            }
            // 交换
            int[] tmp = pre;
            pre = cur;
            cur = tmp;
        }

        System.out.println(pre[maxCap]);
    }


    /**
     * 进一步压缩空间,
     * 假如第二个数组的更新从右向左开始，就可以只使用一个数组，因为数组左侧还是上一个数组的数据
     */
    @Test
    public void _01pack_2() {
        int N = 5;
        int maxCap = 12;
        int[] cap = new int[]{3, 1, 2, 4, 5};
        int[] val = new int[]{2, 4, 1, 5, 1};

        int[] cur = new int[maxCap + 1];
        for (int i = 0; i < N; i++) {
            for (int j = maxCap; j >= cap[i]; j--) {
                cur[j] = Math.max(cur[j], cur[j - cap[i]] + val[i]);
            }
        }
        System.out.println(cur[maxCap]);
    }


    /**
     * Question: 多重背包, 在01背包的基础上增加了每个物品的最大数量.
     * 有 N 种物品，一个容量为 V 的背包，第 i 件物品的体积为 cap[i]，价值为 val[i]，最大数量为num[i],
     * 求在不超过背包容量限制的情况下所能获得的最大物品价值和为多少？
     * <p>
     * 分析其本质也是01背包问题，只是每件物品的选择有num[i]中可能
     */
    @Test
    public void multiPack() {
        int N = 5;
        int maxCap = 12;
        int[] cap = new int[]{3, 1, 2, 4, 5};
        int[] val = new int[]{2, 4, 1, 5, 1};
        int[] num = new int[]{3, 1, 4, 2, 1};

        int[][] dp = new int[N + 1][maxCap + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < maxCap + 1; j++) {
                dp[i + 1][j] = dp[i][j];
                for (int k = 1; k < num[i]; k++) {
                    if (j >= k * cap[i]) {
                        dp[i + 1][j] = Math.max(dp[i][j], dp[i][j - k * cap[i]] + k * val[i]);
                    }
                }
            }
        }

        System.out.println(dp[N][maxCap]);
    }

    @Test
    public void multiPack_1() {
        int N = 5;
        int maxCap = 12;
        int[] cap = new int[]{3, 1, 2, 4, 5};
        int[] val = new int[]{2, 4, 1, 5, 1};
        int[] num = new int[]{3, 1, 4, 2, 1};

        int[] dp = new int[maxCap + 1];
        for (int i = 0; i < N; i++) {
            for (int k = 1; k < num[i]; k++) {
                for (int j = maxCap; j > k * cap[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - k * cap[i]] + k * val[i]);
                }
            }
        }

        System.out.println(dp[maxCap]);
    }


    /**
     * Question: 完全背包，N件物品，每件物品不限数量，每件价值val[i]，重量cap[i]
     * <p>
     * 说是不限数量, 其实还是有最大数量限制=maxCap/cap[i]
     */
    @Test
    public void completePack() {
        int N = 5;
        int maxCap = 12;
        int[] cap = new int[]{3, 1, 2, 4, 5};
        int[] val = new int[]{2, 4, 1, 5, 1};

        int[] dp = new int[maxCap + 1];
        for (int i = 0; i < N; i++) {
            for (int k = 1; k < maxCap / cap[i]; k++) {
                for (int j = maxCap; j > k * cap[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - k * cap[i]] + k * val[i]);
                }
            }
        }

        System.out.println(dp[maxCap]);
    }
}
