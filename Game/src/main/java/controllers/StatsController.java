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
    private Label lossLabel;

    @FXML
    private Label winLabel;

    @FXML
    void ReturnToMenu(ActionEvent event) throws Exception {
    	this.switchScene("menu");
    }
    public void start() {
    	DataParser parser = this.main.getParser();
    	
    	winLabel.setText(String.format("Wins: %d", parser.getWins()));
    }
    
    @Override
    public void clean() {
    }
}
