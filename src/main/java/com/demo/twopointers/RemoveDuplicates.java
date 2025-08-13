package com.demo.twopointers;

import java.util.Arrays;

/**
 * Problem: Remove Duplicates from Sorted Array
 * 
 * PROBLEM: Remove duplicates in-place from a sorted array, return the new length.
 * Each unique element should appear only once, maintaining relative order.
 * Example: nums = [0,0,1,1,1,2,2,3,3,4]
 * Answer: 5, nums = [0,1,2,3,4,_,_,_,_,_]
 * 
 * TECHNIQUE: Two Pointers - Reader and Writer
 * - Reader pointer scans through all elements
 * - Writer pointer marks where to place next unique element
 * - When reader finds new unique element, write it at writer position
 * 
 * VISUALIZATION: nums = [1,1,2,2,3]
 * Initial: writer=0, reader=0
 * Step 1: nums[0]=1, first element, writer=1
 * Step 2: reader=1, nums[1]=1 (duplicate), skip
 * Step 3: reader=2, nums[2]=2 (new), write at writer=1, writer=2
 * Step 4: reader=3, nums[3]=2 (duplicate), skip
 * Step 5: reader=4, nums[4]=3 (new), write at writer=2, writer=3
 * Result: [1,2,3,2,3], length=3
 * 
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - in-place modification
 */
public class RemoveDuplicates {
    
    /**
     * Remove duplicates from sorted array
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Single pass through the array using reader pointer
     *   - Each element is examined exactly once
     *   - Comparison operation nums[reader] != nums[reader-1] is O(1)
     *   - Assignment operation is O(1)
     *   - Total: n iterations * O(1) operations = O(n)
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using one additional variable (writer pointer)
     *   - Array is modified in-place, no additional data structures
     *   - No recursion, so no stack space used
     * 
     * @param nums sorted array with duplicates
     * @return length of array after removing duplicates
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        
        int writer = 1; // Position to write next unique element
        
        for (int reader = 1; reader < nums.length; reader++) {
            if (nums[reader] != nums[reader - 1]) {
                nums[writer] = nums[reader];
                writer++;
            }
        }
        
        return writer;
    }
    
    /**
     * Remove duplicates allowing at most k occurrences of each element
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Single pass through array starting from index k
     *   - Each element compared with element k positions behind: O(1) operation
     *   - Total iterations: n-k, each with O(1) operations = O(n)
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using writer pointer variable
     *   - In-place modification without additional data structures
     *   - No recursion stack space
     * 
     * @param nums sorted array
     * @param k maximum allowed occurrences
     * @return new length
     */
    public static int removeDuplicatesAtMostK(int[] nums, int k) {
        if (nums.length <= k) return nums.length;
        
        int writer = k;
        
        for (int reader = k; reader < nums.length; reader++) {
            if (nums[reader] != nums[writer - k]) {
                nums[writer] = nums[reader];
                writer++;
            }
        }
        
        return writer;
    }
    
    /**
     * Count unique elements without modifying the array
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Single pass through array from index 1 to n-1
     *   - Each iteration performs one comparison: nums[i] != nums[i-1]
     *   - Comparison is O(1), total is (n-1) * O(1) = O(n)
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using one counter variable
     *   - No additional data structures or array modifications
     *   - No recursion stack
     */
    public static int countUnique(int[] nums) {
        if (nums.length == 0) return 0;
        
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Visualization helper - shows the algorithm step by step
     * 
     * Time Complexity: O(n) where n is the length of the array
     *   - Same algorithm as removeDuplicates() with additional O(1) print operations
     *   - Each step includes string formatting and printing: O(1) per iteration
     *   - Array cloning for restoration: O(n)
     *   - Total: O(n) + O(n) = O(n)
     * 
     * Space Complexity: O(n) for array cloning
     *   - Creates a copy of the original array for restoration
     *   - Additional O(1) space for pointer and formatting variables
     *   - Total: O(n) due to array copy
     */
    private static void visualize(int[] nums) {
        System.out.println("=== Remove Duplicates Visualization ===");
        System.out.println("Original array: " + Arrays.toString(nums));
        System.out.println();
        
        if (nums.length == 0) {
            System.out.println("Empty array!");
            return;
        }
        
        int[] original = nums.clone();
        int writer = 1;
        
        System.out.println("Initial state: writer = 1 (keeping first element)");
        System.out.printf("Array: %s\n", Arrays.toString(nums));
        System.out.println("       ^ writer");
        System.out.println();
        
        for (int reader = 1; reader < nums.length; reader++) {
            System.out.printf("Step %d: reader = %d, value = %d\n", reader, reader, nums[reader]);
            
            if (nums[reader] != nums[reader - 1]) {
                System.out.printf("  Different from previous (%d ≠ %d)\n", 
                                nums[reader], nums[reader - 1]);
                System.out.printf("  Write %d at position %d\n", nums[reader], writer);
                nums[writer] = nums[reader];
                writer++;
                
                // Show array state
                System.out.print("  Array: [");
                for (int i = 0; i < nums.length; i++) {
                    if (i > 0) System.out.print(", ");
                    if (i < writer) {
                        System.out.print(nums[i]);
                    } else {
                        System.out.print("_");
                    }
                }
                System.out.println("]");
            } else {
                System.out.printf("  Duplicate of %d, skip\n", nums[reader - 1]);
            }
            System.out.println();
        }
        
        System.out.println("Final result:");
        System.out.print("Unique elements: [");
        for (int i = 0; i < writer; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(nums[i]);
        }
        System.out.println("]");
        System.out.println("New length: " + writer);
        
        // Restore original for other tests
        System.arraycopy(original, 0, nums, 0, nums.length);
    }
    
    /**
     * Show the difference between removing all duplicates vs allowing k occurrences
     * 
     * Time Complexity: O(k * n) where n is array length, k is max iterations (3 in this case)
     *   - Loops through k values (1 to 3)
     *   - For each k, calls removeDuplicatesAtMostK which is O(n)
     *   - Array cloning: O(n) per iteration
     *   - Total: k * (O(n) + O(n)) = O(k * n), simplified to O(n) since k is constant
     * 
     * Space Complexity: O(n) for array cloning
     *   - Creates array copy for each test case
     *   - Additional O(1) space for variables and formatting
     */
    private static void compareRemovalStrategies(int[] nums) {
        System.out.println("=== Comparison of Removal Strategies ===");
        System.out.println("Original: " + Arrays.toString(nums));
        
        // Test different k values
        for (int k = 1; k <= 3; k++) {
            int[] copy = nums.clone();
            int newLength = removeDuplicatesAtMostK(copy, k);
            
            System.out.printf("\nAllow at most %d occurrence(s):\n", k);
            System.out.print("Result: [");
            for (int i = 0; i < newLength; i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(copy[i]);
            }
            System.out.println("]");
            System.out.println("Length: " + newLength);
        }
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        System.out.println("Test 1 - Basic example:");
        int[] nums1 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        visualize(nums1.clone());
        System.out.println();
        
        // Test case 2: No duplicates
        System.out.println("Test 2 - No duplicates:");
        int[] nums2 = {1, 2, 3, 4, 5};
        System.out.println("Array: " + Arrays.toString(nums2));
        int length2 = removeDuplicates(nums2.clone());
        System.out.println("New length: " + length2 + " (unchanged)");
        System.out.println();
        
        // Test case 3: All duplicates
        System.out.println("Test 3 - All duplicates:");
        int[] nums3 = {1, 1, 1, 1, 1};
        System.out.println("Array: " + Arrays.toString(nums3));
        int[] copy3 = nums3.clone();
        int length3 = removeDuplicates(copy3);
        System.out.print("Result: [");
        for (int i = 0; i < length3; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(copy3[i]);
        }
        System.out.println("]");
        System.out.println("New length: " + length3);
        System.out.println();
        
        // Test case 4: Allow at most 2 occurrences
        System.out.println("Test 4 - Allow at most 2 occurrences:");
        int[] nums4 = {1, 1, 1, 2, 2, 3};
        compareRemovalStrategies(nums4);
        System.out.println();
        
        // Test case 5: Single element
        System.out.println("Test 5 - Single element:");
        int[] nums5 = {42};
        System.out.println("Array: " + Arrays.toString(nums5));
        System.out.println("New length: " + removeDuplicates(nums5));
        System.out.println();
        
        // Test case 6: Empty array
        System.out.println("Test 6 - Empty array:");
        int[] nums6 = {};
        System.out.println("Array: " + Arrays.toString(nums6));
        System.out.println("New length: " + removeDuplicates(nums6));
        System.out.println();
        
        // Performance test
        System.out.println("Test 7 - Performance:");
        int size = 100000;
        int[] largeArray = new int[size];
        // Create array with many duplicates
        for (int i = 0; i < size; i++) {
            largeArray[i] = i / 100; // Each number appears 100 times
        }
        
        long start = System.nanoTime();
        int uniqueCount = countUnique(largeArray);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int newLength = removeDuplicates(largeArray.clone());
        long time2 = System.nanoTime() - start;
        
        System.out.printf("Array size: %d\n", size);
        System.out.printf("Unique elements: %d\n", uniqueCount);
        System.out.printf("Count time: %.3f ms\n", time1 / 1000000.0);
        System.out.printf("Remove time: %.3f ms\n", time2 / 1000000.0);
        System.out.println("Complexity: O(n) - linear time!");
    }
}