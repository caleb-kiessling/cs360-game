package application.entities;

import application.core.Answer;
import application.core.GameObject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Asteroid extends GameObject {
	public static int HEIGHT = 70;
	public static int WIDTH = 70;
	
	private Answer answer;
	private AsteroidBelt belt;
	
	public Asteroid(double x, double y, Answer answer, AsteroidBelt belt) {
		this.answer = answer;
		this.belt = belt;
		
		
		this.node = this.createNode(x, y);
	}
	
	private Node createNode(double x, double y) {
		ImageView asteroidImage = new ImageView("asteroid.png");
		asteroidImage.setFitWidth(WIDTH);
		asteroidImage.setFitHeight(HEIGHT);
		
		Label label = new Label(this.answer.getText());
		label.setStyle("-fx-font-size: 14; -fx-alignment: center;");
	
	    StackPane stackPane = new StackPane();
	    stackPane.getChildren().addAll(asteroidImage, label);

	    stackPane.setLayoutX(x - asteroidImage.getFitWidth() / 2);
	    stackPane.setLayoutY(y - asteroidImage.getFitHeight() / 2);

	    return stackPane;
	}
	
	public AsteroidBelt getBelt() {
		return this.belt;
	}
	
    public Answer getAnswer() {
        return answer;
    }
    
    @Override
    public void update(double deltaTime) {
    	// we arent using this... only really want the collision parts of the game object.
    }
}
