package com.github.cnkeep.dubbo.common.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 线程安全LRU Cache
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-21 16:50
 * @version: v0.0.1
 **/
public class LRUCache<K, V> implements Cache<K, V> {
    private Map<K, V> cache;

    public LRUCache(int initCapacity,int maxCapacity) {
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    maxCapacity);
        }

        this.cache = new CacheMap<K, V>(initCapacity,maxCapacity);
    }


    @Override
    public V put(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public String toString() {
        return "LRUCache{" +
                "cache=" + cache +
                '}';
    }

    private static class CacheMap<K, V> extends LinkedHashMap<K, V> {
        private int evictThreshold;
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private static final float DEFAULT_LOAD_FACTOR = 0.75f;
        private static final int DEFAULT_INIT_CAPACITY = 1024;

        public CacheMap(int evictThreshold) {
            this(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR, evictThreshold);
        }

        public CacheMap(int initialCapacity, int evictThreshold) {
            this(initialCapacity, DEFAULT_LOAD_FACTOR, evictThreshold);
        }

        public CacheMap(int initialCapacity, float loadFactor, int evictThreshold) {
            super(initialCapacity, loadFactor, true);
            this.evictThreshold = evictThreshold;
        }

        @Override
        public V get(Object key) {
            lock.readLock().lock();
            try {
                return super.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                super.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return super.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        @Override
        public V put(K key, V value) {
            lock.writeLock().lock();
            try {
                return super.put(key, value);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public V remove(Object key) {
            lock.writeLock().lock();
            try {
                return super.remove(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > evictThreshold;
        }
    }
}
