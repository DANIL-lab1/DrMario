package drmario.logic;

import drmario.model.Board;
import drmario.model.PillColor;
import drmario.model.Position;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Spawns viruses on the board for a new level.
 * <p>
 * Viruses are placed randomly in the bottom half of the board,
 * so they don't interfere with the capsule spawn area.
 * <p>
 * After spawning, ensures no accidental 4+ groups exist.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class VirusSpawner {
    
    /** Top row where viruses can be placed. */
    private static final int MIN_ROW = Board.HEIGHT / 2;
    
    /** Maximum attempts to place a virus (to avoid infinite loop). */
    private static final int MAX_ATTEMPTS = 1000;
    
    private final Random random;
    
    public VirusSpawner() {
        this.random = new Random();
    }
    
    public VirusSpawner(Random random) {
        this.random = random;
    }
    
    /**
     * Spawns the given number of viruses on the board.
     * <p>
     * Also removes any accidental 4+ groups that may have formed.
     *
     * @param board the game board
     * @param count number of viruses to spawn
     * @return the number of viruses actually spawned
     */
    public int spawn(Board board, int count) {
        int spawned = 0;
        int attempts = 0;
        
        while (spawned < count && attempts < MAX_ATTEMPTS) {
            attempts++;
            
            int row = MIN_ROW + random.nextInt(Board.HEIGHT - MIN_ROW);
            int col = random.nextInt(Board.WIDTH);
            
            if (!board.isEmpty(row, col)) {
                continue;
            }
            
            PillColor color = PillColor.random();
            board.addVirus(row, col, color);
            spawned++;
        }
        
        // Remove any accidental 4+ groups
        removeInitialMatches(board);
        
        return spawned;
    }
    
    /**
     * Removes any accidental 4+ groups created during spawning.
     * <p>
     * Prevents free matches at game start.
     *
     * @param board the game board
     */
    private void removeInitialMatches(Board board) {
        boolean changed = true;
        
        while (changed) {
            changed = false;
            List<Set<Position>> matches = Matcher.findMatches(board);
            
            for (Set<Position> group : matches) {
                // Change one virus color to break the group
                Position pos = group.iterator().next();
                PillColor currentColor = board.getCell(pos.getRow(), pos.getCol()).getColor();
                PillColor newColor = getDifferentColor(currentColor);
                board.addVirus(pos.getRow(), pos.getCol(), newColor);
                changed = true;
            }
        }
    }
    
    /**
     * Returns a color different from the given one.
     *
     * @param color the original color
     * @return a different color
     */
    private PillColor getDifferentColor(PillColor color) {
        PillColor[] colors = PillColor.values();
        for (PillColor c : colors) {
            if (c != color) return c;
        }
        return colors[0];
    }
    
    /**
     * Calculates the number of viruses for a given level.
     *
     * @param level the level number (1-based)
     * @return number of viruses
     */
    public static int virusesForLevel(int level) {
        return Math.min(4 * level, 84);
    }
}