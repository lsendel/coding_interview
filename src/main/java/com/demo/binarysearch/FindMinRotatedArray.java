package com.demo.binarysearch;

import java.util.*;

/**
 * Problem: Find Minimum in Rotated Sorted Array
 * 
 * MNEMONIC: "VALLEY FINDER - Find the lowest point in the mountain range!"
 * 🏔️ Like finding the valley between two mountains after an earthquake shifted them
 * 
 * PROBLEM: Find the minimum element in a rotated sorted array.
 * Example: [3,4,5,1,2] → return 1
 * 
 * TECHNIQUE: Binary Search for the "Valley Point"
 * - The minimum is where the "break" happened (rotation point)
 * - Compare middle with right end to decide direction
 * - If mid > right: valley is on the right
 * - If mid < right: valley is on the left (or at mid)
 * 
 * VISUALIZATION:
 *     5
 *   4   
 * 3       2
 *       1   ← Valley (minimum)
 * 
 * [3, 4, 5, 1, 2]
 *        ↑
 *     Valley
 * 
 * WHY COMPARE WITH RIGHT?
 * - If mid > right: We're on the "high mountain" side
 * - If mid < right: We're already in the valley or past it
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */
public class FindMinRotatedArray {
    
    /**
     * Find the valley (minimum) in the mountain range
     * 
     * REMEMBER: "VALLEY BETWEEN MOUNTAINS"
     * - If standing higher than right edge → valley is to the right
     * - If standing lower than right edge → valley is to the left
     * 
     * @param mountainRange the rotated array
     * @return the lowest point (minimum value)
     */
    public static int findMin_ValleyFinder(int[] mountainRange) {
        int leftEdge = 0;
        int rightEdge = mountainRange.length - 1;
        
        // Special case: not rotated (no valley)
        if (mountainRange[leftEdge] <= mountainRange[rightEdge]) {
            return mountainRange[leftEdge];
        }
        
        while (leftEdge < rightEdge) {
            int currentPosition = leftEdge + (rightEdge - leftEdge) / 2;
            
            // Are we on the high mountain or in the valley?
            if (mountainRange[currentPosition] > mountainRange[rightEdge]) {
                // We're on the HIGH MOUNTAIN side
                // Valley must be to the right
                leftEdge = currentPosition + 1;
            } else {
                // We're in the VALLEY or past it
                // Valley is here or to the left
                rightEdge = currentPosition;
            }
        }
        
        return mountainRange[leftEdge];
    }
    
    /**
     * Alternative: Find by checking neighbors
     */
    public static int findMin_NeighborCheck(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            // Already sorted portion
            if (nums[left] < nums[right]) {
                return nums[left];
            }
            
            int mid = left + (right - left) / 2;
            
            // Check if mid is the rotation point
            if (mid > 0 && nums[mid] < nums[mid - 1]) {
                return nums[mid];
            }
            
            // Check if mid+1 is the rotation point
            if (mid < nums.length - 1 && nums[mid] > nums[mid + 1]) {
                return nums[mid + 1];
            }
            
            // Decide which way to go
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return nums[left];
    }
    
    /**
     * Find the rotation amount
     */
    public static int findRotationAmount(int[] nums) {
        int minIndex = findMinIndex(nums);
        return minIndex; // Rotation amount = index of minimum
    }
    
    private static int findMinIndex(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        if (nums[left] <= nums[right]) {
            return 0; // Not rotated
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Visualization helper - shows the valley finding process
     */
    private static void visualizeValleySearch(int[] nums) {
        System.out.println("=== 🏔️ VALLEY FINDER VISUALIZATION ===");
        System.out.println("Mountain range: " + Arrays.toString(nums));
        
        // Draw the mountain range
        drawMountainRange(nums);
        
        System.out.println("\nSearching for the valley:");
        
        int left = 0;
        int right = nums.length - 1;
        int step = 0;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            step++;
            
            System.out.printf("\nStep %d:\n", step);
            System.out.printf("  Exploring range [%d to %d]\n", left, right);
            System.out.printf("  Current position: %d (height: %d)\n", mid, nums[mid]);
            System.out.printf("  Right edge height: %d\n", nums[right]);
            
            if (nums[mid] > nums[right]) {
                System.out.println("  → Standing on HIGH MOUNTAIN (higher than right edge)");
                System.out.println("  → Valley must be to the RIGHT ↘");
                left = mid + 1;
            } else {
                System.out.println("  → In VALLEY or past it (lower than right edge)");
                System.out.println("  → Valley is HERE or to the LEFT ↖");
                right = mid;
            }
        }
        
        System.out.printf("\n🏔️ Valley found at position %d with height %d\n", 
                         left, nums[left]);
    }
    
    /**
     * Draw ASCII mountain range
     */
    private static void drawMountainRange(int[] nums) {
        if (nums.length == 0) return;
        
        int max = Arrays.stream(nums).max().orElse(0);
        int min = Arrays.stream(nums).min().orElse(0);
        int height = max - min + 1;
        
        System.out.println("\nMountain Range Visualization:");
        
        // Draw the mountain
        for (int h = max; h >= min; h--) {
            System.out.printf("%2d |", h);
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] >= h) {
                    System.out.print(" ▓▓");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        
        // Draw base
        System.out.print("   +");
        for (int i = 0; i < nums.length; i++) {
            System.out.print("---");
        }
        System.out.println();
        
        // Draw indices
        System.out.print("    ");
        for (int i = 0; i < nums.length; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        
        // Mark the valley
        int minIndex = findMinIndex(nums);
        System.out.print("    ");
        for (int i = 0; i < nums.length; i++) {
            if (i == minIndex) {
                System.out.print(" ↑ ");
            } else {
                System.out.print("   ");
            }
        }
        System.out.println("\n    Valley (minimum)");
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("🏔️ FIND MINIMUM IN ROTATED ARRAY - Valley Finder!\n");
        
        // Test 1: Basic example
        System.out.println("Test 1 - Classic mountain range:");
        int[] mountains1 = {3, 4, 5, 1, 2};
        
        int valley1 = findMin_ValleyFinder(mountains1);
        System.out.println("Mountain heights: " + Arrays.toString(mountains1));
        System.out.println("Valley (minimum): " + valley1);
        System.out.println("Valley is at position: " + findMinIndex(mountains1));
        System.out.println();
        
        // Test 2: Visualization
        System.out.println("Test 2 - Visual valley search:");
        visualizeValleySearch(mountains1);
        System.out.println();
        
        // Test 3: Different rotation amounts
        System.out.println("Test 3 - Various mountain shifts:");
        int[] original = {1, 2, 3, 4, 5, 6, 7};
        
        for (int rotation = 0; rotation <= original.length; rotation++) {
            int[] rotated = new int[original.length];
            for (int i = 0; i < original.length; i++) {
                rotated[i] = original[(i + rotation) % original.length];
            }
            
            int min = findMin_ValleyFinder(rotated);
            System.out.printf("Rotated %d times: %s → Valley: %d\n", 
                            rotation, Arrays.toString(rotated), min);
        }
        System.out.println();
        
        // Test 4: Edge cases
        System.out.println("Test 4 - Edge cases:");
        
        // Single element
        int[] single = {5};
        System.out.println("Single peak [5]: Valley = " + findMin_ValleyFinder(single));
        
        // Two elements
        int[] two1 = {2, 1};
        int[] two2 = {1, 2};
        System.out.println("Two peaks [2,1]: Valley = " + findMin_ValleyFinder(two1));
        System.out.println("Two peaks [1,2]: Valley = " + findMin_ValleyFinder(two2));
        
        // Not rotated
        int[] notRotated = {1, 2, 3, 4, 5};
        System.out.println("Not rotated [1,2,3,4,5]: Valley = " + 
                         findMin_ValleyFinder(notRotated));
        
        // Fully rotated (same as not rotated)
        int[] fullyRotated = {1, 2, 3, 4, 5};
        System.out.println("Fully rotated: Valley = " + findMin_ValleyFinder(fullyRotated));
        System.out.println();
        
        // Test 5: Compare methods
        System.out.println("Test 5 - Compare two approaches:");
        int[] testArray = {4, 5, 6, 7, 0, 1, 2};
        
        long start1 = System.nanoTime();
        int result1 = findMin_ValleyFinder(testArray);
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        int result2 = findMin_NeighborCheck(testArray);
        long time2 = System.nanoTime() - start2;
        
        System.out.println("Array: " + Arrays.toString(testArray));
        System.out.println("Valley finder: " + result1 + " (Time: " + time1 + " ns)");
        System.out.println("Neighbor check: " + result2 + " (Time: " + time2 + " ns)");
        System.out.println();
        
        // Test 6: Large array
        System.out.println("Test 6 - Large mountain range:");
        int n = 100000;
        int[] largeMountains = new int[n];
        for (int i = 0; i < n; i++) {
            largeMountains[i] = i + 1000; // Heights from 1000 to 100999
        }
        
        // Rotate by 30%
        int rotation = n * 3 / 10;
        int[] rotatedLarge = new int[n];
        for (int i = 0; i < n; i++) {
            rotatedLarge[i] = largeMountains[(i + rotation) % n];
        }
        
        long start = System.nanoTime();
        int valley = findMin_ValleyFinder(rotatedLarge);
        long time = System.nanoTime() - start;
        
        System.out.printf("Mountain range size: %d\n", n);
        System.out.printf("Rotated by: %d positions\n", rotation);
        System.out.printf("Valley height: %d\n", valley);
        System.out.printf("Time to find: %.3f ms\n", time / 1000000.0);
        System.out.printf("Operations: ~%d (log₂(%d) = %.1f)\n", 
                         (int)(Math.log(n) / Math.log(2)), n, Math.log(n) / Math.log(2));
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- VALLEY FINDER: Looking for lowest point between mountains");
        System.out.println("- Compare with RIGHT edge (more intuitive)");
        System.out.println("- If mid > right → valley is on right side");
        System.out.println("- If mid < right → valley is on left side (or at mid)");
        System.out.println("- The valley is where the rotation happened!");
    }
}