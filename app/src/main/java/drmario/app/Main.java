package drmario.app;

import drmario.logic.Matcher;
import drmario.model.*;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        
        // Test 1: 4 in a row (horizontal)
        System.out.println("Test 1: 4 horizontal RED");
        Board board1 = new Board();
        for (int col = 2; col < 6; col++) {
            board1.setCell(15, col, new Cell(Cell.Type.PILL, PillColor.RED));
        }
        board1.print();
        List<Set<Position>> matches1 = Matcher.findMatches(board1);
        System.out.println("Matches found: " + matches1.size());
        System.out.println("Group size: " + matches1.get(0).size());
        
        // Test 2: 4 in a column (vertical)
        System.out.println("\nTest 2: 4 vertical BLUE");
        Board board2 = new Board();
        for (int row = 12; row < 16; row++) {
            board2.setCell(row, 3, new Cell(Cell.Type.PILL, PillColor.BLUE));
        }
        board2.print();
        List<Set<Position>> matches2 = Matcher.findMatches(board2);
        System.out.println("Matches found: " + matches2.size());
        
        // Test 3: Only 3 — no match
        System.out.println("\nTest 3: Only 3 GREEN");
        Board board3 = new Board();
        for (int col = 2; col < 5; col++) {
            board3.setCell(15, col, new Cell(Cell.Type.PILL, PillColor.GREEN));
        }
        board3.print();
        List<Set<Position>> matches3 = Matcher.findMatches(board3);
        System.out.println("Matches found: " + matches3.size() + " (expected 0)");
        
        // Test 4: L-shaped group (5 cells)
        System.out.println("\nTest 4: L-shaped RED (5 cells)");
        Board board4 = new Board();
        board4.setCell(15, 2, new Cell(Cell.Type.PILL, PillColor.RED));
        board4.setCell(15, 3, new Cell(Cell.Type.PILL, PillColor.RED));
        board4.setCell(15, 4, new Cell(Cell.Type.PILL, PillColor.RED));
        board4.setCell(14, 2, new Cell(Cell.Type.PILL, PillColor.RED));
        board4.setCell(13, 2, new Cell(Cell.Type.PILL, PillColor.RED));
        board4.print();
        List<Set<Position>> matches4 = Matcher.findMatches(board4);
        System.out.println("Matches found: " + matches4.size());
        System.out.println("Group size: " + matches4.get(0).size());
        
        // Test 5: Clear matches
        System.out.println("\nTest 5: Clear matches");
        System.out.println("Before clear:");
        board1.print();
        int cleared = Matcher.clearMatches(board1);
        System.out.println("Cleared cells: " + cleared);
        System.out.println("After clear:");
        board1.print();
    }
}