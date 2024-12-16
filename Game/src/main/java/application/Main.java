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
    // Window dimensions
    public static final int HEIGHT = 450;
    public static final int WIDTH = 600;

    // Application title
    public static final String TITLE = "Syntax Blasters";

    private Stage primaryStage;
    private BaseController currentController;
    private DataParser parser;
    private MediaPlayer persistentSound;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Initialize data parser with level and player data
            this.parser = new DataParser("levels.txt", "playerdata.txt");

            // Set up the primary stage
            this.primaryStage = stage;
            this.primaryStage.setTitle(TITLE);
            this.primaryStage.setResizable(true);
            this.primaryStage.getIcons().add(new Image("icon.png"));

            // Show the stage and load the menu scene
            this.primaryStage.show();
            this.primaryStage.requestFocus();
            this.showScene("menu");
            this.primaryStage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace(); // Print errors to the console
        }
    }

    // Play a short sound effect
    public void playSimpleSound(String name, double volume) {
        try {
            Media sound = new Media(Main.class.getResource("/" + name + ".wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose); // Dispose when done
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing sound effect: " + e.getMessage());
        }
    }

    // Play a persistent sound with optional looping
    public void playPersistentSound(String name, double volume, boolean loop) {
        this.stopPersistentSound(); // Stop any current persistent sound

        try {
            Media sound = new Media(Main.class.getResource("/" + name + ".wav").toString());
            persistentSound = new MediaPlayer(sound);
            persistentSound.setVolume(volume);

            if (loop) {
                persistentSound.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
            }

            persistentSound.play();
        } catch (Exception e) {
            System.err.println("Error playing persistent sound: " + e.getMessage());
        }
    }

    // Stop the currently playing persistent sound
    public void stopPersistentSound() {
        if (persistentSound != null) {
            persistentSound.stop();
            persistentSound.dispose();
            persistentSound = null;
        }
    }

    // Load and display a new scene by its FXML file
    public void showScene(String fxml) throws Exception {
        if (this.currentController != null) {
            this.currentController.clean(); // Clean up the current controller
        }

        Scene scene = new Scene(loadFXML(fxml));
        this.primaryStage.setScene(scene);

        this.primaryStage.setForceIntegerRenderScale(true); // Ensure integer scaling

        if (this.currentController != null) {
            this.currentController.setMain(this);
            this.currentController.start(); // Start the new controller
        }
    }

    // Accessor for the data parser
    public DataParser getParser() {
        return this.parser;
    }

    // Load an FXML file and initialize its controller
    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        Parent parent = fxmlLoader.load();

        currentController = fxmlLoader.getController(); // Set the current controller

        return parent;
    }

    // Main entry point for the application
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
