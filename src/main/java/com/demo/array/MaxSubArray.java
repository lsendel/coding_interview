package com.demo.array;

import java.util.Arrays;

/**
 * Problem: Maximum Subarray (Kadane's Algorithm)
 * 
 * PROBLEM: Find the contiguous subarray with the largest sum.
 * Example: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * Answer: 6 (subarray [4, -1, 2, 1])
 * 
 * TECHNIQUE: Dynamic Programming (Kadane's Algorithm)
 * - Track maximum sum ending at current position
 * - Either extend previous subarray or start new one
 * 
 * VISUALIZATION:
 * Array:    [-2,  1, -3,  4, -1,  2,  1, -5,  4]
 * MaxHere:  -2,  1, -2,  4,  3,  5,  6,  1,  5
 * MaxSoFar: -2,  1,  1,  4,  4,  5,  6,  6,  6
 * 
 * DECISION AT EACH STEP:
 * - Extend previous: currentSum + num
 * - Start new: num
 * - Choose maximum of the two
 * 
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - only variables
 */
public class MaxSubArray {
    
    /**
     * Main solution using Kadane's Algorithm
     * 
     * Time Complexity: O(n)
     * - n is the length of the input array
     * - Single pass through all n-1 elements (starting from index 1): O(n)
     * - Each iteration performs:
     *   • Math.max() comparison: O(1)
     *   • Addition: O(1)
     *   • Variable assignment: O(1)
     * - Total: (n-1) iterations × O(1) = O(n)
     * 
     * Space Complexity: O(1)
     * - Only uses two integer variables: maxSoFar, maxEndingHere: O(1)
     * - No additional data structures, arrays, or recursive calls
     * - Algorithm runs in constant space regardless of input size
     * - Total auxiliary space: O(1)
     * 
     * @param nums input array
     * @return maximum subarray sum
     */
    public static int maxSubArray(int[] nums) {
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            // Either extend the existing subarray or start a new one
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }
    
    /**
     * Solution that also tracks the actual subarray indices
     * 
     * Time Complexity: O(n)
     * - n is the length of input array
     * - Single pass through n-1 elements (starting from index 1): O(n)
     * - Each iteration performs constant time operations:
     *   • Comparison to decide extend vs restart: O(1)
     *   • Addition/assignment operations: O(1)
     *   • Index tracking updates: O(1)
     * - Total: (n-1) iterations × O(1) = O(n)
     * 
     * Space Complexity: O(1)
     * - Uses constant number of integer variables:
     *   • maxSoFar, maxEndingHere: O(1)
     *   • start, end, tempStart indices: O(1)
     * - Result array of size 3 is output requirement: O(1)
     * - No additional data structures or recursive calls
     * - Total auxiliary space: O(1)
     * 
     * @param nums input array
     * @return array containing [maxSum, startIndex, endIndex]
     */
    public static int[] maxSubArrayWithIndices(int[] nums) {
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        int start = 0, end = 0, tempStart = 0;
        
        for (int i = 1; i < nums.length; i++) {
            // If starting new subarray is better
            if (nums[i] > maxEndingHere + nums[i]) {
                maxEndingHere = nums[i];
                tempStart = i;
            } else {
                maxEndingHere = maxEndingHere + nums[i];
            }
            
            // Update maximum if we found a better sum
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }
        
        return new int[]{maxSoFar, start, end};
    }
    
    /**
     * Brute force solution for comparison
     * 
     * Time Complexity: O(n²)
     * - n is the length of input array
     * - Outer loop: n iterations for starting positions: O(n)
     * - Inner loop: up to n iterations for each starting position: O(n)
     * - Each inner iteration: addition, comparison, max update: O(1)
     * - Total: n × n × O(1) = O(n²)
     * - Specifically: ∑(i=0 to n-1) ∑(j=i to n-1) 1 = n(n+1)/2 = O(n²)
     * 
     * Space Complexity: O(1)
     * - Only uses constant extra variables: maxSum, currentSum: O(1)
     * - No additional arrays or data structures
     * - No recursive calls or function call stack
     * - Total auxiliary space: O(1)
     * 
     * @param nums input array
     * @return maximum subarray sum
     */
    public static int maxSubArrayBruteForce(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        
        return maxSum;
    }
    
    /**
     * Visualization helper - shows algorithm step by step
     */
    private static void visualize(int[] nums) {
        System.out.println("=== Kadane's Algorithm Visualization ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println();
        
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        
        System.out.println("Initial:");
        System.out.printf("  maxEndingHere = %d, maxSoFar = %d\n", maxEndingHere, maxSoFar);
        System.out.println();
        
        for (int i = 1; i < nums.length; i++) {
            System.out.printf("Index %d, value = %d:\n", i, nums[i]);
            System.out.printf("  Choice: extend (%d + %d = %d) vs start new (%d)\n",
                            maxEndingHere, nums[i], maxEndingHere + nums[i], nums[i]);
            
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            System.out.printf("  maxEndingHere = %d", maxEndingHere);
            
            if (maxEndingHere == nums[i]) {
                System.out.println(" (started new subarray)");
            } else {
                System.out.println(" (extended subarray)");
            }
            
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
            System.out.printf("  maxSoFar = %d\n", maxSoFar);
            System.out.println();
        }
        
        System.out.println("Final maximum sum: " + maxSoFar);
    }
    
    /**
     * Helper to display the actual maximum subarray
     */
    private static void showMaxSubarray(int[] nums) {
        int[] result = maxSubArrayWithIndices(nums);
        int maxSum = result[0];
        int start = result[1];
        int end = result[2];
        
        System.out.print("Maximum subarray: [");
        for (int i = start; i <= end; i++) {
            if (i > start) System.out.print(", ");
            System.out.print(nums[i]);
        }
        System.out.printf("] (indices %d to %d)\n", start, end);
        System.out.println("Sum: " + maxSum);
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Classic example
        int[] test1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Test 1 - Classic example:");
        visualize(test1);
        showMaxSubarray(test1);
        System.out.println();
        
        // Test case 2: All negative numbers
        int[] test2 = {-5, -2, -8, -1, -4};
        System.out.println("Test 2 - All negative numbers:");
        System.out.println("Array: " + Arrays.toString(test2));
        System.out.println("Maximum sum: " + maxSubArray(test2));
        showMaxSubarray(test2);
        System.out.println();
        
        // Test case 3: All positive numbers
        int[] test3 = {1, 2, 3, 4, 5};
        System.out.println("Test 3 - All positive numbers:");
        System.out.println("Array: " + Arrays.toString(test3));
        System.out.println("Maximum sum: " + maxSubArray(test3));
        showMaxSubarray(test3);
        System.out.println();
        
        // Test case 4: Single element
        int[] test4 = {-3};
        System.out.println("Test 4 - Single element:");
        System.out.println("Array: " + Arrays.toString(test4));
        System.out.println("Maximum sum: " + maxSubArray(test4));
        System.out.println();
        
        // Test case 5: Mixed with zeros
        int[] test5 = {-2, 0, -1, 2, -1, 1, 0, -5, 4};
        System.out.println("Test 5 - With zeros:");
        System.out.println("Array: " + Arrays.toString(test5));
        System.out.println("Maximum sum: " + maxSubArray(test5));
        showMaxSubarray(test5);
        System.out.println();
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int)(Math.random() * 200) - 100;
        }
        
        long start = System.nanoTime();
        int kadaneResult = maxSubArray(largeArray);
        long kadaneTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        int bruteResult = maxSubArrayBruteForce(largeArray);
        long bruteTime = System.nanoTime() - start;
        
        System.out.printf("Kadane's algorithm: %d in %d ns\n", kadaneResult, kadaneTime);
        System.out.printf("Brute force: %d in %d ns\n", bruteResult, bruteTime);
        System.out.printf("Speed improvement: %.2fx faster\n", (double)bruteTime / kadaneTime);
    }
}