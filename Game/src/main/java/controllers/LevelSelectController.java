package controllers;

import java.io.IOException;

import application.core.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LevelSelectController extends BaseController {

    @FXML
    private Button BackButton;

    @FXML
    private Label ContentsLabel;

    @FXML
    private Label LevelLabel;

    @FXML
    private Button NewGameButton;

    @FXML
    private Button PlayButton;

    @FXML
    private HBox Contents;

    @FXML
    void NewGameAction(ActionEvent event) throws IOException {
        this.main.playSimpleSound("pickUpCoin", 0.05);

    	DataParser data = this.main.getParser();
    	data.setLevel(1);
        
        this.PlayGameAction(null);
    }

    @FXML
    void PlayGameAction(ActionEvent event) {
        this.main.playSimpleSound("pickUpCoin", 0.05);

        this.switchScene("game_area"); // Switch back to the main menu
    }

    @FXML
    void ReturnToMenu(ActionEvent event) throws Exception {
        this.main.playSimpleSound("blipSelect", 0.05);

        this.switchScene("menu"); // Switch back to the main menu
    }

    @Override
    public void clean() {
    }

    @Override
    public void start() {
    	DataParser data = this.main.getParser();
    	int levelNumber = data.getLevel();
    	int levelIndex = levelNumber - 1;
        	
    	Level level = data.getLevel(levelIndex);
    	
    	if (levelNumber == data.getLevelCount()) {
    		this.PlayButton.setText("Play Again");
    	}
    	
    	this.ContentsLabel.setText(level.getTopic());
    	
    	this.LevelLabel.setText(String.format("Level %d", levelNumber));
    }

    
}
