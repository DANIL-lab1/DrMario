package drmario.app;

import drmario.logic.GameEngine;
import drmario.ui.GamePanel;
import drmario.ui.KeyHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main entry point for Dr. Mario.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public class Main extends Application {
    
    private GameEngine engine;
    private GamePanel panel;
    private KeyHandler keyHandler;
    private long lastFrameTime;
    
    @Override
    public void start(Stage stage) {
        // Create engine
        engine = new GameEngine();
        
        // Create panel
        panel = new GamePanel(engine);
        
        // Create key handler
        keyHandler = new KeyHandler(engine);
        panel.setOnKeyPressed(keyHandler::handle);
        
        // Create scene
        StackPane root = new StackPane(panel);
        Scene scene = new Scene(root, GamePanel.TOTAL_WIDTH, GamePanel.TOTAL_HEIGHT);
        
        // Setup stage
        stage.setTitle("Dr. Mario");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        // Focus panel for key events
        panel.requestFocus();
        
        // Start game loop
        startGameLoop();
    }
    
    /**
     * Starts the game loop using AnimationTimer.
     */
    private void startGameLoop() {
        lastFrameTime = System.nanoTime();
        
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Calculate delta time in seconds
                double deltaTime = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;
                
                // Update game logic
                engine.tick(deltaTime);
                
                // Render
                panel.render();
            }
        };
        
        timer.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}