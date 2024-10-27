package application;
	
import java.io.IOException;

import controllers.BaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	private Stage primaryStage;
	private BaseController currentController;
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			this.primaryStage = stage;
			this.primaryStage.setTitle("Space ship");
			//this.primaryStage.setFullScreen(true);
			this.primaryStage.setResizable(false);
			this.primaryStage.show();
			this.primaryStage.requestFocus();
			
			this.showScene("menu");
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showScene(String fxml) throws Exception {
		if (this.currentController != null) {
			this.currentController.clean();
		}
		
	    Scene scene = new Scene(loadFXML(fxml));
	    this.primaryStage.setScene(scene);
	}
	
    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        
        currentController = fxmlLoader.getController();
        if (currentController != null) {
            currentController.setMain(this);
            currentController.initialize();
        }
        
        return parent;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}

