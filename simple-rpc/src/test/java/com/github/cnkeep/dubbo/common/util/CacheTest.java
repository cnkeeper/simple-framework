package com.github.cnkeep.dubbo.common.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-8 21:49
 * @version:
 **/
public class CacheTest {
    public static void main(String[] args) {
        LoadingCache<Long, Long> cache = CacheBuilder.newBuilder()
                .recordStats()
                .maximumSize(60)
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(new CacheLoader<Long, Long>() {
                    @Override
                    public Long load(Long key) throws Exception {
                        return key;
                    }
                });

        int count = 10_0000;
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < count; i++) {
            cache.getUnchecked(current.nextLong(1,100));
        }

        System.out.println(cache.stats());
    }
}
