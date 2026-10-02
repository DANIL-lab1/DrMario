package drmario.app;

import drmario.logic.GameEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dr. Mario - GameEngine Test ===\n");
        
        // Test 1: Create engine
        System.out.println("Test 1: Create engine");
        GameEngine engine = new GameEngine();
        System.out.println("  Game Over: " + engine.isGameOver());
        System.out.println("  Score: " + engine.getScore().getScore());
        System.out.println("  Level: " + engine.getScore().getLevel());
        System.out.println("  Current capsule: " + engine.getCurrentCapsule());
        System.out.println("  Next capsule: " + engine.getNextCapsule());
        
        // Test 2: Move capsule
        System.out.println("\nTest 2: Move capsule");
        System.out.println("  Before: " + engine.getCurrentCapsule());
        engine.moveLeft();
        System.out.println("  After left: " + engine.getCurrentCapsule());
        engine.moveRight();
        engine.moveRight();
        System.out.println("  After right x2: " + engine.getCurrentCapsule());
        
        // Test 3: Rotate
        System.out.println("\nTest 3: Rotate");
        System.out.println("  Before: " + engine.getCurrentCapsule());
        engine.rotate();
        System.out.println("  After: " + engine.getCurrentCapsule());
        
        // Test 4: Drop capsule
        System.out.println("\nTest 4: Drop capsule");
        engine.drop();
        System.out.println("  Score: " + engine.getScore().getScore());
        System.out.println("  Board after drop:");
        engine.getBoard().print();
        System.out.println("  New capsule: " + engine.getCurrentCapsule());
        
        // Test 5: Multiple drops
        System.out.println("\nTest 5: 5 drops");
        for (int i = 0; i < 5; i++) {
            engine.drop();
        }
        System.out.println("  Score: " + engine.getScore().getScore());
        System.out.println("  Board:");
        engine.getBoard().print();
        
        // Test 6: Game Over check
        System.out.println("\nTest 6: Game Over check");
        System.out.println("  Game Over: " + engine.isGameOver());
    }
}