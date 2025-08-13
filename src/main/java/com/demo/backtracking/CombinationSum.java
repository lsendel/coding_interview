package com.demo.backtracking;

import java.util.*;

/**
 * Problem: Combination Sum
 * 
 * MNEMONIC: "COIN CHANGE - Make exact change with unlimited coins!"
 * 🪙 Like a vending machine: use coins repeatedly to make exact amount
 * 
 * PROBLEM: Find all unique combinations that sum to target.
 * Elements can be used multiple times.
 * Example: candidates = [2,3,6,7], target = 7
 * Answer: [[2,2,3], [7]]
 * 
 * TECHNIQUE: Backtracking with Repetition Allowed
 * - Sort coins for efficient pruning
 * - Try using each coin multiple times
 * - Stop when sum exceeds target (pruning)
 * - Allow reusing same coin by not incrementing start index
 * 
 * VISUALIZATION: Making $7 with coins [2,3,6,7]
 * Start with $0:
 *   Use $2 → $2 (need $5 more)
 *     Use $2 → $4 (need $3 more)
 *       Use $2 → $6 (need $1 more) - No $1 coin! ✗
 *       Use $3 → $7 - Exact! ✓ [2,2,3]
 *   Use $3 → $3 (need $4 more)
 *     Use $3 → $6 (need $1 more) - No $1 coin! ✗
 *   Use $6 → $6 (need $1 more) - No $1 coin! ✗
 *   Use $7 → $7 - Exact! ✓ [7]
 * 
 * Time Complexity: O(n^(target/min)) - worst case
 * Space Complexity: O(target/min) - recursion depth
 */
public class CombinationSum {
    
    /**
     * Find all ways to make exact change
     * 
     * REMEMBER: "VENDING MACHINE" - Keep using coins until exact!
     * Like making change: use any coin as many times as needed
     * 
     * @param coins available coin denominations
     * @param targetAmount target sum to achieve
     * @return all combinations that sum to target
     */
    public static List<List<Integer>> combinationSum_MakeChange(int[] coins, int targetAmount) {
        List<List<Integer>> allWaysToMakeChange = new ArrayList<>();
        Arrays.sort(coins); // Sort for efficient pruning
        
        List<Integer> currentCoins = new ArrayList<>();
        makeChangeRecursively(coins, targetAmount, 0, currentCoins, allWaysToMakeChange);
        
        return allWaysToMakeChange;
    }
    
    /**
     * Recursive helper - try making change
     * 
     * @param coins available denominations
     * @param remainingAmount how much more we need
     * @param coinTypeIndex which coin types we've considered
     * @param currentCoins coins used so far
     * @param allWays collection of all valid combinations
     */
    private static void makeChangeRecursively(int[] coins, int remainingAmount,
                                            int coinTypeIndex, List<Integer> currentCoins,
                                            List<List<Integer>> allWays) {
        // Base case: exact change made!
        if (remainingAmount == 0) {
            allWays.add(new ArrayList<>(currentCoins));
            return;
        }
        
        // Try each coin type (starting from current to avoid duplicates)
        for (int i = coinTypeIndex; i < coins.length; i++) {
            // Pruning: skip if coin is too large
            if (coins[i] > remainingAmount) {
                break; // Since sorted, all remaining coins are also too large
            }
            
            // INSERT COIN: Use this coin
            currentCoins.add(coins[i]);
            
            // CONTINUE: Try to make remaining amount
            // Note: we pass 'i' not 'i+1' to allow reusing same coin
            makeChangeRecursively(coins, remainingAmount - coins[i], 
                                i, currentCoins, allWays);
            
            // RETURN COIN: Remove for next attempt (backtrack)
            currentCoins.remove(currentCoins.size() - 1);
        }
    }
    
    /**
     * Visualization helper - shows the coin selection process
     */
    private static void visualizeCoinSelection(int[] coins, int target) {
        System.out.println("=== 🪙 MAKING CHANGE VISUALIZATION ===");
        System.out.println("Coins available: " + Arrays.toString(coins));
        System.out.println("Target amount: $" + target);
        System.out.println("\nCoin selection process:");
        
        Arrays.sort(coins);
        List<String> steps = new ArrayList<>();
        visualizeHelper(coins, target, 0, new ArrayList<>(), steps, "", 0);
        
        for (String step : steps) {
            System.out.println(step);
        }
    }
    
    private static void visualizeHelper(int[] coins, int remaining, int start,
                                      List<Integer> current, List<String> steps,
                                      String indent, int totalSoFar) {
        if (remaining == 0) {
            steps.add(indent + "✓ Exact change! Used: " + current + " = $" + totalSoFar);
            return;
        }
        
        if (remaining < 0) {
            steps.add(indent + "✗ Exceeded target!");
            return;
        }
        
        boolean anyValidCoin = false;
        for (int i = start; i < coins.length; i++) {
            if (coins[i] <= remaining) {
                anyValidCoin = true;
                current.add(coins[i]);
                int newTotal = totalSoFar + coins[i];
                steps.add(indent + "Insert $" + coins[i] + " coin → Total: $" + 
                         newTotal + " (need $" + (remaining - coins[i]) + " more)");
                
                visualizeHelper(coins, remaining - coins[i], i, current, steps, 
                              indent + "  ", newTotal);
                
                current.remove(current.size() - 1);
                steps.add(indent + "Return $" + coins[i] + " coin");
            }
        }
        
        if (!anyValidCoin && remaining > 0) {
            steps.add(indent + "✗ No coins small enough for $" + remaining);
        }
    }
    
    /**
     * Count total ways without generating them
     */
    public static int countCombinations(int[] coins, int target) {
        Arrays.sort(coins);
        return countHelper(coins, target, 0);
    }
    
    private static int countHelper(int[] coins, int remaining, int start) {
        if (remaining == 0) return 1;
        if (remaining < 0) return 0;
        
        int count = 0;
        for (int i = start; i < coins.length; i++) {
            if (coins[i] > remaining) break;
            count += countHelper(coins, remaining - coins[i], i);
        }
        return count;
    }
    
    /**
     * Find minimum coins needed (different problem but related)
     */
    public static int minimumCoins(int[] coins, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, target + 1);
        dp[0] = 0;
        
        for (int amount = 1; amount <= target; amount++) {
            for (int coin : coins) {
                if (coin <= amount) {
                    dp[amount] = Math.min(dp[amount], dp[amount - coin] + 1);
                }
            }
        }
        
        return dp[target] > target ? -1 : dp[target];
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("🪙 COMBINATION SUM - Vending Machine Change!\n");
        
        // Test 1: Basic example
        System.out.println("Test 1 - Make $7 with coins [2,3,6,7]:");
        int[] coins1 = {2, 3, 6, 7};
        int target1 = 7;
        List<List<Integer>> ways1 = combinationSum_MakeChange(coins1, target1);
        System.out.println("Ways to make $" + target1 + ":");
        for (List<Integer> way : ways1) {
            System.out.print("  Use coins: " + way);
            int sum = way.stream().mapToInt(Integer::intValue).sum();
            System.out.println(" = $" + sum);
        }
        System.out.println("Total ways: " + ways1.size());
        System.out.println();
        
        // Test 2: Visualization
        System.out.println("Test 2 - Detailed process for small example:");
        visualizeCoinSelection(new int[]{2, 3}, 5);
        System.out.println();
        
        // Test 3: Multiple solutions
        System.out.println("Test 3 - Make $4 with coins [1,2,3]:");
        int[] coins3 = {1, 2, 3};
        int target3 = 4;
        List<List<Integer>> ways3 = combinationSum_MakeChange(coins3, target3);
        System.out.println("All ways to make $" + target3 + ":");
        for (List<Integer> way : ways3) {
            System.out.println("  " + way);
        }
        System.out.println("Total: " + ways3.size() + " ways");
        System.out.println();
        
        // Test 4: No solution case
        System.out.println("Test 4 - Impossible case:");
        int[] coins4 = {3, 5};
        int target4 = 1;
        List<List<Integer>> ways4 = combinationSum_MakeChange(coins4, target4);
        System.out.println("Make $" + target4 + " with coins " + Arrays.toString(coins4));
        System.out.println("Possible ways: " + ways4);
        System.out.println("Can't make $1 with only $3 and $5 coins!");
        System.out.println();
        
        // Test 5: Count vs generate
        System.out.println("Test 5 - Counting efficiency:");
        int[] coins5 = {1, 2, 5, 10};
        int target5 = 10;
        List<List<Integer>> generated = combinationSum_MakeChange(coins5, target5);
        int counted = countCombinations(coins5, target5);
        System.out.println("Generated " + generated.size() + " combinations");
        System.out.println("Counted " + counted + " combinations");
        System.out.println("Match: " + (generated.size() == counted));
        System.out.println();
        
        // Test 6: Related problem - minimum coins
        System.out.println("Test 6 - Minimum coins needed:");
        int[] coins6 = {1, 5, 10, 25};
        int[] amounts = {1, 6, 11, 30, 41};
        System.out.println("Coins: " + Arrays.toString(coins6));
        for (int amount : amounts) {
            int minCoins = minimumCoins(coins6, amount);
            System.out.printf("  $%d needs minimum %d coins\n", amount, minCoins);
        }
        System.out.println();
        
        // Test 7: Real world example
        System.out.println("Test 7 - Real vending machine:");
        System.out.println("Soda costs $1.25, you have quarters (25¢) and nickels (5¢)");
        int[] vendingCoins = {5, 25}; // in cents
        int sodaCost = 125; // in cents
        List<List<Integer>> vendingWays = combinationSum_MakeChange(vendingCoins, sodaCost);
        
        System.out.println("Ways to pay exactly $1.25:");
        for (List<Integer> way : vendingWays) {
            Map<Integer, Integer> coinCount = new HashMap<>();
            for (int coin : way) {
                coinCount.put(coin, coinCount.getOrDefault(coin, 0) + 1);
            }
            System.out.print("  ");
            for (Map.Entry<Integer, Integer> entry : coinCount.entrySet()) {
                System.out.printf("%d×%d¢ ", entry.getValue(), entry.getKey());
            }
            System.out.println();
        }
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- VENDING MACHINE: Use coins repeatedly for exact change");
        System.out.println("- Can reuse same coin → don't increment start index");
        System.out.println("- Sort coins for efficient pruning");
        System.out.println("- Stop early when coin > remaining amount");
        System.out.println("- Classic pattern: choose coin, use it, remove it");
    }
}