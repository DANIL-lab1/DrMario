package drmario.logic;

/**
 * Tracks score, level, and game progression.
 * <p>
 * Scoring rules:
 * <ul>
 *     <li>+10 points per cleared pill half</li>
 *     <li>+100 points per cleared virus</li>
 *     <li>+1000 bonus for clearing all viruses</li>
 * </ul>
 * <p>
 * Level increases when all viruses are cleared.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class ScoreManager {
    
    /** Points for clearing a pill half. */
    public static final int POINTS_PER_PILL = 10;
    
    /** Points for clearing a virus. */
    public static final int POINTS_PER_VIRUS = 100;
    
    /** Bonus for clearing all viruses on the board. */
    public static final int LEVEL_COMPLETE_BONUS = 1000;
    
    /** Speed increase per level (in seconds). */
    public static final double SPEED_INCREASE = 0.05;
    
    /** Base fall speed in seconds (time per cell). */
    public static final double BASE_FALL_SPEED = 1.0;
    
    /** Minimum fall speed (max difficulty). */
    public static final double MIN_FALL_SPEED = 0.1;
    
    private int score;
    private int level;
    private int virusesCleared;
    private int pillsCleared;
    
    /**
     * Creates a new score manager for level 1.
     */
    public ScoreManager(){
        this.score = 0;
        this.level = 1;
        this.virusesCleared = 0;
        this.pillsCleared = 0;
    }
    
    // ========== SCORING ==========
    
    /**
     * Adds points for cleared pills.
     *
     * @param count number of pills cleared
     */
    public void addPillsCleared(int count){
        this.pillsCleared += count;
        this.score += count * POINTS_PER_PILL;
    }
    
    /**
     * Adds points for cleared viruses.
     *
     * @param count number of viruses cleared
     */
    public void addVirusesCleared(int count) {
        this.virusesCleared += count;
        this.score += count * POINTS_PER_VIRUS;
    }
    
    /**
     * Completes the level: adds bonus and increases level.
     */
    public void completeLevel() {
        this.score += LEVEL_COMPLETE_BONUS;
        this.level++;
    }
    
    // ========== PROGRESSION ==========
    
    /**
     * Returns the current fall speed in seconds per cell.
     * <p>
     * Speed increases with level but has a minimum.
     *
     * @return fall speed in seconds
     */
    public double getFallSpeed() {
        double speed = BASE_FALL_SPEED - (level - 1) * SPEED_INCREASE;
        return Math.max(speed, MIN_FALL_SPEED);
    }
    
    // ========== GETTERS ==========
    
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public int getVirusesCleared() { return virusesCleared; }
    public int getPillsCleared() { return pillsCleared; }
    
    /**
     * Returns total cleared cells (pills + viruses).
     *
     * @return total cleared
     */
    public int getTotalCleared() {
        return pillsCleared + virusesCleared;
    }
    
    // ========== RESET ==========
    
    /**
     * Resets score to zero (for a new game).
     */
    public void reset() {
        this.score = 0;
        this.level = 1;
        this.virusesCleared = 0;
        this.pillsCleared = 0;
    }
    
    @Override
    public String toString() {
        return String.format(
            "ScoreManager {score=%d, level=%d, viruses=%d, pills=%d, speed=%.2fs} ",
            score, level, virusesCleared, pillsCleared, getFallSpeed()
        );
    }
}
