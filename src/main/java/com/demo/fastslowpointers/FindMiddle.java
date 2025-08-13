package com.demo.fastslowpointers;

/**
 * Problem: Find the middle node of a linked list
 * 
 * PROBLEM: Given a linked list, return the middle node.
 * If there are two middle nodes (even length), return the second one.
 * Example: 1 -> 2 -> 3 -> 4 -> 5, middle is 3
 * Example: 1 -> 2 -> 3 -> 4, middle is 3 (second of two middle nodes)
 * 
 * TECHNIQUE: Fast & Slow Pointers
 * - Slow pointer moves 1 step at a time
 * - Fast pointer moves 2 steps at a time
 * - When fast reaches end, slow is at middle
 * 
 * VISUALIZATION:
 * Odd length: 1 -> 2 -> 3 -> 4 -> 5
 * Step 1: slow=1, fast=1
 * Step 2: slow=2, fast=3
 * Step 3: slow=3, fast=5
 * Fast reaches end, slow is at middle (3)
 * 
 * Even length: 1 -> 2 -> 3 -> 4
 * Step 1: slow=1, fast=1
 * Step 2: slow=2, fast=3
 * Step 3: slow=3, fast=null (fast.next was 4, fast.next.next is null)
 * Fast can't continue, slow is at second middle (3)
 * 
 * KEY INSIGHT: By the time fast covers the full list, slow covers half
 * 
 * Time Complexity: O(n) - single pass through the list
 * Space Complexity: O(1) - only using two pointers
 */
public class FindMiddle {
    
    /**
     * Find the middle node of a linked list
     * 
     * @param head the head of the linked list
     * @return the middle node (second middle if even length)
     * 
     * Time Complexity: O(n) where n is the number of nodes in the linked list
     * - Fast pointer traverses the entire list in n/2 iterations
     * - Slow pointer moves n/2 steps total
     * - Each iteration: move slow (O(1)) + move fast twice (O(1)) + condition check (O(1))
     * - Loop terminates when fast reaches end or can't take 2 more steps
     * - Total: (n/2) iterations * O(1) per iteration = O(n)
     * 
     * Space Complexity: O(1) - constant extra space
     * - Only using two pointer variables (slow and fast)
     * - No additional arrays, lists, or recursive calls
     * - Memory usage independent of input list size
     */
    public static ListNode findMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Alternative: Find the first middle node for even-length lists
     * 
     * @param head the head of the linked list
     * @return the first middle node if even length
     * 
     * Time Complexity: O(n) where n is the number of nodes in the linked list
     * - Similar to findMiddle() but fast pointer starts one step ahead
     * - Still requires traversing approximately half the list
     * - Same number of iterations, just different starting positions
     * - Total: O(n) - starting position doesn't affect overall complexity
     * 
     * Space Complexity: O(1) - constant extra space
     * - Two pointer variables with different initialization
     * - No additional memory allocation regardless of list size
     */
    public static ListNode findFirstMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode slow = head;
        ListNode fast = head.next; // Start fast one step ahead
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Find middle with additional information
     * Returns [middle node, list length, middle index]
     * 
     * Time Complexity: O(n) where n is the number of nodes in the linked list
     * - Uses same fast & slow pointer approach: O(n)
     * - Additional length counting: increment per iteration (O(1) each)
     * - Simple arithmetic for middle index calculation: O(1)
     * - Total: O(n) for traversal + O(n) * O(1) for counting = O(n)
     * 
     * Space Complexity: O(1) - constant extra space
     * - Two pointers + one integer counter + MiddleInfo object
     * - MiddleInfo stores fixed amount of data (node reference + 2 integers)
     * - Space usage doesn't scale with input size
     */
    public static MiddleInfo findMiddleDetailed(ListNode head) {
        if (head == null) {
            return new MiddleInfo(null, 0, -1);
        }
        if (head.next == null) {
            return new MiddleInfo(head, 1, 0);
        }
        
        ListNode slow = head;
        ListNode fast = head;
        int length = 0;
        
        // Count nodes while finding middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            length += 2;
        }
        
        // Adjust length for odd-length lists
        if (fast != null) {
            length++;
        }
        
        int middleIndex = length / 2;
        return new MiddleInfo(slow, length, middleIndex);
    }
    
    /**
     * Helper class to store middle node information
     */
    static class MiddleInfo {
        ListNode middle;
        int length;
        int middleIndex;
        
        MiddleInfo(ListNode middle, int length, int middleIndex) {
            this.middle = middle;
            this.length = length;
            this.middleIndex = middleIndex;
        }
        
        @Override
        public String toString() {
            if (middle == null) {
                return "Empty list";
            }
            return String.format("Middle: %s (index %d), List length: %d", 
                               middle, middleIndex, length);
        }
    }
    
    /**
     * Visualization helper - shows the algorithm step by step
     */
    private static void visualize(ListNode head) {
        System.out.println("=== Finding Middle Node Visualization ===");
        System.out.println("List: " + ListNode.toString(head, 20));
        System.out.println();
        
        if (head == null || head.next == null) {
            System.out.println("List has 0 or 1 node, middle is: " + head);
            return;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        int step = 0;
        
        System.out.println("Initial: slow and fast at head");
        
        while (fast != null && fast.next != null) {
            step++;
            slow = slow.next;
            fast = fast.next.next;
            
            System.out.printf("Step %d:\n", step);
            System.out.printf("  Slow at: %s (moved 1 step)\n", slow);
            System.out.printf("  Fast at: %s (moved 2 steps)\n", 
                            fast != null ? fast.toString() : "end of list");
        }
        
        System.out.println("\nMiddle node: " + slow);
    }
    
    /**
     * Helper method to create a visual representation of the list with middle marked
     */
    private static void showListWithMiddle(ListNode head) {
        MiddleInfo info = findMiddleDetailed(head);
        System.out.print("List: ");
        
        ListNode current = head;
        int index = 0;
        while (current != null) {
            if (index == info.middleIndex) {
                System.out.print("[" + current.val + "]");
            } else {
                System.out.print(current.val);
            }
            
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
            index++;
        }
        System.out.println();
        System.out.println(info);
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: Odd length list
        System.out.println("Test 1 - Odd length list:");
        ListNode list1 = ListNode.createList(new int[]{1, 2, 3, 4, 5});
        visualize(list1);
        showListWithMiddle(list1);
        System.out.println();
        
        // Test case 2: Even length list
        System.out.println("Test 2 - Even length list:");
        ListNode list2 = ListNode.createList(new int[]{1, 2, 3, 4, 5, 6});
        visualize(list2);
        showListWithMiddle(list2);
        System.out.println("First middle: " + findFirstMiddle(list2));
        System.out.println("Second middle: " + findMiddle(list2));
        System.out.println();
        
        // Test case 3: Two nodes
        System.out.println("Test 3 - Two nodes:");
        ListNode list3 = ListNode.createList(new int[]{1, 2});
        showListWithMiddle(list3);
        System.out.println();
        
        // Test case 4: Single node
        System.out.println("Test 4 - Single node:");
        ListNode list4 = new ListNode(42);
        showListWithMiddle(list4);
        System.out.println();
        
        // Test case 5: Empty list
        System.out.println("Test 5 - Empty list:");
        ListNode list5 = null;
        System.out.println("Middle: " + findMiddle(list5));
        System.out.println();
        
        // Test case 6: Large lists
        System.out.println("Test 6 - Performance with different sizes:");
        int[] sizes = {10, 100, 1000, 10000};
        
        for (int size : sizes) {
            int[] values = new int[size];
            for (int i = 0; i < size; i++) {
                values[i] = i;
            }
            ListNode largeList = ListNode.createList(values);
            
            long start = System.nanoTime();
            MiddleInfo info = findMiddleDetailed(largeList);
            long time = System.nanoTime() - start;
            
            System.out.printf("Size: %d, Middle at index: %d, Time: %.3f ms\n",
                            size, info.middleIndex, time / 1000000.0);
        }
        
        // Demonstrate the difference between two middle approaches
        System.out.println("\nDemonstrating first vs second middle for even lists:");
        for (int length = 2; length <= 8; length += 2) {
            int[] values = new int[length];
            for (int i = 0; i < length; i++) {
                values[i] = i + 1;
            }
            ListNode list = ListNode.createList(values);
            
            ListNode first = findFirstMiddle(list);
            ListNode second = findMiddle(list);
            
            System.out.printf("Length %d: First middle = %s, Second middle = %s\n",
                            length, first, second);
        }
    }
}