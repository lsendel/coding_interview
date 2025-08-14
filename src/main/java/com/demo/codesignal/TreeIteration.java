package com.demo.codesignal;

import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
    
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class TreeIteration {
    
    // Breadth-First Search (Level Order Traversal) - Iterative
    public static List<Integer> breadthFirstSearch(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);
            
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        
        return result;
    }
    
    // BFS - Level by Level (returns list of lists)
    public static List<List<Integer>> breadthFirstSearchByLevel(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
    
    // Depth-First Search - Preorder (Root -> Left -> Right) - Recursive
    public static List<Integer> depthFirstSearchPreorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfsPreorderHelper(root, result);
        return result;
    }
    
    private static void dfsPreorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        
        result.add(node.val);
        dfsPreorderHelper(node.left, result);
        dfsPreorderHelper(node.right, result);
    }
    
    // DFS Preorder - Iterative using Stack
    public static List<Integer> depthFirstSearchPreorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return result;
    }
    
    // DFS - Inorder (Left -> Root -> Right) - Recursive
    public static List<Integer> depthFirstSearchInorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfsInorderHelper(root, result);
        return result;
    }
    
    private static void dfsInorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        
        dfsInorderHelper(node.left, result);
        result.add(node.val);
        dfsInorderHelper(node.right, result);
    }
    
    // DFS Inorder - Iterative using Stack
    public static List<Integer> depthFirstSearchInorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }
        
        return result;
    }
    
    // DFS - Postorder (Left -> Right -> Root) - Recursive
    public static List<Integer> depthFirstSearchPostorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfsPostorderHelper(root, result);
        return result;
    }
    
    private static void dfsPostorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        
        dfsPostorderHelper(node.left, result);
        dfsPostorderHelper(node.right, result);
        result.add(node.val);
    }
    
    // DFS Postorder - Iterative using Stack
    public static List<Integer> depthFirstSearchPostorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        
        stack1.push(root);
        
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }
        
        return result;
    }
    
    // Zigzag Level Order Traversal (BFS variant)
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            LinkedList<Integer> currentLevel = new LinkedList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (leftToRight) {
                    currentLevel.addLast(node.val);
                } else {
                    currentLevel.addFirst(node.val);
                }
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            result.add(currentLevel);
            leftToRight = !leftToRight;
        }
        
        return result;
    }
    
    // Helper method to build a sample tree
    private static TreeNode buildSampleTree() {
        /*
                1
               / \
              2   3
             / \   \
            4   5   6
           /
          7
        */
        TreeNode node7 = new TreeNode(7);
        TreeNode node4 = new TreeNode(4, node7, null);
        TreeNode node5 = new TreeNode(5);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node3 = new TreeNode(3, null, node6);
        TreeNode root = new TreeNode(1, node2, node3);
        
        return root;
    }
    
    // Helper method to print results
    private static void printResult(String method, List<Integer> result) {
        System.out.println(method + ": " + result);
    }
    
    private static void printLevelResult(String method, List<List<Integer>> result) {
        System.out.println(method + ":");
        for (int i = 0; i < result.size(); i++) {
            System.out.println("  Level " + i + ": " + result.get(i));
        }
    }
    
    public static void main(String[] args) {
        TreeNode root = buildSampleTree();
        
        System.out.println("Tree Structure:");
        System.out.println("        1");
        System.out.println("       / \\");
        System.out.println("      2   3");
        System.out.println("     / \\   \\");
        System.out.println("    4   5   6");
        System.out.println("   /");
        System.out.println("  7");
        System.out.println();
        
        System.out.println("=== BREADTH-FIRST SEARCH (BFS) ===");
        printResult("BFS (Level Order)", breadthFirstSearch(root));
        printLevelResult("BFS by Level", breadthFirstSearchByLevel(root));
        printLevelResult("Zigzag Level Order", zigzagLevelOrder(root));
        
        System.out.println("\n=== DEPTH-FIRST SEARCH (DFS) ===");
        printResult("DFS Preorder (Recursive)", depthFirstSearchPreorder(root));
        printResult("DFS Preorder (Iterative)", depthFirstSearchPreorderIterative(root));
        printResult("DFS Inorder (Recursive)", depthFirstSearchInorder(root));
        printResult("DFS Inorder (Iterative)", depthFirstSearchInorderIterative(root));
        printResult("DFS Postorder (Recursive)", depthFirstSearchPostorder(root));
        printResult("DFS Postorder (Iterative)", depthFirstSearchPostorderIterative(root));
        
        System.out.println("\n=== EDGE CASES ===");
        System.out.println("Empty tree BFS: " + breadthFirstSearch(null));
        System.out.println("Empty tree DFS: " + depthFirstSearchPreorder(null));
        
        TreeNode singleNode = new TreeNode(42);
        System.out.println("Single node BFS: " + breadthFirstSearch(singleNode));
        System.out.println("Single node DFS: " + depthFirstSearchPreorder(singleNode));
    }
}