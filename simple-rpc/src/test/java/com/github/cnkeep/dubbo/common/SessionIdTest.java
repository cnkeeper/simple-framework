package com.github.cnkeep.dubbo.common;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import org.junit.Rule;
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

    @Rule
    public JUnitPerfRule jUnitPerfRule = new JUnitPerfRule();
    /**
     * JUnitPerfTest测试组件，build/report目录下可以生成测试报告
     */
    @Test
    @JUnitPerfTest(threads = 50,durationMs = 1200,warmUpMs = 100,maxExecutionsPerSecond = 110)
    public void test1()  {
        String str = "1,2;3|5";
        String[] split = str.split("\\D");
        System.out.println(Arrays.toString(split));
        Method[] declaredMethods = SessionIdTest.class.getDeclaredMethods();
        System.out.println(Arrays.toString(declaredMethods));

    }
}
