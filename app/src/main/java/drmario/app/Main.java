package drmario.app;

import drmario.model.Board;
import drmario.model.Cell;
import drmario.model.PillColor;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dr. Mario - Test ===");
        
        // Create board
        Board board = new Board();
        System.out.println("Board created: " + Board.WIDTH + "x" + Board.HEIGHT);
        
        // Set some cells
        board.setCell(15, 0, new Cell(Cell.Type.PILL, PillColor.RED));
        board.setCell(15, 1, new Cell(Cell.Type.PILL, PillColor.BLUE));
        board.setCell(15, 2, new Cell(Cell.Type.VIRUS, PillColor.GREEN));
        
        // Print board
        System.out.println("\nBoard:");
        board.print();
        
        // Test random color
        System.out.println("\nRandom color: " + PillColor.random());
    }
}