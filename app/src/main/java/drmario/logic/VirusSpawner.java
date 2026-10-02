package drmario.logic;

import drmario.model.Board;
import drmario.model.PillColor;

import java.util.Random;

/**
 * Spawns viruses on the board for a new level.
 * <p>
 * Viruses are placed randomly in the bottom half of the board,
 * so they don't interfere with the capsule spawn area.
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
    
    /**
     * Creates a spawner with a new Random instance.
     */
    public VirusSpawner(){
        this.random = new Random();
    }
    
    /**
     * Creates a spawner with a given Random (for testing).
     *
     * @param random the random instance
     */
    public VirusSpawner(Random random){
        this.random = random;
    }
    
    /**
     * Spawns the given number of viruses on the board.
     * <p>
     * Viruses are placed randomly between {@link #MIN_ROW} and bottom.
     * Each virus gets a random color.
     *
     * @param board the game board
     * @param count number of viruses to spawn
     * @return the number of viruses actually spawned
     */
    public int spawn(Board board, int count){
        int spawned = 0;
        int attempts = 0;
        
        while (spawned < count && attempts < MAX_ATTEMPTS){
            attempts++;
            
            int row = MIN_ROW + random.nextInt(Board.HEIGHT - MIN_ROW);
            int col = random.nextInt(Board.WIDTH);
            
            // Skip if cell is not empty
            if (!board.isEmpty(row, col)){
                continue;
            }
            
            PillColor color = PillColor.random();
            board.addVirus(row, col, color);
            spawned++;
        }
        
        return spawned;
    }
    
    /**
     * Calculates the number of viruses for a given level.
     * <p>
     * Level 1: 4 viruses, each level adds more.
     * Formula: 4 * level (capped at 84 = 8*16/2).
     *
     * @param level the level number (1-based)
     * @return number of viruses
     */
    public static int virusesForLevel(int level){
        return Math.min(4 * level, 84);
    }
}
