package com.github.cnkeep.dubbo.common.cache;

import org.junit.Test;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-21 16:57
 * @version:
 **/
public class LRUCacheTest {

    @Test
    public void test(){
        final Cache<String,Integer> cache = new LRUCache<String, Integer>(64,100);
        int count = 1500;

        for (int i = 0; i < count; i++) {
            cache.get("K1");
            cache.put("K"+i,i);
        }

        System.out.println(cache);
    }
}