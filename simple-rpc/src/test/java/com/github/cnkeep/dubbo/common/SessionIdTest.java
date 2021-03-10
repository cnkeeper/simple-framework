package com.github.cnkeep.dubbo.common;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/8/22
 * @version:
 **/
public class SessionIdTest {

    public  long initializeNextSessionId(long id) {
        long nextSid;
        nextSid = ((System.nanoTime() / 1000000) << 24) >>> 8;
        nextSid = nextSid | (id << 56);
        if (nextSid == Long.MIN_VALUE) {
            ++nextSid;  // this is an unlikely edge case, but check it just in case
        }
        return nextSid;
    }

    @Test
    public void test1() throws NoSuchMethodException {
        String str = "1,2;3|5";
        String[] split = str.split("\\D");
        System.out.println(Arrays.toString(split));
        Method[] declaredMethods = SessionIdTest.class.getDeclaredMethods();
        System.out.println(Arrays.toString(declaredMethods));

    }
}
