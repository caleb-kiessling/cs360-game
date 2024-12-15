package application.entities;

import application.Main;
import application.core.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class AsteroidBelt extends GameObject {
	private static int OFF_SET = -50;
	
	private Question question;
	private ArrayList<Asteroid> asteroids;
	private Label label;
	
	public AsteroidBelt(Question question) {
		this.question = question;
		this.asteroids = new ArrayList<>();
		
		this.node = new Pane();
		
		this.label = new Label(this.question.getText());
		this.label.setLayoutX((Main.WIDTH/2) - (this.label.getWidth() / 2));
		this.label.setLayoutY(OFF_SET);
		
		((Pane) this.node).getChildren().add(this.label);
		
		this.createAsteroids();
	}

	private void createAsteroids() {
		ArrayList<Answer> answers = this.question.getAnswers();
		int count = answers.size();
		
		double spacing = Main.WIDTH / (count + 1);
		
		for (int index = 0; index < count; index++) {
			double x = spacing * (index + 1);
			double y = 0;
			
			Asteroid asteroid = new Asteroid(x, y, answers.get(index), this);
			this.asteroids.add(asteroid);
			
			((Pane) this.node).getChildren().add(asteroid.getNode());
		}
	}

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    @Override
    public void update(double deltaTime) {
    	this.node.setLayoutY(this.node.getLayoutY() + (this.question.getSpeed() * deltaTime));
    }
}
