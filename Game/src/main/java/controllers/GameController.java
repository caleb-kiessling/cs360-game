package controllers;

import java.awt.event.KeyListener;

import application.core.GameLoop;
import application.core.GameObject;
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
    
//Set up key listener on initialization
    @FXML
	public void initialize() {
    	ammo=5;
    	AmmoLabel.setText("["+ammo+"] SHOTS");
    	player = new Spaceship();
        GameContent.getChildren().add(player.getVisual());
        player.setXY(300,300);
    	GameContent.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::moveShip);
            }
        });
    	System.out.print(GameContent.getChildren());
    	System.out.print(player.getParent());
    	
    	
	}
    //method for ship actions and key events
    @FXML
    void moveShip(KeyEvent e) {
    	double x = player.getVisual().getLayoutX();
        double y = player.getVisual().getLayoutY();
        double step=15;
        //used for debugging will show key pressed and coordinate
        //System.out.println("Key pressed: " + e.getCode()); // Check key event
       // System.out.println("Before move: X=" + x + ", Y=" + y);

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
        
        System.out.println("After move: X=" + player.getVisual().getLayoutX() + ", Y=" + player.getVisual().getLayoutY());

   }
    //method that creates that shape for bullet and play animation of bullet firing
    @FXML
    void shoot() {
    	if(ammo>0) {
    		ammo--;
        	AmmoLabel.setText("["+ammo+"] SHOTS");
        	Rectangle bullet=new Rectangle(5,20);
            GameContent.getChildren().add(bullet);
            bullet.setX(300);//setX and setY need to be the same as ship to spawn in it
            bullet.setY(300);
            bullet.setLayoutX(player.getVisual().getLayoutX());
            bullet.setLayoutY(player.getVisual().getLayoutY());
            //print statement for debugging
            System.out.println("Bullet at: X=" + bullet.getLayoutX() + ", Bullet at:" + bullet.getLayoutY());
            //animation of bullet
            TranslateTransition shootBullet=new TranslateTransition();
            shootBullet.setDuration(Duration.seconds(2)); // Animation duration
            shootBullet.setNode(bullet); // The node to animate
            shootBullet.setByY(-1000); // Move 1000px up
            shootBullet.setCycleCount(1); // play animation once
            shootBullet.setAutoReverse(false); // Reverse direction after each cycle
            shootBullet.setOnFinished(event -> {//used to destroy bullet object once animation is finished
            	GameContent.getChildren().remove(bullet);
            });

            // Start the animation
            shootBullet.play();
    	}
    	

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
    public void start() {
        this.gameLoop = new GameLoop();
        setupKeyHandler();
        
        System.out.print("GameCOntroller initialize()");
        System.out.print(GameContent.getChildren().size());
        //player = new Spaceship();
       // GameContent.getChildren().add(player);
      //  int playerIndex = GameContent.getChildren().indexOf(player);
        //GameContent.getChildren().get(playerIndex).setLayoutX(20);
       // GameContent.getChildren().get(playerIndex).setLayoutY(20);
       // GameContent.getChildren().get(playerIndex).setVisible(true);
        GameContent.setVisible(true);
        
        gameLoop.start();
      //  player = new Spaceship();
      //  player.setXY(300.0, 300.0);
       // GameContent.getChildren().add(player.getVisual());
        
        //.addKeyListener();
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
