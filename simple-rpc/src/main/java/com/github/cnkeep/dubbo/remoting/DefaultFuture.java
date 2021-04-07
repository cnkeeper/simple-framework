package com.github.cnkeep.dubbo.remoting;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description: TODO Future集中处理
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/4/6
 * @version:
 **/
public class DefaultFuture extends CompletableFuture<Object> {
    private static ConcurrentMap<Long, CompletableFuture<Object>> futures = new ConcurrentHashMap<>(1024);


    public static DefaultFuture newFuture(Long key) {
        DefaultFuture future = new DefaultFuture();
        futures.put(key, future);
        return future;
    }
}
