package com.github.cnkeep.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @description: 二叉树遍历
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-27 9:57
 * @version:
 **/
public class TreeOrderTraversal {
    public static class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int val;
    }

    public List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                result.add(root.val);
                root = root.left;
            }
            root = stack.pop();
            root = root.right;
        }

        return result;
    }

    public List<Integer> inOrderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            result.add(root.val);
            root = root.right;
        }

        return result;
    }

    public List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode mark = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            // 叶子节点/节点已经访问过(上次出栈的元素是你的右节点), 则出栈
            if (root.right == null || root.right == mark) {
                result.add(root.val);
                mark = root;
            } else {
                stack.push(root);
                root = root.right;
            }
        }

        return result;
    }
}
