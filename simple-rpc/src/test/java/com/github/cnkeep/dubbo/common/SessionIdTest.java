package com.github.cnkeep.dubbo.common;

import org.junit.Test;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/8/22
 * @version:
 **/
public class SessionIdTest {

    public static long initializeNextSessionId(long id) {
        long nextSid;
        nextSid = ((System.nanoTime() / 1000000) << 24) >>> 8;
        nextSid = nextSid | (id << 56);
        if (nextSid == Long.MIN_VALUE) {
            ++nextSid;  // this is an unlikely edge case, but check it just in case
        }
        return nextSid;
    }

    @Test
    public void test1(){

    }
}
