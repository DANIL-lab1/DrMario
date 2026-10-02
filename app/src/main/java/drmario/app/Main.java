package drmario.app;

import drmario.logic.Matcher;
import drmario.logic.VirusSpawner;
import drmario.model.Board;

public class Main {
    public static void main(String[] args) {
        
        // Test 1: Spawn 5 viruses
        System.out.println("Test 1: Spawn 5 viruses");
        Board board1 = new Board();
        VirusSpawner spawner = new VirusSpawner();
        int spawned = spawner.spawn(board1, 5);
        System.out.println("Spawned: " + spawned);
        board1.print();
        
        // Test 2: Count viruses
        System.out.println("\nTest 2: Count viruses on board");
        int virusCount = 0;
        for (int row = 0; row < Board.HEIGHT; row++) {
            for (int col = 0; col < Board.WIDTH; col++) {
                if (board1.getCell(row, col).isVirus()) {
                    virusCount++;
                }
            }
        }
        System.out.println("Viruses on board: " + virusCount);
        
        // Test 3: Viruses for level
        System.out.println("\nTest 3: Viruses per level");
        for (int level = 1; level <= 5; level++) {
            System.out.println("  Level " + level + ": " + VirusSpawner.virusesForLevel(level) + " viruses");
        }
        
        // Test 4: Spawn many viruses
        System.out.println("\nTest 4: Spawn 20 viruses");
        Board board2 = new Board();
        int spawned2 = spawner.spawn(board2, 20);
        System.out.println("Spawned: " + spawned2);
        board2.print();
        
        // Test 5: Check matches with viruses
        System.out.println("\nTest 5: Virus matching");
        Board board3 = new Board();
        // Place 3 viruses + 1 pill of same color
        board3.addVirus(15, 2, drmario.model.PillColor.RED);
        board3.addVirus(15, 3, drmario.model.PillColor.RED);
        board3.addVirus(15, 4, drmario.model.PillColor.RED);
        board3.setCell(15, 5, new drmario.model.Cell(
            drmario.model.Cell.Type.PILL, 
            drmario.model.PillColor.RED
        ));
        board3.print();
        
        int matches = Matcher.findMatches(board3).size();
        System.out.println("Matches found: " + matches + " (expected 1 - 3 viruses + 1 pill)");
    }
}