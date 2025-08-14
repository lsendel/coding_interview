package com.demo.codesignal;

import java.io.*;
import java.util.*;
import java.util.Collections;

/*
 * Sock pairing robot - pairs left and right socks of the same color
 */

class Sock {
    public int id;
    public String color;
    public String orientation;
    
    public Sock(int id, String color, String orientation) {
        this.id = id;
        this.color = color;
        this.orientation = orientation;
    }
    
    @Override
    public String toString() {
        return "Sock{id=" + id + ", color='" + color + "', orientation='" + orientation + "'}";
    }
}

class Pair {
    public Sock sock1;
    public Sock sock2;
    
    public Pair(Sock sock1, Sock sock2) {
        this.sock1 = sock1;
        this.sock2 = sock2;
    }
    
    @Override
    public String toString() {
        return "Pair: " + sock1.id + " (" + sock1.color + ", " + sock1.orientation + ") + " 
               + sock2.id + " (" + sock2.color + ", " + sock2.orientation + ")";
    }
}

public class SockPairingRobot {
    public static void main(String[] args) {
        List<Sock> socks = List.of(
            new Sock(1, "black", "left"),
            new Sock(2, "pink", "left"),
            new Sock(3, "pink", "right"),
            new Sock(4, "black", "right")
        );
        
        // Method 1: Simple approach with tracking used socks
        List<Pair> paired = pairSocksSimple(new ArrayList<>(socks));
        System.out.println("Method 1 - Simple pairing:");
        paired.forEach(pair -> System.out.println(pair.sock1.id + " : " + pair.sock2.id));
        
        System.out.println("\nMethod 2 - Optimized pairing:");
        // Method 2: More efficient approach using maps
        List<Pair> pairedOptimized = pairSocksOptimized(new ArrayList<>(socks));
        pairedOptimized.forEach(pair -> System.out.println(pair.sock1.id + " : " + pair.sock2.id));
        
        // Test with edge cases
        System.out.println("\nTesting edge cases:");
        testEdgeCases();
    }
    
    // Simple approach: O(n²) but easy to understand
    public static List<Pair> pairSocksSimple(List<Sock> socks) {
        List<Pair> paired = new ArrayList<>();
        Set<Integer> usedSocks = new HashSet<>();
        
        for (int i = 0; i < socks.size(); i++) {
            Sock sock1 = socks.get(i);
            if (usedSocks.contains(sock1.id)) continue;
            
            for (int j = i + 1; j < socks.size(); j++) {
                Sock sock2 = socks.get(j);
                if (usedSocks.contains(sock2.id)) continue;
                
                // Fix: Use .equals() for string comparison
                if (sock1.color.equals(sock2.color) && 
                    !sock1.orientation.equals(sock2.orientation)) {
                    paired.add(new Pair(sock1, sock2));
                    usedSocks.add(sock1.id);
                    usedSocks.add(sock2.id);
                    break; // Found a pair for sock1, move to next sock
                }
            }
        }
        return paired;
    }
    
    // Optimized approach: O(n) using hash maps
    public static List<Pair> pairSocksOptimized(List<Sock> socks) {
        List<Pair> paired = new ArrayList<>();
        Map<String, Sock> leftSocks = new HashMap<>();
        Map<String, Sock> rightSocks = new HashMap<>();
        
        // Separate socks by orientation and color
        for (Sock sock : socks) {
            if ("left".equals(sock.orientation)) {
                leftSocks.put(sock.color, sock);
            } else if ("right".equals(sock.orientation)) {
                rightSocks.put(sock.color, sock);
            }
        }
        
        // Pair socks of matching colors
        for (String color : leftSocks.keySet()) {
            if (rightSocks.containsKey(color)) {
                paired.add(new Pair(leftSocks.get(color), rightSocks.get(color)));
            }
        }
        
        return paired;
    }
    
    public static void testEdgeCases() {
        // Test case 1: No matching pairs
        List<Sock> noMatches = List.of(
            new Sock(1, "black", "left"),
            new Sock(2, "pink", "left")
        );
        System.out.println("No matches: " + pairSocksSimple(new ArrayList<>(noMatches)).size() + " pairs");
        
        // Test case 2: Multiple socks of same color and orientation
        List<Sock> multipleSame = List.of(
            new Sock(1, "black", "left"),
            new Sock(2, "black", "left"),
            new Sock(3, "black", "right")
        );
        System.out.println("Multiple same: " + pairSocksSimple(new ArrayList<>(multipleSame)).size() + " pairs");
        
        // Test case 3: Many colors
        List<Sock> manyColors = List.of(
            new Sock(1, "red", "left"),
            new Sock(2, "blue", "right"),
            new Sock(3, "red", "right"),
            new Sock(4, "green", "left"),
            new Sock(5, "blue", "left"),
            new Sock(6, "green", "right")
        );
        List<Pair> manyPairs = pairSocksSimple(new ArrayList<>(manyColors));
        System.out.println("Many colors: " + manyPairs.size() + " pairs");
        manyPairs.forEach(pair -> System.out.println("  " + pair));
    }
}