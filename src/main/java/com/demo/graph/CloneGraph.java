package com.demo.graph;

import java.util.*;

/**
 * Problem: Clone Graph
 * 
 * MNEMONIC: "MIRROR MAZE - Creating perfect reflections!"
 * 🪞 Like creating a mirror image where every connection is preserved
 * 
 * PROBLEM: Create a deep copy of an undirected graph.
 * Each node contains a value and list of neighbors.
 * 
 * TECHNIQUE: DFS/BFS with HashMap
 * - Use HashMap to map original nodes to cloned nodes
 * - Prevents infinite loops and maintains structure
 * 
 * VISUALIZATION:
 * Original: 1 -- 2
 *           |    |
 *           4 -- 3
 * 
 * Process:
 * Clone 1 → Create new node 1
 * Clone neighbors of 1 (2, 4) → Create new nodes
 * Recursively clone their neighbors
 * Map maintains: {orig1→clone1, orig2→clone2, ...}
 * 
 * Time Complexity: O(V + E) - visit each node and edge
 * Space Complexity: O(V) - HashMap and recursion stack
 */
public class CloneGraph {
    
    static class Node {
        public int val;
        public List<Node> neighbors;
        
        public Node(int val) {
            this.val = val;
            this.neighbors = new ArrayList<>();
        }
    }
    
    /**
     * Clone graph using DFS
     * 
     * REMEMBER: "MIRROR MAKER"
     * Create reflections while maintaining connections
     * 
     * @param originalNode starting node of graph to clone
     * @return starting node of cloned graph
     */
    public static Node createMirrorMaze_DFS(Node originalNode) {
        if (originalNode == null) return null;
        
        // Mirror mapping: original → clone
        Map<Node, Node> mirrorMap = new HashMap<>();
        
        return createMirrorNode(originalNode, mirrorMap);
    }
    
    /**
     * Recursively create mirror nodes
     * 
     * VISUALIZATION: Like reflecting in a mirror
     * If we've already created the reflection, use it
     * Otherwise, create new reflection and connect neighbors
     */
    private static Node createMirrorNode(Node original, Map<Node, Node> mirrorMap) {
        // Check if we already created this node's mirror
        if (mirrorMap.containsKey(original)) {
            return mirrorMap.get(original);
        }
        
        // Create the mirror node
        Node mirrorNode = new Node(original.val);
        mirrorMap.put(original, mirrorNode);
        
        // Mirror all neighbors
        for (Node neighbor : original.neighbors) {
            Node mirrorNeighbor = createMirrorNode(neighbor, mirrorMap);
            mirrorNode.neighbors.add(mirrorNeighbor);
        }
        
        return mirrorNode;
    }
    
    /**
     * Clone graph using BFS
     * 
     * REMEMBER: "LAYER BY LAYER COPYING"
     * Like photocopying page by page
     */
    public static Node createMirrorMaze_BFS(Node originalNode) {
        if (originalNode == null) return null;
        
        Map<Node, Node> mirrorMap = new HashMap<>();
        Queue<Node> copyQueue = new LinkedList<>();
        
        // Create first mirror and add to queue
        Node firstMirror = new Node(originalNode.val);
        mirrorMap.put(originalNode, firstMirror);
        copyQueue.offer(originalNode);
        
        // Process nodes level by level
        while (!copyQueue.isEmpty()) {
            Node current = copyQueue.poll();
            Node currentMirror = mirrorMap.get(current);
            
            // Process all neighbors
            for (Node neighbor : current.neighbors) {
                if (!mirrorMap.containsKey(neighbor)) {
                    // Create new mirror for unvisited neighbor
                    Node neighborMirror = new Node(neighbor.val);
                    mirrorMap.put(neighbor, neighborMirror);
                    copyQueue.offer(neighbor);
                }
                
                // Connect the mirrors
                currentMirror.neighbors.add(mirrorMap.get(neighbor));
            }
        }
        
        return firstMirror;
    }
    
    /**
     * Visualize graph structure
     */
    private static void visualizeGraph(Node node, String title) {
        if (node == null) {
            System.out.println(title + " is null");
            return;
        }
        
        System.out.println("\n=== " + title + " ===");
        
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.printf("Node %d connects to: ", current.val);
            
            for (Node neighbor : current.neighbors) {
                System.out.printf("%d ", neighbor.val);
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Verify the clone is correct
     */
    private static boolean verifyClone(Node original, Node clone) {
        if (original == null && clone == null) return true;
        if (original == null || clone == null) return false;
        
        Map<Node, Node> mapping = new HashMap<>();
        return verifyDFS(original, clone, mapping, new HashSet<>());
    }
    
    private static boolean verifyDFS(Node orig, Node clone, 
                                    Map<Node, Node> mapping, 
                                    Set<Node> visited) {
        if (visited.contains(orig)) return true;
        visited.add(orig);
        
        // Check value matches
        if (orig.val != clone.val) return false;
        
        // Check same number of neighbors
        if (orig.neighbors.size() != clone.neighbors.size()) return false;
        
        // Check they're different objects
        if (orig == clone) return false;
        
        mapping.put(orig, clone);
        
        // Verify all neighbors
        for (int i = 0; i < orig.neighbors.size(); i++) {
            Node origNeighbor = orig.neighbors.get(i);
            Node cloneNeighbor = clone.neighbors.get(i);
            
            if (mapping.containsKey(origNeighbor)) {
                if (mapping.get(origNeighbor) != cloneNeighbor) return false;
            } else {
                if (!verifyDFS(origNeighbor, cloneNeighbor, mapping, visited)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Create sample graphs for testing
     */
    private static Node createSampleGraph1() {
        // Graph: 1 -- 2
        //        |    |
        //        4 -- 3
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        
        n1.neighbors.addAll(Arrays.asList(n2, n4));
        n2.neighbors.addAll(Arrays.asList(n1, n3));
        n3.neighbors.addAll(Arrays.asList(n2, n4));
        n4.neighbors.addAll(Arrays.asList(n1, n3));
        
        return n1;
    }
    
    private static Node createSampleGraph2() {
        // Triangle: 1 -- 2
        //           \  /
        //            3
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        
        n1.neighbors.addAll(Arrays.asList(n2, n3));
        n2.neighbors.addAll(Arrays.asList(n1, n3));
        n3.neighbors.addAll(Arrays.asList(n1, n2));
        
        return n1;
    }
    
    /**
     * Show the cloning process step by step
     */
    private static void demonstrateCloningProcess() {
        System.out.println("\n=== 🪞 MIRROR MAZE CREATION PROCESS ===");
        System.out.println("\nStep-by-step cloning:");
        System.out.println("1. Start with original node");
        System.out.println("2. Create mirror node with same value");
        System.out.println("3. Add to mapping (original → mirror)");
        System.out.println("4. For each neighbor:");
        System.out.println("   - If already mirrored, use existing");
        System.out.println("   - Else create new mirror recursively");
        System.out.println("5. Connect all mirrors as in original");
        
        System.out.println("\nWhy we need the HashMap:");
        System.out.println("• Prevents infinite loops in cycles");
        System.out.println("• Ensures each node cloned only once");
        System.out.println("• Maintains correct neighbor relationships");
    }
    
    /**
     * Show applications of graph cloning
     */
    private static void showApplications() {
        System.out.println("\n=== 🌍 REAL-WORLD APPLICATIONS ===");
        System.out.println("\nWhere graph cloning is used:");
        System.out.println("1. 🧩 Puzzle Solving: Save game states");
        System.out.println("2. 🧬 Experiment Simulation: Test scenarios");
        System.out.println("3. 🌳 Version Control: Branch creation");
        System.out.println("4. 🧬 Machine Learning: Model checkpoints");
        System.out.println("5. 🎮 Game Development: Level backups");
        System.out.println("6. 🔧 Undo/Redo: State snapshots");
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("🪞 CLONE GRAPH - Mirror Maze Creator!\n");
        
        // Test 1: Square graph
        System.out.println("Test 1 - Square graph:");
        Node original1 = createSampleGraph1();
        visualizeGraph(original1, "Original Square Graph");
        
        Node cloneDFS = createMirrorMaze_DFS(original1);
        visualizeGraph(cloneDFS, "Cloned Graph (DFS)");
        
        boolean valid1 = verifyClone(original1, cloneDFS);
        System.out.println("\nClone verification: " + (valid1 ? "✅ Valid" : "❌ Invalid"));
        
        // Test 2: Triangle graph
        System.out.println("\nTest 2 - Triangle graph:");
        Node original2 = createSampleGraph2();
        visualizeGraph(original2, "Original Triangle Graph");
        
        Node cloneBFS = createMirrorMaze_BFS(original2);
        visualizeGraph(cloneBFS, "Cloned Graph (BFS)");
        
        boolean valid2 = verifyClone(original2, cloneBFS);
        System.out.println("\nClone verification: " + (valid2 ? "✅ Valid" : "❌ Invalid"));
        
        // Test 3: Edge cases
        System.out.println("\nTest 3 - Edge cases:");
        System.out.println("Null graph: " + (createMirrorMaze_DFS(null) == null ? "Correctly returns null" : "Error"));
        
        Node single = new Node(42);
        Node cloneSingle = createMirrorMaze_DFS(single);
        System.out.println("Single node: Original=" + single.val + ", Clone=" + cloneSingle.val);
        
        // Test 4: Process demonstration
        demonstrateCloningProcess();
        
        // Test 5: Applications
        showApplications();
        
        // Test 6: Performance comparison
        System.out.println("\nTest 6 - DFS vs BFS performance:");
        
        // Create larger graph
        int n = 100;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        
        // Connect as a ring with cross connections
        for (int i = 0; i < n; i++) {
            nodes[i].neighbors.add(nodes[(i + 1) % n]);
            nodes[i].neighbors.add(nodes[(i + 2) % n]);
        }
        
        long start1 = System.nanoTime();
        Node largeDFS = createMirrorMaze_DFS(nodes[0]);
        long timeDFS = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        Node largeBFS = createMirrorMaze_BFS(nodes[0]);
        long timeBFS = System.nanoTime() - start2;
        
        System.out.printf("DFS: %.3f ms\n", timeDFS / 1000000.0);
        System.out.printf("BFS: %.3f ms\n", timeBFS / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- MIRROR MAZE: Create perfect reflections");
        System.out.println("- HashMap prevents infinite loops");
        System.out.println("- Each node cloned exactly once");
        System.out.println("- DFS: recursive, natural for graphs");
        System.out.println("- BFS: iterative, level by level");
    }
}