package com.demo.tree;

import java.util.*;

/**
 * Problem: Binary Tree Right Side View
 * 
 * MNEMONIC: "THEATER AUDIENCE - See only the rightmost actor!"
 * 🎭 Looking at a stage from the right side, you only see one actor per row
 * 
 * PROBLEM: Return values of nodes visible from the right side.
 * Example:     1
 *            /   \
 *           2     3
 *            \     \
 *             5     4
 * Answer: [1, 3, 4]
 * 
 * TECHNIQUE 1: Level-order traversal (BFS)
 * - Process level by level
 * - Add last node of each level
 * 
 * TECHNIQUE 2: Modified DFS
 * - Visit right child first
 * - Track depth, add first node at each depth
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h) for DFS, O(w) for BFS (w = max width)
 */
public class RightSideView {
    
    /**
     * BFS approach - watch the stage row by row
     * 
     * REMEMBER: "THEATER ROWS"
     * Process each row, remember the rightmost actor
     * 
     * @param stage tree root
     * @return right side view
     */
    public static List<Integer> watchFromRight_BFS(TreeNode stage) {
        List<Integer> audienceView = new ArrayList<>();
        if (stage == null) return audienceView;
        
        Queue<TreeNode> theaterRow = new LinkedList<>();
        theaterRow.offer(stage);
        
        while (!theaterRow.isEmpty()) {
            int actorsInRow = theaterRow.size();
            
            // Watch all actors in this row
            for (int actor = 0; actor < actorsInRow; actor++) {
                TreeNode currentActor = theaterRow.poll();
                
                // Remember only the rightmost actor
                if (actor == actorsInRow - 1) {
                    audienceView.add(currentActor.val);
                }
                
                // Add next row's actors
                if (currentActor.left != null) {
                    theaterRow.offer(currentActor.left);
                }
                if (currentActor.right != null) {
                    theaterRow.offer(currentActor.right);
                }
            }
        }
        
        return audienceView;
    }
    
    /**
     * DFS approach - spotlight from right
     * 
     * REMEMBER: "SPOTLIGHT SCAN"
     * Shine spotlight from right, first actor hit at each level is visible
     * 
     * @param stage tree root  
     * @return right side view
     */
    public static List<Integer> watchFromRight_DFS(TreeNode stage) {
        List<Integer> spotlightView = new ArrayList<>();
        shineSpotlight(stage, spotlightView, 0);
        return spotlightView;
    }
    
    /**
     * Recursively shine spotlight, right side first
     * 
     * VISUALIZATION: Like a spotlight scanning right to left
     * First actor it hits at each level is visible
     */
    private static void shineSpotlight(TreeNode actor, List<Integer> view, int rowNumber) {
        if (actor == null) return;
        
        // If this is the first actor we see at this row, they're visible!
        if (rowNumber == view.size()) {
            view.add(actor.val);
        }
        
        // Shine spotlight on right side first (they're more visible)
        shineSpotlight(actor.right, view, rowNumber + 1);
        // Then check left side
        shineSpotlight(actor.left, view, rowNumber + 1);
    }
    
    /**
     * Visualize the right side view
     */
    private static void visualizeRightSideView(TreeNode root) {
        System.out.println("=== 🎭 THEATER AUDIENCE VISUALIZATION ===");
        
        System.out.println("\nStage layout:");
        TreeNode.visualizeTree(root);
        
        System.out.println("\n=== BFS: WATCHING ROW BY ROW ===");
        visualizeBFS(root);
        
        System.out.println("\n=== DFS: SPOTLIGHT SCAN ===");
        visualizeDFS(root);
    }
    
    private static void visualizeBFS(TreeNode root) {
        if (root == null) {
            System.out.println("Empty stage!");
            return;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int row = 0;
        
        System.out.println("Processing each row:");
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            System.out.printf("\nRow %d: ", row);
            
            TreeNode rightmost = null;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                System.out.print(node.val + " ");
                rightmost = node;
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            System.out.printf("→ Visible from right: %d", rightmost.val);
            row++;
        }
        
        System.out.println("\n\nFinal view: " + watchFromRight_BFS(root));
    }
    
    private static void visualizeDFS(TreeNode root) {
        System.out.println("Spotlight scanning (right to left):");
        List<Integer> view = new ArrayList<>();
        Map<Integer, List<Integer>> levels = new HashMap<>();
        
        collectLevels(root, 0, levels);
        spotlightScan(root, 0, view, new HashSet<>());
        
        System.out.println("\nFinal view: " + view);
    }
    
    private static void collectLevels(TreeNode node, int level, Map<Integer, List<Integer>> levels) {
        if (node == null) return;
        
        levels.computeIfAbsent(level, k -> new ArrayList<>()).add(node.val);
        collectLevels(node.left, level + 1, levels);
        collectLevels(node.right, level + 1, levels);
    }
    
    private static void spotlightScan(TreeNode node, int level, List<Integer> view, Set<Integer> seen) {
        if (node == null) return;
        
        if (!seen.contains(level)) {
            System.out.printf("Level %d: Spotlight hits %d first! (visible)\n", level, node.val);
            view.add(node.val);
            seen.add(level);
        } else {
            System.out.printf("Level %d: %d is hidden behind another actor\n", level, node.val);
        }
        
        // Scan right first
        spotlightScan(node.right, level + 1, view, seen);
        spotlightScan(node.left, level + 1, view, seen);
    }
    
    /**
     * Compare BFS vs DFS approaches
     */
    private static void compareApproaches() {
        System.out.println("\n=== 🎆 BFS vs DFS COMPARISON ===");
        
        System.out.println("\nBFS (Theater Rows):");
        System.out.println("• Natural level-by-level processing");
        System.out.println("• Easy to understand");
        System.out.println("• Space: O(w) where w is max width");
        System.out.println("• Good for wide trees");
        
        System.out.println("\nDFS (Spotlight Scan):");
        System.out.println("• Clever right-first traversal");
        System.out.println("• Space: O(h) where h is height");
        System.out.println("• Good for deep trees");
        System.out.println("• Elegant recursive solution");
        
        System.out.println("\nWhen to use which:");
        System.out.println("- Wide, shallow tree → DFS (less memory)");
        System.out.println("- Deep, narrow tree → BFS (avoid stack overflow)");
        System.out.println("- Clarity needed → BFS (more intuitive)");
    }
    
    /**
     * Test edge cases and special trees
     */
    private static void testSpecialCases() {
        System.out.println("\n=== 🌳 SPECIAL CASES ===");
        
        // Test 1: Only right spine
        System.out.println("\nTest 1 - Right spine only:");
        TreeNode rightOnly = new TreeNode(1);
        rightOnly.right = new TreeNode(2);
        rightOnly.right.right = new TreeNode(3);
        System.out.println("Tree: 1 → 2 → 3");
        System.out.println("View: " + watchFromRight_BFS(rightOnly));
        
        // Test 2: Only left spine  
        System.out.println("\nTest 2 - Left spine only:");
        TreeNode leftOnly = new TreeNode(1);
        leftOnly.left = new TreeNode(2);
        leftOnly.left.left = new TreeNode(3);
        System.out.println("Tree: 1 ← 2 ← 3");
        System.out.println("View: " + watchFromRight_BFS(leftOnly));
        
        // Test 3: Complete binary tree
        System.out.println("\nTest 3 - Complete binary tree:");
        TreeNode complete = TreeNode.createTree(1, 2, 3, 4, 5, 6, 7);
        System.out.println("View: " + watchFromRight_BFS(complete));
        
        // Test 4: Zigzag tree
        System.out.println("\nTest 4 - Zigzag tree:");
        TreeNode zigzag = new TreeNode(1);
        zigzag.left = new TreeNode(2);
        zigzag.left.right = new TreeNode(3);
        zigzag.left.right.left = new TreeNode(4);
        System.out.println("View: " + watchFromRight_DFS(zigzag));
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("🎭 RIGHT SIDE VIEW - Theater Audience!\n");
        
        // Test 1: Standard example
        System.out.println("Test 1 - Standard tree:");
        TreeNode tree1 = TreeNode.createTree(1, 2, 3, null, 5, null, 4);
        visualizeRightSideView(tree1);
        
        // Test 2: Compare approaches
        compareApproaches();
        
        // Test 3: Special cases
        testSpecialCases();
        
        // Test 4: Empty tree
        System.out.println("\nTest 4 - Edge cases:");
        System.out.println("Empty tree: " + watchFromRight_BFS(null));
        System.out.println("Single node: " + watchFromRight_DFS(new TreeNode(42)));
        
        // Test 5: Performance comparison
        System.out.println("\nTest 5 - Performance comparison:");
        
        // Create a wide tree
        TreeNode wideTree = createWideTree(10, 100);
        long start1 = System.nanoTime();
        List<Integer> bfsResult = watchFromRight_BFS(wideTree);
        long time1 = System.nanoTime() - start1;
        
        // Create a deep tree
        TreeNode deepTree = createDeepTree(1000);
        long start2 = System.nanoTime();
        List<Integer> dfsResult = watchFromRight_DFS(deepTree);
        long time2 = System.nanoTime() - start2;
        
        System.out.printf("Wide tree (10 levels, ~100 nodes/level):\n");
        System.out.printf("  BFS: %d nodes visible in %.3f ms\n", 
                         bfsResult.size(), time1 / 1000000.0);
        
        System.out.printf("Deep tree (1000 levels):\n");
        System.out.printf("  DFS: %d nodes visible in %.3f ms\n", 
                         dfsResult.size(), time2 / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- THEATER AUDIENCE: See rightmost actor per row");
        System.out.println("- BFS: Process level by level, take last");
        System.out.println("- DFS: Visit right first, take first at each depth");
        System.out.println("- Both approaches work, choose based on tree shape");
        System.out.println("- Think of it as viewing a theater stage from the right!");
    }
    
    private static TreeNode createWideTree(int levels, int nodesPerLevel) {
        if (levels <= 0) return null;
        
        Random rand = new Random(42);
        TreeNode root = new TreeNode(0);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        for (int level = 1; level < levels; level++) {
            int currentLevelSize = queue.size();
            
            for (int i = 0; i < currentLevelSize; i++) {
                TreeNode parent = queue.poll();
                
                // Add children randomly to create wide tree
                if (rand.nextDouble() < 0.8) {
                    parent.left = new TreeNode(level * 1000 + i * 2);
                    queue.offer(parent.left);
                }
                if (rand.nextDouble() < 0.8) {
                    parent.right = new TreeNode(level * 1000 + i * 2 + 1);
                    queue.offer(parent.right);
                }
            }
        }
        
        return root;
    }
    
    private static TreeNode createDeepTree(int depth) {
        TreeNode root = new TreeNode(0);
        TreeNode current = root;
        
        for (int i = 1; i < depth; i++) {
            if (i % 2 == 0) {
                current.left = new TreeNode(i);
                current = current.left;
            } else {
                current.right = new TreeNode(i);
                current = current.right;
            }
        }
        
        return root;
    }
}