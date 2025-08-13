package com.demo.codesignal;

import java.util.*;

/**
 * Problem: Remove Duplicates from Sorted Array (In-Place)
 * 
 * MNEMONIC: "UNIQUE KEEPER - Keep only the first of each kind!"
 * 🎯 Like a bouncer who only lets in one person with each name
 * 
 * Time Complexity: O(n) where n is the length of the array
 * Space Complexity: O(1) - constant extra space
 */
public class UniqueKeeper_RemoveDuplicates {
    
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        
        int writePosition = 1;
        
        for (int readPosition = 1; readPosition < nums.length; readPosition++) {
            if (nums[readPosition] != nums[writePosition - 1]) {
                nums[writePosition] = nums[readPosition];
                writePosition++;
            }
        }
        
        return writePosition;
    }
    
    public static void main(String[] args) {
        System.out.println("🎯 REMOVE DUPLICATES - Unique Keeper!\n");
        
        int[] test = {1, 1, 2, 2, 3, 3, 3, 4, 5, 5};
        System.out.println("Input: " + Arrays.toString(test));
        
        int newLength = removeDuplicates(test);
        System.out.print("Output: [");
        for (int i = 0; i < newLength; i++) {
            System.out.print(test[i]);
            if (i < newLength - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("New length: " + newLength);
    }
}