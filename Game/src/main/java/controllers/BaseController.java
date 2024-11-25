package controllers;

import application.Main;


/**
 * BaseController is the parent class of all other controllers.
 * 
 * @author Caleb Kiessling
*/
public abstract class BaseController {
	/**
	 * A variable for the main.
	*/
    protected Main main;

    /**
     * This method sets the main of the class.
     * 
     * @param main
    */
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

    /**
     * Initializes the controller.
    */
    public void start() {
    }

    /**
     * Cleans the controller.
     * Should occur when switching controllers.
    */
    public void clean() {
    }
}
