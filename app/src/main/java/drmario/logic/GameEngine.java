package drmario.logic;

import drmario.model.Board;
import drmario.model.Capsule;
import drmario.model.Direction;
import drmario.model.PillColor;
import drmario.model.Position;

import java.util.List;
import java.util.Set;

/**
 * Central game logic that connects all components.
 * <p>
 * Manages:
 * <ul>
 *     <li>Game state (board, capsules, score)</li>
 *     <li>Capsule movement and rotation</li>
 *     <li>Collision detection via {@link Gravity}</li>
 *     <li>Match detection via {@link Matcher}</li>
 *     <li>Level progression via {@link ScoreManager}</li>
 * </ul>
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class GameEngine {
    
    /** Starting column for new capsules (center). */
    private static final int SPAWN_COL = Board.WIDTH / 2 - 1;
    
    /** Starting row for new capsules. */
    private static final int SPAWN_ROW = 0;
    
    private Board board;
    private Capsule currentCapsule;
    private Capsule nextCapsule;
    private ScoreManager score;
    private VirusSpawner spawner;
    private boolean gameOver;
    
    /** Time accumulator for automatic falling. */
    private double fallTimer;
    
    /**
     * Creates a new game engine.
     */
    public GameEngine() {
        this.spawner = new VirusSpawner();
        startNewGame();
    }
    
    // ========== GAME LIFECYCLE ==========
    
    /**
     * Starts a new game from level 1.
     */
    public void startNewGame(){
        this.board = new Board();
        this.score = new ScoreManager();
        this.gameOver = false;
        this.fallTimer = 0;
        
        // Spawn viruses for level 1
        int virusCount = VirusSpawner.virusesForLevel(score.getLevel());
        spawner.spawn(board, virusCount);
        
        // Create first capsules
        this.nextCapsule = createRandomCapsule();
        spawnNewCapsule();
    }
    
    /**
     * Spawns a new capsule at the top of the board.
     * <p>
     * If the spawn position is blocked — game over.
     */
    private void spawnNewCapsule() {
        this.currentCapsule = nextCapsule;
        this.nextCapsule = createRandomCapsule();
        this.fallTimer = 0;
        
        // Check if spawn position is valid
        if (!Gravity.isValidPosition(currentCapsule, board)) {
            gameOver = true;
        }
    }
    
    /**
     * Creates a random capsule at the spawn position.
     *
     * @return a new random capsule
     */
    private Capsule createRandomCapsule() {
        return new Capsule(
            SPAWN_ROW, SPAWN_COL,
            PillColor.random(),
            PillColor.random(),
            Direction.HORIZONTAL
        );
    }
    
    // ========== PLAYER ACTIONS ==========
    
    /**
     * Moves the current capsule down.
     * <p>
     * If the capsule cannot move down, it gets locked into the board,
     * matches are checked, and a new capsule spawns.
     */
    public void moveDown() {
        if (gameOver || currentCapsule == null) return;
        
        if (Gravity.canMoveDown(currentCapsule, board)) {
            currentCapsule.moveDown();
        } else {
            lockAndAdvance();
        }
    }
    
    /**
     * Moves the current capsule left.
     */
    public void moveLeft() {
        if (gameOver || currentCapsule == null) return;
        
        if (Gravity.canMoveLeft(currentCapsule, board)) {
            currentCapsule.moveLeft();
        }
    }
    
    /**
     * Moves the current capsule right.
     */
    public void moveRight() {
        if (gameOver || currentCapsule == null) return;
        
        if (Gravity.canMoveRight(currentCapsule, board)) {
            currentCapsule.moveRight();
        }
    }
    
    /**
     * Rotates the current capsule.
     */
    public void rotate() {
        if (gameOver || currentCapsule == null) return;
        
        if (Gravity.canRotate(currentCapsule, board)) {
            currentCapsule.rotate();
        }
    }
    
    /**
     * Drops the capsule instantly to the bottom.
     */
    public void drop() {
        if (gameOver || currentCapsule == null) return;
        
        while (Gravity.canMoveDown(currentCapsule, board)) {
            currentCapsule.moveDown();
        }
        lockAndAdvance();
    }
    
    // ========== GAME LOOP ==========
    
    /**
     * Called every frame to update the game state.
     * <p>
     * Automatically moves the capsule down based on fall speed.
     *
     * @param deltaTime time since last tick in seconds
     */
    public void tick(double deltaTime) {
        if (gameOver || currentCapsule == null) return;
        
        fallTimer += deltaTime;
        
        if (fallTimer >= score.getFallSpeed()) {
            fallTimer = 0;
            moveDown();
        }
    }
    
    // ========== LOCKING AND MATCHING ==========
    
    /**
     * Locks the current capsule into the board and advances to the next.
     * <p>
     * After locking:
     * <ol>
     *     <li>Check for matches</li>
     *     <li>Clear matches and add score</li>
     *     <li>Check for level completion</li>
     *     <li>Spawn new capsule</li>
     * </ol>
     */
    private void lockAndAdvance() {
        Gravity.lockCapsule(currentCapsule, board);
        processMatches();
        currentCapsule = null;
        spawnNewCapsule();
    }
    
    /**
     * Processes matches on the board.
     * <p>
     * Finds all groups of 4+ and clears them.
     * Adds score based on cleared cells.
     */
    private void processMatches() {
        List<Set<Position>> matches = Matcher.findMatches(board);
        
        if (matches.isEmpty()) {
            return;
        }
        
        // Count viruses and pills separately for scoring
        int virusesCleared = 0;
        int pillsCleared = 0;
        
        for (Set<Position> group : matches) {
            for (Position pos : group) {
                if (board.getCell(pos.getRow(), pos.getCol()).isVirus()) {
                    virusesCleared++;
                } else {
                    pillsCleared++;
                }
                board.setCell(pos.getRow(), pos.getCol(), new drmario.model.Cell());
            }
        }
        
        score.addPillsCleared(pillsCleared);
        score.addVirusesCleared(virusesCleared);
        
        // Check if all viruses are cleared
        if (countViruses() == 0) {
            score.completeLevel();
            int virusCount = VirusSpawner.virusesForLevel(score.getLevel());
            spawner.spawn(board, virusCount);
        }
    }
    
    /**
     * Counts remaining viruses on the board.
     *
     * @return number of viruses
     */
    private int countViruses() {
        int count = 0;
        for (int row = 0; row < Board.HEIGHT; row++) {
            for (int col = 0; col < Board.WIDTH; col++) {
                if (board.getCell(row, col).isVirus()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    // ========== GETTERS ==========
    
    public Board getBoard() { return board; }
    public Capsule getCurrentCapsule() { return currentCapsule; }
    public Capsule getNextCapsule() { return nextCapsule; }
    public ScoreManager getScore() { return score; }
    public boolean isGameOver() { return gameOver; }
}
