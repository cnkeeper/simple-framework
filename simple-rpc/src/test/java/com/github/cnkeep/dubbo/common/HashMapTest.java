package com.github.cnkeep.dubbo.common;

import org.junit.Test;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * jdk7扩容时都可能导致死锁
 * jdk8在PutTreeValue时可能死循环   死循环在hashMap的1816行或2229行， java version "1.8.0_111"
 * jstack发现可能卡在 at java.util.HashMap$TreeNode.balanceInsertion(HashMap.java:2229)
 * 也有可能卡在  at java.util.HashMap$TreeNode.root(HashMap.java:1816)
 *
 * @author gaosong
 * @since 2019-02-23
 */
public class HashMapTest {
    public static void main(String[] args) {
//        int count = 14;
//        for (int i = 0; i < count; i++) {
//            new HashMapThread().start();
//        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        map.put("1", 1);
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            System.out.println(entry);
        }
    }

    @Test
    public void circleTest() {
        int count = 13;
        for (int i = 0; i < count; i++) {
            new HashMapThread().start();
        }
    }

    @Test
    public void transferTreeNodeThresholdTest() {
        HashMap<HashCodeKey, Integer> map = new HashMap<>(8);
        int count = 10;
        for (int i = 0; i < count; i++) {
            map.put(new HashCodeKey("K" + i), i);
        }

        for (Map.Entry<HashCodeKey, Integer> entry : map.entrySet()) {
            System.out.println(entry);
        }
        for (int i = 0; i < count; ++i) {
            System.out.println(i);
        }
    }

    private static class HashCodeKey {

        public String key;

        public HashCodeKey(String key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashCodeKey that = (HashCodeKey) o;
            return Objects.equals(key, that.key);
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }


    /**
     * Case 1:
     * "Thread-0" #10 prio=5 os_prio=31 tid=0x00007fa22f086800 nid=0x5503 runnable [0x0000700006760000]
     * java.lang.Thread.State: RUNNABLE
     * at java.util.HashMap$TreeNode.balanceInsertion(HashMap.java:2239)
     * at java.util.HashMap$TreeNode.treeify(HashMap.java:1945)
     * at java.util.HashMap$TreeNode.split(HashMap.java:2171)
     * at java.util.HashMap.resize(HashMap.java:714)
     * at java.util.HashMap.putVal(HashMap.java:663)
     * at java.util.HashMap.put(HashMap.java:612)
     * at com.github.cnkeep.dubbo.common.HashMapThread.run(HashMap1.java:34)
     * -----------------------------------------------------------------------------------------------------------
     * Case 2:
     * "Thread-0" #10 prio=5 os_prio=31 tid=0x00007f97f284b800 nid=0x5503 runnable [0x000070000911f000]
     * java.lang.Thread.State: RUNNABLE
     * at java.util.HashMap$TreeNode.root(HashMap.java:1824)
     * at java.util.HashMap$TreeNode.putTreeVal(HashMap.java:1978)
     * at java.util.HashMap.putVal(HashMap.java:638)
     * at java.util.HashMap.put(HashMap.java:612)
     * at com.github.cnkeep.dubbo.common.HashMapThread.run(HashMap1.java:46)
     */
    private class HashMapThread extends Thread {
        AtomicInteger ai = new AtomicInteger(0);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(1);

        @Override
        public void run() {
            while (ai.get() < 100000) {
                map.put(ai.get(), ai.get());
                ai.incrementAndGet();
            }
            System.out.println(Thread.currentThread().getName() + "执行结束完");
        }
    }
}


