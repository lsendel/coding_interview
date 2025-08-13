package com.demo.mergeintervals;

import java.util.*;

/**
 * Problem: Merge Overlapping Intervals
 * 
 * PROBLEM: Given a list of intervals, merge all overlapping intervals.
 * Example: [[1,3], [2,6], [8,10], [15,18]]
 * Answer: [[1,6], [8,10], [15,18]]
 * 
 * TECHNIQUE: Sort then Merge
 * - Sort intervals by start time
 * - Iterate through sorted intervals
 * - If current interval overlaps with last merged, merge them
 * - Otherwise, add current interval as new merged interval
 * 
 * VISUALIZATION: [[1,3], [2,6], [8,10], [15,18]]
 * After sorting: Same order (already sorted)
 * Step 1: Add [1,3] to merged
 * Step 2: [2,6] overlaps with [1,3] (2 <= 3), merge to [1,6]
 * Step 3: [8,10] doesn't overlap with [1,6] (8 > 6), add separately
 * Step 4: [15,18] doesn't overlap with [8,10] (15 > 10), add separately
 * Result: [[1,6], [8,10], [15,18]]
 * 
 * OVERLAP CONDITION: Two intervals [a,b] and [c,d] overlap if c <= b
 * (assuming intervals are sorted by start time)
 * 
 * Time Complexity: O(n log n) - dominated by sorting
 * Space Complexity: O(n) - for the output array
 */
public class MergeOverlappingIntervals {
    
    /**
     * Merge overlapping intervals
     * 
     * @param intervals list of intervals represented as arrays [start, end]
     * @return merged intervals
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] lastMerged = merged.get(merged.size() - 1);
            
            // Check for overlap: current start <= last end
            if (current[0] <= lastMerged[1]) {
                // Merge by extending the end time
                lastMerged[1] = Math.max(lastMerged[1], current[1]);
            } else {
                // No overlap, add as new interval
                merged.add(current);
            }
        }
        
        return merged.toArray(new int[merged.size()][]);
    }
    
    /**
     * Alternative implementation using Interval class
     */
    public static Interval[] mergeWithIntervalClass(Interval[] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }
        
        Arrays.sort(intervals);
        
        List<Interval> merged = new ArrayList<>();
        merged.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            Interval current = intervals[i];
            Interval lastMerged = merged.get(merged.size() - 1);
            
            if (current.overlaps(lastMerged)) {
                // Replace last interval with merged version
                merged.set(merged.size() - 1, current.merge(lastMerged));
            } else {
                merged.add(current);
            }
        }
        
        return merged.toArray(new Interval[0]);
    }
    
    /**
     * Visualization helper - shows the merge process step by step
     */
    private static void visualize(int[][] intervals) {
        System.out.println("=== Merge Intervals Visualization ===");
        System.out.print("Original intervals: ");
        printIntervals(intervals);
        
        if (intervals.length <= 1) {
            System.out.println("No merging needed!");
            return;
        }
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.print("\nAfter sorting: ");
        printIntervals(intervals);
        System.out.println();
        
        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);
        System.out.println("Step 1: Add first interval " + Arrays.toString(intervals[0]));
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] lastMerged = merged.get(merged.size() - 1);
            
            System.out.printf("\nStep %d: Processing interval %s\n", 
                            i + 1, Arrays.toString(current));
            System.out.printf("  Last merged: %s\n", Arrays.toString(lastMerged));
            
            if (current[0] <= lastMerged[1]) {
                System.out.printf("  Overlap detected: %d <= %d\n", current[0], lastMerged[1]);
                int newEnd = Math.max(lastMerged[1], current[1]);
                System.out.printf("  Merging: [%d,%d] + [%d,%d] = [%d,%d]\n",
                                lastMerged[0], lastMerged[1], current[0], current[1],
                                lastMerged[0], newEnd);
                lastMerged[1] = newEnd;
            } else {
                System.out.printf("  No overlap: %d > %d\n", current[0], lastMerged[1]);
                System.out.println("  Adding as new interval");
                merged.add(current);
            }
            
            System.out.print("  Current merged list: ");
            printIntervals(merged.toArray(new int[0][]));
        }
        
        System.out.print("\nFinal result: ");
        printIntervals(merged.toArray(new int[0][]));
    }
    
    /**
     * Helper to print intervals nicely
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
     * Draw intervals on a timeline
     */
    private static void drawTimeline(int[][] intervals, String label) {
        System.out.println("\n" + label);
        
        // Find range
        int minTime = Integer.MAX_VALUE;
        int maxTime = Integer.MIN_VALUE;
        for (int[] interval : intervals) {
            minTime = Math.min(minTime, interval[0]);
            maxTime = Math.max(maxTime, interval[1]);
        }
        
        // Draw timeline
        System.out.print("   ");
        for (int t = minTime; t <= maxTime; t++) {
            System.out.printf("%2d ", t);
        }
        System.out.println();
        
        // Draw each interval
        for (int i = 0; i < intervals.length; i++) {
            System.out.printf("%2d: ", i + 1);
            for (int t = minTime; t <= maxTime; t++) {
                if (t >= intervals[i][0] && t <= intervals[i][1]) {
                    System.out.print("███");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.printf(" [%d,%d]\n", intervals[i][0], intervals[i][1]);
        }
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        System.out.println("Test 1 - Basic example:");
        int[][] intervals1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        visualize(intervals1.clone());
        drawTimeline(intervals1, "Before merging:");
        drawTimeline(merge(intervals1.clone()), "After merging:");
        System.out.println();
        
        // Test case 2: All intervals overlap
        System.out.println("\nTest 2 - All intervals overlap:");
        int[][] intervals2 = {{1, 4}, {2, 5}, {3, 6}, {4, 7}};
        System.out.print("Input: ");
        printIntervals(intervals2);
        System.out.print("Output: ");
        printIntervals(merge(intervals2.clone()));
        drawTimeline(intervals2, "Before:");
        drawTimeline(merge(intervals2.clone()), "After:");
        System.out.println();
        
        // Test case 3: No overlaps
        System.out.println("\nTest 3 - No overlaps:");
        int[][] intervals3 = {{1, 2}, {4, 5}, {7, 8}};
        System.out.print("Input: ");
        printIntervals(intervals3);
        System.out.print("Output: ");
        printIntervals(merge(intervals3.clone()));
        System.out.println();
        
        // Test case 4: Nested intervals
        System.out.println("\nTest 4 - Nested intervals:");
        int[][] intervals4 = {{1, 8}, {2, 3}, {4, 5}, {6, 7}};
        System.out.print("Input: ");
        printIntervals(intervals4);
        System.out.print("Output: ");
        printIntervals(merge(intervals4.clone()));
        drawTimeline(intervals4, "Before:");
        drawTimeline(merge(intervals4.clone()), "After:");
        System.out.println();
        
        // Test case 5: Adjacent intervals
        System.out.println("\nTest 5 - Adjacent intervals (touching):");
        int[][] intervals5 = {{1, 2}, {2, 3}, {3, 4}, {5, 6}};
        System.out.print("Input: ");
        printIntervals(intervals5);
        System.out.print("Output: ");
        printIntervals(merge(intervals5.clone()));
        System.out.println();
        
        // Test case 6: Single interval
        System.out.println("\nTest 6 - Single interval:");
        int[][] intervals6 = {{1, 5}};
        System.out.print("Input: ");
        printIntervals(intervals6);
        System.out.print("Output: ");
        printIntervals(merge(intervals6));
        System.out.println();
        
        // Performance test
        System.out.println("\nTest 7 - Performance:");
        int n = 10000;
        int[][] largeIntervals = new int[n][2];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            int start = rand.nextInt(50000);
            int length = rand.nextInt(100) + 1;
            largeIntervals[i] = new int[]{start, start + length};
        }
        
        long startTime = System.nanoTime();
        int[][] merged = merge(largeIntervals);
        long endTime = System.nanoTime();
        
        System.out.printf("Input intervals: %d\n", n);
        System.out.printf("Merged intervals: %d\n", merged.length);
        System.out.printf("Time taken: %.3f ms\n", (endTime - startTime) / 1000000.0);
        System.out.printf("Reduction: %.1f%%\n", 
                         (1 - (double)merged.length / n) * 100);
    }
}