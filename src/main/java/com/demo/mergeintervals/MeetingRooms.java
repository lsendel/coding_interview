package com.demo.mergeintervals;

import java.util.*;

/**
 * Problem: Meeting Rooms (Can attend all meetings?)
 * 
 * PROBLEM: Given an array of meeting time intervals, determine if a person
 * can attend all meetings (no overlaps allowed).
 * Example: [[0,30], [5,10], [15,20]] → false (overlap between [0,30] and [5,10])
 * 
 * TECHNIQUE: Sort and Check Adjacent
 * - Sort by start time
 * - Check if any meeting starts before the previous one ends
 * 
 * VISUALIZATION: [[7,10], [2,4], [8,12]]
 * After sorting: [[2,4], [7,10], [8,12]]
 * Check: [2,4] vs [7,10]: 4 < 7 ✓ No overlap
 * Check: [7,10] vs [8,12]: 10 > 8 ✗ Overlap!
 * Result: Cannot attend all meetings
 * 
 * Time Complexity: O(n log n) - sorting
 * Space Complexity: O(1) - only using variables
 */
public class MeetingRooms {
    
    /**
     * Check if a person can attend all meetings
     * 
     * @param intervals array of meeting intervals
     * @return true if can attend all meetings, false otherwise
     */
    public static boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length <= 1) return true;
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < intervals[i-1][1]) {
                return false; // Overlap detected
            }
        }
        
        return true;
    }
    
    /**
     * Find all conflicting meeting pairs
     */
    public static List<int[]> findConflicts(int[][] intervals) {
        List<int[]> conflicts = new ArrayList<>();
        
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                if (intervalsOverlap(intervals[i], intervals[j])) {
                    conflicts.add(new int[]{i, j});
                }
            }
        }
        
        return conflicts;
    }
    
    /**
     * Check if two intervals overlap
     */
    private static boolean intervalsOverlap(int[] a, int[] b) {
        return a[0] < b[1] && b[0] < a[1];
    }
    
    /**
     * Get the maximum number of non-overlapping meetings
     * (Activity Selection Problem)
     */
    public static int maxNonOverlappingMeetings(int[][] intervals) {
        if (intervals.length == 0) return 0;
        
        // Sort by end time (greedy approach)
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        
        int count = 1;
        int lastEnd = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= lastEnd) {
                count++;
                lastEnd = intervals[i][1];
            }
        }
        
        return count;
    }
    
    /**
     * Visualization helper - shows the conflict checking process
     */
    private static void visualize(int[][] intervals) {
        System.out.println("=== Meeting Rooms Visualization ===");
        System.out.print("Original meetings: ");
        printMeetings(intervals);
        
        if (intervals.length <= 1) {
            System.out.println("Can attend all meetings: YES (0 or 1 meeting)");
            return;
        }
        
        // Sort by start time
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        System.out.print("Sorted by start time: ");
        printMeetings(sorted);
        System.out.println();
        
        // Check for conflicts
        boolean canAttend = true;
        for (int i = 1; i < sorted.length; i++) {
            System.out.printf("Checking meetings %d and %d:\n", i, i+1);
            System.out.printf("  Meeting %d: [%d,%d]\n", i, sorted[i-1][0], sorted[i-1][1]);
            System.out.printf("  Meeting %d: [%d,%d]\n", i+1, sorted[i][0], sorted[i][1]);
            
            if (sorted[i][0] < sorted[i-1][1]) {
                System.out.printf("  ✗ Conflict: Meeting %d starts at %d before Meeting %d ends at %d\n",
                                i+1, sorted[i][0], i, sorted[i-1][1]);
                canAttend = false;
            } else {
                System.out.printf("  ✓ No conflict: Meeting %d starts at %d after Meeting %d ends at %d\n",
                                i+1, sorted[i][0], i, sorted[i-1][1]);
            }
            System.out.println();
        }
        
        System.out.println("Can attend all meetings: " + (canAttend ? "YES" : "NO"));
    }
    
    /**
     * Draw timeline with meetings
     */
    private static void drawTimeline(int[][] intervals) {
        if (intervals.length == 0) return;
        
        System.out.println("\nMeeting Timeline:");
        
        // Find time range
        int minTime = Arrays.stream(intervals).mapToInt(i -> i[0]).min().orElse(0);
        int maxTime = Arrays.stream(intervals).mapToInt(i -> i[1]).max().orElse(0);
        
        // Draw time scale
        System.out.print("Time: ");
        for (int t = minTime; t <= maxTime; t++) {
            System.out.printf("%2d ", t);
        }
        System.out.println();
        
        // Draw each meeting
        for (int i = 0; i < intervals.length; i++) {
            System.out.printf("M%d:   ", i + 1);
            for (int t = minTime; t <= maxTime; t++) {
                if (t >= intervals[i][0] && t < intervals[i][1]) {
                    System.out.print("███");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.printf(" [%d,%d]\n", intervals[i][0], intervals[i][1]);
        }
        
        // Show conflicts visually
        System.out.println("\nConflicts (X marks overlap):");
        System.out.print("      ");
        for (int t = minTime; t <= maxTime; t++) {
            int count = 0;
            for (int[] interval : intervals) {
                if (t >= interval[0] && t < interval[1]) {
                    count++;
                }
            }
            if (count > 1) {
                System.out.print(" X ");
            } else {
                System.out.print("   ");
            }
        }
        System.out.println();
    }
    
    /**
     * Helper to print meetings
     */
    private static void printMeetings(int[][] intervals) {
        System.out.print("[");
        for (int i = 0; i < intervals.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.printf("[%d,%d]", intervals[i][0], intervals[i][1]);
        }
        System.out.println("]");
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example with conflict
        System.out.println("Test 1 - Basic example with conflict:");
        int[][] meetings1 = {{0, 30}, {5, 10}, {15, 20}};
        visualize(meetings1);
        drawTimeline(meetings1);
        System.out.println();
        
        // Test case 2: No conflicts
        System.out.println("\nTest 2 - No conflicts:");
        int[][] meetings2 = {{7, 10}, {2, 4}};
        visualize(meetings2);
        drawTimeline(meetings2);
        System.out.println();
        
        // Test case 3: Back-to-back meetings
        System.out.println("\nTest 3 - Back-to-back meetings (no overlap):");
        int[][] meetings3 = {{8, 10}, {10, 12}, {12, 14}};
        System.out.print("Meetings: ");
        printMeetings(meetings3);
        System.out.println("Can attend all: " + canAttendMeetings(meetings3));
        drawTimeline(meetings3);
        System.out.println();
        
        // Test case 4: Multiple conflicts
        System.out.println("\nTest 4 - Multiple conflicts:");
        int[][] meetings4 = {{1, 5}, {2, 3}, {3, 6}, {5, 7}};
        System.out.print("Meetings: ");
        printMeetings(meetings4);
        List<int[]> conflicts = findConflicts(meetings4);
        System.out.println("Conflicting pairs:");
        for (int[] conflict : conflicts) {
            System.out.printf("  Meeting %d [%s] conflicts with Meeting %d [%s]\n",
                            conflict[0] + 1, Arrays.toString(meetings4[conflict[0]]),
                            conflict[1] + 1, Arrays.toString(meetings4[conflict[1]]));
        }
        drawTimeline(meetings4);
        System.out.println();
        
        // Test case 5: Maximum non-overlapping meetings
        System.out.println("\nTest 5 - Maximum non-overlapping meetings:");
        int[][] meetings5 = {{1, 3}, {2, 4}, {3, 5}, {4, 6}, {5, 7}};
        System.out.print("All meetings: ");
        printMeetings(meetings5);
        drawTimeline(meetings5);
        int maxMeetings = maxNonOverlappingMeetings(meetings5);
        System.out.println("Maximum meetings that can be attended: " + maxMeetings);
        System.out.println();
        
        // Test case 6: Empty and single meeting
        System.out.println("\nTest 6 - Edge cases:");
        int[][] meetings6a = {};
        int[][] meetings6b = {{1, 2}};
        System.out.println("Empty meetings: Can attend all? " + canAttendMeetings(meetings6a));
        System.out.println("Single meeting: Can attend all? " + canAttendMeetings(meetings6b));
        System.out.println();
        
        // Test case 7: Large meeting covering all
        System.out.println("\nTest 7 - One large meeting conflicts with all:");
        int[][] meetings7 = {{1, 10}, {2, 3}, {4, 5}, {6, 7}, {8, 9}};
        System.out.print("Meetings: ");
        printMeetings(meetings7);
        System.out.println("Can attend all: " + canAttendMeetings(meetings7));
        drawTimeline(meetings7);
        System.out.println();
        
        // Performance test
        System.out.println("\nTest 8 - Performance:");
        int n = 10000;
        int[][] largeMeetings = new int[n][2];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            int start = rand.nextInt(100000);
            int duration = rand.nextInt(60) + 1;
            largeMeetings[i] = new int[]{start, start + duration};
        }
        
        long startTime = System.nanoTime();
        boolean canAttend = canAttendMeetings(largeMeetings);
        long endTime = System.nanoTime();
        
        System.out.printf("Number of meetings: %d\n", n);
        System.out.printf("Can attend all: %s\n", canAttend);
        System.out.printf("Time taken: %.3f ms\n", (endTime - startTime) / 1000000.0);
        System.out.println("Complexity: O(n log n) due to sorting");
    }
}