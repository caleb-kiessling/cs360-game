package application.entities;

import application.core.*;
import javafx.scene.shape.Rectangle;

public class Spaceship implements GameObject{
	private Rectangle visual;
	private double speed = 100.0;
	
	public Spaceship(Rectangle rectangle) {
		this.visual = rectangle;
	}
	
	public Spaceship() {
		this.visual = new Rectangle(30.0, 15.0);
	}
	
	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
