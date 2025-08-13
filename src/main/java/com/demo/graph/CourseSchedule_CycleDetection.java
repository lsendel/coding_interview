package com.demo.graph;

import java.util.*;

/**
 * Problem: Course Schedule (Cycle Detection in Directed Graph)
 * 
 * MNEMONIC: "TRAFFIC LIGHT SYSTEM - Red, Yellow, Green!"
 * 😦 White (unvisited), Yellow (processing), Green (completed)
 * 
 * PROBLEM: Given prerequisites for courses, determine if it's possible to finish
 * all courses (no circular dependencies).
 * Example: numCourses = 4, prerequisites = [[1,0], [2,1], [3,2]]
 * Means: To take course 1, must finish 0; for 2, must finish 1, etc.
 * Answer: true (valid order: 0 → 1 → 2 → 3)
 * 
 * TECHNIQUE: DFS with Three States (Traffic Light Colors)
 * - White (0): Unvisited - haven't started this course
 * - Yellow (1): Currently visiting - in progress
 * - Green (2): Completely visited - course completed
 * - Cycle exists if we visit a yellow node (course in progress)
 * 
 * VISUALIZATION: Prerequisites [[1,0], [2,1], [3,2], [1,3]]
 * Graph: 0 → 1 → 2 → 3 → 1 (cycle!)
 * 
 * DFS from 0:
 * Visit 0 (yellow), visit 1 (yellow), visit 2 (yellow), visit 3 (yellow)
 * Try to visit 1 again - it's yellow! Cycle detected!
 * 
 * Time Complexity: O(V + E) - standard DFS
 * Space Complexity: O(V) - for colors array and recursion
 */
public class CourseSchedule_CycleDetection {
    
    /**
     * Check if all courses can be completed
     * 
     * REMEMBER: "UNIVERSITY PLANNER"
     * Can we graduate without circular prerequisites?
     * 
     * @param totalCourses number of courses
     * @param prerequisites array of [course, prerequisite] pairs
     * @return true if possible to complete all courses
     */
    public static boolean canGraduate_TrafficLightMethod(int totalCourses, int[][] prerequisites) {
        // Build course dependency map (prerequisite graph)
        List<List<Integer>> courseMap = new ArrayList<>();
        for (int course = 0; course < totalCourses; course++) {
            courseMap.add(new ArrayList<>());
        }
        
        // Add prerequisites: prereq[1] must be taken before prereq[0]
        for (int[] prereq : prerequisites) {
            int advancedCourse = prereq[0];
            int basicCourse = prereq[1];
            
            // To take advanced course, need basic course first
            courseMap.get(basicCourse).add(advancedCourse);
        }
        
        // Traffic light colors for each course
        int[] trafficLights = new int[totalCourses]; // 0: white, 1: yellow, 2: green
        
        // Check each course for circular dependencies
        for (int course = 0; course < totalCourses; course++) {
            if (trafficLights[course] == 0) { // Unvisited course
                if (hasCircularDependency(course, courseMap, trafficLights)) {
                    return false; // Can't graduate - circular dependency!
                }
            }
        }
        
        return true; // All courses can be completed!
    }
    
    /**
     * DFS to detect circular dependencies
     * 
     * VISUALIZATION: Like following a chain of prerequisites
     * If we see a yellow light (course in progress), we have a loop!
     */
    private static boolean hasCircularDependency(int currentCourse, 
                                                List<List<Integer>> courseMap, 
                                                int[] trafficLights) {
        // Mark course as in-progress (yellow light)
        trafficLights[currentCourse] = 1;
        
        // Check all courses that depend on this one
        for (int nextCourse : courseMap.get(currentCourse)) {
            if (trafficLights[nextCourse] == 1) {
                // Found a yellow light - circular dependency!
                return true;
            }
            
            if (trafficLights[nextCourse] == 0) { // White light - unvisited
                if (hasCircularDependency(nextCourse, courseMap, trafficLights)) {
                    return true;
                }
            }
            // Green light (2) means already completed - skip
        }
        
        // Mark course as completed (green light)
        trafficLights[currentCourse] = 2;
        return false;
    }
    
    /**
     * Get a valid course order (Topological Sort)
     */
    public static List<Integer> getValidCourseOrder(int totalCourses, int[][] prerequisites) {
        List<List<Integer>> courseMap = new ArrayList<>();
        for (int i = 0; i < totalCourses; i++) {
            courseMap.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            courseMap.get(prereq[1]).add(prereq[0]);
        }
        
        int[] colors = new int[totalCourses];
        List<Integer> courseOrder = new ArrayList<>();
        
        for (int course = 0; course < totalCourses; course++) {
            if (colors[course] == 0) {
                if (!dfsForOrder(course, courseMap, colors, courseOrder)) {
                    return new ArrayList<>(); // No valid order
                }
            }
        }
        
        Collections.reverse(courseOrder); // Reverse to get correct order
        return courseOrder;
    }
    
    private static boolean dfsForOrder(int course, List<List<Integer>> graph, 
                                      int[] colors, List<Integer> order) {
        colors[course] = 1; // Yellow
        
        for (int next : graph.get(course)) {
            if (colors[next] == 1) return false; // Cycle
            if (colors[next] == 0) {
                if (!dfsForOrder(next, graph, colors, order)) return false;
            }
        }
        
        colors[course] = 2; // Green
        order.add(course); // Add in reverse order
        return true;
    }
    
    /**
     * Visualize the course dependency graph
     */
    private static void visualizeCourseGraph(int numCourses, int[][] prerequisites) {
        System.out.println("=== 🎓 COURSE DEPENDENCY VISUALIZATION ===");
        System.out.printf("%d courses with %d prerequisites\n\n", numCourses, prerequisites.length);
        
        // Build and display dependencies
        System.out.println("Prerequisites:");
        for (int[] prereq : prerequisites) {
            System.out.printf("  Course %d requires Course %d first\n", prereq[0], prereq[1]);
        }
        
        // Check for cycles
        System.out.println("\nChecking for circular dependencies...");
        boolean canFinish = canGraduate_TrafficLightMethod(numCourses, prerequisites);
        
        if (canFinish) {
            System.out.println("✅ No cycles detected! You can graduate!");
            
            List<Integer> order = getValidCourseOrder(numCourses, prerequisites);
            if (!order.isEmpty()) {
                System.out.print("\nValid course order: ");
                for (int i = 0; i < order.size(); i++) {
                    System.out.print(order.get(i));
                    if (i < order.size() - 1) System.out.print(" → ");
                }
                System.out.println();
            }
        } else {
            System.out.println("❌ Circular dependency detected! Cannot complete all courses!");
        }
    }
    
    /**
     * Show the traffic light algorithm step by step
     */
    private static void demonstrateTrafficLightAlgorithm() {
        System.out.println("\n=== 😦 TRAFFIC LIGHT ALGORITHM ===");
        System.out.println("\nHow the algorithm works:");
        System.out.println("1. WHITE (0): Course not started");
        System.out.println("2. YELLOW (1): Course in progress");
        System.out.println("3. GREEN (2): Course completed");
        
        System.out.println("\nCycle Detection:");
        System.out.println("- Start DFS from any white course");
        System.out.println("- Turn it yellow when we enter");
        System.out.println("- If we see another yellow → CYCLE!");
        System.out.println("- Turn it green when we exit");
        
        System.out.println("\nExample with cycle:");
        System.out.println("Courses: A → B → C → A");
        System.out.println("1. Visit A (turn yellow)");
        System.out.println("2. Visit B (turn yellow)");
        System.out.println("3. Visit C (turn yellow)");
        System.out.println("4. Try to visit A - it's yellow! CYCLE DETECTED!");
    }
    
    /**
     * Show real-world applications
     */
    private static void showApplications() {
        System.out.println("\n=== 🌍 REAL-WORLD APPLICATIONS ===");
        System.out.println("\nWhere cycle detection is crucial:");
        System.out.println("1. 🎓 Course Prerequisites: Avoiding circular dependencies");
        System.out.println("2. 📦 Package Dependencies: npm, pip, maven");
        System.out.println("3. 🏭 Build Systems: Makefile dependencies");
        System.out.println("4. 📊 Spreadsheet Formulas: Circular references");
        System.out.println("5. 🗺️ Task Scheduling: Project management");
        System.out.println("6. 💰 Deadlock Detection: Database transactions");
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("🎓 COURSE SCHEDULE - Traffic Light Cycle Detection!\n");
        
        // Test 1: Valid course schedule
        System.out.println("Test 1 - Valid course schedule:");
        int[][] prereqs1 = {{1,0}, {2,1}, {3,2}};
        visualizeCourseGraph(4, prereqs1);
        
        // Test 2: Course schedule with cycle
        System.out.println("\nTest 2 - Course schedule with cycle:");
        int[][] prereqs2 = {{1,0}, {2,1}, {3,2}, {1,3}};
        visualizeCourseGraph(4, prereqs2);
        
        // Test 3: Complex dependencies
        System.out.println("\nTest 3 - Complex course network:");
        int[][] prereqs3 = {{1,0}, {2,0}, {3,1}, {3,2}, {4,3}};
        visualizeCourseGraph(5, prereqs3);
        
        // Test 4: No prerequisites
        System.out.println("\nTest 4 - No prerequisites:");
        int[][] prereqs4 = {};
        visualizeCourseGraph(3, prereqs4);
        
        // Test 5: Algorithm explanation
        demonstrateTrafficLightAlgorithm();
        
        // Test 6: Applications
        showApplications();
        
        // Test 7: Performance test
        System.out.println("\nTest 7 - Performance test:");
        int n = 1000;
        int[][] chainPrereqs = new int[n-1][2];
        
        // Create a long chain
        for (int i = 0; i < n-1; i++) {
            chainPrereqs[i] = new int[]{i+1, i};
        }
        
        long start = System.nanoTime();
        boolean result = canGraduate_TrafficLightMethod(n, chainPrereqs);
        long time = System.nanoTime() - start;
        
        System.out.printf("Chain of %d courses: %s in %.3f ms\n", 
                         n, result ? "Valid" : "Has cycle", time / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- TRAFFIC LIGHTS: White → Yellow → Green");
        System.out.println("- Yellow light = course in progress");
        System.out.println("- If we see yellow while yellow = CYCLE!");
        System.out.println("- Used for: courses, packages, build systems");
        System.out.println("- This is topological sort with cycle detection");
    }
}