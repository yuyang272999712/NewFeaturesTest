package com.example;

import com.example.TreeTraverse.TreeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by yuyang on 2018/6/11.
 */

public class TreeTest {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(8);
        TreeNode node1 = new TreeNode(6);
        TreeNode node2 = new TreeNode(10);
        TreeNode node3 = new TreeNode(5);
        TreeNode node4 = new TreeNode(7);
        TreeNode node5 = new TreeNode(9);
        TreeNode node6 = new TreeNode(11);
        TreeNode node7 = new TreeNode(15);
        TreeNode node8 = new TreeNode(24);
        TreeNode node9 = new TreeNode(30);
        TreeNode node10 = new TreeNode(28);

        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        node3.left = node7;
        node5.left = node9;
        node6.right = node10;
        node7.right = node8;

        preOrderTraverse(root);
        System.out.println();
        inOrderTraverse(root);
        System.out.println();
        postOrderTraverse(root);
        System.out.println();
        breadthFirstTraverse(root);
    }

    //前序
    private static void preOrderTraverse2(TreeNode node){
        if (node != null){
            System.out.print(node.value + " ");
            preOrderTraverse2(node.left);
            preOrderTraverse2(node.right);
        }
    }

    private static void preOrderTraverse(TreeNode node){
        if (node == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()){
            while (node != null){
                System.out.print(node.value + " ");
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()){
                node = stack.pop().right;
            }
        }
    }

    //中序
    private static void inOrderTraverse2(TreeNode node){
        if (node != null){
            inOrderTraverse2(node.left);
            System.out.print(node.value + " ");
            inOrderTraverse2(node.right);
        }
    }

    private static void inOrderTraverse(TreeNode node){
        if (node == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (node != null || !stack.isEmpty()){
            while (node != null){
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()){
                node = stack.pop();
                System.out.print(node.value + " ");
                node = node.right;
            }
        }
    }

    //后序
    private static void postOrderTraverse2(TreeNode node){
        if (node != null){
            postOrderTraverse2(node.left);
            postOrderTraverse2(node.right);
            System.out.print(node.value + " ");
        }
    }

    private static void postOrderTraverse(TreeNode node){
        if (node == null){
            return;
        }
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while (node != null || !stack1.isEmpty()){
            while (node != null){
                stack1.push(node);
                stack2.push(0);
                node = node.left;
            }
            while (!stack1.isEmpty() && stack2.peek() == 1){
                TreeNode node1 = stack1.pop();
                System.out.print(node1.value + " ");
                stack2.pop();
            }
            if (!stack1.isEmpty()){
                stack2.pop();
                stack2.push(1);
                node = stack1.peek();
                node = node.right;
            }
        }
    }

    //广度
    private static void breadthFirstTraverse(TreeNode node){
        if (node == null){
            return;
        }
        TreeNode currentNode = null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()){
            currentNode = queue.poll();
            System.out.print(currentNode.value + " ");
            if (currentNode.left != null)
                queue.offer(currentNode.left);
            if (currentNode.right != null)
                queue.offer(currentNode.right);
        }
    }
}
