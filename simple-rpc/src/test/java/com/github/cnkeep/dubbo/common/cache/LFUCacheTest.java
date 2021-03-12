package com.github.cnkeep.dubbo.common.cache;

import org.junit.Test;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/1/21
 * @version:
 **/
public class LFUCacheTest {
    @Test
    public void test(){
        final Cache<String,Integer> cache = new LFUCache<>(8,2);
        int count = 5;

        for (int i = 0; i < count; i++) {
            cache.get("K1");
            cache.put("K"+i,i);
        }

        System.out.println(cache);
    }
}