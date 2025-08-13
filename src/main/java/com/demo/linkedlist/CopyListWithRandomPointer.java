package com.demo.linkedlist;

import java.util.*;

/**
 * Problem: Copy List with Random Pointer
 * 
 * MNEMONIC: "CLONE WITH TWINS - Create twin for each node!"
 * 👯 Like creating an identical twin with all the same connections
 * 
 * PROBLEM: Deep copy a linked list where each node has an additional random pointer
 * that could point to any node in the list or null.
 * 
 * TECHNIQUE 1: HashMap for Old-to-New Mapping
 * - First pass: Create all new nodes, store mapping
 * - Second pass: Set next and random pointers using map
 * 
 * TECHNIQUE 2: Interweaving (O(1) Space)
 * - Insert copy after each original node
 * - Set random pointers using original's random
 * - Extract the copy list
 * 
 * VISUALIZATION (Interweaving):
 * Original: A -> B -> C
 * After insertion: A -> A' -> B -> B' -> C -> C'
 * Set randoms: If A.random = C, then A'.random = C'
 * Extract: A' -> B' -> C'
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(n) for HashMap, O(1) for interweaving
 */
public class CopyListWithRandomPointer {
    
    /**
     * Node class with random pointer
     */
    static class Node {
        int val;
        Node next;
        Node random;
        
        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    
    /**
     * HashMap Approach - Create mapping of original to clone
     * 
     * REMEMBER: "TWIN REGISTRY"
     * Keep a registry mapping each person to their twin
     * 
     * @param originalHead original list head
     * @return deep copied list head
     */
    public static Node cloneWithRegistry_HashMapMethod(Node originalHead) {
        if (originalHead == null) return null;
        
        // Twin registry: maps original to clone
        Map<Node, Node> twinRegistry = new HashMap<>();
        
        // First pass: Create all twin nodes
        Node current = originalHead;
        while (current != null) {
            // Create twin and register it
            Node twin = new Node(current.val);
            twinRegistry.put(current, twin);
            current = current.next;
        }
        
        // Second pass: Connect the twins properly
        current = originalHead;
        while (current != null) {
            Node twin = twinRegistry.get(current);
            
            // Set next pointer to twin of current.next
            twin.next = twinRegistry.get(current.next);
            
            // Set random pointer to twin of current.random
            twin.random = twinRegistry.get(current.random);
            
            current = current.next;
        }
        
        return twinRegistry.get(originalHead);
    }
    
    /**
     * O(1) Space Approach using Interweaving
     * 
     * REMEMBER: "SHADOW CLONE JUTSU"
     * Place shadow clone right after each original
     * 
     * STEPS:
     * 1. Insert copy nodes between originals
     * 2. Set random pointers for copies
     * 3. Extract copy list and restore original
     * 
     * @param originalHead original list head
     * @return deep copied list head
     */
    public static Node cloneWithShadows_InterweavingMethod(Node originalHead) {
        if (originalHead == null) return null;
        
        // Step 1: Create shadow clones (insert after each original)
        Node current = originalHead;
        while (current != null) {
            Node shadowClone = new Node(current.val);
            shadowClone.next = current.next;
            current.next = shadowClone;
            current = shadowClone.next; // Move to next original
        }
        
        // Step 2: Set random pointers for shadow clones
        current = originalHead;
        while (current != null) {
            if (current.random != null) {
                // Shadow's random = original's random's shadow
                current.next.random = current.random.next;
            }
            current = current.next.next; // Skip shadow, go to next original
        }
        
        // Step 3: Extract shadow clone list and restore original
        Node shadowHead = originalHead.next;
        Node original = originalHead;
        Node shadow = shadowHead;
        
        while (original != null) {
            original.next = original.next.next; // Restore original's next
            
            if (shadow.next != null) {
                shadow.next = shadow.next.next; // Connect shadows
            }
            
            original = original.next;
            shadow = shadow.next;
        }
        
        return shadowHead;
    }
    
    /**
     * Visualize the cloning process
     */
    private static void visualizeCloning(Node head) {
        System.out.println("=== 👯 CLONE WITH TWINS VISUALIZATION ===");
        
        System.out.println("\nOriginal list structure:");
        printListWithRandom(head);
        
        // Show HashMap approach
        System.out.println("\n=== METHOD 1: TWIN REGISTRY (HashMap) ===");
        System.out.println("\nStep 1: Create registry and register all twins");
        System.out.println("Registry: {Original → Twin}");
        
        Node curr = head;
        int index = 0;
        while (curr != null) {
            System.out.printf("  Node_%d(val=%d) → Twin_%d(val=%d)\n", 
                            index, curr.val, index, curr.val);
            curr = curr.next;
            index++;
        }
        
        System.out.println("\nStep 2: Connect twins using registry");
        System.out.println("For each original, set twin.next = registry[original.next]");
        System.out.println("                  set twin.random = registry[original.random]");
        
        // Show Interweaving approach
        System.out.println("\n=== METHOD 2: SHADOW CLONE (Interweaving) ===");
        System.out.println("\nStep 1: Insert shadows after originals");
        System.out.println("Before: A → B → C");
        System.out.println("After:  A → A' → B → B' → C → C'");
        
        System.out.println("\nStep 2: Set shadow random pointers");
        System.out.println("If A.random = C, then A'.random = C' (which is C.next)");
        
        System.out.println("\nStep 3: Extract shadow list");
        System.out.println("Separate: A → B → C  and  A' → B' → C'");
    }
    
    /**
     * Print list with random pointers
     */
    private static void printListWithRandom(Node head) {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        
        // First, assign indices to nodes
        Map<Node, Integer> nodeIndex = new HashMap<>();
        Node curr = head;
        int index = 0;
        
        while (curr != null) {
            nodeIndex.put(curr, index++);
            curr = curr.next;
        }
        
        // Print with indices
        curr = head;
        while (curr != null) {
            int idx = nodeIndex.get(curr);
            System.out.printf("Node[%d]: val=%d", idx, curr.val);
            
            if (curr.random != null) {
                System.out.printf(", random=Node[%d]", nodeIndex.get(curr.random));
            } else {
                System.out.print(", random=null");
            }
            
            if (curr.next != null) {
                System.out.printf(", next=Node[%d]", nodeIndex.get(curr.next));
            } else {
                System.out.print(", next=null");
            }
            
            System.out.println();
            curr = curr.next;
        }
    }
    
    /**
     * Create sample list with random pointers
     */
    private static Node createSampleList() {
        Node n1 = new Node(7);
        Node n2 = new Node(13);
        Node n3 = new Node(11);
        Node n4 = new Node(10);
        Node n5 = new Node(1);
        
        // Set next pointers
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        
        // Set random pointers
        n1.random = null;
        n2.random = n1;
        n3.random = n5;
        n4.random = n3;
        n5.random = n1;
        
        return n1;
    }
    
    /**
     * Verify the clone is correct
     */
    private static boolean verifyClone(Node original, Node clone) {
        Map<Node, Node> seen = new HashMap<>();
        
        while (original != null && clone != null) {
            // Check values match
            if (original.val != clone.val) return false;
            
            // Check they're different objects
            if (original == clone) return false;
            
            // Store mapping
            seen.put(original, clone);
            
            original = original.next;
            clone = clone.next;
        }
        
        // Both should be null
        if (original != null || clone != null) return false;
        
        // Verify random pointers
        for (Map.Entry<Node, Node> entry : seen.entrySet()) {
            Node orig = entry.getKey();
            Node cloned = entry.getValue();
            
            if (orig.random == null) {
                if (cloned.random != null) return false;
            } else {
                if (cloned.random != seen.get(orig.random)) return false;
            }
        }
        
        return true;
    }
    
    /**
     * Compare the two approaches
     */
    private static void compareApproaches() {
        System.out.println("\n=== 🎆 APPROACH COMPARISON ===");
        
        System.out.println("\nHASHMAP APPROACH:");
        System.out.println("• Intuitive and straightforward");
        System.out.println("• O(n) extra space for map");
        System.out.println("• Two separate passes");
        System.out.println("• Works for any graph structure");
        
        System.out.println("\nINTERWEAVING APPROACH:");
        System.out.println("• Clever space optimization");
        System.out.println("• O(1) extra space");
        System.out.println("• Three passes through list");
        System.out.println("• Temporarily modifies original");
        System.out.println("• Mind-bending but efficient!");
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("👯 COPY LIST WITH RANDOM POINTER - Clone with Twins!\n");
        
        // Test 1: Standard example
        System.out.println("Test 1 - Standard list with random pointers:");
        Node original = createSampleList();
        visualizeCloning(original);
        
        // Test 2: Clone using both methods
        System.out.println("\nTest 2 - Cloning results:");
        
        Node clone1 = cloneWithRegistry_HashMapMethod(original);
        System.out.println("\nHashMap clone:");
        printListWithRandom(clone1);
        System.out.println("Verification: " + (verifyClone(original, clone1) ? "✅ Valid" : "❌ Invalid"));
        
        Node clone2 = cloneWithShadows_InterweavingMethod(original);
        System.out.println("\nInterweaving clone:");
        printListWithRandom(clone2);
        System.out.println("Verification: " + (verifyClone(original, clone2) ? "✅ Valid" : "❌ Invalid"));
        
        // Test 3: Edge cases
        System.out.println("\nTest 3 - Edge cases:");
        System.out.println("Null list: " + (cloneWithRegistry_HashMapMethod(null) == null ? "✅ Pass" : "❌ Fail"));
        
        Node single = new Node(42);
        single.random = single; // Points to itself
        Node cloneSingle = cloneWithShadows_InterweavingMethod(single);
        System.out.println("Self-referencing node: " + 
                         (cloneSingle.val == 42 && cloneSingle.random == cloneSingle ? "✅ Pass" : "❌ Fail"));
        
        // Test 4: Approach comparison
        compareApproaches();
        
        // Test 5: Performance test
        System.out.println("\nTest 5 - Performance comparison:");
        int size = 10000;
        Node bigList = createLargeList(size);
        
        long start1 = System.nanoTime();
        Node bigClone1 = cloneWithRegistry_HashMapMethod(bigList);
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        Node bigClone2 = cloneWithShadows_InterweavingMethod(bigList);
        long time2 = System.nanoTime() - start2;
        
        System.out.printf("HashMap approach: %.3f ms\n", time1 / 1000000.0);
        System.out.printf("Interweaving approach: %.3f ms\n", time2 / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- CLONE WITH TWINS: Deep copy with all connections");
        System.out.println("- HashMap: Map original → clone, straightforward");
        System.out.println("- Interweaving: A→A'→B→B', clever O(1) space");
        System.out.println("- Random pointers make this tricky!");
        System.out.println("- Both approaches handle cycles correctly");
    }
    
    private static Node createLargeList(int size) {
        if (size == 0) return null;
        
        Node[] nodes = new Node[size];
        
        // Create nodes
        for (int i = 0; i < size; i++) {
            nodes[i] = new Node(i);
        }
        
        // Set next pointers
        for (int i = 0; i < size - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        
        // Set random pointers
        Random rand = new Random(42); // Fixed seed for consistency
        for (int i = 0; i < size; i++) {
            if (rand.nextBoolean()) {
                nodes[i].random = nodes[rand.nextInt(size)];
            }
        }
        
        return nodes[0];
    }
}