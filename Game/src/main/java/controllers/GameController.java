package controllers;

import application.core.GameLoop;
import application.core.GameObject;
import application.entities.Spaceship;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

public class GameController extends BaseController {
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
    	
    }
    
    @Override
    public void initialize() {
        this.gameLoop = new GameLoop();
        setupKeyHandler();
        gameLoop.start();
    }
    
    @Override
    public void clean() {
        gameLoop.stop();
        gameLoop = null;
    }
}
