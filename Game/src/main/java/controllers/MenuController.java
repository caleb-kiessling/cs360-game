package controllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// Menu screen controller
public class MenuController extends BaseController {
    @FXML
    private Label MenuLabel; // Menu title

    @FXML
    private Button ExitButton; // Exit button

    @FXML
    private Button LevelButton; // Level select button

    @FXML
    private Button StatsButton; // Stats button

    @Override
    public void start() {
        this.MenuLabel.setText(Main.TITLE); // Set title
        this.main.playPersistentSound("menu", 0.15, true); // Play menu music
    }

    @FXML
    void EndProgram(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05); // Play sound
        System.exit(0); // Exit
    }

    @FXML
    void SwitchSceneStatistics(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05); // Play sound
        switchScene("stats"); // Go to stats
    }

    @FXML
    void SwitchSceneToLevels(ActionEvent event) {
        this.main.playSimpleSound("blipSelect", 0.05); // Play sound
        this.switchScene("level_select"); // Go to level select
    }

    @Override
    public void clean() {
    }
}
