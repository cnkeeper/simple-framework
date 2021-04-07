package com.github.cnkeep.arthas;


import java.util.concurrent.CountDownLatch;

/**
 * @description: 模拟应用程序
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/5/12
 * @version: 1.0.0
 **/
public class ApplicationServer {

    public static void main(String[] args) throws InterruptedException {
        new CountDownLatch(1).await();
    }
}