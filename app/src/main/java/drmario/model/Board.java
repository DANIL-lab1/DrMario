package drmario.model;

/**
 * Represents the game board.
 * <p>
 * The board is a 2D grid of cells with dimensions:
 * <ul>
 *     <li>Width: 8 columns</li>
 *     <li>Height: 16 rows</li>
 * </ul>
 * <p>
 * Row 0 is the top, row 15 is the bottom.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class Board {
    
    public static final int WIDTH = 8;
    public static final int HEIGHT = 16;
    
    private final Cell[][] cells;
    
    /**
     * Creates an empty board.
     */
    public Board() {
        this.cells = new Cell[HEIGHT][WIDTH];
        clear();
    }
    
    /**
     * Fills the board with empty cells.
     */
    public void clear() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                cells[row][col] = new Cell();
            }
        }
    }
    
    /**
     * Gets a cell at the given position.
     *
     * @param row the row (0 = top)
     * @param col the column (0 = left)
     * @return the cell
     * @throws IndexOutOfBoundsException if position is invalid
     */
    public Cell getCell(int row, int col) {
        if (!isValidPosition(row, col)) {
            throw new IndexOutOfBoundsException("Invalid position: " + row + ", " + col);
        }
        return cells[row][col];
    }
    
    /**
     * Sets a cell at the given position.
     *
     * @param row  the row (0 = top)
     * @param col  the column (0 = left)
     * @param cell the cell to set
     */
    public void setCell(int row, int col, Cell cell) {
        if (!isValidPosition(row, col)) {
            throw new IndexOutOfBoundsException("Invalid position: " + row + ", " + col);
        }
        cells[row][col] = cell;
    }
    
    /**
     * Checks if a position is within the board.
     *
     * @param row the row
     * @param col the column
     * @return true if valid, false otherwise
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH;
    }
    
    /**
     * Checks if a cell is empty.
     *
     * @param row the row
     * @param col the column
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(int row, int col) {
        return getCell(row, col).isEmpty();
    }
    
    /**
     * Prints the board to console (for debugging).
     */
    public void print() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                System.out.print(cells[row][col] + " ");
            }
            System.out.println();
        }
    }
}