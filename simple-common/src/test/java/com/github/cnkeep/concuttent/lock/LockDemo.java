package com.github.cnkeep.concuttent.lock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
 
 
public class LockDemo {
    private static int count = 0;
    //定义全局锁
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();
    private static Condition condition3 = lock.newCondition();
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Thread1());
        Thread thread2 = new Thread(new Thread2());
        Thread thread3 = new Thread(new Thread3());
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.start();
        thread2.start();
        thread3.start();
    }
 
 
    static class Thread1 implements Runnable{
        @Override
        public void run() {
            for (int i = 0 ; i < 10 ; i ++){
                try {
                    lock.lock();
                    if (count % 3 != 0)
                        condition1.await();
                    System.out.println(i+"***A");
                    count++;
                    condition2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
 
    static class Thread2 implements Runnable{
        @Override
        public void run() {
            for (int i = 0 ; i < 10 ; i ++){
                try {
                    lock.lock();
                    if (count % 3 != 1)
                        condition2.await();
                    System.out.println(i+"***B");
                    count++;
                    condition3.signal();// 唤醒条件3
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
 
    static class Thread3 implements Runnable{
        @Override
        public void run() {
            for (int i = 0 ; i < 10 ; i ++){
                try {
                    lock.lock();
                    if (count % 3 != 2)
                        condition3.await();
                    System.out.println(i+"***C");
                    count++;
                    condition1.signal();// 唤醒条件1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
 
}