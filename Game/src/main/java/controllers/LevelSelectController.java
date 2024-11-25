package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class LevelSelectController extends BaseController {
    @FXML
    private Button BackButton;

    @FXML
    private HBox Contents;

    @FXML
    void ReturnToMenu(ActionEvent event) throws Exception {
    	this.switchScene("menu");
    }

    @Override
    public void clean() {

    }
    
    @Override
    public void start() {
        loadLevels();
    }

    private void loadLevels() {
    	
    }
}
