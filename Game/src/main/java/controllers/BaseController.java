package controllers;

import application.Main;

public abstract class BaseController {
    protected Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    protected void switchScene(String fxml) {
        try {
            main.showScene(fxml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
    	
    }

    public void start() {
    }

    public void clean() {
    }
}
