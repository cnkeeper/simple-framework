package com.github.cnkeep.dubbo.common.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 线程安全LFU Cache
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-21 17:10
 * @version: v0.0.1
 **/
public class LFUCache<K, V> implements Cache<K, V> {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Map<K, CacheNode<K, V>> cache;
    private int evictThreshold;
    /**
     * 每个槽表示一次访问，每访问一次，移动到下一个槽，最后一个槽next指向自己。
     * 极端情况，所有的node都移动到最后一个槽，将退化为LRF
     */
    private CacheNodeDeque<K, V>[] freqTable;

    public LFUCache(int initCapacity, int maxCapacity) {
        if (initCapacity < 1) {
            throw new IllegalArgumentException("initCapacity<1");
        }
        this.cache = new HashMap<>(initCapacity);
        this.evictThreshold = maxCapacity;
        initFreqTable();
    }


    private void initFreqTable() {
        freqTable = new CacheNodeDeque[this.evictThreshold + 1];
        for (int i = 0; i <= evictThreshold; i++) {
            freqTable[i] = new CacheNodeDeque<>();
        }

        for (int i = 0; i < evictThreshold; i++) {
            freqTable[i].nextDeque = freqTable[i + 1];
        }

        freqTable[evictThreshold].nextDeque = freqTable[evictThreshold];
    }

    @Override
    public V put(K key, V value) {
        V oldV = null;
        lock.writeLock().lock();
        try {
            if (cache.containsKey(key)) {
                CacheNode<K, V> node = cache.get(key);
                node.leaveDeque();
                oldV = node.value;
                node.value = value;
                freqTable[0].addLast(node);
                cache.put(key, node);
            } else {
                CacheNode<K, V> node = freqTable[0].addLast(key, value);
                cache.put(key, node);
            }

            if (cache.size() > evictThreshold) {
                evictProcess();
            }
            return oldV;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void evictProcess() {
        for (int i = 0; i < evictThreshold; i++) {
            while (!freqTable[i].isEmpty()) {
                System.out.println(cache.size()+":"+evictThreshold);
                if (cache.size() <= evictThreshold) {
                    break;
                }
                CacheNode<K, V> node = freqTable[i].poll();
                cache.remove(node.key);
            }
        }
    }

    @Override
    public V remove(K key) {
        lock.writeLock().lock();
        try {
            CacheNode<K, V> node = cache.remove(key);
            if (null == node) {
                return null;
            }
            node.leaveDeque();
            return node.value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public V get(K key) {
        lock.readLock().lock();
        try {
            CacheNode<K, V> node = cache.get(key);
            if (null == node) {
                return null;
            }
            node.leaveDeque();
            node.own.nextDeque.addLast(node);
            return node.value;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int size() {
        lock.writeLock().lock();
        try {
            return cache.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            cache.clear();
            initFreqTable();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public String toString() {
        return "LFUCache{" +
                "cache=" + cache +
                ", evictThreshold=" + evictThreshold +
                '}';
    }

    private static class CacheNode<K, V> {
        CacheNode<K, V> pre;
        CacheNode<K, V> next;
        K key;
        V value;
        CacheNodeDeque<K, V> own;

        CacheNode() {
        }

        CacheNode(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        public void leaveDeque() {
            boolean isTail = (own.tail == this);
            pre.next = next;
            next.pre = pre;
            if(isTail) {
                own.tail = pre;
            }
        }

        @Override
        public String toString() {
            return "CacheNode{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private static class CacheNodeDeque<K, V> {
        CacheNode<K, V> head;
        CacheNode<K, V> tail;
        CacheNodeDeque<K, V> nextDeque;

        public CacheNodeDeque() {
            head = new CacheNode<>();
            tail = head;
            head.next = tail;
            tail.pre = head;
        }

        public CacheNode<K, V> addLast(K key, V value) {
            CacheNode<K, V> node = new CacheNode<>(key, value);
            node.pre = tail;
            node.next = tail.next;
            tail.next.pre = node;
            tail.next = node;
            tail = node;
            node.own = this;
            return node;
        }

        public void addLast(CacheNode<K, V> node) {
            node.pre = tail;
            node.next = tail.next;
            tail.next.pre = node;
            tail.next = node;
            tail = node;
            node.own = this;
        }

        public CacheNode<K, V> poll() {
            CacheNode<K, V> node = null;
            if (head.pre != head) {
                node = head.next;
                node.next.pre = head;
                head.next = node.next;

                if(tail == node){
                    // 删除的是尾节点
                    tail = head;
                }
            }
            return node;
        }

        public boolean isEmpty() {
            return head.next == head;
        }
    }
}
