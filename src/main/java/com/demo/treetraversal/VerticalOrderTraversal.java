package com.demo.treetraversal;

import java.util.*;

/**
 * Problem: Vertical Order Traversal
 * 
 * MNEMONIC: "COLUMNS OF A NEWSPAPER - Read top to bottom!"
 * 📰 Like reading newspaper columns: leftmost column first, then next...
 * 
 * PROBLEM: Traverse a binary tree vertically from left to right.
 * Nodes in the same vertical line are grouped together.
 * Example:        3
 *               /   \
 *              9     20
 *                   /  \
 *                  15   7
 * Answer: [[9], [3, 15], [20], [7]] - Column by column!
 * 
 * TECHNIQUE: BFS with Column Tracking
 * - Assign column numbers: root = 0, left child = col-1, right child = col+1
 * - Use TreeMap to maintain sorted order of columns
 * - BFS ensures top-to-bottom order within same column
 * 
 * VISUALIZATION: Like a coordinate system
 * Column: -2  -1   0   1   2
 *              9   3       
 *                 15  20
 *                      7
 * 
 * Time Complexity: O(n log n) - TreeMap operations
 * Space Complexity: O(n) - storing all nodes
 */
public class VerticalOrderTraversal {
    
    /**
     * Perform vertical order traversal
     * 
     * REMEMBER: "NEWSPAPER COLUMNS" - Read each column top to bottom
     * Like reading a newspaper: leftmost column first!
     * 
     * @param newspaperRoot the root of the tree
     * @return columns from left to right
     */
    public static List<List<Integer>> verticalOrderTraversal_NewspaperColumns(TreeNode newspaperRoot) {
        List<List<Integer>> newspaperColumns = new ArrayList<>();
        if (newspaperRoot == null) return newspaperColumns;
        
        // TreeMap sorts columns by key (column number)
        Map<Integer, List<Integer>> columnMap = new TreeMap<>();
        
        // Queue for BFS: pairs of (node, column)
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        
        nodeQueue.offer(newspaperRoot);
        columnQueue.offer(0);  // Root starts at column 0
        
        while (!nodeQueue.isEmpty()) {
            TreeNode currentWord = nodeQueue.poll();
            int currentColumn = columnQueue.poll();
            
            // Add word to its column
            columnMap.putIfAbsent(currentColumn, new ArrayList<>());
            columnMap.get(currentColumn).add(currentWord.nodeValue);
            
            // Left child goes to column-1 (left)
            if (currentWord.leftBranch != null) {
                nodeQueue.offer(currentWord.leftBranch);
                columnQueue.offer(currentColumn - 1);
            }
            
            // Right child goes to column+1 (right)
            if (currentWord.rightBranch != null) {
                nodeQueue.offer(currentWord.rightBranch);
                columnQueue.offer(currentColumn + 1);
            }
        }
        
        // Convert map to list (TreeMap ensures sorted order)
        newspaperColumns.addAll(columnMap.values());
        return newspaperColumns;
    }
    
    /**
     * Alternative: Using a wrapper class for cleaner code
     */
    static class NodeColumnPair {
        TreeNode node;
        int column;
        
        NodeColumnPair(TreeNode node, int column) {
            this.node = node;
            this.column = column;
        }
    }
    
    public static List<List<Integer>> verticalOrderCleanVersion(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Map<Integer, List<Integer>> columnMap = new TreeMap<>();
        Queue<NodeColumnPair> queue = new LinkedList<>();
        
        queue.offer(new NodeColumnPair(root, 0));
        
        while (!queue.isEmpty()) {
            NodeColumnPair pair = queue.poll();
            
            columnMap.putIfAbsent(pair.column, new ArrayList<>());
            columnMap.get(pair.column).add(pair.node.nodeValue);
            
            if (pair.node.leftBranch != null) {
                queue.offer(new NodeColumnPair(pair.node.leftBranch, pair.column - 1));
            }
            if (pair.node.rightBranch != null) {
                queue.offer(new NodeColumnPair(pair.node.rightBranch, pair.column + 1));
            }
        }
        
        result.addAll(columnMap.values());
        return result;
    }
    
    /**
     * Visualization helper - shows the coordinate system
     */
    private static void visualizeCoordinateSystem(TreeNode root) {
        System.out.println("=== 📐 COORDINATE SYSTEM VIEW ===");
        
        if (root == null) {
            System.out.println("Empty tree!");
            return;
        }
        
        // First, collect all nodes with their positions
        Map<Integer, Map<Integer, List<Integer>>> positionMap = new TreeMap<>();
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> colQueue = new LinkedList<>();
        Queue<Integer> rowQueue = new LinkedList<>();
        
        nodeQueue.offer(root);
        colQueue.offer(0);
        rowQueue.offer(0);
        
        int maxRow = 0;
        
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int col = colQueue.poll();
            int row = rowQueue.poll();
            
            maxRow = Math.max(maxRow, row);
            
            positionMap.putIfAbsent(row, new TreeMap<>());
            positionMap.get(row).putIfAbsent(col, new ArrayList<>());
            positionMap.get(row).get(col).add(node.nodeValue);
            
            if (node.leftBranch != null) {
                nodeQueue.offer(node.leftBranch);
                colQueue.offer(col - 1);
                rowQueue.offer(row + 1);
            }
            if (node.rightBranch != null) {
                nodeQueue.offer(node.rightBranch);
                colQueue.offer(col + 1);
                rowQueue.offer(row + 1);
            }
        }
        
        // Find column range
        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;
        for (Map<Integer, List<Integer>> rowMap : positionMap.values()) {
            for (int col : rowMap.keySet()) {
                minCol = Math.min(minCol, col);
                maxCol = Math.max(maxCol, col);
            }
        }
        
        // Print header
        System.out.print("Column: ");
        for (int col = minCol; col <= maxCol; col++) {
            System.out.printf("%3d ", col);
        }
        System.out.println("\n        " + "----".repeat(maxCol - minCol + 1));
        
        // Print grid
        for (int row = 0; row <= maxRow; row++) {
            System.out.printf("Row %d:  ", row);
            for (int col = minCol; col <= maxCol; col++) {
                if (positionMap.containsKey(row) && 
                    positionMap.get(row).containsKey(col)) {
                    System.out.printf("%3d ", positionMap.get(row).get(col).get(0));
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Show difference between vertical and level order
     */
    private static void compareWithLevelOrder(TreeNode root) {
        System.out.println("\n=== 📊 Vertical vs Level Order ===");
        
        List<List<Integer>> vertical = verticalOrderTraversal_NewspaperColumns(root);
        // List<List<Integer>> level = LevelOrderTraversal.levelOrderTraversal_FloorByFloor(root); // TODO: Create LevelOrderTraversal class
        
        System.out.println("Vertical Order (by column): " + vertical);
        // System.out.println("Level Order (by row):       " + level); // TODO: Uncomment when LevelOrderTraversal is available
        System.out.println("\nDifference: Vertical groups by X-coordinate, Level groups by Y-coordinate!");
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("📰 VERTICAL ORDER TRAVERSAL - Newspaper Columns!\n");
        
        // Test 1: Basic example
        System.out.println("Test 1 - Reading the Newspaper:");
        TreeNode newspaper = TreeNode.createVerticalTestTree();
        System.out.println("Tree:     3");
        System.out.println("        /   \\");
        System.out.println("       9     20");
        System.out.println("            /  \\");
        System.out.println("           15   7\n");
        
        List<List<Integer>> columns = verticalOrderTraversal_NewspaperColumns(newspaper);
        System.out.println("Newspaper columns (left to right): " + columns);
        System.out.println("Column 1: [9] - Leftmost");
        System.out.println("Column 2: [3,15] - Center (notice top-to-bottom order)");
        System.out.println("Column 3: [20] - Right of center");
        System.out.println("Column 4: [7] - Rightmost");
        System.out.println();
        
        // Test 2: Coordinate system view
        System.out.println("Test 2 - Coordinate System:");
        visualizeCoordinateSystem(newspaper);
        System.out.println();
        
        // Test 3: Perfect BST
        System.out.println("Test 3 - Perfect BST:");
        TreeNode bst = TreeNode.createSampleBST();
        System.out.println("BST vertical order: " + 
                         verticalOrderTraversal_NewspaperColumns(bst));
        visualizeCoordinateSystem(bst);
        System.out.println();
        
        // Test 4: Compare with level order
        System.out.println("Test 4 - Comparison:");
        compareWithLevelOrder(newspaper);
        System.out.println();
        
        // Test 5: Skewed tree
        System.out.println("Test 5 - Right-Skewed Tree:");
        TreeNode skewed = TreeNode.createRightSkewedTree();
        System.out.println("Skewed vertical order: " + 
                         verticalOrderTraversal_NewspaperColumns(skewed));
        System.out.println("Each node in its own column (diagonal line)!");
        System.out.println();
        
        // Test 6: Complex tree
        System.out.println("Test 6 - Complex Tree:");
        TreeNode complex = new TreeNode(1);
        complex.leftBranch = new TreeNode(2);
        complex.rightBranch = new TreeNode(3);
        complex.leftBranch.leftBranch = new TreeNode(4);
        complex.leftBranch.rightBranch = new TreeNode(5);
        complex.rightBranch.leftBranch = new TreeNode(6);
        complex.rightBranch.rightBranch = new TreeNode(7);
        
        System.out.println("Complex tree vertical order: " + 
                         verticalOrderTraversal_NewspaperColumns(complex));
        visualizeCoordinateSystem(complex);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- Vertical Order = NEWSPAPER COLUMNS");
        System.out.println("- Root at column 0, left child -1, right child +1");
        System.out.println("- Use TreeMap to keep columns sorted");
        System.out.println("- BFS ensures top-to-bottom within each column");
        System.out.println("- Like reading newspaper: leftmost column first!");
    }
}