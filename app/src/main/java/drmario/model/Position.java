package drmario.model;

import java.util.Objects;

/**
 * Represents a position on the board.
 * <p>
 * Immutable — row and col cannot be changed after creation.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class Position {
    
    private final int row;
    private final int col;
    
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public int getRow() { return row; }
    public int getCol() { return col; }
    
    /**
     * Returns a new position offset by (dRow, dCol).
     *
     * @param dRow row delta
     * @param dCol column delta
     * @return new position
     */
    public Position offset(int dRow, int dCol) {
        return new Position(row + dRow, col + dCol);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
