package com.demo.codesignal;

import java.util.*;

public class WaterTrap {
    
    public static int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int n = height.length;
        int left = 0, right = n - 1;
        int leftMax = 0, rightMax = 0;
        int water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }
        
        return water;
    }
    
    public static int trapBruteForce(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int water = 0;
        
        for (int i = 1; i < height.length - 1; i++) {
            int leftMax = 0;
            for (int j = 0; j <= i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            
            int rightMax = 0;
            for (int j = i; j < height.length; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            
            water += Math.min(leftMax, rightMax) - height[i];
        }
        
        return water;
    }
    
    public static int trapDynamicProgramming(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        int water = 0;
        
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return water;
    }
    
    public static int trapStack(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        
        Stack<Integer> stack = new Stack<>();
        int water = 0;
        
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                
                if (stack.isEmpty()) {
                    break;
                }
                
                int distance = i - stack.peek() - 1;
                int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[top];
                water += distance * boundedHeight;
            }
            stack.push(i);
        }
        
        return water;
    }
    
    public static void main(String[] args) {
        int[] height1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Test case 1:");
        System.out.println("Height array: " + Arrays.toString(height1));
        System.out.println("Water trapped (Two Pointers): " + trap(height1));
        System.out.println("Water trapped (Brute Force): " + trapBruteForce(height1));
        System.out.println("Water trapped (Dynamic Programming): " + trapDynamicProgramming(height1));
        System.out.println("Water trapped (Stack): " + trapStack(height1));
        
        int[] height2 = {4, 2, 0, 3, 2, 5};
        System.out.println("\nTest case 2:");
        System.out.println("Height array: " + Arrays.toString(height2));
        System.out.println("Water trapped (Two Pointers): " + trap(height2));
        System.out.println("Water trapped (Brute Force): " + trapBruteForce(height2));
        System.out.println("Water trapped (Dynamic Programming): " + trapDynamicProgramming(height2));
        System.out.println("Water trapped (Stack): " + trapStack(height2));
        
        int[] height3 = {3, 0, 2, 0, 4};
        System.out.println("\nTest case 3:");
        System.out.println("Height array: " + Arrays.toString(height3));
        System.out.println("Water trapped (Two Pointers): " + trap(height3));
        System.out.println("Water trapped (Brute Force): " + trapBruteForce(height3));
        System.out.println("Water trapped (Dynamic Programming): " + trapDynamicProgramming(height3));
        System.out.println("Water trapped (Stack): " + trapStack(height3));
        
        int[] height4 = {5, 4, 3, 2, 1};
        System.out.println("\nTest case 4 (Descending):");
        System.out.println("Height array: " + Arrays.toString(height4));
        System.out.println("Water trapped: " + trap(height4));
        
        int[] height5 = {1, 2, 3, 4, 5};
        System.out.println("\nTest case 5 (Ascending):");
        System.out.println("Height array: " + Arrays.toString(height5));
        System.out.println("Water trapped: " + trap(height5));
    }
}