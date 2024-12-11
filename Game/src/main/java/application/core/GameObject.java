package application.core;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public interface GameObject {
	void update(double deltaTime); // game objects eg.. falling asteroids/space ships

	Bounds getBoundInParent();
	Node getVisual();
}
