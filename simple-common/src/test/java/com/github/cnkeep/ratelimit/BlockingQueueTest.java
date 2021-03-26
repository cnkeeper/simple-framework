package com.github.cnkeep.ratelimit;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @description: BlockingQueueTest
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/26
 * @version:
 **/
public class BlockingQueueTest {

    @Test
    public void takeInterruptTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        countDownLatch.await();
    }
}
