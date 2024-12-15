package controllers;

import application.core.DataParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StatsController extends BaseController {	
    @FXML
    private Button BackButton;

    @FXML
    private Label correctLabel;

    @FXML
    private Label destroyedLabel;

    @FXML
    private Label levelLabel;
    
    @FXML
    private Label lossLabel;

    @FXML
    private Label winLabel;

    @FXML
    void ReturnToMenu(ActionEvent event) throws Exception {
        this.main.playSimpleSound("blipSelect", 0.05);
        
        

    	this.switchScene("menu");
    }
    public void start() {
    	DataParser parser = this.main.getParser();

    	levelLabel.setText(String.format("Level: %d", parser.getLevel()));
    	winLabel.setText(String.format("Wins: %d", parser.getWins()));
        lossLabel.setText(String.format("Losses: %d", parser.getLosses()));
        correctLabel.setText(String.format("Correct Answers: %d", parser.getCorrect()));
        
        destroyedLabel.setVisible(false);
        //destroyedLabel.setText(String.format("Asteroids Destroyed: %d", parser.getDestroyed()));
    }
    
    @Override
    public void clean() {
    }
}
