package com.demo.binarysearch;

import java.util.*;

/**
 * Problem: Search a 2D Matrix
 * 
 * MNEMONIC: "BOOK OF PAGES - Read a book page by page!"
 * 📖 Like a book where each page has sorted words, and pages are in order
 * 
 * PROBLEM: Search for target in m x n matrix where:
 * - Each row is sorted
 * - First element of each row > last element of previous row
 * 
 * TECHNIQUE: Treat 2D as 1D - "Flatten the Book"
 * - Matrix is like a book with sorted pages
 * - Can read it as one long sorted story
 * - Convert between page number and row/column
 * 
 * VISUALIZATION:
 * Matrix (Book):          As One Long Story:
 * [1,  3,  5,  7]   →    [1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
 * [10, 11, 16, 20]        ↑
 * [23, 30, 34, 60]        Position 0,1,2,3,4...
 * 
 * COORDINATE CONVERSION:
 * - Story position = row * columns + col
 * - Row = position / columns
 * - Col = position % columns
 * 
 * Time Complexity: O(log(m*n))
 * Space Complexity: O(1)
 */
public class SearchMatrix2D {
    
    /**
     * Search the book of pages for a word
     * 
     * REMEMBER: "BOOK WITH NUMBERED WORDS"
     * - Each page has sorted words
     * - Pages are in order too
     * - Read as one long story!
     * 
     * @param bookPages 2D matrix (pages of the book)
     * @param targetWord value to find
     * @return true if word found in book
     */
    public static boolean searchMatrix_BookOfPages(int[][] bookPages, int targetWord) {
        if (bookPages == null || bookPages.length == 0 || bookPages[0].length == 0) {
            return false;
        }
        
        int totalRows = bookPages.length;
        int totalColumns = bookPages[0].length;
        
        // Treat the book as one long story
        int firstWord = 0;
        int lastWord = totalRows * totalColumns - 1;
        
        while (firstWord <= lastWord) {
            int middleWordPosition = firstWord + (lastWord - firstWord) / 2;
            
            // Convert story position to page (row) and word position (column)
            int pageNumber = middleWordPosition / totalColumns;
            int wordOnPage = middleWordPosition % totalColumns;
            
            int currentWord = bookPages[pageNumber][wordOnPage];
            
            if (currentWord == targetWord) {
                return true; // Found the word!
            } else if (currentWord < targetWord) {
                // Word is later in the story
                firstWord = middleWordPosition + 1;
            } else {
                // Word is earlier in the story
                lastWord = middleWordPosition - 1;
            }
        }
        
        return false; // Word not in book
    }
    
    /**
     * Alternative: Two binary searches
     * First find the row, then search within row
     */
    public static boolean searchMatrix_TwoSearches(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        // First: Find potential row
        int targetRow = findPotentialRow(matrix, target);
        if (targetRow == -1) {
            return false;
        }
        
        // Second: Search within that row
        return binarySearchRow(matrix[targetRow], target);
    }
    
    private static int findPotentialRow(int[][] matrix, int target) {
        int top = 0;
        int bottom = matrix.length - 1;
        
        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;
            
            if (matrix[mid][0] <= target && 
                target <= matrix[mid][matrix[mid].length - 1]) {
                return mid; // Target could be in this row
            } else if (target < matrix[mid][0]) {
                bottom = mid - 1;
            } else {
                top = mid + 1;
            }
        }
        
        return -1;
    }
    
    private static boolean binarySearchRow(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (row[mid] == target) {
                return true;
            } else if (row[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
    
    /**
     * Get the value at a given 1D position
     */
    private static int getValueAt1DPosition(int[][] matrix, int position) {
        int cols = matrix[0].length;
        int row = position / cols;
        int col = position % cols;
        return matrix[row][col];
    }
    
    /**
     * Visualization helper - shows the book concept
     */
    private static void visualizeBookSearch(int[][] matrix, int target) {
        System.out.println("=== 📖 BOOK OF PAGES VISUALIZATION ===");
        
        // Show the book
        System.out.println("The Book (2D Matrix):");
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("Page %d: ", i);
            System.out.println(Arrays.toString(matrix[i]));
        }
        
        // Show as one story
        System.out.println("\nAs One Long Story (1D View):");
        System.out.print("[");
        int totalElements = matrix.length * matrix[0].length;
        for (int i = 0; i < totalElements; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(getValueAt1DPosition(matrix, i));
        }
        System.out.println("]");
        
        // Show search process
        System.out.println("\nSearching for word: " + target);
        visualizeSearchSteps(matrix, target);
    }
    
    private static void visualizeSearchSteps(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int left = 0;
        int right = rows * cols - 1;
        int step = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int row = mid / cols;
            int col = mid % cols;
            int value = matrix[row][col];
            
            step++;
            System.out.printf("\nStep %d:\n", step);
            System.out.printf("  Story range: positions %d-%d\n", left, right);
            System.out.printf("  Middle position: %d\n", mid);
            System.out.printf("  → Page %d, Word %d = %d\n", row, col, value);
            
            if (value == target) {
                System.out.println("  ✓ Found the word!");
                return;
            } else if (value < target) {
                System.out.println("  → Word comes later in story");
                left = mid + 1;
            } else {
                System.out.println("  → Word comes earlier in story");
                right = mid - 1;
            }
        }
        
        System.out.println("\n✗ Word not found in book!");
    }
    
    /**
     * Show position conversions
     */
    private static void demonstrateConversions(int rows, int cols) {
        System.out.println("\n📐 Position Conversion Examples:");
        System.out.printf("Book has %d pages, %d words per page\n", rows, cols);
        System.out.println("\nStory Position → Page & Word:");
        
        for (int pos = 0; pos < Math.min(10, rows * cols); pos++) {
            int row = pos / cols;
            int col = pos % cols;
            System.out.printf("  Position %d → Page %d, Word %d\n", pos, row, col);
        }
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("📖 SEARCH 2D MATRIX - Book of Pages!\n");
        
        // Test 1: Basic example
        System.out.println("Test 1 - Search in a small book:");
        int[][] book1 = {
            {1,  3,  5,  7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int word1 = 11;
        
        boolean found1 = searchMatrix_BookOfPages(book1, word1);
        System.out.println("Looking for word: " + word1);
        System.out.println("Found: " + found1);
        System.out.println();
        
        // Test 2: Visualization
        System.out.println("Test 2 - Visual search process:");
        visualizeBookSearch(book1, word1);
        System.out.println();
        
        // Test 3: Position conversions
        System.out.println("Test 3 - Understanding conversions:");
        demonstrateConversions(3, 4);
        System.out.println();
        
        // Test 4: Various search cases
        System.out.println("Test 4 - Different search scenarios:");
        int[] searchWords = {1, 7, 10, 23, 60, 0, 65, 15};
        
        for (int word : searchWords) {
            boolean found = searchMatrix_BookOfPages(book1, word);
            System.out.printf("  Word %2d: %s\n", word, found ? "Found ✓" : "Not found ✗");
        }
        System.out.println();
        
        // Test 5: Edge cases
        System.out.println("Test 5 - Edge cases:");
        
        // Single element
        int[][] single = {{42}};
        System.out.println("Single page [42], find 42: " + 
                         searchMatrix_BookOfPages(single, 42));
        System.out.println("Single page [42], find 10: " + 
                         searchMatrix_BookOfPages(single, 10));
        
        // Single row
        int[][] singleRow = {{1, 3, 5, 7, 9}};
        System.out.println("Single row book, find 5: " + 
                         searchMatrix_BookOfPages(singleRow, 5));
        
        // Single column
        int[][] singleCol = {{1}, {3}, {5}, {7}};
        System.out.println("Single column book, find 3: " + 
                         searchMatrix_BookOfPages(singleCol, 3));
        
        // Empty
        int[][] empty = {};
        System.out.println("Empty book, find anything: " + 
                         searchMatrix_BookOfPages(empty, 1));
        System.out.println();
        
        // Test 6: Compare methods
        System.out.println("Test 6 - Compare approaches:");
        int[][] testBook = {
            {1,  4,  7,  11},
            {15, 18, 21, 24},
            {28, 31, 34, 37},
            {41, 44, 47, 50}
        };
        int testWord = 31;
        
        long start1 = System.nanoTime();
        boolean result1 = searchMatrix_BookOfPages(testBook, testWord);
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        boolean result2 = searchMatrix_TwoSearches(testBook, testWord);
        long time2 = System.nanoTime() - start2;
        
        System.out.println("1D approach: " + result1 + " (Time: " + time1 + " ns)");
        System.out.println("Two searches: " + result2 + " (Time: " + time2 + " ns)");
        System.out.println();
        
        // Test 7: Large matrix
        System.out.println("Test 7 - Large book performance:");
        int rows = 1000;
        int cols = 1000;
        int[][] largeBook = new int[rows][cols];
        
        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                largeBook[i][j] = num;
                num += 2; // Ensure sorted order with gaps
            }
        }
        
        int targetInLarge = 999999; // Middle of range
        long start = System.nanoTime();
        boolean foundInLarge = searchMatrix_BookOfPages(largeBook, targetInLarge);
        long time = System.nanoTime() - start;
        
        System.out.printf("Book size: %d pages × %d words = %d total\n", 
                         rows, cols, rows * cols);
        System.out.printf("Searching for: %d\n", targetInLarge);
        System.out.printf("Found: %s\n", foundInLarge);
        System.out.printf("Time: %.3f ms\n", time / 1000000.0);
        System.out.printf("Operations: ~%d (log₂(%d) = %.1f)\n", 
                         (int)(Math.log(rows * cols) / Math.log(2)), 
                         rows * cols, 
                         Math.log(rows * cols) / Math.log(2));
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- BOOK OF PAGES: 2D matrix is like a book");
        System.out.println("- Read it as ONE LONG STORY (1D array)");
        System.out.println("- Position = row × columns + col");
        System.out.println("- Row = position / columns");
        System.out.println("- Col = position % columns");
        System.out.println("- Single binary search on flattened view!");
    }
}