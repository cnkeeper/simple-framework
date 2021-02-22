package com.github.cnkeep.dubbo.leetcode;

import java.util.LinkedList;
import java.util.List;

public class Question_25 {

    /**
     * 每k个节点一组，反转
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;

        while (head != null) {
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if(end==null) break;

            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = next;

            pre = start;
            end = start;
        }
        return dummy.next;
    }

    public static ListNode reverse(ListNode start) {
        ListNode pre = null;
        ListNode cur = start;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    public static void travel(ListNode head) {
        List<Integer> values = new LinkedList<>();
        while (head != null) {
            values.add(head.val);
            head = head.next;
        }
        System.out.println(values);
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}