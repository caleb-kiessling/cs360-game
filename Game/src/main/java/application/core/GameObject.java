package application.core;

import javafx.geometry.Bounds;

public interface GameObject {
	void update(double deltaTime); // game objects eg.. falling asteroids/space ships

	Bounds getBoundInParent();
}
