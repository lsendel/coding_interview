package com.demo.array;

import java.util.*;

/**
 * Problem: Subarray Sum Equals K
 * 
 * PROBLEM: Find the number of continuous subarrays that sum to k.
 * Example: nums = [1, 1, 1], k = 2
 * Answer: 2 (subarrays [1,1] at indices [0,1] and [1,2])
 * 
 * TECHNIQUE: Prefix Sum + Hash Map
 * - Track cumulative sum at each position
 * - If cumSum - k exists in map, we found subarray(s)
 * - Map stores: prefix sum → count of occurrences
 * 
 * MATHEMATICAL INSIGHT:
 * If sum[0...j] - sum[0...i] = k, then sum[i+1...j] = k
 * Therefore: sum[0...j] - k = sum[0...i]
 * So we look for (currentSum - k) in our map
 * 
 * VISUALIZATION: nums = [1, 2, 3], k = 3
 * Index:     0  1  2
 * Values:    1  2  3
 * PrefixSum: 1  3  6
 * 
 * At index 0: sum=1, need sum-k=1-3=-2 (not found)
 * At index 1: sum=3, need sum-k=3-3=0 (found! count=1)
 * At index 2: sum=6, need sum-k=6-3=3 (found! count=1)
 * Total: 2 subarrays ([1,2] and [3])
 * 
 * WHY WE NEED MAP[0] = 1:
 * - Handles case when subarray starts from index 0
 * - When currentSum == k, we need previousSum = 0
 * 
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(n) - hash map can store n entries
 */
public class SubarraySum {
    
    /**
     * Main solution using prefix sum and hash map
     * 
     * Time Complexity: O(n)
     * - n is the length of the input array
     * - Single pass through all n elements: O(n)
     * - Each iteration performs:
     *   • Addition to currentSum: O(1)
     *   • HashMap containsKey() check: O(1) average case
     *   • HashMap get() operation: O(1) average case
     *   • HashMap put() operation: O(1) average case
     * - Total: n iterations × O(1) = O(n)
     * - Note: HashMap operations are O(1) average, O(n) worst case with poor hashing
     * 
     * Space Complexity: O(n)
     * - HashMap can store up to n different prefix sums in worst case
     * - Each prefix sum is unique when array has no duplicates
     * - HashMap overhead: O(n) for storing key-value pairs
     * - Other variables (count, currentSum): O(1)
     * - Total auxiliary space: O(n)
     * 
     * @param nums input array
     * @param k target sum
     * @return count of subarrays with sum k
     */
    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> sumCount = new HashMap<>();
        sumCount.put(0, 1); // Empty prefix has sum 0
        
        int count = 0;
        int currentSum = 0;
        
        for (int num : nums) {
            currentSum += num;
            
            // Check if we've seen (currentSum - k) before
            if (sumCount.containsKey(currentSum - k)) {
                count += sumCount.get(currentSum - k);
            }
            
            // Add current sum to map
            sumCount.put(currentSum, sumCount.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Brute force solution for comparison
     * Checks all possible subarrays
     * 
     * Time Complexity: O(n²)
     * - n is the length of input array  
     * - Outer loop runs n times (start positions): O(n)
     * - Inner loop runs up to n times for each start position: O(n)
     * - Each inner iteration: addition and comparison: O(1)
     * - Total: n × n × O(1) = O(n²)
     * - Specifically: ∑(i=0 to n-1) ∑(j=i to n-1) 1 = n(n+1)/2 = O(n²)
     * 
     * Space Complexity: O(1)
     * - Only uses constant extra variables: count, sum: O(1)
     * - No additional data structures or arrays
     * - No recursive calls or function call stack
     * - Total auxiliary space: O(1)
     * 
     * @param nums input array
     * @param k target sum
     * @return count of subarrays with sum k
     */
    public static int subarraySumBruteForce(int[] nums, int k) {
        int count = 0;
        
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Visualization helper - shows the algorithm step by step
     */
    private static void visualize(int[] nums, int k) {
        System.out.println("=== Step-by-Step Visualization ===");
        System.out.println("Array: " + Arrays.toString(nums) + ", k = " + k);
        System.out.println();
        
        Map<Integer, Integer> sumCount = new HashMap<>();
        sumCount.put(0, 1);
        
        int count = 0;
        int currentSum = 0;
        
        System.out.println("Initial: sumCount = {0: 1}");
        System.out.println();
        
        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];
            int needed = currentSum - k;
            
            System.out.printf("Index %d, value = %d:\n", i, nums[i]);
            System.out.printf("  Current sum = %d\n", currentSum);
            System.out.printf("  Need previous sum = %d - %d = %d\n", currentSum, k, needed);
            
            if (sumCount.containsKey(needed)) {
                int found = sumCount.get(needed);
                count += found;
                System.out.printf("  Found %d occurrence(s) of sum %d!\n", found, needed);
                System.out.printf("  Count increased to %d\n", count);
            } else {
                System.out.printf("  Sum %d not found in map\n", needed);
            }
            
            sumCount.put(currentSum, sumCount.getOrDefault(currentSum, 0) + 1);
            System.out.printf("  Updated sumCount: %s\n", sumCount);
            System.out.println();
        }
        
        System.out.println("Final count: " + count);
        System.out.println();
    }
    
    /**
     * Helper to find and print actual subarrays (for understanding)
     */
    private static void findActualSubarrays(int[] nums, int k) {
        System.out.println("=== Actual Subarrays with Sum " + k + " ===");
        
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) {
                    System.out.print("Found subarray: [");
                    for (int i = start; i <= end; i++) {
                        if (i > start) System.out.print(", ");
                        System.out.print(nums[i]);
                    }
                    System.out.printf("] at indices [%d, %d]\n", start, end);
                }
            }
        }
        System.out.println();
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        int[] test1 = {1, 1, 1};
        int k1 = 2;
        System.out.println("Test 1 - Basic example:");
        visualize(test1, k1);
        findActualSubarrays(test1, k1);
        
        // Test case 2: Array with target sum
        int[] test2 = {1, 2, 3};
        int k2 = 3;
        System.out.println("Test 2 - Multiple subarrays:");
        visualize(test2, k2);
        findActualSubarrays(test2, k2);
        
        // Test case 3: Negative numbers
        int[] test3 = {1, -1, 0};
        int k3 = 0;
        System.out.println("Test 3 - With negative numbers:");
        System.out.println("Array: " + Arrays.toString(test3) + ", k = " + k3);
        System.out.println("Result: " + subarraySum(test3, k3));
        findActualSubarrays(test3, k3);
        
        // Test case 4: No valid subarrays
        int[] test4 = {1, 2, 3};
        int k4 = 7;
        System.out.println("Test 4 - No valid subarrays:");
        System.out.println("Array: " + Arrays.toString(test4) + ", k = " + k4);
        System.out.println("Result: " + subarraySum(test4, k4));
        System.out.println();
        
        // Test case 5: Single element equals k
        int[] test5 = {3, 1, 4, 1, 5};
        int k5 = 5;
        System.out.println("Test 5 - Single elements and subarrays:");
        System.out.println("Array: " + Arrays.toString(test5) + ", k = " + k5);
        System.out.println("Result: " + subarraySum(test5, k5));
        findActualSubarrays(test5, k5);
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[] largeArray = new int[1000];
        Arrays.fill(largeArray, 1);
        int kLarge = 50;
        
        long start = System.nanoTime();
        int result1 = subarraySum(largeArray, kLarge);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = subarraySumBruteForce(largeArray, kLarge);
        long time2 = System.nanoTime() - start;
        
        System.out.printf("Optimal solution: %d subarrays in %d ns\n", result1, time1);
        System.out.printf("Brute force: %d subarrays in %d ns\n", result2, time2);
        System.out.printf("Speed improvement: %.2fx faster\n", (double)time2 / time1);
    }
}