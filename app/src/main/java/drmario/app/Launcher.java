package drmario.app;

import javafx.application.Application;

/**
 * Workaround for "JavaFX runtime components are missing" error.
 * This class does NOT extend Application, so the Java launcher
 * does not check for the javafx.graphics module.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}