package com.example.TreeTraverse;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉树遍历
 *
 *                          8
 *                 6               10
 *             5      7         9      11
 *          15               30           28
 *              24
 */

public class TreeTraverse {
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

        preOrderTraverse2(root);
        System.out.println();
        inOrderTraverse2(root);
        System.out.println();
        postOrderTraverse2(root);
        System.out.println();
        breadthFirstTraverse(root);
    }

    /**
     * 前序遍历
     *  按照先根节点，再左子树，后右子树的方法遍历二叉树中的所有节点
     *  8，6，5，15，24，7，10，9，30，11，28。
     * @param root
     */
    private static void preOrderTraverse(TreeNode root) {
        if (root != null){
            System.out.print(root.value + "  ");
            preOrderTraverse(root.left);
            preOrderTraverse(root.right);
        }
    }

    private static void preOrderTraverse2(TreeNode root) {
        if (root != null){
            //创建一个栈用于保存遍历的结点
            Stack<TreeNode> nodes = new Stack<>();
            while (root != null || !nodes.isEmpty()){
                //遍历左子树
                while (root != null){
                    System.out.print(root.value + "  ");
                    nodes.push(root);
                    root = root.left;
                }
                //遍历右子树
                if (!nodes.isEmpty()){
                    root = nodes.pop();
                    root = root.right;
                }
            }
        }
    }

    /**
     * 中序遍历
     *  左孩子，再访问当前节点，最后再访问其右孩子
     *  15，24，5，6，7，8，30，9，10，11，28
     * @param root
     */
    private static void inOrderTraverse(TreeNode root){
        if (root != null){
            inOrderTraverse(root.left);
            System.out.print(root.value + "  ");
            inOrderTraverse(root.right);
        }
    }

    private static void inOrderTraverse2(TreeNode root) {
        if (root != null){
            //创建一个栈用于保存遍历的结点
            Stack<TreeNode> nodes = new Stack<>();
            while (root != null || !nodes.isEmpty()){
                //遍历左子树
                while (root != null){
                    nodes.push(root);
                    root = root.left;
                }
                //遍历右子树
                if (!nodes.isEmpty()){
                    root = nodes.pop();
                    System.out.print(root.value + "  ");
                    root = root.right;
                }
            }
        }
    }

    /**
     * 后序遍历
     *  先访问节点的左右孩子，最后访问节点本身
     *  24，15，5，7，6，30，9，28，11，10，8
     * @param root
     */
    private static void postOrderTraverse(TreeNode root){
        if (root != null){
            postOrderTraverse(root.left);
            postOrderTraverse(root.right);
            System.out.print(root.value + "  ");
        }
    }

    private static void postOrderTraverse2(TreeNode root){
        if (root != null) {
            Stack<TreeNode> nodes1 = new Stack<>();
            //创建栈nodes2的目的在于记住每个访问的节点
            Stack<Integer> nodes2 = new Stack<>();
            //如果栈s2的栈顶是1，标识当前访问的节点
            Integer i = new Integer(1);
            while (root != null || !nodes1.isEmpty()){
                while (root != null){
                    nodes1.push(root);
                    nodes2.push(0);
                    root = root.left;
                }
                //这个循坏的目的是对栈s2栈顶为1时对应的栈s1的栈顶元素进行访问
                while (!nodes1.isEmpty() && nodes2.peek().equals(i)){
                    nodes2.pop();
                    System.out.print(nodes1.pop().value + "  ");
                }
                //访问左子树到头后，就可以访问其右孩子了
                if (!nodes1.isEmpty()){
                    nodes2.pop();
                    nodes2.push(i);
                    root = nodes1.peek();
                    root = root.right;
                }
            }
        }
    }

    /**
     * 广度优先遍历二叉树，又称层次遍历二叉树
     */
    private static void breadthFirstTraverse(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        TreeNode currentNode = null;
        queue.offer(root);
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            System.out.print(currentNode.value + "  ");
            if (currentNode.left != null)
                queue.offer(currentNode.left);
            if (currentNode.right != null)
                queue.offer(currentNode.right);
        }
    }
}
