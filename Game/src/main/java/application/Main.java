package application;

import java.io.IOException;

import application.core.DataParser;
import application.core.Level;
import controllers.BaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	private static final int HEIGHT = 450; // these will be implemented if
										   //we can find a scale to scale to the screen size.
	private static final int WIDTH = 600;
	
	private static final String TITLE = "Syntax Blasters";
	
    private Stage primaryStage;
    private BaseController currentController;
    private DataParser parser;
    private Level currentLevel;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            this.parser = new DataParser("levels.txt", "playerdata.txt");
            this.currentLevel = null;

            this.primaryStage = stage;
            this.primaryStage.setTitle(TITLE);
            this.primaryStage.setResizable(false);
            this.primaryStage.getIcons().add(new Image("icon.png"));

            this.primaryStage.show();
            this.primaryStage.requestFocus();
            this.showScene("menu");
            this.primaryStage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showScene(String fxml) throws Exception {
        if (this.currentController != null) {
            this.currentController.clean();
        }

        Scene scene = new Scene(loadFXML(fxml));
        this.primaryStage.setScene(scene);

        this.primaryStage.setForceIntegerRenderScale(true);
        //this.primaryStage.setHeight(HEIGHT);
        //this.primaryStage.setWidth(WIDTH);
    }

    public void setLevel(Level level) {
        this.currentLevel = level;
    }

    public Level getLevel() {
        return this.currentLevel; // Call this to get level data in the GameController.
    }

    public DataParser getParser() {
        return this.parser;
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        Parent parent = fxmlLoader.load();

        currentController = fxmlLoader.getController();
        if (currentController != null) {
            currentController.setMain(this);
            currentController.start();
        }

        return parent;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
