package com.demo.fastslowpointers;

/**
 * Problem: Detect if a linked list has a cycle
 * 
 * PROBLEM: Given a linked list, determine if it contains a cycle.
 * A cycle exists if a node can be reached again by following the next pointers.
 * 
 * TECHNIQUE: Floyd's Cycle Detection Algorithm (Tortoise and Hare)
 * - Use two pointers: slow (moves 1 step) and fast (moves 2 steps)
 * - If there's a cycle, fast will eventually meet slow
 * - If there's no cycle, fast will reach the end (null)
 * 
 * VISUALIZATION: List with cycle: 1 -> 2 -> 3 -> 4 -> 5
 *                                          ^         |
 *                                          |_________|
 * 
 * Step 1: slow=1, fast=1 (start)
 * Step 2: slow=2, fast=3
 * Step 3: slow=3, fast=5
 * Step 4: slow=4, fast=4 (meet! cycle detected)
 * 
 * WHY IT WORKS: Think of it as two runners on a circular track
 * - The faster runner will lap the slower runner
 * - They will meet at some point on the track
 * - If the track is not circular (no cycle), fast runner reaches the end
 * 
 * Time Complexity: O(n) - in worst case, we visit each node once
 * Space Complexity: O(1) - only using two pointers
 */
public class CycleDetection {
    
    /**
     * Detect if a linked list has a cycle
     * 
     * @param head the head of the linked list
     * @return true if cycle exists, false otherwise
     * 
     * Time Complexity: O(n) where n is the number of nodes in the linked list
     * - Without cycle: fast pointer traverses the entire list once, visiting n/2 nodes
     * - With cycle: both pointers will meet within the cycle. In worst case, slow pointer
     *   makes one complete traversal of the non-cyclic part + one cycle traversal
     * - Mathematical proof: if cycle length is c, they meet in at most c steps after
     *   slow enters the cycle, so total steps ≤ n + c ≤ 2n = O(n)
     * 
     * Space Complexity: O(1) - constant extra space
     * - Only using two pointer variables (slow and fast)
     * - No additional data structures or recursive calls
     * - Space usage doesn't grow with input size
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps
            
            if (slow == fast) {
                return true;  // Cycle detected
            }
        }
        
        return false;  // No cycle
    }
    
    /**
     * Visualization helper - shows the algorithm step by step
     * Note: This uses a modified approach to safely demonstrate with cycles
     */
    private static void visualize(ListNode head, boolean hasCycle) {
        System.out.println("=== Floyd's Cycle Detection Visualization ===");
        System.out.println("List: " + ListNode.toString(head, 10));
        System.out.println("Expected cycle: " + hasCycle);
        System.out.println();
        
        if (head == null || head.next == null) {
            System.out.println("List is too short to have a cycle");
            return;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        int step = 0;
        int maxSteps = 20; // Prevent infinite loop in visualization
        
        while (fast != null && fast.next != null && step < maxSteps) {
            step++;
            slow = slow.next;
            fast = fast.next.next;
            
            System.out.printf("Step %d:\n", step);
            System.out.printf("  Slow pointer at: %s\n", slow);
            System.out.printf("  Fast pointer at: %s\n", fast);
            
            if (slow == fast) {
                System.out.println("  ✓ Pointers meet! Cycle detected.");
                return;
            }
        }
        
        if (fast == null || fast.next == null) {
            System.out.println("  ✗ Fast pointer reached end. No cycle.");
        } else {
            System.out.println("  (Visualization stopped after " + maxSteps + " steps)");
        }
    }
    
    /**
     * Alternative implementation with step counting
     * 
     * Time Complexity: O(n) where n is the number of nodes in the linked list
     * - Same as hasCycle() method - uses identical Floyd's algorithm
     * - Additional step counting is O(1) per iteration
     * - Total: O(n) for traversal + O(n) * O(1) for counting = O(n)
     * 
     * Space Complexity: O(1) - constant extra space
     * - Two pointers (slow, fast) + one integer counter (steps)
     * - All variables use constant space regardless of input size
     */
    public static int hasCycleWithSteps(ListNode head) {
        if (head == null || head.next == null) {
            return -1; // No cycle
        }
        
        ListNode slow = head;
        ListNode fast = head;
        int steps = 0;
        
        while (fast != null && fast.next != null) {
            steps++;
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return steps; // Return number of steps to detect cycle
            }
        }
        
        return -1; // No cycle
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        // Test case 1: List with cycle
        System.out.println("Test 1 - List with cycle:");
        ListNode list1 = ListNode.createListWithCycle(new int[]{3, 2, 0, -4}, 1);
        visualize(list1, true);
        System.out.println("Has cycle: " + hasCycle(list1));
        System.out.println();
        
        // Test case 2: List without cycle
        System.out.println("Test 2 - List without cycle:");
        ListNode list2 = ListNode.createList(new int[]{1, 2, 3, 4, 5});
        visualize(list2, false);
        System.out.println("Has cycle: " + hasCycle(list2));
        System.out.println();
        
        // Test case 3: Two-node cycle
        System.out.println("Test 3 - Two-node cycle:");
        ListNode list3 = ListNode.createListWithCycle(new int[]{1, 2}, 0);
        System.out.println("List: 1 -> 2 -> (back to 1)");
        System.out.println("Has cycle: " + hasCycle(list3));
        int steps3 = hasCycleWithSteps(list3);
        System.out.println("Steps to detect: " + steps3);
        System.out.println();
        
        // Test case 4: Single node with self-loop
        System.out.println("Test 4 - Single node with self-loop:");
        ListNode list4 = new ListNode(1);
        list4.next = list4;
        System.out.println("List: 1 -> (self)");
        System.out.println("Has cycle: " + hasCycle(list4));
        System.out.println();
        
        // Test case 5: Empty list
        System.out.println("Test 5 - Empty list:");
        System.out.println("Has cycle: " + hasCycle(null));
        System.out.println();
        
        // Test case 6: Single node without cycle
        System.out.println("Test 6 - Single node without cycle:");
        ListNode list6 = new ListNode(1);
        System.out.println("List: 1");
        System.out.println("Has cycle: " + hasCycle(list6));
        System.out.println();
        
        // Performance demonstration
        System.out.println("=== Performance Analysis ===");
        int[] sizes = {10, 100, 1000};
        for (int size : sizes) {
            // Create a large list with cycle at middle
            int[] values = new int[size];
            for (int i = 0; i < size; i++) {
                values[i] = i;
            }
            ListNode largeList = ListNode.createListWithCycle(values, size / 2);
            
            int steps = hasCycleWithSteps(largeList);
            System.out.printf("List size: %d, Cycle at: %d, Steps to detect: %d\n", 
                            size, size / 2, steps);
        }
    }
}