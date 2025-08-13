package com.demo;

/**
 * Definition for singly-linked list node.
 * This is a common data structure used in coding interviews.
 */
public class ListNode {
    public int val;
    public ListNode next;
    
    public ListNode() {}
    
    public ListNode(int val) {
        this.val = val;
    }
    
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
    
    /**
     * Create a linked list from an array of values
     */
    public static ListNode createList(int... vals) {
        if (vals.length == 0) return null;
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        for (int v : vals) {
            current.next = new ListNode(v);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Create a linked list with a cycle at the specified position
     */
    public static ListNode createListWithCycle(int[] vals, int pos) {
        if (vals.length == 0) return null;
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        ListNode cycleNode = null;
        
        for (int i = 0; i < vals.length; i++) {
            current.next = new ListNode(vals[i]);
            current = current.next;
            if (i == pos) {
                cycleNode = current;
            }
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && pos < vals.length && cycleNode != null) {
            current.next = cycleNode;
        }
        
        return dummy.next;
    }
    
    /**
     * Print the linked list (with cycle detection to avoid infinite loops)
     */
    public static void printList(ListNode head) {
        printList(head, 20); // Default max nodes to print
    }
    
    public static void printList(ListNode head, int maxNodes) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        ListNode current = head;
        int count = 0;
        
        while (current != null && count < maxNodes) {
            System.out.print(current.val);
            if (current.next != null && count < maxNodes - 1) {
                System.out.print(" -> ");
            }
            current = current.next;
            count++;
        }
        
        if (current != null) {
            System.out.print(" -> ... (truncated)");
        }
        System.out.println();
    }
    
    /**
     * Convert linked list to string representation
     */
    public static String toString(ListNode head, int maxNodes) {
        if (head == null) return "null";
        
        StringBuilder sb = new StringBuilder();
        ListNode current = head;
        int count = 0;
        
        while (current != null && count < maxNodes) {
            sb.append(current.val);
            if (current.next != null && count < maxNodes - 1) {
                sb.append(" -> ");
            }
            current = current.next;
            count++;
        }
        
        if (current != null) {
            sb.append(" -> ... (cycle or truncated)");
        }
        
        return sb.toString();
    }
}
