package drmario.app;

import drmario.logic.ScoreManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dr. Mario - ScoreManager Test ===\n");
        
        ScoreManager score = new ScoreManager();
        
        // Test 1: Initial state
        System.out.println("Test 1: Initial state");
        System.out.println("  " + score);
        System.out.println("  Score: " + score.getScore() + " (expected 0)");
        System.out.println("  Level: " + score.getLevel() + " (expected 1)");
        System.out.println("  Speed: " + score.getFallSpeed() + "s");
        
        // Test 2: Clear pills
        System.out.println("\nTest 2: Clear 4 pills");
        score.addPillsCleared(4);
        System.out.println("  Score: " + score.getScore() + " (expected 40)");
        
        // Test 3: Clear viruses
        System.out.println("\nTest 3: Clear 3 viruses");
        score.addVirusesCleared(3);
        System.out.println("  Score: " + score.getScore() + " (expected 340)");
        
        // Test 4: Complete level
        System.out.println("\nTest 4: Complete level");
        score.completeLevel();
        System.out.println("  Score: " + score.getScore() + " (expected 1340)");
        System.out.println("  Level: " + score.getLevel() + " (expected 2)");
        System.out.println("  Speed: " + score.getFallSpeed() + "s (was 1.0)");
        
        // Test 5: Level progression
        System.out.println("\nTest 5: Level progression");
        while (score.getLevel() < 20) {
            score.completeLevel();
            System.out.printf("  Level: %d, Speed: %.2fs%n", 
                score.getLevel(), score.getFallSpeed());
        }
        
        // Test 6: Reset
        System.out.println("\nTest 6: Reset");
        score.reset();
        System.out.println("  " + score);
        System.out.println("  Score: " + score.getScore() + " (expected 0)");
        System.out.println("  Level: " + score.getLevel() + " (expected 1)");
    }
}