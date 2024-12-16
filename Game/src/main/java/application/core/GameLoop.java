package application.core;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;

// Manages the game loop and updates game objects
public class GameLoop {
    private ArrayList<GameObject> gameObjects = new ArrayList<>(); // List of active game objects
    private boolean paused = true; // Flag for game pause state
    private AnimationTimer timer; // Timer for the game loop
    private Runnable updateCallback; // Callback for extra updates

    private double elapsedTime; // Time elapsed since start

    // Constructor to initialize the game loop
    public GameLoop() {
        this.elapsedTime = 0;
        this.timer = new AnimationTimer() {
            private long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastUpdate) / 1000000000.0; // Time since last frame
                lastUpdate = now;

                if (!paused) {
                    elapsedTime += deltaTime;
                    updateGameObjects(deltaTime); // Update all game objects

                    if (updateCallback != null) {
                        updateCallback.run(); // Run extra updates
                    }
                }
            }
        };
    }

    // Start the game loop
    public void start() {
        paused = false;
        timer.start();
    }

    // Stop the game loop
    public void stop() {
        timer.stop();
    }

    // Check if the game is paused
    public boolean isPaused() {
        return paused;
    }

    // Pause the game loop
    public void pause() {
        paused = true;
    }

    // Resume the game loop
    public void resume() {
        paused = false;
    }

    // Get the list of game objects
    public ArrayList<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    // Add a game object to the loop
    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
    }

    // Remove a game object from the loop
    public void removeGameObject(GameObject obj) {
        gameObjects.remove(obj);
    }

    // Get the elapsed time
    public double getElapsedTime() {
        return this.elapsedTime;
    }

    // Set a callback for additional updates
    public void setUpdateCallback(Runnable callback) {
        this.updateCallback = callback;
    }

    // Update all game objects
    private void updateGameObjects(double deltaTime) {
        for (GameObject gameObject : this.gameObjects) {
            gameObject.update(deltaTime); // Update each game object
        }
    }
}
