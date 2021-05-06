package com.github.cnkeep.interview.huawei;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/26
 * @version:
 **/
public class Main {
    /***
     * 字母反转 给定一个字符串 S，返回 “反转后的” 字符串，其中不是字母的字符都保留在原地，
     * 而所有字母的位置发生反转。
     *      示例 1：输入："ab-cd"
     *      输出："dc-ba"
     *
     *      示例 2：输入："a-bC-dEf-ghIj"
     *      输出："j-Ih-gfE-dCba"
     *
     *      示例 3：输入："Test1ng-Leet=code-Q!"
     *      输出："Qedo1ct-eeLg=ntse-T!"
     */

    /** 反转字符串（只反转字符部分）
     * @param string
     * @return
     */
    public static String reverseString(String string) {
        if (null == string || string.length() < 1) {
            return string;
        }

        char[] chars1 = new char[string.length()];
        Map<Integer, Character> noCharMap = new LinkedHashMap<>();
        int chars1Index = 0;

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                chars1[chars1Index] = c;
                chars1Index++;
            } else {
                noCharMap.put(i, c);
            }
        }

        // 反转
        for (int i = 0; i < chars1Index / 2; i++) {
            char tmp = chars1[i];
            chars1[i] = chars1[chars1Index - 1 - i];
            chars1[chars1Index - 1 - i] = tmp;
        }

        chars1Index = 0;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (noCharMap.containsKey(i)) {
                res.append(noCharMap.get(i));
            } else {
                res.append(chars1[chars1Index]);
                chars1Index++;
            }
        }

        return res.toString();
    }


    @Test
    public void reverserStringTest() {
        Assert.assertEquals("dc-ba", reverseString("ab-cd"));
        Assert.assertEquals("j-Ih-gfE-dCba", reverseString("a-bC-dEf-ghIj"));
        Assert.assertEquals("Qedo1ct-eeLg=ntse-T!", reverseString("Test1ng-Leet=code-Q!"));
        Assert.assertEquals("Qedo1ct-eeLg=nt se-T!", reverseString("Test1ng-Leet=co de-Q!"));
    }
}
