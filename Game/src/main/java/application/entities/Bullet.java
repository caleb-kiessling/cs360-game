package application.entities;

import application.core.GameObject;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public class Bullet implements GameObject{
private Rectangle rectangle;
public Bullet() {
	this.rectangle=new Rectangle(5,20);
}
@Override
public void update(double deltaTime) {
	// TODO Auto-generated method stub
	
}
public Rectangle getVisual() {
	return this.rectangle;
}
@Override
public Bounds getBoundInParent() {
	return rectangle.getBoundsInParent();
}
}
