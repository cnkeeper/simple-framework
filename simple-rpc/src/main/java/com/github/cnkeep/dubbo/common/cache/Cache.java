package com.github.cnkeep.dubbo.common.cache;

/**
 * @description: cache接口
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-21 16:56
 * @version: v0.0.1
 **/
public interface Cache<K,V> {
    /**
     * 新增缓存，如果已经存在则替换
     * @param key
     * @param value
     * @return 缓存已存在-oldValue; null-新增缓存
     */
    V put(K key,V value);

    /**
     * 移除缓存
     * @param key
     * @return 返回已存在的缓存
     */
    V remove(K key);

    /**
     * 获取缓存
     * @param key
     * @return
     */
    V get(K key);

    /**
     * size
     * @return
     */
    int size();

    /**
     * 清空cache
     */
    void clear();
}
