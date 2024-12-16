package application.entities;

import application.Main;
import application.core.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

// Represents a belt of asteroids with a question
public class AsteroidBelt extends GameObject {
    private static int OFF_SET = -50; // Offset for the question label

    private Question question; // The question associated with the belt
    private ArrayList<Asteroid> asteroids; // List of asteroids in the belt
    private Label label; // Label to display the question

    // Constructor to create an asteroid belt
    public AsteroidBelt(Question question) {
        this.question = question;
        this.asteroids = new ArrayList<>();

        this.node = new Pane(); // Create a container for the belt

        this.label = new Label(this.question.getText()); // Label for the question

        // Center the label dynamically when width changes... temp fix
        this.label.widthProperty().addListener((observable, oldValue, newValue) -> {
            this.label.setLayoutX((Main.WIDTH - newValue.doubleValue()) / 2);
        });

        this.label.setLayoutY(OFF_SET); // Position the label
        ((Pane) this.node).getChildren().add(this.label); // Add label to the pane

        this.createAsteroids(); // Generate asteroids
        
        //this.label.setLayoutX((Main.WIDTH - newValue.doubleValue()) / 2);
    }

    // Create asteroids for the belt
    private void createAsteroids() {
        ArrayList<Answer> answers = this.question.getShuffledAnswers(); // Get all answers
        int count = answers.size(); // Number of asteroids to create

        double spacing = Main.WIDTH / (count + 1); // Calculate spacing for asteroids

        for (int index = 0; index < count; index++) {
            double x = spacing * (index + 1); // X position
            double y = 0; // Y position

            // Create an asteroid for each answer
            Asteroid asteroid = new Asteroid(x, y, answers.get(index), this);
            this.asteroids.add(asteroid);

            // Add the asteroid to the pane
            ((Pane) this.node).getChildren().add(asteroid.getNode());
        }
    }

    // Get the list of asteroids
    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    @Override
    public void update(double deltaTime) {
        // Move the belt downward based on the question's speed
        this.node.setLayoutY(this.node.getLayoutY() + (this.question.getSpeed() * deltaTime));
    }
}
