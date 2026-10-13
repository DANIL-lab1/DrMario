package drmario.logic;

import drmario.model.Board;
import drmario.model.Capsule;
import drmario.model.Cell;
import drmario.model.Direction;
import drmario.model.PillColor;
import drmario.model.Position;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

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
    
    // Game States
    public enum State {
        PLAYING,
        ANIMATING,
        LEVEL_COMPLETE,
        GAME_OVER
    }
    
    private State state;
    private double animationTimer;
    private static final double ANIMATION_STEP_TIME = 0.1;
    
    private double levelCompleteTimer;
    private static final double LEVEL_COMPLETE_DELAY = 2.0;
    
    /** Starting column for new capsules (center). */
    private static final int SPAWN_COL = Board.WIDTH / 2 - 1;
    
    /** Starting row for new capsules. */
    private static final int SPAWN_ROW = 0;
    
    private Board board;
    private Capsule currentCapsule;
    private Capsule nextCapsule;
    private ScoreManager score;
    private VirusSpawner spawner;
    
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
    public void startNewGame() {
        this.board = new Board();
        this.score = new ScoreManager();
        this.state = State.PLAYING;
        this.animationTimer = 0;
        this.fallTimer = 0;
        this.levelCompleteTimer = 0;
        
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
            state = State.GAME_OVER;
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
        if (!isPlaying() || currentCapsule == null) return;
        
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
        if (!isPlaying() || currentCapsule == null) return;
        
        if (Gravity.canMoveLeft(currentCapsule, board)) {
            currentCapsule.moveLeft();
        }
    }
    
    /**
     * Moves the current capsule right.
     */
    public void moveRight() {
        if (!isPlaying() || currentCapsule == null) return;
        
        if (Gravity.canMoveRight(currentCapsule, board)) {
            currentCapsule.moveRight();
        }
    }
    
    /**
     * Rotates the current capsule.
     */
    public void rotate() {
        if (!isPlaying() || currentCapsule == null) return;
        
        if (Gravity.canRotate(currentCapsule, board)) {
            currentCapsule.rotate();
        }
    }
    
    /**
     * Drops the capsule instantly to the bottom.
     */
    public void drop() {
        if (!isPlaying() || currentCapsule == null) return;
        
        while (Gravity.canMoveDown(currentCapsule, board)) {
            currentCapsule.moveDown();
        }
        lockAndAdvance();
    }
    
    // ========== GAME LOOP ==========
    
    /**
     * Called every frame to update the game state.
     *
     * @param deltaTime time since last tick in seconds
     */
    public void tick(double deltaTime) {
        // Game Over State
        if (state == State.GAME_OVER) return;
        
        // Level Complete State — wait for SPACE
        if (state == State.LEVEL_COMPLETE) {
            return;
        }
        
        // Animating State — pause for animation
        if (state == State.ANIMATING) {
            animationTimer += deltaTime;
            
            if (animationTimer >= ANIMATION_STEP_TIME) {
                animationTimer = 0;
                state = State.PLAYING;
                
                // Check for chain reaction matches
                if (Matcher.hasMatches(board)) {
                    processMatches();
                }
            }
            return;
        }
        
        // Playing State
        if (state == State.PLAYING && currentCapsule != null) {
            fallTimer += deltaTime;
            
            if (fallTimer >= score.getFallSpeed()) {
                fallTimer = 0;
                moveDown();
            }
        }
    }
    
    // ========== LOCKING AND MATCHING ==========
    
    /**
     * Locks the current capsule into the board and advances to the next.
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
     * Uses chain reaction: after clearing and applying gravity,
     * checks for new matches until none remain.
     */
    private void processMatches() {
        boolean foundMatches = true;
        
        while (foundMatches) {
            List<Set<Position>> matches = Matcher.findMatches(board);
            
            if (matches.isEmpty()) {
                foundMatches = false;
                continue;
            }
            
            Set<Integer> affectedColumns = new HashSet<>();
            int virusesCleared = 0;
            int pillsCleared = 0;
            
            for (Set<Position> group : matches) {
                for (Position pos : group) {
                    if (board.getCell(pos.getRow(), pos.getCol()).isVirus()) {
                        virusesCleared++;
                    } else {
                        pillsCleared++;
                    }
                    affectedColumns.add(pos.getCol());
                    board.setCell(pos.getRow(), pos.getCol(), new Cell());
                }
            }
            
            score.addPillsCleared(pillsCleared);
            score.addVirusesCleared(virusesCleared);
            
            if (!affectedColumns.isEmpty()) {
                // Apply gravity INSTANTLY (only blocks above match fall)
                board.applyGravity(affectedColumns);
                
                // Check if all viruses cleared
                if (board.countViruses() == 0) {
                    state = State.LEVEL_COMPLETE;
                    levelCompleteTimer = 0;
                    score.completeLevel();
                    return;
                }
                
                // Pause for animation
                state = State.ANIMATING;
                animationTimer = 0;
                return;
            }
        }
        
        // Check level completion (for cases without matches)
        if (board.countViruses() == 0) {
            state = State.LEVEL_COMPLETE;
            levelCompleteTimer = 0;
            score.completeLevel();
        }
    }
    
    /**
     * Starts the next level after LEVEL_COMPLETE.
     */
    public void startNextLevel() {
        if (state != State.LEVEL_COMPLETE) return;
        
        board.clear();
        int virusCount = VirusSpawner.virusesForLevel(score.getLevel());
        spawner.spawn(board, virusCount);
        
        this.nextCapsule = createRandomCapsule();
        spawnNewCapsule();
        
        state = State.PLAYING;
    }
    
    // ========== GETTERS ==========
    public State getState() { return state; }
    public boolean isPlaying() { return state == State.PLAYING; }
    public boolean isAnimating() { return state == State.ANIMATING; }
    public boolean isGameOver() { return state == State.GAME_OVER; }
    public boolean isLevelComplete() { return state == State.LEVEL_COMPLETE; }
    
    public Board getBoard() { return board; }
    public Capsule getCurrentCapsule() { return currentCapsule; }
    public Capsule getNextCapsule() { return nextCapsule; }
    public ScoreManager getScore() { return score; }
}