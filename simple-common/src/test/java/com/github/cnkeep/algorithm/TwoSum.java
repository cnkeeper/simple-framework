package com.github.cnkeep.algorithm;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/10
 * @version:
 **/
public class TwoSum {
    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode sum(ListNode n1, ListNode n2) {
        ListNode head = null;
        ListNode tail = null;
        int carry = 0;
        while (n1 != null || n2 != null) {
            int a = n1 == null ? 0 : n1.val;
            int b = n2 == null ? 0 : n2.val;
            int sum = a + b + carry;
            if (head == null) {
                tail = new ListNode(sum % 10, null);
                head = tail;
            } else {
                tail.next = new ListNode(sum % 10, null);
                tail = tail.next;
            }
            if (n1 != null) {
                n1 = n1.next;
            }
            if (n2 != null) {
                n2 = n2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(carry, null);
        }

        return head;
    }
}
