package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class LevelSelectController extends BaseController {
    @FXML
    private Button BackButton;
    
    @FXML
    private Button PlayButton;

    @FXML
    private HBox Contents;

    @FXML
    void ReturnToMenu(ActionEvent event) throws Exception {
    	this.switchScene("menu");
    }
    
    @FXML
    void PlayLevel(ActionEvent event) throws Exception {
    	this.switchScene("game_area");
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
