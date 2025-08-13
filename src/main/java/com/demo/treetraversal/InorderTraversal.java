package com.demo.treetraversal;

import java.util.*;

/**
 * Problem: Inorder Traversal (Iterative)
 * 
 * MNEMONIC: "LEFT-ROOT-RIGHT - Like reading a book!"
 * 📖 Read the left page, then the center, then the right page
 * 
 * PROBLEM: Traverse a binary tree in inorder sequence (Left, Root, Right).
 * For a BST, this returns values in sorted order.
 * Example: Tree:     4
 *                   / \
 *                  2   6
 *                 / \ / \
 *                1  3 5  7
 * Answer: [1, 2, 3, 4, 5, 6, 7] - Notice it's sorted!
 * 
 * TECHNIQUE: Stack-based "Go Left, Visit, Go Right"
 * - Keep going left as far as possible (like flipping to the first page)
 * - When can't go left, read current page (visit node)
 * - Then check the right side
 * 
 * VISUALIZATION: Like exploring a house
 * 1. Enter room, always check left door first
 * 2. If no left door, look around the room (visit)
 * 3. Then check right door
 * 4. Use breadcrumbs (stack) to remember where you came from
 * 
 * Time Complexity: O(n) - visit each node once
 * Space Complexity: O(h) - stack can contain at most h nodes (tree height)
 */
public class InorderTraversal {
    
    /**
     * Perform inorder traversal iteratively
     * 
     * REMEMBER: "Left-Root-Right" = "LRR" = "Learn to Read Right"
     * 
     * @param treeRoot the root of the binary tree
     * @return list of values in sorted order (for BST)
     */
    public static List<Integer> inorderTraversal_LeftRootRight(TreeNode treeRoot) {
        List<Integer> sortedResult = new ArrayList<>();
        Stack<TreeNode> breadcrumbTrail = new Stack<>();  // To remember our path
        TreeNode currentExplorer = treeRoot;
        
        while (currentExplorer != null || !breadcrumbTrail.isEmpty()) {
            // Phase 1: Go as LEFT as possible (find the first page)
            while (currentExplorer != null) {
                breadcrumbTrail.push(currentExplorer);  // Drop breadcrumb
                currentExplorer = currentExplorer.leftBranch;  // Go left
            }
            
            // Phase 2: Visit the node (read the current page)
            currentExplorer = breadcrumbTrail.pop();  // Pick up breadcrumb
            sortedResult.add(currentExplorer.nodeValue);  // Read the value
            
            // Phase 3: Try to go RIGHT (check next chapter)
            currentExplorer = currentExplorer.rightBranch;
        }
        
        return sortedResult;
    }
    
    /**
     * Recursive version for comparison
     * 
     * MNEMONIC: "LRR Recipe" - Left, Root, Right
     * Like a cooking recipe: do left first, then center, then right
     */
    public static List<Integer> inorderRecursive_SimpleVersion(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        
        inorderHelper(node.leftBranch, result);   // L - Left first
        result.add(node.nodeValue);               // R - Root (center)
        inorderHelper(node.rightBranch, result);  // R - Right last
    }
    
    /**
     * Visualization helper - shows the traversal process step by step
     */
    private static void visualizeTraversal(TreeNode root) {
        System.out.println("=== 📖 INORDER TRAVERSAL: Left-Root-Right ===");
        System.out.println("Remember: Like reading a book from left to right!");
        System.out.println();
        
        if (root == null) {
            System.out.println("Empty tree - nothing to read!");
            return;
        }
        
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        int step = 0;
        
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                step++;
                System.out.printf("Step %d: Going LEFT from node %d\n", 
                                step, current.nodeValue);
                stack.push(current);
                current = current.leftBranch;
            }
            
            current = stack.pop();
            step++;
            System.out.printf("Step %d: VISITING node %d (no more left)\n", 
                            step, current.nodeValue);
            result.add(current.nodeValue);
            
            if (current.rightBranch != null) {
                System.out.printf("         Now going RIGHT to explore...\n");
            }
            current = current.rightBranch;
        }
        
        System.out.println("\nFinal inorder result: " + result);
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("🌳 INORDER TRAVERSAL - The Natural Reading Order\n");
        
        // Test 1: Perfect BST
        System.out.println("Test 1 - Perfect Binary Search Tree:");
        TreeNode bst = TreeNode.createSampleBST();
        System.out.println("Tree structure:");
        System.out.println("       4");
        System.out.println("      / \\");
        System.out.println("     2   6");
        System.out.println("    / \\ / \\");
        System.out.println("   1  3 5  7");
        
        visualizeTraversal(bst);
        System.out.println("\nNotice: BST inorder gives SORTED values!");
        System.out.println();
        
        // Test 2: Right-skewed tree
        System.out.println("\nTest 2 - Right-Skewed Tree (Ladder):");
        TreeNode skewed = TreeNode.createRightSkewedTree();
        System.out.println("Tree: 1 -> 2 -> 3 -> 4 (all right children)");
        List<Integer> skewedResult = inorderTraversal_LeftRootRight(skewed);
        System.out.println("Inorder result: " + skewedResult);
        System.out.println("Even skewed trees follow Left-Root-Right!");
        System.out.println();
        
        // Test 3: Single node
        System.out.println("\nTest 3 - Single Node:");
        TreeNode single = new TreeNode(42);
        System.out.println("Tree: just node 42");
        System.out.println("Inorder: " + inorderTraversal_LeftRootRight(single));
        System.out.println();
        
        // Test 4: Empty tree
        System.out.println("\nTest 4 - Empty Tree:");
        System.out.println("Inorder of null: " + inorderTraversal_LeftRootRight(null));
        System.out.println();
        
        // Test 5: Compare iterative vs recursive
        System.out.println("\nTest 5 - Iterative vs Recursive Comparison:");
        TreeNode testTree = TreeNode.createSampleBST();
        List<Integer> iterative = inorderTraversal_LeftRootRight(testTree);
        List<Integer> recursive = inorderRecursive_SimpleVersion(testTree);
        System.out.println("Iterative result: " + iterative);
        System.out.println("Recursive result: " + recursive);
        System.out.println("Results match: " + iterative.equals(recursive));
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- Inorder = LEFT-ROOT-RIGHT = 'Learn to Read Right'");
        System.out.println("- For BST, inorder gives SORTED values");
        System.out.println("- Stack mimics recursion for iterative approach");
        System.out.println("- Go left until can't, visit node, then go right");
    }
}