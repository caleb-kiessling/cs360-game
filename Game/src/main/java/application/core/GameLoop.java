package application.core;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;

public class GameLoop {
    private ArrayList<GameObject> gameObjects = new ArrayList<>(); // Active game objects
    private boolean paused = true; // pause flag
    private AnimationTimer timer;
    private Runnable updateCallback; // callback for additional things
    
    private double elapsedTime;
    
    public GameLoop() {
    	this.elapsedTime = 0;
        this.timer = new AnimationTimer() {
            private long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {
                double deltaTime = (now - lastUpdate) / 1000000000.0;
                lastUpdate = now;

                if (!paused) {
                	elapsedTime += deltaTime;
                    updateGameObjects(deltaTime);

                    if (updateCallback != null) {
                        updateCallback.run();
                    }
                }
            }
        };
    }

    public void start() {
        paused = false;
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public ArrayList<GameObject> getGameObjects(){
    	return this.gameObjects;
    }
    
    public void addGameObject(GameObject obj) {
        gameObjects.add(obj);
    }

    public void removeGameObject(GameObject obj) {
        gameObjects.remove(obj);
    }

    public double getElapsedTime() {
    	return this.elapsedTime;
    }
    
    public void setUpdateCallback(Runnable callback) {
        this.updateCallback = callback;
    }

    private void updateGameObjects(double deltaTime) {
    	for (GameObject gameObject: this.gameObjects) {
    		gameObject.update(deltaTime);
    	}
    	
    	
        //for (GameObject gameObject : new ArrayList<>(gameObjects)) {
          //  gameObject.update(deltaTime);
        //}
    }
}
