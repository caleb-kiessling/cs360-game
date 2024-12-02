package application.entities;

import application.core.*;
import javafx.scene.Node;
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
	
	public Rectangle getVisual() {
		return visual;
		//if we update this class so the spaceship is made up of multiple shapes,
		//change this method to return a list of all the shape objects.
	}
	
	public void setX(double xCoord) {
		visual.setX(xCoord);
	}
	
	public void setY(double yCoord) {
		visual.setY(yCoord);
	}
	
	public void setXY(double xCoord, double yCoord) {
		visual.setX(xCoord);
		visual.setY(yCoord);
	}
}
