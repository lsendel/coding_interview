package com.demo.graph;

import java.util.*;

/**
 * Problem: Is Graph Bipartite?
 * 
 * MNEMONIC: "RED vs BLUE TEAM - No teammates as neighbors!"
 * 🔴🔵 Like dividing players into two teams where teammates can't be adjacent
 * 
 * PROBLEM: Determine if a graph can be colored with two colors such that
 * no adjacent nodes have the same color.
 * Example: graph = [[1,3], [0,2], [1,3], [0,2]]
 * Answer: true (nodes 0,2 are RED; nodes 1,3 are BLUE)
 * 
 * TECHNIQUE: BFS/DFS with Coloring
 * - Try to color graph with two colors
 * - If we find conflict (adjacent same color), not bipartite
 * 
 * VISUALIZATION:
 * Graph: 0 -- 1
 *        |    |
 *        3 -- 2
 * 
 * Coloring:
 * Color 0 RED → neighbors 1,3 must be BLUE
 * Color 1 BLUE → neighbors 0,2 must be RED
 * Color 2 RED → neighbors 1,3 must be BLUE ✓
 * Color 3 BLUE → neighbors 0,2 must be RED ✓
 * Success! Graph is bipartite
 * 
 * Time Complexity: O(V + E)
 * Space Complexity: O(V) for colors array
 */
public class BipartiteGraph {
    
    /**
     * Check if graph can be divided into two teams
     * 
     * REMEMBER: "TEAM ASSIGNMENT"
     * Assign players to RED or BLUE team, no teammates adjacent
     * 
     * @param playerConnections adjacency list representation
     * @return true if graph is bipartite
     */
    public static boolean canFormTwoTeams_TeamAssignment(int[][] playerConnections) {
        int totalPlayers = playerConnections.length;
        
        // Team assignments: 0=unassigned, 1=RED team, 2=BLUE team
        int[] teamColors = new int[totalPlayers];
        
        // Check each unassigned player (handles disconnected components)
        for (int player = 0; player < totalPlayers; player++) {
            if (teamColors[player] == 0) { // Unassigned player
                if (!assignTeamsBFS(player, playerConnections, teamColors)) {
                    return false; // Conflict found!
                }
            }
        }
        
        return true; // Successfully formed two teams!
    }
    
    /**
     * BFS to assign teams (colors)
     * 
     * VISUALIZATION: Like spreading team jerseys
     * Start with RED, neighbors get BLUE, and so on
     */
    private static boolean assignTeamsBFS(int startPlayer, 
                                         int[][] connections, 
                                         int[] teamColors) {
        Queue<Integer> assignmentQueue = new LinkedList<>();
        assignmentQueue.offer(startPlayer);
        teamColors[startPlayer] = 1; // Assign to RED team
        
        while (!assignmentQueue.isEmpty()) {
            int currentPlayer = assignmentQueue.poll();
            int currentTeam = teamColors[currentPlayer];
            int oppositeTeam = (currentTeam == 1) ? 2 : 1;
            
            // Check all connected players
            for (int connectedPlayer : connections[currentPlayer]) {
                if (teamColors[connectedPlayer] == 0) {
                    // Unassigned - give opposite team color
                    teamColors[connectedPlayer] = oppositeTeam;
                    assignmentQueue.offer(connectedPlayer);
                } else if (teamColors[connectedPlayer] == currentTeam) {
                    // Same team conflict! Can't be bipartite
                    return false;
                }
                // If opposite team, that's what we want - continue
            }
        }
        
        return true;
    }
    
    /**
     * DFS version for team assignment
     */
    public static boolean canFormTwoTeams_DFS(int[][] playerConnections) {
        int totalPlayers = playerConnections.length;
        int[] teamColors = new int[totalPlayers];
        
        for (int player = 0; player < totalPlayers; player++) {
            if (teamColors[player] == 0) {
                if (!assignTeamDFS(player, 1, playerConnections, teamColors)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static boolean assignTeamDFS(int player, int team, 
                                        int[][] connections, int[] teamColors) {
        teamColors[player] = team;
        int oppositeTeam = (team == 1) ? 2 : 1;
        
        for (int connected : connections[player]) {
            if (teamColors[connected] == 0) {
                // Recursively assign opposite team
                if (!assignTeamDFS(connected, oppositeTeam, connections, teamColors)) {
                    return false;
                }
            } else if (teamColors[connected] == team) {
                // Same team conflict!
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Visualize the team assignment process
     */
    private static void visualizeTeamAssignment(int[][] graph) {
        System.out.println("=== 🔴🔵 TEAM ASSIGNMENT VISUALIZATION ===");
        System.out.printf("Assigning %d players to teams...\n\n", graph.length);
        
        // Show connections
        System.out.println("Player connections:");
        for (int i = 0; i < graph.length; i++) {
            System.out.printf("Player %d connected to: ", i);
            for (int neighbor : graph[i]) {
                System.out.printf("%d ", neighbor);
            }
            System.out.println();
        }
        
        // Perform assignment with visualization
        int[] teams = new int[graph.length];
        boolean canAssign = true;
        
        System.out.println("\nTeam assignment process:");
        
        for (int player = 0; player < graph.length; player++) {
            if (teams[player] == 0) {
                System.out.printf("\nStarting new component with Player %d:\n", player);
                if (!visualizeAssignmentBFS(player, graph, teams)) {
                    canAssign = false;
                    break;
                }
            }
        }
        
        if (canAssign) {
            System.out.println("\n✅ Success! Teams formed:");
            System.out.print("RED Team 🔴: ");
            for (int i = 0; i < teams.length; i++) {
                if (teams[i] == 1) System.out.print(i + " ");
            }
            System.out.print("\nBLUE Team 🔵: ");
            for (int i = 0; i < teams.length; i++) {
                if (teams[i] == 2) System.out.print(i + " ");
            }
            System.out.println();
        } else {
            System.out.println("\n❌ Cannot form two teams! Graph is not bipartite.");
        }
    }
    
    private static boolean visualizeAssignmentBFS(int start, int[][] graph, int[] teams) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        teams[start] = 1;
        System.out.printf("  Assigned Player %d to RED team\n", start);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            String currentTeam = teams[current] == 1 ? "RED" : "BLUE";
            String oppositeTeam = teams[current] == 1 ? "BLUE" : "RED";
            
            for (int neighbor : graph[current]) {
                if (teams[neighbor] == 0) {
                    teams[neighbor] = teams[current] == 1 ? 2 : 1;
                    System.out.printf("  Player %d (%s) connects to Player %d → assign %s\n", 
                                    current, currentTeam, neighbor, oppositeTeam);
                    queue.offer(neighbor);
                } else if (teams[neighbor] == teams[current]) {
                    System.out.printf("  ❌ CONFLICT: Player %d (%s) connects to Player %d (also %s)!\n", 
                                    current, currentTeam, neighbor, currentTeam);
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Show different graph types
     */
    private static void demonstrateGraphTypes() {
        System.out.println("\n=== 🌐 BIPARTITE vs NON-BIPARTITE GRAPHS ===");
        
        System.out.println("\nBIPARTITE GRAPHS (can form two teams):");
        System.out.println("1. Even Cycles: Square, Hexagon, etc.");
        System.out.println("   0--1    0--1");
        System.out.println("   |  |    |  |");
        System.out.println("   3--2    5--2");
        System.out.println("           |  |");
        System.out.println("           4--3");
        
        System.out.println("\n2. Trees (no cycles):");
        System.out.println("       0");
        System.out.println("      / \\");
        System.out.println("     1   2");
        System.out.println("    / \\");
        System.out.println("   3   4");
        
        System.out.println("\nNON-BIPARTITE GRAPHS (cannot form two teams):");
        System.out.println("1. Odd Cycles: Triangle, Pentagon, etc.");
        System.out.println("   0--1    0--1");
        System.out.println("   \\  /    |  |");
        System.out.println("     2      4--2");
        System.out.println("            \\  |");
        System.out.println("              3");
        
        System.out.println("\nKEY INSIGHT: Odd cycles make bipartite impossible!");
    }
    
    /**
     * Real-world applications
     */
    private static void showApplications() {
        System.out.println("\n=== 🌍 REAL-WORLD APPLICATIONS ===");
        System.out.println("\nWhere bipartite graphs appear:");
        System.out.println("1. 🎭 Job Matching: Workers ↔ Jobs");
        System.out.println("2. 🏫 Course Scheduling: Students ↔ Courses");
        System.out.println("3. 🏠 Real Estate: Buyers ↔ Houses");
        System.out.println("4. 📝 Dating Apps: Users ↔ Potential Matches");
        System.out.println("5. ⚽ Sports Leagues: Home teams ↔ Away teams");
        System.out.println("6. 🎨 2-Coloring Problems: Map coloring, scheduling");
    }
    
    /**
     * Main method to demonstrate the solution
     */
    public static void main(String[] args) {
        System.out.println("🔴🔵 BIPARTITE GRAPH - Red vs Blue Team Assignment!\n");
        
        // Test 1: Bipartite square
        System.out.println("Test 1 - Square graph (bipartite):");
        int[][] square = {{1,3}, {0,2}, {1,3}, {0,2}};
        visualizeTeamAssignment(square);
        
        // Test 2: Non-bipartite triangle
        System.out.println("\nTest 2 - Triangle graph (not bipartite):");
        int[][] triangle = {{1,2}, {0,2}, {0,1}};
        visualizeTeamAssignment(triangle);
        
        // Test 3: Tree (always bipartite)
        System.out.println("\nTest 3 - Tree structure:");
        int[][] tree = {{1,2}, {0,3,4}, {0}, {1}, {1}};
        visualizeTeamAssignment(tree);
        
        // Test 4: Disconnected components
        System.out.println("\nTest 4 - Multiple components:");
        int[][] disconnected = {{1}, {0}, {3}, {2}, {5}, {4}};
        visualizeTeamAssignment(disconnected);
        
        // Test 5: Graph types
        demonstrateGraphTypes();
        
        // Test 6: Applications
        showApplications();
        
        // Test 7: Performance comparison
        System.out.println("\nTest 7 - BFS vs DFS performance:");
        int n = 1000;
        int[][] largeGraph = new int[n][];
        
        // Create a large bipartite graph
        for (int i = 0; i < n; i++) {
            if (i < n/2) {
                largeGraph[i] = new int[]{n/2 + i % (n/2)};
            } else {
                largeGraph[i] = new int[]{i - n/2};
            }
        }
        
        long start1 = System.nanoTime();
        boolean result1 = canFormTwoTeams_TeamAssignment(largeGraph);
        long time1 = System.nanoTime() - start1;
        
        long start2 = System.nanoTime();
        boolean result2 = canFormTwoTeams_DFS(largeGraph);
        long time2 = System.nanoTime() - start2;
        
        System.out.printf("BFS: %s in %.3f ms\n", result1, time1 / 1000000.0);
        System.out.printf("DFS: %s in %.3f ms\n", result2, time2 / 1000000.0);
        
        // Memory tip
        System.out.println("\n💡 REMEMBER:");
        System.out.println("- RED vs BLUE: Two teams, no teammates adjacent");
        System.out.println("- Start coloring, propagate opposite colors");
        System.out.println("- Conflict = same color neighbors = not bipartite");
        System.out.println("- Odd cycles make bipartite impossible");
        System.out.println("- Trees are always bipartite");
    }
}