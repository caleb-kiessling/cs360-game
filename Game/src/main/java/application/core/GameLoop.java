package application.core;

import java.util.ArrayList;
//import java.util.List;

import application.entities.Asteroid;
import application.entities.Bullet;
import application.entities.Spaceship;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class GameLoop {	
	private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private boolean paused = true; // support pausing.
	private AnimationTimer timer;
	Pane gamepane;
	public GameLoop(Pane gamepane) {
		this.gamepane=gamepane;
		timer = new AnimationTimer() {
			private long lastUpdate = System.nanoTime();
			
			@Override
			public void handle(long now) {
				double dt = (now - lastUpdate) / 1000000000;
				lastUpdate = now;
				if (!paused) {
					updateGameObjects(dt);
					checkCollisions();
				}
			}
		};
	}
	
	public void start() {
		if(this.timer==null) {
			System.out.println("null timer");
		}else {
		 this.timer.start();

		}
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
	
	public void checkCollisions() {
		for (int i = 0; i < gameObjects.size(); i++) {
            GameObject obj1 = gameObjects.get(i);

            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject obj2 = gameObjects.get(j);

                // Check if objects collide
                if (obj1.getBoundInParent().intersects(obj2.getBoundInParent())) {
                    // Handle the collision
                    handleCollision(obj1, obj2);
                    
                }
            }
        }
	}
	private void handleCollision(GameObject obj1, GameObject obj2) {
        // Custom collision handling logic
        if ((obj1 instanceof Bullet && obj2 instanceof Asteroid)||(obj2 instanceof Bullet && obj1 instanceof Asteroid)) {
            // Destroy both bullet and asteroid
            removeGameObject(obj1);
            removeGameObject(obj2);
            gamepane.getChildren().removeAll(obj1.getVisual(),obj2.getVisual());
            System.out.println("Asteroid shot");
        } else if ((obj1 instanceof Asteroid && obj2 instanceof Spaceship)||(obj2 instanceof Asteroid && obj1 instanceof Spaceship)) {
            // End the game if asteroid hits the player
            System.out.println("Game Over!");
            stop(); // Stop the game loop
            
        }
        // Add other collision scenarios here
    }
	
}
