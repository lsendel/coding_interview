package com.demo.codesignal;

import java.util.*;

/**
 * Problem: Remove Duplicates from Sorted Array II
 * 
 * MNEMONIC: "DUPLICATE DUO - Keep at most two of each kind!"
 * 🎯 Like a party bouncer who allows max 2 people with same name
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(1) - constant extra space
 */
public class DuplicateDuo_RemoveDuplicatesII {
    
    public static int removeDuplicates(int[] nums) {
        if (nums.length <= 2) return nums.length;
        
        int writeIndex = 2;
        
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] != nums[writeIndex - 2]) {
                nums[writeIndex] = nums[i];
                writeIndex++;
            }
        }
        
        return writeIndex;
    }
    
    public static void main(String[] args) {
        System.out.println("🎯 REMOVE DUPLICATES II - Duplicate Duo!\n");
        
        int[] test = {1, 1, 1, 2, 2, 3};
        System.out.println("Input: " + Arrays.toString(test));
        
        int newLength = removeDuplicates(test);
        System.out.print("Output: [");
        for (int i = 0; i < newLength; i++) {
            System.out.print(test[i]);
            if (i < newLength - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("New length: " + newLength);
        System.out.println("Expected: [1, 1, 2, 2, 3]");
    }
}