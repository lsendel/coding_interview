package com.demo.codesignal;

import java.util.*;

public class CustomArrayIteration {
    
    /**
     * Iterates through an array based on a custom pattern
     * Given array: "gctacbadc"
     * Pattern: "tadcag"
     * This means we want to visit the array in an order that produces "tadcag"
     */
    public static String iterateByPattern(String array, String pattern) {
        // Map each character to its positions in the original array
        Map<Character, Queue<Integer>> charPositions = new HashMap<>();
        
        for (int i = 0; i < array.length(); i++) {
            char c = array.charAt(i);
            charPositions.computeIfAbsent(c, k -> new LinkedList<>()).offer(i);
        }
        
        StringBuilder result = new StringBuilder();
        List<Integer> visitOrder = new ArrayList<>();
        
        // For each character in the pattern, find its position in the array
        for (char c : pattern.toCharArray()) {
            if (charPositions.containsKey(c) && !charPositions.get(c).isEmpty()) {
                int position = charPositions.get(c).poll();
                visitOrder.add(position);
                result.append(array.charAt(position));
            }
        }
        
        System.out.println("Visit order (indices): " + visitOrder);
        return result.toString();
    }
    
    /**
     * Alternative approach: Find indices that would produce the pattern
     * and iterate in that order
     */
    public static String iterateByIndices(String array, String pattern) {
        // Find the indices needed to produce the pattern
        List<Integer> indices = findIndicesForPattern(array, pattern);
        
        if (indices == null) {
            return "Pattern cannot be formed from array";
        }
        
        StringBuilder result = new StringBuilder();
        for (int index : indices) {
            result.append(array.charAt(index));
        }
        
        return result.toString();
    }
    
    /**
     * Finds the indices in the array that would produce the given pattern
     */
    private static List<Integer> findIndicesForPattern(String array, String pattern) {
        List<Integer> indices = new ArrayList<>();
        Map<Character, Queue<Integer>> charPositions = new HashMap<>();
        
        // Build map of character positions
        for (int i = 0; i < array.length(); i++) {
            char c = array.charAt(i);
            charPositions.computeIfAbsent(c, k -> new LinkedList<>()).offer(i);
        }
        
        // For each character in pattern, get next available position
        for (char c : pattern.toCharArray()) {
            if (!charPositions.containsKey(c) || charPositions.get(c).isEmpty()) {
                return null; // Pattern cannot be formed
            }
            indices.add(charPositions.get(c).poll());
        }
        
        return indices;
    }
    
    /**
     * Custom iterator class that iterates through array in specified order
     */
    static class CustomOrderIterator implements Iterator<Character> {
        private final String array;
        private final List<Integer> orderIndices;
        private int currentPosition;
        
        public CustomOrderIterator(String array, String pattern) {
            this.array = array;
            this.orderIndices = findIndicesForPattern(array, pattern);
            this.currentPosition = 0;
        }
        
        @Override
        public boolean hasNext() {
            return orderIndices != null && currentPosition < orderIndices.size();
        }
        
        @Override
        public Character next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array.charAt(orderIndices.get(currentPosition++));
        }
    }
    
    /**
     * Iterate using custom iterator
     */
    public static String iterateWithCustomIterator(String array, String pattern) {
        CustomOrderIterator iterator = new CustomOrderIterator(array, pattern);
        StringBuilder result = new StringBuilder();
        
        while (iterator.hasNext()) {
            result.append(iterator.next());
        }
        
        return result.toString();
    }
    
    /**
     * Method to visualize the iteration process
     */
    public static void visualizeIteration(String array, String pattern) {
        System.out.println("\n=== Iteration Visualization ===");
        System.out.println("Original array: " + array);
        System.out.println("Array indices:  " + "0123456789".substring(0, array.length()));
        System.out.println("Target pattern: " + pattern);
        
        List<Integer> indices = findIndicesForPattern(array, pattern);
        if (indices == null) {
            System.out.println("Cannot form pattern from array!");
            return;
        }
        
        System.out.println("\nIteration steps:");
        for (int i = 0; i < pattern.length(); i++) {
            int index = indices.get(i);
            char c = array.charAt(index);
            System.out.printf("Step %d: Visit index %d -> '%c' (matches pattern[%d] = '%c')\n",
                    i + 1, index, c, i, pattern.charAt(i));
        }
        
        System.out.println("\nArray visualization with visit order:");
        for (int i = 0; i < array.length(); i++) {
            System.out.print(array.charAt(i) + " ");
        }
        System.out.println();
        
        for (int i = 0; i < array.length(); i++) {
            int visitOrder = indices.indexOf(i);
            if (visitOrder != -1) {
                System.out.print((visitOrder + 1) + " ");
            } else {
                System.out.print("- ");
            }
        }
        System.out.println("\n");
    }
    
    /**
     * Method to handle multiple patterns
     */
    public static Map<String, String> iterateMultiplePatterns(String array, List<String> patterns) {
        Map<String, String> results = new HashMap<>();
        
        for (String pattern : patterns) {
            String result = iterateByPattern(array, pattern);
            results.put(pattern, result);
        }
        
        return results;
    }
    
    public static void main(String[] args) {
        String array = "gctacbadc";
        String pattern = "tadcag";
        
        System.out.println("=== Custom Array Iteration ===");
        System.out.println("Array: " + array);
        System.out.println("Pattern: " + pattern);
        System.out.println();
        
        // Method 1: Iterate by pattern
        System.out.println("Method 1 - Iterate by pattern:");
        String result1 = iterateByPattern(array, pattern);
        System.out.println("Result: " + result1);
        System.out.println("Matches pattern: " + result1.equals(pattern));
        
        // Method 2: Iterate by indices
        System.out.println("\nMethod 2 - Iterate by indices:");
        String result2 = iterateByIndices(array, pattern);
        System.out.println("Result: " + result2);
        System.out.println("Matches pattern: " + result2.equals(pattern));
        
        // Method 3: Using custom iterator
        System.out.println("\nMethod 3 - Using custom iterator:");
        String result3 = iterateWithCustomIterator(array, pattern);
        System.out.println("Result: " + result3);
        System.out.println("Matches pattern: " + result3.equals(pattern));
        
        // Visualize the iteration
        visualizeIteration(array, pattern);
        
        // Test with different patterns
        System.out.println("=== Testing Multiple Patterns ===");
        List<String> patterns = Arrays.asList(
            "gcc",     // Multiple same characters
            "cat",     // Simple pattern
            "bad",     // Another pattern
            "xyz"      // Pattern that cannot be formed
        );
        
        Map<String, String> multiResults = iterateMultiplePatterns(array, patterns);
        for (Map.Entry<String, String> entry : multiResults.entrySet()) {
            System.out.printf("Pattern '%s' -> Result: '%s'\n", 
                entry.getKey(), entry.getValue());
        }
        
        // Edge cases
        System.out.println("\n=== Edge Cases ===");
        System.out.println("Empty pattern: " + iterateByPattern(array, ""));
        System.out.println("Single character: " + iterateByPattern(array, "g"));
        System.out.println("All characters: " + iterateByPattern("abc", "abc"));
        
        // Demonstrating index mapping for the original example
        System.out.println("\n=== Index Mapping for Original Example ===");
        List<Integer> originalIndices = findIndicesForPattern(array, pattern);
        System.out.println("To get '" + pattern + "' from '" + array + "':");
        System.out.println("Visit indices in this order: " + originalIndices);
        System.out.print("Characters at those indices: ");
        for (int idx : originalIndices) {
            System.out.print(array.charAt(idx));
        }
        System.out.println();
    }
}