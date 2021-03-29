package com.github.cnkeep.algorithm;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadTest {
    private ReentrantLock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private volatile boolean muA = true;

    public void printA() {
        lock.lock();
        try {
            while (!muA) {
                conditionA.await();
            }
            System.out.println("A");
            muA = false;
            conditionA.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (muA) {
                conditionA.await();
            }
            System.out.println("B");
            muA = true;
            conditionA.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MultiThreadTest test = new MultiThreadTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    test.printA();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    test.printB();
                }
            }
        }).start();
    }
}
