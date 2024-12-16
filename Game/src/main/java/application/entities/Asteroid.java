package application.entities;

import application.core.Answer;
import application.core.GameObject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

// Represents an asteroid in the game
public class Asteroid extends GameObject {
    public static int HEIGHT = 70; // Height of the asteroid
    public static int WIDTH = 70; // Width of the asteroid

    private Answer answer; // Associated answer for the asteroid
    private AsteroidBelt belt; // Reference to the asteroid belt it belongs to

    // Constructor to create an asteroid
    public Asteroid(double x, double y, Answer answer, AsteroidBelt belt) {
        this.answer = answer;
        this.belt = belt;

        this.node = this.createNode(x, y); // Create the visual representation
    }

    // Create the visual of the asteroid
    private Node createNode(double x, double y) {
        ImageView asteroidImage = new ImageView("asteroid.png"); // Asteroid image
        asteroidImage.setFitWidth(WIDTH);
        asteroidImage.setFitHeight(HEIGHT);

        Label label = new Label(this.answer.getText()); // Text label for the answer
        //label.setStyle("-fx-font-size: 14; -fx-alignment: center;");
        
        int textLength = this.answer.getText().length();
        int fontSize = calculateFontSize(textLength); // Calculate appropriate font size
        label.setStyle(label.getStyle() + "-fx-font-size: " + fontSize + "; -fx-alignment: center;");

        StackPane stackPane = new StackPane(); // Stack the image and label
        stackPane.getChildren().addAll(asteroidImage, label);

        stackPane.setLayoutX(x - asteroidImage.getFitWidth() / 2); // Center position
        stackPane.setLayoutY(y - asteroidImage.getFitHeight() / 2);

        return stackPane;
    }

    // Calculate font size based on text length
    private int calculateFontSize(int textLength) {
        if (textLength <= 10) {
            return 13; // Default size for short text
        } else if (textLength <= 14) {
            return 11; // Slightly smaller for medium text
        } else if (textLength <= 17) {
            return 9; // Smaller for longer text
        } else {
            return 8; // Smallest size for very long text
        }
    }
    
    // Get the asteroid belt this asteroid belongs to
    public AsteroidBelt getBelt() {
        return this.belt;
    }

    // Get the answer associated with this asteroid
    public Answer getAnswer() {
        return answer;
    }

    @Override
    public void update(double deltaTime) {
        // Not used; only keeping for collision handling
    }
}
