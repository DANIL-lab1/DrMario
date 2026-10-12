package drmario.model;

import java.util.Set;

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
    
    public Board() {
        this.cells = new Cell[HEIGHT][WIDTH];
        clear();
    }
    
    public void clear() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                cells[row][col] = new Cell();
            }
        }
    }
    
    public Cell getCell(int row, int col) {
        if (!isValidPosition(row, col)) {
            throw new IndexOutOfBoundsException("Invalid position: " + row + ", " + col);
        }
        return cells[row][col];
    }
    
    public void setCell(int row, int col, Cell cell) {
        if (!isValidPosition(row, col)) {
            throw new IndexOutOfBoundsException("Invalid position: " + row + ", " + col);
        }
        cells[row][col] = cell;
    }
    
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH;
    }
    
    public boolean isEmpty(int row, int col) {
        return getCell(row, col).isEmpty();
    }
    
    public void addVirus(int row, int col, PillColor color) {
        setCell(row, col, new Cell(Cell.Type.VIRUS, color));
    }
    
    /**
     * Applies gravity to specific columns only.
     *
     * @param columns the set of column indices
     */
    public void applyGravity(Set<Integer> columns) {
        for (int col : columns) {
            applyGravityInColumn(col);
        }
    }
    
    /**
     * Applies gravity to all columns.
     */
    public void applyGravity() {
        for (int col = 0; col < WIDTH; col++) {
            applyGravityInColumn(col);
        }
    }
    
    private void applyGravityInColumn(int col) {
        int writeRow = HEIGHT - 1;
        
        for (int readRow = HEIGHT - 1; readRow >= 0; readRow--) {
            Cell cell = cells[readRow][col];
            
            if (!cell.isEmpty()) {
                if (readRow != writeRow) {
                    cells[writeRow][col] = cell;
                    cells[readRow][col] = new Cell();
                }
                writeRow--;
            }
        }
    }
    
    public int countViruses() {
        int count = 0;
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                if (cells[row][col].isVirus()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public void print() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                System.out.print(cells[row][col] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Moves all cells down by ONE row in the given columns.
     * <p>
     * Returns true if any cell was moved.
     *
     * @param columns the set of column indices
     * @return true if at least one cell moved
     */
    public boolean stepGravity(Set<Integer> columns) {
        boolean moved = false;

        for (int col : columns) {
            for (int row = HEIGHT - 1; row > 0; row--) {
               Cell current = cells[row][col];
               Cell above = cells[row - 1][col];

               if (current.isEmpty() && !above.isEmpty()) {
                   cells[row][col] = above;
                   cells[row - 1][col] = new Cell();
                   moved = true;
                }
            }
        }
        return moved;
   }
}