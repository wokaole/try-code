package com.cold.tutorial.algorithm;

import java.util.*;

/**
 * 二叉树遍历
 * Created by faker on 2017/2/25.
 */
public class TreeNode {

    private int val;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    /**
     * 前序遍历：先遍历根结点再遍历左结点最后遍历右结点
     * 递归前序遍历:
     */
    public static void preOrderRecursion(TreeNode rootNode) {
        if (rootNode == null) { //空节点
            return;
        }
        print(rootNode);
        preOrderRecursion(rootNode.left);
        preOrderRecursion(rootNode.right);
    }

    /**
     * 非递归前序遍历
     */
    public static List<Integer> preOrderTraversal(TreeNode rootNode) {
        List<Integer> resultList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(rootNode);

        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();

            if (treeNode != null) {
                resultList.add(treeNode.val);
                stack.push(treeNode.right);
                stack.push(treeNode.left);
            }
        }

        return resultList;
    }

    /**
     * 递归中序遍历：先访问左结点再访问根节点最后访问右结点
     * @param rootNode
     */
    public static void midOrderRecursion(TreeNode rootNode) {
        if (rootNode == null) {
            return;
        }
        midOrderRecursion(rootNode.left);
        print(rootNode);
        midOrderRecursion(rootNode.right);
    }

    /**
     * 非递归中序遍历
     * 栈的方式：先将根节点入栈，然后判断是否有左节点，有的话入栈，重复， 直到节点没有左，则访问这个结点，然后把右节点入栈，每个节点重复这个操作
     */
    public static List<Integer> midOrderTraversal(TreeNode rootNode) {
        List<Integer> resultList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        TreeNode cur = rootNode;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            TreeNode treeNode = stack.pop();
            resultList.add(treeNode.val);
            cur = treeNode.right;
        }
        return resultList;
    }

    /**
     * 递归后续遍历：左孩子-- 右孩子--根节点
     */
    public static void postOrderRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrderRecursion(root.left);
        postOrderRecursion(root.right);
        print(root);
    }

    /**
     * 非递归后续遍历: 先采用类似先序遍历，先遍历根结点再右孩子最后左孩子（先序是先根结点再左孩子最后右孩子），最后把遍历的序列逆转即得到了后序遍历
     */
    public static List<Integer> postOrderTraversal(TreeNode rootNode) {
        List<Integer> resultList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        stack.push(rootNode);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();
            if (treeNode != null) {
                resultList.add(treeNode.val);
                stack.push(treeNode.left);
                stack.push(treeNode.right);
            }
        }
        Collections.reverse(resultList);
        return resultList;
    }

    public static List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root == null) return ans;

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            ans.addFirst(cur.val);
            if (cur.left != null) {
                stack.push(cur.left);
            }
            if (cur.right != null) {
                stack.push(cur.right);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(6);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(8);
        TreeNode t4 = new TreeNode(0);
        TreeNode t5 = new TreeNode(4);
        TreeNode t6 = new TreeNode(9);
        TreeNode t7 = new TreeNode(1);
        TreeNode t8 = new TreeNode(5);
        t1.setLeft(t2);
        t1.setRight(t3);
        t2.setLeft(t4);
        t2.setRight(t5);
        t3.setRight(t6);
        t4.setRight(t7);
        t5.setRight(t8);

        preOrderRecursion(t1);
        System.out.println(preOrderTraversal(t1));
        midOrderRecursion(t1);
        System.out.println(midOrderTraversal(t1));
        postOrderRecursion(t1);
        System.out.println(postOrderTraversal(t1));
        System.out.println(postorderTraversal(t1));
    }

    public static void print(TreeNode treeNode) {
        System.out.print(treeNode.val + " ");
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }
}
