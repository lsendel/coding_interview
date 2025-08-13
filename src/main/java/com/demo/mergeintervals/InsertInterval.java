package com.demo.mergeintervals;

import java.util.*;

/**
 * Problem: Insert Interval
 * 
 * PROBLEM: Given a sorted list of non-overlapping intervals and a new interval,
 * insert the new interval and merge if necessary.
 * Example: intervals = [[1,3], [6,9]], newInterval = [2,5]
 * Answer: [[1,5], [6,9]]
 * 
 * TECHNIQUE: Three-Phase Approach
 * 1. Add all intervals that end before new interval starts
 * 2. Merge all overlapping intervals with new interval
 * 3. Add all remaining intervals that start after new interval ends
 * 
 * VISUALIZATION: intervals = [[1,2], [3,5], [6,7], [8,10]], new = [4,8]
 * Phase 1: [1,2] ends before 4, add it
 * Phase 2: [3,5] overlaps (3 <= 8 and 5 >= 4), merge to [3,8]
 *          [6,7] overlaps (6 <= 8), extend to [3,8]
 *          [8,10] overlaps (8 <= 8), extend to [3,10]
 * Phase 3: No more intervals
 * Result: [[1,2], [3,10]]
 * 
 * Time Complexity: O(n) - single pass through intervals
 * Space Complexity: O(n) - for the output array
 */
public class InsertInterval {
    
    /**
     * Insert a new interval into sorted non-overlapping intervals
     * 
     * @param intervals sorted non-overlapping intervals
     * @param newInterval interval to insert
     * @return intervals after insertion and merging
     */
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;
        
        // Phase 1: Add all intervals that end before new interval starts
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        
        // Phase 2: Merge overlapping intervals
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        result.add(newInterval);
        
        // Phase 3: Add remaining intervals
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Alternative implementation with detailed tracking
     */
    public static InsertResult insertWithDetails(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        List<int[]> mergedWith = new ArrayList<>();
        int[] finalInterval = newInterval.clone();
        int i = 0;
        int n = intervals.length;
        
        // Phase 1
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }
        int phase1Count = i;
        
        // Phase 2
        while (i < n && intervals[i][0] <= newInterval[1]) {
            mergedWith.add(intervals[i]);
            finalInterval[0] = Math.min(finalInterval[0], intervals[i][0]);
            finalInterval[1] = Math.max(finalInterval[1], intervals[i][1]);
            i++;
        }
        result.add(finalInterval);
        
        // Phase 3
        int phase3Start = i;
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }
        
        return new InsertResult(
            result.toArray(new int[0][]),
            phase1Count,
            mergedWith.toArray(new int[0][]),
            n - phase3Start,
            finalInterval
        );
    }
    
    /**
     * Helper class to store detailed insertion results
     */
    static class InsertResult {
        int[][] result;
        int beforeCount;
        int[][] mergedIntervals;
        int afterCount;
        int[] finalNewInterval;
        
        InsertResult(int[][] result, int beforeCount, int[][] mergedIntervals, 
                    int afterCount, int[] finalNewInterval) {
            this.result = result;
            this.beforeCount = beforeCount;
            this.mergedIntervals = mergedIntervals;
            this.afterCount = afterCount;
            this.finalNewInterval = finalNewInterval;
        }
    }
    
    /**
     * Visualization helper - shows the three phases
     */
    private static void visualize(int[][] intervals, int[] newInterval) {
        System.out.println("=== Insert Interval Visualization ===");
        System.out.print("Original intervals: ");
        printIntervals(intervals);
        System.out.println("New interval: " + Arrays.toString(newInterval));
        System.out.println();
        
        InsertResult result = insertWithDetails(intervals, newInterval);
        
        System.out.println("Phase 1: Add intervals ending before new interval starts");
        System.out.printf("  New interval starts at %d\n", newInterval[0]);
        System.out.printf("  Added %d interval(s) that end before %d\n", 
                         result.beforeCount, newInterval[0]);
        
        System.out.println("\nPhase 2: Merge overlapping intervals");
        if (result.mergedIntervals.length > 0) {
            System.out.println("  Intervals that overlap with " + 
                             Arrays.toString(newInterval) + ":");
            for (int[] interval : result.mergedIntervals) {
                System.out.println("    " + Arrays.toString(interval));
            }
            System.out.println("  Merged result: " + 
                             Arrays.toString(result.finalNewInterval));
        } else {
            System.out.println("  No overlapping intervals");
            System.out.println("  Added new interval as-is: " + 
                             Arrays.toString(newInterval));
        }
        
        System.out.println("\nPhase 3: Add remaining intervals");
        System.out.printf("  Added %d remaining interval(s)\n", result.afterCount);
        
        System.out.print("\nFinal result: ");
        printIntervals(result.result);
    }
    
    /**
     * Helper to print intervals
     */
    private static void printIntervals(int[][] intervals) {
        System.out.print("[");
        for (int i = 0; i < intervals.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.printf("[%d,%d]", intervals[i][0], intervals[i][1]);
        }
        System.out.println("]");
    }
    
    /**
     * Draw timeline visualization
     */
    private static void drawTimeline(int[][] intervals, int[] newInterval, String label) {
        System.out.println("\n" + label);
        
        // Find range
        int minTime = Math.min(newInterval[0], 
                              Arrays.stream(intervals).mapToInt(i -> i[0]).min().orElse(0));
        int maxTime = Math.max(newInterval[1],
                              Arrays.stream(intervals).mapToInt(i -> i[1]).max().orElse(0));
        
        // Draw timeline
        System.out.print("     ");
        for (int t = minTime; t <= maxTime; t++) {
            System.out.printf("%2d ", t);
        }
        System.out.println();
        
        // Draw existing intervals
        for (int i = 0; i < intervals.length; i++) {
            System.out.printf("I%d:  ", i + 1);
            for (int t = minTime; t <= maxTime; t++) {
                if (t >= intervals[i][0] && t <= intervals[i][1]) {
                    System.out.print("███");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.printf(" [%d,%d]\n", intervals[i][0], intervals[i][1]);
        }
        
        // Draw new interval
        System.out.print("NEW: ");
        for (int t = minTime; t <= maxTime; t++) {
            if (t >= newInterval[0] && t <= newInterval[1]) {
                System.out.print("▓▓▓");
            } else {
                System.out.print("   ");
            }
        }
        System.out.printf(" [%d,%d]\n", newInterval[0], newInterval[1]);
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        System.out.println("Test 1 - Basic example:");
        int[][] intervals1 = {{1, 3}, {6, 9}};
        int[] new1 = {2, 5};
        visualize(intervals1, new1);
        drawTimeline(intervals1, new1, "Before insertion:");
        drawTimeline(insert(intervals1.clone(), new1), new1, "After insertion:");
        System.out.println();
        
        // Test case 2: New interval overlaps multiple
        System.out.println("\nTest 2 - Overlaps multiple intervals:");
        int[][] intervals2 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] new2 = {4, 8};
        visualize(intervals2, new2);
        drawTimeline(intervals2, new2, "Timeline:");
        System.out.println();
        
        // Test case 3: New interval before all
        System.out.println("\nTest 3 - New interval before all:");
        int[][] intervals3 = {{3, 5}, {6, 9}};
        int[] new3 = {1, 2};
        System.out.print("Intervals: ");
        printIntervals(intervals3);
        System.out.println("New: " + Arrays.toString(new3));
        System.out.print("Result: ");
        printIntervals(insert(intervals3, new3));
        System.out.println();
        
        // Test case 4: New interval after all
        System.out.println("\nTest 4 - New interval after all:");
        int[][] intervals4 = {{1, 2}, {3, 4}};
        int[] new4 = {5, 6};
        System.out.print("Intervals: ");
        printIntervals(intervals4);
        System.out.println("New: " + Arrays.toString(new4));
        System.out.print("Result: ");
        printIntervals(insert(intervals4, new4));
        System.out.println();
        
        // Test case 5: Empty intervals
        System.out.println("\nTest 5 - Empty intervals:");
        int[][] intervals5 = {};
        int[] new5 = {5, 7};
        System.out.println("Intervals: []");
        System.out.println("New: " + Arrays.toString(new5));
        System.out.print("Result: ");
        printIntervals(insert(intervals5, new5));
        System.out.println();
        
        // Test case 6: New interval covers all
        System.out.println("\nTest 6 - New interval covers all:");
        int[][] intervals6 = {{3, 4}, {5, 6}, {7, 8}};
        int[] new6 = {1, 10};
        visualize(intervals6, new6);
        System.out.println();
        
        // Performance test
        System.out.println("\nTest 7 - Performance:");
        int n = 100000;
        int[][] largeIntervals = new int[n][2];
        for (int i = 0; i < n; i++) {
            largeIntervals[i] = new int[]{i * 3, i * 3 + 1};
        }
        int[] newLarge = {50000, 60000};
        
        long startTime = System.nanoTime();
        int[][] result = insert(largeIntervals, newLarge);
        long endTime = System.nanoTime();
        
        System.out.printf("Original intervals: %d\n", n);
        System.out.printf("Result intervals: %d\n", result.length);
        System.out.printf("Time taken: %.3f ms\n", (endTime - startTime) / 1000000.0);
        System.out.println("Complexity: O(n) - linear time!");
    }
}