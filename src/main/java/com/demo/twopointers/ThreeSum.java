package com.demo.twopointers;

import java.util.*;

/**
 * Problem: Three Sum
 * 
 * PROBLEM: Given an array of integers, find all unique triplets that sum to zero.
 * Example: arr = [-1, 0, 1, 2, -1, -4]
 * Answer: [[-1, -1, 2], [-1, 0, 1]]
 * 
 * TECHNIQUE: Fix one element, then use Two Pointers for the remaining two
 * - Sort the array first
 * - For each element, find two others that sum to its negative
 * - Skip duplicates to ensure unique triplets
 * 
 * VISUALIZATION: arr = [-4, -1, -1, 0, 1, 2]
 * Fix arr[0] = -4, need two elements summing to 4
 * - left=1(-1), right=5(2), sum=1 < 4, move left
 * - left=2(-1), right=5(2), sum=1 < 4, move left
 * - left=3(0), right=5(2), sum=2 < 4, move left
 * - left=4(1), right=5(2), sum=3 < 4, move left
 * - left >= right, no solution for -4
 * 
 * Fix arr[1] = -1, need two elements summing to 1
 * - left=2(-1), right=5(2), sum=1 ✓ Found [-1, -1, 2]
 * 
 * KEY INSIGHTS:
 * - Sorting enables the two-pointer technique
 * - Skip duplicates to avoid duplicate triplets
 * - Early termination when first element > 0 (can't sum to 0)
 * 
 * Time Complexity: O(n²) - n iterations with O(n) two-pointer search each
 * Space Complexity: O(1) excluding output array
 */
public class ThreeSum {
    
    /**
     * Find all unique triplets that sum to zero
     * 
     * Time Complexity: O(n²) where n is the length of the array
     *   - Sorting the array: O(n log n)
     *   - Outer loop iterates through n-2 elements: O(n)
     *   - For each outer iteration, inner two-pointer search: O(n)
     *   - Total: O(n log n) + O(n) * O(n) = O(n²)
     *   - Skipping duplicates doesn't change overall complexity
     * 
     * Space Complexity: O(1) excluding the output list
     *   - Only using constant extra variables (left, right, target)
     *   - Sorting is done in-place (typically O(log n) for recursion stack)
     *   - Output list size depends on number of valid triplets (not counted in space complexity)
     * 
     * @param nums array of integers
     * @return list of unique triplets that sum to zero
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 3) return result;
        
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            // Early termination
            if (nums[i] > 0) break;
            
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Generalized k-Sum problem (find k numbers that sum to target)
     * 
     * Time Complexity: O(n^(k-1)) where n is array length, k is the sum count
     *   - For k=2: O(n) using two pointers
     *   - For k=3: O(n²) using nested loops with two-pointer base case
     *   - For k=4: O(n³) and so on
     *   - Each level of recursion reduces k by 1 and iterates through O(n) elements
     * 
     * Space Complexity: O(k) for recursion stack depth
     *   - Recursion depth is k-2 (until we reach the base case k=2)
     *   - Each recursive call uses O(1) additional space
     *   - Plus O(log n) for sorting if not already sorted
     */
    public static List<List<Integer>> kSum(int[] nums, int target, int k) {
        Arrays.sort(nums);
        return kSumHelper(nums, target, k, 0);
    }
    
    /**
     * Recursive helper for k-Sum problem
     * 
     * Time Complexity: O(n^(k-1)) where n is the remaining array length
     *   - Base case (k=2): O(n) using two pointers
     *   - Recursive case: O(n) iterations, each calling kSumHelper with k-1
     *   - Results in O(n^(k-1)) overall complexity
     * 
     * Space Complexity: O(k) for recursion stack depth
     *   - Maximum recursion depth is k-2 (until base case k=2)
     *   - Each call uses O(1) additional space for variables
     *   - Plus space for intermediate result lists
     */
    private static List<List<Integer>> kSumHelper(int[] nums, long target, int k, int start) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Base case: two sum
        if (k == 2) {
            int left = start;
            int right = nums.length - 1;
            
            while (left < right) {
                long sum = (long)nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[left], nums[right]));
                    
                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            return result;
        }
        
        // Recursive case
        for (int i = start; i < nums.length - k + 1; i++) {
            // Skip duplicates
            if (i > start && nums[i] == nums[i - 1]) continue;
            
            // Get (k-1)Sum for remaining elements
            List<List<Integer>> subResult = kSumHelper(nums, target - nums[i], k - 1, i + 1);
            
            for (List<Integer> list : subResult) {
                List<Integer> newList = new ArrayList<>();
                newList.add(nums[i]);
                newList.addAll(list);
                result.add(newList);
            }
        }
        
        return result;
    }
    
    /**
     * Find triplets closest to target (not necessarily zero)
     * 
     * Time Complexity: O(n²) where n is the length of the array
     *   - Sorting: O(n log n)
     *   - Outer loop: O(n) iterations
     *   - Inner two-pointer search: O(n) per outer iteration
     *   - Total: O(n log n) + O(n²) = O(n²)
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using variables to track closest sum and pointers
     *   - Sorting uses O(log n) stack space
     */
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2];
        
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (Math.abs(sum - target) < Math.abs(closestSum - target)) {
                    closestSum = sum;
                }
                
                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    return sum; // Exact match
                }
            }
        }
        
        return closestSum;
    }
    
    /**
     * Visualization helper - shows the algorithm step by step
     * 
     * Time Complexity: O(n²) where n is the length of the array
     *   - Same algorithm as threeSum() but limited to first 3 outer iterations for demo
     *   - Each iteration includes O(1) additional printing operations
     *   - Overall complexity remains O(n²) for the full algorithm
     * 
     * Space Complexity: O(m) where m is the number of triplets found
     *   - Stores result list for visualization purposes
     *   - Plus O(1) for pointer variables and temporary calculations
     */
    private static void visualize(int[] nums) {
        System.out.println("=== Three Sum Visualization ===");
        System.out.println("Original array: " + Arrays.toString(nums));
        
        Arrays.sort(nums);
        System.out.println("Sorted array: " + Arrays.toString(nums));
        System.out.println("Target sum: 0");
        System.out.println();
        
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2 && i < 3; i++) { // Limit visualization
            if (i > 0 && nums[i] == nums[i - 1]) {
                System.out.printf("Skipping duplicate at index %d (value %d)\n", i, nums[i]);
                continue;
            }
            
            System.out.printf("Fixing first element: nums[%d] = %d\n", i, nums[i]);
            System.out.printf("Need two elements that sum to %d\n", -nums[i]);
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                System.out.printf("  left=%d(%d), right=%d(%d), sum=%d",
                                left, nums[left], right, nums[right], sum);
                
                if (sum == -nums[i]) {
                    System.out.printf(" ✓ Found triplet: [%d, %d, %d]\n",
                                    nums[i], nums[left], nums[right]);
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < -nums[i]) {
                    System.out.println(" < target, move left");
                    left++;
                } else {
                    System.out.println(" > target, move right");
                    right--;
                }
            }
            System.out.println();
        }
        
        System.out.println("Result: " + result);
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        System.out.println("Test 1 - Basic example:");
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        visualize(nums1);
        System.out.println();
        
        // Test case 2: No solution
        System.out.println("Test 2 - No solution:");
        int[] nums2 = {1, 2, 3, 4};
        System.out.println("Array: " + Arrays.toString(nums2));
        System.out.println("Result: " + threeSum(nums2));
        System.out.println();
        
        // Test case 3: All zeros
        System.out.println("Test 3 - All zeros:");
        int[] nums3 = {0, 0, 0, 0};
        System.out.println("Array: " + Arrays.toString(nums3));
        System.out.println("Result: " + threeSum(nums3));
        System.out.println();
        
        // Test case 4: Many duplicates
        System.out.println("Test 4 - Many duplicates:");
        int[] nums4 = {-2, 0, 0, 2, 2};
        System.out.println("Array: " + Arrays.toString(nums4));
        System.out.println("Result: " + threeSum(nums4));
        System.out.println();
        
        // Test case 5: Three sum closest
        System.out.println("Test 5 - Three sum closest:");
        int[] nums5 = {-1, 2, 1, -4};
        int target = 1;
        System.out.println("Array: " + Arrays.toString(nums5) + ", target: " + target);
        System.out.println("Closest sum: " + threeSumClosest(nums5, target));
        System.out.println();
        
        // Test case 6: Four sum (using kSum)
        System.out.println("Test 6 - Four sum (target = 0):");
        int[] nums6 = {1, 0, -1, 0, -2, 2};
        System.out.println("Array: " + Arrays.toString(nums6));
        System.out.println("4-Sum result: " + kSum(nums6, 0, 4));
        System.out.println();
        
        // Performance test
        System.out.println("Test 7 - Performance:");
        int size = 1000;
        int[] largeArray = new int[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            largeArray[i] = rand.nextInt(201) - 100; // -100 to 100
        }
        
        long start = System.nanoTime();
        List<List<Integer>> result = threeSum(largeArray);
        long time = System.nanoTime() - start;
        
        System.out.printf("Array size: %d\n", size);
        System.out.printf("Triplets found: %d\n", result.size());
        System.out.printf("Time: %.3f ms\n", time / 1000000.0);
        System.out.println("Complexity: O(n²) - quadratic");
    }
}