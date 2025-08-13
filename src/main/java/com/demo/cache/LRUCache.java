package com.demo.cache;

import java.util.*;

/**
 * LRU (Least Recently Used) Cache Implementation
 * 
 * PROBLEM: Design a data structure that follows LRU eviction policy.
 * - get(key): Return value if key exists, -1 otherwise. Mark as recently used.
 * - put(key, value): Update or insert. If at capacity, remove least recently used.
 * Both operations must be O(1).
 * 
 * TECHNIQUE: HashMap + Doubly Linked List
 * - HashMap: O(1) access to nodes
 * - Doubly Linked List: O(1) removal and addition
 * - Head: Most recently used
 * - Tail: Least recently used
 * 
 * VISUALIZATION:
 * Capacity = 3
 * Operations: put(1,1), put(2,2), put(3,3), get(1), put(4,4)
 * 
 * After put(1,1): head <-> [1,1] <-> tail
 * After put(2,2): head <-> [2,2] <-> [1,1] <-> tail
 * After put(3,3): head <-> [3,3] <-> [2,2] <-> [1,1] <-> tail
 * After get(1):   head <-> [1,1] <-> [3,3] <-> [2,2] <-> tail
 * After put(4,4): head <-> [4,4] <-> [1,1] <-> [3,3] <-> tail (2 evicted)
 * 
 * KEY OPERATIONS:
 * 1. Add to head: New or accessed items
 * 2. Remove node: When accessed (to move) or evicted
 * 3. Remove from tail: LRU eviction
 * 
 * Time Complexity: O(1) for both get and put
 * Space Complexity: O(capacity)
 */
public class LRUCache {
    
    /**
     * Doubly Linked List Node
     * Stores key-value pair and pointers
     */
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node() {}
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head;  // Dummy head
    private final Node tail;  // Dummy tail
    
    /**
     * Initialize LRU Cache with given capacity
     * 
     * SETUP:
     * - Create dummy head and tail for easier operations
     * - Connect head <-> tail initially
     * - HashMap to store key -> node mapping
     * 
     * Time Complexity: O(1)
     * - Creating HashMap: O(1) - HashMap constructor with default initial capacity
     * - Creating dummy nodes: O(1) - Two Node object creations
     * - Linking dummy nodes: O(1) - Two pointer assignments
     * - All operations are constant time regardless of capacity parameter
     * 
     * Space Complexity: O(1)
     * - HashMap initialization: O(1) - Only creates empty backing array
     * - Two dummy nodes: O(1) - Fixed overhead regardless of capacity
     * - Instance variables: O(1) - Constant number of references
     * - Note: Actual space usage will grow to O(capacity) as items are added
     * 
     * @param capacity maximum number of items
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Create dummy nodes
        this.head = new Node();
        this.tail = new Node();
        
        // Connect them
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Get value for key
     * 
     * ALGORITHM:
     * 1. Check if key exists in cache
     * 2. If exists, move node to head (mark as recently used)
     * 3. Return value or -1
     * 
     * Time Complexity: O(1)
     * - HashMap.get(key): O(1) average case - Hash table lookup with good hash function
     * - removeNode(node): O(1) - Direct pointer manipulation (prev/next updates)
     * - addToHead(node): O(1) - Direct insertion after head with pointer updates
     * - All operations are independent of cache size or number of elements
     * - No iteration or searching through data structures
     * 
     * Space Complexity: O(1)
     * - No additional data structures created
     * - Only uses existing node reference and local variables
     * - Memory usage is constant regardless of input or cache state
     * - No recursive calls that would use call stack space
     * 
     * @param key the key to look up
     * @return value if exists, -1 otherwise
     */
    public int get(int key) {
        Node node = cache.get(key);
        
        if (node == null) {
            return -1;
        }
        
        // Move to head (mark as recently used)
        removeNode(node);
        addToHead(node);
        
        return node.value;
    }
    
    /**
     * Put key-value pair in cache
     * 
     * ALGORITHM:
     * 1. If key exists, update value and move to head
     * 2. If new key:
     *    a. Create new node and add to head
     *    b. If over capacity, remove LRU (tail)
     * 
     * Time Complexity: O(1)
     * - HashMap.get(key): O(1) average case - Hash table lookup
     * - For existing key:
     *   - Value update: O(1) - Direct assignment
     *   - removeNode(): O(1) - Pointer manipulation
     *   - addToHead(): O(1) - Pointer manipulation
     * - For new key:
     *   - Node creation: O(1) - Object instantiation
     *   - HashMap.put(): O(1) average case - Hash table insertion
     *   - addToHead(): O(1) - Pointer manipulation
     *   - removeTail(): O(1) - Direct access to tail.prev and pointer updates
     *   - HashMap.remove(): O(1) average case - Hash table removal
     * - All operations are constant time regardless of cache size
     * 
     * Space Complexity: O(1)
     * - For existing key: No additional space (reuses existing node)
     * - For new key: O(1) - Creates one new Node object
     * - Eviction maintains overall space at O(capacity)
     * - No additional data structures or recursive calls
     * - Local variables use constant space
     * 
     * @param key the key
     * @param value the value
     */
    public void put(int key, int value) {
        Node node = cache.get(key);
        
        if (node != null) {
            // Update existing
            node.value = value;
            removeNode(node);
            addToHead(node);
        } else {
            // Add new
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            
            // Check capacity
            if (cache.size() > capacity) {
                Node lru = removeTail();
                cache.remove(lru.key);
            }
        }
    }
    
    /**
     * Add node right after head
     * 
     * VISUALIZATION:
     * Before: head <-> [A] <-> [B] <-> tail
     * After:  head <-> [new] <-> [A] <-> [B] <-> tail
     * 
     * Time Complexity: O(1)
     * - Four pointer assignments: O(1) each
     *   1. node.prev = head
     *   2. node.next = head.next
     *   3. head.next.prev = node
     *   4. head.next = node
     * - Direct access to head and head.next (no traversal)
     * - Operations are independent of list size
     * 
     * Space Complexity: O(1)
     * - No new memory allocation (node already exists)
     * - Only manipulates existing pointer references
     * - No additional variables beyond method parameters
     * 
     * @param node node to add
     */
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Remove node from linked list
     * 
     * VISUALIZATION:
     * Before: [A] <-> [node] <-> [B]
     * After:  [A] <-> [B]
     * 
     * Time Complexity: O(1)
     * - Two pointer updates: O(1) each
     *   1. node.prev.next = node.next (bypass node going forward)
     *   2. node.next.prev = node.prev (bypass node going backward)
     * - Direct access to adjacent nodes via prev/next pointers
     * - No traversal or searching required
     * - Independent of list size
     * 
     * Space Complexity: O(1)
     * - No additional memory allocation
     * - Only updates existing pointer references
     * - Node itself is not deallocated (handled by garbage collector)
     * - Uses no additional variables
     * 
     * @param node node to remove
     */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    /**
     * Remove and return the tail node (LRU)
     * 
     * Time Complexity: O(1)
     * - tail.prev access: O(1) - Direct pointer dereference to LRU node
     * - removeNode(lru): O(1) - As analyzed above, constant time pointer updates
     * - No traversal from head needed due to doubly-linked structure
     * - Independent of list size
     * 
     * Space Complexity: O(1)
     * - One local variable (lru): O(1) - Stores reference to existing node
     * - removeNode() uses O(1) space as analyzed above
     * - Returns existing node reference (no new allocation)
     * 
     * @return the removed node
     */
    private Node removeTail() {
        Node lru = tail.prev;
        removeNode(lru);
        return lru;
    }
    
    /**
     * Alternative Implementation using LinkedHashMap
     * 
     * LinkedHashMap maintains insertion order and has a special
     * constructor for access-order mode, perfect for LRU.
     * 
     * This is a cleaner but less customizable approach.
     */
    public static class LRUCacheLinkedHashMap extends LinkedHashMap<Integer, Integer> {
        private final int capacity;
        
        public LRUCacheLinkedHashMap(int capacity) {
            // true = access-order (not insertion-order)
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }
        
        /**
         * Get value for key using LinkedHashMap's built-in access-order
         * 
         * Time Complexity: O(1)
         * - LinkedHashMap.getOrDefault(): O(1) average case
         * - Hash table lookup with automatic LRU ordering
         * - Access automatically moves entry to end (most recent)
         * 
         * Space Complexity: O(1)
         * - No additional space beyond LinkedHashMap's internal operations
         */
        public int get(int key) {
            return super.getOrDefault(key, -1);
        }
        
        /**
         * Put key-value pair using LinkedHashMap's built-in capacity management
         * 
         * Time Complexity: O(1)
         * - LinkedHashMap.put(): O(1) average case
         * - Hash table insertion with automatic ordering
         * - removeEldestEntry() called automatically: O(1)
         * 
         * Space Complexity: O(1)
         * - Reuses existing entry if key exists
         * - Creates one new entry if new key (managed by removeEldestEntry)
         */
        public void put(int key, int value) {
            super.put(key, value);
        }
        
        /**
         * Determines whether to remove eldest entry (LRU eviction policy)
         * Called automatically by LinkedHashMap after each put operation
         * 
         * Time Complexity: O(1)
         * - size() method: O(1) - LinkedHashMap maintains size counter
         * - Integer comparison: O(1)
         * - If returns true, LinkedHashMap removes eldest entry: O(1)
         * 
         * Space Complexity: O(1)
         * - No additional space used
         * - Method parameter is reference to existing entry
         */
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
    
    /**
     * Test the LRU Cache
     */
    public static void main(String[] args) {
        // Example usage
        LRUCache cache = new LRUCache(2);
        
        cache.put(1, 1);      // cache is {1=1}
        cache.put(2, 2);      // cache is {1=1, 2=2}
        System.out.println(cache.get(1));  // returns 1, cache is {2=2, 1=1}
        
        cache.put(3, 3);      // evicts key 2, cache is {1=1, 3=3}
        System.out.println(cache.get(2));  // returns -1 (not found)
        
        cache.put(4, 4);      // evicts key 1, cache is {3=3, 4=4}
        System.out.println(cache.get(1));  // returns -1 (not found)
        System.out.println(cache.get(3));  // returns 3
        System.out.println(cache.get(4));  // returns 4
    }
}