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
    
    private int ammo;
    private LevelControl lvlControl;
    private List<TranslateTransition> asteroidTransitions = new ArrayList<>();

    
    @FXML
    @Override
    public void start() {
    	lvlControl=new LevelControl();
    	gameLoop=new GameLoop(GameContent);
        gameLoop.setGameEventListener(this); // Set the listener
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
    
    public void loadQuestion() {
        QLabel.setText(lvlControl.getCurrentQuestion().getText());
        List<Answer> answers = lvlControl.getCurrentQuestion().getAnswers();
        

        List<Asteroid> asteroids = new ArrayList<>(); // Keep track of all asteroids
        for (int i = 0; i < answers.size(); i++) {
            Asteroid asteroid = createAsteroid(answers.get(i), asteroids);
            asteroids.add(asteroid); // Add the new asteroid to the list
            asteroidFall(asteroid);
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


    
    public void asteroidFall(Asteroid a) {
    	 //animation of asteroid
        TranslateTransition asteroidFall=new TranslateTransition();
        asteroidFall.setDuration(Duration.seconds(30)); // Animation duration
        asteroidFall.setNode(a.getVisual()); // The node to animate
        asteroidFall.setByY(700); // Move 1000px down
        asteroidFall.setCycleCount(1); // play animation once
        asteroidFall.setAutoReverse(false); // Reverse direction after each cycle
        asteroidFall.setOnFinished(event -> {//used to destroy asteroid object once animation is finished
        	GameContent.getChildren().remove(a.getVisual());
        	removeGameObject(a);
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

	@Override
	public void onCorrectAnswerShot() {

		if (lvlControl.getQuestionCount() < lvlControl.getCurrentLevel().getQuestions().size()) {
			System.out.println("Loading the next question...");
			lvlControl.nextQuestion();
			loadQuestion();
		}
		ammo++;
	}
}
