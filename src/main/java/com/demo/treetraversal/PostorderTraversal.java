package com.demo.treetraversal;

import java.util.*;

/**
 * Problem: Postorder Traversal (Iterative)
 * 
 * MNEMONIC: "LEFT-RIGHT-ROOT - Children before Parents!"
 * 👨‍👩‍👧‍👦 Like a family photo: kids in front, parents in back
 * 
 * PROBLEM: Traverse a binary tree in postorder sequence (Left, Right, Root).
 * Used for deleting trees or calculating sizes/heights.
 * Example: Tree:     4
 *                   / \
 *                  2   6
 *                 / \ / \
 *                1  3 5  7
 * Answer: [1, 3, 2, 5, 7, 6, 4] - Children before parents!
 * 
 * TECHNIQUE: "Reverse Preorder Trick"
 * - Do modified preorder: Root, Right, Left
 * - Add to front of result (or reverse at end)
 * - This magically gives us postorder!
 * 
 * WHY IT WORKS:
 * - Preorder: Root-Left-Right
 * - Modified: Root-Right-Left
 * - Reversed: Left-Right-Root (Postorder!)
 * 
 * Time Complexity: O(n) - visit each node once
 * Space Complexity: O(h) - stack size
 */
public class PostorderTraversal {
    
    /**
     * Perform postorder traversal using reverse preorder trick
     * 
     * REMEMBER: "LEFT-RIGHT-ROOT" = "LRR" = "Let's Respect Roots-last"
     * Children get processed before their parents!
     * 
     * @param familyTreeRoot the root of the binary tree
     * @return list of values with children before parents
     */
    public static List<Integer> postorderTraversal_ChildrenBeforeParents(TreeNode familyTreeRoot) {
        LinkedList<Integer> familyPhotoOrder = new LinkedList<>();  // Use LinkedList for efficient addFirst
        if (familyTreeRoot == null) return familyPhotoOrder;
        
        Stack<TreeNode> photoStack = new Stack<>();
        photoStack.push(familyTreeRoot);
        
        // Do modified preorder (Root-Right-Left) and add to front
        while (!photoStack.isEmpty()) {
            TreeNode currentPerson = photoStack.pop();
            familyPhotoOrder.addFirst(currentPerson.nodeValue);  // Add to FRONT!
            
            // Push LEFT first, then RIGHT (opposite of normal preorder)
            if (currentPerson.leftBranch != null) {
                photoStack.push(currentPerson.leftBranch);
            }
            if (currentPerson.rightBranch != null) {
                photoStack.push(currentPerson.rightBranch);
            }
        }
        
        return familyPhotoOrder;
    }
    
    /**
     * Alternative: Two-stack approach for better understanding
     * More intuitive but uses more space
     */
    public static List<Integer> postorderTraversal_TwoStackMethod(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        
        // First stack: normal traversal
        stack1.push(root);
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);  // Collect in second stack
            
            // Push left first, then right
            if (node.leftBranch != null) {
                stack1.push(node.leftBranch);
            }
            if (node.rightBranch != null) {
                stack1.push(node.rightBranch);
            }
        }
        
        // Second stack has nodes in reverse postorder
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().nodeValue);
        }
        
        return result;
    }
    
    /**
     * True iterative postorder (harder but more authentic)
     * Uses a flag to track if we're visiting node for first or second time
     */
    public static List<Integer> postorderTraversal_AuthenticIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode lastVisited = null;
        TreeNode current = root;
        
        while (!stack.isEmpty() || current != null) {
            // Go to leftmost node
            if (current != null) {
                stack.push(current);
                current = current.leftBranch;
            } else {
                TreeNode peekNode = stack.peek();
                // If right child exists and not processed yet
                if (peekNode.rightBranch != null && lastVisited != peekNode.rightBranch) {
                    current = peekNode.rightBranch;
                } else {
                    // Visit the node
                    result.add(peekNode.nodeValue);
                    lastVisited = stack.pop();
                }
            }
        }
        
        return result;
    }
    
    /**
     * Visualization helper - shows why postorder is useful
     */
    private static void demonstrateTreeDeletion(TreeNode root) {
        System.out.println("\n=== Why Postorder for Tree Deletion? ===");
        System.out.println("To safely delete a tree, you must:");
        System.out.println("1. Delete all children first");
        System.out.println("2. Then delete the parent");
        System.out.println("This is exactly postorder traversal!\n");
        
        List<Integer> deletionOrder = postorderTraversal_ChildrenBeforeParents(root);
        System.out.println("Safe deletion order: " + deletionOrder);
        System.out.println("Notice: Every node's children appear before it");
        
        // Demonstrate
        for (int i = 0; i < deletionOrder.size(); i++) {
            System.out.printf("Step %d: Delete node %d (its children are already gone)\n", 
                            i + 1, deletionOrder.get(i));
        }
    }
    
    /**
     * Show the relationship between traversals
     */
    private static void compareAllTraversals(TreeNode root) {
        System.out.println("\n=== 🎯 Traversal Comparison ===");
        
        List<Integer> preorder = PreorderTraversal.preorderTraversal_RootLeftRight(root);
        List<Integer> inorder = InorderTraversal.inorderTraversal_LeftRootRight(root);
        List<Integer> postorder = postorderTraversal_ChildrenBeforeParents(root);
        
        System.out.println("Tree:        4");
        System.out.println("           /   \\");
        System.out.println("          2     6");
        System.out.println("         / \\   / \\");
        System.out.println("        1   3 5   7\n");
        
        System.out.println("PREorder  (Root-Left-Right):  " + preorder);
        System.out.println("          Parent → Children\n");
        
        System.out.println("INorder   (Left-Root-Right):  " + inorder);
        System.out.println("          Left → Parent → Right\n");
        
        System.out.println("POSTorder (Left-Right-Root):  " + postorder);
        System.out.println("          Children → Parent");
    }
    
    /**
     * Main method to run and test the solution
     */
    public static void main(String[] args) {
        System.out.println("👨‍👩‍👧‍👦 POSTORDER TRAVERSAL - Children Before Parents!\n");
        
        // Test 1: Perfect BST
        System.out.println("Test 1 - Family Photo Arrangement:");
        TreeNode familyTree = TreeNode.createSampleBST();
        
        List<Integer> photoOrder = postorderTraversal_ChildrenBeforeParents(familyTree);
        System.out.println("Family photo order (kids in front): " + photoOrder);
        System.out.println("Notice: Every parent appears AFTER their children!");
        System.out.println();
        
        // Test 2: Tree deletion demonstration
        System.out.println("Test 2 - Tree Deletion Use Case:");
        demonstrateTreeDeletion(familyTree);
        System.out.println();
        
        // Test 3: Compare different methods
        System.out.println("Test 3 - Different Implementation Methods:");
        TreeNode testTree = TreeNode.createSampleBST();
        
        List<Integer> reverseMethod = postorderTraversal_ChildrenBeforeParents(testTree);
        List<Integer> twoStackMethod = postorderTraversal_TwoStackMethod(testTree);
        List<Integer> authenticMethod = postorderTraversal_AuthenticIterative(testTree);
        
        System.out.println("Reverse preorder method:  " + reverseMethod);
        System.out.println("Two-stack method:         " + twoStackMethod);
        System.out.println("Authentic iterative:      " + authenticMethod);
        System.out.println("All methods give same result: " + 
                         (reverseMethod.equals(twoStackMethod) && 
                         twoStackMethod.equals(authenticMethod)));
        System.out.println();
        
        // Test 4: Show all three traversals
        System.out.println("Test 4 - All Three Traversals:");
        compareAllTraversals(familyTree);
        System.out.println();
        
        // Test 5: Edge cases
        System.out.println("Test 5 - Edge Cases:");
        System.out.println("Empty tree: " + postorderTraversal_ChildrenBeforeParents(null));
        System.out.println("Single node: " + postorderTraversal_ChildrenBeforeParents(new TreeNode(42)));
        
        TreeNode skewed = TreeNode.createRightSkewedTree();
        System.out.println("Right-skewed 1->2->3->4: " + 
                         postorderTraversal_ChildrenBeforeParents(skewed));
        System.out.println();
        
        // Test 6: Calculate tree height using postorder
        System.out.println("Test 6 - Calculate Tree Height (Postorder Application):");
        System.out.println("To find height, we need children's heights first!");
        System.out.println("This naturally fits postorder traversal pattern");
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- POSTorder = LEFT-RIGHT-ROOT = 'Let's Respect Roots-last'");
        System.out.println("- 'Post' means parent AFTER children");
        System.out.println("- Like family photo: kids in front, parents in back");
        System.out.println("- Reverse trick: Root-Right-Left reversed = Left-Right-Root");
        System.out.println("- Use cases: tree deletion, size/height calculation");
    }
}