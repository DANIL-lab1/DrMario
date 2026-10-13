package drmario.ui;

import drmario.logic.GameEngine;
import drmario.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * JavaFX Canvas that renders the game.
 * <p>
 * Draws:
 * <ul>
 *     <li>Board background and grid</li>
 *     <li>Pills and viruses</li>
 *     <li>Current falling capsule</li>
 *     <li>Score and level</li>
 * </ul>
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class GamePanel extends Canvas {
    
    /** Size of one cell in pixels. */
    public static final int CELL_SIZE = 40;
    
    /** Width of the board in pixels. */
    public static final int BOARD_WIDTH = Board.WIDTH * CELL_SIZE;
    
    /** Height of the board in pixels. */
    public static final int BOARD_HEIGHT = Board.HEIGHT * CELL_SIZE;
    
    /** Width of the side panel (score, next capsule). */
    public static final int PANEL_WIDTH = 120;
    
    /** Total width of the canvas. */
    public static final int TOTAL_WIDTH = BOARD_WIDTH + PANEL_WIDTH;
    
    /** Total height of the canvas. */
    public static final int TOTAL_HEIGHT = BOARD_HEIGHT;
    
    private final GameEngine engine;
    private final GraphicsContext gc;
    
    /**
     * Creates a new game panel.
     *
     * @param engine the game engine
     */
    public GamePanel(GameEngine engine) {
        super(TOTAL_WIDTH, TOTAL_HEIGHT);
        this.engine = engine;
        this.gc = getGraphicsContext2D();
        setFocusTraversable(true);
    }
    
    /**
     * Renders the entire game.
     * <p>
     * Called every frame from the game loop.
     */
    public void render() {
        clear();
        drawBoardBackground();
        drawGrid();
        drawBoardCells();
        drawCurrentCapsule();
        drawSidePanel();
        
        if (engine.isLevelComplete()) {
            gc.setFill(Color.GOLD);
            gc.setFont(Font.font("Monospace", FontWeight.BOLD, 28));
            gc.fillText("LEVEL COMPLETE!", 60, 250);
            gc.setFont(Font.font("Monospace", FontWeight.BOLD, 16));
            gc.setFill(Color.WHITE);
            gc.fillText("Press SPACE to continue", 60, 300);
        }
        
        if (engine.isGameOver()) {
            gc.setFill(Color.color(0, 0, 0, 0.7));
            gc.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

            gc.setFill(Color.RED);
            gc.setFont(Font.font("Monospace", FontWeight.BOLD, 36));
            gc.fillText("GAME OVER", 60, 280);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Monospace", FontWeight.BOLD, 16));
            gc.fillText("Press R to restart", 80, 330);
        }
    }
    
    /**
     * Clears the canvas.
     */
    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
    }
    
    /**
     * Draws the board background (dark blue).
     */
    private void drawBoardBackground() {
        gc.setFill(Color.web("#1a1a2e"));
        gc.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
    }
    
    /**
     * Draws the grid lines.
     */
    private void drawGrid() {
        gc.setStroke(Color.web("#16213e"));
        gc.setLineWidth(1);
        
        // Vertical lines
        for (int col = 0; col <= Board.WIDTH; col++) {
            double x = col * CELL_SIZE;
            gc.strokeLine(x, 0, x, BOARD_HEIGHT);
        }
        
        // Horizontal lines
        for (int row = 0; row <= Board.HEIGHT; row++) {
            double y = row * CELL_SIZE;
            gc.strokeLine(0, y, BOARD_WIDTH, y);
        }
    }
    
    /**
     * Draws pills and viruses from the board.
     */
    private void drawBoardCells() {
        Board board = engine.getBoard();
        
        for (int row = 0; row < Board.HEIGHT; row++) {
            for (int col = 0; col < Board.WIDTH; col++) {
                Cell cell = board.getCell(row, col);
                if (cell.isEmpty()) continue;
                
                if (cell.isVirus()) {
                    drawVirus(row, col, cell.getColor());
                } else if (cell.isPill()) {
                    drawPill(row, col, cell.getColor());
                }
            }
        }
    }
    
    /**
     * Draws the current falling capsule.
     */
    private void drawCurrentCapsule() {
        Capsule capsule = engine.getCurrentCapsule();
        if (capsule == null) return;
        
        drawPill(capsule.getRow(), capsule.getCol(), capsule.getFirstColor());
        drawPill(capsule.getSecondRow(), capsule.getSecondCol(), capsule.getSecondColor());
    }
    
    /**
     * Draws a pill half at the given position.
     */
    private void drawPill(int row, int col, PillColor color) {
        double x = col * CELL_SIZE;
        double y = row * CELL_SIZE;
        
        gc.setFill(toFxColor(color));
        gc.fillRoundRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4, 10, 10);
        
        // Highlight
        gc.setFill(Color.WHITE.deriveColor(0, 1, 1, 0.3));
        gc.fillOval(x + 8, y + 8, CELL_SIZE - 24, CELL_SIZE - 24);
    }
    
    /**
     * Draws a virus at the given position (with spikes).
     */
    private void drawVirus(int row, int col, PillColor color) {
        double x = col * CELL_SIZE;
        double y = row * CELL_SIZE;
        double centerX = x + CELL_SIZE / 2.0;
        double centerY = y + CELL_SIZE / 2.0;
        double radius = CELL_SIZE / 2.0 - 4;
        
        gc.setFill(toFxColor(color).darker());
        gc.fillOval(x + 4, y + 4, CELL_SIZE - 8, CELL_SIZE - 8);
        
        // Spikes
        gc.setStroke(toFxColor(color).brighter());
        gc.setLineWidth(2);
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI * 2 * i / 8;
            double x1 = centerX + Math.cos(angle) * radius;
            double y1 = centerY + Math.sin(angle) * radius;
            double x2 = centerX + Math.cos(angle) * (radius + 5);
            double y2 = centerY + Math.sin(angle) * (radius + 5);
            gc.strokeLine(x1, y1, x2, y2);
        }
        
        // Eyes
        gc.setFill(Color.WHITE);
        gc.fillOval(centerX - 8, centerY - 4, 5, 5);
        gc.fillOval(centerX + 3, centerY - 4, 5, 5);
        gc.setFill(Color.BLACK);
        gc.fillOval(centerX - 7, centerY - 3, 3, 3);
        gc.fillOval(centerX + 4, centerY - 3, 3, 3);
    }
    
    /**
     * Draws the side panel with score and next capsule.
     */
    private void drawSidePanel() {
        double panelX = BOARD_WIDTH;
        
        // Panel background
        gc.setFill(Color.web("#0f0f1e"));
        gc.fillRect(panelX, 0, PANEL_WIDTH, TOTAL_HEIGHT);
        
        // Score
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Monospace", FontWeight.BOLD, 14));
        gc.fillText("SCORE", panelX + 15, 30);
        gc.setFont(Font.font("Monospace", FontWeight.BOLD, 20));
        gc.setFill(Color.YELLOW);
        gc.fillText(String.valueOf(engine.getScore().getScore()), panelX + 15, 55);
        
        // Level
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Monospace", FontWeight.BOLD, 14));
        gc.fillText("LEVEL", panelX + 15, 100);
        gc.setFont(Font.font("Monospace", FontWeight.BOLD, 20));
        gc.setFill(Color.CYAN);
        gc.fillText(String.valueOf(engine.getScore().getLevel()), panelX + 15, 125);
        
        // Next capsule
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Monospace", FontWeight.BOLD, 14));
        gc.fillText("NEXT", panelX + 15, 180);
        
        Capsule next = engine.getNextCapsule();
        if (next != null) {
            drawPillInPanel(panelX + 20, 200, next.getFirstColor());
            drawPillInPanel(panelX + 20 + CELL_SIZE, 200, next.getSecondColor());
        }
        
        // Game Over
        if (engine.isGameOver()) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font("Monospace", FontWeight.BOLD, 24));
            gc.fillText("GAME", panelX + 15, 300);
            gc.fillText("OVER", panelX + 15, 330);
        }
    }
    
    /**
     * Draws a pill in the side panel.
     */
    private void drawPillInPanel(double x, double y, PillColor color) {
        gc.setFill(toFxColor(color));
        gc.fillRoundRect(x, y, CELL_SIZE - 4, CELL_SIZE - 4, 10, 10);
    }
    
    /**
     * Converts PillColor to JavaFX Color.
     */
    private Color toFxColor(PillColor color) {
        switch (color) {
            case RED: return Color.web("#e94560");
            case BLUE: return Color.web("#4d9de0");
            case GREEN: return Color.web("#4caf50");
            default: return Color.GRAY;
        }
    }
}