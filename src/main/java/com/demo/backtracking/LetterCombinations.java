package com.demo.backtracking;

import java.util.*;

/**
 * Problem: Letter Combinations of a Phone Number
 * 
 * MNEMONIC: "PHONE TYPING - Like texting on old Nokia!"
 * 📱 Remember T9 keyboards? Press 2 for ABC, 3 for DEF...
 * 
 * PROBLEM: Given a string of digits 2-9, return all possible letter combinations
 * that the number could represent (like on a phone keypad).
 * Example: "23" → ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 
 * TECHNIQUE: Backtracking with "Type-Delete-Type" Pattern
 * - Type a letter (choose)
 * - Continue typing next digit's letters (explore)
 * - Delete to try different letter (backtrack)
 * 
 * VISUALIZATION for "23":
 * Typing: ""
 * Press 2: Type 'a' → "a"
 *   Press 3: Type 'd' → "ad" ✓ (save)
 *            Delete → "a"
 *            Type 'e' → "ae" ✓ (save)
 *            Delete → "a"
 *            Type 'f' → "af" ✓ (save)
 *   Delete 'a' → ""
 * Press 2: Type 'b' → "b"
 *   (Continue pattern...)
 * 
 * Time Complexity: O(4^n) worst case (digits 7,9 have 4 letters)
 * Space Complexity: O(n) for recursion depth
 */
public class LetterCombinations {
    
    // Phone keypad mapping - just like old phones!
    private static final String[] PHONE_KEYPAD = {
        "",     // 0 (no letters)
        "",     // 1 (no letters)
        "abc",  // 2 (ABC key)
        "def",  // 3 (DEF key)
        "ghi",  // 4 (GHI key)
        "jkl",  // 5 (JKL key)
        "mno",  // 6 (MNO key)
        "pqrs", // 7 (PQRS key)
        "tuv",  // 8 (TUV key)
        "wxyz"  // 9 (WXYZ key)
    };
    
    /**
     * Find all letter combinations for phone number
     * 
     * REMEMBER: "TYPE-DELETE-TYPE" - Like texting on T9!
     * Type letter, continue typing, delete to try next
     * 
     * @param phoneNumber string of digits 2-9
     * @return all possible text messages
     */
    public static List<String> letterCombinations_PhoneTyping(String phoneNumber) {
        List<String> allTextMessages = new ArrayList<>();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return allTextMessages;
        }
        
        StringBuilder currentMessage = new StringBuilder();
        typeAllCombinations(phoneNumber, 0, currentMessage, allTextMessages);
        
        return allTextMessages;
    }
    
    /**
     * Recursive helper - simulates typing on phone
     * 
     * @param phoneNumber the digits to type
     * @param digitPosition which digit we're typing
     * @param currentMessage message typed so far
     * @param allMessages collection of all messages
     */
    private static void typeAllCombinations(String phoneNumber, int digitPosition,
                                          StringBuilder currentMessage, 
                                          List<String> allMessages) {
        // Base case: finished typing all digits
        if (digitPosition == phoneNumber.length()) {
            allMessages.add(currentMessage.toString());
            return;
        }
        
        // Get letters for current digit key
        char digitKey = phoneNumber.charAt(digitPosition);
        String lettersOnKey = PHONE_KEYPAD[digitKey - '0'];
        
        // Try typing each letter on this key
        for (char letter : lettersOnKey.toCharArray()) {
            // TYPE: Press the letter
            currentMessage.append(letter);
            
            // CONTINUE: Type next digit's letters
            typeAllCombinations(phoneNumber, digitPosition + 1, 
                              currentMessage, allMessages);
            
            // DELETE: Backspace to try next letter
            currentMessage.deleteCharAt(currentMessage.length() - 1);
        }
    }
    
    /**
     * Visualization helper - shows the typing process
     */
    private static void visualizeTypingProcess(String digits) {
        System.out.println("=== 📱 PHONE TYPING VISUALIZATION ===");
        System.out.println("Phone number: " + digits);
        System.out.println("\nPhone keypad reminder:");
        for (int i = 2; i <= 9; i++) {
            System.out.printf("Key %d: %s\n", i, PHONE_KEYPAD[i]);
        }
        System.out.println("\nTyping process:");
        
        List<String> steps = new ArrayList<>();
        visualizeHelper(digits, 0, new StringBuilder(), steps, "");
        
        for (String step : steps) {
            System.out.println(step);
        }
    }
    
    private static void visualizeHelper(String digits, int pos, StringBuilder current,
                                      List<String> steps, String indent) {
        if (pos == digits.length()) {
            steps.add(indent + "Complete message: \"" + current + "\" ✓");
            return;
        }
        
        char digit = digits.charAt(pos);
        String letters = PHONE_KEYPAD[digit - '0'];
        steps.add(indent + "Press key " + digit + " (options: " + letters + ")");
        
        for (char letter : letters.toCharArray()) {
            current.append(letter);
            steps.add(indent + "  Type '" + letter + "' → \"" + current + "\"");
            
            visualizeHelper(digits, pos + 1, current, steps, indent + "    ");
            
            current.deleteCharAt(current.length() - 1);
            if (pos < digits.length() - 1) {
                steps.add(indent + "  Delete '" + letter + "' → \"" + current + "\"");
            }
        }
    }
    
    /**
     * Alternative: Iterative approach using queue
     */
    public static List<String> letterCombinations_QueueMethod(String digits) {
        if (digits == null || digits.isEmpty()) {
            return new ArrayList<>();
        }
        
        LinkedList<String> messageQueue = new LinkedList<>();
        messageQueue.add("");
        
        for (char digit : digits.toCharArray()) {
            String letters = PHONE_KEYPAD[digit - '0'];
            int queueSize = messageQueue.size();
            
            // Process all current messages
            for (int i = 0; i < queueSize; i++) {
                String currentMessage = messageQueue.poll();
                
                // Add each possible letter
                for (char letter : letters.toCharArray()) {
                    messageQueue.add(currentMessage + letter);
                }
            }
        }
        
        return new ArrayList<>(messageQueue);
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("📱 LETTER COMBINATIONS - Phone Typing Challenge!\n");
        
        // Test 1: Basic example
        System.out.println("Test 1 - Type '23' on phone:");
        String phoneNum1 = "23";
        List<String> messages1 = letterCombinations_PhoneTyping(phoneNum1);
        System.out.println("All possible messages: " + messages1);
        System.out.println("Total combinations: " + messages1.size());
        System.out.println();
        
        // Test 2: Visualization
        System.out.println("Test 2 - Detailed typing process:");
        visualizeTypingProcess("23");
        System.out.println();
        
        // Test 3: Single digit
        System.out.println("Test 3 - Single digit '2':");
        System.out.println("Messages: " + letterCombinations_PhoneTyping("2"));
        System.out.println();
        
        // Test 4: Longer number
        System.out.println("Test 4 - Longer number '234':");
        List<String> messages4 = letterCombinations_PhoneTyping("234");
        System.out.println("First 10 messages: " + messages4.subList(0, Math.min(10, messages4.size())));
        System.out.println("Total combinations: " + messages4.size());
        System.out.println("Formula: 3 × 3 × 3 = 27");
        System.out.println();
        
        // Test 5: Compare methods
        System.out.println("Test 5 - Compare recursive vs iterative:");
        String testNum = "237";
        List<String> recursive = letterCombinations_PhoneTyping(testNum);
        List<String> iterative = letterCombinations_QueueMethod(testNum);
        
        System.out.println("Recursive count: " + recursive.size());
        System.out.println("Iterative count: " + iterative.size());
        System.out.println("Results match: " + recursive.equals(iterative));
        System.out.println();
        
        // Test 6: Edge cases
        System.out.println("Test 6 - Edge cases:");
        System.out.println("Empty string: " + letterCombinations_PhoneTyping(""));
        System.out.println("Digit with 4 letters '7': " + letterCombinations_PhoneTyping("7"));
        System.out.println();
        
        // Test 7: Real world example
        System.out.println("Test 7 - Real phone words:");
        System.out.println("'4663' could spell 'GOOD' or 'HOME' or...");
        List<String> words = letterCombinations_PhoneTyping("4663");
        System.out.println("Total possibilities: " + words.size());
        System.out.println("Some combinations: " + words.subList(0, 5) + "...");
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- Like typing on old T9 phone keyboards");
        System.out.println("- TYPE letter → CONTINUE typing → DELETE to try next");
        System.out.println("- Each digit has 3-4 letter choices");
        System.out.println("- Total combinations = product of choices");
        System.out.println("- Classic backtracking: choose-explore-unchoose");
    }
}