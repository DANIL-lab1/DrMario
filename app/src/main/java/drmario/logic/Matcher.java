package drmario.logic;

import drmario.model.Board;
import drmario.model.Cell;
import drmario.model.PillColor;
import drmario.model.Position;

import java.util.*;

/**
 * Finds and clears groups of 4+ same-colored pills/viruses.
 * <p>
 * Uses BFS (breadth-first search) to find connected groups.
 * A group must have at least {@link #MIN_MATCH_SIZE} cells.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class Matcher {
    
    /** Minimum group size to clear. */
    public static final int MIN_MATCH_SIZE = 4;
    
    /** Directions: up, down, left, right. */
    private static final int[][] DIRECTIONS = {
        {-1, 0}, // up
        {1, 0}, // down
        {0, -1}, // left
        {0, 1} // right
    };
            
            /**
     * Finds all groups of 4+ same-colored cells.
     *
     * @param board the game board
     * @return list of groups; each group is a set of positions
     */
    public static List<Set<Position>> findMatches(Board board){
        List<Set<Position>> matches = new ArrayList<>();
        Set<Position> visited = new HashSet<>();
        
        for (int row = 0; row < Board.HEIGHT; row++){
            for (int col = 0; col < Board.WIDTH; col++){
                Position pos = new Position(row, col);
                if (visited.contains(pos)) continue;
                if (board.getCell(row, col).isEmpty()) continue;
                
                // BFS to find all connected same-colored cells
                Set<Position> group = findGroup(board, pos);
                visited.addAll(group);
                
                if (group.size() >= MIN_MATCH_SIZE){
                    matches.add(group);
                }
            }
        }
        return matches;
    }
    
    /**
     * BFS from a starting position to find all connected cells of the same color.
     *
     * @param board the game board
     * @param start starting position
     * @return set of positions in the group
     */
    private static Set<Position> findGroup(Board board, Position start) {
        Cell startCell = board.getCell(start.getRow(), start.getCol());
        PillColor color = startCell.getColor();
        
        Set<Position> group = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        
        queue.add(start);
        group.add(start);
        
        while (!queue.isEmpty()){
            Position current = queue.poll();
            
            for (int[] dir : DIRECTIONS) {
                Position next = current.offset(dir[0], dir[1]);
                
                if (!board.isValidPosition(next.getRow(), next.getCol())) continue;
                if (group.contains(next)) continue;
                
                Cell cell = board.getCell(next.getRow(), next.getCol());
                if (cell.isEmpty()) continue;
                if (cell.getColor() != color) continue;
                
                group.add(next);
                queue.add(next);
            }
        }
        return group;
    }
    
    /**
     * Clears all matched groups from the board.
     *
     * @param board the game board
     * @return total number of cleared cells
     */
    public static int clearMatches(Board board){
        List<Set<Position>> matches = findMatches(board);
        int cleared = 0;
        
        for (Set<Position> group : matches){
            for (Position pos : group){
                board.setCell(pos.getRow(), pos.getCol(), new Cell());
                cleared++;
            }
        }
        
        return cleared;
    }
    
    /**
     * Checks if there are any matches on the board.
     *
     * @param board the game board
     * @return true if at least one group exists
     */
    public static boolean hasMatches(Board board) {
        return !findMatches(board).isEmpty();
    }
}
