package com.demo.linkedlist;

import com.demo.ListNode;

import java.util.*;

/**
 * Problem: Merge Two Sorted Lists
 * 
 * MNEMONIC: "ZIPPER MERGE - Interweaving two sorted chains!"
 * 🩶 Like closing a zipper, choosing the smaller tooth each time
 * 
 * PROBLEM: Merge two sorted linked lists into one sorted list.
 * Example: 1 -> 2 -> 4 and 1 -> 3 -> 4 => 1 -> 1 -> 2 -> 3 -> 4 -> 4
 * 
 * TECHNIQUE: Two Pointers Comparison
 * - Compare current nodes of both lists
 * - Attach smaller node to result
 * - Move pointer of chosen list forward
 * - Handle remaining nodes when one list ends
 * 
 * VISUALIZATION:
 * l1: 1 -> 2 -> 4
 * l2: 1 -> 3 -> 4
 * 
 * Step 1: 1 <= 1, choose l1's 1
 * Step 2: 2 > 1, choose l2's 1
 * Step 3: 2 < 3, choose l1's 2
 * Step 4: 4 > 3, choose l2's 3
 * Step 5: 4 == 4, choose l1's 4
 * Step 6: only l2's 4 remains
 * 
 * RECURSIVE vs ITERATIVE:
 * - Iterative: O(1) space, more efficient
 * - Recursive: Cleaner code, O(n) stack space
 * 
 * Time Complexity: O(m + n)
 * Space Complexity: O(1) iterative, O(m + n) recursive
 */
public class MergeTwoSortedLists {
    
    /**
     * Merge two sorted lists iteratively
     * 
     * REMEMBER: "ZIPPER TEETH"
     * Choose the smaller tooth each time to close the zipper
     * 
     * @param chain1 first sorted list
     * @param chain2 second sorted list
     * @return merged sorted list
     */
    public static ListNode mergeWithZipper_IterativeMethod(ListNode chain1, ListNode chain2) {
        ListNode zipperStart = new ListNode(0); // Dummy head for easier handling
        ListNode zipperPuller = zipperStart; // Current position of zipper
        
        // Pull the zipper by choosing smaller teeth
        while (chain1 != null && chain2 != null) {
            if (chain1.val <= chain2.val) {
                // Chain1's tooth is smaller (or equal), attach it
                zipperPuller.next = chain1;
                chain1 = chain1.next;
            } else {
                // Chain2's tooth is smaller, attach it
                zipperPuller.next = chain2;
                chain2 = chain2.next;
            }
            zipperPuller = zipperPuller.next; // Move zipper forward
        }
        
        // Attach any remaining teeth (one chain is exhausted)
        zipperPuller.next = (chain1 != null) ? chain1 : chain2;
        
        return zipperStart.next; // Skip dummy head
    }
    
    /**
     * Merge two sorted lists recursively
     * 
     * REMEMBER: "RECURSIVE WEAVING"
     * Let recursion handle the rest after choosing current node
     * 
     * @param thread1 first sorted list
     * @param thread2 second sorted list
     * @return merged sorted list
     */
    public static ListNode mergeWithWeaving_RecursiveMethod(ListNode thread1, ListNode thread2) {
        // Base cases: if one thread ends, return the other
        if (thread1 == null) return thread2;
        if (thread2 == null) return thread1;
        
        // Choose the smaller thread and weave the rest
        if (thread1.val <= thread2.val) {
            thread1.next = mergeWithWeaving_RecursiveMethod(thread1.next, thread2);
            return thread1;
        } else {
            thread2.next = mergeWithWeaving_RecursiveMethod(thread1, thread2.next);
            return thread2;
        }
    }
    
    /**
     * Visualize the merging process
     */
    private static void visualizeMerging(ListNode l1, ListNode l2) {
        System.out.println("=== 🩶 ZIPPER MERGE VISUALIZATION ===");
        
        System.out.println("\nOriginal lists:");
        System.out.print("Chain 1: ");
        ListNode.printList(l1);
        System.out.print("Chain 2: ");
        ListNode.printList(l2);

        // Create copies for step-by-step
        ListNode copy1 = copyList(l1);
        ListNode copy2 = copyList(l2);

        System.out.println("\nZipper merging process:");
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int step = 1;
        
        while (copy1 != null && copy2 != null) {
            System.out.printf("\nStep %d: Compare %d and %d", step++, copy1.val, copy2.val);
            
            if (copy1.val <= copy2.val) {
                System.out.printf(" → Choose %d from Chain 1\n", copy1.val);
                current.next = new ListNode(copy1.val);
                copy1 = copy1.next;
            } else {
                System.out.printf(" → Choose %d from Chain 2\n", copy2.val);
                current.next = new ListNode(copy2.val);
                copy2 = copy2.next;
            }
            current = current.next;
            
            System.out.print("Merged so far: ");
            ListNode.printList(dummy.next);
        }
        
        // Handle remaining
        if (copy1 != null || copy2 != null) {
            System.out.printf("\nStep %d: Attach remaining from Chain %s\n", 
                            step, copy1 != null ? "1" : "2");
            current.next = copy1 != null ? copy1 : copy2;
        }
        
        System.out.print("\nFinal merged list: ");
        ListNode.printList(dummy.next);
    }

    // Helper to create a linked list from varargs
    public static ListNode createList(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int v : vals) {
            current.next = new ListNode(v);
            current = current.next;
        }
        return dummy.next;
    }

    // Helper to print a linked list
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) System.out.print(" -> ");
            head = head.next;
        }
        System.out.println();
    }

    // Helper to copy a list
    private static ListNode copyList(ListNode head) {
        if (head == null) return null;
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (head != null) {
            current.next = new ListNode(head.val);
            current = current.next;
            head = head.next;
        }
        return dummy.next;
    }
    
    /**
     * Compare iterative vs recursive approaches
     */
    private static void compareApproaches() {
        System.out.println("\n=== 🔄 ITERATIVE vs RECURSIVE ===");
        
        System.out.println("\nITERATIVE (Zipper) Approach:");
        System.out.println("• Uses while loop and pointers");
        System.out.println("• O(1) space complexity");
        System.out.println("• No risk of stack overflow");
        System.out.println("• More code but efficient");
        
        System.out.println("\nRECURSIVE (Weaving) Approach:");
        System.out.println("• Elegant and concise");
        System.out.println("• O(m+n) space for call stack");
        System.out.println("• Risk of stack overflow for large lists");
        System.out.println("• Natural problem decomposition");
        
        System.out.println("\nVisual comparison:");
        System.out.println("Iterative: [1] [3] → pick 1 → [3] [4] → pick 3...");
        System.out.println("           ↑   ↑                   ↑   ↑");
        System.out.println("          p1  p2                  p1  p2");
        
        System.out.println("\nRecursive: merge([1,3], [2,4])");
        System.out.println("           = 1 -> merge([3], [2,4])");
        System.out.println("           = 1 -> 2 -> merge([3], [4])");
        System.out.println("           = 1 -> 2 -> 3 -> merge([], [4])");
        System.out.println("           = 1 -> 2 -> 3 -> 4");
    }
    
    /**
     * Show edge cases
     */
    private static void demonstrateEdgeCases() {
        System.out.println("\n=== 🅴 EDGE CASES ===");
        
        // Empty lists
        System.out.println("\n1. Empty lists:");
        System.out.print("Empty + [1,2,3]: ");
        ListNode.printList(mergeWithZipper_IterativeMethod(null, ListNode.createList(1, 2, 3)));

        // Single elements
        System.out.println("\n2. Single elements:");
        ListNode single1 = ListNode.createList(5);
        ListNode single2 = ListNode.createList(2);
        System.out.print("[5] + [2]: ");
        ListNode.printList(mergeWithZipper_IterativeMethod(single1, single2));

        // All elements from one list
        System.out.println("\n3. Non-overlapping ranges:");
        ListNode low = ListNode.createList(1, 2, 3);
        ListNode high = ListNode.createList(7, 8, 9);
        System.out.print("[1,2,3] + [7,8,9]: ");
        ListNode.printList(mergeWithZipper_IterativeMethod(low, high));

        // Duplicates
        System.out.println("\n4. Many duplicates:");
        ListNode dup1 = ListNode.createList(1, 1, 1);
        ListNode dup2 = ListNode.createList(1, 1, 1);
        System.out.print("[1,1,1] + [1,1,1]: ");
        ListNode.printList(mergeWithZipper_IterativeMethod(dup1, dup2));
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("🩶 MERGE TWO SORTED LISTS - Zipper Merge!\n");
        
        // Test 1: Standard example
        System.out.println("Test 1 - Standard merge:");
        ListNode l1 = ListNode.createList(1, 2, 4);
        ListNode l2 = ListNode.createList(1, 3, 4);
        visualizeMerging(l1, l2);
        
        // Test 2: Different lengths
        System.out.println("\nTest 2 - Different lengths:");
        ListNode short1 = ListNode.createList(1, 5);
        ListNode long1 = ListNode.createList(2, 3, 4, 6, 7);
        visualizeMerging(short1, long1);
        
        // Test 3: Edge cases
        demonstrateEdgeCases();
        
        // Test 4: Compare approaches
        compareApproaches();
        
        // Test 5: Performance comparison
        System.out.println("\nTest 5 - Performance comparison:");
        int size = 10000;
        ListNode big1 = createSortedList(size, 1);
        ListNode big2 = createSortedList(size, 2);

        long start1 = System.nanoTime();
        ListNode result1 = mergeWithZipper_IterativeMethod(copyList(big1), copyList(big2));
        long time1 = System.nanoTime() - start1;
        
        // For recursive, use smaller size to avoid stack overflow
        ListNode small1 = createSortedList(1000, 1);
        ListNode small2 = createSortedList(1000, 2);

        long start2 = System.nanoTime();
        ListNode result2 = mergeWithWeaving_RecursiveMethod(small1, small2);
        long time2 = System.nanoTime() - start2;
        
        System.out.printf("Iterative (%d nodes): %.3f ms\n", size * 2, time1 / 1000000.0);
        System.out.printf("Recursive (%d nodes): %.3f ms\n", 2000, time2 / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- ZIPPER MERGE: Choose smaller tooth each time");
        System.out.println("- Two pointers moving through lists");
        System.out.println("- Don't forget to attach remaining nodes");
        System.out.println("- Iterative = efficient, Recursive = elegant");
        System.out.println("- Works because input lists are already sorted!");
    }
    
    private static ListNode createSortedList(int size, int start) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        for (int i = 0; i < size; i++) {
            current.next = new ListNode(start + i * 3);
            current = current.next;
        }
        
        return dummy.next;
    }
}