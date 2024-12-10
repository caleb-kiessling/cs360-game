package controllers;

import java.awt.event.KeyListener;

import application.core.GameLoop;
import application.core.GameObject;
import application.entities.Spaceship;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
    
//Set up key listener on initialization
    @FXML
	public void initialize() {
    	GameContent.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::moveShip);
            }
        });
    	
	}
    //method for moving the ship. Currently does not function correctly
    @FXML
    void moveShip(KeyEvent e) {
    	double x = player.getLayoutX();
        double y = player.getLayoutY();
        double step=20;
        //used for debugging will show key pressed and coordinate but ship visually does not move to the coordinate
        System.out.println("Key pressed: " + e.getCode()); // Check key event
        System.out.println("Before move: X=" + x + ", Y=" + y);

        if (e.getCode() == KeyCode.UP) {
            player.setLayoutY(y - step);
        }
        if (e.getCode() == KeyCode.DOWN) {
            player.setLayoutY(y + step);
        }
        if (e.getCode() == KeyCode.LEFT) {
            player.setLayoutX(x - step);
        }
        if (e.getCode() == KeyCode.RIGHT) {
            player.setLayoutX(x + step);
        }
        System.out.println("After move: X=" + player.getLayoutX() + ", Y=" + player.getLayoutY());

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
        player = new Spaceship();
        GameContent.getChildren().add(player);
        int playerIndex = GameContent.getChildren().indexOf(player);
        GameContent.getChildren().get(playerIndex).setLayoutX(20);
        GameContent.getChildren().get(playerIndex).setLayoutY(20);
        GameContent.getChildren().get(playerIndex).setVisible(true);
        GameContent.setVisible(true);
        
        gameLoop.start();
        player = new Spaceship();
        player.setXY(300.0, 300.0);
        GameContent.getChildren().add(player.getVisual());
        
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
