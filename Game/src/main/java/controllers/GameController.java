package controllers;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.core.Answer;
import application.core.DataParser;
import application.core.GameEventListener;
import application.core.GameLoop;
import application.core.GameObject;
import application.core.Level;
import application.core.Question;
import application.core.LevelControl;
import application.entities.Asteroid;
import application.entities.Bullet;
import application.entities.Spaceship;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController extends BaseController implements KeyListener, GameEventListener {
	private int score = 0;
	
    private GameLoop gameLoop;
    private Spaceship player;

    @FXML
    private Pane GameContent; // Main game area

    @FXML
    private Label ScoreLabel;

    @FXML
    private Label AmmoLabel;
    @FXML
    private Label QLabel;
    
    @FXML
    private Label HealthLabel;
    
    @FXML
    private Rectangle killBorder;
    
    private int ammo;
    private int health;
    private LevelControl lvlControl;
    private List<TranslateTransition> asteroidTransitions = new ArrayList<>();
	private int correct;
	private int destroyed;
	private int losses;
	private boolean lose;

    
    @FXML
    @Override
    public void start() {
    	lvlControl=new LevelControl();
    	gameLoop=new GameLoop(GameContent);
        gameLoop.setGameEventListener(this); // Set the listener
	    
        
    	ammo=5;
    	health=3;
    	score=0;
		updateHUD();
		
    	player = new Spaceship();
        GameContent.getChildren().add(player.getVisual());
        player.setXY(300,300);
        gameLoop.addGameObject(player);
        
    	GameContent.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::moveShip);
            }
        });
    	
    	   	
    	gameLoop.start();
    	gameLoop.resume();
    	Platform.runLater(() -> {
    	    loadQuestion(); // Call the method after the scene is ready
    	});
    }
    
    //method for ship actions and key events
    @FXML
    void moveShip(KeyEvent e) {
    	double x = player.getVisual().getLayoutX();
        double y = player.getVisual().getLayoutY();
        double step=30;
        if(!gameLoop.isPaused()) {
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
        if(e.getCode()==KeyCode.P) {
        	togglePause();
        	
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
    //loads the question into the label and answers into the asteroid
    public void loadQuestion() {
        QLabel.setText(lvlControl.getCurrentQuestion().getText());
        List<Answer> answers = lvlControl.getCurrentQuestion().getAnswers();
        

        List<Asteroid> asteroids = new ArrayList<>(); // Keep track of all asteroids
        for (int i = 0; i < answers.size(); i++) {
            Asteroid asteroid = createAsteroid(answers.get(i), asteroids);
            asteroids.add(asteroid); // Add the new asteroid to the list
            asteroidAnimation(asteroid);
        }
     
    }

    public Asteroid createAsteroid(Answer answer, List<Asteroid> existingAsteroids) {
        Asteroid a = new Asteroid(answer);
        Random random = new Random();

        // Validate GameContent dimensions
        double gameWidth = GameContent.getWidth();
        double gameHeight = GameContent.getHeight();
        
        if (gameWidth <= 0 || gameHeight <= 0) {
            System.err.println("Error: GameContent dimensions not initialized.");
            return null;
        }

        boolean validPosition = false;
        int maxAttempts = 100; // Limit attempts to prevent infinite loops
        int attempts = 0;

        while (!validPosition && attempts < maxAttempts) {
            attempts++;

            // Generate random x and y positions
            double x = random.nextDouble() * (gameWidth - 100);
            double y = -100 - random.nextDouble() * 50;

            // Set the tentative position
            a.setLayoutXY(x, y);
            a.setStartxy(x, y);

            // Check for intersection with existing asteroids
            validPosition = true;
            for (Asteroid other : existingAsteroids) {
                if (a.getBoundInParent().intersects(other.getBoundInParent())) {
                    validPosition = false;
                    break;
                }
            }
        }

        if (!validPosition) {
            System.err.println("Error: Unable to place asteroid after " + maxAttempts + " attempts.");
            return null; // Return null if no valid position is found
        }

        // Add asteroid to the game content
        GameContent.getChildren().add(a.getVisual());
        gameLoop.addGameObject(a);

        return a;
    }


    
    public void asteroidAnimation(Asteroid a) {
    	 //animation of asteroid
        TranslateTransition asteroidFall=new TranslateTransition();
        asteroidFall.setDuration(Duration.seconds(30)); // Animation duration
        asteroidFall.setNode(a.getVisual()); // The node to animate
        asteroidFall.setByY(700); // Move 1000px down
        asteroidFall.setCycleCount(1); // play animation once
        asteroidFall.setAutoReverse(false); // Reverse direction after each cycle
        asteroidFall.setOnFinished(event -> {//used to destroy asteroid object once animation is finished
        	if(a.getAnswer().isCorrect()&&!(a.getIsShot())) {
            	restartQuestion();//restarts question if correct answer is not shot
            }else {
            	GameContent.getChildren().remove(a.getVisual());
            	removeGameObject(a);
            	asteroidTransitions.remove(asteroidFall);
            	if(a.getIsShot()) {
            		destroyed++;
            	}
            }
        	
        });
        asteroidTransitions.add(asteroidFall);
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
    	if(gameLoop.isPaused()) {
    		gameLoop.resume();
    		for (TranslateTransition transition : asteroidTransitions) {
    			transition.play();
    		}
    	}else {
    		gameLoop.pause();
    		for (TranslateTransition transition : asteroidTransitions) {
    			transition.pause();
    		}
    	}
    }

    public void updateHUD() {
        ScoreLabel.setText("Score: " + score);
        AmmoLabel.setText("Ammo: " + ammo);
        HealthLabel.setText("Health: "+health);
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

	@Override
	public void onCorrectAnswerShot() {
		ammo++;
		correct++;
		score+=10;
		if(!lvlControl.hasNextQuestion()) {
			lose=false;
			endLevel();
		}
		lvlControl.nextQuestion();
		updateHUD();
		loadQuestion();
	}
	public void onShipAsteroidCollision() {
		if(health>0) {
			int fadeDuration=3;
			//change fadeDuration to match invulnerable time 
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeDuration/ 2), player.getVisual());
		    fadeOut.setFromValue(1);
		    fadeOut.setToValue(0);
		    fadeOut.setAutoReverse(true);
		    fadeOut.setCycleCount(3 * 2);  // Each flicker has two transitions (fade in and out)
		    fadeOut.play();
			health--;
			System.out.println("Ship has taken Damage");
			updateHUD();
			
		}else{
			System.out.println("Gameover");
			losses++;
			lose=true;
			endLevel();
		}
	}
	//restarts the question if the correct answer is not shot and goes outside playzone
	public void restartQuestion() {
	    // Stop all asteroid animations
	    for (TranslateTransition transition : asteroidTransitions) {
	        transition.stop();
	    }
	    asteroidTransitions.clear(); // Clear all transitions

	    // Remove all asteroids from the game
	    ArrayList<Asteroid> asteroids = gameLoop.getAsteroids();
	    for (Asteroid a : asteroids) {
	        GameContent.getChildren().remove(a.getVisual());
	        removeGameObject(a);
	    }
	    asteroids.clear(); // Clear the asteroid list in the game loop
	    health--;
	    // Reload the question
	    loadQuestion();
	}
	public void endLevel() {
		lvlControl.setPlayerData(destroyed, losses, correct);
		if(lose==false) {
			System.out.print("won");
		}else {
			System.out.print("lost");
		}
		System.exit(0);
	}
}

