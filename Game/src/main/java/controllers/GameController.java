package controllers;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import application.core.GameLoop;
import application.core.GameObject;
import application.entities.Asteroid;
import application.entities.Bullet;
import application.entities.Spaceship;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController extends BaseController implements KeyListener {
	private int score = 0;
	
    private GameLoop gameLoop;
    private Spaceship player;

    @FXML
    private Pane GameContent; // Main game area

    @FXML
    private Label ScoreLabel;

    @FXML
    private Label AmmoLabel;
    
    private int ammo;
    
    
    @FXML
    @Override
    public void start() {
    	gameLoop=new GameLoop(GameContent);
    	ammo=5;
    	AmmoLabel.setText("["+ammo+"] SHOTS");
    	player = new Spaceship();
        GameContent.getChildren().add(player.getVisual());
        player.setXY(300,300);
        gameLoop.addGameObject(player);
    	GameContent.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::moveShip);
            }
        });
    	asteroidFall();
    	gameLoop.start();
    	gameLoop.resume();
    }
    
    //method for ship actions and key events
    @FXML
    void moveShip(KeyEvent e) {
    	double x = player.getVisual().getLayoutX();
        double y = player.getVisual().getLayoutY();
        double step=15;

        if (e.getCode() == KeyCode.UP) {
            player.getVisual().setLayoutY(y - step);
        }
        if (e.getCode() == KeyCode.DOWN) {
            player.getVisual().setLayoutY(y + step);
        }
        if (e.getCode() == KeyCode.LEFT) {
            player.getVisual().setLayoutX(x - step);
        }
        if (e.getCode() == KeyCode.RIGHT) {
            player.getVisual().setLayoutX(x + step);
        }
        if (e.getCode() == KeyCode.SPACE) {
            shoot();
        }
        

   }
    //method that creates that shape for bullet and play animation of bullet firing
    @FXML
    void shoot() {
    	if(ammo>0) {
    		ammo--;
        	AmmoLabel.setText("["+ammo+"] SHOTS");
        	Bullet bullet=new Bullet();
            GameContent.getChildren().add(bullet.getVisual());
            bullet.getVisual().setX(300);//setX and setY need to be the same as ship to spawn in it
            bullet.getVisual().setY(300);
            bullet.getVisual().setLayoutX(player.getVisual().getLayoutX()+15);
            bullet.getVisual().setLayoutY(player.getVisual().getLayoutY());
            
            gameLoop.addGameObject(bullet);
            //animation of bullet
            TranslateTransition shootBullet=new TranslateTransition();
            shootBullet.setDuration(Duration.seconds(2)); // Animation duration
            shootBullet.setNode(bullet.getVisual()); // The node to animate
            shootBullet.setByY(-1000); // Move 1000px up
            shootBullet.setCycleCount(1); // play animation once
            shootBullet.setAutoReverse(false); // Reverse direction after each cycle
            shootBullet.setOnFinished(event -> {//used to destroy bullet object once animation is finished
            	GameContent.getChildren().remove(bullet.getVisual());
            	gameLoop.removeGameObject(bullet);
            });

            // Start the animation
            shootBullet.play();
    	}
    	

    }
    
    public void asteroidFall() {
    	Asteroid a=new Asteroid("answer");
    	GameContent.getChildren().add(a.getVisual());
    	a.setLayoutXY(300,0);
    	
    	gameLoop.addGameObject(a);
    	 //animation of asteroid
        TranslateTransition asteroidFall=new TranslateTransition();
        asteroidFall.setDuration(Duration.seconds(20)); // Animation duration
        asteroidFall.setNode(a.getVisual()); // The node to animate
        asteroidFall.setByY(100); // Move 1000px up
        asteroidFall.setCycleCount(1); // play animation once
        asteroidFall.setAutoReverse(false); // Reverse direction after each cycle
        asteroidFall.setOnFinished(event -> {//used to destroy bullet object once animation is finished
        	GameContent.getChildren().remove(a.getVisual());
        });
        asteroidFall.play();
    }
    
 
    public void addGameObject(GameObject gameObject) {
        gameLoop.addGameObject(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameLoop.removeGameObject(gameObject);
    }

    private void setupKeyHandler() {
        GameContent.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                togglePause();
            }
        });
    }

    private void togglePause() {
        if (gameLoop.isPaused()) {
            gameLoop.resume();
        } else {
            gameLoop.pause();
        }
    }

    public void updateHUD(int score, int ammo) {
        ScoreLabel.setText("Score: " + score);
        AmmoLabel.setText("Ammo: " + ammo);
    }
   
  
    @Override
    public void clean() {
        gameLoop.stop();
        gameLoop = null;
    }

	@Override
	public void keyTyped(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub
    	System.out.println("key pressed");
	}

	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub
    	System.out.println("key pressed");
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
