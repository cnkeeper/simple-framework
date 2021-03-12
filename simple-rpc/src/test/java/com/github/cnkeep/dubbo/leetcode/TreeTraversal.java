package com.github.cnkeep.dubbo.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/3/10
 * @version:
 **/
public class TreeTraversal {

    public static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
    }

    public static List<TreeNode> preTraversal(TreeNode root) {
        List<TreeNode> result = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (null != root || !stack.isEmpty()) {
            while (root != null) {
                result.add(root);
                stack.push(root);
                root = root.left;
            }

            root = stack.pop().right;
        }
        return result;
    }


    public static List<TreeNode> inOrderTraversal(TreeNode root){
        List<TreeNode> result = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        while (null != root || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            result.add(root);
            root = root.right;
        }
        return result;
    }

    public static List<TreeNode> postTraversal(TreeNode root){
        List<TreeNode> result = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode mark = root;
        while (null != root || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            if(root.right == null || root.right == mark){
                result.add(root);
                mark = root;
            }else {
                stack.push(root);
                root = root.right;
            }
        }
        return result;
    }
}
