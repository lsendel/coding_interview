package com.demo.backtracking;

import java.util.*;

/**
 * Problem: Subsets (Power Set)
 * 
 * MNEMONIC: "SHOPPING CART - Include or Skip items!"
 * 🛒 Like shopping: for each item, decide to take it or leave it
 * 
 * PROBLEM: Given an array of unique integers, return all possible subsets.
 * Example: [1,2,3] → [[],[1],[2],[3],[1,2],[1,3],[2,3],[1,2,3]]
 * 
 * TECHNIQUE: Include/Exclude Decision Tree
 * - For each item, make a binary decision: take it or skip it
 * - This creates a binary tree of 2^n possibilities
 * - Each path from root to leaf is one subset
 * 
 * VISUALIZATION: Shopping for items [1,2,3]
 *                    Cart: []
 *           /                    \
 *     Skip 1                    Take 1
 *       []                       [1]
 *     /    \                   /    \
 *  Skip 2  Take 2         Skip 2   Take 2
 *   []      [2]            [1]      [1,2]
 *  /  \    /  \           /  \      /   \
 * S3  T3  S3  T3        S3   T3   S3    T3
 * [] [3] [2] [2,3]     [1] [1,3] [1,2] [1,2,3]
 * 
 * Time Complexity: O(2^n × n) - 2^n subsets, n to copy each
 * Space Complexity: O(n) for recursion depth
 */
public class Subsets {
    
    /**
     * Generate all subsets using shopping cart analogy
     * 
     * REMEMBER: "SHOPPING DECISIONS" - Take it or leave it!
     * For each item in store, decide: add to cart or skip
     * 
     * @param items array of unique items (integers)
     * @return all possible shopping carts (subsets)
     */
    public static List<List<Integer>> generateSubsets_ShoppingCart(int[] items) {
        List<List<Integer>> allCarts = new ArrayList<>();
        List<Integer> currentCart = new ArrayList<>();
        
        shopForItems(items, 0, currentCart, allCarts);
        
        return allCarts;
    }
    
    /**
     * Recursive helper - simulate shopping decisions
     * 
     * @param items all items in the store
     * @param shelfPosition current position in store
     * @param currentCart items in cart so far
     * @param allCarts collection of all possible carts
     */
    private static void shopForItems(int[] items, int shelfPosition,
                                   List<Integer> currentCart,
                                   List<List<Integer>> allCarts) {
        // Add current cart state (could be empty or partial)
        allCarts.add(new ArrayList<>(currentCart));
        
        // Browse remaining items on shelves
        for (int shelf = shelfPosition; shelf < items.length; shelf++) {
            // TAKE: Add item to cart
            currentCart.add(items[shelf]);
            
            // CONTINUE: Shop for more items
            shopForItems(items, shelf + 1, currentCart, allCarts);
            
            // RETURN: Put item back (backtrack)
            currentCart.remove(currentCart.size() - 1);
        }
    }
    
    /**
     * Alternative: Binary representation approach
     * 
     * MNEMONIC: "BINARY SWITCHES" - Each bit is on/off
     * Like light switches: 1 = include, 0 = exclude
     * 
     * @param items array of unique items
     * @return all subsets
     */
    public static List<List<Integer>> generateSubsets_BinaryMethod(int[] items) {
        List<List<Integer>> allSubsets = new ArrayList<>();
        int totalSubsets = 1 << items.length; // 2^n
        
        // Each number from 0 to 2^n-1 represents a subset
        for (int binaryPattern = 0; binaryPattern < totalSubsets; binaryPattern++) {
            List<Integer> subset = new ArrayList<>();
            
            // Check each bit position
            for (int itemIndex = 0; itemIndex < items.length; itemIndex++) {
                // If bit is set, include the item
                if ((binaryPattern & (1 << itemIndex)) != 0) {
                    subset.add(items[itemIndex]);
                }
            }
            
            allSubsets.add(subset);
        }
        
        return allSubsets;
    }
    
    /**
     * Alternative: Iterative building approach
     * 
     * MNEMONIC: "SNOWBALL EFFECT" - Start small, grow bigger
     * For each new item, duplicate all existing subsets and add item
     * 
     * @param items array of unique items
     * @return all subsets
     */
    public static List<List<Integer>> generateSubsets_IterativeMethod(int[] items) {
        List<List<Integer>> allSubsets = new ArrayList<>();
        allSubsets.add(new ArrayList<>()); // Start with empty subset
        
        // For each item, add it to all existing subsets
        for (int newItem : items) {
            int currentSize = allSubsets.size();
            
            // Duplicate each existing subset and add new item
            for (int i = 0; i < currentSize; i++) {
                List<Integer> newSubset = new ArrayList<>(allSubsets.get(i));
                newSubset.add(newItem);
                allSubsets.add(newSubset);
            }
        }
        
        return allSubsets;
    }
    
    /**
     * Visualization helper - shows the decision process
     */
    private static void visualizeShoppingProcess(int[] items) {
        System.out.println("=== 🛒 SHOPPING CART DECISIONS ===");
        System.out.println("Items in store: " + Arrays.toString(items));
        System.out.println("\nDecision process:");
        
        List<String> steps = new ArrayList<>();
        visualizeHelper(items, 0, new ArrayList<>(), steps, "");
        
        for (String step : steps) {
            System.out.println(step);
        }
    }
    
    private static void visualizeHelper(int[] items, int pos, List<Integer> cart,
                                      List<String> steps, String indent) {
        steps.add(indent + "Current cart: " + cart);
        
        if (pos >= items.length) {
            steps.add(indent + "✓ Checkout with: " + cart);
            return;
        }
        
        // Show decision point
        steps.add(indent + "Item " + items[pos] + " on shelf:");
        
        // Skip item
        steps.add(indent + "  → SKIP item " + items[pos]);
        visualizeHelper(items, pos + 1, new ArrayList<>(cart), steps, indent + "    ");
        
        // Take item
        List<Integer> withItem = new ArrayList<>(cart);
        withItem.add(items[pos]);
        steps.add(indent + "  → TAKE item " + items[pos] + " (cart: " + withItem + ")");
        visualizeHelper(items, pos + 1, withItem, steps, indent + "    ");
    }
    
    /**
     * Show binary representation
     */
    private static void showBinaryRepresentation(int[] items) {
        System.out.println("\n=== 🔢 BINARY REPRESENTATION ===");
        System.out.println("Items: " + Arrays.toString(items));
        System.out.println("\nBinary patterns (1=include, 0=exclude):");
        
        int n = items.length;
        for (int i = 0; i < (1 << n); i++) {
            System.out.printf("%3d = %s → ", i, 
                            String.format("%" + n + "s", Integer.toBinaryString(i))
                                  .replace(' ', '0'));
            
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subset.add(items[j]);
                }
            }
            System.out.println(subset);
        }
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("🛒 SUBSETS - Shopping Cart Combinations!\n");
        
        // Test 1: Basic example
        System.out.println("Test 1 - Shopping for [1,2,3]:");
        int[] items1 = {1, 2, 3};
        List<List<Integer>> carts1 = generateSubsets_ShoppingCart(items1);
        System.out.println("All possible carts:");
        for (int i = 0; i < carts1.size(); i++) {
            System.out.printf("  Cart %d: %s\n", i + 1, carts1.get(i));
        }
        System.out.println("Total: " + carts1.size() + " (2^3 = 8)");
        System.out.println();
        
        // Test 2: Visualization
        System.out.println("Test 2 - Shopping decisions for [1,2]:");
        visualizeShoppingProcess(new int[]{1, 2});
        System.out.println();
        
        // Test 3: Binary representation
        System.out.println("Test 3 - Binary patterns:");
        showBinaryRepresentation(new int[]{1, 2, 3});
        System.out.println();
        
        // Test 4: Compare all methods
        System.out.println("Test 4 - Compare three methods:");
        int[] test4 = {10, 20, 30, 40};
        
        List<List<Integer>> backtrack = generateSubsets_ShoppingCart(test4);
        List<List<Integer>> binary = generateSubsets_BinaryMethod(test4);
        List<List<Integer>> iterative = generateSubsets_IterativeMethod(test4);
        
        System.out.println("Backtracking count: " + backtrack.size());
        System.out.println("Binary count: " + binary.size());
        System.out.println("Iterative count: " + iterative.size());
        System.out.println("Expected (2^4): " + (1 << 4));
        
        // Sort for comparison
        Comparator<List<Integer>> comp = (a, b) -> {
            if (a.size() != b.size()) return a.size() - b.size();
            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).equals(b.get(i))) return a.get(i) - b.get(i);
            }
            return 0;
        };
        
        backtrack.sort(comp);
        binary.sort(comp);
        iterative.sort(comp);
        
        System.out.println("All methods produce same subsets: " + 
                         (backtrack.equals(binary) && binary.equals(iterative)));
        System.out.println();
        
        // Test 5: Edge cases
        System.out.println("Test 5 - Edge cases:");
        System.out.println("Empty array: " + generateSubsets_ShoppingCart(new int[]{}));
        System.out.println("Single item [42]: " + generateSubsets_ShoppingCart(new int[]{42}));
        System.out.println();
        
        // Test 6: Growth visualization
        System.out.println("Test 6 - Exponential growth:");
        for (int n = 0; n <= 10; n++) {
            System.out.printf("n=%d items → 2^%d = %d subsets\n", n, n, (1 << n));
        }
        System.out.println();
        
        // Test 7: Real world example
        System.out.println("Test 7 - Pizza toppings:");
        String[] toppings = {"Cheese", "Pepperoni", "Mushrooms"};
        System.out.println("Available toppings: " + Arrays.toString(toppings));
        
        int[] indices = {0, 1, 2};
        List<List<Integer>> combos = generateSubsets_ShoppingCart(indices);
        
        System.out.println("All pizza combinations:");
        for (List<Integer> combo : combos) {
            System.out.print("  Pizza with: ");
            if (combo.isEmpty()) {
                System.out.println("(plain)");
            } else {
                List<String> selected = new ArrayList<>();
                for (int idx : combo) {
                    selected.add(toppings[idx]);
                }
                System.out.println(selected);
            }
        }
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- SHOPPING CART: Take it or leave it for each item");
        System.out.println("- Creates binary tree of decisions");
        System.out.println("- 2^n total subsets (including empty set)");
        System.out.println("- Binary method: each subset = binary number");
        System.out.println("- Iterative: double subsets with each new item");
    }
}