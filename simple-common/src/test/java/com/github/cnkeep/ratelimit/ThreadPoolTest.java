package com.github.cnkeep.ratelimit;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
                1,
                20, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(1),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true && !Thread.currentThread().isInterrupted()){
                    System.out.println(System.currentTimeMillis());
                }
            }
        });

        System.out.println(">>>>>>>>>>>>>>>>>>>");
        executor.shutdownNow();
        while (true){
            System.out.println(executor.isTerminated());
        }
    }
}
