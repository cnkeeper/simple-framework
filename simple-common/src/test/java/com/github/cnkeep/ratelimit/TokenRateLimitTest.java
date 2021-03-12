package com.github.cnkeep.ratelimit;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import org.junit.Rule;
import org.junit.Test;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/12
 * @version:
 **/
public class TokenRateLimitTest {

    @Rule
    public JUnitPerfRule rule = new JUnitPerfRule();

    TokenRateLimit rateLimit = new TokenRateLimit(10);

    @Test
    @JUnitPerfTest(threads = 2, maxExecutionsPerSecond = 10, durationMs = 5 * 1000, warmUpMs = 10)
    public void acquire() {
        System.out.println(rateLimit.acquire());
    }
}