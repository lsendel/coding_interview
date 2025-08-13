package com.demo.dynamicprogramming;

import java.util.*;

/**
 * Problem: Climbing Stairs
 * 
 * MNEMONIC: "STAIRWAY TO SUCCESS - One or two steps at a time!"
 * 🪜 Like climbing stairs where you can take 1 or 2 steps each time
 * 
 * PROBLEM: You can climb 1 or 2 steps at a time.
 * How many distinct ways to reach the top of n stairs?
 * 
 * TECHNIQUE: Dynamic Programming - "Fibonacci in Disguise"
 * - To reach step n, you came from step n-1 or n-2
 * - ways(n) = ways(n-1) + ways(n-2)
 * - It's the Fibonacci sequence!
 * 
 * VISUALIZATION for n=4:
 * Ways to reach:
 * Step 0: 1 way (start)
 * Step 1: 1 way (0→1)
 * Step 2: 2 ways (0→1→2 or 0→2)
 * Step 3: 3 ways (add ways from step 1 and 2)
 * Step 4: 5 ways (add ways from step 2 and 3)
 * 
 * All paths to top:
 * 1. [1,1,1,1] - Baby steps all the way
 * 2. [1,1,2]   - Two singles, then double
 * 3. [1,2,1]   - Single, double, single
 * 4. [2,1,1]   - Double, then singles
 * 5. [2,2]     - Giant steps only
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1) optimized
 */
public class ClimbingStairs {
    
    /**
     * Count ways to climb stairs - optimized space
     * 
     * REMEMBER: "FIBONACCI STAIRS"
     * Each step = sum of ways to reach previous two steps
     * 
     * @param totalSteps number of stairs to climb
     * @return number of distinct ways
     */
    public static int climbStairs_StairwayToSuccess(int totalSteps) {
        if (totalSteps <= 2) {
            return totalSteps; // 1 stair = 1 way, 2 stairs = 2 ways
        }
        
        int oneStepBack = 2;   // Ways to reach step n-1
        int twoStepsBack = 1;  // Ways to reach step n-2
        
        // Build up from step 3 to n
        for (int currentStep = 3; currentStep <= totalSteps; currentStep++) {
            int waysToCurrentStep = oneStepBack + twoStepsBack;
            
            // Slide the window forward
            twoStepsBack = oneStepBack;
            oneStepBack = waysToCurrentStep;
        }
        
        return oneStepBack;
    }
    
    /**
     * Get all actual paths (not just count)
     */
    public static List<List<Integer>> getAllPaths(int n) {
        List<List<Integer>> allPaths = new ArrayList<>();
        backtrackPaths(n, 0, new ArrayList<>(), allPaths);
        return allPaths;
    }
    
    private static void backtrackPaths(int target, int current, 
                                      List<Integer> path, List<List<Integer>> allPaths) {
        if (current == target) {
            allPaths.add(new ArrayList<>(path));
            return;
        }
        
        if (current > target) {
            return;
        }
        
        // Try 1 step
        path.add(1);
        backtrackPaths(target, current + 1, path, allPaths);
        path.remove(path.size() - 1);
        
        // Try 2 steps
        path.add(2);
        backtrackPaths(target, current + 2, path, allPaths);
        path.remove(path.size() - 1);
    }
    
    /**
     * DP with full array (for visualization)
     */
    public static int[] buildDPTable(int n) {
        if (n <= 0) return new int[]{1};
        
        int[] dp = new int[n + 1];
        dp[0] = 1; // Base: 1 way to stay at ground
        dp[1] = 1; // 1 way to reach first step
        
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp;
    }
    
    /**
     * Visualization helper - shows the climbing process
     */
    private static void visualizeClimbing(int n) {
        System.out.println("=== 🪜 STAIRWAY VISUALIZATION ===");
        System.out.printf("Climbing %d stairs (1 or 2 steps at a time)\n\n", n);
        
        // Draw the staircase
        drawStaircase(n);
        
        // Show DP building
        System.out.println("\nBuilding solution step by step:");
        int[] dp = buildDPTable(n);
        
        for (int i = 0; i <= n; i++) {
            System.out.printf("Step %d: %d way(s)", i, dp[i]);
            
            if (i == 0) {
                System.out.println(" (starting position)");
            } else if (i == 1) {
                System.out.println(" (only one step from start)");
            } else {
                System.out.printf(" (from step %d: %d ways + from step %d: %d ways)\n",
                                i - 1, dp[i - 1], i - 2, dp[i - 2]);
            }
        }
        
        // Show all paths for small n
        if (n <= 5) {
            System.out.println("\nAll possible paths:");
            List<List<Integer>> paths = getAllPaths(n);
            for (int i = 0; i < paths.size(); i++) {
                System.out.printf("%d. %s", i + 1, paths.get(i));
                
                // Show the journey
                System.out.print(" → ");
                int position = 0;
                for (int step : paths.get(i)) {
                    position += step;
                    System.out.print(position + " ");
                }
                System.out.println();
            }
        }
    }
    
    /**
     * Draw ASCII staircase
     */
    private static void drawStaircase(int n) {
        System.out.println("The Staircase:");
        
        for (int i = n; i >= 0; i--) {
            // Draw spaces
            for (int j = 0; j < n - i; j++) {
                System.out.print("  ");
            }
            
            // Draw step
            if (i == n) {
                System.out.println("🎯 Top!");
            } else if (i == 0) {
                System.out.println("👟 Start");
            } else {
                System.out.printf("│_%d_│\n", i);
            }
        }
    }
    
    /**
     * Show relationship to Fibonacci
     */
    private static void showFibonacciConnection(int n) {
        System.out.println("\n=== 🔢 FIBONACCI CONNECTION ===");
        System.out.println("Climbing stairs is Fibonacci in disguise!");
        System.out.println("\nFibonacci: 0, 1, 1, 2, 3, 5, 8, 13, 21...");
        System.out.println("ClimbStairs: 1, 1, 2, 3, 5, 8, 13, 21...");
        System.out.println("(Just shifted by one position!)\n");
        
        System.out.println("Step | Ways | Fibonacci");
        System.out.println("-----|------|----------");
        
        for (int i = 0; i <= Math.min(n, 10); i++) {
            int ways = climbStairs_StairwayToSuccess(i);
            int fib = fibonacci(i + 1);
            System.out.printf(" %2d  |  %3d | %3d\n", i, ways, fib);
        }
    }
    
    private static int fibonacci(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
    
    /**
     * Extension: What if we can take 1, 2, or 3 steps?
     */
    public static int climbStairsTriple(int n) {
        if (n == 0) return 1;
        if (n == 1) return 1;
        if (n == 2) return 2;
        
        int threeBack = 1;  // ways(0)
        int twoBack = 1;    // ways(1)
        int oneBack = 2;    // ways(2)
        
        for (int i = 3; i <= n; i++) {
            int current = oneBack + twoBack + threeBack;
            threeBack = twoBack;
            twoBack = oneBack;
            oneBack = current;
        }
        
        return oneBack;
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("🪜 CLIMBING STAIRS - Stairway to Success!\n");
        
        // Test 1: Basic examples
        System.out.println("Test 1 - Small staircases:");
        for (int n = 1; n <= 5; n++) {
            int ways = climbStairs_StairwayToSuccess(n);
            System.out.printf("%d stair(s): %d way(s)\n", n, ways);
        }
        System.out.println();
        
        // Test 2: Visualization
        System.out.println("Test 2 - Detailed visualization:");
        visualizeClimbing(4);
        System.out.println();
        
        // Test 3: Fibonacci connection
        System.out.println("Test 3 - The Fibonacci secret:");
        showFibonacciConnection(10);
        System.out.println();
        
        // Test 4: Larger values
        System.out.println("Test 4 - Larger staircases:");
        int[] testCases = {10, 20, 35, 44};
        
        for (int n : testCases) {
            long start = System.nanoTime();
            int ways = climbStairs_StairwayToSuccess(n);
            long time = System.nanoTime() - start;
            
            System.out.printf("n=%d: %,d ways (computed in %.3f ms)\n", 
                            n, ways, time / 1000000.0);
        }
        System.out.println();
        
        // Test 5: Extension - triple steps
        System.out.println("Test 5 - Extension: 1, 2, or 3 steps allowed:");
        System.out.println("n | 1,2 steps | 1,2,3 steps");
        System.out.println("--|-----------|------------");
        
        for (int n = 1; n <= 10; n++) {
            int ways2 = climbStairs_StairwayToSuccess(n);
            int ways3 = climbStairsTriple(n);
            System.out.printf("%2d|    %3d    |    %3d\n", n, ways2, ways3);
        }
        System.out.println();
        
        // Test 6: Real-world analogy
        System.out.println("Test 6 - Real-world applications:");
        System.out.println("\nThis problem appears everywhere:");
        System.out.println("• Decoding messages (1 or 2 digits at a time)");
        System.out.println("• Tiling floors (1×2 or 2×1 tiles)");
        System.out.println("• Robot paths (right or down moves)");
        System.out.println("• Making change (using certain denominations)");
        
        System.out.println("\nExample: Ways to tile a 2×n floor with 1×2 tiles:");
        for (int n = 1; n <= 8; n++) {
            System.out.printf("2×%d floor: %d ways\n", n, climbStairs_StairwayToSuccess(n));
        }
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- STAIRWAY: Can take 1 or 2 steps at a time");
        System.out.println("- To reach step n: came from n-1 or n-2");
        System.out.println("- Formula: ways(n) = ways(n-1) + ways(n-2)");
        System.out.println("- It's FIBONACCI in disguise!");
        System.out.println("- Only need to track last 2 values: O(1) space");
    }
}