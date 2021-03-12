package com.github.cnkeep.ratelimit;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/3/12
 * @version:
 **/
public class BucketRateLimitTest {

    @Rule
    public JUnitPerfRule rule = new JUnitPerfRule();

    RateLimit rateLimit = new BucketRateLimit(10);

    @Test
    @JUnitPerfTest(threads = 2, maxExecutionsPerSecond = 5, durationMs = 5 * 1000, warmUpMs = 10)
    public void acquire() {
        System.out.println(rateLimit.acquire());
    }
}