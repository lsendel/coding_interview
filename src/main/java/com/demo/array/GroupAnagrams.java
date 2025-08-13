package com.demo.array;

import java.util.*;

/**
 * Problem: Group Anagrams
 * 
 * PROBLEM: Given an array of strings, group anagrams together.
 * Example: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * Answer: [["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]
 * 
 * TECHNIQUE: Hash Map with Sorted String as Key
 * - Sort each string to create a canonical form
 * - Use sorted string as key in hash map
 * - All anagrams will have the same sorted form
 * 
 * ALTERNATIVE TECHNIQUE: Character Count as Key
 * - Count frequency of each character
 * - Use count array as key (e.g., "a2b1t1" for "bat")
 * 
 * VISUALIZATION:
 * "eat" → sort → "aet" → map["aet"] = ["eat"]
 * "tea" → sort → "aet" → map["aet"] = ["eat", "tea"]
 * "tan" → sort → "ant" → map["ant"] = ["tan"]
 * "ate" → sort → "aet" → map["aet"] = ["eat", "tea", "ate"]
 * etc.
 * 
 * WHY SORTING WORKS:
 * - Anagrams have same characters in different order
 * - Sorting puts characters in canonical order
 * - All anagrams produce identical sorted string
 * 
 * Time Complexity: O(n * k log k) where n = number of strings, k = max string length
 * Space Complexity: O(n * k) for storing the groups
 */
public class GroupAnagrams {
    
    /**
     * Main solution using sorted string as key
     * 
     * Time Complexity: O(n * k log k)
     * - n is the number of strings in the input array
     * - k is the maximum length of any string in the array
     * - For each string: sorting takes O(k log k) time
     * - HashMap operations (put/get) are O(1) average case
     * - Total: n strings × O(k log k) sorting = O(n * k log k)
     * 
     * Space Complexity: O(n * k)
     * - HashMap stores all n strings, each of maximum length k
     * - Sorted character arrays for keys: O(n * k) space
     * - Result list contains all n strings: O(n * k) space
     * - Total auxiliary space: O(n * k)
     * 
     * @param strs array of strings
     * @return grouped anagrams
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            // Create canonical form by sorting
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            
            // Group by canonical form
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    /**
     * Alternative: Group Anagrams using Character Count
     * 
     * TECHNIQUE: Use character frequency as key
     * - Count occurrences of each character
     * - Build a unique key from counts
     * - More efficient than sorting for long strings
     * 
     * Time Complexity: O(n * k)
     * - n is the number of strings in input array
     * - k is the maximum length of any string
     * - For each string: iterate through k characters to count frequencies: O(k)
     * - Build key string: iterate through 26 characters (constant): O(26) = O(1)
     * - HashMap operations: O(1) average case
     * - Total: n strings × (O(k) + O(1)) = O(n * k)
     * - This is better than O(n * k log k) when k is large
     * 
     * Space Complexity: O(n * k)
     * - Character count array: O(26) = O(1) per string (reused)
     * - HashMap stores all n strings: O(n * k) for string storage
     * - Key strings: in worst case O(k) length each, n keys total: O(n * k)
     * - Total auxiliary space: O(n * k)
     * 
     * @param strs array of strings
     * @return grouped anagrams
     */
    public static List<List<String>> groupAnagramsCount(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            // Count character frequencies
            int[] count = new int[26];
            for (char c : str.toCharArray()) {
                count[c - 'a']++;
            }
            
            // Build key from counts
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if (count[i] > 0) {
                    keyBuilder.append((char)('a' + i)).append(count[i]);
                }
            }
            String key = keyBuilder.toString();
            
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Basic example
        String[] test1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Test 1 - Input: " + Arrays.toString(test1));
        System.out.println("Sorted Key Method: " + groupAnagrams(test1));
        System.out.println("Count Key Method: " + groupAnagramsCount(test1));
        System.out.println();
        
        // Test case 2: Empty strings
        String[] test2 = {""};
        System.out.println("Test 2 - Input: " + Arrays.toString(test2));
        System.out.println("Result: " + groupAnagrams(test2));
        System.out.println();
        
        // Test case 3: Single character strings
        String[] test3 = {"a"};
        System.out.println("Test 3 - Input: " + Arrays.toString(test3));
        System.out.println("Result: " + groupAnagrams(test3));
        System.out.println();
        
        // Test case 4: No anagrams
        String[] test4 = {"abc", "def", "ghi"};
        System.out.println("Test 4 - Input: " + Arrays.toString(test4));
        System.out.println("Result: " + groupAnagrams(test4));
    }
}