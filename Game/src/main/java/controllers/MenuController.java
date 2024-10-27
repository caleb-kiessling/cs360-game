package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController extends BaseController {

    @FXML
    private Button ExitButton;

    @FXML
    private Button LevelButton;

    @FXML
    private Button StatsButton;

    @Override
    public void initialize() {
    }

    @Override
    public void start() {
    }

    @FXML
    void EndProgram(ActionEvent event) {
        System.exit(0); // Close the application
    }

    @FXML
    void SwitchSceneStatistics(ActionEvent event) {
        //switchScene("statistics"); // this doesnt exist right now.
    }

    @FXML
    void SwitchSceneToLevels(ActionEvent event) {
       this.switchScene("level_select");
    }

    @Override
    public void clean() {
    }
}
