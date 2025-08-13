package com.demo.treetraversal;

import java.util.*;

/**
 * Problem: Morris Inorder Traversal
 * 
 * MNEMONIC: "THREADING A NEEDLE - Create temporary threads!"
 * 🧵 Like sewing: create threads to find your way back
 * 
 * PROBLEM: Perform inorder traversal using O(1) space (no stack/recursion).
 * This is the Morris Traversal algorithm - a clever space optimization.
 * 
 * TECHNIQUE: Temporary Tree Threading
 * - Create temporary links (threads) from nodes back to their successors
 * - Use these threads to return without a stack
 * - Restore tree structure as we go (leave no trace!)
 * 
 * PROCESS: "The Seamstress Algorithm"
 * 1. If no left child → visit current, go right
 * 2. Find rightmost node in left subtree (predecessor)
 * 3. If predecessor has no thread → create thread to current, go left
 * 4. If predecessor has thread → remove thread, visit current, go right
 * 
 * VISUALIZATION: Creating threads
 *     4                4
 *    / \              / \
 *   2   6    =>      2   6
 *  / \              / \ 
 * 1   3            1   3
 *                   \___↗ (thread back to 2)
 * 
 * Time Complexity: O(n) - each edge traversed at most twice
 * Space Complexity: O(1) - no additional space used!
 */
public class MorrisInorderTraversal {
    
    /**
     * Morris Inorder Traversal - The space-efficient magician!
     * 
     * REMEMBER: "THREAD AND VISIT" - Like Hansel and Gretel's breadcrumbs
     * But instead of crumbs, we use threads!
     * 
     * @param fabricRoot the root of the tree to thread
     * @return inorder traversal with O(1) space
     */
    public static List<Integer> morrisInorder_ThreadingNeedle(TreeNode fabricRoot) {
        List<Integer> sewingPattern = new ArrayList<>();  // Our result
        TreeNode currentStitch = fabricRoot;  // Current position in fabric
        
        while (currentStitch != null) {
            if (currentStitch.leftBranch == null) {
                // Case 1: No left child - visit and go right
                sewingPattern.add(currentStitch.nodeValue);
                currentStitch = currentStitch.rightBranch;
            } else {
                // Case 2: Has left child - need to thread
                
                // Find the rightmost node in left subtree (inorder predecessor)
                TreeNode threadEnd = findInorderPredecessor(currentStitch);
                
                if (threadEnd.rightBranch == null) {
                    // No thread exists - create one!
                    threadEnd.rightBranch = currentStitch;  // Thread back to current
                    currentStitch = currentStitch.leftBranch;  // Go explore left
                } else {
                    // Thread exists - we're back! Remove thread and visit
                    threadEnd.rightBranch = null;  // Cut the thread
                    sewingPattern.add(currentStitch.nodeValue);  // Visit
                    currentStitch = currentStitch.rightBranch;  // Go right
                }
            }
        }
        
        return sewingPattern;
    }
    
    /**
     * Find inorder predecessor (rightmost in left subtree)
     * This is where we'll attach our thread!
     */
    private static TreeNode findInorderPredecessor(TreeNode node) {
        TreeNode predecessor = node.leftBranch;
        
        // Go right as far as possible, but stop if we see our thread
        while (predecessor.rightBranch != null && 
               predecessor.rightBranch != node) {
            predecessor = predecessor.rightBranch;
        }
        
        return predecessor;
    }
    
    /**
     * Visualization helper - shows threading process step by step
     */
    private static void visualizeThreadingProcess(TreeNode root) {
        System.out.println("=== 🧵 MORRIS THREADING VISUALIZATION ===");
        System.out.println("Watch as we create and remove threads!\n");
        
        if (root == null) {
            System.out.println("Empty tree - no threading needed!");
            return;
        }
        
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        int step = 0;
        
        while (current != null) {
            step++;
            System.out.printf("Step %d: At node %d\n", step, current.nodeValue);
            
            if (current.leftBranch == null) {
                System.out.println("  → No left child, VISIT and go right");
                result.add(current.nodeValue);
                current = current.rightBranch;
            } else {
                TreeNode pred = findInorderPredecessor(current);
                
                if (pred.rightBranch == null) {
                    System.out.printf("  → Creating thread: %d -----> %d\n", 
                                    pred.nodeValue, current.nodeValue);
                    System.out.println("  → Going left to explore");
                    pred.rightBranch = current;
                    current = current.leftBranch;
                } else {
                    System.out.printf("  → Found thread from %d, cutting it\n", 
                                    pred.nodeValue);
                    System.out.println("  → VISIT current and go right");
                    pred.rightBranch = null;
                    result.add(current.nodeValue);
                    current = current.rightBranch;
                }
            }
            System.out.println();
        }
        
        System.out.println("Final result: " + result);
    }
    
    /**
     * Compare Morris with standard inorder
     */
    private static void compareWithStandardInorder(TreeNode root) {
        System.out.println("\n=== 🏆 Morris vs Standard Comparison ===");
        
        // Morris traversal
        long morrisStart = System.nanoTime();
        List<Integer> morrisResult = morrisInorder_ThreadingNeedle(root);
        long morrisTime = System.nanoTime() - morrisStart;
        
        // Standard traversal
        long standardStart = System.nanoTime();
        List<Integer> standardResult = InorderTraversal.inorderTraversal_LeftRootRight(root);
        long standardTime = System.nanoTime() - standardStart;
        
        System.out.println("Morris result:   " + morrisResult);
        System.out.println("Standard result: " + standardResult);
        System.out.println("Results match: " + morrisResult.equals(standardResult));
        
        System.out.printf("\nMorris time:   %.3f μs (O(1) space!)\n", morrisTime / 1000.0);
        System.out.printf("Standard time: %.3f μs (O(h) space)\n", standardTime / 1000.0);
        
        System.out.println("\nSpace Complexity:");
        System.out.println("Morris:   O(1) - No stack needed!");
        System.out.println("Standard: O(h) - Stack can be deep");
    }
    
    /**
     * Demonstrate why Morris is useful
     */
    private static void demonstrateUseCases() {
        System.out.println("\n=== 💡 When to Use Morris Traversal ===");
        
        System.out.println("✅ USE Morris when:");
        System.out.println("   - Space is extremely limited (embedded systems)");
        System.out.println("   - Tree is very deep (avoid stack overflow)");
        System.out.println("   - You need to traverse multiple times");
        
        System.out.println("\n❌ AVOID Morris when:");
        System.out.println("   - Tree structure must not be modified temporarily");
        System.out.println("   - Concurrent access to tree (threading isn't thread-safe!)");
        System.out.println("   - Code simplicity is more important than space");
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("🧵 MORRIS INORDER TRAVERSAL - The Space Magician!\n");
        
        // Test 1: Basic BST with visualization
        System.out.println("Test 1 - Threading Process:");
        TreeNode bst = TreeNode.createSampleBST();
        visualizeThreadingProcess(bst);
        System.out.println();
        
        // Test 2: Compare with standard
        System.out.println("Test 2 - Comparison:");
        compareWithStandardInorder(bst);
        System.out.println();
        
        // Test 3: Edge cases
        System.out.println("Test 3 - Edge Cases:");
        
        // Empty tree
        System.out.println("Empty tree: " + morrisInorder_ThreadingNeedle(null));
        
        // Single node
        TreeNode single = new TreeNode(42);
        System.out.println("Single node: " + morrisInorder_ThreadingNeedle(single));
        
        // Left skewed
        TreeNode leftSkewed = new TreeNode(4);
        leftSkewed.leftBranch = new TreeNode(3);
        leftSkewed.leftBranch.leftBranch = new TreeNode(2);
        leftSkewed.leftBranch.leftBranch.leftBranch = new TreeNode(1);
        System.out.println("Left-skewed: " + morrisInorder_ThreadingNeedle(leftSkewed));
        
        // Right skewed
        TreeNode rightSkewed = TreeNode.createRightSkewedTree();
        System.out.println("Right-skewed: " + morrisInorder_ThreadingNeedle(rightSkewed));
        System.out.println();
        
        // Test 4: Large tree performance
        System.out.println("Test 4 - Performance with Deep Tree:");
        
        // Create a deep tree
        TreeNode deepRoot = new TreeNode(1000);
        TreeNode current = deepRoot;
        for (int i = 999; i >= 1; i--) {
            current.leftBranch = new TreeNode(i);
            current = current.leftBranch;
        }
        
        System.out.println("Tree depth: 1000 nodes");
        
        long morrisStart = System.nanoTime();
        List<Integer> morrisDeep = morrisInorder_ThreadingNeedle(deepRoot);
        long morrisTime = System.nanoTime() - morrisStart;
        
        System.out.printf("Morris traversed %d nodes in %.3f ms\n", 
                         morrisDeep.size(), morrisTime / 1000000.0);
        System.out.println("Space used: O(1) - No stack overflow risk!");
        System.out.println();
        
        // Test 5: Use cases
        demonstrateUseCases();
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- Morris = 'Threading a Needle' - temporary threads");
        System.out.println("- O(1) space but temporarily modifies tree");
        System.out.println("- Find predecessor → Create/Remove thread → Move");
        System.out.println("- Perfect for space-constrained environments");
        System.out.println("- Trade-off: More complex code for O(1) space");
    }
}