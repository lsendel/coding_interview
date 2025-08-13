package com.demo.tree;

import java.util.*;

/**
 * Problem: Lowest Common Ancestor of a Binary Tree
 * 
 * MNEMONIC: "FAMILY REUNION - Find where relatives meet!"
 * 👨‍👩‍👧‍👦 Like finding the most recent common ancestor in a family tree
 * 
 * PROBLEM: Find the LCA of two nodes in a binary tree.
 * The LCA is the lowest node that has both p and q as descendants.
 * 
 * Example:     3
 *            /   \
 *           5     1
 *          / \   / \
 *         6   2 0   8
 *            / \
 *           7   4
 * LCA(5, 1) = 3
 * LCA(5, 4) = 5 (a node can be its own ancestor)
 * 
 * TECHNIQUE: Recursive search
 * - If current node is p or q, it could be LCA
 * - Search left and right subtrees
 * - If both subtrees contain targets, current is LCA
 * - Otherwise, return whichever side found something
 * 
 * VISUALIZATION: Finding LCA(5, 4)
 * At node 3: Left finds 5, right finds null → return 5's subtree result
 * At node 5: It's target! Left finds null, right finds 4 → 5 is LCA
 * 
 * Time Complexity: O(n) - might visit all nodes
 * Space Complexity: O(h) - recursion depth
 */
public class LowestCommonAncestor {
    
    /**
     * Find lowest common ancestor
     * 
     * REMEMBER: "FAMILY TREE SEARCH"
     * Search for both relatives, where they meet is the ancestor
     * 
     * @param familyTreeRoot tree root
     * @param relative1 first node
     * @param relative2 second node
     * @return lowest common ancestor
     */
    public static TreeNode findFamilyReunionPoint(TreeNode familyTreeRoot, 
                                                  TreeNode relative1, 
                                                  TreeNode relative2) {
        // Base cases: no family or found a relative
        if (familyTreeRoot == null || familyTreeRoot == relative1 || familyTreeRoot == relative2) {
            return familyTreeRoot;
        }
        
        // Search for relatives in left family branch
        TreeNode leftFamilySearch = findFamilyReunionPoint(familyTreeRoot.left, relative1, relative2);
        
        // Search for relatives in right family branch
        TreeNode rightFamilySearch = findFamilyReunionPoint(familyTreeRoot.right, relative1, relative2);
        
        // If both branches found relatives, current person is the common ancestor!
        if (leftFamilySearch != null && rightFamilySearch != null) {
            return familyTreeRoot;
        }
        
        // Otherwise, return whichever branch found a relative
        return leftFamilySearch != null ? leftFamilySearch : rightFamilySearch;
    }
    
    /**
     * Visualize the LCA finding process
     */
    private static void visualizeLCAFinding(TreeNode root, int p, int q) {
        System.out.println("=== 👨‍👩‍👧‍👦 FAMILY REUNION VISUALIZATION ===");
        
        System.out.println("\nFamily tree structure:");
        TreeNode.visualizeTree(root);
        
        // Find the actual nodes
        TreeNode nodeP = findNode(root, p);
        TreeNode nodeQ = findNode(root, q);
        
        if (nodeP == null || nodeQ == null) {
            System.out.println("\nOne or both relatives not found in the family tree!");
            return;
        }
        
        System.out.printf("\nSearching for common ancestor of %d and %d:\n", p, q);
        
        Map<TreeNode, String> searchPath = new HashMap<>();
        TreeNode lca = visualizeSearch(root, nodeP, nodeQ, searchPath, "");
        
        System.out.printf("\n🎆 Family reunion point: %s\n", 
                         lca != null ? String.valueOf(lca.val) : "Not found");
    }
    
    private static TreeNode visualizeSearch(TreeNode current, TreeNode p, TreeNode q, 
                                          Map<TreeNode, String> path, String indent) {
        if (current == null) {
            System.out.println(indent + "Reached end of branch (null)");
            return null;
        }
        
        System.out.printf("%sVisiting family member %d\n", indent, current.val);
        
        // Check if current is one of our relatives
        if (current == p || current == q) {
            System.out.printf("%s  🎉 Found relative %d!\n", indent, current.val);
            return current;
        }
        
        // Search left family branch
        System.out.printf("%s  Searching left branch...\n", indent);
        TreeNode leftResult = visualizeSearch(current.left, p, q, path, indent + "    ");
        
        // Search right family branch
        System.out.printf("%s  Searching right branch...\n", indent);
        TreeNode rightResult = visualizeSearch(current.right, p, q, path, indent + "    ");
        
        // Analyze results
        if (leftResult != null && rightResult != null) {
            System.out.printf("%s  🎆 Both branches have relatives! %d is the common ancestor!\n", 
                            indent, current.val);
            return current;
        } else if (leftResult != null) {
            System.out.printf("%s  Left branch has relative(s)\n", indent);
            return leftResult;
        } else if (rightResult != null) {
            System.out.printf("%s  Right branch has relative(s)\n", indent);
            return rightResult;
        } else {
            System.out.printf("%s  No relatives found in this branch\n", indent);
            return null;
        }
    }
    
    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        
        TreeNode left = findNode(root.left, val);
        if (left != null) return left;
        
        return findNode(root.right, val);
    }
    
    /**
     * Show different LCA scenarios
     */
    private static void demonstrateLCAScenarios() {
        System.out.println("\n=== 🎭 LCA SCENARIOS ===");
        
        // Scenario 1: Nodes at different levels
        System.out.println("\n1. Different levels (uncle & nephew):");
        System.out.println("       1");
        System.out.println("      / \\");
        System.out.println("     2   3");
        System.out.println("    / \\");
        System.out.println("   4   5");
        System.out.println("LCA(4, 3) = 1 (grandparent connects uncle and nephew)");
        
        // Scenario 2: One is ancestor of other
        System.out.println("\n2. Direct ancestry (parent & child):");
        System.out.println("       1");
        System.out.println("      / \\");
        System.out.println("     2   3");
        System.out.println("    / \\");
        System.out.println("   4   5");
        System.out.println("LCA(2, 4) = 2 (parent is its own ancestor)");
        
        // Scenario 3: Siblings
        System.out.println("\n3. Siblings:");
        System.out.println("       1");
        System.out.println("      / \\");
        System.out.println("     2   3");
        System.out.println("LCA(2, 3) = 1 (parent connects siblings)");
        
        // Scenario 4: Deep in tree
        System.out.println("\n4. Deep relatives:");
        System.out.println("         1");
        System.out.println("        / \\");
        System.out.println("       2   3");
        System.out.println("      /     \\");
        System.out.println("     4       5");
        System.out.println("    / \\     / \\\\");
        System.out.println("   6   7   8   9");
        System.out.println("LCA(6, 8) = 1 (great-grandparent connects cousins)");
    }
    
    /**
     * Explain the algorithm elegance
     */
    private static void explainAlgorithm() {
        System.out.println("\n=== 💡 ALGORITHM INSIGHTS ===");
        
        System.out.println("\nWhy this algorithm is elegant:");
        System.out.println("1. Handles all cases with simple logic");
        System.out.println("2. Node can be its own ancestor");
        System.out.println("3. Works by propagating information upward");
        
        System.out.println("\nHow it works:");
        System.out.println("- If we find p or q, return it");
        System.out.println("- If left subtree has one target and right has other, current is LCA");
        System.out.println("- If only one subtree has targets, they're both there");
        System.out.println("- Information bubbles up until we find the split point");
        
        System.out.println("\nKey insight:");
        System.out.println("The LCA is the node where paths to p and q diverge!");
    }
    
    /**
     * Test with various examples
     */
    private static void testVariousExamples() {
        System.out.println("\n=== 🌳 VARIOUS TREE TESTS ===");
        
        // Test 1: Standard example
        System.out.println("\nTest 1 - Standard family tree:");
        TreeNode tree1 = TreeNode.createTree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4);
        visualizeLCAFinding(tree1, 5, 1);
        
        // Test 2: One is ancestor
        System.out.println("\nTest 2 - Parent-child relationship:");
        visualizeLCAFinding(tree1, 5, 4);
        
        // Test 3: Leaf nodes
        System.out.println("\nTest 3 - Two leaf nodes:");
        visualizeLCAFinding(tree1, 7, 8);
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("👨‍👩‍👧‍👦 LOWEST COMMON ANCESTOR - Family Reunion!\n");
        
        // Test 1: Various examples
        testVariousExamples();
        
        // Test 2: LCA scenarios
        demonstrateLCAScenarios();
        
        // Test 3: Algorithm explanation
        explainAlgorithm();
        
        // Test 4: Edge cases
        System.out.println("\nTest 4 - Edge cases:");
        
        TreeNode single = new TreeNode(1);
        System.out.println("Single node tree, LCA(1,1): " + 
                         findFamilyReunionPoint(single, single, single).val);
        
        TreeNode twoNodes = new TreeNode(1);
        twoNodes.left = new TreeNode(2);
        System.out.println("Two nodes, LCA(1,2): " + 
                         findFamilyReunionPoint(twoNodes, twoNodes, twoNodes.left).val);
        
        // Test 5: Performance test
        System.out.println("\nTest 5 - Performance with large tree:");
        TreeNode largeTree = createLargeTree(10000);
        TreeNode node1 = findNode(largeTree, 2500);
        TreeNode node2 = findNode(largeTree, 7500);
        
        long start = System.nanoTime();
        TreeNode lca = findFamilyReunionPoint(largeTree, node1, node2);
        long time = System.nanoTime() - start;
        
        System.out.printf("LCA of nodes 2500 and 7500: %d (found in %.3f ms)\n", 
                         lca.val, time / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- FAMILY REUNION: Where relatives meet in the tree");
        System.out.println("- If both subtrees have targets, current is LCA");
        System.out.println("- A node can be its own ancestor");
        System.out.println("- Information bubbles up from leaves to root");
        System.out.println("- The split point is the reunion point!");
    }
    
    private static TreeNode createLargeTree(int size) {
        return createBalancedTree(1, size);
    }
    
    private static TreeNode createBalancedTree(int start, int end) {
        if (start > end) return null;
        
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(mid);
        
        root.left = createBalancedTree(start, mid - 1);
        root.right = createBalancedTree(mid + 1, end);
        
        return root;
    }
}