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
        player.setXY(50.0, 50.0);
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
