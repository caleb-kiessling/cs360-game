package controllers;

import application.Main;

// Base class for all controllers
public abstract class BaseController {
    protected Main main; // Reference to the main application

    // Set the main application
    public void setMain(Main main) {
        this.main = main;
    }

    // Switch to a different scene
    protected void switchScene(String fxml) {
        try {
            main.showScene(fxml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Start the controller
    public void start() {
    }

    // Clean up the controller (called when switching)
    public void clean() {
    }
}
