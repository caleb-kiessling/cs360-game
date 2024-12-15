package controllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MenuController extends BaseController {
    @FXML
    private Label MenuLabel;
	
    @FXML
    private Button ExitButton;

    @FXML
    private Button LevelButton;

    @FXML
    private Button StatsButton;

    @Override
    public void start() {
    	this.MenuLabel.setText(Main.TITLE);
    	this.main.playPersistentSound("menu", 0.15, true);
    }

    @FXML
    void EndProgram(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05);

        System.exit(0); // Close the application
    }

    @FXML
    void SwitchSceneStatistics(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05);

        switchScene("stats");
    }

    @FXML
    void SwitchSceneToLevels(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05);

       this.switchScene("level_select");
    }

    @Override
    public void clean() {
    }
}
