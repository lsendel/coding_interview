package com.demo.treetraversal;

/**
 * Tree Node Helper Class
 * 
 * MNEMONIC: "TREE NODE - TRUNK and BRANCHES"
 * 🌳 A tree has a trunk (value) and branches (left/right children)
 * 
 * This is the fundamental building block for all tree problems.
 * Each node contains:
 * - Value (the data stored in the node)
 * - Left child reference (left branch)
 * - Right child reference (right branch)
 */
public class TreeNode {
    public int nodeValue;      // The trunk's value
    public TreeNode leftBranch;   // Left branch (child)
    public TreeNode rightBranch;  // Right branch (child)
    
    /**
     * Create a tree node with just a value (no children)
     * Like planting a seed that will grow branches later
     */
    public TreeNode(int value) {
        this.nodeValue = value;
        this.leftBranch = null;
        this.rightBranch = null;
    }
    
    /**
     * Create a tree node with value and children
     * Like creating a small tree with branches already attached
     */
    public TreeNode(int value, TreeNode left, TreeNode right) {
        this.nodeValue = value;
        this.leftBranch = left;
        this.rightBranch = right;
    }
    
    /**
     * String representation for easy debugging
     */
    @Override
    public String toString() {
        return "Node(" + nodeValue + ")";
    }
    
    /**
     * Helper method to build a simple test tree
     * 
     * Creates this tree:
     *         4
     *        / \
     *       2   6
     *      / \ / \
     *     1  3 5  7
     * 
     * MNEMONIC: "4 at the top, 2-6 in middle, 1-3-5-7 at bottom"
     * Perfect BST for testing traversals!
     */
    public static TreeNode createSampleBST() {
        TreeNode root = new TreeNode(4);
        root.leftBranch = new TreeNode(2);
        root.rightBranch = new TreeNode(6);
        root.leftBranch.leftBranch = new TreeNode(1);
        root.leftBranch.rightBranch = new TreeNode(3);
        root.rightBranch.leftBranch = new TreeNode(5);
        root.rightBranch.rightBranch = new TreeNode(7);
        return root;
    }
    
    /**
     * Helper to create an unbalanced tree for testing
     * 
     * Creates this tree:
     *     1
     *      \
     *       2
     *        \
     *         3
     *          \
     *           4
     * 
     * MNEMONIC: "Ladder tree - only right steps"
     */
    public static TreeNode createRightSkewedTree() {
        TreeNode root = new TreeNode(1);
        root.rightBranch = new TreeNode(2);
        root.rightBranch.rightBranch = new TreeNode(3);
        root.rightBranch.rightBranch.rightBranch = new TreeNode(4);
        return root;
    }
    
    /**
     * Helper to create a more complex tree for vertical order testing
     * 
     * Creates this tree:
     *         3
     *       /   \
     *      9     20
     *           /  \
     *          15   7
     * 
     * MNEMONIC: "3 splits to 9 and 20, 20 splits to 15 and 7"
     */
    public static TreeNode createVerticalTestTree() {
        TreeNode root = new TreeNode(3);
        root.leftBranch = new TreeNode(9);
        root.rightBranch = new TreeNode(20);
        root.rightBranch.leftBranch = new TreeNode(15);
        root.rightBranch.rightBranch = new TreeNode(7);
        return root;
    }
}