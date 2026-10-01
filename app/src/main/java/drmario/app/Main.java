package drmario.app;

import drmario.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dr. Mario - Test ===");
        
         // Create a capsule
        Capsule capsule = new Capsule(0, 3, PillColor.RED, PillColor.BLUE, Direction.HORIZONTAL);
        System.out.println("Created: " + capsule);
        System.out.println("First half:  (" + capsule.getRow() + ", " + capsule.getCol() + ") = " + capsule.getFirstColor());
        System.out.println("Second half: (" + capsule.getSecondRow() + ", " + capsule.getSecondCol() + ") = " + capsule.getSecondColor());
    
        // Rotate
        capsule.rotate();
        System.out.println("\nAfter rotation: " + capsule);
        System.out.println("First half:  (" + capsule.getRow() + ", " + capsule.getCol() + ")");
        System.out.println("Second half: (" + capsule.getSecondRow() + ", " + capsule.getSecondCol() + ")");
        
        // Move down
        capsule.moveDown();
        System.out.println("\nAfter move down: " + capsule);
        
        // Move right
        capsule.moveRight();
        System.out.println("After move right: " + capsule);
        
        // Copy
        Capsule copy = capsule.copy();
        System.out.println("\nCopy: " + copy);
        System.out.println("Copy equals original: " + capsule.equals(copy));
        
        // Random capsule
        Capsule random = Capsule.random(0, 4);
        System.out.println("\nRandom capsule: " + random);
    }
}