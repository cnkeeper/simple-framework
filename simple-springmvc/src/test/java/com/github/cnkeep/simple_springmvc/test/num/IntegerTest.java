package com.github.cnkeep.simple_springmvc.test.num;

import org.junit.Test;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/29
 * @version:
 **/
public class IntegerTest {

    @Test
    public void test1() {
        long x = 100L;
        System.out.println(Long.toBinaryString(x));
        System.out.println(Long.toBinaryString(-x));
        System.out.println(Long.toBinaryString(x & -x));
    }
}
