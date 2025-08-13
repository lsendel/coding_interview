package com.demo.mergeintervals;

/**
 * Helper class to represent an interval
 * Used across multiple interval-based problems
 */
public class Interval implements Comparable<Interval> {
    public int start;
    public int end;
    
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public Interval(int[] interval) {
        this.start = interval[0];
        this.end = interval[1];
    }
    
    public int[] toArray() {
        return new int[]{start, end};
    }
    
    @Override
    public String toString() {
        return "[" + start + "," + end + "]";
    }
    
    @Override
    public int compareTo(Interval other) {
        return Integer.compare(this.start, other.start);
    }
    
    /**
     * Check if this interval overlaps with another
     */
    public boolean overlaps(Interval other) {
        return this.start <= other.end && other.start <= this.end;
    }
    
    /**
     * Merge this interval with another (assuming they overlap)
     */
    public Interval merge(Interval other) {
        return new Interval(
            Math.min(this.start, other.start),
            Math.max(this.end, other.end)
        );
    }
    
    /**
     * Convert array of int arrays to array of Intervals
     */
    public static Interval[] fromArray(int[][] intervals) {
        Interval[] result = new Interval[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            result[i] = new Interval(intervals[i]);
        }
        return result;
    }
    
    /**
     * Convert array of Intervals to array of int arrays
     */
    public static int[][] toArray(Interval[] intervals) {
        int[][] result = new int[intervals.length][2];
        for (int i = 0; i < intervals.length; i++) {
            result[i] = intervals[i].toArray();
        }
        return result;
    }
}