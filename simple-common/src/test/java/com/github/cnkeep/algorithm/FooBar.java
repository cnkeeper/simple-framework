package com.github.cnkeep.algorithm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private AtomicLong counter = new AtomicLong(0);


    public void foo() throws InterruptedException {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                while (counter.get() % 2 != 0) {
                    condition.await();
                    System.out.println("foo await");
                }

//                System.out.print("foo");
                counter.incrementAndGet();
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void bar() throws InterruptedException {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                while (counter.get() % 2 != 1) {
                    // 不满足当前线程条件，等待
                    condition.await();
                    System.out.println("bar await");
                }

//                System.out.println("bar");
                counter.incrementAndGet();
                // 唤醒其他线程
                condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FooBar foorBar = new FooBar();

        final CountDownLatch latch = new CountDownLatch(2);

        Runnable fooRun = new Runnable() {
            public void run() {
                try {
                    foorBar.foo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        };

        Runnable barRun = () -> {
            try {
                foorBar.bar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        };
        new Thread(fooRun).start();
        new Thread(barRun).start();
//        latch.await();
    }
}