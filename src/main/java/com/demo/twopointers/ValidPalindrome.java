package com.demo.twopointers;

/**
 * Problem: Valid Palindrome
 * 
 * PROBLEM: Check if a string is a palindrome, considering only alphanumeric
 * characters and ignoring cases.
 * Example: "A man, a plan, a canal: Panama" → true
 * 
 * TECHNIQUE: Two Pointers moving towards center
 * - Skip non-alphanumeric characters
 * - Compare characters case-insensitively
 * - Move inward until pointers meet
 * 
 * VISUALIZATION: "A man, a plan, a canal: Panama"
 * After filtering: "amanaplanacanalpanama"
 * left=0('a'), right=20('a') ✓
 * left=1('m'), right=19('m') ✓
 * Continue until pointers meet...
 * 
 * Time Complexity: O(n) - visit each character once
 * Space Complexity: O(1) - no extra space used
 */
public class ValidPalindrome {
    
    /**
     * Check if string is a valid palindrome
     * 
     * Time Complexity: O(n) where n is the length of the string
     *   - Two pointers move from ends toward center, each character visited at most once
     *   - Character.isLetterOrDigit() check: O(1) per character
     *   - Character.toLowerCase() conversion: O(1) per character
     *   - While loops for skipping non-alphanumeric: O(n) total across all iterations
     *   - Overall: O(n) since each character is processed at most a constant number of times
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using two pointer variables (left, right)
     *   - No additional data structures or string modifications
     *   - Character operations don't create new objects (primitives)
     * 
     * @param s input string
     * @return true if palindrome, false otherwise
     */
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            // Compare characters (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != 
                Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Alternative: Check if palindrome after removing at most one character
     * 
     * Time Complexity: O(n) where n is the length of the string
     *   - First pass with two pointers: O(n) to find first mismatch
     *   - When mismatch found, calls isPalindromeRange twice:
     *     - Each call processes at most n-1 characters: O(n)
     *     - Two calls total: 2 * O(n) = O(n)
     *   - Total: O(n) + O(n) = O(n)
     * 
     * Space Complexity: O(1) constant extra space
     *   - Uses two pointer variables plus helper method call
     *   - isPalindromeRange uses O(1) space
     *   - No recursion, so no stack space
     */
    public static boolean isPalindromeWithOneRemoval(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                // Try removing either left or right character
                return isPalindromeRange(s, left + 1, right) || 
                       isPalindromeRange(s, left, right - 1);
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Helper method to check if substring is palindrome
     * 
     * Time Complexity: O(k) where k is the length of the range (right - left + 1)
     *   - Single pass with two pointers moving toward center
     *   - Each character in range visited exactly once
     *   - Character comparison is O(1)
     * 
     * Space Complexity: O(1) constant extra space
     *   - Only using pointer variables
     *   - No additional data structures
     */
    private static boolean isPalindromeRange(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    /**
     * Get the cleaned alphanumeric string for visualization
     * 
     * Time Complexity: O(n) where n is the length of the input string
     *   - Single pass through all characters in the string
     *   - Character.isLetterOrDigit() check: O(1) per character
     *   - Character.toLowerCase() and StringBuilder.append(): O(1) per character
     *   - StringBuilder.toString(): O(m) where m is the cleaned string length
     *   - Total: O(n) + O(m) = O(n) since m ≤ n
     * 
     * Space Complexity: O(n) for the cleaned string
     *   - StringBuilder grows up to size n in worst case (all characters alphanumeric)
     *   - Returned string takes O(m) space where m ≤ n
     *   - Total: O(n) space
     */
    private static String getCleanedString(String s) {
        StringBuilder cleaned = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }
        return cleaned.toString();
    }
    
    /**
     * Visualization helper - shows the algorithm step by step
     * 
     * Time Complexity: O(n) where n is the length of the string
     *   - Calls getCleanedString(): O(n)
     *   - Performs same two-pointer algorithm as isPalindrome(): O(n)
     *   - Additional printing and formatting: O(1) per iteration
     *   - Total: O(n) + O(n) = O(n)
     * 
     * Space Complexity: O(n) for the cleaned string
     *   - getCleanedString() creates O(n) space for cleaned version
     *   - Additional O(1) space for pointer variables and formatting
     */
    private static void visualize(String s) {
        System.out.println("=== Valid Palindrome Visualization ===");
        System.out.println("Original: \"" + s + "\"");
        
        String cleaned = getCleanedString(s);
        System.out.println("Cleaned: \"" + cleaned + "\"");
        System.out.println();
        
        int left = 0;
        int right = s.length() - 1;
        int step = 0;
        
        while (left < right) {
            // Skip non-alphanumeric from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            
            if (left >= right) break;
            
            step++;
            char leftChar = Character.toLowerCase(s.charAt(left));
            char rightChar = Character.toLowerCase(s.charAt(right));
            
            System.out.printf("Step %d: left=%d ('%c'), right=%d ('%c')\n",
                            step, left, s.charAt(left), right, s.charAt(right));
            System.out.printf("  Compare: '%c' vs '%c' ", leftChar, rightChar);
            
            if (leftChar == rightChar) {
                System.out.println("✓ Match");
            } else {
                System.out.println("✗ Mismatch - NOT A PALINDROME");
                return;
            }
            
            left++;
            right--;
        }
        
        System.out.println("\n✓ IS A PALINDROME");
    }
    
    /**
     * Show character-by-character comparison with visual alignment
     * 
     * Time Complexity: O(n) where n is the length of the cleaned string
     *   - getCleanedString(): O(original string length)
     *   - Three loops through cleaned string of length n: 3 * O(n) = O(n)
     *   - Character access and printing: O(1) per character
     *   - Total: O(n)
     * 
     * Space Complexity: O(n) for the cleaned string
     *   - Cleaned string storage: O(n)
     *   - Additional O(1) space for loop variables
     */
    private static void showAlignment(String s) {
        String cleaned = getCleanedString(s);
        int n = cleaned.length();
        
        System.out.println("\nCharacter alignment:");
        System.out.print("Forward:  ");
        for (int i = 0; i < n; i++) {
            System.out.print(cleaned.charAt(i) + " ");
        }
        System.out.println();
        
        System.out.print("Backward: ");
        for (int i = n - 1; i >= 0; i--) {
            System.out.print(cleaned.charAt(i) + " ");
        }
        System.out.println();
        
        System.out.print("Match:    ");
        for (int i = 0; i < n; i++) {
            if (cleaned.charAt(i) == cleaned.charAt(n - 1 - i)) {
                System.out.print("✓ ");
            } else {
                System.out.print("✗ ");
            }
        }
        System.out.println();
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Classic palindrome
        System.out.println("Test 1 - Classic palindrome:");
        String s1 = "A man, a plan, a canal: Panama";
        visualize(s1);
        showAlignment(s1);
        System.out.println();
        
        // Test case 2: Not a palindrome
        System.out.println("Test 2 - Not a palindrome:");
        String s2 = "race a car";
        visualize(s2);
        System.out.println();
        
        // Test case 3: Empty string
        System.out.println("Test 3 - Empty string:");
        String s3 = "";
        System.out.println("\"" + s3 + "\" is palindrome: " + isPalindrome(s3));
        System.out.println();
        
        // Test case 4: Single character
        System.out.println("Test 4 - Single character:");
        String s4 = "a";
        System.out.println("\"" + s4 + "\" is palindrome: " + isPalindrome(s4));
        System.out.println();
        
        // Test case 5: Only non-alphanumeric
        System.out.println("Test 5 - Only non-alphanumeric:");
        String s5 = ".,!?";
        System.out.println("\"" + s5 + "\" is palindrome: " + isPalindrome(s5));
        System.out.println("(Empty after cleaning, considered palindrome)");
        System.out.println();
        
        // Test case 6: Numeric palindrome
        System.out.println("Test 6 - Numeric palindrome:");
        String s6 = "12321";
        System.out.println("\"" + s6 + "\" is palindrome: " + isPalindrome(s6));
        showAlignment(s6);
        System.out.println();
        
        // Test case 7: Mixed case and special characters
        System.out.println("Test 7 - Complex example:");
        String s7 = "Madam, I'm Adam";
        System.out.println("Original: \"" + s7 + "\"");
        System.out.println("Cleaned: \"" + getCleanedString(s7) + "\"");
        System.out.println("Is palindrome: " + isPalindrome(s7));
        System.out.println();
        
        // Test case 8: Palindrome with one removal
        System.out.println("Test 8 - Palindrome with one character removal:");
        String[] testStrings = {"abca", "raceacar", "abc"};
        for (String s : testStrings) {
            System.out.printf("\"%s\" - Can be palindrome by removing at most one char: %s\n",
                            s, isPalindromeWithOneRemoval(s));
        }
        System.out.println();
        
        // Performance test
        System.out.println("Test 9 - Performance:");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50000; i++) {
            sb.append((char)('a' + i % 26));
        }
        String forward = sb.toString();
        String palindrome = forward + new StringBuilder(forward).reverse().toString();
        
        long start = System.nanoTime();
        boolean result = isPalindrome(palindrome);
        long time = System.nanoTime() - start;
        
        System.out.printf("String length: %d\n", palindrome.length());
        System.out.printf("Is palindrome: %s\n", result);
        System.out.printf("Time: %.3f ms\n", time / 1000000.0);
        System.out.println("Complexity: O(n) - linear time!");
    }
}