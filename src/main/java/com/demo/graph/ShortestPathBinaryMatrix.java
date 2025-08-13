package com.demo.graph;

import java.util.*;

/**
 * Problem: Shortest Path in Binary Matrix (BFS)
 * 
 * MNEMONIC: "CHESS KING'S JOURNEY - 8 directions to victory!"
 * ♚ The king can move in all 8 directions, finding the shortest path
 * 
 * PROBLEM: Find shortest path from top-left to bottom-right in binary matrix.
 * Can move in 8 directions, only through cells with value 0.
 * Example: [[0,0,0],
 *           [1,1,0],
 *           [1,1,0]]
 * Answer: 4 (path length)
 * 
 * TECHNIQUE: BFS for Shortest Path
 * - BFS guarantees shortest path in unweighted graph
 * - Use queue to explore level by level
 * - Mark visited cells to avoid revisiting
 * 
 * VISUALIZATION:
 * Level 1: [(0,0)]
 * Level 2: [(0,1), (1,0) blocked]
 * Level 3: [(0,2), (1,2)]
 * Level 4: [(2,2)] - reached destination!
 * 
 * 8 DIRECTIONS: 
 * [-1,-1] [-1,0] [-1,1]
 * [0,-1]   KING   [0,1]
 * [1,-1]  [1,0]   [1,1]
 * 
 * Time Complexity: O(n²) - may visit all cells
 * Space Complexity: O(n²) - queue can contain O(n²) cells
 */
public class ShortestPathBinaryMatrix {
    
    /**
     * Find shortest path for the Chess King
     * 
 * REMEMBER: "KING'S SHORTEST JOURNEY"
     * The king explores all 8 directions level by level
     * 
     * @param chessBoard binary matrix (0 = free, 1 = blocked)
     * @return shortest path length, or -1 if no path
     */
    public static int findKingsPath_ChessJourney(int[][] chessBoard) {
        int boardSize = chessBoard.length;
        
        // King can't start or end on blocked square
        if (chessBoard[0][0] == 1 || chessBoard[boardSize-1][boardSize-1] == 1) {
            return -1;
        }
        
        // Special case: 1x1 board
        if (boardSize == 1) return 1;
        
        // King's 8 possible moves
        int[][] kingMoves = {
            {-1,-1}, {-1,0}, {-1,1},  // Up-left, Up, Up-right
            {0,-1},          {0,1},    // Left,        Right
            {1,-1},  {1,0},  {1,1}     // Down-left, Down, Down-right
        };
        
        // BFS queue: [row, col]
        Queue<int[]> moveQueue = new LinkedList<>();
        moveQueue.offer(new int[]{0, 0});
        chessBoard[0][0] = 1; // Mark starting position as visited
        
        int pathLength = 1;
        
        while (!moveQueue.isEmpty()) {
            int movesAtThisLevel = moveQueue.size();
            pathLength++;
            
            // Process all positions at current distance
            for (int i = 0; i < movesAtThisLevel; i++) {
                int[] currentPos = moveQueue.poll();
                
                // Try all 8 directions
                for (int[] move : kingMoves) {
                    int newRow = currentPos[0] + move[0];
                    int newCol = currentPos[1] + move[1];
                    
                    // Check if move is valid
                    if (isValidSquare(newRow, newCol, boardSize) && 
                        chessBoard[newRow][newCol] == 0) {
                        
                        // Check if we reached the destination
                        if (newRow == boardSize-1 && newCol == boardSize-1) {
                            return pathLength;
                        }
                        
                        // Add to queue and mark as visited
                        moveQueue.offer(new int[]{newRow, newCol});
                        chessBoard[newRow][newCol] = 1;
                    }
                }
            }
        }
        
        return -1; // No path found
    }
    
    private static boolean isValidSquare(int row, int col, int size) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
    
    /**
     * Visualize the king's journey
     */
    private static void visualizeKingsJourney(int[][] grid) {
        System.out.println("=== ♚ CHESS KING'S JOURNEY ===");
        
        int n = grid.length;
        int[][] originalGrid = new int[n][n];
        
        // Copy grid to preserve original
        for (int i = 0; i < n; i++) {
            originalGrid[i] = grid[i].clone();
        }
        
        // Show the board
        System.out.println("\nChess Board:");
        printBoard(originalGrid);
        
        // Find path with visualization
        int pathLength = findPathWithVisualization(originalGrid);
        
        if (pathLength != -1) {
            System.out.printf("\n✅ King reached destination in %d moves!\n", pathLength);
        } else {
            System.out.println("\n❌ No path exists! The king is blocked!\n");
        }
    }
    
    private static int findPathWithVisualization(int[][] grid) {
        int n = grid.length;
        
        if (grid[0][0] == 1 || grid[n-1][n-1] == 1) {
            return -1;
        }
        
        if (n == 1) return 1;
        
        int[][] directions = {{-1,-1}, {-1,0}, {-1,1}, {0,-1}, 
                             {0,1}, {1,-1}, {1,0}, {1,1}};
        
        Queue<int[]> queue = new LinkedList<>();
        int[][] distances = new int[n][n];
        
        queue.offer(new int[]{0, 0});
        distances[0][0] = 1;
        
        System.out.println("\nKing's exploration:");
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.printf("Level %d: ", distances[queue.peek()[0]][queue.peek()[1]]);
            
            for (int i = 0; i < levelSize; i++) {
                int[] pos = queue.poll();
                System.out.printf("(%d,%d) ", pos[0], pos[1]);
                
                for (int[] dir : directions) {
                    int newRow = pos[0] + dir[0];
                    int newCol = pos[1] + dir[1];
                    
                    if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n && 
                        grid[newRow][newCol] == 0 && distances[newRow][newCol] == 0) {
                        
                        distances[newRow][newCol] = distances[pos[0]][pos[1]] + 1;
                        
                        if (newRow == n-1 && newCol == n-1) {
                            System.out.println();
                            System.out.println("\nPath visualization:");
                            printPath(distances);
                            return distances[newRow][newCol];
                        }
                        
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
            System.out.println();
        }
        
        return -1;
    }
    
    private static void printBoard(int[][] grid) {
        int n = grid.length;
        
        // Top border
        System.out.print("  ");
        for (int j = 0; j < n; j++) {
            System.out.printf(" %d ", j);
        }
        System.out.println();
        
        for (int i = 0; i < n; i++) {
            System.out.printf("%d ", i);
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("[♚]"); // King start
                } else if (i == n-1 && j == n-1) {
                    System.out.print("[🏁]"); // Goal
                } else if (grid[i][j] == 0) {
                    System.out.print("[ ]"); // Free
                } else {
                    System.out.print("[█]"); // Blocked
                }
            }
            System.out.println();
        }
    }
    
    private static void printPath(int[][] distances) {
        int n = distances.length;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (distances[i][j] > 0) {
                    System.out.printf("%2d ", distances[i][j]);
                } else {
                    System.out.print(" X ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Show king's movement patterns
     */
    private static void demonstrateKingMovement() {
        System.out.println("\n=== ♚ KING'S MOVEMENT PATTERNS ===");
        System.out.println("\nThe Chess King can move in 8 directions:");
        System.out.println("\n    ↖  ↑  ↗");
        System.out.println("     \\ | /");
        System.out.println("  ← - ♚ - →");
        System.out.println("     / | \\");
        System.out.println("    ↙  ↓  ↘");
        
        System.out.println("\nWhy BFS finds shortest path:");
        System.out.println("1. BFS explores level by level");
        System.out.println("2. First time we reach destination = shortest path");
        System.out.println("3. All moves have same cost (1 step)");
    }
    
    /**
     * Compare with other movement patterns
     */
    private static void compareMovementPatterns() {
        System.out.println("\n=== 🎲 MOVEMENT PATTERN COMPARISON ===");
        System.out.println("\nDifferent pieces, different patterns:");
        System.out.println("\n1. KING (8 directions): All adjacent squares");
        System.out.println("2. ROOK (4 directions): Up, Down, Left, Right only");
        System.out.println("3. BISHOP (4 directions): Diagonals only");
        System.out.println("4. QUEEN (8 directions): Like King but unlimited distance");
        
        System.out.println("\nFor shortest path in grid:");
        System.out.println("• King movement (8-dir) usually gives shortest path");
        System.out.println("• Rook movement (4-dir) for Manhattan distance");
        System.out.println("• Bishop movement for diagonal grids");
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("♚ SHORTEST PATH IN BINARY MATRIX - Chess King's Journey!\n");
        
        // Test 1: Basic path
        System.out.println("Test 1 - Basic path:");
        int[][] grid1 = {
            {0, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
        };
        visualizeKingsJourney(grid1);
        
        // Test 2: Blocked path
        System.out.println("\nTest 2 - Blocked path:");
        int[][] grid2 = {
            {0, 1, 0},
            {1, 1, 0},
            {0, 0, 0}
        };
        visualizeKingsJourney(grid2);
        
        // Test 3: Complex maze
        System.out.println("\nTest 3 - Complex maze:");
        int[][] grid3 = {
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0}
        };
        visualizeKingsJourney(grid3);
        
        // Test 4: Edge cases
        System.out.println("\nTest 4 - Edge cases:");
        System.out.println("1x1 grid: " + findKingsPath_ChessJourney(new int[][]{{0}}));
        System.out.println("Blocked start: " + findKingsPath_ChessJourney(new int[][]{{1,0},{0,0}}));
        System.out.println("Blocked end: " + findKingsPath_ChessJourney(new int[][]{{0,0},{0,1}}));
        
        // Test 5: Movement patterns
        demonstrateKingMovement();
        
        // Test 6: Comparison
        compareMovementPatterns();
        
        // Test 7: Performance
        System.out.println("\nTest 7 - Performance test:");
        int size = 100;
        int[][] largeGrid = new int[size][size];
        
        long start = System.nanoTime();
        int result = findKingsPath_ChessJourney(largeGrid);
        long time = System.nanoTime() - start;
        
        System.out.printf("%dx%d grid: path length %d in %.3f ms\n", 
                         size, size, result, time / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- CHESS KING: Moves in all 8 directions");
        System.out.println("- BFS guarantees shortest path");
        System.out.println("- Level by level exploration");
        System.out.println("- Mark visited to avoid cycles");
        System.out.println("- First arrival = shortest path!");
    }
}