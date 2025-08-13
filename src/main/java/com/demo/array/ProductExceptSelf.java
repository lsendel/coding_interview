package com.demo.array;

import java.util.Arrays;

/**
 * Problem: Product of Array Except Self
 * 
 * PROBLEM: Given an array nums, return an array where output[i] is the product
 * of all elements except nums[i]. Cannot use division.
 * Example: nums = [1, 2, 3, 4]
 * Answer: [24, 12, 8, 6]
 * 
 * TECHNIQUE: Left and Right Product Arrays
 * - Calculate prefix products (product of all elements to the left)
 * - Calculate suffix products (product of all elements to the right)
 * - Result[i] = left[i] * right[i]
 * 
 * OPTIMIZATION: Use output array for left products, single pass for right
 * 
 * VISUALIZATION: nums = [1, 2, 3, 4]
 * Left products:  [1, 1, 2, 6]    (products of elements to the left)
 * Right products: [24, 12, 4, 1]  (products of elements to the right)
 * Result: [1*24, 1*12, 2*4, 6*1] = [24, 12, 8, 6]
 * 
 * SINGLE ARRAY APPROACH:
 * Pass 1 (left to right): Build left products in result array
 * Pass 2 (right to left): Multiply by right products on the fly
 * 
 * Time Complexity: O(n) - two passes through array
 * Space Complexity: O(1) - excluding output array
 */
public class ProductExceptSelf {
    
    /**
     * Main solution using two passes
     * 
     * Time Complexity: O(n)
     * - n is the length of the input array
     * - First pass (left to right): iterate through n elements once: O(n)
     * - Second pass (right to left): iterate through n elements once: O(n)
     * - Total: O(n) + O(n) = O(n)
     * - Each array access and multiplication is O(1)
     * 
     * Space Complexity: O(1) auxiliary space
     * - Only uses constant extra variables: rightProduct (O(1))
     * - Result array is not counted as auxiliary space (required for output)
     * - No additional data structures or recursive calls
     * - Total auxiliary space: O(1)
     * 
     * @param nums input array
     * @return array of products except self
     */
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        
        // Pass 1: Calculate left products
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        
        // Pass 2: Calculate right products and multiply
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i];
        }
        
        return result;
    }
    
    /**
     * Alternative: Using separate arrays for clarity (not space-optimal)
     * This version is easier to understand but uses extra space
     * 
     * Time Complexity: O(n)
     * - n is the length of input array
     * - Build left products array: one pass through n elements: O(n)
     * - Build right products array: one pass through n elements: O(n)
     * - Final multiplication: one pass through n elements: O(n)
     * - Total: O(n) + O(n) + O(n) = O(n)
     * 
     * Space Complexity: O(n) auxiliary space
     * - Left products array: O(n) space
     * - Right products array: O(n) space
     * - Result array: O(n) space (required for output, may not count as auxiliary)
     * - Total auxiliary space: O(n) for left and right arrays
     * - This uses more space than the optimized version but is more readable
     * 
     * @param nums input array
     * @return array of products except self
     */
    public static int[] productExceptSelfWithArrays(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int[] result = new int[n];
        
        // Build left products array
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
        }
        
        // Build right products array
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }
        
        // Multiply left and right products
        for (int i = 0; i < n; i++) {
            result[i] = left[i] * right[i];
        }
        
        return result;
    }
    
    /**
     * Helper method to print step-by-step visualization
     */
    private static void visualize(int[] nums) {
        System.out.println("=== Step-by-Step Visualization ===");
        System.out.println("Input array: " + Arrays.toString(nums));
        
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        
        // Calculate and show left products
        left[0] = 1;
        System.out.print("Left products: [1");
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * nums[i - 1];
            System.out.print(", " + left[i]);
        }
        System.out.println("]");
        
        // Calculate and show right products
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }
        System.out.println("Right products: " + Arrays.toString(right));
        
        // Show final calculation
        System.out.print("Final result: [");
        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(left[i] + "*" + right[i] + "=" + (left[i] * right[i]));
        }
        System.out.println("]");
        System.out.println();
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        int[] test1 = {1, 2, 3, 4};
        System.out.println("Test 1 - Input: " + Arrays.toString(test1));
        visualize(test1);
        System.out.println("Result: " + Arrays.toString(productExceptSelf(test1)));
        System.out.println();
        
        // Test case 2: Array with zeros
        int[] test2 = {0, 1, 2, 3};
        System.out.println("Test 2 - Input with zero: " + Arrays.toString(test2));
        System.out.println("Result: " + Arrays.toString(productExceptSelf(test2)));
        System.out.println();
        
        // Test case 3: Array with multiple zeros
        int[] test3 = {0, 0, 1, 2};
        System.out.println("Test 3 - Input with multiple zeros: " + Arrays.toString(test3));
        System.out.println("Result: " + Arrays.toString(productExceptSelf(test3)));
        System.out.println();
        
        // Test case 4: Two elements
        int[] test4 = {2, 3};
        System.out.println("Test 4 - Two elements: " + Arrays.toString(test4));
        System.out.println("Result: " + Arrays.toString(productExceptSelf(test4)));
        System.out.println();
        
        // Test case 5: Negative numbers
        int[] test5 = {-1, 1, 0, -3, 3};
        System.out.println("Test 5 - With negatives: " + Arrays.toString(test5));
        System.out.println("Result: " + Arrays.toString(productExceptSelf(test5)));
    }
}