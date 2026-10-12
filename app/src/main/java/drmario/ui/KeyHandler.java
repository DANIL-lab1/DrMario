package drmario.ui;

import drmario.logic.GameEngine;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handles keyboard input for the game.
 * <p>
 * Controls:
 * <ul>
 *     <li>LEFT arrow — move capsule left</li>
 *     <li>RIGHT arrow — move capsule right</li>
 *     <li>DOWN arrow — move capsule down</li>
 *     <li>UP arrow — rotate capsule</li>
 *     <li>SPACE — drop capsule instantly</li>
 * </ul>
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class KeyHandler {
    
    private final GameEngine engine;
    
    public KeyHandler(GameEngine engine) {
        this.engine = engine;
    }
    
    /**
     * Handles a key press.
     *
     * @param event the key event
     */
    public void handle(KeyEvent event) {
        if (engine.isGameOver()) return;
        
        KeyCode code = event.getCode();
        
        switch (code) {
            case LEFT:
                engine.moveLeft();
                break;
            case RIGHT:
                engine.moveRight();
                break;
            case DOWN:
                engine.moveDown();
                break;
            case UP:
                engine.rotate();
                break;
            case SPACE:
                if (engine.isLevelComplete()) {
                    engine.startNextLevel();
                } else {
                    engine.drop();
                }
                break;
            default:
                // Ignore other keys
        }
    }
}