package com.demo.array;

import java.util.Arrays;

/**
 * Problem: Move Zeroes
 * 
 * PROBLEM: Move all zeros to the end of array while maintaining relative order
 * of non-zero elements. Do this in-place.
 * Example: nums = [0, 1, 0, 3, 12]
 * Answer: [1, 3, 12, 0, 0]
 * 
 * TECHNIQUE: Two Pointers - Writer and Reader
 * - Writer pointer: where to place next non-zero element
 * - Reader pointer: scans through array
 * - When reader finds non-zero, write it at writer position
 * 
 * VISUALIZATION: nums = [0, 1, 0, 3, 12]
 * Initial: writer=0, reader=0
 * Step 1: nums[0]=0, skip
 * Step 2: nums[1]=1, write at writer=0, writer=1
 * Step 3: nums[2]=0, skip
 * Step 4: nums[3]=3, write at writer=1, writer=2
 * Step 5: nums[4]=12, write at writer=2, writer=3
 * Fill rest with zeros: [1, 3, 12, 0, 0]
 * 
 * KEY INSIGHT: We're partitioning array into [non-zeros | zeros]
 * 
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - in-place operation
 */
public class MoveZeroes {
    
    /**
     * Main solution using two pointers
     * 
     * Time Complexity: O(n)
     * - n is the length of the input array
     * - First loop (reader): iterates through all n elements once: O(n)
     * - Second loop (fill zeros): in worst case iterates through n elements: O(n)
     * - Each iteration performs constant time operations: array access, comparison, assignment
     * - Total: O(n) + O(n) = O(n)
     * 
     * Space Complexity: O(1)
     * - Only uses two integer variables: writer and reader: O(1)
     * - Modifies input array in-place, no additional data structures
     * - No recursive calls or function call stack
     * - Total auxiliary space: O(1)
     * 
     * @param nums array to modify in-place
     */
    public static void moveZeroes(int[] nums) {
        int writer = 0; // Position to write next non-zero
        
        // Move all non-zeros to the front
        for (int reader = 0; reader < nums.length; reader++) {
            if (nums[reader] != 0) {
                nums[writer] = nums[reader];
                writer++;
            }
        }
        
        // Fill the rest with zeros
        while (writer < nums.length) {
            nums[writer] = 0;
            writer++;
        }
    }
    
    /**
     * Alternative: Move Zeroes with Swapping
     * 
     * TECHNIQUE: Swap non-zero elements to front
     * - Maintains relative order
     * - Fewer writes if many non-zeros
     * 
     * Time Complexity: O(n)
     * - n is the length of input array
     * - Single pass through all n elements: O(n)
     * - Each iteration: constant time comparison and potential swap: O(1)
     * - Swapping involves 3 assignments, still O(1)
     * - Total: n iterations × O(1) = O(n)
     * 
     * Space Complexity: O(1)
     * - Uses only two integer variables: writer and reader: O(1)
     * - Uses one temporary variable for swapping: O(1)
     * - In-place modification, no additional arrays or data structures
     * - Total auxiliary space: O(1)
     * 
     * @param nums array to modify in-place
     */
    public static void moveZeroesSwap(int[] nums) {
        int writer = 0;
        
        for (int reader = 0; reader < nums.length; reader++) {
            if (nums[reader] != 0) {
                // Swap if positions are different
                if (reader != writer) {
                    int temp = nums[writer];
                    nums[writer] = nums[reader];
                    nums[reader] = temp;
                }
                writer++;
            }
        }
    }
    
    /**
     * Visualization helper - shows step by step process
     */
    private static void visualizeSteps(int[] nums) {
        System.out.println("=== Step-by-Step Visualization ===");
        int[] original = nums.clone();
        System.out.println("Original: " + Arrays.toString(original));
        
        int writer = 0;
        System.out.println("\nMoving non-zeros to front:");
        
        for (int reader = 0; reader < nums.length; reader++) {
            System.out.printf("Step %d: reader=%d, writer=%d, nums[reader]=%d\n", 
                            reader + 1, reader, writer, nums[reader]);
            
            if (nums[reader] != 0) {
                nums[writer] = nums[reader];
                System.out.printf("  -> Write %d at position %d\n", nums[reader], writer);
                System.out.println("  -> Array: " + Arrays.toString(nums));
                writer++;
            } else {
                System.out.println("  -> Skip zero");
            }
        }
        
        System.out.println("\nFilling rest with zeros:");
        while (writer < nums.length) {
            nums[writer] = 0;
            System.out.printf("Position %d filled with 0\n", writer);
            writer++;
        }
        
        System.out.println("\nFinal: " + Arrays.toString(nums));
        System.out.println();
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        int[] test1 = {0, 1, 0, 3, 12};
        System.out.println("Test 1 - Basic example:");
        visualizeSteps(test1.clone());
        
        // Test case 2: No zeros
        int[] test2 = {1, 2, 3, 4, 5};
        System.out.println("Test 2 - No zeros: " + Arrays.toString(test2));
        moveZeroes(test2);
        System.out.println("Result: " + Arrays.toString(test2));
        System.out.println();
        
        // Test case 3: All zeros
        int[] test3 = {0, 0, 0, 0};
        System.out.println("Test 3 - All zeros: " + Arrays.toString(test3));
        moveZeroes(test3);
        System.out.println("Result: " + Arrays.toString(test3));
        System.out.println();
        
        // Test case 4: Zeros at start
        int[] test4 = {0, 0, 1, 2, 3};
        System.out.println("Test 4 - Zeros at start: " + Arrays.toString(test4));
        moveZeroes(test4);
        System.out.println("Result: " + Arrays.toString(test4));
        System.out.println();
        
        // Test case 5: Zeros at end (already correct)
        int[] test5 = {1, 2, 3, 0, 0};
        System.out.println("Test 5 - Zeros at end: " + Arrays.toString(test5));
        moveZeroes(test5);
        System.out.println("Result: " + Arrays.toString(test5));
        System.out.println();
        
        // Test case 6: Compare swap method
        int[] test6a = {0, 1, 0, 3, 12};
        int[] test6b = {0, 1, 0, 3, 12};
        System.out.println("Test 6 - Compare methods:");
        System.out.println("Input: " + Arrays.toString(test6a));
        moveZeroes(test6a);
        System.out.println("Write method: " + Arrays.toString(test6a));
        moveZeroesSwap(test6b);
        System.out.println("Swap method: " + Arrays.toString(test6b));
    }
}