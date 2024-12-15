package application;

import java.io.IOException;

import application.core.DataParser;
import controllers.BaseController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {
	public static final int HEIGHT = 450;
	public static final int WIDTH = 600;
	
	public static final String TITLE = "Syntax Blasters";
	
    private Stage primaryStage;
    private BaseController currentController;
    private DataParser parser;
    
    private MediaPlayer persistentSound;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            this.parser = new DataParser("levels.txt", "playerdata.txt");

            this.primaryStage = stage;
            this.primaryStage.setTitle(TITLE);
            this.primaryStage.setResizable(true);
            this.primaryStage.getIcons().add(new Image("icon.png"));

            this.primaryStage.show();
            this.primaryStage.requestFocus();
            this.showScene("menu");
            this.primaryStage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSimpleSound(String name, double volume) {
    	 try {
    		 Media sound = new Media(Main.class.getResource("/" + name + ".wav").toString());
             MediaPlayer mediaPlayer = new MediaPlayer(sound);
             mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
             mediaPlayer.setVolume(volume);
             mediaPlayer.play();
         } catch (Exception e) {
             System.err.println("Error playing sound effect: " + e.getMessage());
         }
    }
    
    public void playPersistentSound(String name, double volume, boolean loop) {
    	this.stopPersistentSound();
    	
    	try {
    		 Media sound = new Media(Main.class.getResource("/" + name + ".wav").toString());
             persistentSound = new MediaPlayer(sound);
             persistentSound.setVolume(volume);
             
             if (loop) {
            	 persistentSound.setCycleCount(MediaPlayer.INDEFINITE);
             }
             
             persistentSound.play();
    	} catch(Exception e){
            System.err.println("Error playing persistent sound: " + e.getMessage());
    	}
    }
    
    public void stopPersistentSound() {
    	 if (persistentSound != null) {
    		 persistentSound.stop();
    		 persistentSound.dispose();
    		 persistentSound = null;
         }
    }
    
    public void showScene(String fxml) throws Exception {
        if (this.currentController != null) {
            this.currentController.clean();
        }

        Scene scene = new Scene(loadFXML(fxml));
        this.primaryStage.setScene(scene);

        this.primaryStage.setForceIntegerRenderScale(true);
        
        if (this.currentController != null) {
            this.currentController.setMain(this);
            this.currentController.start();
        }
    }

    public DataParser getParser() {
        return this.parser;
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        Parent parent = fxmlLoader.load();

        currentController = fxmlLoader.getController();
        
        return parent;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
