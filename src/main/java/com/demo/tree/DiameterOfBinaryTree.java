package com.demo.tree;

import java.util.*;

/**
 * Problem: Diameter of Binary Tree
 * 
 * MNEMONIC: "BRIDGE BUILDER - Find the longest bridge across the tree!"
 * 🌉 Like building the longest possible bridge through the tree
 * 
 * PROBLEM: Find the length of the longest path between any two nodes.
 * Path may or may not pass through root.
 * 
 * Example:     1
 *            /   \
 *           2     3
 *          / \
 *         4   5
 * Answer: 3 (path 4-2-1-3 or 5-2-1-3)
 * 
 * TECHNIQUE: Track height and diameter simultaneously
 * - Diameter at node = left_height + right_height
 * - Update global max diameter
 * - Return height for parent's calculation
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(h)
 */
public class DiameterOfBinaryTree {
    
    /**
     * Find diameter of tree (longest bridge)
     * 
     * REMEMBER: "BRIDGE SPANS"
     * The longest bridge spans from deepest left to deepest right
     * 
     * @param treeBase tree root
     * @return diameter (number of edges)
     */
    public static int findLongestBridge(TreeNode treeBase) {
        // Track the longest bridge we've found
        int[] longestBridge = {0};
        
        // Calculate heights and find bridges
        measureBridgeSupports(treeBase, longestBridge);
        
        return longestBridge[0];
    }
    
    /**
     * Measure height of supports and potential bridge length
     * 
     * VISUALIZATION: Like measuring bridge supports
     * - Left support height
     * - Right support height
     * - Bridge length = left + right
     * 
     * @return height of this support
     */
    private static int measureBridgeSupports(TreeNode support, int[] longestBridge) {
        if (support == null) return 0;
        
        // Measure left and right support heights
        int leftSupportHeight = measureBridgeSupports(support.left, longestBridge);
        int rightSupportHeight = measureBridgeSupports(support.right, longestBridge);
        
        // Calculate bridge length through this support
        int bridgeLength = leftSupportHeight + rightSupportHeight;
        
        // Update longest bridge if this is better
        longestBridge[0] = Math.max(longestBridge[0], bridgeLength);
        
        // Return height of this support (for parent's bridge calculation)
        return 1 + Math.max(leftSupportHeight, rightSupportHeight);
    }
    
    /**
     * Visualize the diameter finding process
     */
    private static void visualizeDiameterFinding(TreeNode root) {
        System.out.println("=== 🌉 BRIDGE BUILDER VISUALIZATION ===");
        
        System.out.println("\nTree structure:");
        TreeNode.visualizeTree(root);
        
        System.out.println("\nBuilding bridges at each node:");
        int[] diameter = {0};
        Map<TreeNode, Integer> heights = new HashMap<>();
        
        visualizeBridgeBuilding(root, diameter, heights, "");
        
        System.out.printf("\n🎆 Longest bridge spans %d edges!\n", diameter[0]);
        
        // Show all possible paths
        System.out.println("\nPossible bridge paths:");
        findAllPaths(root, diameter[0]);
    }
    
    private static int visualizeBridgeBuilding(TreeNode node, int[] maxDiameter, 
                                               Map<TreeNode, Integer> heights, String indent) {
        if (node == null) {
            System.out.println(indent + "Ground level (null): height = 0");
            return 0;
        }
        
        System.out.println(indent + "At support " + node.val + ":");
        
        // Measure supports
        int leftHeight = visualizeBridgeBuilding(node.left, maxDiameter, heights, indent + "  ");
        int rightHeight = visualizeBridgeBuilding(node.right, maxDiameter, heights, indent + "  ");
        
        // Calculate bridge
        int bridgeHere = leftHeight + rightHeight;
        int heightHere = 1 + Math.max(leftHeight, rightHeight);
        
        System.out.printf("%s  Left support: %d, Right support: %d\n", indent, leftHeight, rightHeight);
        System.out.printf("%s  Bridge length here: %d + %d = %d edges\n", 
                         indent, leftHeight, rightHeight, bridgeHere);
        System.out.printf("%s  Support height: 1 + max(%d, %d) = %d\n", 
                         indent, leftHeight, rightHeight, heightHere);
        
        if (bridgeHere > maxDiameter[0]) {
            System.out.printf("%s  🎆 New longest bridge: %d edges!\n", indent, bridgeHere);
            maxDiameter[0] = bridgeHere;
        }
        
        heights.put(node, heightHere);
        return heightHere;
    }
    
    /**
     * Find and display all paths of given length
     */
    private static void findAllPaths(TreeNode root, int targetLength) {
        List<List<Integer>> allPaths = new ArrayList<>();
        
        // Find all paths from each node
        findPathsFromNode(root, targetLength, allPaths);
        
        // Display unique paths
        Set<String> uniquePaths = new HashSet<>();
        for (List<Integer> path : allPaths) {
            String pathStr = path.toString();
            String reverseStr = new ArrayList<>(path).toString();
            Collections.reverse(path);
            
            if (!uniquePaths.contains(pathStr) && !uniquePaths.contains(reverseStr)) {
                uniquePaths.add(pathStr);
                System.out.println("  " + pathStr.replace("[", "").replace("]", "").replace(",", " →"));
            }
        }
    }
    
    private static void findPathsFromNode(TreeNode node, int targetLength, List<List<Integer>> result) {
        if (node == null) return;
        
        List<List<Integer>> leftPaths = new ArrayList<>();
        List<List<Integer>> rightPaths = new ArrayList<>();
        
        getPaths(node.left, leftPaths, new ArrayList<>());
        getPaths(node.right, rightPaths, new ArrayList<>());
        
        // Check paths through this node
        for (List<Integer> leftPath : leftPaths) {
            for (List<Integer> rightPath : rightPaths) {
                if (leftPath.size() + rightPath.size() == targetLength) {
                    List<Integer> fullPath = new ArrayList<>();
                    for (int i = leftPath.size() - 1; i >= 0; i--) {
                        fullPath.add(leftPath.get(i));
                    }
                    fullPath.add(node.val);
                    fullPath.addAll(rightPath);
                    result.add(fullPath);
                }
            }
        }
        
        // Continue searching in subtrees
        findPathsFromNode(node.left, targetLength, result);
        findPathsFromNode(node.right, targetLength, result);
    }
    
    private static void getPaths(TreeNode node, List<List<Integer>> paths, List<Integer> current) {
        if (node == null) {
            if (!current.isEmpty()) {
                paths.add(new ArrayList<>(current));
            }
            return;
        }
        
        current.add(node.val);
        
        if (node.left == null && node.right == null) {
            paths.add(new ArrayList<>(current));
        } else {
            getPaths(node.left, paths, current);
            getPaths(node.right, paths, current);
        }
        
        current.remove(current.size() - 1);
    }
    
    /**
     * Show different diameter patterns
     */
    private static void demonstrateDiameterPatterns() {
        System.out.println("\n=== 🌳 DIAMETER PATTERNS ===");
        
        // Pattern 1: Through root
        System.out.println("\n1. Diameter through root:");
        System.out.println("       1");
        System.out.println("      / \\");
        System.out.println("     2   3");
        System.out.println("    / \\");
        System.out.println("   4   5");
        System.out.println("Longest path: 4 → 2 → 1 → 3 (or 5 → 2 → 1 → 3)");
        System.out.println("Diameter = 3 edges");
        
        // Pattern 2: Not through root
        System.out.println("\n2. Diameter NOT through root:");
        System.out.println("         1");
        System.out.println("        /");
        System.out.println("       2");
        System.out.println("      / \\");
        System.out.println("     3   4");
        System.out.println("    / \\   \\\\");
        System.out.println("   5   6   7");
        System.out.println("Longest path: 5 → 3 → 2 → 4 → 7");
        System.out.println("Diameter = 4 edges (skips root!)");
        
        // Pattern 3: Linear tree
        System.out.println("\n3. Linear tree (path is the tree):");
        System.out.println("1 → 2 → 3 → 4 → 5");
        System.out.println("Diameter = 4 edges");
        
        // Pattern 4: Single node
        System.out.println("\n4. Single node:");
        System.out.println("   1");
        System.out.println("Diameter = 0 edges");
    }
    
    /**
     * Test various tree shapes
     */
    private static void testVariousShapes() {
        System.out.println("\n=== 🌳 VARIOUS TREE SHAPES ===");
        
        // Test 1: Balanced tree
        System.out.println("\nTest 1 - Balanced tree:");
        TreeNode balanced = TreeNode.createTree(1, 2, 3, 4, 5, 6, 7);
        visualizeDiameterFinding(balanced);
        
        // Test 2: Skewed tree
        System.out.println("\nTest 2 - Left-skewed tree:");
        TreeNode skewed = new TreeNode(1);
        skewed.left = new TreeNode(2);
        skewed.left.left = new TreeNode(3);
        skewed.left.left.left = new TreeNode(4);
        int diameter2 = findLongestBridge(skewed);
        System.out.println("Diameter: " + diameter2);
        
        // Test 3: Complex tree
        System.out.println("\nTest 3 - Complex tree:");
        TreeNode complex = new TreeNode(1);
        complex.left = new TreeNode(2);
        complex.left.left = new TreeNode(4);
        complex.left.right = new TreeNode(5);
        complex.left.left.left = new TreeNode(8);
        complex.left.left.right = new TreeNode(9);
        complex.right = new TreeNode(3);
        complex.right.right = new TreeNode(7);
        visualizeDiameterFinding(complex);
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("🌉 DIAMETER OF BINARY TREE - Bridge Builder!\n");
        
        // Test 1: Various shapes
        testVariousShapes();
        
        // Test 2: Diameter patterns
        demonstrateDiameterPatterns();
        
        // Test 3: Edge cases
        System.out.println("\nTest 3 - Edge cases:");
        System.out.println("Empty tree: " + findLongestBridge(null));
        System.out.println("Single node: " + findLongestBridge(new TreeNode(1)));
        
        TreeNode twoNodes = new TreeNode(1);
        twoNodes.left = new TreeNode(2);
        System.out.println("Two nodes: " + findLongestBridge(twoNodes));
        
        // Test 4: Performance test
        System.out.println("\nTest 4 - Performance with large tree:");
        TreeNode largeTree = createBalancedTree(15); // 2^15 - 1 nodes
        
        long start = System.nanoTime();
        int largeDiameter = findLongestBridge(largeTree);
        long time = System.nanoTime() - start;
        
        System.out.printf("Balanced tree with %d nodes: diameter = %d in %.3f ms\n", 
                         (1 << 15) - 1, largeDiameter, time / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- BRIDGE BUILDER: Find longest bridge in tree");
        System.out.println("- Bridge at node = left height + right height");
        System.out.println("- Height = 1 + max(left, right)");
        System.out.println("- Bridge might not go through root!");
        System.out.println("- Track global maximum as we calculate");
    }
    
    private static TreeNode createBalancedTree(int depth) {
        if (depth <= 0) return null;
        
        TreeNode root = new TreeNode(depth);
        root.left = createBalancedTree(depth - 1);
        root.right = createBalancedTree(depth - 1);
        
        return root;
    }
}