package com.demo.twopointers;

import java.util.Arrays;

/**
 * Problem: Array Pair Sum (Two Sum II - Sorted Array)
 * 
 * PROBLEM: Given a sorted array of integers and a target sum, find if there's 
 * a pair of elements that adds up to the target. Return the indices (1-indexed).
 * 
 * Example: arr = [1, 2, 4, 6, 8, 9], target = 10
 * Answer: [2, 5] (elements 2 and 8 at indices 1 and 4 in 0-indexed)
 * 
 * TECHNIQUE: Two Pointers moving towards each other
 * - Start with pointers at opposite ends of the sorted array
 * - If sum is too small, move left pointer right (increase sum)
 * - If sum is too large, move right pointer left (decrease sum)
 * - If sum equals target, we found our pair
 * 
 * VISUALIZATION: arr = [1, 2, 4, 6, 8, 9], target = 10
 * Step 1: left=0(1), right=5(9), sum=10 ✓ Found!
 * 
 * Another example: arr = [1, 2, 3, 4], target = 6
 * Step 1: left=0(1), right=3(4), sum=5 < 6, move left
 * Step 2: left=1(2), right=3(4), sum=6 ✓ Found!
 * 
 * WHY IT WORKS: The array is sorted, so:
 * - Moving left pointer right always increases the sum
 * - Moving right pointer left always decreases the sum
 * - We can systematically explore all possibilities
 * 
 * Time Complexity: O(n) - each element visited at most once
 * Space Complexity: O(1) - only using two pointers
 */
public class TwoSum {
    
    /**
     * Find two numbers that add up to target in a sorted array
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Single pass through the array using two pointers
     *   - Each element is visited at most once
     *   - Left pointer moves from 0 to n-1 (at most n moves)
     *   - Right pointer moves from n-1 to 0 (at most n moves)
     *   - Total operations: O(n)
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using two integer variables (left, right)
     *   - Return array is O(1) as it always contains exactly 2 elements
     *   - No additional data structures or recursion stack
     * 
     * @param arr sorted array of integers
     * @param target the target sum
     * @return array with two indices (1-indexed) or empty array if no solution
     */
    public static int[] twoSum(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            int currentSum = arr[left] + arr[right];
            
            if (currentSum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed
            } else if (currentSum < target) {
                left++;  // Need larger sum
            } else {
                right--; // Need smaller sum
            }
        }
        
        return new int[0]; // No solution found
    }
    
    /**
     * Alternative: Return the actual values instead of indices
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Identical algorithm to twoSum(), same single-pass approach
     *   - Each element visited at most once by the two pointers
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using two pointer variables and temporary sum calculation
     *   - Return array is constant size (2 elements)
     */
    public static int[] twoSumValues(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            int currentSum = arr[left] + arr[right];
            
            if (currentSum == target) {
                return new int[]{arr[left], arr[right]};
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return new int[0];
    }
    
    /**
     * Find all pairs that sum to target (not just one)
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Single pass with two pointers from ends toward center
     *   - When pair found, we skip duplicates which takes O(k) where k is duplicate count
     *   - In worst case, all elements are the same: O(n) for skipping duplicates
     *   - Overall: O(n) as each element is processed at most once
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using pointer variables and counters
     *   - Output is printed directly, not stored in additional data structures
     */
    public static void findAllPairs(int[] arr, int target) {
        System.out.println("All pairs that sum to " + target + ":");
        int left = 0;
        int right = arr.length - 1;
        int count = 0;
        
        while (left < right) {
            int currentSum = arr[left] + arr[right];
            
            if (currentSum == target) {
                System.out.printf("Pair found: %d + %d = %d (indices %d, %d)\n",
                                arr[left], arr[right], target, left + 1, right + 1);
                count++;
                
                // Check for duplicates
                int leftVal = arr[left];
                int rightVal = arr[right];
                
                // Skip duplicates on left
                while (left < right && arr[left] == leftVal) {
                    left++;
                }
                
                // Skip duplicates on right
                while (left < right && arr[right] == rightVal) {
                    right--;
                }
            } else if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        System.out.println("Total pairs found: " + count);
    }
    
    /**
     * Visualization helper - shows the algorithm step by step
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Performs the same two-pointer algorithm as twoSum()
     *   - Additional O(1) print statements per iteration don't change complexity
     *   - String formatting operations are O(1) for fixed-size outputs
     * 
     * Space Complexity: O(1) constant extra space
     *   - Same space usage as twoSum() with additional local variables for formatting
     *   - No additional data structures created
     */
    private static void visualize(int[] arr, int target) {
        System.out.println("=== Two Sum Visualization ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + target);
        System.out.println();
        
        int left = 0;
        int right = arr.length - 1;
        int step = 0;
        
        while (left < right) {
            step++;
            int currentSum = arr[left] + arr[right];
            
            System.out.printf("Step %d: left=%d (value=%d), right=%d (value=%d)\n",
                            step, left, arr[left], right, arr[right]);
            System.out.printf("  Sum: %d + %d = %d", arr[left], arr[right], currentSum);
            
            if (currentSum == target) {
                System.out.println(" ✓ TARGET FOUND!");
                System.out.printf("  Result: indices [%d, %d]\n", left + 1, right + 1);
                return;
            } else if (currentSum < target) {
                System.out.println(" < " + target + " (too small, move left pointer right)");
                left++;
            } else {
                System.out.println(" > " + target + " (too large, move right pointer left)");
                right--;
            }
        }
        
        System.out.println("\nNo solution found!");
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        System.out.println("Test 1 - Basic example:");
        int[] arr1 = {1, 2, 4, 6, 8, 9};
        int target1 = 10;
        visualize(arr1, target1);
        System.out.println();
        
        // Test case 2: Multiple pairs
        System.out.println("Test 2 - Multiple pairs:");
        int[] arr2 = {1, 1, 2, 3, 4, 4, 5, 6};
        int target2 = 7;
        findAllPairs(arr2, target2);
        System.out.println();
        
        // Test case 3: No solution
        System.out.println("Test 3 - No solution:");
        int[] arr3 = {1, 2, 3, 4};
        int target3 = 10;
        visualize(arr3, target3);
        System.out.println();
        
        // Test case 4: Negative numbers
        System.out.println("Test 4 - With negative numbers:");
        int[] arr4 = {-4, -1, 0, 3, 5, 9};
        int target4 = 4;
        visualize(arr4, target4);
        System.out.println();
        
        // Test case 5: Edge cases
        System.out.println("Test 5 - Edge cases:");
        
        // Two elements
        int[] arr5a = {1, 3};
        System.out.println("Two elements [1, 3], target 4: " + 
                         Arrays.toString(twoSum(arr5a, 4)));
        
        // Same elements
        int[] arr5b = {3, 3};
        System.out.println("Same elements [3, 3], target 6: " + 
                         Arrays.toString(twoSum(arr5b, 6)));
        
        // Large numbers
        int[] arr5c = {100, 500, 1000, 2000, 3000};
        System.out.println("Large numbers, target 3500: " + 
                         Arrays.toString(twoSum(arr5c, 3500)));
        System.out.println();
        
        // Performance test
        System.out.println("Test 6 - Performance:");
        int[] largeArr = new int[100000];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = i * 2; // Even numbers
        }
        int largeTarget = 150000;
        
        long start = System.nanoTime();
        int[] result = twoSum(largeArr, largeTarget);
        long time = System.nanoTime() - start;
        
        System.out.printf("Array size: %d\n", largeArr.length);
        System.out.printf("Target: %d\n", largeTarget);
        System.out.printf("Result: %s\n", Arrays.toString(result));
        System.out.printf("Time: %.3f ms\n", time / 1000000.0);
        System.out.println("Operations: O(n) - very efficient!");
    }
}