package drmario.model;

/**
 * Represents a single cell on the game board.
 * <p>
 * A cell can be:
 * <ul>
 *     <li>EMPTY — no pill or virus</li>
 *     <li>PILL — part of a falling capsule</li>
 *     <li>VIRUS — enemy that must be cleared</li>
 * </ul>
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class Cell {
    
    /**
     * Type of cell content.
     */
    public enum Type {
        EMPTY,
        PILL,
        VIRUS
    }
    
    private final Type type;
    private final PillColor color;
    
    /**
     * Creates an empty cell.
     */
    public Cell() {
        this.type = Type.EMPTY;
        this.color = null;
    }
    
    /**
     * Creates a cell with content.
     *
     * @param type  the cell type
     * @param color the cell color (null for EMPTY)
     */
    public Cell(Type type, PillColor color) {
        this.type = type;
        this.color = color;
    }
    
    public Type getType() { return type; }
    public PillColor getColor() { return color; }
    
    public boolean isEmpty() {
        return type == Type.EMPTY;
    }
    
    public boolean isPill() {
        return type == Type.PILL;
    }
    
    public boolean isVirus() {
        return type == Type.VIRUS;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) return ".";
        return color.name().charAt(0) + "";  // R, B, G
    }
}