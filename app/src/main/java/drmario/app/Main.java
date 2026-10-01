package drmario.app;

import drmario.logic.Gravity;
import drmario.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dr. Mario - Test ===");
        
        Board board = new Board();
        
        // Test 1: Capsule in empty board — can move down
        Capsule capsule = new Capsule(0, 3, PillColor.RED, PillColor.BLUE, Direction.HORIZONTAL);
        System.out.println("Test 1: Capsule at top, empty board");
        System.out.println("  " + capsule);
        System.out.println("  Can move down? " + Gravity.canMoveDown(capsule, board));
        System.out.println("  Can move left? " + Gravity.canMoveLeft(capsule, board));
        System.out.println("  Can move right? " + Gravity.canMoveRight(capsule, board));
        System.out.println("  Can rotate? " + Gravity.canRotate(capsule, board));
        
        // Test 2: Capsule at left edge — cannot move left
        Capsule leftEdge = new Capsule(0, 0, PillColor.RED, PillColor.BLUE, Direction.HORIZONTAL);
        System.out.println("\nTest 2: Capsule at left edge");
        System.out.println("  " + leftEdge);
        System.out.println("  Can move left? " + Gravity.canMoveLeft(leftEdge, board));
        
        // Test 3: Capsule at right edge — cannot move right
        Capsule rightEdge = new Capsule(0, 6, PillColor.RED, PillColor.BLUE, Direction.HORIZONTAL);
        System.out.println("\nTest 3: Capsule at right edge");
        System.out.println("  " + rightEdge);
        System.out.println("  Can move right? " + Gravity.canMoveRight(rightEdge, board));
        
        // Test 4: Capsule above another pill — cannot move down
        board.setCell(5, 3, new Cell(Cell.Type.PILL, PillColor.GREEN));
        Capsule above = new Capsule(3, 3, PillColor.RED, PillColor.BLUE, Direction.VERTICAL);
        System.out.println("\nTest 4: Capsule above existing pill");
        System.out.println("  " + above);
        System.out.println("  Can move down? " + Gravity.canMoveDown(above, board));
        
        // Test 5: Lock capsule into board
        System.out.println("\nTest 5: Lock capsule into board");
        Capsule toLock = new Capsule(15, 3, PillColor.RED, PillColor.BLUE, Direction.HORIZONTAL);
        Gravity.lockCapsule(toLock, board);
        System.out.println("  After lock:");
        board.print();
    }
}