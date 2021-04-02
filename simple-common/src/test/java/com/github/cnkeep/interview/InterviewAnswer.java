package com.github.cnkeep.interview;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 试题解答
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/4/1
 * @version: 1.0.0
 **/
public final class InterviewAnswer {
    /**
     * <pre>
     * 问题：用java语言写一段java冒泡排序算法，并分析时间复杂度和空间复杂度
     *     时间复杂度：O(N^2), 2次循环
     *     最好：O(N), 已经有序时一次就完成排序
     *     空间复杂度：O(1), 只有中间变量
     * 思路： 每次和下个元素比较，大于则交换，单词循环后，第n个元素即为前n-1个最大值。再加一层循环挨个查找[1,n-1],[1,n-2]...[1,1]
     * (优化点，单次查找最大值却没有交换值，说明所有数据已经有序，直接退出)
     * </pre>
     *
     * @param nums
     */
    public static void bubbleSort(int[] nums) {
        if (nums == null || nums.length < 1) {
            return;
        }


        for (int i = 0; i < nums.length; i++) {
            boolean swapped = false;
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                    swapped = true;
                }
            }

            // 本次没有交换值，说明所有数据已经有序，不需要排了
            if (!swapped) {
                break;
            }
        }
    }


    /**
     * <pre>
     * 问题：
     *    输入“hello world java”，
     *    输出“java world hello”
     * 思路：按照空格分词，每个单词作为一个结点，将所有单词组翻转
     * </pre>
     *
     * @param line
     * @return
     */
    public static String reverseByWord(String line) {
        if (null == line || line.length() < 1) {
            return line;
        }

        String split = " ";
        // 拆分单词
        String[] words = line.split(split);
        if (words.length == 1) {
            return line;
        }

        // 词组原地翻转
        reverse(words);

        // 字符串拼接
        return join(split, words);
    }

    /**
     * 拼接数组元素
     *
     * @param split 连接符号
     * @param words 数组
     * @return
     */
    private static String join(String split, String[] words) {
        List<String> numbers = Arrays.asList(words);
        return numbers.stream().collect(Collectors.joining(split));
    }

    /**
     * 翻转数组
     *
     * @param words
     */
    private static void reverse(String[] words) {
        int len = words.length;
        int mid = len >>> 1;
        for (int index = 0; index < mid; index++) {
            String tmp = words[index];
            words[index] = words[len - 1 - index];
            words[len - 1 - index] = tmp;
        }
    }


    /**
     * <pre>
     * 问题：寻找正确的字符串中，没有重复字符的最大化子字符串。
     *     输入：java2novice，输出：a2novice
     *     输入：java_language_is_sweet，输出：uage_is
     * 思路：
     *  滑动窗口的思想, 窗口内的字符都是不重复的，不断的尝试扩大窗口，直到遇到重复字符，累计求最大的窗口;
     *  空间复杂度：O(字符集), 存储了中间遍历的字符
     *  时间复杂度：O(n), 一次遍历就结束了，for内部的while是从上次结束位置开始的
     * </pre>
     *
     * @param source
     * @return
     */
    public static String longestNoDuplicateSubstring(String source) {
        if (null == source || source.length() < 1) {
            return source;
        }


        Set<String> res = new LinkedHashSet<>();
        // 记录每个字符是否出现过
        Set<Character> visitedSet = new HashSet<>();
        int len = source.length();
        // 窗口右边缘，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int right = 0;
        // 开始位置
        int maxStart = -1;
        // 最大长度
        int maxLen = 0;

        for (int left = 0; left < len; left++) {
            if (left != 0) {
                // 窗口左侧收缩
                visitedSet.remove(source.charAt(left - 1));
            }

            while (right < len && !visitedSet.contains(source.charAt(right))) {
                // 窗口右侧扩张，直到遇见重复字符
                visitedSet.add(source.charAt(right));
                right++;
            }

            // [left,right]是一个无重复字符子串, 取历史最大值
            if ((right - left + 1) > maxLen) {
                maxStart = left;
                maxLen = right - left + 1;

                res.clear();
                res.add(source.substring(maxStart, maxStart + maxLen - 1));
            }else if((right - left + 1) == maxLen){
               // 相同长度窗口
                maxStart = left;
                maxLen = right - left + 1;
                res.add(source.substring(maxStart, maxStart + maxLen - 1));
            }
        }


        return source.substring(maxStart, maxStart + maxLen - 1);
    }


    @Test
    public void bubbleSortTest() {
        int[] nums = {0, 5, 1, 3, 1, 2};
        int[] numsCopy = Arrays.copyOf(nums, nums.length);

        bubbleSort(nums);
        // System.out.println(Arrays.toString(nums));
        Arrays.sort(numsCopy);
        Assert.assertEquals(Arrays.toString(numsCopy), Arrays.toString(nums));

        nums = new int[]{0, 1, 2, 3, 4};
        bubbleSort(nums);
        // System.out.println(Arrays.toString(nums));
        numsCopy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(numsCopy);
        Assert.assertEquals(Arrays.toString(numsCopy), Arrays.toString(nums));
    }


    @Test
    public void reverseWordTest() {
        String words = "hello world java";
        System.out.println();
        Assert.assertEquals("java world hello", reverseByWord(words));
        Assert.assertEquals("12", reverseByWord("12"));
        Assert.assertEquals("", reverseByWord(" "));
    }


    @Test
    public void longestNoDuplicateSubstringTest() {
//        Assert.assertEquals("a2novice", longestNoDuplicateSubstring("java2novice"));
//        Assert.assertEquals("uage_is", longestNoDuplicateSubstring("java_language_is_sweet"));
//        Assert.assertEquals("1234", longestNoDuplicateSubstring("haha111234"));
        Assert.assertEquals("cab", longestNoDuplicateSubstring("abcabcbb"));
    }
}
