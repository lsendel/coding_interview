package com.demo.tree;

import java.util.*;

/**
 * Problem: Validate Binary Search Tree
 * 
 * MNEMONIC: "BOUNDARY KEEPER - Each node must stay within bounds!"
 * 🛡️ Like a security guard checking if everyone is in the right zone
 * 
 * PROBLEM: Determine if a binary tree is a valid BST.
 * A valid BST has: left subtree values < node < right subtree values
 * 
 * TECHNIQUE: Recursive validation with bounds
 * - Pass min/max bounds down the tree
 * - Each node must be within its bounds
 * - Update bounds for children
 * 
 * COMMON MISTAKE: Only comparing with immediate children
 * Wrong:     10           This looks valid if only
 *           /  \          checking immediate children
 *          5    15        but 20 > 10 violates BST
 *         / \   /
 *        3   7 20
 * 
 * VISUALIZATION of bounds:
 * Root 10: (-∞, +∞)
 * Left 5: (-∞, 10)
 * Right 15: (10, +∞)
 * Node 20: (10, 15) - INVALID! 20 > 15
 * 
 * Time Complexity: O(n) - visit each node once
 * Space Complexity: O(h) - recursion stack depth
 */
public class ValidateBST {
    
    /**
     * Validate BST using bounds
     * 
     * REMEMBER: "SECURITY ZONES"
     * Each node must stay within its allowed zone
     * 
     * @param securityCheckpoint tree root
     * @return true if valid BST
     */
    public static boolean isValidBST_BoundaryMethod(TreeNode securityCheckpoint) {
        // Check with infinite bounds initially
        return checkSecurityZone(securityCheckpoint, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    /**
     * Recursively check if node is within bounds
     * 
     * VISUALIZATION: Like checking ID at different zones
     * Zone A: IDs 1-50 allowed
     * Zone B: IDs 51-100 allowed
     * Anyone outside their zone is invalid!
     */
    private static boolean checkSecurityZone(TreeNode guard, long minAllowed, long maxAllowed) {
        if (guard == null) return true; // No guard, no problem
        
        // Check if current guard is in valid zone
        if (guard.val <= minAllowed || guard.val >= maxAllowed) {
            return false; // Security breach!
        }
        
        // Check left zone (must be less than current guard)
        boolean leftZoneSecure = checkSecurityZone(guard.left, minAllowed, guard.val);
        
        // Check right zone (must be greater than current guard)
        boolean rightZoneSecure = checkSecurityZone(guard.right, guard.val, maxAllowed);
        
        return leftZoneSecure && rightZoneSecure;
    }
    
    /**
     * Alternative: Inorder Traversal Approach
     * 
     * REMEMBER: "ASCENDING PARADE"
     * BST inorder traversal must be in ascending order
     */
    static class InorderValidator {
        private Integer previousMarcher = null;
        
        public boolean isValidBST_ParadeMethod(TreeNode paradeStart) {
            return checkAscendingOrder(paradeStart);
        }
        
        /**
         * Check if parade is in ascending order
         * 
         * VISUALIZATION: Like a parade where each person
         * must have a higher number than the previous
         */
        private boolean checkAscendingOrder(TreeNode currentMarcher) {
            if (currentMarcher == null) return true;
            
            // Check left side of parade first
            if (!checkAscendingOrder(currentMarcher.left)) return false;
            
            // Check current marcher's number
            if (previousMarcher != null && currentMarcher.val <= previousMarcher) {
                return false; // Out of order!
            }
            previousMarcher = currentMarcher.val;
            
            // Check right side of parade
            return checkAscendingOrder(currentMarcher.right);
        }
    }
    
    /**
     * Visualize the validation process
     */
    private static void visualizeValidation(TreeNode root) {
        System.out.println("=== 🛡️ BOUNDARY KEEPER VISUALIZATION ===");
        
        System.out.println("\nTree structure:");
        TreeNode.visualizeTree(root);
        
        System.out.println("\nBoundary checking process:");
        boolean isValid = visualizeBounds(root, Long.MIN_VALUE, Long.MAX_VALUE, "");
        
        System.out.println("\nResult: " + (isValid ? "✅ Valid BST" : "❌ Invalid BST"));
        
        // Also show inorder traversal
        System.out.println("\nInorder traversal (should be ascending for valid BST):");
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        System.out.print("Sequence: ");
        for (int i = 0; i < inorder.size(); i++) {
            System.out.print(inorder.get(i));
            if (i < inorder.size() - 1) {
                if (inorder.get(i) >= inorder.get(i + 1)) {
                    System.out.print(" ❌ "); // Mark where order breaks
                } else {
                    System.out.print(" < ");
                }
            }
        }
        System.out.println();
    }
    
    private static boolean visualizeBounds(TreeNode node, long min, long max, String indent) {
        if (node == null) return true;
        
        System.out.printf("%sNode %d: bounds (%s, %s)", 
                         indent, node.val, 
                         min == Long.MIN_VALUE ? "-∞" : String.valueOf(min),
                         max == Long.MAX_VALUE ? "+∞" : String.valueOf(max));
        
        boolean valid = node.val > min && node.val < max;
        System.out.println(valid ? " ✓" : " ✗ INVALID!");
        
        if (!valid) return false;
        
        boolean leftValid = visualizeBounds(node.left, min, node.val, indent + "  ");
        boolean rightValid = visualizeBounds(node.right, node.val, max, indent + "  ");
        
        return leftValid && rightValid;
    }
    
    private static void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }
    
    /**
     * Show common BST mistakes
     */
    private static void demonstrateCommonMistakes() {
        System.out.println("\n=== ⚠️ COMMON BST MISTAKES ===");
        
        // Mistake 1: Only checking immediate children
        System.out.println("\n1. Only checking immediate children:");
        TreeNode mistake1 = new TreeNode(10);
        mistake1.left = new TreeNode(5);
        mistake1.right = new TreeNode(15);
        mistake1.right.left = new TreeNode(20); // 20 > 15 but also > 10!
        
        System.out.println("   10");
        System.out.println("  /  \\");
        System.out.println(" 5    15");
        System.out.println("      /");
        System.out.println("     20  ← Invalid! 20 > 10");
        
        // Mistake 2: Duplicate values
        System.out.println("\n2. Duplicate values:");
        System.out.println("   5");
        System.out.println("  / \\");
        System.out.println(" 5   5  ← Invalid! No duplicates in standard BST");
        
        // Mistake 3: Equal values on boundaries
        System.out.println("\n3. Values equal to parent:");
        System.out.println("   10");
        System.out.println("  /  \\");
        System.out.println(" 5    10  ← Invalid! Right must be > parent");
    }
    
    /**
     * Test various tree configurations
     */
    private static void testVariousTrees() {
        System.out.println("\n=== 🌳 VARIOUS TREE TESTS ===");
        
        // Test 1: Valid BST
        System.out.println("\nTest 1 - Valid BST:");
        TreeNode valid = TreeNode.createTree(10, 5, 15, 3, 7, null, 20);
        visualizeValidation(valid);
        
        // Test 2: Invalid BST
        System.out.println("\nTest 2 - Invalid BST (subtree violation):");
        TreeNode invalid = TreeNode.createTree(10, 5, 15, 3, 7, 12, 20);
        invalid.left.right.right = new TreeNode(11); // 11 > 10!
        visualizeValidation(invalid);
        
        // Test 3: Single node
        System.out.println("\nTest 3 - Single node:");
        TreeNode single = new TreeNode(42);
        System.out.println("Valid BST: " + isValidBST_BoundaryMethod(single));
        
        // Test 4: Edge case with min/max values
        System.out.println("\nTest 4 - Edge values:");
        TreeNode edge = new TreeNode(Integer.MAX_VALUE);
        System.out.println("Max value as root: " + isValidBST_BoundaryMethod(edge));
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("🛡️ VALIDATE BST - Boundary Keeper!\n");
        
        // Test 1: Basic validation
        testVariousTrees();
        
        // Test 2: Common mistakes
        demonstrateCommonMistakes();
        
        // Test 3: Compare approaches
        System.out.println("\nTest 3 - Comparing approaches:");
        TreeNode test = TreeNode.createTree(5, 3, 8, 2, 4, 7, 9);
        
        System.out.println("Boundary method: " + isValidBST_BoundaryMethod(test));
        System.out.println("Inorder method: " + new InorderValidator().isValidBST_ParadeMethod(test));
        
        // Test 4: Performance test
        System.out.println("\nTest 4 - Performance with large tree:");
        TreeNode largeBST = createLargeBST(10000);
        
        long start1 = System.nanoTime();
        boolean result1 = isValidBST_BoundaryMethod(largeBST);
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        boolean result2 = new InorderValidator().isValidBST_ParadeMethod(largeBST);
        long time2 = System.nanoTime() - start2;
        
        System.out.printf("Boundary method: %b in %.3f ms\n", result1, time1 / 1000000.0);
        System.out.printf("Inorder method: %b in %.3f ms\n", result2, time2 / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- BOUNDARY KEEPER: Each node has min/max bounds");
        System.out.println("- Left child: inherits parent's min, uses parent as max");
        System.out.println("- Right child: uses parent as min, inherits parent's max");
        System.out.println("- Common mistake: only checking immediate children");
        System.out.println("- Alternative: inorder traversal must be ascending");
    }
    
    private static TreeNode createLargeBST(int size) {
        return createBSTHelper(1, size);
    }
    
    private static TreeNode createBSTHelper(int start, int end) {
        if (start > end) return null;
        
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(mid);
        
        root.left = createBSTHelper(start, mid - 1);
        root.right = createBSTHelper(mid + 1, end);
        
        return root;
    }
}