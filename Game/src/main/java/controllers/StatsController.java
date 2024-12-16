package controllers;

import application.core.DataParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// Controller for the stats screen
public class StatsController extends BaseController {	
    @FXML
    private Button BackButton; // Button to return to menu

    @FXML
    private Label correctLabel; // Label for correct answers

    @FXML
    private Label destroyedLabel; // Label for destroyed asteroids

    @FXML
    private Label levelLabel; // Label for current level
    
    @FXML
    private Label lossLabel; // Label for losses

    @FXML
    private Label winLabel; // Label for wins

    // Handle the back button action
    @FXML
    void ReturnToMenu(ActionEvent event) throws Exception {
        this.main.playSimpleSound("blipSelect", 0.05); // Play a sound
        this.switchScene("menu"); // Switch to the menu screen
    }

    // Initialize the controller
    public void start() {
        DataParser parser = this.main.getParser(); // Get data from parser

        levelLabel.setText(String.format("Level: %d", parser.getLevel())); // Set level
        winLabel.setText(String.format("Wins: %d", parser.getWins())); // Set wins
        lossLabel.setText(String.format("Losses: %d", parser.getLosses())); // Set losses
        correctLabel.setText(String.format("Correct Answers: %d", parser.getCorrect())); // Set correct answers
        
        destroyedLabel.setVisible(false); // Hide destroyed label
        //destroyedLabel.setText(String.format("Asteroids Destroyed: %d", parser.getDestroyed())); // Optional
    }
    
    @Override
    public void clean() {
    }
}
