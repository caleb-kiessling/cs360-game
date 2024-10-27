package application.core;

import java.util.ArrayList;
//import java.util.List;

import javafx.animation.AnimationTimer;

public class GameLoop {	
	private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private boolean paused = true; // support pausing.
	private AnimationTimer timer;
	
	public GameLoop() {
		timer = new AnimationTimer() {
			private long lastUpdate = System.nanoTime();
			
			@Override
			public void handle(long now) {
				double dt = (now - lastUpdate) / 1000000000;
				lastUpdate = now;
				if (!paused) {
					updateGameObjects(dt);
				}
			}
		};
	}
	
	public void start() {
		this.timer.start();
	}
	
	public void stop() {
		this.timer.stop();
	}

	public boolean isPaused() {
		return this.paused;
	}
	
	public void pause() {
		this.paused = true;
	}
	
	public void resume() {
		this.paused = false;
	}
	
	public void addGameObject(GameObject obj) {
		this.gameObjects.add(obj);
	}
	
	public void removeGameObject(GameObject obj) {
		this.gameObjects.remove(obj);
	}
	
	private void updateGameObjects(double dt) {
		for (GameObject gameObject: this.gameObjects) {
			gameObject.update(dt);
		}
	}
	
}
