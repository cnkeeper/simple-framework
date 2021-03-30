package com.github.cnkeep.algorithm.linkedlist;

/**
 * @description: 链表相关
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/30
 * @version:
 **/
public class LinkedListSolution {
    public static class Node {
        Node next;
        int value;

        public Node() {
        }

        public Node(Node next, int value) {
            this.next = next;
            this.value = value;
        }
    }

    /**
     * 有序重复链表去重
     * Input>  1->1->2->2->3
     * Output> 1->2->3
     *
     * @param head
     */
    public static Node distinctDuplicateNode(Node head) {
        if (null == head) {
            return null;
        }
        Node cur = head;
        while (null != cur.next) {
            if (cur.value == cur.next.value) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return head;
    }

    /**
     * 删除有序链表中重复的项
     * Input>  1->1->2->2->3
     * Output> 3
     *
     * @param head
     * @return
     */
    public static Node removeDuplicateNode(Node head) {
        if (null == head) {
            return null;
        }

        Node dummy = new Node(head, -1);
        Node cur = dummy;
        while (cur.next != null && cur.next.next != null) {
            if (cur.next.value == cur.next.next.value) {
                int x = cur.next.value;
                while (cur.next != null && cur.next.value == x) {
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }

        return dummy.next;
    }

    /**
     * 查找2个链表的相交点
     * 我走过你走的路，我们终究会相遇
     * A
     * \
     * D-C
     * /
     * B
     * 等式：AD+DC+BD = BD+DC+AD
     * 也可以使用map, 先遍历存储第一个链表，在遍历第二个链表判断是否存在与第一个链表中
     * @return
     */
    public static Node findIntersectNode(Node head1, Node head2) {
        Node cur1 = head1;
        Node cur2 = head2;
        while (cur1 != cur2) {
            cur1 = cur1==null ? head2 : cur1.next;
            cur2 = cur2==null ? head1 : cur2.next;
        }
        return cur1;
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public static Node reverseNode(Node head){
        Node pre = null;
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
             cur.next = pre;
             pre = cur;
             cur = next;
        }
        return pre;
    }
}
