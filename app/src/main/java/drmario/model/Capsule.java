package drmario.model;

import java.util.Objects;

/**
 * Represents a falling capsule in Dr. Mario.
 * <p>
 * A capsule has two halves with different colors.
 * The position refers to the top-left half.
 * <p>
 * Orientation:
 * <ul>
 *     <li>HORIZONTAL: [A][B] — side by side</li>
 *     <li>VERTICAL: [A] on top, [B] below</li>
 * </ul>
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class Capsule {
    
    private int row;
    private int col;
    private PillColor firstColor;
    private PillColor secondColor;
    private Direction direction;
    
    /**
     * Creates a new capsule at the given position.
     *
     * @param row         the row of the first half
     * @param col         the column of the first half
     * @param firstColor  color of the first half
     * @param secondColor color of the second half
     * @param direction   orientation (HORIZONTAL or VERTICAL)
     */
    public Capsule(int row, int col, PillColor firstColor, PillColor secondColor, Direction direction) {
        this.row = row;
        this.col = col;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.direction = direction;
    }
    
    /**
     * Creates a random capsule at the top of the board.
     *
     * @param row the starting row
     * @param col the starting column
     * @return a new random capsule
     */
    public static Capsule random(int row, int col) {
        return new Capsule(
            row, col,
            PillColor.random(),
            PillColor.random(),
            Direction.HORIZONTAL
        );
    }
    
    // ========== GETTERS ==========
    
    public int getRow() { return row; }
    public int getCol() { return col; }
    public PillColor getFirstColor() { return firstColor; }
    public PillColor getSecondColor() { return secondColor; }
    public Direction getDirection() { return direction; }
    
    /**
     * Returns the row of the second half.
     *
     * @return the row of the second half
     */
    public int getSecondRow() {
        return switch (direction) {
            case HORIZONTAL, HORIZONTAL_REVERSED -> row;
            case VERTICAL -> row + 1;
            case VERTICAL_REVERSED -> row - 1;
        };
    }

    /**
     * Returns the column of the second half.
     *
     * @return the column of the second half
     */
    public int getSecondCol() {
        return switch (direction) {
            case VERTICAL, VERTICAL_REVERSED -> col;
            case HORIZONTAL -> col + 1;
            case HORIZONTAL_REVERSED -> col - 1;
        };
    }
    
    // ========== MOVEMENT ==========
    
    /**
     * Moves the capsule one cell down.
     */
    public void moveDown() {
        row++;
    }
    
    /**
     * Moves the capsule one cell left.
     */
    public void moveLeft() {
        col--;
    }
    
    /**
     * Moves the capsule one cell right.
     */
    public void moveRight() {
        col++;
    }
    
    /**
     * Rotates the capsule 90 degrees clockwise.
     * <p>
     * Cycle: HORIZONTAL → VERTICAL → HORIZONTAL_REVERSED → VERTICAL_REVERSED → HORIZONTAL
     */
    public void rotate() {
        direction = switch (direction) {
            case HORIZONTAL -> Direction.VERTICAL;
            case VERTICAL -> Direction.HORIZONTAL_REVERSED;
            case HORIZONTAL_REVERSED -> Direction.VERTICAL_REVERSED;
            case VERTICAL_REVERSED -> Direction.HORIZONTAL;
        };
    }
    
    /**
     * Returns a copy of this capsule.
     * <p>
     * Useful for collision checks without modifying the original.
     *
     * @return a new capsule with the same state
     */
    public Capsule copy() {
        return new Capsule(row, col, firstColor, secondColor, direction);
    }
    
    // ========== EQUALS / HASHCODE ==========
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capsule capsule = (Capsule) o;
        return row == capsule.row 
            && col == capsule.col 
            && firstColor == capsule.firstColor 
            && secondColor == capsule.secondColor 
            && direction == capsule.direction;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(row, col, firstColor, secondColor, direction);
    }
    
    @Override
    public String toString() {
        return String.format("Capsule {pos=(%d,%d), colors=[%s,%s], dir=%s} ",
            row, col, firstColor, secondColor, direction);
    }
}