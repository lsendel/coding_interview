package com.demo.slidingwindow;

import java.util.Arrays;

/**
 * Sliding Window Problem: Maximum Sum Subarray of Size K
 * 
 * Given an array of integers and a positive integer k, find the maximum sum 
 * of any contiguous subarray of size k.
 * 
 * PROBLEM: Given an array [1, 4, 2, 10, 23, 3, 1, 0, 20] and k=4, find the maximum
 * sum of any contiguous subarray of exactly k elements. Answer: 39 (sum of [4, 2, 10, 23])
 * 
 * TECHNIQUE: Fixed-Size Sliding Window
 * - The window maintains exactly k elements at all times
 * - Start by calculating the sum of the first k elements
 * - Then slide the window one position at a time:
 *   - Remove the leftmost element from the sum
 *   - Add the new rightmost element to the sum
 *   - Track the maximum sum seen so far
 * 
 * VISUALIZATION: Array = [1, 4, 2, 10, 23, 3, 1, 0, 20], k = 4
 * Window 1: [1, 4, 2, 10] = 17
 * Window 2: [4, 2, 10, 23] = 39 (maximum)
 * Window 3: [2, 10, 23, 3] = 38
 * etc...
 * 
 * Time Complexity: O(n) - we visit each element once
 * Space Complexity: O(1) - only using a few variables
 */
public class MaxSumSubarray {
    
    /**
     * Finds the maximum sum of a subarray with size k using sliding window technique.
     * 
     * Time Complexity: O(n)
     * - First loop (lines 49-51): O(k) to calculate initial window sum
     * - Second loop (lines 55-59): O(n-k) iterations, each doing O(1) work
     * - Total: O(k) + O(n-k) = O(n) since we visit each element exactly once
     * - Contrast with brute force O(n*k): for each position, calculate sum of k elements
     * 
     * Space Complexity: O(1)
     * - Only uses a constant number of variables: maxSum, windowSum, i
     * - No additional data structures that grow with input size
     * - Memory usage is independent of array size or window size
     * 
     * @param arr the input array
     * @param k the size of the subarray
     * @return the maximum sum of any subarray of size k
     */
    public static int maxSumSubarray(int[] arr, int k) {
        if (arr == null || arr.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        int maxSum = 0;
        int windowSum = 0;
        
        // Calculate sum of first window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        maxSum = windowSum;
        
        // Slide the window and update max sum
        for (int i = k; i < arr.length; i++) {
            // Add the new element and remove the leftmost element
            windowSum = windowSum + arr[i] - arr[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    /**
     * Visualization helper - shows the sliding window process step by step
     * 
     * Time Complexity: O(n * k)
     * - Outer loop: O(n-k) iterations for sliding the window
     * - Inner loop for printing: O(k) iterations per window
     * - Total: O((n-k) * k) = O(n*k) due to visualization overhead
     * - Note: This is slower than the main algorithm due to string operations
     * 
     * Space Complexity: O(1)
     * - Uses constant extra space for tracking variables
     * - String printing uses temporary space but doesn't accumulate
     */
    private static void visualize(int[] arr, int k) {
        System.out.println("=== Sliding Window Visualization ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Window size (k): " + k);
        System.out.println();
        
        int maxSum = 0;
        int maxStartIndex = 0;
        int windowSum = 0;
        
        // First window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        maxSum = windowSum;
        
        System.out.printf("Window 1: [");
        for (int i = 0; i < k; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(arr[i]);
        }
        System.out.printf("] = %d\n", windowSum);
        
        // Slide window
        for (int i = k; i < arr.length; i++) {
            windowSum = windowSum + arr[i] - arr[i - k];
            
            System.out.printf("Window %d: [", i - k + 2);
            for (int j = i - k + 1; j <= i; j++) {
                if (j > i - k + 1) System.out.print(", ");
                System.out.print(arr[j]);
            }
            System.out.printf("] = %d", windowSum);
            
            if (windowSum > maxSum) {
                maxSum = windowSum;
                maxStartIndex = i - k + 1;
                System.out.print(" (new maximum!)");
            }
            System.out.println();
        }
        
        System.out.println();
        System.out.printf("Maximum sum: %d\n", maxSum);
        System.out.print("Maximum subarray: [");
        for (int i = maxStartIndex; i < maxStartIndex + k; i++) {
            if (i > maxStartIndex) System.out.print(", ");
            System.out.print(arr[i]);
        }
        System.out.println("]");
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        int[] test1 = {1, 4, 2, 10, 23, 3, 1, 0, 20};
        int k1 = 4;
        System.out.println("Test 1 - Basic example:");
        visualize(test1, k1);
        System.out.println("Result: " + maxSumSubarray(test1, k1));
        System.out.println();
        
        // Test case 2: Window size = 1
        int[] test2 = {5, 2, 8, 1, 9, 3};
        int k2 = 1;
        System.out.println("Test 2 - Window size 1 (find max element):");
        System.out.println("Array: " + Arrays.toString(test2));
        System.out.println("Result: " + maxSumSubarray(test2, k2));
        System.out.println();
        
        // Test case 3: Window size = array length
        int[] test3 = {1, 2, 3, 4, 5};
        int k3 = 5;
        System.out.println("Test 3 - Window size equals array length:");
        System.out.println("Array: " + Arrays.toString(test3));
        System.out.println("Result: " + maxSumSubarray(test3, k3));
        System.out.println();
        
        // Test case 4: Negative numbers
        int[] test4 = {-1, 2, 3, -4, 5, -6, 7};
        int k4 = 3;
        System.out.println("Test 4 - Array with negative numbers:");
        visualize(test4, k4);
        System.out.println("Result: " + maxSumSubarray(test4, k4));
        System.out.println();
        
        // Test case 5: All negative numbers
        int[] test5 = {-5, -2, -8, -1, -4};
        int k5 = 2;
        System.out.println("Test 5 - All negative numbers:");
        System.out.println("Array: " + Arrays.toString(test5));
        System.out.println("Result: " + maxSumSubarray(test5, k5) + " (least negative sum)");
        System.out.println();
        
        // Performance test
        System.out.println("=== Performance Test ===");
        int[] largeArray = new int[100000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int)(Math.random() * 100);
        }
        int kLarge = 1000;
        
        long start = System.nanoTime();
        int result = maxSumSubarray(largeArray, kLarge);
        long time = System.nanoTime() - start;
        
        System.out.printf("Array size: %d, Window size: %d\n", largeArray.length, kLarge);
        System.out.printf("Maximum sum: %d\n", result);
        System.out.printf("Time taken: %d ns (%.3f ms)\n", time, time / 1000000.0);
        System.out.println("Number of operations: " + largeArray.length + " (linear time)");
    }
}