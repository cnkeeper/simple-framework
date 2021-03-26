package com.github.cnkeep.ratelimit;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/14
 * @version:
 **/
public class HashMapTest {

    public static class Node {
        int val;

        public Node(int val) {
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return val == node.val;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    @Test
    public void resizeTest() {
        Map<Node, Node> map = new HashMap<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            map.put(new Node(i), new Node(i));
        }
    }

    @Test
    public void test1() {
        int index = 0;
        int[] arr = {0, 1, 1, 2, 3, 3, 5};
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != arr[index]) {
                index++;
                arr[index] = arr[i];
            }
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(index);
    }

    public static void main(String[] args) {
        Object blocker = new Object();
        LockSupport.park(blocker);
        System.out.println(1);
        LockSupport.park(blocker);
        System.out.println(2);
    }
}
