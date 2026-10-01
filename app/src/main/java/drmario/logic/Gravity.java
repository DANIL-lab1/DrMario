package drmario.logic;

import drmario.model.Board;
import drmario.model.Capsule;
import drmario.model.Direction;

/**
 * Provides collision detection and movement validation for capsules.
 * <p>
 * All methods are static — this is a utility class.
 * The class does NOT modify the capsule; it only checks if movement is possible.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class Gravity {
    
    /**
     * Checks if the capsule can move down without collision.
     *
     * @param capsule the capsule to check
     * @param board   the game board
     * @return true if move down is valid, false otherwise
     */
    public static boolean canMoveDown(Capsule capsule, Board board){
        Capsule moved = capsule.copy();
        moved.moveDown();
        return isValidPosition(moved, board);
    }
    
    /**
     * Checks if the capsule can move left without collision.
     *
     * @param capsule the capsule to check
     * @param board   the game board
     * @return true if move left is valid, false otherwise
     */
    public static boolean canMoveLeft(Capsule capsule, Board board) {
        Capsule moved = capsule.copy();
        moved.moveLeft();
        return isValidPosition(moved, board);
    }
    
    /**
     * Checks if the capsule can move right without collision.
     *
     * @param capsule the capsule to check
     * @param board   the game board
     * @return true if move right is valid, false otherwise
     */
    public static boolean canMoveRight(Capsule capsule, Board board) {
        Capsule moved = capsule.copy();
        moved.moveRight();
        return isValidPosition(moved, board);
    }
    
    /**
     * Checks if the capsule can rotate without collision.
     * <p>
     * Rotation is valid if:
     * <ul>
     *     <li>The new position is within the board</li>
     *     <li>The new position is empty (no pills or viruses)</li>
     * </ul>
     *
     * @param capsule the capsule to check
     * @param board   the game board
     * @return true if rotation is valid, false otherwise
     */
    public static boolean canRotate(Capsule capsule, Board board){
        Capsule rotated = capsule.copy();
        rotated.rotate();
        
        // After rotation, capsule might be pushed out of bounds
        // Try to "kick" it back into the board
        if (!isValidPosition(rotated, board)){
            // Try moving left
            if (rotated.getCol() >= 0 && rotated.getSecondCol() < Board.WIDTH) {
                return false;
            }
            return false;
        }
        return true;
    }
    
    /**
     * Checks if the capsule is in a valid position.
     * <p>
     * A position is valid if:
     * <ul>
     *     <li>Both halves are within board boundaries</li>
     *     <li>Both halves are in empty cells</li>
     * </ul>
     *
     * @param capsule the capsule to check
     * @param board   the game board
     * @return true if position is valid, false otherwise
     */
    public static boolean isValidPosition(Capsule capsule, Board board){
        int row1 = capsule.getRow();
        int col1 = capsule.getCol();
        int row2 = capsule.getSecondRow();
        int col2 = capsule.getSecondCol();
        
        // Check boundaries
        if (!board.isValidPosition(row1, col1) || !board.isValidPosition(row2, col2)){
            return false;
        }
        
        // Check if cells are empty
        if (!board.isEmpty(row1, col1) || !board.isEmpty(row2, col2)){
            return false;
        }
        
        return true;
    }
    
    /**
     * Locks the capsule into the board.
     * <p>
     * Places the capsule's halves as PILL cells on the board.
     * Used when the capsule reaches the bottom or hits another pill.
     *
     * @param capsule the capsule to lock
     * @param board   the game board
     */
    public static void lockCapsule(Capsule capsule, Board board){
        board.setCell(
            capsule.getRow(),
            capsule.getCol(),
            new drmario.model.Cell(drmario.model.Cell.Type.PILL, capsule.getFirstColor())
        );
        
        board.setCell(
            capsule.getSecondRow(), 
            capsule.getSecondCol(), 
            new drmario.model.Cell(drmario.model.Cell.Type.PILL, capsule.getSecondColor())
        );
    }
}
